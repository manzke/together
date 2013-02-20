package com.saperion.sdb.rs.models;

import com.saperion.common.lang.format.ToStringFormatter;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;

/**
 * The class StateHolder.
 *
 * @author sts
 */
public class StateHolder<T extends Enum<T>> {

	private EnumSet<T> states;

	public StateHolder() {
	}

	public StateHolder(EnumSet<T> states) {
		this.states = states;
	}

	public String toString() {
		return ToStringFormatter.format(getClass(), null, "states", states);
	}

	public Collection<T> getStates() {
		if (null == states) {
			return Collections.emptyList();
		}
		return states.clone();
	}

	public void addState(T state) {
		if (null == states) {
			states = EnumSet.<T> of(state);
		} else {
			states.add(state);
		}
	}

	public void removeState(T state) {
		if (null != states) {
			states.remove(state);
		}
	}

	public void setStates(Collection<T> newStates) {
		if (newStates.isEmpty()) {
			if (null != states) {
				states.clear();
			}
			return;
		}

		states = EnumSet.<T> copyOf(newStates);
	}
}
