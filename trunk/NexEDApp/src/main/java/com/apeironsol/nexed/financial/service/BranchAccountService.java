/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.financial.service;

import com.apeironsol.nexed.financial.model.BranchExpense;
import com.apeironsol.nexed.payroll.model.EmployeeSalary;
import com.apeironsol.nexed.procurement.model.PurchaseInvoice;
import com.apeironsol.nexed.util.constants.BranchAccountTypeConstant;

/**
 * 
 * @author sunny
 * 
 *         Service interface for BranchBankAccount.
 * 
 */
public interface BranchAccountService {
	void updateBranchAccountTransaction(final BranchExpense branchExpense);

	void updateBranchAccountTransaction(final PurchaseInvoice purchaseInvoice);

	void updateBranchAccountTransaction(final EmployeeSalary employeeSalary);

	void deleteBranchAccountTransactionByExternalTransactionAndAccountId(final BranchAccountTypeConstant branchAccountType, final Long accountId,
			final String externalTransactionNr);
}
