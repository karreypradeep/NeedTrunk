package com.apeironsol.need.core.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.apeironsol.need.util.constants.MonthConstant;

/**
 * 
 * @author pradeep
 * 
 */
public class SectionAttendanceReportMonthlyDO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= 808604623033703292L;

	private MonthConstant		monthConstant;

	private int					numberOfPeriods;

	private int					numberOfActiveStudents;

	private int					numberOfStudentAbsents;

	private Date				attendanceStartDate;

	/**
	 * @return the monthConstant
	 */
	public MonthConstant getMonthConstant() {
		return this.monthConstant;
	}

	/**
	 * @param monthConstant
	 *            the monthConstant to set
	 */
	public void setMonthConstant(final MonthConstant monthConstant) {
		this.monthConstant = monthConstant;
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
	 * @return the numberOfActiveStudents
	 */
	public int getNumberOfActiveStudents() {
		return this.numberOfActiveStudents;
	}

	/**
	 * @param numberOfActiveStudents
	 *            the numberOfActiveStudents to set
	 */
	public void setNumberOfActiveStudents(final int numberOfActiveStudents) {
		this.numberOfActiveStudents = numberOfActiveStudents;
	}

	/**
	 * @return the numberOfStudentAbsents
	 */
	public int getNumberOfStudentAbsents() {
		return this.numberOfStudentAbsents;
	}

	/**
	 * @param numberOfStudentAbsents
	 *            the numberOfStudentAbsents to set
	 */
	public void setNumberOfStudentAbsents(final int numberOfStudentAbsents) {
		this.numberOfStudentAbsents = numberOfStudentAbsents;
	}

	/**
	 * @return the attendanceStartDate
	 */
	public Date getAttendanceStartDate() {
		return this.attendanceStartDate;
	}

	/**
	 * @param attendanceStartDate
	 *            the attendanceStartDate to set
	 */
	public void setAttendanceStartDate(final Date attendanceStartDate) {
		this.attendanceStartDate = attendanceStartDate;
	}

}
