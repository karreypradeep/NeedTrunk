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

import com.apeironsol.need.financial.model.BranchBankAccountTransaction;
import com.apeironsol.need.util.constants.BankAccountTransactionTypeConstant;
import com.apeironsol.framework.BaseDao;

/**
 * 
 * @author sunny
 *         Data access interface for branch bank account implementation.
 * 
 */
public interface BranchBankAccountTransactionDao extends BaseDao<BranchBankAccountTransaction> {
	/**
	 * Find BranchBankAccount by branch id.
	 * 
	 * @param id
	 *            branchBankAccount id.
	 * @return
	 */
	Collection<BranchBankAccountTransaction> findBranchBankAccountTransactionsByBranchBankAccountId(Long branchBankAccountId);

	/**
	 * Find BranchBankAccountTransaction by branch id.
	 * 
	 * @param id
	 *            BranchBankAccountTransaction id.
	 * @return
	 */
	Collection<BranchBankAccountTransaction> findBranchBankAccountTransactionsByFromDateAndToDate(final Long branchBankAccountId, Date fromDate, Date toDate,
			EnumSet<BankAccountTransactionTypeConstant> bankAccountTransactionTypes);

	/**
	 * Find BranchBankAccountTransaction by id.
	 * 
	 * @param id
	 *            BranchBankAccountTransaction id.
	 * @return
	 */
	BranchBankAccountTransaction findBranchBankAccountTransactionByBranchBankAccountIdAndExternalTransactionNr(Long branchBankAccountId,
			String externalTransactionNr);

}
