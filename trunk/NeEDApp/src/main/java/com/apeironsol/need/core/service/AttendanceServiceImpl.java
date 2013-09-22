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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.AttendanceDao;
import com.apeironsol.need.core.model.Attendance;
import com.apeironsol.need.core.model.Section;

/**
 * Service layer implementation for Student Attendance DAO implementation.
 * 
 * @author Pradeep
 *         StudentAttendance
 */
@Service("attendanceService")
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

	@Resource
	private AttendanceDao	attendanceDao;

	@Override
	public Attendance findAttendanceBySectionSubjectIdAndAttendanceDate(final Long sectionSubjectId, final Date attendanceDate) {
		return this.attendanceDao.findAttendanceBySectionSubjectIdAndAttendanceDate(sectionSubjectId, attendanceDate);
	}

	@Override
	public Attendance findAttendanceBySectionIdAndAttendanceDateForDailyAttendance(final Long sectionId, final Date attendanceDate) {
		return this.attendanceDao.findAttendanceBySectionIdAndAttendanceDateForDailyAttendance(sectionId, attendanceDate);
	}

	@Override
	public Collection<Attendance> findAttendancesBySectionId(final Long sectionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Attendance> findAttendancesBySectionIdAndAttendanceDate(final Long sectionId, final Date attendanceDate) {
		return this.attendanceDao.findAttendancesBySectionIdAndAttendanceDate(sectionId, attendanceDate);
	}

	@Override
	public Collection<Attendance> findAttendancesBySectionsAndAttendanceDate(final Collection<Section> sections, final Date attendanceDate) {
		return this.attendanceDao.findAttendancesBySectionsAndAttendanceDate(sections, attendanceDate);
	}

}
