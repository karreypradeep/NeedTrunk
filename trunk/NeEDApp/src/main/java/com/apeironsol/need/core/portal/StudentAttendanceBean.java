/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.core.portal;

/**
 * Managed bean for section attendance.
 * 
 * @author Pradeep
 */
import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Resource;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.dataobject.StudentAttendanceMonthlyDO;
import com.apeironsol.need.core.service.StudentAttendanceService;

@Named
@Scope("session")
public class StudentAttendanceBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long						serialVersionUID	= -7574215985226438727L;

	@Resource
	private StudentBean								studentBean;

	private Collection<StudentAttendanceMonthlyDO>	studentAttendanceMonthlyDOs;

	@Resource
	private StudentAttendanceService				studentAttendanceService;

	public enum AttendanceWizard {

		ATTENDANCE_SUMMARY("attendanceSummary"), ATTENDANCE_REPORT("attendanceReport");

		private String	key;

		AttendanceWizard(final String key) {
			this.key = key;
		}

		public String getKey() {
			return this.key;
		}

		public void setKey(final String key) {
			this.key = key;
		}
	};

	private String	attendanceWizardActiveStep	= AttendanceWizard.ATTENDANCE_REPORT.getKey();

	@Override
	public void onTabChange() {
		this.studentAttendanceMonthlyDOs = this.studentAttendanceService.getStudentAttendanceDetailsForEntireYearByStudentAcademicYearId(this.studentBean
				.getStudentAcademicYear().getId());
	}

	/**
	 * @return the attendanceWizardActiveStep
	 */
	public String getAttendanceWizardActiveStep() {
		return this.attendanceWizardActiveStep;
	}

	/**
	 * @param attendanceWizardActiveStep
	 *            the attendanceWizardActiveStep to set
	 */
	public void setAttendanceWizardActiveStep(final String attendanceWizardActiveStep) {
		this.attendanceWizardActiveStep = attendanceWizardActiveStep;
	}

	/**
	 * @return the studentAttendanceMonthlyDOs
	 */
	public Collection<StudentAttendanceMonthlyDO> getStudentAttendanceMonthlyDOs() {
		return this.studentAttendanceMonthlyDOs;
	}

	/**
	 * @param studentAttendanceMonthlyDOs
	 *            the studentAttendanceMonthlyDOs to set
	 */
	public void setStudentAttendanceMonthlyDOs(final Collection<StudentAttendanceMonthlyDO> studentAttendanceMonthlyDOs) {
		this.studentAttendanceMonthlyDOs = studentAttendanceMonthlyDOs;
	}

}