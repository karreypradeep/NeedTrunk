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
 * @author Sunny
 * 
 *         Enums for EmploymentStatusConstant constants
 * 
 */
public enum EmploymentCurrentStatusConstant implements Labeled {

	ACTIVE("active"), RESIGNED("resigned"), TERMINATED("terminated"), RETIRED("retired");

	private String	label;

	EmploymentCurrentStatusConstant(final String label) {
		this.setLabel(label);
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
