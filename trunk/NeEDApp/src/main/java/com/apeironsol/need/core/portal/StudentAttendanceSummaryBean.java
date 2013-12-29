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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.dataobject.StudentAttendanceDO;
import com.apeironsol.need.core.dataobject.StudentAttendanceMonthlyDO;
import com.apeironsol.need.core.model.StudentAbsent;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.comparator.StudentAttendanceMonthlyDOComparator;

@Named
@Scope("session")
public class StudentAttendanceSummaryBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long			serialVersionUID	= -8361202043344029517L;

	private final List<ColumnModel>		attendanceColumns	= new ArrayList<ColumnModel>();

	private final List<Integer>			attendanceDates		= new ArrayList<Integer>();

	@Resource
	private StudentAttendanceBean		studentAttendanceBean;

	private StudentAttendanceMonthlyDO	currentStudentAttendanceMonthlyDO;

	private int							currentDateOfMonth;

	/**
	 * Column class used for creating dynamic columns for data table for
	 * attendance.
	 * 
	 * @author pradeep
	 * 
	 */
	static public class ColumnModel implements Serializable {

		/**
		 * Unique serial version id for this class.
		 */
		private static final long					serialVersionUID	= 2756415594765769312L;

		/**
		 * Date of month header.
		 */
		private final String						monthHeader;

		/**
		 * True if the header is column header.
		 */
		private final boolean						dateColumn;

		private final StudentAttendanceMonthlyDO	studentAttendanceMonthlyDO;

		public ColumnModel(final String monthHeader, final boolean dateColumn, final StudentAttendanceMonthlyDO studentAttendanceMonthlyDO) {
			this.monthHeader = monthHeader;
			this.dateColumn = dateColumn;
			this.studentAttendanceMonthlyDO = studentAttendanceMonthlyDO;
		}

		public String getMonthHeader() {
			return this.monthHeader;
		}

		public boolean getDateColumn() {
			return this.dateColumn;
		}

		/**
		 * @return the studentAttendanceMonthlyDO
		 */
		public StudentAttendanceMonthlyDO getStudentAttendanceMonthlyDO() {
			return this.studentAttendanceMonthlyDO;
		}
	}

	@Override
	public void onTabChange() {

	}

	/**
	 * Returns columns for current month to be displayed.
	 * 
	 * @return the attendanceColumns
	 */
	public List<ColumnModel> getAttendanceColumns() {
		return this.attendanceColumns;
	}

	/**
	 * Builds attendance columns for weekly view of current month.
	 */
	public void buildSectionAttendanceColumns() {
		this.getAttendanceColumns().clear();
		ColumnModel column = new ColumnModel("Date", true, null);
		this.getAttendanceColumns().add(column);

		if (this.studentAttendanceBean.getStudentAttendanceMonthlyDOs() != null) {
			StudentAttendanceMonthlyDOComparator comparator = new StudentAttendanceMonthlyDOComparator(
					StudentAttendanceMonthlyDOComparator.Order.ATTENDANCE_MONTH_DATE);
			comparator.setAscending();
			Collections.sort((List<StudentAttendanceMonthlyDO>) this.studentAttendanceBean.getStudentAttendanceMonthlyDOs(), comparator);
			for (StudentAttendanceMonthlyDO studentAttendanceMonthlyDO : this.studentAttendanceBean.getStudentAttendanceMonthlyDOs()) {
				ColumnModel columnNew = new ColumnModel(studentAttendanceMonthlyDO.getMonth().getLabel(), false, studentAttendanceMonthlyDO);
				this.getAttendanceColumns().add(columnNew);
			}
		}
		this.attendanceDates.clear();
		for (int i = 1; i < 32; i++) {
			this.attendanceDates.add(i);
		}

	}

	/**
	 * @return the attendanceDates
	 */
	public List<Integer> getAttendanceDates() {
		return this.attendanceDates;
	}

	/**
	 * Returns style class for attendance present and absent command link.
	 * 
	 * @param student
	 *            student
	 * @param date
	 *            attendance date.
	 * @return style class for attendance present and absent command link.
	 */
	public String getAttendanceClass(final StudentAttendanceMonthlyDO studentAttendanceMonthlyDO, final int date) {
		return "yellow";
	}

	/**
	 * Returns style class for attendance present and absent command link.
	 * 
	 * @param student
	 *            student
	 * @param date
	 *            attendance date.
	 * @return style class for attendance present and absent command link.
	 */
	public boolean isAttendanceTakenForDate(final StudentAttendanceMonthlyDO studentAttendanceMonthlyDO, final int date) {
		return studentAttendanceMonthlyDO.isAttendanceTakenForDate(date - 1);
	}

	/**
	 * Returns style class for attendance present and absent command link.
	 * 
	 * @param student
	 *            student
	 * @param date
	 *            attendance date.
	 * @return style class for attendance present and absent command link.
	 */
	public String getStyleClassForStudent(final StudentAttendanceMonthlyDO studentAttendanceMonthlyDO, final int date) {
		boolean attendanceTaken = studentAttendanceMonthlyDO.isAttendanceTakenForDate(date - 1);
		Calendar suppliedDate = Calendar.getInstance();
		suppliedDate.setTime(studentAttendanceMonthlyDO.getAttendanceMonth().getTime());
		suppliedDate.set(Calendar.DATE, date);
		DateUtil.clearTimeInfo(suppliedDate);
		return !attendanceTaken ? "ui-icon ui-icon-white  ui-icon-note" : studentAttendanceMonthlyDO.getStudentAttendanceDOsByDate()
				.get(suppliedDate.getTime()) == null ? "ui-icon ui-icon-white ui-icon-check" : "ui-icon ui-icon-white ui-icon-close";
	}

	/**
	 * Return attendance status for student on date.
	 * 
	 * @param student
	 *            student.
	 * @param date
	 *            date.
	 * @return
	 */
	public boolean isStudentPresentOnDate(final StudentAttendanceMonthlyDO studentAttendanceMonthlyDO, final int date) {
		Calendar attendanceDate = DateUtil.returnFirstDateOfMonth(studentAttendanceMonthlyDO.getAttendanceMonth());
		attendanceDate.add(Calendar.DATE, date - 1);
		DateUtil.clearTimeInfo(attendanceDate);
		return studentAttendanceMonthlyDO.getStudentAttendanceDOsByDate().get(attendanceDate.getTime()) == null;
	}

	/**
	 * @return the currentStudentAttendanceMonthlyDO
	 */
	public StudentAttendanceMonthlyDO getCurrentStudentAttendanceMonthlyDO() {
		return this.currentStudentAttendanceMonthlyDO;
	}

	/**
	 * @param currentStudentAttendanceMonthlyDO
	 *            the currentStudentAttendanceMonthlyDO to set
	 */
	public void setCurrentStudentAttendanceMonthlyDO(final StudentAttendanceMonthlyDO currentStudentAttendanceMonthlyDO) {
		this.currentStudentAttendanceMonthlyDO = currentStudentAttendanceMonthlyDO;
	}

	/**
	 * @return the currentDateOfMonth
	 */
	public int getCurrentDateOfMonth() {
		return this.currentDateOfMonth;
	}

	/**
	 * @param currentDateOfMonth
	 *            the currentDateOfMonth to set
	 */
	public void setCurrentDateOfMonth(final int currentDateOfMonth) {
		this.currentDateOfMonth = currentDateOfMonth;
	}

	public String getDialogHeader() {
		String header = "Attendance";
		if (this.currentStudentAttendanceMonthlyDO != null) {
			Calendar calendarMonth = this.currentStudentAttendanceMonthlyDO.getAttendanceMonth();
			calendarMonth.set(Calendar.DATE, this.currentDateOfMonth);
			if (!this.isAttendanceTakenForDate(this.currentStudentAttendanceMonthlyDO, this.currentDateOfMonth)) {
				header = "Attendance not taken.";
			}
		}
		return header;
	}

	public java.util.Date getCurrentAttendanceDate() {
		Calendar calendarMonth = this.currentStudentAttendanceMonthlyDO.getAttendanceMonth();
		calendarMonth.set(Calendar.DATE, this.currentDateOfMonth);
		return calendarMonth.getTime();
	}

	public String getAbsentReason() {
		String reason = "NA";
		StudentAttendanceDO currentStudentAttendanceDO = this.currentStudentAttendanceMonthlyDO.getStudentAttendanceDOsByDate().get(
				this.getCurrentAttendanceDate());
		if (currentStudentAttendanceDO != null) {
			if (currentStudentAttendanceDO.getStudentAbsentForDailyAttendance() != null) {
				reason = currentStudentAttendanceDO.getStudentAbsentForDailyAttendance().getAbsentReason();
			} else {
				StudentAbsent studentAbsent = currentStudentAttendanceDO.getStudentAbsentsBySubjectId().values() != null
						&& !currentStudentAttendanceDO.getStudentAbsentsBySubjectId().values().isEmpty() ? currentStudentAttendanceDO
						.getStudentAbsentsBySubjectId().values().iterator().next()
						: currentStudentAttendanceDO.getStudentAbsentsBySubjectId() != null ? currentStudentAttendanceDO.getStudentAbsentsBySubjectId()
								.values().iterator().next() : null;
				reason = studentAbsent != null ? studentAbsent.getAbsentReason() : null;
			}
		}
		return reason;
	}

	public boolean isCurrentAttendanceSubjectWise() {
		boolean result = false;
		if (this.currentStudentAttendanceMonthlyDO != null) {
			StudentAttendanceDO currentStudentAttendanceDO = this.currentStudentAttendanceMonthlyDO.getStudentAttendanceDOsByDate().get(
					this.getCurrentAttendanceDate());
			if (currentStudentAttendanceDO != null) {
				if (currentStudentAttendanceDO.getStudentAbsentForDailyAttendance() != null) {
					result = false;
				} else {
					result = true;
				}
			}
		}
		return result;
	}

	public String getCurrentStudentAllSubjects() {
		String allSubjects = "NA";
		StudentAttendanceDO currentStudentAttendanceDO = this.currentStudentAttendanceMonthlyDO.getStudentAttendanceDOsByDate().get(
				this.getCurrentAttendanceDate());
		if (currentStudentAttendanceDO != null) {
			allSubjects = currentStudentAttendanceDO.getAllSubjectsAsString();
		}
		return allSubjects;
	}

	public String getCurrentStudentSubjectsAbsent() {
		String allSubjects = "NA";
		StudentAttendanceDO currentStudentAttendanceDO = this.currentStudentAttendanceMonthlyDO.getStudentAttendanceDOsByDate().get(
				this.getCurrentAttendanceDate());
		if (currentStudentAttendanceDO != null) {
			allSubjects = currentStudentAttendanceDO.getAbsentSubjectsAsString();
		}
		return allSubjects;
	}
}
