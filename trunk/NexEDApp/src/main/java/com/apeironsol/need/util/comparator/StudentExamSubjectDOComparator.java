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

import com.apeironsol.need.academics.dataobject.StudentExamSubjectDO;
import com.apeironsol.need.core.model.BranchFeeTypePeriodical;

/**
 * Comparator for {@link BranchFeeTypePeriodical}
 * 
 * @author Pradeep.
 */
public class StudentExamSubjectDOComparator implements Comparator<StudentExamSubjectDO>, Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1054777258492582017L;

	public static enum Order {

		SCHEDULED_DATE, EXAM_END_DATE;
	};

	private final Order	orderBy;

	private boolean		ascending	= true;

	public StudentExamSubjectDOComparator(final Order orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public int compare(final StudentExamSubjectDO source, final StudentExamSubjectDO target) {
		int result = 0;
		if (Order.SCHEDULED_DATE.equals(this.orderBy) && source.getSectionExamSubject() != null && target.getSectionExamSubject() != null) {
			result = source.getSectionExamSubject().getScheduledDate().compareTo(target.getSectionExamSubject().getScheduledDate());
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
