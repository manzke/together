package com.saperion.sdb.rs.models;

import com.saperion.common.lang.format.ToStringFormatter;
import com.saperion.sdb.spi.rights.ShareRight;
import com.saperion.sdb.spi.states.SpaceState;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.EnumSet;

/** The class Space. */
@XmlRootElement
public class Space extends Item {

	private EnumSet<ShareRight> rights;
	private StateHolder<SpaceState> stateHolder;

	public Space() {
		super(ModelType.SPACE);
		rights = EnumSet.noneOf(ShareRight.class);
		this.stateHolder = new StateHolder<SpaceState>(EnumSet.noneOf(SpaceState.class));
	}

	public String toString() {
		return ToStringFormatter.format(getClass(), super.toString(), "rights", rights,
				"stateHolder", stateHolder);
	}

	public Space setRights(Collection<ShareRight> rights) {
		if (rights == null || rights.isEmpty()) {
			this.rights = EnumSet.noneOf(ShareRight.class);
		} else {
			this.rights = EnumSet.copyOf(rights);
		}
		return this;
	}

	public Space addRight(ShareRight right) {
		if (this.rights == null) {
			this.rights = EnumSet.of(right);
		} else {
			this.rights.add(right);
		}
		return this;
	}

	@XmlElementWrapper(name = "rights")
	@XmlElement(name = "right")
	public Collection<ShareRight> getRights() {
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
