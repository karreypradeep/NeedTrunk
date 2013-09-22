/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.constants;

import com.apeironsol.need.util.EnumUtil;
import com.apeironsol.need.util.portal.ViewUtil;

/**
 * Enums for admission status constants.
 * 
 * @author Pradeep
 */
public enum AdmissionStatusConstant implements Labeled {

	SUBMITTED("submitted"), INREVIEW("inreview"), REVIEWED("reviewed"), ACCEPTED("accepted"), ADMITTED("admitted"), REJECTED(
			"rejected"), ARCHIVE("archive");

	private String	label;

	AdmissionStatusConstant(final String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return ViewUtil.getEnumLabel(label);
	}

	@Override
	public void setLabel(final String label) {
		this.label = label;
	}

	public static AdmissionStatusConstant[] getValuesSortedByLabels() {
		return EnumUtil.getEnumsSortedByLabels(AdmissionStatusConstant.class).toArray(
				new AdmissionStatusConstant[AdmissionStatusConstant.values().length]);
	}

	public String getValue() {
		return label;
	}
}
