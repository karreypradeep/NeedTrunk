/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.service.BranchService;
import com.apeironsol.need.financial.dao.BranchBalanceSheetDao;
import com.apeironsol.need.financial.dao.StudentFeeTransactionDao;
import com.apeironsol.need.financial.dataobject.BalanceSheetElementDO;
import com.apeironsol.need.financial.dataobject.BalanceSheetElementTransaction;
import com.apeironsol.need.financial.model.BranchBalanceSheet;
import com.apeironsol.need.financial.model.BranchBankAccount;
import com.apeironsol.need.financial.model.BranchBankAccountTransaction;
import com.apeironsol.need.financial.model.BranchCreditAccount;
import com.apeironsol.need.financial.model.BranchCreditAccountTransaction;
import com.apeironsol.need.financial.model.BranchExpense;
import com.apeironsol.need.financial.model.BranchInvestment;
import com.apeironsol.need.financial.model.StudentFeeTransaction;
import com.apeironsol.need.payroll.model.EmployeeSalary;
import com.apeironsol.need.payroll.service.EmployeeSalaryService;
import com.apeironsol.need.procurement.model.PurchaseInvoice;
import com.apeironsol.need.procurement.service.PurchaseInvoiceService;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.BankAccountTransactionTypeConstant;
import com.apeironsol.need.util.constants.CreditAccountTransactionTypeConstant;
import com.apeironsol.need.util.searchcriteria.BranchInvestmentSearchCriteria;
import com.apeironsol.need.util.searchcriteria.EmployeeSalarySearchCriteria;
import com.apeironsol.need.util.searchcriteria.FeeCollectedSearchCriteria;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * 
 * @author pradeep
 * 
 *         Service interface implementation for BranchBalanceSheet.
 * 
 */
@Service("branchBalanceSheetService")
@Transactional(rollbackFor = Exception.class)
public class BranchBalanceSheetServiceImpl implements BranchBalanceSheetService {

	@Resource
	private BranchBalanceSheetDao			branchBalanceSheetDao;

	@Resource
	StudentFeeTransactionDao				studentFeeTransactionDao;

	@Resource
	BranchExpenseService					branchExpenseService;

	@Resource
	EmployeeSalaryService					employeeSalaryService;

	@Resource
	BranchInvestmentService					branchInvestmentService;

	@Resource
	PurchaseInvoiceService					purchaseInvoiceService;

	@Resource
	BranchService							branchService;

	@Resource
	BranchCreditAccountTransactionService	branchCreditAccountTransactionService;

	@Resource
	BranchCreditAccountService				branchCreditAccountService;

	@Resource
	BranchBankAccountTransactionService		branchBankAccountTransactionService;

	@Resource
	BranchBankAccountService				branchBankAccountService;

	@Override
	public BranchBalanceSheet saveBranchBalanceSheet(final BranchBalanceSheet branchBalanceSheet) {
		if (branchBalanceSheet.getEndDate() != null) {
			if (branchBalanceSheet.getEndDate().before(branchBalanceSheet.getStartDate())) {
				throw new BusinessException("End date should be on or after start date.");
			}

			if (branchBalanceSheet.getEndDate().after(DateUtil.getSystemDate())) {
				throw new BusinessException("End date should be before current date.");
			}
		}
		return this.branchBalanceSheetDao.persist(branchBalanceSheet);
	}

	@Override
	public void removeBranchBalanceSheet(final BranchBalanceSheet branchBalanceSheet) throws BusinessException, SystemException {
		this.branchBalanceSheetDao.remove(branchBalanceSheet);
	}

	@Override
	public BranchBalanceSheet findBranchBalanceSheetById(final Long id) {
		return this.branchBalanceSheetDao.findById(id);
	}

	@Override
	public Collection<BranchBalanceSheet> findBranchBalanceSheetsByBranchId(final Long branchId) {
		return this.branchBalanceSheetDao.findBranchBalanceSheetByBranchId(branchId);
	}

