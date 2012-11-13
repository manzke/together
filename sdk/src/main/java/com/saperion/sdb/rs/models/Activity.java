package com.saperion.sdb.rs.models;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * The Activity model.
 *
 * @author sts
 */
@XmlRootElement
public class Activity extends CreatedTypedIdentifiable {

	private String activity;
	private String type;

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

	@Override
	public String toString() {
		return "Activity{" + "activity='" + activity + '\'' + "} " + super.toString();
	}
}
