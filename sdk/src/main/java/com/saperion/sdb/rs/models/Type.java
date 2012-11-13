package com.saperion.sdb.rs.models;

import java.util.Locale;


/**
 * The class Type.
 * 
 * @author sts
 */
public class Type {
	private ModelType modelType;

	protected Type() {
		modelType = ModelType.UNKNOWN;
	}

	public Type(ModelType type) {
		this.modelType = type;
	}

	public String getType() {
		return modelType.name().toLowerCase();
	}

	public static Type fromName(String name) {
		ModelType type = ModelType.valueOf(name.toUpperCase(Locale.US));
		if (null != type) {
			return new Type(type);
		}
		return null;
	}

	@Override
	public String toString() {
		return "Type{" + "modelType=" + modelType + '}';
	}
}
