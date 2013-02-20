package com.saperion.sdb.spi.states;

import javax.xml.bind.annotation.XmlEnum;

/**
 * The class SpaceState.
 */
@XmlEnum
public enum SpaceState {
	CRYPTED,
	SYNCHRONIZE_WEB,
	SYNCHRONIZE_APP,
	SYNCHRONIZE_DESKTOP,
	SHARED,
	RECYCLED,
	FAVORED;
}
