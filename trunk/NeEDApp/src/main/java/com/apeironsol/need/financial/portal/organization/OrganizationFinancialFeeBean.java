/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.financial.portal.organization;

import java.util.Collection;

import javax.annotation.Resource;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.portal.util.TabbedBean;
import com.apeironsol.need.core.service.BranchService;

/**
 * JSF managed for financial income.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class OrganizationFinancialFeeBean extends TabbedBean {

	@Resource
	protected BranchService		branchService;

	private Collection<Branch>	activeBranches		= null;

	private boolean				loadActiveBranches	= true;

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5563250331308028492L;

	@Override
	public void onTabChange() {
		// TODO Auto-generated method stub

	}

	public void loadActiveBranchesForOrganization() {
		if (this.loadActiveBranches) {
			this.setActiveBranches(this.branchService.findAllBranchsByActiveState(true));
		}
	}

	/**
	 * @return the activeBranches
	 */
	public Collection<Branch> getActiveBranches() {
		if (this.activeBranches == null) {
			this.loadActiveBranches = true;
			this.loadActiveBranchesForOrganization();
		}
		return this.activeBranches;
	}

	/**
	 * @param activeBranches
	 *            the activeBranches to set
	 */
	public void setActiveBranches(final Collection<Branch> activeBranches) {
		this.activeBranches = activeBranches;
	}

}
