package com.saperion.sdb.rs.models;


/**
 * The class SpacedItem.
 *
 * @author sts
 */
public class SpacedItem extends Item {

	private String spaceId;
	private String parentId;

	protected SpacedItem() {
	}

	public SpacedItem(ModelType type) {
		super(type);
	}

	public String getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(String spaceId) {
		this.spaceId = spaceId;
	}

	public String getParentId() {
		return parentId;
	}

	public Item setParentId(String parentId) {
		this.parentId = parentId;
		return this;
	}

	@Override
	public String toString() {
		return "SpacedItem{" + "spaceId='" + spaceId + '\'' + ", parentId='" + parentId + '\''
				+ "} " + super.toString();
	}
}
