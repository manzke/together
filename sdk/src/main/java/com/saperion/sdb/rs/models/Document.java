package com.saperion.sdb.rs.models;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.saperion.sdb.spi.states.DocumentState;

@XmlRootElement
public class Document extends SpacedItem {

	private int versionNumber;
	private long pages;
	private long size;
	private String mimeType;
	private String creationDate;

	private StateHolder<DocumentState> stateHolder;

	public Document() {
		super(ModelType.DOCUMENT);
		this.stateHolder = new StateHolder<DocumentState>();
	}

	public long getPages() {
		return pages;
	}

	public Document setPages(long pages) {
		this.pages = pages;
		return this;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public int getVersionNumber() {
		return versionNumber;
	}

	public Document setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
		return this;
	}

	/**
	 * Returns the size of this documents attachment (binary content) in bytes.
	 *
	 * @return the size of this documents attachment in bytes.
	 */
	public long getSize() {
		return size;
	}

	/**
	 * Sets the size of this documents attachment in bytes.
	 *
	 * @param size in bytes.
	 */
	public Document setSize(long size) {
		this.size = size;
		return this;
	}

	public String getMimeType() {
		return mimeType;
	}

	public Document setMimeType(String mimeType) {
		this.mimeType = mimeType;
		return this;
	}

	public Document addState(DocumentState state) {
		this.stateHolder.addState(state);
		return this;
	}

	public Document removeState(DocumentState state) {
		this.stateHolder.removeState(state);
		return this;
	}

	@XmlElementWrapper(name = "states")
	@XmlElement(name = "state")
	public Collection<DocumentState> getStates() {
		return this.stateHolder.getStates();
	}

	public Item setStates(Collection<DocumentState> states) {
		this.stateHolder.setStates(states);
		return this;
	}
}
