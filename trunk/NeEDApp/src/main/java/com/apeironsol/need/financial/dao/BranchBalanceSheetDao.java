/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.dao;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.financial.model.BranchBalanceSheet;
import com.apeironsol.framework.BaseDao;

/**
 * 
 * @author pradeep
 *         Data access interface for branch bank account implementation.
 * 
 */
public interface BranchBalanceSheetDao extends BaseDao<BranchBalanceSheet> {

	/**
	 * Find BranchBalanceSheet by branch id.
	 * 
	 * @param id
	 *            BranchBalanceSheet id.
	 * @return
	 */
	Collection<BranchBalanceSheet> findBranchBalanceSheetByBranchId(Long branchId);

	/**
	 * Find BranchBalanceSheet by branch id.
	 * 
	 * @param id
	 *            BranchBalanceSheet id.
	 * @return
	 */
	BranchBalanceSheet findLatestBranchBalanceSheetByBranchIdAndProcessedState(Long branchId, final Boolean balanceSheetProcessed);

	/**
	 * Find BranchBalanceSheet by branch id.
	 * 
	 * @param id
	 *            branchBalanceSheet id.
	 * @return
	 */
	BranchBalanceSheet findBranchBalanceSheetsByBranchIdAndDate(Long branchId, final Date date);

	BranchBalanceSheet findActiveBranchBalanceSheetByBranchId(Long branchId);
}
