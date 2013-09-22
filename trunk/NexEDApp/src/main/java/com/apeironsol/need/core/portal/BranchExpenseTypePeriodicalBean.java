/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.portal;

import java.util.Collection;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.core.model.BranchExpenseTypePeriodical;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.service.BranchExpenseTypePeriodicalService;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.ApplicationException;

@Named
@Scope(value = "session")
public class BranchExpenseTypePeriodicalBean extends AbstractPortalBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long						serialVersionUID				= -5264199044480163913L;

	/**
	 * Logger for branch expense bean.
	 */
	private static final Logger											log								= Logger.getLogger(BranchExpenseTypePeriodicalBean.class);

	/**
	 * Branch expense service resource supplied by spring.
	 */
	@Resource
	private BranchExpenseTypePeriodicalService		branchExpenseTypePeriodicalService;

	/**
	 * Branch expense type entity.
	 */
	private BranchExpenseTypePeriodical				branchExpenseTypePeriodical;

	/**
	 * Branch expense type entity.
	 */
	private BranchExpenseTypePeriodical				highestBranchExpenseTypePeriodical;

	/**
	 * Branch assembly.
	 */
	private BranchAssembly							branchAssembly;

	/**
	 * Branch expense type entity.
	 */
	private Collection<BranchExpenseTypePeriodical>	branchExpenseTypesForBranchAssembly;

	/**
	 * Building block entity.
	 */
	private BuildingBlock							buildingBlock;

	/**
	 * Indicator to specify if the branch expense types should be loaded form
	 * database.
	 */
	private boolean									loadBranchExpenseTypesFromDB	= false;

	/**
	 * Flag to display new branch expense type periodical.
	 */
	private boolean									displayNewBranchExpenseType		= false;

	/**
	 * Creates a new branch expense type periodical.
	 * 
	 * @return view path for branch expense.
	 */
	public void newBranchExpenseTypePeriodical() {
		this.branchExpenseTypePeriodical = new BranchExpenseTypePeriodical();
		this.branchExpenseTypePeriodical.setBranchAssembly(this.getBranchAssembly());
	}

	/**
	 * Saves the branch expense type.
	 * 
	 * @return null.So that the view remains in same page.
	 */
	public void saveBranchExpenseTypePeriodical() {
		try {
			this.branchExpenseTypePeriodical.setBranchAssembly(this.getBranchAssembly());
			this.branchExpenseTypePeriodical = this.branchExpenseTypePeriodicalService
					.saveBranchExpenseTypePeriodical(this.branchExpenseTypePeriodical);
			ViewUtil.addMessage("Expense type saved sucessfully.", FacesMessage.SEVERITY_INFO);
			this.displayNewBranchExpenseType = false;
		} catch (ApplicationException exception) {
			log.info(exception.getMessage());
			ViewExceptionHandler.handle(exception);
		}
		this.loadBranchExpenseTypesFromDB = true;
	}

	/**
	 * Removes branch expense type.
	 * 
	 * @return view path for branch expenses.
	 */
	public void removeBranchExpenseTypePeriodical() {
		try {
			this.branchExpenseTypePeriodicalService.removeBranchExpenseTypePeriodical(this.branchExpenseTypePeriodical);
		} catch (ApplicationException exception) {
			log.info(exception.getMessage());
			ViewExceptionHandler.handle(exception);
		}
		this.loadBranchExpenseTypesFromDB = true;
	}

	/**
	 * Removes branch expense type.
	 * 
	 * @return view path for branch expenses.
	 */
	public void viewBranchExpenseTypePeriodical() {
		this.highestBranchExpenseTypePeriodical = this.branchExpenseTypePeriodicalService
				.findLatestBranchExpenseTypePeriodical(this.branchAssembly);
	}

	/**
	 * Return expense name from building block of the branch assembly.
	 * 
	 * @return
	 */
	public String getBranchExpenseTypeName() {
		return this.buildingBlock != null ? this.buildingBlock.getName() : "";
	}

	/**
	 * Retrieves branch assembly for the current branch expense type.
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
	 * Loads branch expense types from database.
	 */
	public void loadBranchExpenseTypesFromDatabase() {
		try {
			if (this.isLoadBranchExpenseTypesFromDB()) {
				this.branchExpenseTypesForBranchAssembly = this.branchExpenseTypePeriodicalService
						.findBranchExpenseTypeByBranchAssembly(this.branchAssembly);

				this.highestBranchExpenseTypePeriodical = this.branchExpenseTypePeriodicalService
						.findLatestBranchExpenseTypePeriodical(this.branchAssembly);

				this.loadBranchExpenseTypesFromDB = false;
			}
		} catch (Exception ex) {
			log.info(ex.getMessage());
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

	/**
	 * Returns all the branch expense types for the branch assembly.
	 * 
	 * @return all the branch expense types for the branch assembly.
	 */
	public Collection<BranchExpenseTypePeriodical> getBranchExpenseTypesForBranchAssembly() {
		return this.branchExpenseTypesForBranchAssembly;
	}

	/**
	 * Returns true if the branch expense types should be loaded from database.
	 * 
	 * @return true if the branch expense types should be loaded from database.
	 */
	public void setLoadBranchExpenseTypesFromDB(final boolean loadBranchExpenseTypesFromDB) {
		this.loadBranchExpenseTypesFromDB = loadBranchExpenseTypesFromDB;
	}

	/**
	 * Returns true if the branch expense types should be loaded from database.
	 * 
	 * @return true if the branch expense types should be loaded from database.
	 */
	public boolean isLoadBranchExpenseTypesFromDB() {
		return this.loadBranchExpenseTypesFromDB;
	}

	public BuildingBlock getBuildingBlock() {
		return this.buildingBlock;
	}

	public void setBuildingBlock(final BuildingBlock buildingBlock) {
		this.buildingBlock = buildingBlock;
	}

	public boolean isDisplayNewBranchExpenseType() {
		return this.displayNewBranchExpenseType;
	}

	public void setDisplayNewBranchExpenseType(final boolean displayNewBranchExpenseType) {
		this.displayNewBranchExpenseType = displayNewBranchExpenseType;
	}

	public BranchExpenseTypePeriodical getBranchExpenseTypePeriodical() {
		return this.branchExpenseTypePeriodical;
	}

	public void setBranchExpenseTypePeriodical(final BranchExpenseTypePeriodical branchExpenseTypePeriodical) {
		this.branchExpenseTypePeriodical = branchExpenseTypePeriodical;
	}

	public BranchExpenseTypePeriodical getHighestBranchExpenseTypePeriodical() {
		return this.highestBranchExpenseTypePeriodical;
	}

	public void setHighestBranchExpenseTypePeriodical(
			final BranchExpenseTypePeriodical highestBranchExpenseTypePeriodical) {
		this.highestBranchExpenseTypePeriodical = highestBranchExpenseTypePeriodical;
	}

	public boolean isDisablePeriodicalCompoment() {

		if (this.highestBranchExpenseTypePeriodical != null && this.branchExpenseTypePeriodical != null
				&& this.branchExpenseTypePeriodical.getId() != null
				&& !this.branchExpenseTypePeriodical.getId().equals(this.highestBranchExpenseTypePeriodical.getId())) {
			return true;
		}
		return false;
	}

}
