/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.service;

import java.util.Collection;

import com.apeironsol.need.financial.model.FinancialYear;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

public interface FinancialYearService {

	/**
	 * Find financial year by id.
	 * 
	 * @param id
	 *            financial year id.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 */
	FinancialYear findFinancialYearById(Long id) throws BusinessException;

	/**
	 * Save financial year.
	 * 
	 * @param financialYear
	 *            financial year.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 *             In case of exception.
	 */
	FinancialYear saveFinancialYear(FinancialYear financialYear) throws BusinessException, InvalidArgumentException;

	/**
	 * Remove financial year.
	 * 
	 * @param financialYear
	 *            financial year.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeFinancialYear(FinancialYear financialYear) throws BusinessException;

	/**
	 * Find all financial years.
	 * 
	 * @return collection of all financial years.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<FinancialYear> findAllFinancialYears() throws BusinessException;

	/**
	 * Find all financial years by branch id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<FinancialYear> findFinancialYearsByBranchId(final Long branchId) throws BusinessException;

}
