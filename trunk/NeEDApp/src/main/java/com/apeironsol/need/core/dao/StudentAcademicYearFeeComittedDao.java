package com.apeironsol.need.core.dao;

import java.util.Collection;

import com.apeironsol.framework.BaseDao;
import com.apeironsol.need.core.model.StudentAcademicYearFeeComitted;

public interface StudentAcademicYearFeeComittedDao extends BaseDao<StudentAcademicYearFeeComitted> {
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
