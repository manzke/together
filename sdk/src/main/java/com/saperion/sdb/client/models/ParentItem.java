package com.saperion.sdb.client.models;

import java.util.ArrayList;
import java.util.List;

import com.saperion.sdb.client.Together;
import com.saperion.sdb.client.exceptions.AuthenticationException;
import com.saperion.sdb.client.exceptions.ConnectException;
import com.sun.jersey.api.client.GenericType;

public abstract class ParentItem<ClientType, BackendType extends com.saperion.sdb.rs.models.CreatedTypedIdentifiable>
		extends OwnedItem<ClientType, BackendType> {

	public ParentItem(BackendType delegate, String resource, Together together) {
		super(delegate, resource, together);
	}
	
	//TODO share, catch throwable

	protected <T> List<T> children(Class<T> clazz, GenericType<List<T>> type)
			throws AuthenticationException, ConnectException {
		String endpoint;
		if (clazz.equals(com.saperion.sdb.rs.models.Document.class)) {
			endpoint = "/documents";
		} else if (clazz.equals(com.saperion.sdb.rs.models.Folder.class)) {
			endpoint = "/subfolders";
		} else if (clazz.equals(com.saperion.sdb.rs.models.Share.class)) {
			endpoint = "/shares";
		} else {
			throw new IllegalArgumentException("Type \"" + clazz.getName()
					+ "\" is unknown for retrieving childs.");
		}
		return together.get(resource + "/" + getId() + endpoint, type);
	}

	public List<Document> documents() throws AuthenticationException, ConnectException {
		List<com.saperion.sdb.rs.models.Document> documents =
				children(com.saperion.sdb.rs.models.Document.class, DOCUMENT_CHILDREN);
		List<Document> converted = new ArrayList<Document>();
		for (com.saperion.sdb.rs.models.Document document : documents) {
			converted.add(new Document(document, together));
		}
		return converted;
	}

	public List<Folder> folders() throws AuthenticationException, ConnectException {
		List<com.saperion.sdb.rs.models.Folder> children =
				children(com.saperion.sdb.rs.models.Folder.class, FOLDER_CHILDREN);
		List<Folder> converted = new ArrayList<Folder>();
		for (com.saperion.sdb.rs.models.Folder folder : children) {
			converted.add(new Folder(folder, together));
		}
		return converted;
	}

	public List<ClientModel<?>> children() throws AuthenticationException, ConnectException {
		List<ClientModel<?>> children = together.get(resource + "/" + getId() + "/children", ClientModel.ITEM_CHILDREN);
		for (ClientModel<?> child : children) {
			child.setContext(together);
		}
		return children;
	}
}
