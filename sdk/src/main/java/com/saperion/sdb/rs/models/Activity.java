package com.saperion.sdb.rs.models;

import com.saperion.common.lang.format.ToStringFormatter;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Activity model.
 *
 * @author sts
 */
@XmlRootElement
public class Activity extends CreatedTyped {

	private String activity;
	private String type;
	protected String documentId;

	public Activity() {
		super(ModelType.ACTIVITY);
	}

	public String getActivityType() {
		return type;
	}

	public void setActivityType(String type) {
		this.type = type;
	}

	/**
	 * Returns the activity of this activity.
	 *
	 * @return the activity of this activity.
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * Sets the activity of this activity.
	 *
	 * @param activity The activity to be set.
	 *
	 * @return an instance of this Activity.
	 */
	public Activity setActivity(String activity) {
		this.activity = activity;
		return this;
	}

	/**
	 * Returns the id of the document this activity was set on.
	 * @return the document id.
	 */
	public String getDocumentId() {
		return documentId;
	}

	/**
	 * Sets the id of the document this activity relates to.
	 * @param documentId the document id.
	 */
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	@Override
	public String toString() {
		return ToStringFormatter.format(getClass(), super.toString(), "activity", activity,
				"documentId", documentId);
	}
}
