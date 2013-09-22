/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.financial.service;

import java.util.Date;

import com.apeironsol.need.util.dataobject.BranchFinancialDO;
import com.apeironsol.need.util.searchcriteria.FeeDueSearchCriteria;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service for branch financial details.
 * 
 * @author Pradeep
 * 
 */
public interface BranchFinancialService {

	/**
	 * Retrieve branch fee financial details by academic year.
	 * 
	 * @param academicYearId
	 *            academic year id.
	 * @return branch fee financial details by academic year.
	 */
	BranchFinancialDO getBranchFeeFinancialDetailsByAcademicYearId(final Long branchId, final Long academicYearId);

	/**
	 * Retrieve branch fee financial details by academic year.
	 * 
	 * @param academicYearId
	 *            academic year id.
	 * @return branch fee financial details by academic year.
	 */
	BranchFinancialDO getBranchFeeFinancialDetailsByAcademicYearIdForDueDate(final Long branchId, final Long academicYearId, final Date dueDate);

	/**
	 * Retrieve branch fee financial details by FeeDueSearchCriteria.
	 * 
	 * @param feeDueSearchCriteria
	 *            feeDueSearchCriteria.
	 * @return branch fee financial details by feeDueSearchCriteria.
	 */
	BranchFinancialDO getBranchFeeFinancialDetailsBySearchCriteria(final FeeDueSearchCriteria feeDueSearchCriteria);

	/**
	 * Returns true if transactions are allowed for the date.
	 * 
	 * @param branchId branch id.
	 * @param transactionDate transaction date.
	 * @return
	 * @throws BusinessException In case of exception.
	 */
	void checkIfBranchFinancialTransactionsAreAllowed(final Long branchId, final Date transactionDate) throws BusinessException;
}
