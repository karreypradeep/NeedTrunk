/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.apeironsol.framework.BaseEntity;


/**
 * Entity class for academic year holidays.
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "ACADEMIC_YEAR_HOLIDAY")
public class AcademicYearHoliday extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class
	 */
	private static final long	serialVersionUID	= -3436528969299305658L;

	@NotNull(message = "model.holiday_start_date_mandatory")
	@Column(name = "START_DATE", nullable = false)
	private Date				startDate;

	@NotNull(message = "model.holiday_end_date_mandatory")
	@Column(name = "END_DATE", nullable = false)
	private Date				endDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ACADEMIC_YEAR_ID", nullable = false)
	private AcademicYear		academicYear;

	@Column(name = "DESCRIPTION", length = 200)
	private String				description;

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the academicYear
	 */
	public AcademicYear getAcademicYear() {
		return this.academicYear;
	}

	/**
	 * @param academicYear
	 *            the academicYear to set
	 */
	public void setAcademicYear(final AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

}
