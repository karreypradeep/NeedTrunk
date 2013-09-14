/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.core.service;

import java.util.Collection;

import com.apeironsol.nexed.core.model.Attendance;
import com.apeironsol.nexed.core.model.StudentAbsent;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
public interface StudentAbsentService {

	/**
	 * Find student attendance by section object id and student attendance
	 * object id.
	 * 
	 * @param sectionId
	 *            section object id.
	 * @param studentAcademicYearId
	 *            student attendance object id.
	 * @return collection of student attendance by section object id and student
	 *         attendance object id.
	 */
	StudentAbsent findStudentAttendanceByAttendanceIdAndStudentAcademicYearId(Long attendanceId, Long studentAcademicYearId);

	Collection<StudentAbsent> findStudentAttendanceByAttendancesAndStudentAcademicYearId(final Collection<Attendance> attendances,
			final Long studentAcademicYearId);

	Collection<StudentAbsent> findStudentAttendanceByAttendances(final Collection<Attendance> attendances);
}
