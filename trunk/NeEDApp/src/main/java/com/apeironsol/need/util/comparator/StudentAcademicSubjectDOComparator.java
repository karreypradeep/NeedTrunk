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

import com.apeironsol.need.academics.dataobject.StudentAcademicSubjectDO;
import com.apeironsol.need.academics.dataobject.StudentExamSubjectDO;

/**
 * Comparator for {@link StudentExamSubjectDO}
 * 
 * @author Pradeep.
 */
public class StudentAcademicSubjectDOComparator implements Comparator<StudentAcademicSubjectDO>, Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1054777258492582017L;

	public static enum Order {

		SUBJECT_NAME;
	};

	private final Order	orderBy;

	private boolean		ascending	= true;

	public StudentAcademicSubjectDOComparator(final Order orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public int compare(final StudentAcademicSubjectDO source, final StudentAcademicSubjectDO target) {
		int result = 0;
		if (Order.SUBJECT_NAME.equals(this.orderBy) && source.getSubject() != null && target.getSubject() != null) {
			result = source.getSubject().getName().compareTo(target.getSubject().getName());
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