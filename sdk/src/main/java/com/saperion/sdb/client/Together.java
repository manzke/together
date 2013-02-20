package com.saperion.sdb.client;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.google.common.base.Preconditions;
import com.saperion.common.lang.string.Strings;
import com.saperion.sdb.client.exceptions.AuthenticationException;
import com.saperion.sdb.client.exceptions.ConnectException;
import com.saperion.sdb.client.helper.ClientRuntimeDelegate;
import com.saperion.sdb.client.helper.StreamResultBodyReader;
import com.saperion.sdb.client.helper.StreamResultBodyWriter;
import com.saperion.sdb.client.helper.TypedFinder;
import com.saperion.sdb.client.models.ClientModel;
import com.saperion.sdb.client.models.CurrentUser;
import com.saperion.sdb.client.models.Document;
import com.saperion.sdb.client.models.Folder;
import com.saperion.sdb.client.models.Space;
import com.saperion.sdb.client.models.User;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;

public class Together implements Closeable {
	public static final String AUTH_HEADER = "Authorization";
	
	private static final GenericType<List<com.saperion.sdb.rs.models.Space>> TYPE =
			new GenericType<List<com.saperion.sdb.rs.models.Space>>() {
			};
	
	protected Client client;
	protected String base;
	protected String auth = "";
	protected boolean showDeleted;
	
	protected Together(){}
	
	public Together(Config config){
		StringBuilder builder = new StringBuilder();
		builder.append(config.protocol.value).append(config.domain).append(":").append(config
				.port);
		if (!Strings.isNullOrEmpty(config.serviceName)){
			builder.append("/").append(config.serviceName);
		}
		
		ClientConfig clientConfig = new DefaultApacheHttpClientConfig();
		clientConfig.getClasses().add(JacksonJsonProvider.class);
		clientConfig.getClasses().add(StreamResultBodyReader.class);
		clientConfig.getClasses().add(StreamResultBodyWriter.class);

		client = Client.create(clientConfig);
		base = builder.toString();

		//this is necessary, as the if-clause above does not work somehow... ?
		if (base.endsWith("/")){
			base = base.substring(0, base.length() - 1);
		}
	}
	
	public CurrentUser me() throws AuthenticationException, ConnectException{
		return fire(new ClientRequest<CurrentUser>() {
			@Override
			public CurrentUser fire() throws Throwable {
				return new CurrentUser(client.resource(base+"/users/current").accept(MediaType.APPLICATION_JSON).header(AUTH_HEADER, auth).get(com.saperion.sdb.rs.models.User.class), Together.this);
			}
		});
	}
	
	public List<Space> mySpaces() throws AuthenticationException, ConnectException{
		return fire(new ClientRequest<List<Space>>() {
			@Override
			public List<Space> fire() throws Throwable {
				List<com.saperion.sdb.rs.models.Space> spaces = client.resource(base+"/spaces").accept(MediaType.APPLICATION_JSON).header(AUTH_HEADER, auth).get(TYPE);
				List<Space> converted = new ArrayList<Space>();
				
				for(com.saperion.sdb.rs.models.Space space : spaces){
					converted.add(new Space(space, Together.this));
				}
				
				return converted;
			}
		});
	}
	
	public Together login(String email, String password) throws AuthenticationException, ConnectException{
		auth = "Basic "+Base64.encodeBase64String((email+":"+password).getBytes());
		me();
		return this;
	}
	
	/**
	 * Support Types {@link Space}, {@link Folder}, {@link Document}, {@link User}
	 * @param type Support Types {@link Space}, {@link Folder}, {@link Document}, {@link User}
	 * @return {@link TypedFinder} which needs the ID to retrieve the object for the submitted type
	 */
	public <ClientType, T extends ClientModel<ClientType>> TypedFinder<ClientType, T> find(Class<T> type){
		return new TypedFinder<ClientType, T>(this, type);
	}
	
	@Override
	public String toString() {
		return "URL ["+base+"] authenticated? "+!Strings.isBlank(auth);
	}
	
	@Override
	public void close() {
		client.destroy();
	}
	
	public <T> T get(String path, GenericType<T> type) throws AuthenticationException, ConnectException{
		return get(path, MediaType.APPLICATION_JSON, type);
	}
	
	public <T> T get(final String path, final String accept, final GenericType<T> type) throws AuthenticationException, ConnectException{
		return fire(new ClientRequest<T>() {
			@Override
			public T fire() throws Throwable {
				return client.resource(base+path).accept(accept).header(AUTH_HEADER, auth).get(type);
			}
		});
	}
	
