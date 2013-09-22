/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.util.constants;

import com.apeironsol.need.util.portal.ViewUtil;

/**
 * Enums for Fees classification Types level constants
 * 
 * @author Pradeep
 */
public enum FeeClassificationLevelConstant implements Labeled {

	BRANCH_LEVEL("branch_level"), KLASS_LEVEL("class_level"), STUDENT_LEVEL("student_level"), TRANSPORTATION_LEVEL("transportation_level");

	private String	label;

	FeeClassificationLevelConstant(final String label) {
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
