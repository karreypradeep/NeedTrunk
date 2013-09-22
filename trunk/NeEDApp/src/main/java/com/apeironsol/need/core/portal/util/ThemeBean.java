/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.portal.util;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

@Named
@Scope(value = "session")
public class ThemeBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long	serialVersionUID	= 7793857860236101739L;

	private Map<String, String>	themes;

	private String				theme				= "apeironsol";			// default

	@PostConstruct
	public void init() {

		this.setThemes(new TreeMap<String, String>());
		// this.getThemes().put("Afterdark", "afterdark");
		// this.getThemes().put("Afternoon", "afternoon");
		// this.getThemes().put("Afterwork", "afterwork");
		this.getThemes().put("apeironsol Default", "apeironsol");
		// this.getThemes().put("apeironsol Blue", "bizblue");
		this.getThemes().put("apeironsol Green", "bizgreen");
		// this.getThemes().put("apeironsol Red", "bizred");
		// this.getThemes().put("Blitzer", "blitzer");
		// this.getThemes().put("Bootstrap", "bootstrap");

		// this.getThemes().put("Bluesky", "bluesky");
		// this.getThemes().put("Aristo", "aristo");
		// this.getThemes().put("Cruze", "cruze");
		// this.getThemes().put("Black-Tie", "black-tie");
		// this.getThemes().put("Casablanca", "casablanca");
		// this.getThemes().put("Cupertino", "cupertino");
		// this.getThemes().put("Dark-Hive", "dark-hive");
		// this.getThemes().put("Dot-Luv", "dot-luv");
		// this.getThemes().put("Eggplant", "eggplant");
		// this.getThemes().put("Excite-Bike", "excite-bike");
		// this.getThemes().put("Flick", "flick");
		// this.getThemes().put("Glass-X", "glass-x");
		// this.getThemes().put("Hot-Sneaks", "hot-sneaks");
		// this.getThemes().put("Humanity", "humanity");
		// this.getThemes().put("Le-Frog", "le-frog");
		// this.getThemes().put("Midnight", "midnight");
		// this.getThemes().put("Mint-Choc", "mint-choc");
		// this.getThemes().put("Overcast", "overcast");
		// this.getThemes().put("Pepper-Grinder", "pepper-grinder");
		// this.getThemes().put("Redmond", "redmond");
		// this.getThemes().put("Rocket", "rocket");
		// this.getThemes().put("Sam", "sam");
		// this.getThemes().put("Smoothness", "smoothness");
		// this.getThemes().put("South-Street", "south-street");
		// this.getThemes().put("Start", "start");
		// this.getThemes().put("Sunny", "sunny");
		// this.getThemes().put("Swanky-Purse", "swanky-purse");
		// this.getThemes().put("Trontastic", "trontastic");
		// this.getThemes().put("UI-Darkness", "ui-darkness");
		// this.getThemes().put("UI-Lightness", "ui-lightness");
		// this.getThemes().put("Vader", "vader");
	}

	public void saveTheme() {

	}

	public String getTheme() {
		this.init();
		return this.theme;
	}

	public void setTheme(final String theme) {
		this.theme = theme;
	}

	public Map<String, String> getThemes() {
		return this.themes;
	}

	public void setThemes(final Map<String, String> themes) {
		this.themes = themes;
	}
}
