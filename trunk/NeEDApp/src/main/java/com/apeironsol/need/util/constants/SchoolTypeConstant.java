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
 * Enums for school type constaint
 * 
 * @author Pradeep
 */
public enum SchoolTypeConstant implements Labeled {

	PRIVATE("private"), GOVT("govt"), OTHERS("others");

	private String	label;

	SchoolTypeConstant(final String label) {
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
