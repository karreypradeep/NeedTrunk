package com.apeironsol.need.core.dao;

import java.util.Collection;

import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.framework.BaseDao;

public interface StudentAcademicYearDao extends BaseDao<StudentAcademicYear> {

	/**
	 * Find student academic year by student id and academic year id.
	 * 
	 * @param studentId
	 *            student id.
	 * @param academicYearId
	 *            academic year id.
	 * @return student academic year by student id and academic year id.
	 */
	StudentAcademicYear findStudentAcademicYearByStudentIdAndAcademicYearId(final Long studentId, final Long academicYearId);

	/**
	 * Retrieve all student academic years for student.
	 * 
	 * @param studentId
	 *            student id.
	 * @return collection of all student academic years for student.
	 */
	Collection<StudentAcademicYear> findStudentAcademicYearByStudentId(final Long studentId);

	/**
	 * Remove Student Academic Year by student id and Academic Year id.
	 * 
	 * @param studentId
	 *            student id.
	 */
	void removeStudentAcademicYearByStudendIdAndAcademicYearId(final Long studentId, final Long academicYearId);

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
