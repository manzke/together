package com.saperion.sdb.rs.models;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The class Localizations.
 *
 * @author sts
 */
@XmlRootElement
public class Localizations {

	private String language;
	private final Map<String, String> locals;

	public Localizations() {
		locals = new HashMap<String, String>();
	}

	public void setLocale(Locale locale) {
		this.language = locale.getLanguage();
	}

	public String put(String key, String value) {
		return locals.put(key, value);
	}

	public String getType() {
		return "localization";
	}

	public String getLanguage() {
		return this.language;
	}

	@XmlElementWrapper(name = "localizations")
	public Map<String, String> getLocals() {
		return this.locals;
	}

	public void putAll(Map<? extends String, ? extends String> m) {
		locals.putAll(m);
	}

	//
	// METHODS BELOW ARE ONLY FOR JAX-B.
	//
	public void setType(String type) {
		//ignore.
	}

	public void setLanguage(String language) {
		//ignore.
	}
}
