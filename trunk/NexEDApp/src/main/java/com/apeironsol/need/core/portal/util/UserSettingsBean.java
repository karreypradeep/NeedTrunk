/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */

package com.apeironsol.need.core.portal.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.util.portal.ViewUtil;

/**
 * User settings bean.
 * 
 * @author Pradeep
 */
@Named("userSettingsBean")
@Scope(value = "session")
public final class UserSettingsBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long		serialVersionUID	= -1161846576925021711L;

	/** Logger. */
	private static final Logger		log					= Logger.getLogger(UserSettingsBean.class);

	private final List<SelectItem>	languages			= new ArrayList<SelectItem>();

	private final String			language			= "en";

	private Locale					locale;

	@PostConstruct
	public void init() {
		this.languages.add(new SelectItem("en", "English"));
		this.languages.add(new SelectItem("tu", "Telugu"));
		this.locale = new Locale(this.language);
	}

	public void setLocale() {
		log.info("Locale has updated to US");
		ViewUtil.getViewRoot().setLocale(this.locale);
		ViewUtil.addMessage("settings_locale_changed", FacesMessage.SEVERITY_INFO);
	}

	public Locale getLocale() {
		return this.locale;
	}

	public boolean hasOrganizationAuthority() {
		return false;
	}

	public boolean hasManagementAuthority() {
		return false;
	}

	public boolean hasStudentAuthority() {
		return false;
	}

	public boolean hasParentAuthortiy() {
		return false;
	}

	public boolean hasEmployeeAuthortiy() {
		return false;
	}
}
