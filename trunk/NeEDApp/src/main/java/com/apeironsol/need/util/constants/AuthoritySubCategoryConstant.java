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
 * Enums for Authority constants
 * 
 * @author Pradeep
 */
public enum AuthoritySubCategoryConstant implements Labeled {

	// User Authorities
	ROLE("role"),
	ACCESS("access"),
	ADMISSION("admission"),
	ORGANIZATION("organization"),
	BRANCH("branch"),
	BUILDING_BLOCK("building_block"),
	USER_ACCOUNT("user_account"),
	USER_GROUP("user_group"),
	ACADEMIC_YEAR("academic_year"),
	BATCH("batch"),
	KLASS("klass"),
	SECTION("section"),
	STUDENT("student"),
	EMPLOYEE("employee"),
	FEE_DEFINITION("fee_definition"),
	FEE_PAYMENT("fee_payment"),
	FEE_REFUND("fee_refund"),
	FEE_DEDUCTION("fee_deduction"),
	ASSERT("assert"),
	LIABILITY("liability"),
	INCOME("income"),
	INVESTMENT("investment"),
	EXPENSES("expenses"),
	BANK_ACCOUNTS("bank_accounts"),
	BALANCE_SHEET("balance_sheet"),
	FINANCE("finance"),
	PURCHASE_ORDER("purchase_order"),
	NOTIFICATIONS("notifications"),
	EXAM("exam");

	private String	label;

	AuthoritySubCategoryConstant(final String label) {
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
