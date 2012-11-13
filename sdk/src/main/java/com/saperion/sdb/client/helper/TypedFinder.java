package com.saperion.sdb.client.helper;

import com.saperion.sdb.client.Together;
import com.saperion.sdb.client.exceptions.AuthenticationException;
import com.saperion.sdb.client.exceptions.ConnectException;
import com.saperion.sdb.client.models.ClientModel;
import com.saperion.sdb.client.models.Document;
import com.saperion.sdb.client.models.Folder;
import com.saperion.sdb.client.models.Space;
import com.saperion.sdb.client.models.User;

public class TypedFinder<ClientType, Type extends ClientModel<ClientType>> {
	private Together together;
	private Class<Type> type;
	private String id;

	public TypedFinder(Together together, Class<Type> type) {
		this.together = together;
		this.type = type;
	}

	/**
	 * Search for a {@link UserManagementItem} by it's ID.
	 * 
	 * @param id
	 * 		the item's ID
	 * @return this TypedFinder instance
	 * @throws SearchException
	 * 		when items of this type cannot be searched for by name
	 */
	public TypedFinder<ClientType, Type> byId(String id) {
		this.id = id;
		return this;
	}

	public Type result() throws AuthenticationException, ConnectException {
		if(type.equals(Document.class)){
			com.saperion.sdb.rs.models.Document document = together.get("/documents/"+id, com.saperion.sdb.rs.models.Document.class);
			return type.cast(new Document(document, together));
		}
		else if(type.equals(Folder.class)){
			com.saperion.sdb.rs.models.Folder folder = together.get("/folders/"+id, com.saperion.sdb.rs.models.Folder.class);
			return type.cast(new Folder(folder, together));
		}
		else if(type.equals(Space.class)){
			com.saperion.sdb.rs.models.Space space = together.get("/spaces/"+id, com.saperion.sdb.rs.models.Space.class);
			return type.cast(new Space(space, together));
		}
		else if(type.equals(User.class)){
			com.saperion.sdb.rs.models.User user = together.get("/users/"+id, com.saperion.sdb.rs.models.User.class);
			return type.cast(new User(user, together));
		}
		throw new IllegalArgumentException("Type \""+type+"\" is unknown.");
	}
}
