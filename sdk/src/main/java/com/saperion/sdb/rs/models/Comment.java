package com.saperion.sdb.rs.models;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * The Comment model.
 *
 * @author sts
 */
@XmlRootElement
public class Comment extends CreatedTypedIdentifiable {

	private String comment;

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

	@Override
	public String toString() {
		return "Comment{" + "comment='" + comment + '\'' + "} " + super.toString();
	}
}
