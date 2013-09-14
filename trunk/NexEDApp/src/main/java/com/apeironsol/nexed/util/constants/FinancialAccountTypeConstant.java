package com.apeironsol.nexed.util.constants;

import com.apeironsol.nexed.util.portal.ViewUtil;

public enum FinancialAccountTypeConstant implements Labeled {

	STUDENT_ACCOUNT("student_account"), EMPLOYEE_ACCOUNT("employee_account"), BRANCH_ACCOUNT("branch_account"), ORGANIZATION_ACCOUNT(
			"organizatin_account");

	private String	label;

	FinancialAccountTypeConstant(final String label) {
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
