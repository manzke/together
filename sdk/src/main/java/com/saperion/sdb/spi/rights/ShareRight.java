package com.saperion.sdb.spi.rights;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;

import javax.xml.bind.annotation.XmlEnum;

/**
 * The class ShareRight. Share rights are building on each other: WRITE includes READ, SHARE
 * includes WRITE (and READ).
 */
@XmlEnum
public enum ShareRight {

	/** The right to read the shared resource. */
	READ,
	/** The right to write AND read the shared resource. */
	WRITE,
	/** The right to share AND write AND read the shared resource. */
	SHARE;

	public static Collection<ShareRight> asCollection() {
		return Arrays.asList(ShareRight.values());
	}

	public static EnumSet<ShareRight> asEnumSet() {
		return EnumSet.copyOf(ShareRight.asCollection());
	}
}
