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

import com.apeironsol.need.core.dataobject.StudentAttendanceMonthlyDO;
import com.apeironsol.need.core.model.BranchFeeTypePeriodical;

/**
 * Comparator for {@link BranchFeeTypePeriodical}
 * 
 * @author Pradeep.
 */
public class StudentAttendanceMonthlyDOComparator implements Comparator<StudentAttendanceMonthlyDO>, Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1054777258492582017L;

	public static enum Order {

		MONTH, ATTENDANCE_MONTH_DATE;
	};

	private final Order	orderBy;

	private boolean		ascending	= true;

	public StudentAttendanceMonthlyDOComparator(final Order orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public int compare(final StudentAttendanceMonthlyDO source, final StudentAttendanceMonthlyDO target) {
		int result = 0;
		if (Order.MONTH.equals(this.orderBy) && source.getMonth() != null && target.getMonth() != null) {
			result = source.getMonth().getMonthNumber().compareTo(target.getMonth().getMonthNumber());
		} else if (Order.ATTENDANCE_MONTH_DATE.equals(this.orderBy) && source.getAttendanceMonth() != null && target.getAttendanceMonth() != null) {
			result = source.getAttendanceMonth().compareTo(target.getAttendanceMonth());
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
