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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;
import com.apeironsol.need.core.dao.StudentAbsentDao;
import com.apeironsol.need.core.dataobject.StudentAttendanceDO;
import com.apeironsol.need.core.dataobject.StudentAttendanceMonthlyDO;
import com.apeironsol.need.core.model.Attendance;
import com.apeironsol.need.core.model.StudentAbsent;

/**
 * Service layer implementation for Student Attendance DAO implementation.
 * 
 * @author Pradeep
 *         StudentAttendance
 */
@Service("studentAttendanceService")
@Transactional
public class StudentAttendanceServiceImpl implements StudentAttendanceService {

	@Resource
	private StudentAbsentDao	studentAbsentDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentAbsent> findStudentAttendanceByScetionIdAndStudentAcademicYearId(final Long sectionId, final Long studentAcademicYearId)
			throws BusinessException {
		return this.studentAbsentDAO.findStudentAttendanceByScetionIdAndStudentAcademicYearId(sectionId, studentAcademicYearId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentAbsent> findStudentAttendanceByScetionIdAndStudentAcademicYearIdAndForMonth(final Long sectionId,
			final Long studentAcademicYearId, final Date month) throws BusinessException {
		return this.studentAbsentDAO.findStudentAttendanceByScetionIdAndStudentAcademicYearIdAndForMonth(sectionId, studentAcademicYearId, month);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentAbsent findStudentAttendanceById(final Long id) throws BusinessException {
		return this.studentAbsentDAO.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentAbsent saveStudentAttendance(final StudentAbsent studentAttendance) throws BusinessException, InvalidArgumentException {
		return this.studentAbsentDAO.persist(studentAttendance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeStudentAttendance(final StudentAbsent studentAttendance) throws BusinessException {
		this.studentAbsentDAO.remove(studentAttendance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentAbsent> findAllStudentAttendances() throws BusinessException {
		return this.studentAbsentDAO.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentAbsent findStudentAttendanceByScetionIdAndStudentAcademicYearIdAndForDate(final Long sectionId, final Long studentAcademicYearId,
			final Date date) throws BusinessException {
		return this.studentAbsentDAO.findStudentAttendanceByScetionIdAndStudentAcademicYearIdAndForDate(sectionId, studentAcademicYearId, date);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentAbsent findStudentAttendanceByStudentAcademicYearIdAndDate(final Long studentAcademicYearId, final Date date) throws BusinessException {
		return this.studentAbsentDAO.findStudentAttendanceByStudentAcademicYearIdAndDate(studentAcademicYearId, date);
	}

	@Override
	public Map<Long, StudentAttendanceDO> getSectionAttendanceDetailsByScetionIdAndDate(final Long sectionId, final Date date) {
		return this.studentAbsentDAO.getSectionAttendanceDetailsByScetionIdAndDate(sectionId, date);
	}

	@Override
	public StudentAttendanceDO getStudentAttendanceDetailsByScetionIdAndStudentAcademicYearIdAndDate(final Long studentAcademicYearId, final Long sectionId,
			final Date date) {
		return this.studentAbsentDAO.getStudentAttendanceDetailsByScetionIdAndStudentAcademicYearIdAndDate(studentAcademicYearId, sectionId, date);
	}

	@Override
	public Attendance saveStudentAbsents(final Attendance attendance, final Collection<StudentAttendanceDO> studentAttendanceDOs) {
		return this.studentAbsentDAO.saveStudentAbsents(attendance, studentAttendanceDOs);
	}

	@Override
	public Map<Long, StudentAttendanceMonthlyDO> getSectionAttendanceDetailsByScetionIdAndFromDateAndToDate(final Long sectionId, final Date fromDate,
			final Date toDate) {
		return this.studentAbsentDAO.getSectionAttendanceDetailsByScetionIdAndFromDateAndToDate(sectionId, fromDate, toDate);
	}

	@Override
	public StudentAttendanceMonthlyDO getStudentAttendanceDetailsForEntireMonthByStudentAcademicYearIdAndMonth(final Long studentAcademicYearId, final Date date) {
		return this.studentAbsentDAO.getStudentAttendanceDetailsForEntireMonthByStudentAcademicYearIdAndMonth(studentAcademicYearId, date);
	}

	@Override
	public Collection<StudentAttendanceMonthlyDO> getStudentAttendanceDetailsForEntireYearByStudentAcademicYearId(final Long studentAcademicYearId) {
		return this.studentAbsentDAO.getStudentAttendanceDetailsForEntireYearByStudentAcademicYearId(studentAcademicYearId);
	}

}
