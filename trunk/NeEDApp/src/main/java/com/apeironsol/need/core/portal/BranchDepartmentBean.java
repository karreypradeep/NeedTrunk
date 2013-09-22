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
import com.apeironsol.need.core.model.BranchDepartment;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.service.BranchDepartmentService;
import com.apeironsol.need.util.portal.ViewPathConstants;
import com.apeironsol.need.util.portal.ViewUtil;

@Named
@Scope(value = "session")
public class BranchDepartmentBean extends AbstractPortalBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long				serialVersionUID			= 7706906418772309273L;

	/**
	 * Logger for branch department bean.
	 */
	private static final Logger									log							= Logger.getLogger(BranchDepartmentBean.class);

	/**
	 * Branch department service resource supplied by spring.
	 */
	@Resource
	private BranchDepartmentService			branchDepartmentService;

	/**
	 * Branch department entity.
	 */
	private BranchDepartment				branchDepartment;

	/**
	 * Branch assembly.
	 */
	private BranchAssembly					branchAssembly;

	/**
	 * Branch department entity.
	 */
	private Collection<BranchDepartment>	branchDepartmentsForBranchAssembly;

	/**
	 * Indicator to specify if the branch departments should be loaded form
	 * database.
	 */
	private boolean							loadBranchDepartmentsFromDB	= false;

	private BuildingBlock					buildingBlock;

	/**
	 * Returns current branch department.
	 * 
	 * @return current branch department.
	 */
	public BranchDepartment getBranchDepartment() {
		return this.branchDepartment;
	}

	/**
	 * Sets the branch department.
	 * 
	 * @param branchDepartment
	 *            the branch department.
	 */
	public void setBranchDepartment(final BranchDepartment branchDepartment) {
		this.branchDepartment = branchDepartment;
	}

	/**
	 * Shows selected branch department.
	 * 
	 * @return view path for branch department.
	 */
	public String showBranchDepartment() {
		return ViewPathConstants.ORGANIZATION_BRANCH_DEPARTMENT;
	}

	/**
	 * Cancel action. Displays the table of branch departments.
	 * 
	 * @return view path for branch department.
	 */
	public String cancelAction() {
		return ViewPathConstants.ORGANIZATION_BRANCH_DEPARTMENTS;
	}

	/**
	 * Saves the branch department.
	 * 
	 * @return null.So that the view remains in same page.
	 */
	public String saveBranchDepartment() {
		try {
			this.branchDepartment = this.branchDepartmentService.saveBranchDepartment(this.branchDepartment);
			ViewUtil.addMessage("Department saved sucessfully.", FacesMessage.SEVERITY_INFO);
			this.loadBranchDepartmentsFromDB = true;
		} catch (Exception ex) {
			log.info(ex.getMessage());
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}

	/**
	 * Creates a new branch department periodical.
	 * 
	 * @return view path for branch department.
	 */
	public String addNewBranchDepartmentPeriodical() {
		this.branchDepartment = new BranchDepartment();
		this.branchDepartment.setBranchAssembly(this.getBranchAssembly());
		return ViewPathConstants.ORGANIZATION_BRANCH_DEPARTMENT;
	}

	/**
	 * Removes branch department.
	 * 
	 * @return view path for branch departments.
	 */
	public String removeBranchDepartment() {
		try {
			this.branchDepartmentService.removeBranchDepartment(this.getBranchDepartment());
			ViewUtil.addMessage(this.getDepartmentName() + " period removed successfully.", FacesMessage.SEVERITY_INFO);
		} catch (Exception ex) {
			log.info(ex.getMessage());
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		this.loadBranchDepartmentsFromDB = true;
		return ViewPathConstants.ORGANIZATION_BRANCH_DEPARTMENTS;
	}

	/**
	 * Ends branch department.
	 * 
	 * @return
	 */
	public String endDepartment() {
		try {
			this.branchDepartment = this.branchDepartmentService.saveBranchDepartment(this.branchDepartment);
			ViewUtil.addMessage("Department sucessfully terminated.", FacesMessage.SEVERITY_INFO);
		} catch (Exception ex) {
			log.info(ex.getMessage());
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}

	/**
	 * Return department name from building block of the branch assembly.
	 * 
	 * @return
	 */
	public String getDepartmentName() {
		return this.buildingBlock != null ? this.buildingBlock.getName() : "";
	}

	/**
	 * Retrieves branch assembly for the current branch department.
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
		this.loadBranchDepartmentsFromDB = true;
	}

	/**
	 * Loads branch departments from database.
	 */
	public void loadBranchDepartmentsFromDatabase() {
		try {
			if (this.isLoadBranchDepartmentsFromDB()) {
				this.branchDepartmentsForBranchAssembly = this.branchDepartmentService
						.findBranchDepartmentsByBranchAssembly(this.branchAssembly);
				this.loadBranchDepartmentsFromDB = false;
			}
		} catch (Exception ex) {
			log.info(ex.getMessage());
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

	/**
	 * Returns all the branch departments for the branch assembly.
	 * 
	 * @return all the branch departments for the branch assembly.
	 */
	public Collection<BranchDepartment> getBranchDepartmentsForBranchAssembly() {
		return this.branchDepartmentsForBranchAssembly;
	}

	/**
	 * Returns true if the branch departments should be loaded from database.
	 * 
	 * @return true if the branch departments should be loaded from database.
	 */
	public boolean isLoadBranchDepartmentsFromDB() {
		return this.loadBranchDepartmentsFromDB;
	}

	public BuildingBlock getBuildingBlock() {
		return this.buildingBlock;
	}

	public void setBuildingBlock(final BuildingBlock buildingBlock) {
		this.buildingBlock = buildingBlock;
	}

	public void setLoadBranchFeeTypesFromDB(final boolean loadBranchDepartmentsFromDB) {
		this.loadBranchDepartmentsFromDB = loadBranchDepartmentsFromDB;

	}

}
