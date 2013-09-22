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
 * Enums for FeesTypes constants
 * 
 * @author Pradeep
 */
public enum FrequencyConstant implements Labeled {

	MONTHLY("monthly"), QUARTERLY("quarterly"), HALF_YEARLY("half_yearly"), YEARLY("yearly"), BY_MONTHLY("by_monthly"), ONE_TIME(
			"one_time"), CUSTOM("custom");

	private String	label;

	FrequencyConstant(final String label) {
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
