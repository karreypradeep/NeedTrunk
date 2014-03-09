/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.core.dashboard;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.portal.AbstractStudentBean;
import com.apeironsol.need.core.portal.util.ViewContentHandlerBean;
import com.apeironsol.need.financial.model.StudentFeeTransaction;
import com.apeironsol.need.financial.service.StudentFinancialService;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.need.util.searchcriteria.FeeCollectedSearchCriteria;

@Named
@Scope(value = "session")
public class BranchDashboardBean extends AbstractStudentBean {

	private static final long					serialVersionUID			= 3297278234820272987L;

	private String								dashBoardPortletClicked;

	@Resource
	private StudentFinancialService				studentFinancialService;

	private FeeCollectedSearchCriteria			feeCollectedSearchCriteria	= null;

	/**
	 * Purchase order.
	 */
	private Collection<StudentFeeTransaction>	studentFeeTransactionsProcessed;

	@Resource
	private ViewContentHandlerBean				viewContentHandlerBean;

	private String								studentParentOrGuardianName;

	public enum BranchDashboardPortals {
		STUDENT_ABSENTIES("studentAbsenties"),
		EMPLOYEE_ABSENTIES("employeeAbsenties"),
		FEE_COLLECTED("feeCollected"),
		EXPENSES("expenses");

		private String	key;

		BranchDashboardPortals(final String key) {
			this.key = key;
		}

		public String getKey() {
			return this.key;
		}

		public void setKey(final String key) {
			this.key = key;
		}
	}

	/**
	 * @return the studentParentOrGuardianName
	 */
	@Override
	public String getStudentParentOrGuardianName() {
		return this.studentParentOrGuardianName;
	}

	/**
	 * @param studentParentOrGuardianName
	 *            the studentParentOrGuardianName to set
	 */
	public void setStudentParentOrGuardianName(final String studentParentOrGuardianName) {
		this.studentParentOrGuardianName = studentParentOrGuardianName;
	}

	@PostConstruct
	public void init() {
		this.initializeSearchCriteria();
	}

	public void initializeSearchCriteria() {
		if (this.getFeeCollectedSearchCriteria() == null) {
			this.setFeeCollectedSearchCriteria(new FeeCollectedSearchCriteria(this.sessionBean.getCurrentBranch()));
		}
	}

	@Override
	public void onTabChange() {
		this.setViewOrNewAction(false);
	}

	/**
	 * @return the feeCollectedSearchCriteria
	 */
	public FeeCollectedSearchCriteria getFeeCollectedSearchCriteria() {
		return this.feeCollectedSearchCriteria;
	}

	public void searchFeesCollectedBySearchCriteria() {
		this.dashBoardPortletClicked = BranchDashboardPortals.FEE_COLLECTED.getKey();
		this.feeCollectedSearchCriteria.setBranch(this.sessionBean.getCurrentBranch());
		this.feeCollectedSearchCriteria.setFromDate(this.sessionBean.getCurrentDate());
		this.feeCollectedSearchCriteria.setToDate(this.sessionBean.getCurrentDate());
		this.studentFeeTransactionsProcessed = this.studentFinancialService.findFeesCollectedBySearchCriteria(this.feeCollectedSearchCriteria);
		if (this.studentFeeTransactionsProcessed == null || this.studentFeeTransactionsProcessed.isEmpty()) {
			ViewUtil.addMessage("No fee collected records found for entered search criteria..", FacesMessage.SEVERITY_INFO);
		}
	}

	/**
	 * @param feeCollectedSearchCriteria
	 *            the feeCollectedSearchCriteria to set
	 */
	public void setFeeCollectedSearchCriteria(final FeeCollectedSearchCriteria feeCollectedSearchCriteria) {
		this.feeCollectedSearchCriteria = feeCollectedSearchCriteria;
	}

	public String resetSearchCriteria() {
		this.feeCollectedSearchCriteria.resetSeacrhCriteria();
		return null;
	}

	/**
	 * @return the studentFeeTransactionsProcessed
	 */
	public Collection<StudentFeeTransaction> getStudentFeeTransactionsProcessed() {
		return this.studentFeeTransactionsProcessed;
	}

	/**
	 * @param studentFeeTransactionsProcessed
	 *            the studentFeeTransactionsProcessed to set
	 */
	public void setStudentFeeTransactionsProcessed(final Collection<StudentFeeTransaction> studentFeeTransactionsProcessed) {
		this.studentFeeTransactionsProcessed = studentFeeTransactionsProcessed;
	}

	public double getTotalFeeCollected() {
		double totalFeeCollected = 0.0;
		this.searchFeesCollectedBySearchCriteria();
		if (this.studentFeeTransactionsProcessed != null && !this.studentFeeTransactionsProcessed.isEmpty()) {
			for (final StudentFeeTransaction studentFeeTransaction : this.studentFeeTransactionsProcessed) {
				totalFeeCollected += studentFeeTransaction.getAmount();
			}
		}
		return totalFeeCollected;
	}


	public String getDashBoardPortletClicked() {
		return this.dashBoardPortletClicked;
	}

	public void setDashBoardPortletClicked(final String dashBoardPortletClicked) {
		this.dashBoardPortletClicked = dashBoardPortletClicked;
	}

}
