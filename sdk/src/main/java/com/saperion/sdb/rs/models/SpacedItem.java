package com.saperion.sdb.rs.models;

import com.saperion.common.lang.format.ToStringFormatter;

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
		return ToStringFormatter.format(getClass(), super.toString(), "spaceId", spaceId,
				"parentId", parentId);
	}
}
