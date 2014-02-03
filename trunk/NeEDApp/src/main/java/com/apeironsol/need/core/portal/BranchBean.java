/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.portal;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.core.model.Address;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.service.BranchService;
import com.apeironsol.need.security.portal.BranchesAppStatusBean;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewPathConstants;
import com.apeironsol.need.util.portal.ViewUtil;

@Named
@Scope(value = "session")
public class BranchBean extends AbstractBranchBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long		serialVersionUID	= -8924761849582756702L;

	/**
	 * Logger for the class.
	 */
	private static final Logger		log					= Logger.getLogger(BranchBean.class);

	/**
	 * Branch service resource.
	 */
	@Resource
	private BranchService			branchService;

	/**
	 * Branch service resource.
	 */
	@Resource
	private SessionBean				sessionBean;

	/**
	 * Collection of available branches.
	 */
	private Collection<Branch>		availableBranchs;

	private Branch					branch;

	private boolean					loadBranchesFlag;

	@Resource
	private BranchesAppStatusBean	branchesAppStatusBean;

	private Collection<Branch>		filteredValues		= new ArrayList<Branch>();

	/**
	 * Returns the branch object.
	 * 
	 * @return the branch object.
	 */
	public Branch getBranch() {
		return this.branch;
	}

	/**
	 * Sets the branch.
	 * 
	 * @param branch
	 *            the branch.
	 */
	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	/**
	 * Sets the branch.
	 * 
	 * @param branch
	 *            the branch.
	 */
	public void setBranchInfo(final Branch branch) {
		this.branch = branch;
		// this.sessionBean.setCurrentBranch(branch);
		this.sessionBean.setLoadBranchTreeFromDatabase(true);
		this.sessionBean.setLoadBranchBuildingBlockTreeFromDatabase(true);
	}

	/**
	 * This method is called after construction is executed.
	 */
	@PostConstruct
	public void init() {

	}

	/**
	 * Returns the available branches.
	 * 
	 * @return the available branches.
	 */
	public Collection<Branch> getBranches() {
		return this.availableBranchs;
	}

	/**
	 * Load the branches related data. This method is called before the page
	 * branches is
	 * rendered.
	 */
	public void loadBranches() {
		try {
			if (this.loadBranchesFlag) {
				this.availableBranchs = this.branchService.findAllBranchs();
				this.filteredValues = this.availableBranchs;
				this.loadBranchesFlag = false;
			}

		} catch (final ApplicationException ex) {
			log.info(ex.getMessage());
			ViewExceptionHandler.handle(ex);
		}
	}

	/**
	 * Creates a new branch.
	 * 
	 * @return
	 */
	public void newBranch() {
		this.branch = new Branch();
		this.branch.setAddress(new Address());
		setBranchInfo(this.branch);
		setViewOrNewAction(true);

	}

	/**
	 * Removes the branch from database.
	 * 
	 * @return
	 */
	public void removeBranch() {
		try {
			this.branchService.removeBranch(getBranch());
			ViewUtil.addMessage("Branch removed successfully.", FacesMessage.SEVERITY_INFO);
			setLoadBranchesFlag(true);
			setViewOrNewAction(false);
			this.branchesAppStatusBean.reloadBranchesStatus();
		} catch (final ApplicationException ex) {
			log.info(ex.getMessage());
			ViewExceptionHandler.handle(ex);
		}
	}

	/**
	 * Saves the branch in database.
	 * 
	 * @return
	 */
	public String saveBranch() {
		try {
			getBranch().setOrganization(getOrganization());

			if (getBranch().getId() != null) {
				this.branch = this.branchService.saveBranch(getBranch());
				setBranchInfo(this.branch);
			} else {

				this.branch = this.branchService.createNewBranch(this.branch);
				setBranchInfo(this.branch);
			}
			this.branchesAppStatusBean.reloadBranchesStatus();
			ViewUtil.addMessage("Branch saved successfully.", FacesMessage.SEVERITY_INFO);

		} catch (final ApplicationException ex) {
			log.info(ex.getMessage());
			ViewExceptionHandler.handle(ex);
		} catch (final Throwable ex) {
			log.info(ex.getMessage());
			ViewExceptionHandler.handle(ex);
		}
		return null;
	}

	/**
	 * Saves the branch in database.
	 * 
	 * @return
	 */
	public String cancleAction() {
		setBranchInfo(new Branch());
		return ViewPathConstants.ORGANIZATION_BRANCHES;
	}

	public boolean isDisbledBranchSubTabs() {
		if ((getBranch() == null) || (getBranch().getId() == null)) {
			return true;
		}

		return false;
	}

	public void performSanityCheck() {
		try {

			this.branchService.performSanityCheck(getBranch());

			ViewUtil.addMessage("Branch passed sanity check successfully.", FacesMessage.SEVERITY_INFO);
		} catch (final ApplicationException ex) {
			log.info(ex.getMessage());
			ViewExceptionHandler.handle(ex);
		}
	}

	public boolean isDisableSanityCheck() {

		if ((this.branch == null) || (this.branch.getId() == null)) {
			return true;
		}
		return false;
	}

	public void activateBranch() {
		try {

			this.branchService.performSanityCheck(getBranch());
			setBranchInfo(this.branchService.activateBranch(getBranch()));
			this.branchesAppStatusBean.reloadBranchesStatus();
			ViewUtil.addMessage("Branch has bean activated sucessfully.", FacesMessage.SEVERITY_INFO);

		} catch (final ApplicationException ex) {
			log.info(ex.getMessage());
			ViewExceptionHandler.handle(ex);
		}

	}

	public void updateBranchStatusToApplicationLevel() {

	}

	public void inactivateBranch() {
		try {
			// this.sessionBean.setCurrentBranch(null);
			getBranch().setActive(Boolean.FALSE);
			setBranchInfo(this.branchService.saveBranch(getBranch()));
			this.branchesAppStatusBean.reloadBranchesStatus();
			ViewUtil.addMessage("Branch has bean in activated sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (final ApplicationException ex) {
			log.info(ex.getMessage());
			ViewExceptionHandler.handle(ex);
		}

	}

	public boolean isDisableActivate() {
		if ((this.branch != null) && !this.branch.isActive()) {
			return false;
		}
		return true;
	}

	public boolean isDisableInActivate() {
		if ((this.branch != null) && this.branch.isActive()) {
			return false;
		}
		return true;
	}

	@Override
	public void onTabChange() {

	}

	public boolean isLoadBranchesFlag() {
		return this.loadBranchesFlag;
	}

	public void setLoadBranchesFlag(final boolean loadBranchesFlag) {
		this.loadBranchesFlag = loadBranchesFlag;
	}

	public Collection<Branch> getFilteredValues() {
		return this.filteredValues;
	}

	public void setFilteredValues(final Collection<Branch> filteredValues) {
		this.filteredValues = filteredValues;
	}
}