package com.saperion.sdb.client.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.saperion.sdb.client.Together;
import com.saperion.sdb.client.exceptions.AuthenticationException;
import com.saperion.sdb.client.exceptions.ConnectException;
import com.saperion.sdb.spi.rights.SpaceRight;
import com.saperion.sdb.spi.states.SpaceState;

public class Space extends ParentItem<Space, com.saperion.sdb.rs.models.Space> {
	public Space(Together together) {
		this(new com.saperion.sdb.rs.models.Space(), together);
	}

	public Space(com.saperion.sdb.rs.models.Space space, Together together) {
		super(space, "/spaces", together);
	}

	public void recycle() {
		delegate.addState(SpaceState.DELETED);
	}
	
	public void restore() {
		delegate.removeState(SpaceState.DELETED);
	}
	
	public List<Share> shares() throws AuthenticationException, ConnectException {
		List<com.saperion.sdb.rs.models.Share> shares = children(com.saperion.sdb.rs.models.Share.class, SHARE_CHILDREN);
		List<Share> converted = new ArrayList<Share>();
		for(com.saperion.sdb.rs.models.Share share : shares){
			converted.add(new Share(share, this, together));
		}
		return converted;
	}
		
	@Override
	public ClientModelType getType() {
		return ClientModelType.SPACE;
	}
	
	//delegates
	public String getLastModified() {
		return delegate.getLastModified();
	}

	public String getName() {
		return delegate.getName();
	}

	public Space setName(String name) {
		delegate.setName(name);
		return this;
	}

	public String getDescription() {
		return delegate.getDescription();
	}

	public Space setDescription(String description) {
		delegate.setDescription(description);
		return this;
	}

	public Space setRights(Collection<SpaceRight> rights) {
		delegate.setRights(rights);
		return this;
	}

	public List<String> getTags() {
		return delegate.getTags();
	}

	public Space setTags(List<String> tags) {
		delegate.setTags(tags);
		return this;
	}

	public Space addRight(SpaceRight right) {
		delegate.addRight(right);
		return this;
	}

	public Space addTag(String tag) {
		delegate.addTag(tag);
		return this;
	}

	public Collection<SpaceRight> getRights() {
		return delegate.getRights();
	}

	public Space addState(SpaceState state) {
		delegate.addState(state);
		return this;
	}

	public Space removeState(SpaceState state) {
		delegate.removeState(state);
		return this;
	}

	public Collection<SpaceState> getStates() {
		return delegate.getStates();
	}

	public Space setStates(Collection<SpaceState> states) {
		delegate.setStates(states);
		return this;
	}
	
	@Override
	public String toString() {
		//FIXME just a quick fix for filtering in the ui
		return delegate.getName();
	}
}
