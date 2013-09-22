package com.apeironsol.need.util.constants;

import com.apeironsol.need.util.portal.ViewUtil;

public enum DurationTypeConstants implements Labeled {

	MILLI_SECONDS("milli_seconds"), SECONDS("seconds"), MINUTES("minutes"), HOURS("hours");

	private String	label;

	DurationTypeConstants(final String label) {
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
