package com.saperion.sdb.rs.models;


/**
 * Superclass of all classes that are modifiable and that have a certain type.
 */
public abstract class TypedModifiable extends Modifiable {
	private Type type;

	public TypedModifiable() {
		this(ModelType.UNKNOWN);
	}

	public TypedModifiable(ModelType type) {
		this.type = new Type(type);
	}

	public TypedModifiable setType(String type) {
		Type t = Type.fromName(type);
		if (null != t) {
			this.type = t;
		}
		return this;
	}

	public String getType() {
		return type.getType();
	}

	@Override
	public String toString() {
		return "TypedModifiable{" + "type=" + type + "} " + super.toString();
	}
}
