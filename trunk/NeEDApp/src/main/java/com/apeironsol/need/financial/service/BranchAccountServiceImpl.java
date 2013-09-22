/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.financial.model.BranchBankAccountTransaction;
import com.apeironsol.need.financial.model.BranchCreditAccount;
import com.apeironsol.need.financial.model.BranchCreditAccountTransaction;
import com.apeironsol.need.financial.model.BranchExpense;
import com.apeironsol.need.payroll.model.EmployeeSalary;
import com.apeironsol.need.procurement.model.PurchaseInvoice;
import com.apeironsol.need.util.constants.BankAccountTransactionTypeConstant;
import com.apeironsol.need.util.constants.BranchAccountTypeConstant;
import com.apeironsol.need.util.constants.CreditAccountTransactionTypeConstant;
import com.apeironsol.framework.exception.ApplicationException;

/**
 * 
 * @author sunny
 * 
 *         Service interface implementation for BranchBankAccount.
 * 
 */
@Service("branchAccountService")
@Transactional
public class BranchAccountServiceImpl implements BranchAccountService {

	@Resource
	private BranchCreditAccountService				branchCreditAccountService;

	@Resource
	private BranchCreditAccountTransactionService	branchCreditAccountTransactionService;

	@Resource
	private BranchBankAccountService				branchBankAccountService;

	@Resource
	private BranchBankAccountTransactionService		branchBankAccountTransactionService;

	/**
	 * If payment option for expense is of type cheque then update or remove
	 * transactions from either bank account or credit account.
	 * 
	 * @param branchExpense
	 *            branchExpense
	 * @param deleteTxn
	 *            true if transaction has to be deleted.
	 */
	@Override
	public void updateBranchAccountTransaction(final BranchExpense branchExpense) {
		final String description = branchExpense.getExpenseBuildingBlock().getName() + "/" + branchExpense.getName()
				+ (branchExpense.getDescription() != null ? "/" + branchExpense.getDescription() : "");
		if (BranchAccountTypeConstant.BANK_ACCOUNT.equals(branchExpense.getBranchAccountType())) {
			this.createOrUpdateBranchBankAccountTransaction(branchExpense.getBranchBankAccount().getId(), branchExpense.getTransactionNr(),
					branchExpense.getAmount(), description, branchExpense.getExpenseDate());
		} else {
			this.createOrUpdateBranchCreditTransaction(branchExpense.getBranchCreditAccount().getId(), branchExpense.getTransactionNr(),
					branchExpense.getAmount(), description, branchExpense.getExpenseDate());
		}
	}

	/**
	 * If payment option for expense is of type cheque then update or remove
	 * transactions from either bank account or credit account.
	 * 
	 * @param branchExpense
	 *            branchExpense
	 * @param deleteTxn
	 *            true if transaction has to be deleted.
	 */
	@Override
	public void updateBranchAccountTransaction(final PurchaseInvoice purchaseInvoice) {
		final String description = "PurchaseOrder#" + purchaseInvoice.getPurchaseOrder().getPurchaseOrderNumber()
				+ (purchaseInvoice.getRemarks() != null ? "/" + purchaseInvoice.getRemarks() : "");
		if (BranchAccountTypeConstant.BANK_ACCOUNT.equals(purchaseInvoice.getBranchAccountType())) {
			this.createOrUpdateBranchBankAccountTransaction(purchaseInvoice.getBranchBankAccount().getId(), purchaseInvoice.getTransactionNr(),
					purchaseInvoice.getAmount(), description, purchaseInvoice.getInvoiceDate());
		} else {
			this.createOrUpdateBranchCreditTransaction(purchaseInvoice.getBranchCreditAccount().getId(), purchaseInvoice.getTransactionNr(),
					purchaseInvoice.getAmount(), description, purchaseInvoice.getInvoiceDate());
		}
	}

	/**
	 * If payment option for expense is of type cheque then update or remove
	 * transactions from branch bank account.
	 * 
	 * @param branchExpense
	 *            branchExpense
	 */
	private void createOrUpdateBranchBankAccountTransaction(final Long bankAccountId, final String transactionNr, final Double amount,
			final String description, final Date transactionDate) {

		final BranchBankAccountTransaction branchBankAccountTransaction = this.branchBankAccountTransactionService
				.findBranchBankAccountTransactionByBranchBankAccountIdAndExternalTransactionNr(bankAccountId, transactionNr);
		if (branchBankAccountTransaction != null) {
			branchBankAccountTransaction.setAmount(amount);
			this.branchBankAccountTransactionService.saveBranchBankAccountTransaction(branchBankAccountTransaction);
		} else {
			this.branchBankAccountService.processBranchBankAccountTransaction(bankAccountId, BankAccountTransactionTypeConstant.WITHDRAW, amount, description,
					transactionNr, transactionDate);
		}

	}

