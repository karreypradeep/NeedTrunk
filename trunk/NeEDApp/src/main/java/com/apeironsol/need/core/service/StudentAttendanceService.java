/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;
import com.apeironsol.need.core.dataobject.StudentAttendanceDO;
import com.apeironsol.need.core.dataobject.StudentAttendanceMonthlyDO;
import com.apeironsol.need.core.model.Attendance;
import com.apeironsol.need.core.model.StudentAbsent;

/**
 * Service layer interface for Student Attendance DAO implementation.
 * 
 * @author Pradeep
 * 
 */
public interface StudentAttendanceService {

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
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<StudentAbsent> findStudentAttendanceByScetionIdAndStudentAcademicYearId(Long sectionId, Long studentAcademicYearId) throws BusinessException;

	/**
	 * Find student attendance by section object id and student attendance
	 * object id and for entire month in supplied date.
	 * 
	 * @param sectionId
	 *            section object id.
	 * @param studentAcademicYearId
	 *            student attendance object id.
	 * @return collection of student attendance by section object id and student
	 *         attendance object id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<StudentAbsent> findStudentAttendanceByScetionIdAndStudentAcademicYearIdAndForMonth(Long sectionId, Long studentAcademicYearId, Date month)
			throws BusinessException;

	/**
	 * Retrieve student attendance by object id.
	 * 
	 * @param id
	 *            id of student attendance.
	 * @return student attendance by object id.
	 * @throws BusinessException
	 * @throws BusinessException
	 *             In case of exception.
	 */
	StudentAbsent findStudentAttendanceById(Long id) throws BusinessException;

	/**
	 * Saves supplied student attendance.
	 * 
	 * @param studentAttendance
	 *            student attendance.
	 * @return student attendance.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 */
	StudentAbsent saveStudentAttendance(StudentAbsent studentAttendance) throws BusinessException, InvalidArgumentException;

	/**
	 * Removes student attendance.
	 * 
	 * @param studentAttendance
	 *            student attendance to be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeStudentAttendance(StudentAbsent studentAttendance) throws BusinessException;

	/**
	 * Retrieves all student attendance.
	 * 
	 * @return collection of all student attendance.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<StudentAbsent> findAllStudentAttendances() throws BusinessException;

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
	 * @throws BusinessException
	 *             In case of exception.
	 */
	StudentAbsent findStudentAttendanceByScetionIdAndStudentAcademicYearIdAndForDate(Long sectionId, Long studentAcademicYearId, Date date)
			throws BusinessException;

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
	 * @throws BusinessException
	 *             In case of exception.
	 */
	StudentAbsent findStudentAttendanceByStudentAcademicYearIdAndDate(Long studentAcademicYearId, Date date) throws BusinessException;

	Map<Long, StudentAttendanceDO> getSectionAttendanceDetailsByScetionIdAndDate(Long sectionId, Date date);

	StudentAttendanceDO getStudentAttendanceDetailsByScetionIdAndStudentAcademicYearIdAndDate(Long studentAcademicYearId, Long sectionId, Date date);

	Attendance saveStudentAbsents(final Attendance attendance, final Collection<StudentAttendanceDO> studentAttendanceDOs);

	Map<Long, StudentAttendanceMonthlyDO> getSectionAttendanceDetailsByScetionIdAndFromDateAndToDate(final Long sectionId, final Date fromDate,
			final Date toDate);

	StudentAttendanceMonthlyDO getStudentAttendanceDetailsForEntireMonthByStudentAcademicYearIdAndMonth(Long studentAcademicYearId, Date date);

	Collection<StudentAttendanceMonthlyDO> getStudentAttendanceDetailsForEntireYearByStudentAcademicYearId(Long studentAcademicYearId);

}
