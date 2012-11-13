package com.saperion.sdb.spi.states;

import javax.xml.bind.annotation.XmlEnum;

/**
 * The class SpaceState.
 */
@XmlEnum
public enum SpaceState {
	CRYPTED, SYNCHRONIZE, SHARED, DELETED;
}
