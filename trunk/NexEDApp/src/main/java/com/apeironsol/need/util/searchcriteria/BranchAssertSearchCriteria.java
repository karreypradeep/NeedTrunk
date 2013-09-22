/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.searchcriteria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.BuildingBlock;

/**
 * Singleton class for branch assert search criteria..
 * 
 * @author Pradeep
 */
public class BranchAssertSearchCriteria implements SearchCriteria {

	/**
	 * Universal serial version id for this class
	 */
	private static final long			serialVersionUID			= 6070822367619472726L;

	/**
	 * Searched branch asserts with assert date after from date, including from
	 * date.
	 */
	private Date						fromDate;

	/**
	 * Searched branch asserts with assert date before to date, including to
	 * date.
	 */
	private Date						toDate;

	/**
	 * Name of assert.
	 */
	private String						assertName;

	private Collection<BuildingBlock>	assertTypeBuildingBlocks	= new ArrayList<BuildingBlock>();

	/**
	 * Branch.
	 */
	private Branch						branch;

	/**
	 * 
	 * @param branch
	 */
	public BranchAssertSearchCriteria(final Branch branch) {
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
		this.assertName = null;
		this.assertTypeBuildingBlocks = new ArrayList<BuildingBlock>();
	}

	/**
	 * @return the assertName
	 */
	public String getAssertName() {
		return this.assertName;
	}

	/**
	 * @param assertName
	 *            the assertName to set
	 */
	public void setAssertName(final String assertName) {
		this.assertName = assertName;
	}

	/**
	 * @return the assertTypeBuildingBlocks
	 */
	public Collection<BuildingBlock> getAssertTypeBuildingBlocks() {
		return this.assertTypeBuildingBlocks;
	}

	/**
	 * @param assertTypeBuildingBlocks
	 *            the assertTypeBuildingBlocks to set
	 */
	public void setAssertTypeBuildingBlocks(final Collection<BuildingBlock> assertTypeBuildingBlocks) {
		this.assertTypeBuildingBlocks = assertTypeBuildingBlocks;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSearchCriteriaIsEmpty() {
		return (this.assertName == null || this.assertName.isEmpty()) && (this.assertTypeBuildingBlocks == null || this.assertTypeBuildingBlocks.isEmpty())
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
