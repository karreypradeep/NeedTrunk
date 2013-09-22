/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.portal;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.portal.AbstractPortalBean;
import com.apeironsol.need.financial.model.FinancialYear;
import com.apeironsol.need.financial.service.FinancialYearService;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.framework.exception.ApplicationException;

/**
 * JSF managed bean for financial year.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class FinancialYearBean extends AbstractPortalBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long			serialVersionUID	= -6386826753827216125L;

	@Resource
	private FinancialYearService		financialYearService;

	private FinancialYear				financialYear;

	private boolean						loadFinancialYearsFlag;

	private Collection<FinancialYear>	financialYears;

	@PostConstruct
	public void init() {
		this.setFinancialYear(new FinancialYear());

	}

	/**
	 * Returns collection of academic years.
	 * 
	 * @return collection of academic years.
	 */
	public Collection<FinancialYear> getFinancialYears() {
		return this.financialYears;
	}

	/**
	 * Return academic year.
	 * 
	 * @return academic year.
	 */
	public FinancialYear getFinancialYear() {
		return this.financialYear;
	}

	/**
	 * Sets academic year.
	 * 
	 * @param financialYear
	 *            academic year.
	 */
	public void setFinancialYear(final FinancialYear financialYear) {
		this.financialYear = financialYear;
	}

	public void saveFinancialYear() {
		Branch currentBranch = this.sessionBean.getCurrentBranch();
		this.financialYear.setBranch(currentBranch);
		try {
			this.financialYearService.saveFinancialYear(this.financialYear);
			this.setViewOrNewAction(false);
			this.setLoadFinancialYearsFlag(true);
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void removeFinancialYear() {
		try {
			this.financialYearService.removeFinancialYear(this.financialYear);
			this.setLoadFinancialYearsFlag(true);
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void resetFinancialYear() {
		this.financialYear = new FinancialYear();
	}

	public void newFinancialYear() {

	}

	/**
	 * Load academic years present in the database for current branch.
	 */
	public void loadFinancialYears() {
		try {

			if (this.isLoadFinancialYearsFlag()) {
				Branch currentBranch = this.sessionBean.getCurrentBranch();
				this.financialYears = this.financialYearService.findFinancialYearsByBranchId(currentBranch.getId());
				this.setLoadFinancialYearsFlag(false);
			}
		} catch (ApplicationException ex) {
			ViewExceptionHandler.handle(ex);
		}

	}

	public boolean isLoadFinancialYearsFlag() {
		return this.loadFinancialYearsFlag;
	}

	public void setLoadFinancialYearsFlag(final boolean loadFinancialYearsFlag) {
		this.loadFinancialYearsFlag = loadFinancialYearsFlag;
	}

}
