package com.saperion.sdb.rs.models;

import java.util.Collection;
import java.util.EnumSet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.saperion.sdb.spi.rights.SpaceRight;
import com.saperion.sdb.spi.states.SpaceState;

/** The class Space. */
@XmlRootElement
public class Space extends Item {

	private EnumSet<SpaceRight> rights;
	private StateHolder<SpaceState> stateHolder;

	public Space() {
		super(ModelType.SPACE);
		rights = EnumSet.noneOf(SpaceRight.class);
		this.stateHolder = new StateHolder<SpaceState>(EnumSet.noneOf(SpaceState.class));
	}

	public Space setRights(Collection<SpaceRight> rights) {
		if (rights == null || rights.isEmpty()) {
			this.rights = EnumSet.noneOf(SpaceRight.class);
		} else {
			this.rights = EnumSet.copyOf(rights);
		}
		return this;
	}

	public Space addRight(SpaceRight right) {
		if (this.rights == null) {
			this.rights = EnumSet.of(right);
		} else {
			this.rights.add(right);
		}
		return this;
	}

	@XmlElementWrapper(name = "rights")
	@XmlElement(name = "right")
	public Collection<SpaceRight> getRights() {
		return this.rights;
	}

	public Space addState(SpaceState state) {
		this.stateHolder.addState(state);
		return this;
	}

	public Space removeState(SpaceState state) {
		this.stateHolder.removeState(state);
		return this;
	}

	@XmlElementWrapper(name = "states")
	@XmlElement(name = "state")
	public Collection<SpaceState> getStates() {
		return this.stateHolder.getStates();
	}

	public Item setStates(Collection<SpaceState> states) {
		this.stateHolder.setStates(states);
		return this;
	}

}
