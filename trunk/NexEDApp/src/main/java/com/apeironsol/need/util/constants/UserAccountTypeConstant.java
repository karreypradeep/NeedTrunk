/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.util.constants;

import java.util.ArrayList;
import java.util.List;

import com.apeironsol.need.util.portal.ViewUtil;

/**
 * Enums for login type constants
 * 
 * @author Sunny
 */
public enum UserAccountTypeConstant implements Labeled {
	EMPLOYEE("employee"), PARENT("parent"), STUDENT("student"), apeironsol("apeironsol");

	private static final List<UserAccountTypeConstant>	allowedLoginTypes	= new ArrayList<UserAccountTypeConstant>();

	private String									label;

	UserAccountTypeConstant(final String label) {
		setLabel(label);
	}

	static {
		allowedLoginTypes.add(EMPLOYEE);
		allowedLoginTypes.add(PARENT);
		allowedLoginTypes.add(STUDENT);
	}

	/**
	 * @return the allowedlogintypes
	 */
	public static List<UserAccountTypeConstant> getAllowedlogintypes() {
		return allowedLoginTypes;
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
