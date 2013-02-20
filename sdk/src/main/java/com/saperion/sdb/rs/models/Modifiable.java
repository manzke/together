package com.saperion.sdb.rs.models;

import com.saperion.common.lang.format.ToStringFormatter;

/** Superclass of all classes that are modifiable. */
public class Modifiable {

	protected String lastModified;

	public Modifiable() {
	}

	public String getLastModified() {
		return lastModified;
	}

	public Modifiable setLastModified(String lastModified) {
		this.lastModified = lastModified;
		return this;
	}

	@Override
	public String toString() {
		return ToStringFormatter.format(getClass(), null, "lastModified", lastModified);
	}
}
