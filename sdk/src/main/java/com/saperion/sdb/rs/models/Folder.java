package com.saperion.sdb.rs.models;

import com.saperion.common.lang.format.ToStringFormatter;
import com.saperion.sdb.spi.states.FolderState;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

/** The class Folder. */
@XmlRootElement
public class Folder extends SpacedItem {

	private StateHolder<FolderState> stateHolder;

	public Folder() {
		super(ModelType.FOLDER);
		this.stateHolder = new StateHolder<FolderState>();
	}

	@Override
	public String toString() {
		return ToStringFormatter.format(getClass(), super.toString(), "stateHolder", stateHolder);
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
