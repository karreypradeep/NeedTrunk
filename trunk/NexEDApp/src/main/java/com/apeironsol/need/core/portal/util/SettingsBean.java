/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.portal.util;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

@Named
@Scope(value = "application")
public class SettingsBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long	serialVersionUID	= 4306457720536839894L;

	public static final String	DEVELOPMENT			= "Development";

	public static final String	PRODUCTION			= "Production";

	private String				projectState		= DEVELOPMENT;

	private boolean				showLog				= false;

	@PostConstruct
	public void init() {

	}

	public String getProjectState() {
		return this.projectState;
	}

	public void setProjectState(final String projectState) {
		this.projectState = projectState;
	}

	public boolean isShowLog() {
		return this.showLog;
	}

	public void setShowLog(final boolean showLog) {
		this.showLog = showLog;
	}

	public void toggleLog() {
		this.showLog = !this.showLog;
	}

}
