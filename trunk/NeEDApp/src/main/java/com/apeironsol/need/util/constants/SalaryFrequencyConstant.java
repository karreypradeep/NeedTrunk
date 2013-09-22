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
public enum SalaryFrequencyConstant implements Labeled {

	MONTHLY("monthly", 12), QUARTERLY("quarterly", 4), HALF_YEARLY("half_yearly", 2), YEARLY("yearly", 1);

	private String	label;
	private int		frequency;

	SalaryFrequencyConstant(final String label, final int frequency) {
		this.setLabel(label);
		this.setFrequency(frequency);
	}

	@Override
	public String getLabel() {
		return ViewUtil.getEnumLabel(this.label);
	}

	@Override
	public void setLabel(final String label) {
		this.label = label;
	}

	/**
	 * @return the frequency
	 */
	public int getFrequency() {
		return this.frequency;
	}

	/**
	 * @param frequency
	 *            the frequency to set
	 */
	public void setFrequency(final int frequency) {
		this.frequency = frequency;
	}
}
