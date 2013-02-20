package com.saperion.sdb.rs.models;

import java.util.Collection;
import java.util.EnumSet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.saperion.common.lang.format.ToStringFormatter;
import com.saperion.sdb.spi.rights.UserRight;
import com.saperion.sdb.spi.states.UserState;

@XmlRootElement
public class User extends TypedIdentifiable {
	private String displayname;
	private String firstname;
	private String lastname;
	private String email;
	private String company;
	private EnumSet<UserRight> rights;
	private StateHolder<UserState> stateHolder;

	public User() {
		super(ModelType.USER);
		rights = EnumSet.noneOf(UserRight.class);
		this.stateHolder = new StateHolder<UserState>();
	}

	public String getDisplayname() {
		return displayname;
	}

	public User setDisplayname(String displayName) {
		this.displayname = displayName;
		return this;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getCompany() {
		return company;
	}

	public User setCompany(String company) {
		this.company = company;
		return this;
	}

	public User setRights(Collection<UserRight> userRights) {
		if (userRights == null || userRights.isEmpty()) {
			this.rights = EnumSet.noneOf(UserRight.class);
		} else {
			this.rights = EnumSet.copyOf(userRights);
		}
		return this;
	}

	public User addRight(UserRight right) {
		rights.add(right);
		return this;
	}

	public User removeRight(UserRight right) {
		rights.remove(right);
		if (rights == null)
			rights = EnumSet.noneOf(UserRight.class);
		return this;
	}

	@XmlElementWrapper(name = "rights")
	@XmlElement(name = "right")
	public Collection<UserRight> getRights() {
		return this.rights;
	}

	public User addState(UserState state) {
		this.stateHolder.addState(state);
		return this;
	}

	public User removeState(UserState state) {
		this.stateHolder.removeState(state);
		return this;
	}

	@XmlElementWrapper(name = "states")
	@XmlElement(name = "state")
	public Collection<UserState> getStates() {
		return this.stateHolder.getStates();
	}

	public User setStates(Collection<UserState> states) {
		this.stateHolder.setStates(states);
		return this;
	}

	@Override
	public String toString() {
		return ToStringFormatter.format(getClass(), super.toString(), "displayname", displayname,
				"firstname", firstname, "lastname", lastname, "email", email, "company", company,
				"rights", rights, "states", stateHolder);
	}
}
