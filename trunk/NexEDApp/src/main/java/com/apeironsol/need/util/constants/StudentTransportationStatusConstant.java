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
 * Enums for student status constants.
 * 
 */
public enum StudentTransportationStatusConstant implements Labeled {
	
	ASSIGN_TRANSPORTATION("assign_transportation"),
	CLOSE_TRANSPORTATION("close_transportation");
	
	private String	label;

	StudentTransportationStatusConstant(final String label) {
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
