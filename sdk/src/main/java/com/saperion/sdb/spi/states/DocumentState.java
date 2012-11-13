package com.saperion.sdb.spi.states;

import javax.xml.bind.annotation.XmlEnum;


/**
 * The class DocumentState.
 */
@XmlEnum
public enum DocumentState {
	CRYPTED, DELETED, LINKED, COMMENTED;
}
