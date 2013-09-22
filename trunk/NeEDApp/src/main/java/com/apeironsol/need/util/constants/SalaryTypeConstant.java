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
 * Enum for salary type constant.
 * 
 * @author Pradeep
 */
public enum SalaryTypeConstant implements Labeled {

	MONTHLY("monthly"), ADVANCE("advance"), BONUS("bonus");

	private String	label;

	SalaryTypeConstant(final String label) {
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

	public static SalaryTypeConstant[] getSalaryTypeConstantsForPayment() {
		SalaryTypeConstant[] salaryTypeConstantsForPayments = new SalaryTypeConstant[2];
		salaryTypeConstantsForPayments[0] = MONTHLY;
		salaryTypeConstantsForPayments[1] = ADVANCE;
		return salaryTypeConstantsForPayments;
	}

}
