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
 * Singleton class for branch liability search criteria..
 * 
 * @author Pradeep
 */
public class BranchLiabilitySearchCriteria implements SearchCriteria {

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
	private String						liabilityName;

	private Collection<BuildingBlock>	liabilityTypeBuildingBlocks	= new ArrayList<BuildingBlock>();

	/**
	 * Branch.
	 */
	private Branch						branch;

	/**
	 * 
	 * @param branch
	 */
	public BranchLiabilitySearchCriteria(final Branch branch) {
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
		this.liabilityName = null;
		this.liabilityTypeBuildingBlocks = new ArrayList<BuildingBlock>();
	}

	/**
	 * @return the liabilityName
	 */
	public String getLiabilityName() {
		return this.liabilityName;
	}

	/**
	 * @param liabilityName
	 *            the liabilityName to set
	 */
	public void setLiabilityName(final String liabilityName) {
		this.liabilityName = liabilityName;
	}

	/**
	 * @return the liabilityTypeBuildingBlocks
	 */
	public Collection<BuildingBlock> getLiabilityTypeBuildingBlocks() {
		return this.liabilityTypeBuildingBlocks;
	}

	/**
	 * @param liabilityTypeBuildingBlocks
	 *            the liabilityTypeBuildingBlocks to set
	 */
	public void setLiabilityTypeBuildingBlocks(final Collection<BuildingBlock> assertTypeBuildingBlocks) {
		this.liabilityTypeBuildingBlocks = assertTypeBuildingBlocks;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSearchCriteriaIsEmpty() {
		return (this.liabilityName == null || this.liabilityName.isEmpty())
				&& (this.liabilityTypeBuildingBlocks == null || this.liabilityTypeBuildingBlocks.isEmpty()) && this.fromDate == null && this.toDate == null;
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
