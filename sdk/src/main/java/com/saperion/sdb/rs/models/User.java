package com.saperion.sdb.rs.models;

import java.util.Collection;
import java.util.EnumSet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.saperion.sdb.spi.rights.UserRight;

@XmlRootElement
public class User extends TypedIdentifiable {
	private String name;
	private String email;
	private String company;
	private EnumSet<UserRight> rights;
	private boolean guest;

	private boolean inRecycleBin;

	public User() {
		super(ModelType.USER);
		rights = EnumSet.noneOf(UserRight.class);
	}

	public String getName() {
		return name;
	}

	public User setName(String name) {
		this.name = name;
		return this;
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

	public boolean isGuest() {
		return guest;
	}

	public void setGuest(boolean guest) {
		this.guest = guest;
	}

	public boolean isInRecycleBin() {
		return inRecycleBin;
	}

	public void setInRecycleBin(boolean inRecycleBin) {
		this.inRecycleBin = inRecycleBin;
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

	@Override
	public String toString() {
		return "User{" + "name='" + name + '\'' + ", email='" + email + '\'' + ", company='"
				+ company + '\'' + ", rights=" + rights + ", guest=" + guest + "} "
				+ super.toString();
	}
}
