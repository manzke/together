package com.saperion.sdb.rs.models;

import com.saperion.common.lang.format.ToStringFormatter;

/** Superclass of all classes that are identifiable by an ID and that have a certain type. */
public abstract class TypedIdentifiable extends Identifiable {
	private Type type;

	public TypedIdentifiable() {
		this.type = new Type(ModelType.UNKNOWN);
	}

	public TypedIdentifiable(ModelType type) {
		this.type = new Type(type);
	}

	public String getType() {
		return type.getType();
	}

	public TypedIdentifiable setType(String type) {
		Type t = Type.fromName(type);
		if (null != t) {
			this.type = t;
		}
		return this;
	}

	@Override
	public String toString() {
		return ToStringFormatter.format(getClass(), super.toString(), "type", type);
	}
}
