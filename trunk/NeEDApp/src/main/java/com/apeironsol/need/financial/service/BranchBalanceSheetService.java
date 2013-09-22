/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.service;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.financial.dataobject.BalanceSheetElementDO;
import com.apeironsol.need.financial.model.BranchBalanceSheet;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * 
 * @author pradeep
 * 
 *         Service interface for BranchBalanceSheet.
 * 
 */
public interface BranchBalanceSheetService {

	/**
	 * Save BranchBalanceSheet.
	 * 
	 * @param branchBalanceSheet
	 *            branchBalanceSheet to be saved.
	 * @return
	 */
	BranchBalanceSheet saveBranchBalanceSheet(BranchBalanceSheet branchBalanceSheet);

	/**
	 * Save BranchBalanceSheet.
	 * 
	 * @param branchBalanceSheet
	 *            branchBalanceSheet to be saved.
	 * @return
	 */
	BranchBalanceSheet processBranchBalanceSheet(BranchBalanceSheet branchBalanceSheet);

	/**
	 * Save BranchBalanceSheet.
	 * 
	 * @param branchBalanceSheet
	 *            branchBalanceSheet to be saved.
	 * @throws BusinessException
	 * @throws SystemException
	 */
	void removeBranchBalanceSheet(BranchBalanceSheet branchBalanceSheet) throws BusinessException, SystemException;

	/**
	 * Find BranchBalanceSheet by id.
	 * 
	 * @param id
	 *            branchBalanceSheet id.
	 * @return
	 */
	BranchBalanceSheet findBranchBalanceSheetById(Long id);

	/**
	 * Find BranchBalanceSheet by id.
	 * 
	 * @param id
	 *            branchBalanceSheet id.
	 * @return
	 */
	BranchBalanceSheet findActiveBranchBalanceSheetByBranchId(Long branchId);

	/**
	 * Find BranchBalanceSheet by branch id.
	 * 
	 * @param id
	 *            branchBalanceSheet id.
	 * @return
	 */
	Collection<BranchBalanceSheet> findBranchBalanceSheetsByBranchId(Long branchId);

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
	 *            BranchBalanceSheet id.
	 * @return
	 */
	Collection<BalanceSheetElementDO> getBranchBalanceSheetElements(Long branchId, final Date startDate, final Date endDate);

	/**
	 * Find BranchBalanceSheet by branch id.
	 * 
	 * @param id
	 *            BranchBalanceSheet id.
	 * @return
	 */
	Collection<BalanceSheetElementDO> getBranchBalanceSheetIncomeElements(Long branchId, final Date startDate, final Date endDate);

	/**
	 * Find BranchBalanceSheet by branch id.
	 * 
	 * @param id
	 *            BranchBalanceSheet id.
	 * @return
	 */
	Collection<BalanceSheetElementDO> getBranchBalanceSheetExpenseElements(Long branchId, final Date startDate, final Date endDate);

	/**
	 * Find BranchBalanceSheet by branch id.
	 * 
	 * @param id
	 *            branchBalanceSheet id.
	 * @return
	 */
	BranchBalanceSheet findBranchBalanceSheetByBranchIdAndDate(Long branchId, final Date date);

	/**
	 * Save BranchBalanceSheet.
	 * 
	 * @param branchBalanceSheet
	 *            branchBalanceSheet to be saved.
	 * @return
	 */
	BranchBalanceSheet openClosedBranchBalanceSheet(BranchBalanceSheet branchBalanceSheet);

}
