package com.saperion.sdb.rs.models;

import com.saperion.common.lang.format.ToStringFormatter;

/** Superclass of all classes that are identifiable by an ID. */
public abstract class Identifiable {

	protected String id;

	public Identifiable() {
	}

	public Identifiable setId(String id) {
		this.id = id;
		return this;
	}

	public String getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return ToStringFormatter.format(getClass(), null, "id", id);
	}
}
