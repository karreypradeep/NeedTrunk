/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.reporting.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.nexed.core.model.Branch;
import com.apeironsol.nexed.financial.model.BranchExpense;
import com.apeironsol.nexed.financial.service.BranchExpenseService;
import com.apeironsol.nexed.reporting.ro.ExpenseRO;
import com.apeironsol.nexed.reporting.ro.FinancialRO;
import com.apeironsol.nexed.util.searchcriteria.BranchExpenseSearchCriteria;

/**
 * Data access interface for supplier entity implementation.
 * 
 * @author sunny
 * 
 */
@Service("financialReportService")
@Transactional
public class FinancialReportServiceImpl implements FinancialReportService {

	/**
	 * Branch expense service.
	 */
	@Resource
	private BranchExpenseService	branchExpenseService;

	@Override
	public FinancialRO getExpensesReportForBranch(final Branch branch, final BranchExpenseSearchCriteria branchExpenseSearchCriteria) {
		Collection<BranchExpense> branchExpenseList = this.branchExpenseService.findBranchExpensesBySearchCriteria(branchExpenseSearchCriteria);
		FinancialRO finalcialRO = new FinancialRO();
		ExpenseRO expenseRO = new ExpenseRO();
		for (BranchExpense branchExpense : branchExpenseList) {
			expenseRO.addBranchExpense(branchExpense);
		}
		finalcialRO.setExpenseRO(expenseRO);
		return finalcialRO;
	}

}
