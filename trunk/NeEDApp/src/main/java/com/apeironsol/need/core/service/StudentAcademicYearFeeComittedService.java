/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.need.core.model.StudentAcademicYearFeeComitted;

/**
 * Service interface for student academic year.
 * 
 * @author Pradeep
 * 
 */
public interface StudentAcademicYearFeeComittedService {

	/**
	 * Find student academic yearby by id.
	 * 
	 * @param studentId
	 *            student id.
	 * @param academicYearId
	 *            academic year id.
	 * @return student academic year by student id and academic year id.
	 */
	StudentAcademicYearFeeComitted findStudentAcademicYearFeeComittedById(final Long id);

	/**
	 * Find student academic year by student id and academic year id.
	 * 
	 * @param studentId
	 *            student id.
	 * @param academicYearId
	 *            academic year id.
	 * @return student academic year by student id and academic year id.
	 */
	StudentAcademicYearFeeComitted saveStudentAcademicYearFeeComitted(final StudentAcademicYearFeeComitted studentAcademicYearFeeComitted);

	/**
	 * Find student academic year by student id and academic year id.
	 * 
	 * @param studentId
	 *            student id.
	 * @param academicYearId
	 *            academic year id.
	 * @return student academic year by student id and academic year id.
	 */
	Collection<StudentAcademicYearFeeComitted> saveStudentAcademicYearFeeComitted(
			final Collection<StudentAcademicYearFeeComitted> studentAcademicYearFeeComitted);

	/**
	 * Find student academic year by student id and academic year id.
	 * 
	 * @param studentId
	 *            student id.
	 * @param academicYearId
	 *            academic year id.
	 * @return student academic year by student id and academic year id.
	 */
	StudentAcademicYearFeeComitted findStudentAcademicYearFeeComittedByStudentIdAndAcademicYearId(final Long studentId, final Long academicYearId);

	/**
	 * Find student academic year by student id and academic year id.
	 * 
	 * @param studentId
	 *            student id.
	 * @param academicYearId
	 *            academic year id.
	 * @return student academic year by student id and academic year id.
	 */
	Collection<StudentAcademicYearFeeComitted> findStudentAcademicYearFeeComittedByStudentId(final Long studentId);

	/**
	 * Find student academic year by student id and academic year id.
	 * 
	 * @param studentId
	 *            student id.
	 * @param academicYearId
	 *            academic year id.
	 * @return student academic year by student id and academic year id.
	 */
	Collection<StudentAcademicYearFeeComitted> findStudentAcademicYearFeeComittedByStudentIds(final Long studentIds);

}
