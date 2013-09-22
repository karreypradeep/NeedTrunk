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

import com.apeironsol.need.core.model.Address;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.financial.model.BranchBankAccount;
import com.apeironsol.need.financial.model.BranchBankAccountTransaction;
import com.apeironsol.need.financial.service.BranchBankAccountService;
import com.apeironsol.need.financial.service.BranchBankAccountTransactionService;
import com.apeironsol.need.util.constants.BankAccountTransactionTypeConstant;
import com.apeironsol.need.util.portal.ViewUtil;

/**
 * 
 * @author sunny
 *         BranchBankAccount portal bean.
 * 
 */
@Named
@Scope(value = "session")
public class BranchBankAccountBean extends AbstractTabbedBean implements Serializable {

	/**
	 * 
	 */
	private static final long							serialVersionUID	= 5097140262635458175L;

	private Collection<BranchBankAccount>				branchBankAccounts;

	private boolean										loadBrachBankAccountInd;

	@Resource
	private BranchBankAccountService					branchBankAccountService;

	private BranchBankAccount							currentBranchBankAccount;

	private BankAccountTransactionTypeConstant			bankAccountTransactionType;

	private Double										transactionAmount;

	private Date										transactionFromDate;

	private Date										transactionToDate;

	private Collection<BranchBankAccountTransaction>	branchBankAccountTransactions;

	@Resource
	private BranchBankAccountTransactionService			branchBankAccountTransactionService;

	private boolean										editBrachBankAccountDetailsInd;

	private String										transactionDescription;

	/**
	 * @return the branchBankAccounts
	 */
	public Collection<BranchBankAccount> getBranchBankAccounts() {
		return this.branchBankAccounts;
	}

	/**
	 * @param branchBankAccounts
	 *            the branchBankAccounts to set
	 */
	public void setBranchBankAccounts(final Collection<BranchBankAccount> branchBankAccounts) {
		this.branchBankAccounts = branchBankAccounts;
	}

	@Override
	public void onTabChange() {
		if (this.getActiveTabIndex() == 1) {
			this.transactionAmount = null;
			this.bankAccountTransactionType = null;
		} else if (this.getActiveTabIndex() == 0) {
			this.editBrachBankAccountDetailsInd = false;
		}
	}

	public void loadBranchBankAccounts() {
		if (this.isLoadBrachBankAccountInd()) {
			this.setBranchBankAccounts(this.branchBankAccountService.findBranchBankAccountByBranchId(this.sessionBean.getCurrentBranch().getId()));
		}
	}

	/**
	 * @return the loadBrachBankAccountInd
	 */
	public boolean isLoadBrachBankAccountInd() {
		return this.loadBrachBankAccountInd;
	}

	/**
	 * @param loadBrachBankAccountInd
	 *            the loadBrachBankAccountInd to set
	 */
	public void setLoadBrachBankAccountInd(final boolean loadBrachBankAccountInd) {
		this.loadBrachBankAccountInd = loadBrachBankAccountInd;
	}

	public String newBranchBankAccount() {
		this.currentBranchBankAccount = new BranchBankAccount();
		this.currentBranchBankAccount.setBranch(this.sessionBean.getCurrentBranch());
		this.currentBranchBankAccount.setBankBranchAddress(new Address());
		this.setLoadBrachBankAccountInd(true);
		this.editBrachBankAccountDetailsInd = true;
		return null;
	}

	/**
	 * @return the currentBranchBankAccount
	 */
	public BranchBankAccount getCurrentBranchBankAccount() {
		return this.currentBranchBankAccount;
	}

	/**
	 * @param currentBranchBankAccount
	 *            the currentBranchBankAccount to set
	 */
	public void setCurrentBranchBankAccount(final BranchBankAccount currentBranchBankAccount) {
		this.currentBranchBankAccount = currentBranchBankAccount;
	}

