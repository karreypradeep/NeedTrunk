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
public enum RegistrationStatusConstant implements Labeled {

	SUBMITTED("submitted"), ADMISSION("admission"), JOINED_OTHER_COLLEGE("joined_other_college");

	private String	label;

	RegistrationStatusConstant(final String label) {
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

	public static RegistrationStatusConstant[] getValuesSortedByLabels() {
		return EnumUtil.getEnumsSortedByLabels(RegistrationStatusConstant.class).toArray(
				new RegistrationStatusConstant[RegistrationStatusConstant.values().length]);
	}

	public String getValue() {
		return this.label;
	}
}
