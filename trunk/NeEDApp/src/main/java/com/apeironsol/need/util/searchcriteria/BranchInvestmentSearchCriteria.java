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
 * Singleton class for branch investment search criteria..
 * 
 * @author Pradeep
 */
public class BranchInvestmentSearchCriteria implements SearchCriteria {

	/**
	 * Universal serial version id for this class
	 */
	private static final long			serialVersionUID				= 6070822367619472726L;

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
	private String						investmentName;

	private Collection<BuildingBlock>	investmentTypeBuildingBlocks	= new ArrayList<BuildingBlock>();

	/**
	 * Branch.
	 */
	private Branch						branch;

	/**
	 * 
	 * @param branch
	 */
	public BranchInvestmentSearchCriteria(final Branch branch) {
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
		this.investmentName = null;
		this.investmentTypeBuildingBlocks = new ArrayList<BuildingBlock>();
	}

	/**
	 * @return the investmentName
	 */
	public String getInvestmentName() {
		return this.investmentName;
	}

	/**
	 * @param investmentName
	 *            the investmentName to set
	 */
	public void setInvestmentName(final String investmentName) {
		this.investmentName = investmentName;
	}

	/**
	 * @return the investmentTypeBuildingBlocks
	 */
	public Collection<BuildingBlock> getInvestmentTypeBuildingBlocks() {
		return this.investmentTypeBuildingBlocks;
	}

	/**
	 * @param investmentTypeBuildingBlocks
	 *            the investmentTypeBuildingBlocks to set
	 */
	public void setInvestmentTypeBuildingBlocks(final Collection<BuildingBlock> assertTypeBuildingBlocks) {
		this.investmentTypeBuildingBlocks = assertTypeBuildingBlocks;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSearchCriteriaIsEmpty() {
		return (this.investmentName == null || this.investmentName.isEmpty())
				&& (this.investmentTypeBuildingBlocks == null || this.investmentTypeBuildingBlocks.isEmpty()) && this.fromDate == null && this.toDate == null;
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
