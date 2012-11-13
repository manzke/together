package com.saperion.sdb.client.models;

import com.saperion.common.lang.string.Strings;
import com.saperion.common.lang.unsafe.Unsafe;
import com.saperion.sdb.client.Together;
import com.saperion.sdb.client.exceptions.AuthenticationException;
import com.saperion.sdb.client.exceptions.ConnectException;

public abstract class Item<ClientType, BackendType extends com.saperion.sdb.rs.models.TypedIdentifiable> implements ClientModel<ClientType> {
	protected BackendType delegate;
	protected Class<BackendType> delegatesClass;
	protected Together together;
	protected String resource;
	
	public Item(BackendType delegate, String resource, Together together){
		this.delegate = delegate;
		this.together = together;
		this.resource = resource;
		this.delegatesClass = Unsafe.cast(delegate.getClass());
	}
	
	public ClientType save() throws AuthenticationException, ConnectException {
		if(Strings.isBlank(getId())){
			delegate = together.create(resource, delegate, delegatesClass);
		}else{
			delegate = together.update(resource+"/"+getId(), delegate, delegatesClass);
		}

		return Unsafe.<ClientType>cast(this);
	}
	
	public ClientType reload() throws AuthenticationException, ConnectException {
		if(!Strings.isBlank(getId())){
			delegate = together.get(resource+"/"+getId(), delegatesClass);
			return Unsafe.<ClientType>cast(this);
		}
		throw new IllegalStateException("Object doesn't have an ID, so it can't be reloaded.");
	}
	
	public void delete() throws AuthenticationException, ConnectException {
		together.delete(resource+"/"+getId());
	}
	
	public String getId() {
		return delegate.getId();
	}

	@Override
	public String getResource() {
		return resource;
	}
	
	@Override
	public void setContext(Together together) {
		this.together = together;
	}
	
	@Override
	public String toString() {
		return delegate.toString();
	}
}
