package com.saperion.sdb.rs.models;

import com.saperion.common.lang.format.ToStringFormatter;

public abstract class CreatedTypedIdentifiable extends TypedIdentifiable {

	private String ownerId;
	private String ownerName;
	private String creationDate;

	public CreatedTypedIdentifiable() {
		super(ModelType.UNKNOWN);
	}

	public CreatedTypedIdentifiable(ModelType type) {
		super(type);
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return ToStringFormatter.format(getClass(), super.toString(), "ownerId", ownerId,
				"ownerName", ownerName, "creationDate", creationDate);
	}
}
