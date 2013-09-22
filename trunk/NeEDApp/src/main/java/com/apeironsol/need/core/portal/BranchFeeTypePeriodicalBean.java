/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.portal;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.core.model.BranchFeeTypePeriodical;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.service.BranchFeeTypePeriodicalService;
import com.apeironsol.need.util.comparator.BranchFeeTypePeriodicalComparator;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.ApplicationException;

@Named
@Scope(value = "session")
public class BranchFeeTypePeriodicalBean extends AbstractPortalBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long					serialVersionUID			= -421105268699170992L;

	/**
	 * Logger for branch fee bean.
	 */
	private static final Logger										log							= Logger.getLogger(BranchFeeTypePeriodicalBean.class);

	/**
	 * Branch fee service resource supplied by spring.
	 */
	@Resource
	private BranchFeeTypePeriodicalService		branchFeeTypePeriodicalService;

	/**
	 * Branch fee type entity.
	 */
	private BranchFeeTypePeriodical				branchFeeTypePeriodical;

	/**
	 * Branch fee type entity.
	 */
	private BranchFeeTypePeriodical				highestBranchFeeTypePeriodical;

	/**
	 * Branch assembly.
	 */
	private BranchAssembly						branchAssembly;

	/**
	 * Branch fee type entity.
	 */
	private Collection<BranchFeeTypePeriodical>	branchFeeTypesForBranchAssembly;

	/**
	 * Building block entity.
	 */
	private BuildingBlock						buildingBlock;

	/**
	 * Indicator to specify if the branch fee types should be loaded form
	 * database.
	 */
	private boolean								loadBranchFeeTypesFromDB	= false;

	/**
	 * Flag to display new branch fee type periodical.
	 */
	private boolean								displayNewBranchFeeType		= false;

	/**
	 * Creates a new branch fee type periodical.
	 * 
	 * @return view path for branch fee.
	 */
	public void newBranchFeeTypePeriodical() {
		this.branchFeeTypePeriodical = new BranchFeeTypePeriodical();
		this.branchFeeTypePeriodical.setBranchAssembly(this.getBranchAssembly());
	}

	/**
	 * Saves the branch fee type.
	 * 
	 * @return null.So that the view remains in same page.
	 */
	public void saveBranchFeeTypePeriodical() {
		try {
			this.branchFeeTypePeriodical.setBranchAssembly(this.getBranchAssembly());
			this.branchFeeTypePeriodical = this.branchFeeTypePeriodicalService
					.saveBranchFeeTypePeriodical(this.branchFeeTypePeriodical);
			ViewUtil.addMessage("Fee type saved sucessfully.", FacesMessage.SEVERITY_INFO);
			this.displayNewBranchFeeType = false;
		} catch (ApplicationException exception) {
			log.info(exception.getMessage());
			ViewExceptionHandler.handle(exception);
		}
		this.loadBranchFeeTypesFromDB = true;
	}

	/**
	 * Removes branch fee type.
	 * 
	 * @return view path for branch fees.
	 */
	public void removeBranchFeeTypePeriodical() {
		try {
			this.branchFeeTypePeriodicalService.removeBranchFeePeriodical(this.branchFeeTypePeriodical);
		} catch (ApplicationException exception) {
			log.info(exception.getMessage());
			ViewExceptionHandler.handle(exception);
		}
		this.loadBranchFeeTypesFromDB = true;
	}

	/**
	 * Removes branch fee type.
	 * 
	 * @return view path for branch fees.
	 */
	public void viewBranchFeeTypePeriodical() {
		this.highestBranchFeeTypePeriodical = this.branchFeeTypePeriodicalService
				.findLatestBranchFeePeriodical(this.branchAssembly);
	}

	/**
	 * Return fee name from building block of the branch assembly.
	 * 
	 * @return
	 */
	public String getBranchFeeTypeName() {
		return this.buildingBlock != null ? this.buildingBlock.getName() : "";
	}

	/**
	 * Retrieves branch assembly for the current branch fee type.
	 * 
	 * @return branch assembly.
	 */
	public BranchAssembly getBranchAssembly() {
		return this.branchAssembly;
	}

	/**
	 * Sets the branch assembly.
	 * 
	 * @param branchAssembly
	 *            branch assembly.
	 */
	public void setBranchAssembly(final BranchAssembly branchAssembly) {
		this.branchAssembly = branchAssembly;
	}

	/**
	 * Loads branch fee types from database.
	 */
	public void loadBranchFeeTypesFromDatabase() {
		try {
			if (this.isLoadBranchFeeTypesFromDB()) {
				this.branchFeeTypesForBranchAssembly = this.branchFeeTypePeriodicalService
						.findBranchFeeTypeByBranchAssembly(this.branchAssembly);

				BranchFeeTypePeriodicalComparator compatator = new BranchFeeTypePeriodicalComparator(
						BranchFeeTypePeriodicalComparator.START_DATE);
				compatator.setDescending();
				Collections.sort((List<BranchFeeTypePeriodical>) this.branchFeeTypesForBranchAssembly, compatator);

				this.highestBranchFeeTypePeriodical = this.branchFeeTypePeriodicalService
						.findLatestBranchFeePeriodical(this.branchAssembly);

				this.loadBranchFeeTypesFromDB = false;
			}
		} catch (ApplicationException ex) {
			log.info(ex.getMessage());
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

	/**
	 * Returns all the branch fee types for the branch assembly.
	 * 
	 * @return all the branch fee types for the branch assembly.
	 */
	public Collection<BranchFeeTypePeriodical> getBranchFeeTypesForBranchAssembly() {
		return this.branchFeeTypesForBranchAssembly;
	}

	/**
	 * Returns true if the branch fee types should be loaded from database.
	 * 
	 * @return true if the branch fee types should be loaded from database.
	 */
	public void setLoadBranchFeeTypesFromDB(final boolean loadBranchFeeTypesFromDB) {
		this.loadBranchFeeTypesFromDB = loadBranchFeeTypesFromDB;
	}

	/**
	 * Returns true if the branch fee types should be loaded from database.
	 * 
	 * @return true if the branch fee types should be loaded from database.
	 */
	public boolean isLoadBranchFeeTypesFromDB() {
		return this.loadBranchFeeTypesFromDB;
	}

	public BuildingBlock getBuildingBlock() {
		return this.buildingBlock;
	}

	public void setBuildingBlock(final BuildingBlock buildingBlock) {
		this.buildingBlock = buildingBlock;
	}

	public boolean isDisplayNewBranchFeeType() {
		return this.displayNewBranchFeeType;
	}

	public void setDisplayNewBranchFeeType(final boolean displayNewBranchFeeType) {
		this.displayNewBranchFeeType = displayNewBranchFeeType;
	}

	public BranchFeeTypePeriodical getBranchFeeTypePeriodical() {
		return this.branchFeeTypePeriodical;
	}

	public void setBranchFeeTypePeriodical(final BranchFeeTypePeriodical branchFeeTypePeriodical) {
		this.branchFeeTypePeriodical = branchFeeTypePeriodical;
	}

	public BranchFeeTypePeriodical getHighestBranchFeeTypePeriodical() {
		return this.highestBranchFeeTypePeriodical;
	}

	public void setHighestBranchFeeTypePeriodical(final BranchFeeTypePeriodical highestBranchFeeTypePeriodical) {
		this.highestBranchFeeTypePeriodical = highestBranchFeeTypePeriodical;
	}

	public boolean isDisablePeriodicalCompoment() {

		if (this.highestBranchFeeTypePeriodical != null && this.branchFeeTypePeriodical != null
				&& this.branchFeeTypePeriodical.getId() != null
				&& !this.branchFeeTypePeriodical.getId().equals(this.highestBranchFeeTypePeriodical.getId())) {
			return true;
		}
		return false;
	}
}
