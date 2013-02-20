package com.saperion.sdb.rs.models;

import com.saperion.common.lang.format.ToStringFormatter;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

/** The class Item. */
@JsonSerialize(include = Inclusion.NON_NULL)
public abstract class Item extends ModifiableTypedIdentifiable {
	private String name;
	private String description;
	private String modificationId;
	private List<String> tags;

	protected Item() {

	}

	public Item(ModelType type) {
		super(type);
	}

	public String getName() {
		return name;
	}

	public Item setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Item setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getModificationId() {
		return modificationId;
	}

	public void setModificationId(String modificationId) {
		this.modificationId = modificationId;
	}

	@XmlElementWrapper(name = "tags")
	@XmlElement(name = "tag")
	public List<String> getTags() {
		return tags;
	}

	public Item setTags(List<String> tags) {
		this.tags = tags;
		return this;
	}

	public Item addTag(String tag) {
		if (null == tags) {
			tags = new ArrayList<String>();
		}
		tags.add(tag);
		return this;
	}

	@Override
	public String toString() {
		return ToStringFormatter.format(getClass(), super.toString(), "name", name, "description",
				description, "tags", tags, "modificationId", modificationId);
	}
}