	@Override
	public BranchBalanceSheet findLatestBranchBalanceSheetByBranchIdAndProcessedState(final Long branchId, final Boolean balanceSheetProcessed) {
		return this.branchBalanceSheetDao.findLatestBranchBalanceSheetByBranchIdAndProcessedState(branchId, balanceSheetProcessed);
	}

	@Override
	public Collection<BalanceSheetElementDO> getBranchBalanceSheetElements(final Long branchId, final Date startDate, final Date endDate) {
		final Collection<BalanceSheetElementDO> branchBalanceSheetElements = new ArrayList<BalanceSheetElementDO>();
		final Collection<BalanceSheetElementDO> branchBalanceSheetIncomeElements = this.getBranchBalanceSheetIncomeElements(branchId, startDate, endDate);
		if (branchBalanceSheetIncomeElements != null && !branchBalanceSheetIncomeElements.isEmpty()) {
			branchBalanceSheetElements.addAll(branchBalanceSheetIncomeElements);
		}

		final Collection<BalanceSheetElementDO> branchBalanceSheetExpenseElements = this.getBranchBalanceSheetExpenseElements(branchId, startDate, endDate);
		if (branchBalanceSheetExpenseElements != null && !branchBalanceSheetExpenseElements.isEmpty()) {
			branchBalanceSheetElements.addAll(branchBalanceSheetExpenseElements);
		}
		return branchBalanceSheetElements;
	}

