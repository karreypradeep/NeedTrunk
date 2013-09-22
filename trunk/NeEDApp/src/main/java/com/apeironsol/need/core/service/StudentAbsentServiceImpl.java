/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.StudentAbsentDao;
import com.apeironsol.need.core.model.Attendance;
import com.apeironsol.need.core.model.StudentAbsent;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Service("studentAbsentService")
@Transactional
public class StudentAbsentServiceImpl implements StudentAbsentService {

	@Resource
	StudentAbsentDao	studentAbsentDao;

	@Override
	public StudentAbsent findStudentAttendanceByAttendanceIdAndStudentAcademicYearId(final Long attendanceId, final Long studentAcademicYearId) {
		return this.studentAbsentDao.findStudentAttendanceByAttendanceIdAndStudentAcademicYearId(attendanceId, studentAcademicYearId);
	}

	@Override
	public Collection<StudentAbsent> findStudentAttendanceByAttendancesAndStudentAcademicYearId(final Collection<Attendance> attendances,
			final Long studentAcademicYearId) {
		return this.studentAbsentDao.findStudentAttendanceByAttendancesAndStudentAcademicYearId(attendances, studentAcademicYearId);
	}

	@Override
	public Collection<StudentAbsent> findStudentAttendanceByAttendances(final Collection<Attendance> attendances) {
		return this.studentAbsentDao.findStudentAttendanceByAttendances(attendances);
	}

}
