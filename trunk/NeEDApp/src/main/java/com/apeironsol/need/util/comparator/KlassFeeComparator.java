/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.comparator;

import java.io.Serializable;
import java.util.Comparator;

import com.apeironsol.need.financial.model.KlassLevelFee;

/**
 * Comparator for {@link KlassLevelFee}
 * 
 * @author Pradeep.
 */
public class KlassFeeComparator implements Comparator<KlassLevelFee>, Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1054777258492582017L;

	/**
	 * Property to compare by: name.
	 */
	public static final String	FEE_TYPE			= "feeType";

	private final String		orderBy;

	private boolean				ascending			= true;

	public KlassFeeComparator(final String orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public int compare(final KlassLevelFee source, final KlassLevelFee target) {
		int result = 0;
		if (FEE_TYPE.equals(this.orderBy)) {
			result = source.getBuildingBlock().getFeeType().compareTo(target.getBuildingBlock().getFeeType());
		}
		return result != 0 ? this.ascending ? result : result == 1 ? -1 : 1 : result;
	}

	public void setAscending() {
		this.ascending = true;
	}

	public void setDescending() {
		this.ascending = false;
	}
}
