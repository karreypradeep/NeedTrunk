package com.apeironsol.need.core.dao;

import java.util.Collection;

import com.apeironsol.need.core.model.StudentAcademicYearFeeSummary;
import com.apeironsol.framework.BaseDao;

public interface StudentAcademicYearFeeSummaryDao extends BaseDao<StudentAcademicYearFeeSummary> {
	/**
	 * Find student academic year by student id and academic year id.
	 * 
	 * @param studentId
	 *            student id.
	 * @param academicYearId
	 *            academic year id.
	 * @return student academic year by student id and academic year id.
	 */
	StudentAcademicYearFeeSummary findStudentAcademicYearFeeSummaryByStudentAcademicYearId(final Long studentAcademicYearId);

	/**
	 * Find student academic year by student id and academic year id.
	 * 
	 * @param studentId
	 *            student id.
	 * @param academicYearId
	 *            academic year id.
	 * @return student academic year by student id and academic year id.
	 */
	Collection<StudentAcademicYearFeeSummary> findStudentAcademicYearFeeSummaryByStudentAcademicYearIds(final Collection<Long> studentAcademicYearIds);
}
