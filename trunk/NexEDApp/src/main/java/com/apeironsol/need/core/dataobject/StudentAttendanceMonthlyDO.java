package com.apeironsol.need.core.dataobject;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.apeironsol.need.core.model.SectionSubject;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.util.constants.MonthConstant;

/**
 * 
 * @author pradeep
 * 
 */
public class StudentAttendanceMonthlyDO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long						serialVersionUID			= -2616258498887137032L;

	private Collection<SectionSubject>				sectionSubjects;

	private StudentAcademicYear						studentAcademicYear;

	private final Map<Date, StudentAttendanceDO>	studentAttendanceDOsByDate	= new HashMap<Date, StudentAttendanceDO>();

	private MonthConstant							month;

	private Calendar								attendanceMonth;

	private int										numberOfPeriods;

	private final boolean							attendanceTaken[]			= new boolean[31];

	/**
	 * @return the studentAcademicYear
	 */
	public StudentAcademicYear getStudentAcademicYear() {
		return this.studentAcademicYear;
	}

	/**
	 * @param studentAcademicYear
	 *            the studentAcademicYear to set
	 */
	public void setStudentAcademicYear(final StudentAcademicYear studentAcademicYear) {
		this.studentAcademicYear = studentAcademicYear;
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

	public int getNumberOfPeriodsForADay() {
		return this.sectionSubjects != null ? this.sectionSubjects.size() : 0;
	}

	/**
	 * @return the studentAbsentsByDate
	 */
	public Map<Date, StudentAttendanceDO> getStudentAttendanceDOsByDate() {
		return this.studentAttendanceDOsByDate;
	}

	/**
	 * @return the month
	 */
	public MonthConstant getMonth() {
		return this.month;
	}

	/**
	 * @param month
	 *            the month to set
	 */
	public void setMonth(final MonthConstant month) {
		this.month = month;
	}

	/**
	 * @return the numberOfPeriods
	 */
	public int getNumberOfPeriods() {
		return this.numberOfPeriods;
	}

	/**
	 * @param numberOfPeriods
	 *            the numberOfPeriods to set
	 */
	public void setNumberOfPeriods(final int numberOfPeriods) {
		this.numberOfPeriods = numberOfPeriods;
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

	public boolean isAttendanceTakenForDate(final int dateOfMonth) {
		return this.getAttendanceTaken()[dateOfMonth];
	}

	/**
	 * @return the attendanceTaken
	 */
	public boolean[] getAttendanceTaken() {
		return this.attendanceTaken;
	}

	/**
	 * @return the attendanceTaken
	 */
	public void setAttendanceTaken(final int attendanceDate) {
		if (attendanceDate < 31) {
			this.attendanceTaken[attendanceDate] = true;
		}
	}

	public int getNumberOfAbsentsForMonth() {
		int noOfAbsents = 0;
		for (StudentAttendanceDO studentAttendanceDO : this.getStudentAttendanceDOsByDate().values()) {
			noOfAbsents += studentAttendanceDO.getStudentAbsentForDailyAttendance() == null ? studentAttendanceDO.getStudentAbsentsBySubjectId().values()
					.size() : 1;
		}
		return noOfAbsents;
	}
}
