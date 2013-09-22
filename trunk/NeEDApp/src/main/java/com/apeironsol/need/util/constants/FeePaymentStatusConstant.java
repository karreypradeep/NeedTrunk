package com.apeironsol.need.util.constants;

import com.apeironsol.need.util.portal.ViewUtil;

public enum FeePaymentStatusConstant implements Labeled {

	SUBMITTED("submitted"), APPROVED("approved"), REJECTED("rejected");

	private String	label;

	FeePaymentStatusConstant(final String label) {
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
