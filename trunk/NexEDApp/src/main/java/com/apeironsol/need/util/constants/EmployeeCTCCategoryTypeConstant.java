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
public enum EmployeeCTCCategoryTypeConstant implements Labeled {

	PROVIDENT_FUND("provident_fund"),
	BASIC_SALARY("basic_salary"),
	DEARNESS_ALLOWANCE("dearness_allowance"),
	HOUSE_RENT_ALLOWANCE("house_rent_allowance"),
	MEDICAL_ALLOWANCE("medical_allowance"),
	LEAVE_TRAVEL_ALLOWANCE("leave_travel_allowance"),
	CONVEYANCE_ALLOWANCE("conveyance_allowance"),
	SPECIAL_ALLOWANCE("special_allowance"),
	GRATUITY("gratuity"),
	OTHER_ALLOWANCE("other_allowance"),
	VARIABLE_PAY("variable_pay"),
	MEDICAL_INSURANCE("medical_insurance"),
	MEAL_COUPON("meal_coupon"),
	BONUS("bonus");

	private String	label;

	EmployeeCTCCategoryTypeConstant(final String label) {
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
