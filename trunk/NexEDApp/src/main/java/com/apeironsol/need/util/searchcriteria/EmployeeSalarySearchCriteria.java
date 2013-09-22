/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.util.searchcriteria;

import java.util.Date;

import com.apeironsol.need.core.model.Branch;

/**
 * Singleton class for branch employee search criteria..
 * 
 * @author Pradeep
 */
public class EmployeeSalarySearchCriteria extends EmployeeSearchCriteria {

	/**
	 * Universal serial version id for this class
	 */
	private static final long	serialVersionUID	= -8789474672643380267L;

	/**
	 * Student date of birth.
	 */
	private Date				fromDate;

	/**
	 * Student date of birth.
	 */
	private Date				toDate;

	/**
	 * Student date of birth.
	 */
	private Date				salaryMonth;

	/**
	 * 
	 * @param branch
	 */
	public EmployeeSalarySearchCriteria(final Branch branch) {
		super(branch);
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return this.fromDate;
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(final Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return this.toDate;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(final Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetSeacrhCriteria() {
		this.toDate = null;
		this.fromDate = null;
		this.salaryMonth = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSearchCriteriaIsEmpty() {
		return super.isSearchCriteriaIsEmpty() && this.fromDate == null && this.salaryMonth == null && this.toDate == null;
	}

	/**
	 * @return the salaryMonth
	 */
	public Date getSalaryMonth() {
		return this.salaryMonth;
	}

	/**
	 * @param salaryMonth
	 *            the salaryMonth to set
	 */
	public void setSalaryMonth(final Date salaryMonth) {
		this.salaryMonth = salaryMonth;
	}
}