	@Override
	public Collection<BalanceSheetElementDO> getBranchBalanceSheetIncomeElements(final Long branchId, final Date startDate, final Date endDate) {
		final Branch branch = this.branchService.findBranchById(branchId);
		final Collection<BalanceSheetElementDO> branchBalanceSheetIncomeElements = new ArrayList<BalanceSheetElementDO>();
		final FeeCollectedSearchCriteria feeCollectedSearchCriteria = new FeeCollectedSearchCriteria(branch);
		feeCollectedSearchCriteria.setFromDate(startDate);
		feeCollectedSearchCriteria.setToDate(endDate);

		final Collection<StudentFeeTransaction> studentFeeTransactions = this.studentFeeTransactionDao
				.findFeesCollectedBySearchCriteria(feeCollectedSearchCriteria);
		new HashMap<String, Double>();

		final BalanceSheetElementDO balanceSheetElementDOStudentFeeTrans = new BalanceSheetElementDO();
		BalanceSheetElementTransaction balanceSheetElementTransaction = null;
		balanceSheetElementDOStudentFeeTrans.setDescription("Student Fee collection");
		for (final StudentFeeTransaction studentFeeTransaction : studentFeeTransactions) {
			balanceSheetElementTransaction = new BalanceSheetElementTransaction();
			balanceSheetElementTransaction.setDescription(studentFeeTransaction.getStudentAcademicYear().getStudent().getDisplayName() + "/"
					+ studentFeeTransaction.getStudentAcademicYear().getStudent().getAdmissionNr());
			balanceSheetElementTransaction.setTransactionAmount(studentFeeTransaction.getAmount());
			balanceSheetElementTransaction.setTransactionDate(studentFeeTransaction.getTransactionDate());
			balanceSheetElementDOStudentFeeTrans.addBalanceSheetElementTransaction(balanceSheetElementTransaction);
		}
		branchBalanceSheetIncomeElements.add(balanceSheetElementDOStudentFeeTrans);

		final BalanceSheetElementDO balanceSheetElementDOInvestment = new BalanceSheetElementDO();
		balanceSheetElementDOInvestment.setDescription("Investments");
		final BranchInvestmentSearchCriteria branchInvestmentSearchCriteria = new BranchInvestmentSearchCriteria(branch);
		branchInvestmentSearchCriteria.setFromDate(startDate);
		branchInvestmentSearchCriteria.setToDate(endDate);
		final Collection<BranchInvestment> branchInvestments = this.branchInvestmentService
				.findBranchInvestmentBySearchCriteria(branchInvestmentSearchCriteria);
		for (final BranchInvestment branchInvestment : branchInvestments) {
			balanceSheetElementTransaction = new BalanceSheetElementTransaction();
			balanceSheetElementTransaction.setDescription(branchInvestment.getDescription());
			balanceSheetElementTransaction.setTransactionAmount(branchInvestment.getAmount());
			balanceSheetElementTransaction.setTransactionDate(branchInvestment.getInvestmentDate());
			balanceSheetElementDOInvestment.addBalanceSheetElementTransaction(balanceSheetElementTransaction);
		}
		branchBalanceSheetIncomeElements.add(balanceSheetElementDOInvestment);

		final BalanceSheetElementDO balanceSheetElementDOCreditAccount = new BalanceSheetElementDO();
		balanceSheetElementDOCreditAccount.setDescription("Credit Accounts withdraw");
		final Collection<BranchCreditAccount> branchCreditAccounts = this.branchCreditAccountService.findBranchCreditAccountByBranchId(branchId);
		for (final BranchCreditAccount branchCreditAccount : branchCreditAccounts) {
			final Collection<BranchCreditAccountTransaction> branchCreditAccountTransactions = this.branchCreditAccountTransactionService
					.findBranchCreditAccountTransactionsByFromDateAndToDate(branchCreditAccount.getId(), startDate, endDate,
							EnumSet.of(CreditAccountTransactionTypeConstant.WITHDRAW));
			for (final BranchCreditAccountTransaction branchCreditAccountTransaction : branchCreditAccountTransactions) {
				balanceSheetElementTransaction = new BalanceSheetElementTransaction();
				balanceSheetElementTransaction.setDescription(branchCreditAccountTransaction.getDescription());
				balanceSheetElementTransaction.setTransactionAmount(branchCreditAccountTransaction.getAmount());
				balanceSheetElementTransaction.setTransactionDate(branchCreditAccountTransaction.getTransactionDate());
				balanceSheetElementDOCreditAccount.addBalanceSheetElementTransaction(balanceSheetElementTransaction);
			}
		}
		branchBalanceSheetIncomeElements.add(balanceSheetElementDOCreditAccount);

		final BalanceSheetElementDO balanceSheetElementDOSaveAccount = new BalanceSheetElementDO();
		balanceSheetElementDOSaveAccount.setDescription("Bank Accounts withdraw");
		final Collection<BranchBankAccount> branchBankAccounts = this.branchBankAccountService.findBranchBankAccountByBranchId(branchId);
		for (final BranchBankAccount branchBankAccount : branchBankAccounts) {
			final Collection<BranchBankAccountTransaction> branchBankAccountTransactions = this.branchBankAccountTransactionService
					.findBranchBankAccountTransactionsByFromDateAndToDate(branchBankAccount.getId(), startDate, endDate,
							EnumSet.of(BankAccountTransactionTypeConstant.WITHDRAW));
			for (final BranchBankAccountTransaction branchBankAccountTransaction : branchBankAccountTransactions) {
				balanceSheetElementTransaction = new BalanceSheetElementTransaction();
				balanceSheetElementTransaction.setDescription(branchBankAccountTransaction.getDescription());
				balanceSheetElementTransaction.setTransactionAmount(branchBankAccountTransaction.getAmount());
				balanceSheetElementTransaction.setTransactionDate(branchBankAccountTransaction.getTransactionDate());
				balanceSheetElementDOSaveAccount.addBalanceSheetElementTransaction(balanceSheetElementTransaction);
			}
		}
		branchBalanceSheetIncomeElements.add(balanceSheetElementDOSaveAccount);
		return branchBalanceSheetIncomeElements;
	}

