/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.constants;

import java.util.ArrayList;
import java.util.List;

import com.apeironsol.need.util.portal.ViewUtil;

/**
 * Enums for FeesTypes constants
 * 
 * @author Pradeep
 * @author Pradeep
 */
public enum FeeTypeConstant implements Labeled {

	GENERAL_FEE("general_fee"),
	TUITION_FEE("tuition_fee"),
	HOSTEL_FEE("hostel_fee"),
	MISCELLANEOUS_FEE("miscellaneous_fee"),
	PAST_DUE("past_due"),
	APPLICATION_FEE("application_fee"),
	TRANSPORTATION_FEE("transportation_fee"),
	RESERVATION_FEE("reservation_fee");

	private static final List<FeeTypeConstant>	klassFeeTypes	= new ArrayList<FeeTypeConstant>();

	static {
		klassFeeTypes.add(TUITION_FEE);
		klassFeeTypes.add(HOSTEL_FEE);
	}

	private String								label;

	FeeTypeConstant(final String label) {
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

	public FeeTypeConstant[] getKlassFeeTypes() {
		return new FeeTypeConstant[] { FeeTypeConstant.TUITION_FEE, FeeTypeConstant.HOSTEL_FEE };
	}
}
