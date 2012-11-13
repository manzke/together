package com.saperion.sdb.rs.models;


/**
 * Superclass of all classes that are modifiable and that have a certain type.
 */
public abstract class Typed {
	private Type type;

	public Typed() {
		this(ModelType.UNKNOWN);
	}

	public Typed(ModelType type) {
		this.type = new Type(type);
	}

	public Typed setType(String type) {
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
		return "Typed{" + "type=" + type + '}';
	}
}
