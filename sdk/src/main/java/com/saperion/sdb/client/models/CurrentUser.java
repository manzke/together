package com.saperion.sdb.client.models;

import com.saperion.sdb.client.Together;

public class CurrentUser extends User {
	public CurrentUser(com.saperion.sdb.rs.models.User user, Together together) {
		super(user, together);
	}
	
	//delegates
	public String getId() {
		return "current";
	}

	@Override
	public String toString() {
		return delegate.toString();
	}
}
