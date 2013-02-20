package com.saperion.sdb.rs.models;

import com.saperion.common.lang.format.ToStringFormatter;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Comment model.
 *
 * @author sts
 */
@XmlRootElement
public class Comment extends CreatedTypedIdentifiable {

	private String comment;
	protected String documentId;

	public Comment() {
		super(ModelType.COMMENT);
	}

	/**
	 * Returns the comment of this model.
	 *
	 * @return the comment of this model.
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets the comment.
	 *
	 * @param comment The comment to be set.
	 *
	 * @return an instance of this model.
	 */
	public Comment setComment(String comment) {
		this.comment = comment;
		return this;
	}

	/**
	 * Returns the id of the document this comment was set on.
	 * @return the document id.
	 */
	public String getDocumentId() {
		return documentId;
	}

	/**
	 * Sets the id of the document this comment relates to.
	 * @param documentId the document id.
	 */
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	@Override
	public String toString() {
		return ToStringFormatter.format(getClass(), super.toString(), "comment", comment,
				"documentId", documentId);
	}
}
