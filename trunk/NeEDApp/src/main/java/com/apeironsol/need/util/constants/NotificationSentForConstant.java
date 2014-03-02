/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.util.constants;

import com.apeironsol.need.util.portal.ViewUtil;

/**
 * @author pradeepk
 * 
 */
public enum NotificationSentForConstant implements Labeled {

	STUDENTS("students"),
	ADMISSIONS("admissions"),
	EMPLOYEES("employees"),
	BRANCH_FINANCIALS("branch_financials"),
	STUDENT_REGISTRATIONS("student_registrations");

	private String	label;

	NotificationSentForConstant(final String label) {
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

	public static NotificationSentForConstant getConstantByLabel(final String label) {
		NotificationSentForConstant constant = null;
		if ("students".equals(label)) {
			constant = STUDENTS;
		} else if ("admissions".equals(label)) {
			constant = ADMISSIONS;
		} else if ("employees".equals(label)) {
			constant = EMPLOYEES;
		} else if ("student_registrations".equals(label)) {
			constant = STUDENT_REGISTRATIONS;
		} else if ("branch_financials".equals(label)) {
			constant = BRANCH_FINANCIALS;
		} else {
			constant = STUDENTS;
		}
		return constant;
	}

}
