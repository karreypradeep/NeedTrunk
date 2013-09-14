/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.util.constants;

import com.apeironsol.nexed.util.portal.ViewUtil;

/**
 * Enums for alert types.
 * 
 * @author Pradeep.
 */
public enum AttendanceTypeConstant implements Labeled {

	DAILY("daily"), SUBJECT_WISE("subject_wise");

	private String	label;

	AttendanceTypeConstant(final String label) {
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
