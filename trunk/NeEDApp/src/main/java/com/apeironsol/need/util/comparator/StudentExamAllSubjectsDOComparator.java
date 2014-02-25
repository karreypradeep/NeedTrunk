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

import com.apeironsol.need.academics.dataobject.StudentExamAllSubjectsDO;
import com.apeironsol.need.core.model.BranchFeeTypePeriodical;

/**
 * Comparator for {@link BranchFeeTypePeriodical}
 * 
 * @author Pradeep.
 */
public class StudentExamAllSubjectsDOComparator implements Comparator<StudentExamAllSubjectsDO>, Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1054777258492582017L;

	public static enum Order {

		STUDENT_ADMISSION_NR, STUDENT_DISPLAY_NAME;
	};

	private final Order	orderBy;

	private boolean		ascending	= true;

	public StudentExamAllSubjectsDOComparator(final Order orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public int compare(final StudentExamAllSubjectsDO source, final StudentExamAllSubjectsDO target) {
		int result = 0;
		if (Order.STUDENT_ADMISSION_NR.equals(this.orderBy) && (source.getStudentAcademicYear().getStudent().getAdmissionNr() != null)
				&& (target.getStudentAcademicYear().getStudent().getAdmissionNr() != null)) {
			result = source.getStudentAcademicYear().getStudent().getAdmissionNr().compareTo(target.getStudentAcademicYear().getStudent().getAdmissionNr());
		} else if (Order.STUDENT_DISPLAY_NAME.equals(this.orderBy) && (source.getStudentAcademicYear().getStudent().getDisplayName() != null)
				&& (target.getStudentAcademicYear().getStudent().getDisplayName() != null)) {
			result = source.getStudentAcademicYear().getStudent().getDisplayName().compareTo(target.getStudentAcademicYear().getStudent().getDisplayName());
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
