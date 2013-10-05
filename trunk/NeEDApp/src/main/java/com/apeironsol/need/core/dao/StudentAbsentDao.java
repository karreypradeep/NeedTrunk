/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.apeironsol.framework.BaseDao;
import com.apeironsol.need.core.dataobject.SectionAttendanceReportMonthlyDO;
import com.apeironsol.need.core.dataobject.StudentAttendanceDO;
import com.apeironsol.need.core.dataobject.StudentAttendanceMonthlyDO;
import com.apeironsol.need.core.model.Attendance;
import com.apeironsol.need.core.model.StudentAbsent;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
public interface StudentAbsentDao extends BaseDao<StudentAbsent> {

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
	Collection<StudentAbsent> findStudentAttendanceByScetionIdAndStudentAcademicYearId(Long sectionId, Long studentAcademicYearId);

	/**
	 * Find student attendance by section object id and student attendance
	 * object id and for month supplied.
	 * 
	 * @param sectionId
	 *            section object id.
	 * @param studentAcademicYearId
	 *            student attendance object id.
	 * @return collection of student attendance by section object id and student
	 *         attendance object id.
	 */
	Collection<StudentAbsent> findStudentAttendanceByScetionIdAndStudentAcademicYearIdAndForMonth(Long sectionId, Long studentAcademicYearId, Date month);

	/**
	 * Find student attendance by section object id and student attendance
	 * object id and for supplied day.
	 * 
	 * @param sectionId
	 *            section object id.
	 * @param studentAcademicYearId
	 *            student attendance object id.
	 * @param date
	 *            attendance date.
	 * @return collection of student attendance by section object id and student
	 *         attendance object id.
	 */
	StudentAbsent findStudentAttendanceByScetionIdAndStudentAcademicYearIdAndForDate(Long sectionId, Long studentAcademicYearId, Date date);

	/**
	 * Find student attendance by student attendance object id and for supplied
	 * day.
	 * 
	 * @param studentAcademicYearId
	 *            student attendance object id.
	 * @param date
	 *            attendance date.
	 * @return collection of student attendance by section object id and student
	 *         attendance object id.
	 */
	StudentAbsent findStudentAttendanceByStudentAcademicYearIdAndDate(Long studentAcademicYearId, Date date);

	Map<Long, StudentAttendanceDO> getSectionAttendanceDetailsByScetionIdAndDate(Long sectionId, Date date);

	StudentAttendanceDO getStudentAttendanceDetailsByScetionIdAndStudentAcademicYearIdAndDate(Long studentAcademicYearId, Long sectionId, Date date);

	StudentAttendanceMonthlyDO getStudentAttendanceDetailsForEntireMonthByStudentAcademicYearIdAndMonth(Long studentAcademicYearId, Date date);

	void saveStudentAbsents(final Attendance attendance, final Collection<StudentAttendanceDO> studentAttendanceDOs);

	Map<Long, StudentAttendanceMonthlyDO> getSectionAttendanceDetailsByScetionIdAndFromDateAndToDate(final Long sectionId, final Date fromDate,
			final Date toDate);

	SectionAttendanceReportMonthlyDO generateSectionAttendanceReportBetweenDates(final Long sectionId, final Date attendanceStartDate,
			final Date attendanceEndDate);

	Collection<StudentAttendanceMonthlyDO> getStudentAttendanceDetailsForEntireYearByStudentAcademicYearId(Long studentAcademicYearId);

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

	Collection<StudentAbsent> findStudentAttendanceByAttendance(final Attendance attendance);
}
