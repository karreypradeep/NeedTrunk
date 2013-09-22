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

import com.apeironsol.need.core.dataobject.SectionAttendanceReportMonthlyDO;
import com.apeironsol.need.core.model.BranchFeeTypePeriodical;

/**
 * Comparator for {@link BranchFeeTypePeriodical}
 * 
 * @author Pradeep.
 */
public class SectionAttendanceReportMonthlyDOComparator implements Comparator<SectionAttendanceReportMonthlyDO>, Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1054777258492582017L;

	public static enum Order {

		MONTH, START_DATE;
	};

	private final Order	orderBy;

	private boolean		ascending	= true;

	public SectionAttendanceReportMonthlyDOComparator(final Order orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public int compare(final SectionAttendanceReportMonthlyDO source, final SectionAttendanceReportMonthlyDO target) {
		int result = 0;
		if (Order.MONTH.equals(this.orderBy) && source.getMonthConstant() != null && target.getMonthConstant() != null) {
			result = source.getMonthConstant().getMonthNumber().compareTo(target.getMonthConstant().getMonthNumber());
		} else if (Order.START_DATE.equals(this.orderBy) && source.getAttendanceStartDate() != null && target.getAttendanceStartDate() != null) {
			result = source.getAttendanceStartDate().compareTo(target.getAttendanceStartDate());
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