	@Override
	public Collection<BalanceSheetElementDO> getBranchBalanceSheetExpenseElements(final Long branchId, final Date startDate, final Date endDate) {
		final Collection<BalanceSheetElementDO> branchBalanceSheetExpenseElements = new ArrayList<BalanceSheetElementDO>();
		final Branch branch = this.branchService.findBranchById(branchId);

		// Expenses
		final Collection<BranchExpense> branchExpenses = this.branchExpenseService.findBranchExpensesByBranchIdBetweenDates(branchId, startDate, endDate);
		final BalanceSheetElementDO balanceSheetElementDOExpenses = new BalanceSheetElementDO();
		balanceSheetElementDOExpenses.setDescription("Expenses");
		BalanceSheetElementTransaction balanceSheetElementTransaction = null;
		for (final BranchExpense branchExpense : branchExpenses) {
			balanceSheetElementTransaction = new BalanceSheetElementTransaction();
			balanceSheetElementTransaction.setDescription(branchExpense.getDescription());
			balanceSheetElementTransaction.setTransactionAmount(branchExpense.getAmount());
			balanceSheetElementTransaction.setTransactionDate(branchExpense.getExpenseDate());
			balanceSheetElementDOExpenses.addBalanceSheetElementTransaction(balanceSheetElementTransaction);
		}
		branchBalanceSheetExpenseElements.add(balanceSheetElementDOExpenses);

		// Salaries paid
		final EmployeeSalarySearchCriteria employeeSalarySearchCriteria = new EmployeeSalarySearchCriteria(branch);
		employeeSalarySearchCriteria.setFromDate(startDate);
		employeeSalarySearchCriteria.setToDate(endDate);
		final Collection<EmployeeSalary> employeeSalaries = this.employeeSalaryService.findEmployeeSalariesBySearchCriteria(employeeSalarySearchCriteria);

		final BalanceSheetElementDO balanceSheetElementDOSalaries = new BalanceSheetElementDO();
		balanceSheetElementDOSalaries.setDescription("Salaries");
		for (final EmployeeSalary employeeSalary : employeeSalaries) {
			balanceSheetElementTransaction = new BalanceSheetElementTransaction();
			balanceSheetElementTransaction.setDescription(employeeSalary.getEmployee().getDisplayName() + "/"
					+ employeeSalary.getEmployee().getEmployeeNumber());
			balanceSheetElementTransaction.setTransactionAmount(employeeSalary.getAmount());
			balanceSheetElementTransaction.setTransactionDate(employeeSalary.getPaidDate());
			balanceSheetElementDOSalaries.addBalanceSheetElementTransaction(balanceSheetElementTransaction);
		}
		branchBalanceSheetExpenseElements.add(balanceSheetElementDOSalaries);

		final Collection<PurchaseInvoice> purchaseInvoices = this.purchaseInvoiceService.findPurchaseInvoicesByBranchIdAndAcademicYear(branchId, startDate,
				endDate);
		final BalanceSheetElementDO balanceSheetElementDOPurchaseInvoices = new BalanceSheetElementDO();
		balanceSheetElementDOPurchaseInvoices.setDescription("Purchase Invoices");
		for (final PurchaseInvoice purchaseInvoice : purchaseInvoices) {
			balanceSheetElementTransaction = new BalanceSheetElementTransaction();
			balanceSheetElementTransaction.setDescription(purchaseInvoice.getRemarks());
			balanceSheetElementTransaction.setTransactionAmount(purchaseInvoice.getAmount());
			balanceSheetElementTransaction.setTransactionDate(purchaseInvoice.getInvoiceDate());
			balanceSheetElementDOPurchaseInvoices.addBalanceSheetElementTransaction(balanceSheetElementTransaction);
		}
		branchBalanceSheetExpenseElements.add(balanceSheetElementDOPurchaseInvoices);

		final Collection<BranchCreditAccount> branchCreditAccounts = this.branchCreditAccountService.findBranchCreditAccountByBranchId(branchId);
		final BalanceSheetElementDO balanceSheetElementDOCreditAccount = new BalanceSheetElementDO();
		balanceSheetElementDOCreditAccount.setDescription("Credit Accounts deposit");
		for (final BranchCreditAccount branchCreditAccount : branchCreditAccounts) {
			final Collection<BranchCreditAccountTransaction> branchCreditAccountTransactions = this.branchCreditAccountTransactionService
					.findBranchCreditAccountTransactionsByFromDateAndToDate(branchCreditAccount.getId(), startDate, endDate,
							EnumSet.of(CreditAccountTransactionTypeConstant.DEPOSIT));
			for (final BranchCreditAccountTransaction branchCreditAccountTransaction : branchCreditAccountTransactions) {
				balanceSheetElementTransaction = new BalanceSheetElementTransaction();
				balanceSheetElementTransaction.setDescription(branchCreditAccountTransaction.getDescription());
				balanceSheetElementTransaction.setTransactionAmount(branchCreditAccountTransaction.getAmount());
				balanceSheetElementTransaction.setTransactionDate(branchCreditAccountTransaction.getTransactionDate());
				balanceSheetElementDOCreditAccount.addBalanceSheetElementTransaction(balanceSheetElementTransaction);
			}
		}
		branchBalanceSheetExpenseElements.add(balanceSheetElementDOCreditAccount);

		final Collection<BranchBankAccount> branchBankAccounts = this.branchBankAccountService.findBranchBankAccountByBranchId(branchId);
		final BalanceSheetElementDO balanceSheetElementDOBankAccount = new BalanceSheetElementDO();
		balanceSheetElementDOBankAccount.setDescription("Bank Accounts deposit");
		for (final BranchBankAccount branchBankAccount : branchBankAccounts) {
			final Collection<BranchBankAccountTransaction> branchBankAccountTransactions = this.branchBankAccountTransactionService
					.findBranchBankAccountTransactionsByFromDateAndToDate(branchBankAccount.getId(), startDate, endDate,
							EnumSet.of(BankAccountTransactionTypeConstant.DEPOSIT));
			for (final BranchBankAccountTransaction branchBankAccountTransaction : branchBankAccountTransactions) {
				balanceSheetElementTransaction = new BalanceSheetElementTransaction();
				balanceSheetElementTransaction.setDescription(branchBankAccountTransaction.getDescription());
				balanceSheetElementTransaction.setTransactionAmount(branchBankAccountTransaction.getAmount());
				balanceSheetElementTransaction.setTransactionDate(branchBankAccountTransaction.getTransactionDate());
				balanceSheetElementDOBankAccount.addBalanceSheetElementTransaction(balanceSheetElementTransaction);
			}
		}
		branchBalanceSheetExpenseElements.add(balanceSheetElementDOBankAccount);
		return branchBalanceSheetExpenseElements;
	}

