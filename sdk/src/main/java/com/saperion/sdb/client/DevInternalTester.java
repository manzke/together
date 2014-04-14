package com.saperion.sdb.client;

import java.util.List;

import com.saperion.sdb.client.Together.Config;
import com.saperion.sdb.client.Together.Protocol;
import com.saperion.sdb.client.models.CurrentUser;
import com.saperion.sdb.client.models.Document;
import com.saperion.sdb.client.models.Folder;
import com.saperion.sdb.client.models.Share;
import com.saperion.sdb.client.models.Space;
import com.saperion.sdb.client.models.StreamResult;


public class DevInternalTester {
	private static final String TOGETHER_CONFIG_SERVICE = "together://config?service?";
	private static final String HTTP_PROTOCOL = "http://";
	private static final String HTTPS_PROTOCOL = "https://";
	public static final String USE_SSL_KEY = "use.ssl";
	public static final String HOST_KEY = "host";
	public static final String PORT_KEY = "port";
	public static final String RESOURCE_KEY = "resource";
	public static final String PASSWORD_KEY = "password";
	public static final String USERNAME_KEY = "username";
	
	public static void main(String[] args) {
		String uri = "together://config?service?http://213.61.60.88:8600/sdb-service-rest?daniel.manzke@saperion.com";
		// together://config?service?http://213.61.60.88:8600/sdb-service-rest?daniel.manzke@saperion.com
		if (uri.contains(TOGETHER_CONFIG_SERVICE)) {
			String stripped = uri.substring(TOGETHER_CONFIG_SERVICE
					.length());
			if (stripped.contains(HTTP_PROTOCOL)) {
				stripped = stripped.substring(HTTP_PROTOCOL.length());
			} else if (stripped.contains(HTTPS_PROTOCOL)) {
				stripped = stripped.substring(HTTPS_PROTOCOL.length());
			}
			
			int index = stripped.indexOf("/");
			if(index > 0){
				String hostAndPort = stripped.substring(0, index);
				int dotIndex = hostAndPort.indexOf(":");
				String host = hostAndPort;
				String port = "80";
				if(dotIndex > 0){
					host = hostAndPort.substring(0, dotIndex);
					port = hostAndPort.substring(dotIndex+1, hostAndPort.length());
				}
				stripped = stripped.substring(index);
				int questionMarkIndex = stripped.indexOf("?");
				if(questionMarkIndex > 0){
					String resource = stripped.substring(1, questionMarkIndex);
					String user = stripped.substring(questionMarkIndex+1);
				}
			}else{
				//we have a problem
				System.out.println("root");
			}
			 
		}
	}
	
	public static void main2(String[] args) throws Throwable {
		Config config = new Together.Config().protocol(Protocol.HTTP).host("213.61.60.88").port(8600);
		Together together = config.build();
		try{
			together.login("daniel.manzke@swisscom.com", "password");
			CurrentUser me = together.me();
			System.out.println("My User: "+me);
			List<Space> mySpaces = together.mySpaces();
			for(Space space : mySpaces){
				System.out.println("Space: "+space);
				System.out.println("Space-Owner: "+space.getOwner());
				List<Share> shares = space.shares();
				for(Share share : shares){
					System.out.println("Share: "+share);
					System.out.println("Share-Owner: "+share.getOwner());
					System.out.println("Share-Receiver: "+share.getReceiver());

				}
				List<Folder> subfolders = space.folders();
				for(Folder child : subfolders){
					System.out.println("Folder: "+child);
					System.out.println("Folder-Owner: "+child.getOwner());
				}
				List<Document> documents = space.documents();
				for(Document child : documents){
					System.out.println("Document: "+child);
					System.out.println("Document-Owner: "+child.getOwner());
				}
			}

			Document document = together.find(Document.class).byId("BB2148B2CC492F46AD651383AF90E754000000000000X268470567").result();
			StreamResult download = document.download();
			System.out.println("Document-Content: "+download);
			download.saveAs(document.getName());
			
//			
//			Folder folder = together.find(Folder.class).byId("F7BF5C26EAFFC94BB26D0007BEF1AD7C000000000000X268470655").result();
//			System.out.println("Folder: "+folder);
//			List<ClientModel<?>> children = folder.children();
//			for(ClientModel<?> child : children){
//				System.out.println(child);
//			}
//			
//			Space space = together.find(Space.class).byId("52CA3A9D4BC18B4DAC9C98519512F813000000000000X268470655").result();
//			System.out.println("Space: "+space);
//			children = space.children();
//			for(ClientModel<?> child : children){
//				System.out.println(child);
//			}
			
			System.out.println(me);
			me.avatar().saveAs("myavatar.png");
		}catch(Throwable t){
			throw t;
		} finally {
			together.close();
		}
	}
}
