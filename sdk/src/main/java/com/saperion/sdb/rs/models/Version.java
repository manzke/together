package com.saperion.sdb.rs.models;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * The class Version.
 *
 * @author sts
 */
@XmlRootElement
public class Version extends CreatedTypedIdentifiable {

	private int number;
	private String comment;

	public Version() {
		super(ModelType.VERSION);
	}

	public Version setNumber(int number) {
		this.number = number;
		return this;
	}

	public int getNumber() {
		return this.number;
	}

	public Version setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public String getComment() {
		return this.comment;
	}

	@Override
	public String toString() {
		return "Version{" + "number=" + number + ", comment='" + comment + '\'' + "} "
				+ super.toString();
	}
}
