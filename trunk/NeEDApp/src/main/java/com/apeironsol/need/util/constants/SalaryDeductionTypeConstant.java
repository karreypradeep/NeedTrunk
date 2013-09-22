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
public enum SalaryDeductionTypeConstant implements Labeled {

	PROVIDENT_FUND("provident_fund"),
	PROFESSIONAL_TAX("professional_tax"),
	INCOME_TAX("income_tax"),
	LOAN("loan"),
	OTHER_DEDUTION("other_deduction"),
	ADVANCE_SALARY("advance_salary"),
	MEAL_COUPON("meal_coupon");

	private String	label;

	SalaryDeductionTypeConstant(final String label) {
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

}
