package com.saperion.sdb.client.models;

import java.util.Collection;
import java.util.List;

import com.saperion.sdb.client.Together;
import com.saperion.sdb.client.exceptions.AuthenticationException;
import com.saperion.sdb.client.exceptions.ConnectException;
import com.saperion.sdb.spi.states.DocumentState;

public class Document extends OwnedItem<Document, com.saperion.sdb.rs.models.Document> {
	public Document(Together together) {
		this(new com.saperion.sdb.rs.models.Document(), together);
	}
	
	public Document(com.saperion.sdb.rs.models.Document document, Together together) {
		super(document, "/documents", together);
	}
	
	public void recycle() {
		delegate.addState(DocumentState.DELETED);
	}
	
	public void restore() {
		delegate.removeState(DocumentState.DELETED);
	}
	
	public StreamResult download() throws AuthenticationException, ConnectException{
		return together.get("/documents/"+getId()+"/file", "application/octet-stream", StreamResult.class);
	}
	
	public void upload(StreamResult stream) throws AuthenticationException, ConnectException{
		together.update("/documents/"+getId()+"/file", stream);
	}
	
	//TODO share, links, add(Comment), comments, activities, versions, download(Options), upload
	@Override
	public ClientModelType getType() {
		return ClientModelType.DOCUMENT;
	}
	
	//delegates
	//replace with getSpace
	public String getSpaceId() {
		return delegate.getSpaceId();
	}

	public String getLastModified() {
		return delegate.getLastModified();
	}
	//replace with getParent()
	public String getParentId() {
		return delegate.getParentId();
	}

	public Document setLastModified(String lastModified) {
		delegate.setLastModified(lastModified);
		return this;
	}

	public String getName() {
		return delegate.getName();
	}

	public Document setName(String name) {
		delegate.setName(name);
		return this;
	}

	public String getDescription() {
		return delegate.getDescription();
	}

	public Document setDescription(String description) {
		delegate.setDescription(description);
		return this;
	}

	public List<String> getTags() {
		return delegate.getTags();
	}

	public Document setTags(List<String> tags) {
		delegate.setTags(tags);
		return this;
	}

	public int getVersionNumber() {
		return delegate.getVersionNumber();
	}

	public Document addTag(String tag) {
		delegate.addTag(tag);
		return this;
	}

	public long getSize() {
		return delegate.getSize();
	}

	public String getMimeType() {
		return delegate.getMimeType();
	}

	public Document addState(DocumentState state) {
		delegate.addState(state);
		return this;
	}

	public Document removeState(DocumentState state) {
		delegate.removeState(state);
		return this;
	}

	public Collection<DocumentState> getStates() {
		return delegate.getStates();
	}

	public Document setStates(Collection<DocumentState> states) {
		delegate.setStates(states);
		return this;
	}
}
