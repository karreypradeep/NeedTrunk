package com.apeironsol.nexed.util.constants;

import com.apeironsol.nexed.util.portal.ViewUtil;

public enum StudentAdditionalFeeTypeConstant implements Labeled {

	PAST_DUE("past_due"), MISCELLANEOUS("miscellaneous");

	private String	label;

	StudentAdditionalFeeTypeConstant(final String label) {
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
