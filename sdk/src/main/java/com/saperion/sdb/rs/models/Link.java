package com.saperion.sdb.rs.models;

import com.saperion.common.lang.format.ToStringFormatter;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The class Link.
 *
 * @author sts
 */
@XmlRootElement
public class Link extends CreatedTypedIdentifiable {

	private String password;
	private String expirationDate;
	private String url;
	private String name;
	protected String documentId;
	private int version;
	private int accessLimit;
	private int currentAccessCount;

	public Link() {
		super(ModelType.LINK);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public Link setUrl(String url) {
		this.url = url;
		return this;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public Link setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public Link setPassword(String password) {
		this.password = password;
		return this;
	}

	public int getAccessLimit() {
		return accessLimit;
	}

	public Link setAccessLimit(int accessLimit) {
		this.accessLimit = accessLimit;
		return this;
	}

	public int getCurrentAccessCount() {
		return currentAccessCount;
	}

	public Link setCurrentAccessCount(int currentAccessCount) {
		this.currentAccessCount = currentAccessCount;
		return this;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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
		return ToStringFormatter.format(getClass(), super.toString(), "password", password,
				"expirationDate", expirationDate, "url", url, "name", name, "version", version,
				"accessLimit", accessLimit, "currentAccessCount", currentAccessCount, "documentId",
				documentId);
	}
}
