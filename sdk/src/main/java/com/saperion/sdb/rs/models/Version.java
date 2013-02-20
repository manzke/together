package com.saperion.sdb.rs.models;

import com.saperion.common.lang.format.ToStringFormatter;

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
	protected String documentId;

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

	/**
	 * Returns the id of the document this link was created for.
	 * @return the document id.
	 */
	public String getDocumentId() {
		return documentId;
	}

	/**
	 * Sets the id of the document this link relates to.
	 * @param documentId the document id.
	 */
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	@Override
	public String toString() {
		return ToStringFormatter.format(getClass(), super.toString(), "number", number, "comment",
				comment, "documentId", documentId);
	}
}
