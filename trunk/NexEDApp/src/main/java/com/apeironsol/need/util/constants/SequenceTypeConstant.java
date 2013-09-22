package com.apeironsol.need.util.constants;

public enum SequenceTypeConstant implements Labeled {
	REGISTRATION_NUMBER("registration_number"),
	ADMISSION_NUMBER("admission_number"),
	FEE_DEPOSIT("fee_deposit"),
	FEE_REFUND("fee_refund"),
	FEE_DEDUCT("fee_deduct"),
	PURCHASE_ORDER_NUMBER("purchase_order_number"),
	SALARY("salary"),
	BANK_ACCOUNT_TRANSACTION("bank_account_transaction"),
	CREDIT_ACCOUNT_TRANSACTION("credit_account_transaction"),
	BRANCH_EXPENSE("branch_expense"),
	BRANCH_INVOICE("branch_invoice"),
	BRANCH_INVESTMENT("branch_investment"),
	EMPLOYEE_NUMBER("employee_number");

	private String	label;

	SequenceTypeConstant(final String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public void setLabel(final String label) {
		this.label = label;

	}

}
