/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for academic year expenses.
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "BRANCH_BALANCE_SHEET")
public class BranchBalanceSheet extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class
	 */
	private static final long	serialVersionUID	= 9001376037928822415L;

	@Column(name = "START_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date				startDate;

	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	private Date				endDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch				branch;

	@Column(name = "OPENING_BALANCE", nullable = false)
	private Double				openingBalance;

	@Column(name = "CLOSING_BALANCE")
	private Double				closingBalance;

	@Column(name = "CLOSED_IND", nullable = false)
	private Boolean				balanceSheetClosedIndicator;

	@OneToOne(optional = true)
	@JoinColumn(name = "PREVS_BALANCE_SHEET_ID")
	private BranchBalanceSheet	previousBranchBalanceSheet;

	/**
	 * @return the branch
	 */
	public Branch getBranch() {
		return branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the openingBalance
	 */
	public Double getOpeningBalance() {
		return openingBalance;
	}

	/**
	 * @param openingBalance
	 *            the openingBalance to set
	 */
	public void setOpeningBalance(final Double openingBalance) {
		this.openingBalance = openingBalance;
	}

	/**
	 * @return the closingBalance
	 */
	public Double getClosingBalance() {
		return closingBalance;
	}

	/**
	 * @param closingBalance
	 *            the closingBalance to set
	 */
	public void setClosingBalance(final Double closingBalance) {
		this.closingBalance = closingBalance;
	}

	/**
	 * @return the balanceSheetProcessed
	 */
	public Boolean getBalanceSheetClosedIndicator() {
		return balanceSheetClosedIndicator;
	}

	/**
	 * @param balanceSheetProcessed
	 *            the balanceSheetProcessed to set
	 */
	public void setBalanceSheetClosedIndicator(final Boolean balanceSheetClosedIndicator) {
		this.balanceSheetClosedIndicator = balanceSheetClosedIndicator;
	}

	/**
	 * @return the previousBranchBalanceSheet
	 */
	public BranchBalanceSheet getPreviousBranchBalanceSheet() {
		return previousBranchBalanceSheet;
	}

	/**
	 * @param previousBranchBalanceSheet the previousBranchBalanceSheet to set
	 */
	public void setPreviousBranchBalanceSheet(final BranchBalanceSheet previousBranchBalanceSheet) {
		this.previousBranchBalanceSheet = previousBranchBalanceSheet;
	}

}