	/**
	 * Saves academic year BankAccount to database.
	 */
	public String saveBranchBankAccount() {
		try {
			this.currentBranchBankAccount = this.branchBankAccountService.saveBranchBankAccount(this.currentBranchBankAccount);
			ViewUtil.addMessage("Bank account saved successfully.", FacesMessage.SEVERITY_INFO);
		} catch (final Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		this.setViewOrNewAction(false);
		this.editBrachBankAccountDetailsInd = false;
		return null;
	}

	/**
	 * Removes academic year BankAccount from database.
	 */
	public String removeBranchBankAccount() {
		try {
			this.branchBankAccountService.removeBranchBankAccount(this.currentBranchBankAccount);
			ViewUtil.addMessage("Bank account removed successfully.", FacesMessage.SEVERITY_INFO);
		} catch (final Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		this.setViewOrNewAction(false);
		this.setLoadBrachBankAccountInd(true);
		this.editBrachBankAccountDetailsInd = false;
		return null;
	}

	/**
	 * Removes academic year BankAccount from database.
	 */
	public String calcelAction() {
		this.setViewOrNewAction(false);
		this.editBrachBankAccountDetailsInd = false;
		return null;
	}

	/**
	 * Removes academic year BankAccount from database.
	 */
	public String editBankAccountDetails() {
		this.editBrachBankAccountDetailsInd = true;
		return null;
	}

	/**
	 * Removes academic year BankAccount from database.
	 */
	public String viewBranchBankAccount() {
		this.setViewOrNewAction(true);
		this.setActiveTabIndex(0);
		return null;
	}

	/**
	 * @return the bankAccountTransactionType
	 */
	public BankAccountTransactionTypeConstant getBankAccountTransactionType() {
		return this.bankAccountTransactionType;
	}

	/**
	 * @param bankAccountTransactionType
	 *            the bankAccountTransactionType to set
	 */
	public void setBankAccountTransactionType(final BankAccountTransactionTypeConstant bankAccountTransactionType) {
		this.bankAccountTransactionType = bankAccountTransactionType;
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

	public String processBankTransaction() {
		if (this.transactionAmount == null || this.transactionAmount <= 0) {
			ViewUtil.addMessage("Transaction amount cannot be less than zero.", FacesMessage.SEVERITY_ERROR);
		}
		try {
			this.currentBranchBankAccount = this.branchBankAccountService.processBranchBankAccountTransaction(this.currentBranchBankAccount.getId(),
					this.bankAccountTransactionType, this.transactionAmount, this.transactionDescription, null, null);
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
	 * @return the branchBankAccountTransactions
	 */
	public Collection<BranchBankAccountTransaction> getBranchBankAccountTransactions() {
		return this.branchBankAccountTransactions;
	}

	/**
	 * @param branchBankAccountTransactions
	 *            the branchBankAccountTransactions to set
	 */
	public void setBranchBankAccountTransactions(final Collection<BranchBankAccountTransaction> branchBankAccountTransactions) {
		this.branchBankAccountTransactions = branchBankAccountTransactions;
	}

	public String searchBankAccountTransactions() {
		try {
			this.branchBankAccountTransactions = this.branchBankAccountTransactionService.findBranchBankAccountTransactionsByFromDateAndToDate(
					this.currentBranchBankAccount.getId(), this.transactionFromDate, this.transactionToDate,
					EnumSet.allOf(BankAccountTransactionTypeConstant.class));
			if (this.branchBankAccountTransactions == null || this.branchBankAccountTransactions.isEmpty()) {
				ViewUtil.addMessage("No transactions found.", FacesMessage.SEVERITY_INFO);
			}
		} catch (final Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}

	/**
	 * @return the editBrachBankAccountDetailsInd
	 */
	public boolean isEditBrachBankAccountDetailsInd() {
		return this.editBrachBankAccountDetailsInd;
	}

	/**
	 * @param editBrachBankAccountDetailsInd
	 *            the editBrachBankAccountDetailsInd to set
	 */
	public void setEditBrachBankAccountDetailsInd(final boolean editBrachBankAccountDetailsInd) {
		this.editBrachBankAccountDetailsInd = editBrachBankAccountDetailsInd;
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

}