	@Override
	public BranchBalanceSheet findBranchBalanceSheetByBranchIdAndDate(final Long branchId, final Date date) {
		return this.branchBalanceSheetDao.findBranchBalanceSheetsByBranchIdAndDate(branchId, date);
	}

	@Override
	public BranchBalanceSheet findActiveBranchBalanceSheetByBranchId(final Long branchId) {
		return this.branchBalanceSheetDao.findActiveBranchBalanceSheetByBranchId(branchId);
	}

	@Override
	public BranchBalanceSheet processBranchBalanceSheet(final BranchBalanceSheet branchBalanceSheet) {
		if (branchBalanceSheet.getEndDate() == null) {
			throw new BusinessException("End date is required.");
		}
		if (branchBalanceSheet.getEndDate().before(branchBalanceSheet.getStartDate())) {
			throw new BusinessException("End date should be on or after start date.");
		}
		if (branchBalanceSheet.getEndDate().after(DateUtil.getSystemDate())) {
			throw new BusinessException("End date should be before current date.");
		}
		branchBalanceSheet.setBalanceSheetClosedIndicator(true);
		return this.saveBranchBalanceSheet(branchBalanceSheet);
	}

	@Override
	public BranchBalanceSheet openClosedBranchBalanceSheet(final BranchBalanceSheet branchBalanceSheet) {
		branchBalanceSheet.setBalanceSheetClosedIndicator(false);
		return this.saveBranchBalanceSheet(branchBalanceSheet);
	}

}
