package com.apeironsol.nexed.util.constants;

import com.apeironsol.nexed.util.portal.ViewUtil;

public enum TransactionTypeConstant implements Labeled {

	PAYMENT("payment"), WITHDRAWL("withdrawl"), EXPENSES("advance");

	private String	label;

	TransactionTypeConstant(final String label) {
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
