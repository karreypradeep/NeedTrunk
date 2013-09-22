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
import java.util.EnumSet;

import com.apeironsol.need.financial.model.BranchCreditAccountTransaction;
import com.apeironsol.need.util.constants.CreditAccountTransactionTypeConstant;
import com.apeironsol.framework.BaseDao;

/**
 * 
 * @author sunny
 *         Data access interface for branch bank account implementation.
 * 
 */
public interface BranchCreditAccountTransactionDao extends BaseDao<BranchCreditAccountTransaction> {
	/**
	 * Find BranchCreditAccount by branch id.
	 * 
	 * @param id
	 *            branchCreditAccount id.
	 * @return
	 */
	Collection<BranchCreditAccountTransaction> findBranchCreditAccountTransactionsByBranchCreditAccountId(Long branchCreditAccountId);

	/**
	 * Find BranchCreditAccountTransaction by branch id.
	 * 
	 * @param id
	 *            BranchCreditAccountTransaction id.
	 * @return
	 */
	Collection<BranchCreditAccountTransaction> findBranchCreditAccountTransactionsByFromDateAndToDate(final Long branchCreditAccountId, Date fromDate,
			Date toDate, EnumSet<CreditAccountTransactionTypeConstant> creditAccountTransactionTypes);

	/**
	 * Find BranchCreditAccountTransaction by id.
	 * 
	 * @param id
	 *            BranchCreditAccountTransaction id.
	 * @return
	 */
	BranchCreditAccountTransaction findBranchCreditAccountTransactionByBranchCreditAccountIdAndExternalTransactionNr(Long branchCreditAccountId,
			String externalTransactionNr);

}
