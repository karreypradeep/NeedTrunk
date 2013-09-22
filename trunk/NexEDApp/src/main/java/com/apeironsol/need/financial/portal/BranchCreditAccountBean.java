/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.portal;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.financial.model.BranchCreditAccount;
import com.apeironsol.need.financial.model.BranchCreditAccountTransaction;
import com.apeironsol.need.financial.service.BranchCreditAccountService;
import com.apeironsol.need.financial.service.BranchCreditAccountTransactionService;
import com.apeironsol.need.hrms.model.Employee;
import com.apeironsol.need.hrms.service.EmployeeService;
import com.apeironsol.need.util.constants.CreditAccountTransactionTypeConstant;
import com.apeironsol.need.util.portal.ViewUtil;

/**
 * 
 * @author sunny
 *         BranchCreditAccountBean portal bean.
 * 
 */
@Named
@Scope(value = "session")
public class BranchCreditAccountBean extends AbstractTabbedBean implements Serializable {

	/**
	 * 
	 */
	private static final long							serialVersionUID	= -1298567402417821045L;

	private Collection<BranchCreditAccount>				branchCreditAccounts;

	private boolean										loadBrachCreditAccountInd;

	@Resource
	private BranchCreditAccountService					branchCreditAccountService;

	private BranchCreditAccount							currentBranchCreditAccount;

	private CreditAccountTransactionTypeConstant		creditAccountTransactionType;

	private Double										transactionAmount;

	private Date										transactionFromDate;

	private Date										transactionToDate;

	private Collection<BranchCreditAccountTransaction>	branchCreditAccountTransactions;

	@Resource
	private BranchCreditAccountTransactionService		branchCreditAccountTransactionService;

	private boolean										editBrachCreditAccountDetailsInd;

	private String										transactionDescription;

	private Collection<Employee>						branchEmployees;

	@Resource
	private EmployeeService								employeeService;

	private CreditAccountTransactionTypeConstant		creditAccountTransactionTypeForStatement;

	/**
	 * @return the branchCreditAccounts
	 */
	public Collection<BranchCreditAccount> getBranchCreditAccounts() {
		return this.branchCreditAccounts;
	}

	/**
	 * @param branchCreditAccounts
	 *            the branchCreditAccounts to set
	 */
	public void setBranchCreditAccounts(final Collection<BranchCreditAccount> branchCreditAccounts) {
		this.branchCreditAccounts = branchCreditAccounts;
	}

	@Override
	public void onTabChange() {
		if (this.getActiveTabIndex() == 1) {
			this.transactionAmount = null;
			this.creditAccountTransactionType = null;
			this.transactionDescription = null;
		} else if (this.getActiveTabIndex() == 0) {
			this.editBrachCreditAccountDetailsInd = false;
		}
	}

	public void loadBranchCreditAccounts() {
		if (this.isLoadBrachCreditAccountInd()) {
			this.setBranchCreditAccounts(this.branchCreditAccountService.findBranchCreditAccountByBranchId(this.sessionBean.getCurrentBranch().getId()));
			this.setBranchEmployees(this.employeeService.findAllEmployeesByBranchId(this.sessionBean.getCurrentBranch().getId()));
		}
	}

	/**
	 * @return the loadBrachCreditAccountInd
	 */
	public boolean isLoadBrachCreditAccountInd() {
		return this.loadBrachCreditAccountInd;
	}

	/**
	 * @param loadBrachCreditAccountInd
	 *            the loadBrachCreditAccountInd to set
	 */
	public void setLoadBrachCreditAccountInd(final boolean loadBrachCreditAccountInd) {
		this.loadBrachCreditAccountInd = loadBrachCreditAccountInd;
	}

	public String newBranchCreditAccount() {
		this.currentBranchCreditAccount = new BranchCreditAccount();
		this.currentBranchCreditAccount.setBranch(this.sessionBean.getCurrentBranch());
		this.setLoadBrachCreditAccountInd(true);
		this.currentBranchCreditAccount.setAccountBalance(0.0);
		this.editBrachCreditAccountDetailsInd = true;
		return null;
	}

	/**
	 * @return the currentBranchCreditAccount
	 */
	public BranchCreditAccount getCurrentBranchCreditAccount() {
		return this.currentBranchCreditAccount;
	}

	/**
	 * @param currentBranchCreditAccount
	 *            the currentBranchCreditAccount to set
	 */
	public void setCurrentBranchCreditAccount(final BranchCreditAccount currentBranchCreditAccount) {
		this.currentBranchCreditAccount = currentBranchCreditAccount;
	}

