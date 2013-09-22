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
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.dataobject.StudentAttendanceDO;
import com.apeironsol.need.core.dataobject.StudentAttendanceMonthlyDO;
import com.apeironsol.need.core.model.AcademicYearHoliday;
import com.apeironsol.need.core.model.SectionSubject;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.core.service.AcademicYearHolidayService;
import com.apeironsol.need.core.service.StudentAttendanceService;
import com.apeironsol.need.util.DateUtil;

@Named
@Scope("session")
public class SectionAttendanceSummaryBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long						serialVersionUID				= -7354019652222454580L;

	@Resource
	private SectionBean								sectionBean;

	@Resource
	private SectionAttendanceBean					sectionAttendanceBean;

	@Resource
	private StudentAttendanceService				studentAttendanceService;

	@Resource
	private AcademicYearHolidayService				academicYearHolidayService;

	String[]										strMonths						= new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
			"Sep", "Oct", "Nov", "Dec"												};

	String[]										strWeekDays						= new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };

	private final List<ColumnModel>					attendanceColumns				= new ArrayList<ColumnModel>();

	private Calendar								attendanceMonth;

	private Calendar								currentAttendanceDate;

	/**
	 * Number of weeks for current attendance month being displayed.
	 */
	private int										noOfWeeksForAttendanceMonth;

	/**
	 * Current week for current attendance month being displayed.
	 */
	private int										currentWeekForAttendanceMonth	= 1;

	private boolean									loadSectionStudentsAttendance	= true;

	Collection<AcademicYearHoliday>					academicYearHolidays;

	private AcademicYearHoliday						academicYearHoliday;

	private Map<Long, StudentAttendanceMonthlyDO>	studentAttendanceMonthlyDOs;

	private StudentAcademicYear						currentStudentAcademicYear;

	private String									currentAttendanceDateColumn;

	private Collection<SectionSubject>				sectionSubjects;

	/**
	 * @return the loadSectionStudentsAttendance
	 */
	public boolean isLoadSectionStudentsAttendance() {
		return this.loadSectionStudentsAttendance;
	}

	/**
	 * @param loadSectionStudentsAttendance
	 *            the loadSectionStudentsAttendance to set
	 */
	public void setLoadSectionStudentsAttendance(final boolean loadSectionStudentsAttendance) {
		this.loadSectionStudentsAttendance = loadSectionStudentsAttendance;
	}

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
		private static final long	serialVersionUID	= 2756415594765769312L;

		/**
		 * Day of month header.
		 */
		private final String		dayOfWeekHeader;

		/**
		 * Date of month header.
		 */
		private final String		dateOfMonthHeader;

		/**
		 * True if the header is column header.
		 */
		private final boolean		nameColumn;

		public ColumnModel(final String dayOfWeekHeader, final String dateOfMonthHeader, final boolean nameColumn) {
			this.dayOfWeekHeader = dayOfWeekHeader;
			this.dateOfMonthHeader = dateOfMonthHeader;
			this.nameColumn = nameColumn;
		}

		public String getDayOfWeekHeader() {
			return this.dayOfWeekHeader;
		}

		public String getDateOfMonthHeader() {
			return this.dateOfMonthHeader;
		}

		public boolean getNameColumn() {
			return this.nameColumn;
		}
	}

	/**
	 * Increases attendance month by one month.
	 * 
	 * @return month of attendance after increase by one month.
	 */
	public String increaseMonth() {
		this.attendanceMonth.add(Calendar.MONTH, 1);
		this.calculateNoOfWeeksForAttendanceMonth();
		this.loadSectionStudentsAttendance = true;
		this.loadStudentAttendanceDetailsForSection();
		return null;
	}

	/**
	 * Decreases attendance month by one month.
	 * 
	 * @return month of attendance after decrease by one month.
	 */
	public String decreaseMonth() {
		this.attendanceMonth.add(Calendar.MONTH, -1);
		this.calculateNoOfWeeksForAttendanceMonth();
		this.loadSectionStudentsAttendance = true;
		this.loadStudentAttendanceDetailsForSection();
		return null;
	}

	/**
	 * Builds attendance columns for weekly view of current month.
	 */
	public void buildSectionAttendanceColumns() {
		this.getAttendanceColumns().clear();
		ColumnModel column = new ColumnModel("Name", null, true);
		this.getAttendanceColumns().add(column);
		Calendar calendar = (Calendar) this.attendanceMonth.clone();
		// calendar.setTime(forMonth);
		int firstDateOfWeek = DateUtil.getFirstDateOfWeekOfMonth(calendar, this.currentWeekForAttendanceMonth, Calendar.MONDAY);
		int lastDateOfWeek = DateUtil.getLastDateOfWeekOfMonth(calendar, this.currentWeekForAttendanceMonth, Calendar.MONDAY);

		for (int i = firstDateOfWeek; i <= lastDateOfWeek; i++) {
			calendar.set(Calendar.DATE, i);
			ColumnModel columnNew = new ColumnModel(this.strWeekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1], "" + i, false);
			this.getAttendanceColumns().add(columnNew);
		}

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
	 * Return attendance month for display.
	 * 
	 * @return the attendanceMonth for display.
	 */
	public String getAttendanceMonthDisplay() {
		return this.strMonths[this.attendanceMonth.get(Calendar.MONTH)] + " " + this.attendanceMonth.get(Calendar.YEAR);
	}

	/**
	 * Returns true if previous month button is enabled.
	 * 
	 * @return true if previous month button is enabled.
	 */
	public boolean isPreviousMonthForAttendanceVisible() {
		Calendar startDateFirstOfMonth = Calendar.getInstance();
		startDateFirstOfMonth.setTime(this.sectionBean.getSection().getAcademicYear().getClassStartDate());
		return !(this.attendanceMonth.get(Calendar.MONTH) != startDateFirstOfMonth.get(Calendar.MONTH) || this.attendanceMonth.get(Calendar.MONTH) == startDateFirstOfMonth
				.get(Calendar.MONTH) && this.attendanceMonth.get(Calendar.YEAR) != startDateFirstOfMonth.get(Calendar.YEAR));
	}

	/**
	 * Returns true if next month button is enabled.
	 * 
	 * @return true if next month button is enabled.
	 */
	public boolean isNextMonthForAttendanceVisible() {
		Calendar endDateFirstOfMonth = Calendar.getInstance();
		endDateFirstOfMonth.setTime(this.sectionBean.getSection().getAcademicYear().getEndDate());
		return !(this.attendanceMonth.get(Calendar.MONTH) != endDateFirstOfMonth.get(Calendar.MONTH) || this.attendanceMonth.get(Calendar.MONTH) == endDateFirstOfMonth
				.get(Calendar.MONTH) && this.attendanceMonth.get(Calendar.YEAR) != endDateFirstOfMonth.get(Calendar.YEAR));
	}

	/**
	 * Returns number of weeks for attendance month being displayed.
	 * 
	 * @return the noOfWeeksForAttendanceMonth number of weeks for attendance
	 *         month being displayed.
	 */
	public int getNoOfWeeksForAttendanceMonth() {
		return this.noOfWeeksForAttendanceMonth;
	}

	/**
	 * Set number of weeks for attendance month being displayed.
	 * 
	 * @param noOfWeeksForAttendanceMonth
	 *            the number of weeks for attendance month being displayed.
	 */
	public void setNoOfWeeksForAttendanceMonth(final int noOfWeeksForAttendanceMonth) {
		this.noOfWeeksForAttendanceMonth = noOfWeeksForAttendanceMonth;
	}

	/**
	 * Calculates number of weeks for current attendance month being displayed
	 * and builds section attendance columns.
	 */
	private void calculateNoOfWeeksForAttendanceMonth() {
		Calendar calendar = (Calendar) this.attendanceMonth.clone();
		this.noOfWeeksForAttendanceMonth = DateUtil.calculateNumberOfWeeksInMonth(calendar, Calendar.MONDAY);
		this.setCurrentWeekForAttendanceMonth(1);
		this.buildSectionAttendanceColumns();
	}

	/**
	 * Return current week for attendance month.
	 * 
	 * @return the currentWeekForAttendanceMonth current week for attendance
	 *         month.
	 */
	public int getCurrentWeekForAttendanceMonth() {
		return this.currentWeekForAttendanceMonth;
	}

	/**
	 * @param currentWeekForAttendanceMonth
	 *            the currentWeekForAttendanceMonth to set
	 */
	public void setCurrentWeekForAttendanceMonth(final int currentWeekForAttendanceMonth) {
		this.currentWeekForAttendanceMonth = currentWeekForAttendanceMonth;
	}

	/**
	 * Returns true if previous week button is disabled.
	 * 
	 * @return true if previous week button is disabled.
	 */
	public boolean isPreviousWeekForAttendanceVisible() {
		return !(this.currentWeekForAttendanceMonth > 1);
	}

	/**
	 * Returns true if next week button is disabled.
	 * 
	 * @return true if next week button is disabled.
	 */
	public boolean isNextWeekForAttendanceVisible() {
		return !(this.currentWeekForAttendanceMonth < this.noOfWeeksForAttendanceMonth);
	}

	public String increaseWeek() {
		this.currentWeekForAttendanceMonth++;
		this.buildSectionAttendanceColumns();
		return null;
	}

	public String decreaseWeek() {
		this.currentWeekForAttendanceMonth--;
		this.buildSectionAttendanceColumns();
		return null;
	}

	public void loadStudentAttendanceDetailsForSection() {
		this.studentAttendanceMonthlyDOs = this.studentAttendanceService.getSectionAttendanceDetailsByScetionIdAndFromDateAndToDate(this.sectionBean
				.getSection().getId(), DateUtil.returnFirstDateOfMonth(this.attendanceMonth.getTime()), DateUtil.returnLastDateOfMonth(this.attendanceMonth
				.getTime()));
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
	public boolean isStudentPresentOnDate(final StudentAcademicYear studentAcademicYear, final String date) {
		boolean result = true;
		int attDate = Integer.valueOf(date.trim()).intValue();
		Calendar attendanceDate = DateUtil.returnFirstDateOfMonth(this.attendanceMonth);
		attendanceDate.add(Calendar.DATE, attDate - 1);
		DateUtil.clearTimeInfo(attendanceDate);
		if (this.studentAttendanceMonthlyDOs.get(studentAcademicYear.getId()) != null) {
			StudentAttendanceMonthlyDO studentAttendanceMonthlyDO = this.studentAttendanceMonthlyDOs.get(studentAcademicYear.getId());
			if (studentAttendanceMonthlyDO.getStudentAttendanceDOsByDate().get(attendanceDate.getTime()) != null) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * @return the attendanceMonth
	 */
	public Calendar getAttendanceMonth() {
		return this.attendanceMonth;
	}

	/**
	 * @param attendanceMonth
	 *            the attendanceMonth to set
	 */
	public void setAttendanceMonth(final Calendar attendanceMonth) {
		this.attendanceMonth = attendanceMonth;
	}

	@Override
	public void onTabChange() {

		this.attendanceMonth = Calendar.getInstance();
		if (this.sessionBean.getCurrentAcademicYear() != null
				&& this.sectionBean.getSection().getAcademicYear().getId().equals(this.sessionBean.getCurrentAcademicYear().getId())) {
			this.attendanceMonth.setTime(DateUtil.getSystemDate());
		} else {
			this.attendanceMonth.setTime(this.sectionBean.getSection().getAcademicYear().getClassStartDate());
		}
		int todayDate = this.getAttendanceMonth().get(Calendar.DATE);
		this.calculateNoOfWeeksForAttendanceMonth();
		while (this.currentWeekForAttendanceMonth * 7 < todayDate) {
			this.currentWeekForAttendanceMonth++;
		}
		this.loadStudentAttendanceDetailsForSection();
		this.buildSectionAttendanceColumns();
		this.academicYearHolidays = this.academicYearHolidayService.findAcademicYearHolidaysByAcademicYearId(this.sectionBean.getSection().getAcademicYear()
				.getId());
		this.sectionAttendanceBean.setLoadStudentsForSectionFlag(true);
		this.sectionAttendanceBean.setLoadSectionSubjectsFlag(true);
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
	public String getStyleClassForStudent(final StudentAcademicYear studentAcademicYear, final String date) {
		int attDate = Integer.valueOf(date.trim()).intValue();
		Calendar suppliedDate = Calendar.getInstance();
		suppliedDate.setTime(this.getAttendanceMonth().getTime());
		suppliedDate.set(Calendar.DATE, attDate);
		DateUtil.clearTimeInfo(suppliedDate);
		return this.isWeekEnd(suppliedDate) || this.isHoliday(suppliedDate) ? "ui-icon ui-icon-white  ui-icon-note" : this.isStudentPresentOnDate(
				studentAcademicYear, date) ? "ui-icon ui-icon-white ui-icon-check" : "ui-icon ui-icon-white ui-icon-close";
	}

	/**
	 * Return true if date supplied is week end.
	 * 
	 * @param date
	 *            date for which week end has to be checked.
	 * @return true if date supplied is week end.
	 */
	public boolean isWeekEnd(final String date) {
		int attDate = Integer.valueOf(date.trim()).intValue();
		Calendar suppliedDate = Calendar.getInstance();
		suppliedDate.setTime(this.getAttendanceMonth().getTime());
		suppliedDate.set(Calendar.DATE, attDate);
		DateUtil.clearTimeInfo(suppliedDate);
		return this.isWeekEnd(suppliedDate);
	}

	/**
	 * Return true if date supplied is week end.
	 * 
	 * @param suppliedDate
	 *            date for which week end has to be checked.
	 * @return
	 */
	private boolean isWeekEnd(final Calendar suppliedDate) {
		boolean result = false;
		if (suppliedDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || suppliedDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			result = true;
		}
		return result;
	}

	/**
	 * Return true if date supplied is holiday.
	 * 
	 * @param date
	 *            date for which holiday has to be checked.
	 * @return
	 */
	public boolean isHoliday(final String date) {
		int attDate = Integer.valueOf(date.trim()).intValue();
		Calendar suppliedDate = Calendar.getInstance();
		suppliedDate.setTime(this.getAttendanceMonth().getTime());
		suppliedDate.set(Calendar.DATE, attDate);
		DateUtil.clearTimeInfo(suppliedDate);
		return this.isHoliday(suppliedDate);
	}

	/**
	 * Return true if date supplied is holiday.
	 * 
	 * @param suppliedDate
	 *            date for which holiday has to be checked.
	 * @return true if date supplied is holiday.
	 */
	private boolean isHoliday(final Calendar suppliedDate) {
		boolean result = false;
		for (AcademicYearHoliday academicYearHoliday : this.academicYearHolidays) {
			Calendar academicYearHolidayStartDate = Calendar.getInstance();
			academicYearHolidayStartDate.setTime(academicYearHoliday.getStartDate());
			DateUtil.clearTimeInfo(academicYearHolidayStartDate);
			Calendar academicYearHolidayEndDate = Calendar.getInstance();
			academicYearHolidayEndDate.setTime(academicYearHoliday.getEndDate());
			DateUtil.clearTimeInfo(academicYearHolidayEndDate);
			if (suppliedDate.equals(academicYearHolidayStartDate) || suppliedDate.equals(academicYearHolidayEndDate)
					|| suppliedDate.after(academicYearHolidayStartDate) && suppliedDate.before(academicYearHolidayEndDate)) {
				this.academicYearHoliday = academicYearHoliday;
				result = true;
			}
		}
		return result;
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
	public String getStyleClassForAttendanceColumn(final String date) {
		int dayOfMonth = Integer.valueOf(date.trim()).intValue();
		Calendar suppliedDate = Calendar.getInstance();
		suppliedDate.setTime(this.getAttendanceMonth().getTime());
		suppliedDate.set(Calendar.DATE, dayOfMonth);
		DateUtil.clearTimeInfo(suppliedDate);
		String styleClass = "";
		if (this.isWeekEnd(suppliedDate)) {
			styleClass = "yellow";
		} else if (this.isHoliday(suppliedDate)) {
			styleClass = "LightSalmon";
		}
		return styleClass;
	}

	/**
	 * Return tool tip for attendance. If attendance is allowed then tool tip
	 * will be student name concatenated with date and day of week. If it is
	 * week end then Weekend is returned or else then holiday description is
	 * returned.
	 * 
	 * @param student
	 *            student.
	 * @param column
	 *            column model.
	 * @return
	 */
	public String getToolTipForAttendance(final Student student, final ColumnModel column) {
		String toolTip = "";
		int dayOfMonth = Integer.valueOf(column.getDateOfMonthHeader().trim()).intValue();
		Calendar suppliedDate = Calendar.getInstance();
		suppliedDate.setTime(this.getAttendanceMonth().getTime());
		suppliedDate.set(Calendar.DATE, dayOfMonth);
		DateUtil.clearTimeInfo(suppliedDate);
		if (this.isWeekEnd(suppliedDate)) {
			toolTip = "Week end";
		} else if (this.isHoliday(suppliedDate)) {
			toolTip = this.academicYearHoliday.getDescription();
		} else {
			toolTip = student.getDisplayName() + " " + column.getDayOfWeekHeader() + " " + column.getDateOfMonthHeader();
		}
		return toolTip;
	}

	/**
	 * @return the studentAttendanceDOs
	 */
	public Map<Long, StudentAttendanceMonthlyDO> getStudentAttendanceMonthlyDOs() {
		return this.studentAttendanceMonthlyDOs;
	}

	/**
	 * @param studentAttendanceDOs
	 *            the studentAttendanceDOs to set
	 */
	public void setStudentAttendanceMonthlyDOs(final Map<Long, StudentAttendanceMonthlyDO> studentAttendanceMonthlyDOs) {
		this.studentAttendanceMonthlyDOs = studentAttendanceMonthlyDOs;
	}

	/**
	 * @return the currentStudentAcademicYear
	 */
	public StudentAcademicYear getCurrentStudentAcademicYear() {
		return this.currentStudentAcademicYear;
	}

	/**
	 * @param currentStudentAcademicYear
	 *            the currentStudentAcademicYear to set
	 */
	public void setCurrentStudentAcademicYear(final StudentAcademicYear currentStudentAcademicYear) {
		this.currentStudentAcademicYear = currentStudentAcademicYear;
	}

	public String getCurrentStudentSubjectsPresent() {
		String result = this.getCurrentStudentAllSubjects();
		StudentAttendanceMonthlyDO currentStudentAttendanceMonthlyDO = this.getStudentAttendanceMonthlyDOs().get(this.currentStudentAcademicYear.getId());
		if (currentStudentAttendanceMonthlyDO != null) {
			StudentAttendanceDO studentAttendanceDO = currentStudentAttendanceMonthlyDO.getStudentAttendanceDOsByDate().get(
					this.getCurrentAttendanceDate().getTime());
			if (studentAttendanceDO != null) {
				result = studentAttendanceDO.getPresentSubjectsAsString();
			}
		}
		return result;
	}

	public String getCurrentStudentSubjectsAbsent() {
		String result = "NA";
		StudentAttendanceMonthlyDO currentStudentAttendanceMonthlyDO = this.getStudentAttendanceMonthlyDOs().get(this.currentStudentAcademicYear.getId());
		if (currentStudentAttendanceMonthlyDO != null) {
			StudentAttendanceDO studentAttendanceDO = currentStudentAttendanceMonthlyDO.getStudentAttendanceDOsByDate().get(
					this.getCurrentAttendanceDate().getTime());
			if (studentAttendanceDO != null) {
				result = studentAttendanceDO.getAbsentSubjectsAsString();
			}
		}
		return result;
	}

	public String getCurrentStudentAllSubjects() {
		String result = "NA";
		if (this.sectionSubjects == null || this.sectionSubjects.isEmpty()) {
			this.sectionSubjects = this.sectionAttendanceBean.getSectionSubjects();
		}
		if (this.sectionSubjects != null && !this.sectionSubjects.isEmpty()) {
			int noOfSubjects = this.sectionSubjects.size();
			int counter = 0;
			result = "";
			for (SectionSubject sectionSubject : this.sectionSubjects) {
				counter++;
				result += sectionSubject.getSubject().getName();
				if (counter < noOfSubjects) {
					result += ", ";
				}
			}
		}
		return result.trim().length() == 0 ? "NA" : result;
	}

	/**
	 * @return the currentAttendanceDate
	 */
	public Calendar getCurrentAttendanceDate() {
		int dayOfMonth = Integer.valueOf(this.currentAttendanceDateColumn.trim()).intValue();
		this.currentAttendanceDate = Calendar.getInstance();
		this.currentAttendanceDate.setTime(this.getAttendanceMonth().getTime());
		this.currentAttendanceDate.set(Calendar.DATE, dayOfMonth);
		DateUtil.clearTimeInfo(this.currentAttendanceDate);
		return this.currentAttendanceDate;
	}

	/**
	 * @param currentAttendanceDate
	 *            the currentAttendanceDate to set
	 */
	public void setCurrentAttendanceDate(final Calendar currentAttendanceDate) {
		this.currentAttendanceDate = currentAttendanceDate;
	}

	/**
	 * @return the currentAttendanceDateColumn
	 */
	public String getCurrentAttendanceDateColumn() {
		return this.currentAttendanceDateColumn;
	}

	/**
	 * @param currentAttendanceDateColumn
	 *            the currentAttendanceDateColumn to set
	 */
	public void setCurrentAttendanceDateColumn(final String currentAttendanceDateColumn) {
		this.currentAttendanceDateColumn = currentAttendanceDateColumn;
	}

	/**
	 * @return the sectionSubjects
	 */
	public Collection<SectionSubject> getSectionSubjects() {
		return this.sectionSubjects;
	}

	/**
	 * @param sectionSubjects
	 *            the sectionSubjects to set
	 */
	public void setSectionSubjects(final Collection<SectionSubject> sectionSubjects) {
		this.sectionSubjects = sectionSubjects;
	}
}
