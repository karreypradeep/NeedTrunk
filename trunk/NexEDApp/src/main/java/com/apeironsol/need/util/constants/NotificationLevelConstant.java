/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.util.constants;

import com.apeironsol.need.util.portal.ViewUtil;

/**
 * @author pradeepk
 *
 */
public enum NotificationLevelConstant implements Labeled {
	
	ORGANIZATION("organization"),BRANCH("branch"),CLASS("class"),SECTION("section"),STUDENT("student"),ADHOC("adhoc");

	private String	label;

	NotificationLevelConstant(final String label) {
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
