/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.constants;

import com.apeironsol.need.util.EnumUtil;
import com.apeironsol.need.util.portal.ViewUtil;

/**
 * Enums for admission status constants.
 * 
 * @author Pradeep
 */
public enum StudentRegistrationStatusConstant implements Labeled {

	SUBMITTED("submitted"), ADMITTED("admitted"), JOINED_OTHER_COLLEGE("joined_other_college"), NOT_AVAILABLE("not_available");

	private String	label;

	StudentRegistrationStatusConstant(final String label) {
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

	public static StudentRegistrationStatusConstant[] getValuesSortedByLabels() {
		return EnumUtil.getEnumsSortedByLabels(StudentRegistrationStatusConstant.class).toArray(
				new StudentRegistrationStatusConstant[StudentRegistrationStatusConstant.values().length]);
	}

	public String getValue() {
		return this.label;
	}
}