	/**
	 * Saves academic year CreditAccount to database.
	 */
	public String saveBranchCreditAccount() {
		try {
			this.currentBranchCreditAccount = this.branchCreditAccountService.saveBranchCreditAccount(this.currentBranchCreditAccount);
			ViewUtil.addMessage("Credit account saved successfully.", FacesMessage.SEVERITY_INFO);
		} catch (final Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		this.setViewOrNewAction(false);
		this.editBrachCreditAccountDetailsInd = false;
		return null;
	}

	/**
	 * Removes academic year CreditAccount from database.
	 */
	public String removeBranchCreditAccount() {
		try {
			this.branchCreditAccountService.removeBranchCreditAccount(this.currentBranchCreditAccount);
			ViewUtil.addMessage("Credit account removed successfully.", FacesMessage.SEVERITY_INFO);
		} catch (final Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		this.setViewOrNewAction(false);
		this.setLoadBrachCreditAccountInd(true);
		this.editBrachCreditAccountDetailsInd = false;
		return null;
	}

	/**
	 * Removes academic year CreditAccount from database.
	 */
	public String calcelAction() {
		this.setViewOrNewAction(false);
		this.editBrachCreditAccountDetailsInd = false;
		return null;
	}

	/**
	 * Removes academic year CreditAccount from database.
	 */
	public String editCreditAccountDetails() {
		this.editBrachCreditAccountDetailsInd = true;
		return null;
	}

	/**
	 * Removes academic year CreditAccount from database.
	 */
	public String viewBranchCreditAccount() {
		this.setViewOrNewAction(true);
		this.setActiveTabIndex(0);
		return null;
	}

	/**
	 * @return the creditAccountTransactionType
	 */
	public CreditAccountTransactionTypeConstant getCreditAccountTransactionType() {
		return this.creditAccountTransactionType;
	}

	/**
	 * @param creditAccountTransactionType
	 *            the creditAccountTransactionType to set
	 */
	public void setCreditAccountTransactionType(final CreditAccountTransactionTypeConstant creditAccountTransactionType) {
		this.creditAccountTransactionType = creditAccountTransactionType;
	}

	/**
	 * @return the transactionAmount
	 */
	public Double getTransactionAmount() {
		return this.transactionAmount;
	}

	/**
	 * @param transactionAmount
	 *            the transactionAmount to set
	 */
	public void setTransactionAmount(final Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String processCreditTransaction() {
		if (this.transactionAmount == null || this.transactionAmount <= 0) {
			ViewUtil.addMessage("Transaction amount cannot be less than zero.", FacesMessage.SEVERITY_ERROR);
		}
		try {
			this.currentBranchCreditAccount = this.branchCreditAccountService.processBranchCreditAccountTransaction(this.currentBranchCreditAccount.getId(),
					this.creditAccountTransactionType, this.transactionAmount, this.transactionDescription, null, null);
			ViewUtil.addMessage("Transaction performed successfully.", FacesMessage.SEVERITY_INFO);
			this.setActiveTabIndex(0);
		} catch (final Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}

	/**
	 * @return the transactionFromDate
	 */
	public Date getTransactionFromDate() {
		return this.transactionFromDate;
	}

	/**
	 * @param transactionFromDate
	 *            the transactionFromDate to set
	 */
	public void setTransactionFromDate(final Date transactionFromDate) {
		this.transactionFromDate = transactionFromDate;
	}

	/**
	 * @return the transactionToDate
	 */
	public Date getTransactionToDate() {
		return this.transactionToDate;
	}

	/**
	 * @param transactionToDate
	 *            the transactionToDate to set
	 */
	public void setTransactionToDate(final Date transactionToDate) {
		this.transactionToDate = transactionToDate;
	}

	/**
	 * @return the branchCreditAccountTransactions
	 */
	public Collection<BranchCreditAccountTransaction> getBranchCreditAccountTransactions() {
		return this.branchCreditAccountTransactions;
	}

	/**
	 * @param branchCreditAccountTransactions
	 *            the branchCreditAccountTransactions to set
	 */
	public void setBranchCreditAccountTransactions(final Collection<BranchCreditAccountTransaction> branchCreditAccountTransactions) {
		this.branchCreditAccountTransactions = branchCreditAccountTransactions;
	}

	public String searchCreditAccountTransactions() {
		try {
			EnumSet<CreditAccountTransactionTypeConstant> enumSet = null;
			if (this.creditAccountTransactionTypeForStatement != null) {
				enumSet = EnumSet.of(this.creditAccountTransactionTypeForStatement);
			} else {
				enumSet = EnumSet.allOf(CreditAccountTransactionTypeConstant.class);
			}
			this.branchCreditAccountTransactions = this.branchCreditAccountTransactionService.findBranchCreditAccountTransactionsByFromDateAndToDate(
					this.currentBranchCreditAccount.getId(), this.transactionFromDate, this.transactionToDate, enumSet);
			if (this.branchCreditAccountTransactions == null || this.branchCreditAccountTransactions.isEmpty()) {
				ViewUtil.addMessage("No transactions found.", FacesMessage.SEVERITY_INFO);
			}
		} catch (final Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}

	/**
	 * @return the editBrachCreditAccountDetailsInd
	 */
	public boolean isEditBrachCreditAccountDetailsInd() {
		return this.editBrachCreditAccountDetailsInd;
	}

	/**
	 * @param editBrachCreditAccountDetailsInd
	 *            the editBrachCreditAccountDetailsInd to set
	 */
	public void setEditBrachCreditAccountDetailsInd(final boolean editBrachCreditAccountDetailsInd) {
		this.editBrachCreditAccountDetailsInd = editBrachCreditAccountDetailsInd;
	}

	/**
	 * @return the transactionDescription
	 */
	public String getTransactionDescription() {
		return this.transactionDescription;
	}

	/**
	 * @param transactionDescription
	 *            the transactionDescription to set
	 */
	public void setTransactionDescription(final String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

	/**
	 * @return the branchEmployees
	 */
	public Collection<Employee> getBranchEmployees() {
		return this.branchEmployees;
	}

	/**
	 * @param branchEmployees
	 *            the branchEmployees to set
	 */
	public void setBranchEmployees(final Collection<Employee> branchEmployees) {
		this.branchEmployees = branchEmployees;
	}

	public CreditAccountTransactionTypeConstant getCreditAccountTransactionTypeForStatement() {
		return this.creditAccountTransactionTypeForStatement;
	}

	public void setCreditAccountTransactionTypeForStatement(final CreditAccountTransactionTypeConstant creditAccountTransactionTypeForStatement) {
		this.creditAccountTransactionTypeForStatement = creditAccountTransactionTypeForStatement;
	}

}
