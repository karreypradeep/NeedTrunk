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

import com.apeironsol.need.academics.model.ReportCardExam;
import com.apeironsol.need.core.model.BranchFeeTypePeriodical;

/**
 * Comparator for {@link BranchFeeTypePeriodical}
 * 
 * @author Pradeep.
 */
public class ReportCardExamComparator implements Comparator<ReportCardExam>, Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4671883815776021148L;

	public static enum Order {
		PERCENTAGE;
	};

	private final Order	orderBy;

	private boolean		ascending	= true;

	public ReportCardExamComparator(final Order orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public int compare(final ReportCardExam source, final ReportCardExam target) {
		int result = 0;
		if (Order.PERCENTAGE.equals(this.orderBy) && source.getPercentage() != null && target.getPercentage() != null) {
			result = source.getPercentage().compareTo(target.getPercentage());
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
