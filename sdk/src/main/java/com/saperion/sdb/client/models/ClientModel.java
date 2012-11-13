package com.saperion.sdb.client.models;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.saperion.sdb.client.Together;
import com.saperion.sdb.client.exceptions.AuthenticationException;
import com.saperion.sdb.client.exceptions.ConnectException;
import com.saperion.sdb.client.helper.ItemDeserializer;
import com.saperion.sdb.rs.models.TypedIdentifiable;
import com.sun.jersey.api.client.GenericType;

@JsonDeserialize(using = ItemDeserializer.class)
public interface ClientModel<ClientType> {
	static final GenericType<List<com.saperion.sdb.rs.models.Folder>> FOLDER_CHILDREN =
			new GenericType<List<com.saperion.sdb.rs.models.Folder>>() {
			};
	static final GenericType<List<com.saperion.sdb.rs.models.Share>> SHARE_CHILDREN =
			new GenericType<List<com.saperion.sdb.rs.models.Share>>() {
			};
	static final GenericType<List<com.saperion.sdb.rs.models.Document>> DOCUMENT_CHILDREN =
			new GenericType<List<com.saperion.sdb.rs.models.Document>>() {
			};
	static final GenericType<List<ClientModel<?>>> ITEM_CHILDREN =
			new GenericType<List<ClientModel<?>>>() {
			};

	ClientType save() throws AuthenticationException, ConnectException;

	ClientModelType getType();

	String getResource();

	void setContext(Together together);

	enum ClientModelType {
		FOLDER(com.saperion.sdb.client.models.Folder.class, com.saperion.sdb.rs.models.Folder.class),
		SPACE(com.saperion.sdb.client.models.Space.class, com.saperion.sdb.rs.models.Space.class),
		SHARE(com.saperion.sdb.client.models.Share.class, com.saperion.sdb.rs.models.Share.class),
		DOCUMENT(com.saperion.sdb.client.models.Document.class,
				com.saperion.sdb.rs.models.Document.class),
		USER(com.saperion.sdb.client.models.User.class, com.saperion.sdb.rs.models.User.class);
		private Class<?> client;
		private Class<?> backend;

		private ClientModelType(Class<? extends ClientModel<?>> client,
				Class<? extends TypedIdentifiable> backend) {
			this.client = client;
			this.backend = backend;
		}
		
		public Class<?> getBackend() {
			return backend;
		}
		
		public Class<?> getClient() {
			return client;
		}
	}
}
