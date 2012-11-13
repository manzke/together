package com.saperion.sdb.rs.models;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.saperion.sdb.spi.states.FolderState;

/** The class Folder. */
@XmlRootElement
public class Folder extends SpacedItem {

	private StateHolder<FolderState> stateHolder;

	public Folder() {
		super(ModelType.FOLDER);
		this.stateHolder = new StateHolder<FolderState>();
	}

	public Folder addState(FolderState state) {
		this.stateHolder.addState(state);
		return this;
	}

	public Folder removeState(FolderState state) {
		this.stateHolder.removeState(state);
		return this;
	}

	@XmlElementWrapper(name = "states")
	@XmlElement(name = "state")
	public Collection<FolderState> getStates() {
		return this.stateHolder.getStates();
	}

	public Item setStates(Collection<FolderState> states) {
		this.stateHolder.setStates(states);
		return this;
	}
}
