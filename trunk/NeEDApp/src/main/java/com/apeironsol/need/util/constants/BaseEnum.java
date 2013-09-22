package com.apeironsol.need.util.constants;

import com.apeironsol.need.util.portal.ViewUtil;

public enum BaseEnum implements Labeled {

	BASE("base");

	private String	label;

	BaseEnum(final String label) {
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
