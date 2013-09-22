/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.constants;

import com.apeironsol.need.util.portal.ViewUtil;

/**
 * Enums for Authority constants
 * 
 * @author Pradeep
 */
public enum AuthorityCategoryConstant implements Labeled {

	// User Authorities
	SECURITY("security"),
	CORE("core"),
	FINANCIALS("financials"),
	HUMAN_RESOURCE("humanResource"),
	PROCUREMENT("procurement"),
	ACADEMICS("academics"),
	REPORTS("reports"),
	TRANSPORTATION("transportation"),
	IMPORTS("imports"),
	NOTIFICATIONS("notifications"),
	STUDENT("student");

	private String	label;

	AuthorityCategoryConstant(final String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return ViewUtil.getEnumLabel(this.label);
	}

	@Override
	public void setLabel(final String label) {
		this.label = label;
	}

}
