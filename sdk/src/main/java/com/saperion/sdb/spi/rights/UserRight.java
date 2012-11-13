package com.saperion.sdb.spi.rights;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;

import javax.xml.bind.annotation.XmlEnum;

/**
 * The class UserRight.
*/
@XmlEnum
public enum UserRight {
	INVITE, SHARE, MANAGE;

	public static Collection<UserRight> asCollection() {
		return Arrays.asList(UserRight.values());
	}

	public static EnumSet<UserRight> asEnumSet() {
		return EnumSet.copyOf(UserRight.asCollection());
	}
}
