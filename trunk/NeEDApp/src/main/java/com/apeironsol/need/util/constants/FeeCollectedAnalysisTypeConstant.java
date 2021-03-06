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
public enum FeeCollectedAnalysisTypeConstant implements Labeled {

	BY_COURSE("by_course"), BY_GENDER("by_gender"), BY_FATHER_OCCUPATION("by_father_occupation"), BY_LOCATION("by_location");

	private String	label;

	FeeCollectedAnalysisTypeConstant(final String label) {
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

	public static FeeCollectedAnalysisTypeConstant[] getValuesSortedByLabels() {
		return EnumUtil.getEnumsSortedByLabels(FeeCollectedAnalysisTypeConstant.class).toArray(
				new FeeCollectedAnalysisTypeConstant[FeeCollectedAnalysisTypeConstant.values().length]);
	}

	public String getValue() {
		return this.label;
	}
}
