/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.apeironsol.need.util.constants.WeekDayConstant;
import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for academic year holidays.
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "ACADEMIC_YEAR_WEEK_END", uniqueConstraints = { @UniqueConstraint(columnNames = { "WEEK_END_DATE",
		"ACADEMIC_YEAR_ID" }, name = "UQ_ACADEMIC_YEAR_WEEK_END") })
public class AcademicYearWeekEnd extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class
	 */
	private static final long	serialVersionUID	= -6093768701671976978L;

	@Column(name = "WEEK_END_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date				weekEndDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ACADEMIC_YEAR_ID", nullable = false)
	private AcademicYear		academicYear;

	/**
	 * @return the academicYear
	 */
	public AcademicYear getAcademicYear() {
		return academicYear;
	}

	/**
	 * @param academicYear
	 *            the academicYear to set
	 */
	public void setAcademicYear(final AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	/**
	 * @return the weekEndDate
	 */
	public Date getWeekEndDate() {
		return weekEndDate;
	}

	/**
	 * @param weekEndDate
	 *            the weekEndDate to set
	 */
	public void setWeekEndDate(final Date weekEndDate) {
		this.weekEndDate = weekEndDate;
	}

	public WeekDayConstant getWeekEndDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(weekEndDate);
		return WeekDayConstant.getWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
	}
}
