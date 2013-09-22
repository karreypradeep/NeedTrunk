/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.util.searchcriteria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.BuildingBlock;

/**
 * Singleton class for branch expense search criteria..
 * 
 * @author Pradeep
 */
public class BranchExpenseSearchCriteria implements SearchCriteria {

	/**
	 * Universal serial version id for this class
	 */
	private static final long			serialVersionUID			= 6070822367619472726L;

	/**
	 * Searched branch liabilities with assert date after from date, including
	 * from
	 * date.
	 */
	private Date						fromDate;

	/**
	 * Searched branch liabilities with assert date before to date, including to
	 * date.
	 */
	private Date						toDate;

	/**
	 * Name of assert.
	 */
	private String						expenseName;

	private Collection<BuildingBlock>	expenseTypeBuildingBlocks	= new ArrayList<BuildingBlock>();

	/**
	 * Branch.
	 */
	private Branch						branch;

	/**
	 * 
	 * @param branch
	 */
	public BranchExpenseSearchCriteria(final Branch branch) {
		this.setBranch(branch);
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
		this.fromDate = null;
		this.toDate = null;
		this.expenseName = null;
		this.expenseTypeBuildingBlocks = new ArrayList<BuildingBlock>();
	}

	/**
	 * @return the expenseName
	 */
	public String getExpenseName() {
		return this.expenseName;
	}

	/**
	 * @param expenseName
	 *            the expenseName to set
	 */
	public void setExpenseName(final String expenseName) {
		this.expenseName = expenseName;
	}

	/**
	 * @return the expenseTypeBuildingBlocks
	 */
	public Collection<BuildingBlock> getExpenseTypeBuildingBlocks() {
		return this.expenseTypeBuildingBlocks;
	}

	/**
	 * @param expenseTypeBuildingBlocks
	 *            the expenseTypeBuildingBlocks to set
	 */
	public void setExpenseTypeBuildingBlocks(final Collection<BuildingBlock> assertTypeBuildingBlocks) {
		this.expenseTypeBuildingBlocks = assertTypeBuildingBlocks;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSearchCriteriaIsEmpty() {
		return (this.expenseName == null || this.expenseName.isEmpty()) && (this.expenseTypeBuildingBlocks == null || this.expenseTypeBuildingBlocks.isEmpty())
				&& this.fromDate == null && this.toDate == null;
	}

	/**
	 * @return the branch
	 */
	@Override
	public Branch getBranch() {
		return this.branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

}
