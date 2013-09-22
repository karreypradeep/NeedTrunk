/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.dao;

import java.util.Collection;

import com.apeironsol.need.financial.model.FinancialYear;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for academic year implementation.
 * 
 * @author Pradeep
 */
public interface FinancialYearDao extends BaseDao<FinancialYear> {

	/**
	 * Find financial years by branch id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return collection of all financial years by branch id.
	 */
	Collection<FinancialYear> findFinancialYearsByBranchId(Long branchId);

}
