package com.saperion.sdb.rs.models;

import java.util.Collection;
import java.util.EnumSet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.saperion.sdb.spi.rights.ShareRight;

/**
 * The class Share.
 *
 * @author sts
 */
@XmlRootElement
public class Share extends CreatedTypedIdentifiable {
	private String name;
	private String receiverId;
	private String receiverName;
	private EnumSet<ShareRight> rights;

	public Share() {
		super(ModelType.SHARE);
	}

	public String getName() {
		return name;
	}

	public Share setName(String name) {
		this.name = name;
		return this;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public Share setReceiverId(String receiverId) {
		this.receiverId = receiverId;
		return this;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public Share setReceiverName(String receiverName) {
		this.receiverName = receiverName;
		return this;
	}

	@XmlElementWrapper(name = "rights")
	@XmlElement(name = "right")
	public Collection<ShareRight> getRights() {
		return rights;
	}

	public Share setRights(Collection<ShareRight> rights) {
		if (rights.isEmpty()) {
			this.rights = EnumSet.noneOf(ShareRight.class);
		} else {
			this.rights = EnumSet.copyOf(rights);
		}
		return this;
	}

	public Share addRight(ShareRight right) {
		if (null == right) {
			rights = EnumSet.of(right);
		} else {
			rights.add(right);
		}
		return this;
	}

	@Override
	public String toString() {
		return "Share{" + "name='" + name + '\'' + ", receiverId='" + receiverId + '\''
				+ ", receiverName='" + receiverName + '\'' + ", rights=" + rights + "} "
				+ super.toString();
	}
}
