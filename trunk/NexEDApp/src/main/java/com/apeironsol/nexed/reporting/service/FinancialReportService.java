/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.reporting.service;

import com.apeironsol.nexed.core.model.Branch;
import com.apeironsol.nexed.reporting.ro.FinancialRO;
import com.apeironsol.nexed.util.searchcriteria.BranchExpenseSearchCriteria;


/**
 * Service layer interface for financial report implementation.
 * 
 * @author sunny
 * 
 */
public interface FinancialReportService{

	/**
	 *  Retrieve branch information for search criteria.
	 * @param branch
	 * @param studentSearchCriteria
	 * @return
	 */
	FinancialRO getExpensesReportForBranch(final Branch branch, final BranchExpenseSearchCriteria branchExpenseSearchCriteria);

}
