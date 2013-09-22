package com.apeironsol.need.util.constants;

import com.apeironsol.need.util.portal.ViewUtil;

public enum BranchTypeConstant implements Labeled {

	SCHOOL("school"), 
	INTERMEDIATE("intermediate"), 
	UNDER_GRADUATION("under_graduation"),
	POST_GRADUATION("post_graduation"), 
	MASTERS("masters"),
	DOCTORAL("doctoral");

	private String	label;

	BranchTypeConstant(final String label) {
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
