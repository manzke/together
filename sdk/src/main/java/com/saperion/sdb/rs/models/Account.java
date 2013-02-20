package com.saperion.sdb.rs.models;

import com.saperion.common.lang.format.ToStringFormatter;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Account extends Typed {

	private String name;
	private int userLimit;
	private int usedUsers;
	private int guestLimit;
	private int usedGuest;
	private long storageLimit;
	private long uploadLimit;
	private long usedStorage;

	public Account() {
		super(ModelType.ACCOUNT);
	}

	public String getName() {
		return name;
	}

	public Account setName(String name) {
		this.name = name;
		return this;
	}

	public int getGuestLimit() {
		return guestLimit;
	}

	public void setGuestLimit(int guestLimit) {
		this.guestLimit = guestLimit;
	}

	public int getUsedGuest() {
		return usedGuest;
	}

	public void setUsedGuest(int usedGuest) {
		this.usedGuest = usedGuest;
	}

	/** @return the userLimit */
	public int getUserLimit() {
		return userLimit;
	}

	/** @return the usedUsers */
	public int getUsedUsers() {
		return usedUsers;
	}

	/** @return the storageLimit */
	public long getStorageLimit() {
		return storageLimit;
	}

	/** @return the usedStorage */
	public long getUsedStorage() {
		return usedStorage;
	}

	/** @param userLimit the userLimit to set */
	public final void setUserLimit(int userLimit) {
		this.userLimit = userLimit;
	}

	/** @param usedUsers the usedUsers to set */
	public final void setUsedUsers(int usedUsers) {
		this.usedUsers = usedUsers;
	}

	/** @param storageLimit the storageLimit to set */
	public final void setStorageLimit(long storageLimit) {
		this.storageLimit = storageLimit;
	}

	/** @param usedStorage the usedStorage to set */
	public final void setUsedStorage(long usedStorage) {
		this.usedStorage = usedStorage;
	}

	public long getUploadLimit() {
		return uploadLimit;
	}

	public void setUploadLimit(long uploadLimit) {
		this.uploadLimit = uploadLimit;
	}

	@Override
	public String toString() {
		return ToStringFormatter.format(getClass(), super.toString(), "name", name, "userLimit",
				userLimit, "usedUsers", usedUsers, "guestLimit", guestLimit, "usedGuest",
				usedGuest, "storageLimit", storageLimit, "uploadLimit", uploadLimit, "usedStorage",
				usedStorage);
	}
}
