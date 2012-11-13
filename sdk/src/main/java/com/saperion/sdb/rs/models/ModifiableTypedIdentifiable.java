package com.saperion.sdb.rs.models;


public abstract class ModifiableTypedIdentifiable extends CreatedTypedIdentifiable {

	private Modifiable modification = new Modifiable();

	public ModifiableTypedIdentifiable() {
		super(ModelType.UNKNOWN);
	}

	public ModifiableTypedIdentifiable(ModelType type) {
		super(type);
	}

	public String getLastModified() {
		return modification.getLastModified();
	}

	public ModifiableTypedIdentifiable setLastModified(String lastModified) {
		this.modification.setLastModified(lastModified);
		return this;
	}

	@Override
	public String toString() {
		return "ModifiableTypedIdentifiable{" + "modification=" + modification + "} "
				+ super.toString();
	}
}
