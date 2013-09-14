/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.util.constants;

import com.apeironsol.nexed.util.EnumUtil;
import com.apeironsol.nexed.util.portal.ViewUtil;

/**
 * Enums for student status constants.
 * 
 */
public enum StudentStatusConstant implements Labeled {

	IN_ADMISSION("in_admission"),
	ACCEPTED("accepted"),
	ACTIVE("active"),
	REJECTED("rejected"),
	TRANSFERRED("transferred"),
	ALMUNUS("almunus"),
	ARCHIVE("archive"),
	ACCEPT_FOR_DROPOUT("accept_for_dropout"),
	DROPOUT("dropout");

	private String	label;

	StudentStatusConstant(final String label) {
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

	public static StudentStatusConstant[] getValuesSortedByLabels() {
		return EnumUtil.getEnumsSortedByLabels(StudentStatusConstant.class).toArray(new StudentStatusConstant[StudentStatusConstant.values().length]);
	}
}
