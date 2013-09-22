/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.financial.portal;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Resource;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.Address;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.procurement.model.Supplier;
import com.apeironsol.need.procurement.service.SupplierService;

/**
 * JSF managed for financial income.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class FinanceSupplierBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long		serialVersionUID	= 2649327175051422697L;

	/**
	 * Supplier.
	 */
	private Supplier				supplier;

	/**
	 * Collection of suppliers for current branch.
	 */
	private Collection<Supplier>	suppliersForCurrentBranch;

	@Resource
	private SupplierService			supplierService;

	/**
	 * Indicator for specifying to load suppliers from database.
	 */
	private boolean					loadSuppliersForCurrentBranchIndicator;

	@Override
	public void onTabChange() {
		setViewOrNewAction(false);
		setLoadSuppliersForCurrentBranchIndicator(true);
	}

	/**
	 * @return the supplier
	 */
	public Supplier getSupplier() {
		return this.supplier;
	}

	/**
	 * @param supplier
	 *            the supplier to set
	 */
	public void setSupplier(final Supplier supplier) {
		this.supplier = supplier;
	}

	/**
	 * @return the suppliersForCurrentBranch
	 */
	public Collection<Supplier> getSuppliersForCurrentBranch() {
		return this.suppliersForCurrentBranch;
	}

	/**
	 * @param suppliersForCurrentBranch
	 *            the suppliersForCurrentBranch to set
	 */
	public void setSuppliersForCurrentBranch(final Collection<Supplier> suppliersForCurrentBranch) {
		this.suppliersForCurrentBranch = suppliersForCurrentBranch;
	}

	public void loadAllSuppliersForCurrentBranch() {
		if (isLoadSuppliersForCurrentBranchIndicator()) {
			setSuppliersForCurrentBranch(this.supplierService.findSuppliersByBranchId(this.sessionBean
					.getCurrentBranch().getId()));
		}
	}

	/**
	 * @return the loadSuppliersForCurrentBranchIndicator
	 */
	public boolean isLoadSuppliersForCurrentBranchIndicator() {
		return this.loadSuppliersForCurrentBranchIndicator;
	}

	/**
	 * @param loadSuppliersForCurrentBranchIndicator
	 *            the loadSuppliersForCurrentBranchIndicator to set
	 */
	public void setLoadSuppliersForCurrentBranchIndicator(final boolean loadSuppliersForCurrentBranchIndicator) {
		this.loadSuppliersForCurrentBranchIndicator = loadSuppliersForCurrentBranchIndicator;
	}

	public String saveSupplier() {
		this.supplier = this.supplierService.saveSupplier(this.supplier);
		setLoadSuppliersForCurrentBranchIndicator(true);
		setViewOrNewAction(false);
		return null;
	}

	public String removeSupplier() {
		this.supplierService.removeSupplier(this.supplier);
		setLoadSuppliersForCurrentBranchIndicator(true);
		setViewOrNewAction(false);
		return null;
	}

	/**
	 * Removes academic year Income from database.
	 */
	public String calcelAction() {
		setViewOrNewAction(false);
		return null;
	}

	/**
	 * Removes academic year Income from database.
	 */
	public String viewSupplier() {
		setViewOrNewAction(true);
		return null;
	}

	/**
	 * Create new academic year Income.
	 */
	public String newSupplier() {
		this.supplier = new Supplier();
		this.supplier.setAddress(new Address());
		this.supplier.setBranch(this.sessionBean.getCurrentBranch());
		setViewOrNewAction(true);
		return null;
	}

}
