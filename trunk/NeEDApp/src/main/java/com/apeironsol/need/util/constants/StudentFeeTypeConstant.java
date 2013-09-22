package com.apeironsol.need.util.constants;

import com.apeironsol.need.util.portal.ViewUtil;

public enum StudentFeeTypeConstant implements Labeled {

	KLASS_FEE("klass_fee"), ADDITIONAL_FEE("additional_fee");

	private String	label;

	StudentFeeTypeConstant(final String label) {
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

	public String getValue() {
		return this.label;
	}

}