	/**
	 * Remove transactions from branch bank account.
	 * 
	 * @param branchExpense
	 *            branchExpense
	 */
	@Override
	public void deleteBranchAccountTransactionByExternalTransactionAndAccountId(final BranchAccountTypeConstant branchAccountType, final Long accountId,
			final String externalTransactionNr) {
		if (BranchAccountTypeConstant.BANK_ACCOUNT.equals(branchAccountType)) {
			final BranchBankAccountTransaction branchBankAccountTransaction = this.branchBankAccountTransactionService
					.findBranchBankAccountTransactionByBranchBankAccountIdAndExternalTransactionNr(accountId, externalTransactionNr);
			if (branchBankAccountTransaction != null) {
				this.branchBankAccountTransactionService.removeBranchBankAccountTransaction(branchBankAccountTransaction);
			}
		} else {
			final BranchCreditAccountTransaction branchCreditAccountTransaction = this.branchCreditAccountTransactionService
					.findBranchCreditAccountTransactionByBranchCreditAccountIdAndExternalTransactionNr(accountId, externalTransactionNr);
			if (branchCreditAccountTransaction != null) {
				this.branchCreditAccountTransactionService.removeBranchCreditAccountTransaction(branchCreditAccountTransaction);
			}
		}
	}

	/**
	 * If payment option for expense is of type cheque then update or remove
	 * transactions from branch credit account.
	 * 
	 * @param branchExpense
	 *            branchExpense
	 */
	private void createOrUpdateBranchCreditTransaction(final Long creditAccountId, final String externalTransactionNr, final Double amount,
			final String description, final Date transactionDate) {

		final BranchCreditAccountTransaction branchCreditAccountTransaction = this.branchCreditAccountTransactionService
				.findBranchCreditAccountTransactionByBranchCreditAccountIdAndExternalTransactionNr(creditAccountId, externalTransactionNr);
		if (branchCreditAccountTransaction != null) {
			final BranchCreditAccount branchCreditAccount = branchCreditAccountTransaction.getBranchCreditAccount();
			double accountBalance = branchCreditAccount.getAccountBalance();
			accountBalance = accountBalance + branchCreditAccountTransaction.getAmount();
			if (accountBalance < branchCreditAccountTransaction.getAmount()) {
				throw new ApplicationException(" Transaction amount " + branchCreditAccountTransaction.getAmount()
						+ " for with draw cannot be more than balance available " + branchCreditAccount.getAccountBalance()
						+ "  for Credit Account with number " + branchCreditAccount.getAccountNumber());
			}
			branchCreditAccountTransaction.setAmount(amount);
			this.branchCreditAccountTransactionService.saveBranchCreditAccountTransaction(branchCreditAccountTransaction);
			branchCreditAccount.setAccountBalance(accountBalance);
			this.branchCreditAccountService.saveBranchCreditAccount(branchCreditAccount);
		} else {
			this.branchCreditAccountService.processBranchCreditAccountTransaction(creditAccountId, CreditAccountTransactionTypeConstant.WITHDRAW, amount,
					description, externalTransactionNr, transactionDate);
		}

	}

	@Override
	public void updateBranchAccountTransaction(final EmployeeSalary employeeSalary) {
		final String description = "Salary/" + employeeSalary.getEmployee().getDisplayName();
		if (BranchAccountTypeConstant.BANK_ACCOUNT.equals(employeeSalary.getBranchAccountType())) {
			this.createOrUpdateBranchBankAccountTransaction(employeeSalary.getBranchBankAccount().getId(), employeeSalary.getTransactionNr(),
					employeeSalary.getAmount(), description, employeeSalary.getPaidDate());
		} else {
			this.createOrUpdateBranchCreditTransaction(employeeSalary.getBranchCreditAccount().getId(), employeeSalary.getTransactionNr(),
					employeeSalary.getAmount(), description, employeeSalary.getPaidDate());
		}
	}

}
