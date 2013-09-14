/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.core.service;

import java.util.Collection;

import com.apeironsol.nexed.core.model.StudentAcademicYear;

/**
 * Service interface for student academic year.
 * 
 * @author pradeep
 * 
 */
public interface StudentAcademicYearService {

	/**
	 * Find student academic yearby by id.
	 * 
	 * @param studentId
	 *            student id.
	 * @param academicYearId
	 *            academic year id.
	 * @return student academic year by student id and academic year id.
	 */
	StudentAcademicYear findStudentAcademicYearById(final Long id);

	/**
	 * Find student academic year by student id and academic year id.
	 * 
	 * @param studentId
	 *            student id.
	 * @param academicYearId
	 *            academic year id.
	 * @return student academic year by student id and academic year id.
	 */
	StudentAcademicYear findStudentAcademicYearByStudentIdAndAcademicYearId(final Long studentId,
			final Long academicYearId);

	/**
	 * Retrieve all student academic years for student.
	 * 
	 * @param studentId
	 *            student id.
	 * @return collection of all student academic years for student.
	 */
	Collection<StudentAcademicYear> findStudentAcademicYearsByStudentId(Long studentId);

	/**
	 * Retrieve student current academic year , the more recent academic year
	 * for the student.
	 * 
	 * @param studentId
	 *            student id.
	 * @return the student academic year.
	 */
	StudentAcademicYear findStudentCurrentOrMostRecentAcademicYearByStudentId(Long studentId);

}
