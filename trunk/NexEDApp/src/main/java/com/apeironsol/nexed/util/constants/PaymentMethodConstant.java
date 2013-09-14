package com.apeironsol.nexed.util.constants;

import com.apeironsol.nexed.util.portal.ViewUtil;

public enum PaymentMethodConstant implements Labeled {

	CASH("cash"), CHEQUE("cheque");

	private String	label;

	PaymentMethodConstant(final String label) {
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
