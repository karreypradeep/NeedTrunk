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
public enum EmployeeCTCDefinitionTypeConstant implements Labeled {

	MONTHLY_SALARY_STRUCTURE("monthly_salary_structure"),
	RETTIRAL_BENEFITS("retiral_benefits"),
	ADDITIONAL_BENEFITS("additional_benefits"),
	ANNUAL_BENEFITS("annual_benefits");

	private String	label;

	EmployeeCTCDefinitionTypeConstant(final String label) {
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
