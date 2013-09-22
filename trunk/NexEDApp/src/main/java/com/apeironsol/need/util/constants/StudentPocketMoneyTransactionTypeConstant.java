/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.util.constants;

import com.apeironsol.need.util.EnumUtil;
import com.apeironsol.need.util.portal.ViewUtil;

/**
 * Enum for PocketMoneyTransactionType constants.
 * 
 * @author Pradeep
 */
public enum StudentPocketMoneyTransactionTypeConstant implements Labeled {

	DEPOSIT("deposit"), WITHDRAW("withdraw");

	private String	label;

	StudentPocketMoneyTransactionTypeConstant(final String label) {
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

	public static StudentPocketMoneyTransactionTypeConstant[] getValuesSortedByLabels() {
		return EnumUtil.getEnumsSortedByLabels(StudentPocketMoneyTransactionTypeConstant.class)
				.toArray(
						new StudentPocketMoneyTransactionTypeConstant[StudentPocketMoneyTransactionTypeConstant
								.values().length]);
	}
}
