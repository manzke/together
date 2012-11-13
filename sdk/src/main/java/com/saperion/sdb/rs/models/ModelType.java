package com.saperion.sdb.rs.models;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;


/**
 * The class ModelType.
 *
 * @author sts
 */
public enum ModelType {
	ACTIVITY,
	DOCUMENT,
	FOLDER,
	SPACE,
	SHARE,
	LINK,
	ATTACHMENT,
	VERSION,
	COMMENT,
	USER,
	ACCOUNT,
	SYSTEM,
	UNKNOWN;

	public static Collection<ModelType> asCollection() {
		return Arrays.asList(ModelType.values());
	}

	public static EnumSet<ModelType> asEnumSet() {
		return EnumSet.copyOf(ModelType.asCollection());
	}
}
