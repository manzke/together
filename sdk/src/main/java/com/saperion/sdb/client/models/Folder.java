package com.saperion.sdb.client.models;

import java.util.Collection;
import java.util.List;

import com.saperion.sdb.client.Together;
import com.saperion.sdb.spi.states.FolderState;


public class Folder extends ParentItem<Folder, com.saperion.sdb.rs.models.Folder> {
	public Folder(Together together) {
		this(new com.saperion.sdb.rs.models.Folder(), together);
	}
	
	public Folder(com.saperion.sdb.rs.models.Folder folder, Together together) {
		super(folder, "/folders", together);
	}
	
	public void recycle() {
		delegate.addState(FolderState.DELETED);
	}
	
	public void restore() {
		delegate.removeState(FolderState.DELETED);
	}
	
	@Override
	public ClientModelType getType() {
		return ClientModelType.FOLDER;
	}

	// delegate
	public String getSpaceId() {
		return delegate.getSpaceId();
	}

	public String getLastModified() {
		return delegate.getLastModified();
	}

	public String getId() {
		return delegate.getId();
	}

	public String getParentId() {
		return delegate.getParentId();
	}

	public String getName() {
		return delegate.getName();
	}

	public Folder setName(String name) {
		delegate.setName(name);
		return this;
	}

	public Folder addState(FolderState state) {
		delegate.addState(state);
		return this;
	}

	public String getDescription() {
		return delegate.getDescription();
	}

	public Folder removeState(FolderState state) {
		delegate.removeState(state);
		return this;
	}

	public Folder setDescription(String description) {
		delegate.setDescription(description);
		return this;
	}

	public Collection<FolderState> getStates() {
		return delegate.getStates();
	}

	public List<String> getTags() {
		return delegate.getTags();
	}

	public Folder setTags(List<String> tags) {
		delegate.setTags(tags);
		return this;
	}

	public Folder setStates(Collection<FolderState> states) {
		delegate.setStates(states);
		return this;
	}

	public Folder addTag(String tag) {
		delegate.addTag(tag);
		return this;
	}
}
