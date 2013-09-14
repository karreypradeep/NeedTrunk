/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.util.constants;

import com.apeironsol.nexed.util.portal.ViewUtil;

/**
 * Enums for accessControlType constants
 * 
 * @author Pradeep
 */
public enum AccessControlTypeConstant implements Labeled {
	ORGANIZATION("organization"), BRANCH("branch");

	private String	label;

	AccessControlTypeConstant(final String label) {
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
