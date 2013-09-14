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
 * @author Pradeep
 */
public enum StudentFeeTransactionTypeConstant implements Labeled {

	PAYMENT("payment"), REFUND("refund"), DEDUCT("deduct");

	private String	label;

	StudentFeeTransactionTypeConstant(final String label) {
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

	public static StudentFeeTransactionTypeConstant[] getValuesSortedByLabels() {
		return EnumUtil.getEnumsSortedByLabels(StudentFeeTransactionTypeConstant.class).toArray(
				new StudentFeeTransactionTypeConstant[StudentFeeTransactionTypeConstant.values().length]);
	}
}
