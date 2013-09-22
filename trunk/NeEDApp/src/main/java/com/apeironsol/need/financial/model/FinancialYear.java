/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.model;

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

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.framework.BaseEntity;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Entity class for calendar year
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "FINANCIAL_YEAR")
public class FinancialYear extends BaseEntity implements Serializable {

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch				branch;

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

	public String getDisplayLabel() {
		if (this.startDate != null && this.endDate != null) {
			return new SimpleDateFormat("MMM/yyyy").format(this.startDate) + "-"
					+ new SimpleDateFormat("MMM/yyyy").format(this.endDate);
		} else {
			return "";
		}
	}

	@Override
	public void validate() throws InvalidArgumentException {
		Assert.notNull(this.startDate);
		Assert.notNull(this.endDate);

		DateTime startDate = new DateTime(this.startDate.getTime());
		DateTime endDate = new DateTime(this.endDate.getTime());
		DateTime startDatePlusYear = startDate.plusYears(1).minusDays(1);

		if (startDatePlusYear.compareTo(endDate) == 0) {
			throw new InvalidArgumentException("The duration of academic year should be one year.");
		}
	}
}
