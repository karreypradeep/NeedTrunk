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

import com.apeironsol.need.academics.model.GradeSystemRange;
import com.apeironsol.need.core.model.BranchFeeTypePeriodical;

/**
 * Comparator for {@link BranchFeeTypePeriodical}
 * 
 * @author Pradeep.
 */
public class GradeSystemRangeComparator implements Comparator<GradeSystemRange>, Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8109354420346421104L;

	public static enum Order {

		MIN_PERCENTAGE, MAX_PERCENTAGE;
	};

	private final Order	orderBy;

	private boolean		ascending	= true;

	public GradeSystemRangeComparator(final Order orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public int compare(final GradeSystemRange source, final GradeSystemRange target) {
		int result = 0;
		if (Order.MIN_PERCENTAGE.equals(this.orderBy) && source.getMinimumRange() != null && target.getMinimumRange() != null) {
			result = source.getMinimumRange().compareTo(target.getMinimumRange());
		}
		if (Order.MAX_PERCENTAGE.equals(this.orderBy) && source.getMaximumRange() != null && target.getMaximumRange() != null) {
			result = source.getMaximumRange().compareTo(target.getMaximumRange());
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
