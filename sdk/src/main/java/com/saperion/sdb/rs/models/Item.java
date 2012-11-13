package com.saperion.sdb.rs.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;


/** The class Item. */
public abstract class Item extends ModifiableTypedIdentifiable {
	private String name;
	private String description;
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
		return "Item{" + "name='" + name + '\'' + ", description='" + description + '\''
				+ ", tags=" + tags + "} " + super.toString();
	}
}
