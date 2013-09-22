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
 * Enums for section reports.
 * 
 * @author Pradeep.
 */
public enum SectionReportsConstant implements Labeled {
	ATTENDANCE_REPORT("Attendance report"), FINANCIAL_REPORT("Financial report");

	private String	label;

	SectionReportsConstant(final String label) {
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
