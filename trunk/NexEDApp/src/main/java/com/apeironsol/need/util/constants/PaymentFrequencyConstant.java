package com.apeironsol.need.util.constants;

import com.apeironsol.need.util.portal.ViewUtil;

public enum PaymentFrequencyConstant implements Labeled {

	ONCE("once"), TWICE("twice"), THRICE("thrice"), EVERY_MONTH("every_month"), CUSTOM("custom");

	private String	label;

	PaymentFrequencyConstant(final String label) {
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
