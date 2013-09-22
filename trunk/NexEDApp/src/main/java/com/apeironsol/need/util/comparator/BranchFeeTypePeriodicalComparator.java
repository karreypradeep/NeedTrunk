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

import com.apeironsol.need.core.model.BranchFeeTypePeriodical;

/**
 * Comparator for {@link BranchFeeTypePeriodical}
 * 
 * @author Pradeep.
 */
public class BranchFeeTypePeriodicalComparator implements Comparator<BranchFeeTypePeriodical>, Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1054777258492582017L;

	/**
	 * Property to compare by: name.
	 */
	public static final String	START_DATE			= "startDate";

	private final String		orderBy;

	private boolean				ascending			= true;

	public BranchFeeTypePeriodicalComparator(final String orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public int compare(final BranchFeeTypePeriodical source, final BranchFeeTypePeriodical target) {
		int result = 0;
		if (START_DATE.equals(this.orderBy)) {
			result = source.getStartDate().compareTo(target.getStartDate());
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
