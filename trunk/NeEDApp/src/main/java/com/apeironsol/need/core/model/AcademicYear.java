/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.springframework.util.Assert;

import com.apeironsol.need.util.DateUtil;
import com.apeironsol.framework.BaseEntity;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Entity class for Academic Year
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "ACADEMIC_YEAR")
public class AcademicYear extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class
	 */
	private static final long	serialVersionUID	= -3436528969299305658L;

	@NotNull(message = "model.start_date_mandatory")
	@Column(name = "START_DATE", nullable = false)
	private Date				startDate;

	@NotNull(message = "model.end_date_mandatory")
	@Column(name = "END_DATE", nullable = false)
	private Date				endDate;

	@NotNull(message = "model.admissions_start_date_mandatory")
	@Column(name = "ADMISSION_START_DATE", nullable = false)
	private Date				admissionStartDate;

	@NotNull(message = "model.admissions_end_date_mandatory")
	@Column(name = "ADMISSION_END_DATE", nullable = false)
	private Date				admissionEndDate;

	@NotNull(message = "model.class_start_date_mandatory")
	@Column(name = "CLASS_START_DATE", nullable = false)
	private Date				classStartDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch				branch;

	@Column(name = "ACTIVE")
	private boolean				active;

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

	public Branch getBranch() {
		return this.branch;
	}

	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	/**
	 * Returns number of days between start date and end date. If either of
	 * start date or end date is null then zero is returned.
	 * 
	 * @return
	 */
	public int getDays() {
		int noOfDays = 0;
		if (this.startDate != null && this.endDate != null) {

			noOfDays = DateUtil.dateDiffInDays(this.startDate, this.endDate);
		}
		return noOfDays;
	}

	public int getMonths() {
		int noOfMonths = 0;
		if (this.startDate != null && this.endDate != null) {
			noOfMonths = DateUtil.dateDiffInMonths(this.startDate, this.endDate);
		}
		return noOfMonths;
	}

	public int getWeeks() {
		int noOfWeeks = 0;
		if (this.startDate != null && this.endDate != null) {
			noOfWeeks = DateUtil.dateDiffInWeeks(this.startDate, this.endDate);
		}
		return noOfWeeks;
	}

	public String getDisplayLabel() {
		if (this.startDate != null && this.endDate != null) {
			return new SimpleDateFormat("yyyy(MMM)").format(this.startDate) + "----" + new SimpleDateFormat("yyyy(MMM)").format(this.endDate);
		} else {
			return "";
		}
	}

	@Override
	public void validate() throws InvalidArgumentException {
		Assert.notNull(this.startDate);
		Assert.notNull(this.endDate);

		Assert.notNull(this.admissionStartDate);
		Assert.notNull(this.admissionEndDate);

		DateTime startDate = new DateTime(this.startDate.getTime());
		DateTime endDate = new DateTime(this.endDate.getTime());
		DateTime startDatePlusYear = startDate.plusYears(1).minusDays(1);

		if (startDatePlusYear.compareTo(endDate) < 0) {
			throw new InvalidArgumentException("The duration of academic year should not be greater then an year.");
		}

		if (this.admissionStartDate.compareTo(this.admissionEndDate) > 0) {
			throw new InvalidArgumentException("Admissions start date should be before admissins end date.");
		}

		if (this.admissionEndDate.compareTo(this.endDate) > 0) {
			throw new InvalidArgumentException("Admissions end date should be before academic year end date.");
		}
	}

	public Date getAdmissionStartDate() {
		return this.admissionStartDate;
	}

	public void setAdmissionStartDate(final Date admissionStartDate) {
		this.admissionStartDate = admissionStartDate;
	}

	public Date getAdmissionEndDate() {
		return this.admissionEndDate;
	}

	public void setAdmissionEndDate(final Date admissionEndDate) {
		this.admissionEndDate = admissionEndDate;
	}

	public boolean isAdmissionsOpen() {
		if (this.admissionStartDate.compareTo(DateUtil.getSystemDate()) <= 0 && this.admissionEndDate.compareTo(DateUtil.getSystemDate()) >= 0) {
			return true;
		}
		return false;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(final boolean active) {
		this.active = active;
	}

	/**
	 * @return the classStartDate
	 */
	public Date getClassStartDate() {
		return this.classStartDate;
	}

	/**
	 * @param classStartDate
	 *            the classStartDate to set
	 */
	public void setClassStartDate(final Date classStartDate) {
		this.classStartDate = classStartDate;
	}
}
