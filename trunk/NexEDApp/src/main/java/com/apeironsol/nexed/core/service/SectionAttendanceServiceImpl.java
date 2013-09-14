/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.core.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.nexed.core.dao.AcademicYearDao;
import com.apeironsol.nexed.core.dao.StudentAbsentDao;
import com.apeironsol.nexed.core.dataobject.SectionAttendanceReportMonthlyDO;
import com.apeironsol.nexed.core.model.AcademicYear;
import com.apeironsol.nexed.util.DateUtil;

/**
 * Service layer implementation for Student Attendance DAO implementation.
 * 
 * @author Pradeep
 *         StudentAttendance
 */
@Service("sectionAttendanceService")
@Transactional
public class SectionAttendanceServiceImpl implements SectionAttendanceService {

	@Resource
	private StudentAbsentDao	studentAbsentDAO;

	@Resource
	private AcademicYearDao		academicYearDao;

	@Override
	public SectionAttendanceReportMonthlyDO generateSectionAttendanceReportForMonth(final Long sectionId, final Date monthDate) {
		Date attendanceStartDate = DateUtil.returnFirstDateOfMonth(monthDate);
		Date attendanceEndDate = DateUtil.returnFirstDateOfMonth(monthDate);
		return this.studentAbsentDAO.generateSectionAttendanceReportBetweenDates(sectionId, attendanceStartDate, attendanceEndDate);
	}

	@Override
	public Collection<SectionAttendanceReportMonthlyDO> generateSectionAttendanceReportForAcademicYear(final Long sectionId, final Long academicYearId) {
		// TODO Auto-generated method stub
		Collection<SectionAttendanceReportMonthlyDO> sectionAttendanceReportMonthlyDOs = new ArrayList<SectionAttendanceReportMonthlyDO>();
		AcademicYear academicYear = this.academicYearDao.findById(academicYearId);
		Date attendanceStartDate = DateUtil.returnFirstDateOfMonth(academicYear.getClassStartDate());
		while (!attendanceStartDate.after(academicYear.getEndDate())) {
			SectionAttendanceReportMonthlyDO sectionAttendanceReportMonthlyDO = this.generateSectionAttendanceReportForMonth(sectionId, attendanceStartDate);
			if (sectionAttendanceReportMonthlyDO != null) {
				sectionAttendanceReportMonthlyDOs.add(sectionAttendanceReportMonthlyDO);
			}
			Calendar calStartDate = Calendar.getInstance();
			calStartDate.setTime(attendanceStartDate);
			calStartDate.add(Calendar.MONTH, 1);
			attendanceStartDate = calStartDate.getTime();
		}
		return sectionAttendanceReportMonthlyDOs;
	}
}
