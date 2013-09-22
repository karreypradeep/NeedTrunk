package com.apeironsol.need.util.comparator;

import java.util.Comparator;

import com.apeironsol.need.reporting.ro.StudentRO;

public class StudentROComparator implements Comparator<StudentRO> {

	@Override
	public int compare(final StudentRO studentSource, final StudentRO studentTarget) {
		try {
			return studentSource.getStudentAcademicYear().getStudent().getDisplayName().compareTo(studentTarget.getStudentAcademicYear().getStudent().getDisplayName());
		} catch (NullPointerException npe) {
			return 0;
		}
	}

}
