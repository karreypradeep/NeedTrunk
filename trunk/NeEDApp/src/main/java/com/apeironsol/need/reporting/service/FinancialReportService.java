/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.reporting.service;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.reporting.ro.FinancialRO;
import com.apeironsol.need.util.searchcriteria.BranchExpenseSearchCriteria;


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