	public <T> T get(String path, Class<T> type) throws AuthenticationException, ConnectException{
		return get(path, MediaType.APPLICATION_JSON, type);
	}
	
	public <T> T get(final String path, final String accept, final Class<T> type) throws AuthenticationException, ConnectException{
		return fire(new ClientRequest<T>() {
			@Override
			public T fire() throws Throwable {
				return client.resource(base+path).accept(accept).header(AUTH_HEADER, auth).get(type);
			}
		});
	}
	
	public <T> T update(final String path, final T entity, final Class<T> type) throws AuthenticationException, ConnectException{
		return fire(new ClientRequest<T>() {
			@Override
			public T fire() throws Throwable {
				return client.resource(base+path).header(AUTH_HEADER, auth).put(type, entity);
			}
		});	
	}
	
	public <T> void update(final String path, final T entity) throws AuthenticationException, ConnectException{
		fire(new ClientRequest<Void>() {
			@Override
			public Void fire() throws Throwable {
				client.resource(base+path).header(AUTH_HEADER, auth).put(entity);
				return null;
			}
		});	
	}
	
	public <T> T create(final String path, final T entity, final Class<T> type) throws AuthenticationException, ConnectException{
		return fire(new ClientRequest<T>() {
			@Override
			public T fire() throws Throwable {
				return client.resource(base+path).header(AUTH_HEADER, auth).post(type, entity);
			}
		});
	}
	
	public void delete(final String path) throws AuthenticationException, ConnectException{
		fire(new ClientRequest<Void>() {
			@Override
			public Void fire() throws Throwable {
				client.resource(base+path).header(AUTH_HEADER, auth).delete();
				return null;
			}
		});
	}
	
	protected <T> T fire(ClientRequest<T> request) throws ConnectException, AuthenticationException{
		try {
			return request.fire();
		} catch (UniformInterfaceException e) {
			if(e.getResponse().getStatus() == 401){
				throw new AuthenticationException("You are not logged in.");
			}
			throw new ConnectException(e);
		} catch (ClientHandlerException e) {
			String message = e.getMessage();
			if(message.contains("Received authentication challenge")){
				throw new AuthenticationException("You are not logged in.");
			}
			throw e;
		} catch (Throwable e) {
			throw new ConnectException(e);
		}
	}
	
	public static interface ClientRequest<T>{
		T fire() throws Throwable;
	}
	
	public static class Config{
		private String domain = "127.0.0.1";
		private int port = 80;
		private Protocol protocol = Protocol.HTTPS;
		private String serviceName = "sdb-service-rest";
		
		public Config host(String domainOrIp){
			Preconditions.checkNotNull(domainOrIp, "Domain or IP can't be null.");
			this.domain = domainOrIp;
			return this;
		}
		
		public Config port(int port){
			Preconditions.checkArgument(port > 0, "Port must be higher than zero");
			this.port = port;
			return this;
		}
		
		public Config protocol(Protocol protocol){
			Preconditions.checkNotNull(protocol, "Protocol can't be null.");
			this.protocol = protocol;
			return this;
		}
		
		public Config resource(String serviceName){
			Preconditions.checkNotNull(serviceName, "Name of the service can't be null.");
			this.serviceName = serviceName;
			return this;
		}
		
		public Config client(Class<?> clientClass){
			RuntimeDelegate.setInstance(new ClientRuntimeDelegate());
			return this;
		}
		
		public Together build() throws Exception {
			StringBuilder builder = new StringBuilder();
			builder.append(protocol.value).append(domain).append(":").append(port);
			if (!Strings.isNullOrEmpty(serviceName)){
				builder.append("/").append(serviceName);
			}
			
			Together together = new Together();
			ClientConfig config = new DefaultApacheHttpClientConfig();
			config.getClasses().add(JacksonJsonProvider.class);
			config.getClasses().add(StreamResultBodyReader.class);
			config.getClasses().add(StreamResultBodyWriter.class);
			together.client = Client.create(config);
			together.base = builder.toString();

			//this is necessary, as the if-clause above does not work somehow... ?
			if (together.base.endsWith("/")){
				together.base = together.base.substring(0, together.base.length() - 1);
			}
			
			return together;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(protocol.value).append(domain).append(":").append(port).append("/").append(serviceName);
			return builder.toString();
		}
	}
	
	public static enum Protocol{
		HTTP("http://"),
		HTTPS("https://");
		
		private String value;
		private Protocol(String value){
			this.value = value;
		}
	}
}