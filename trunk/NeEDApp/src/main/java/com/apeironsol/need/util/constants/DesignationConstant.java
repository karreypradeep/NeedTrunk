/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.constants;

import com.apeironsol.need.util.portal.ViewUtil;

/**
 * Enums for designation constants
 * 
 * @author Pradeep
 */
public enum DesignationConstant implements Labeled {

	DIRECTOR("director"), VICE_PRESIDENT("vice_president"), ACCOUNTANT("accountant"), PRINCIPAL("principal"), VICE_PRINCIPAL(
			"vice_principal"), TEACHER("teacher"), HEAD_MASTER("head_master");

	private String	label;

	DesignationConstant(final String label) {
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
