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

import com.apeironsol.need.academics.dataobject.StudentAcademicExamDO;
import com.apeironsol.need.academics.dataobject.StudentExamSubjectDO;

/**
 * Comparator for {@link StudentExamSubjectDO}
 * 
 * @author Pradeep.
 */
public class StudentAcademicExamDOComparator implements Comparator<StudentAcademicExamDO>, Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1054777258492582017L;

	public static enum Order {

		EXAM_START_DATE, EXAM_END_DATE;
	};

	private final Order	orderBy;

	private boolean		ascending	= true;

	public StudentAcademicExamDOComparator(final Order orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public int compare(final StudentAcademicExamDO source, final StudentAcademicExamDO target) {
		int result = 0;
		if (Order.EXAM_START_DATE.equals(this.orderBy) && source.getSectionExam() != null && target.getSectionExam() != null) {
			result = source.getSectionExam().getStartDate().compareTo(target.getSectionExam().getStartDate());
		}

		if (Order.EXAM_END_DATE.equals(this.orderBy) && source.getSectionExam() != null && target.getSectionExam() != null) {
			result = source.getSectionExam().getEndDate().compareTo(target.getSectionExam().getEndDate());
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