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
 * Enums for FeesTypes constants
 * 
 * @author Pradeep
 */
public enum StudentExamSubjectStatusConstant implements Labeled {

	ASSIGNED("assigned"), TAKEN("taken"), ABSENT("absent");

	private String	label;

	StudentExamSubjectStatusConstant(final String label) {
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
