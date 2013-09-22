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
import java.util.EnumSet;

import com.apeironsol.need.financial.model.BranchCreditAccountTransaction;
import com.apeironsol.need.util.constants.CreditAccountTransactionTypeConstant;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * 
 * @author sunny
 * 
 *         Service interface for BranchCreditAccountTransaction.
 * 
 */
public interface BranchCreditAccountTransactionService {

	/**
	 * Save BranchCreditAccountTransaction.
	 * 
	 * @param BranchCreditAccountTransaction
	 *            BranchCreditAccountTransaction to be saved.
	 * @return
	 */
	BranchCreditAccountTransaction saveBranchCreditAccountTransaction(BranchCreditAccountTransaction branchCreditAccountTransaction);

	/**
	 * Save BranchCreditAccountTransaction.
	 * 
	 * @param BranchCreditAccountTransaction
	 *            BranchCreditAccountTransaction to be saved.
	 * @throws BusinessException
	 * @throws SystemException
	 */
	void removeBranchCreditAccountTransaction(BranchCreditAccountTransaction branchCreditAccountTransaction) throws BusinessException, SystemException;

	/**
	 * Find BranchCreditAccountTransaction by id.
	 * 
	 * @param id
	 *            BranchCreditAccountTransaction id.
	 * @return
	 */
	BranchCreditAccountTransaction findBranchCreditAccountTransactionById(Long id);

	/**
	 * Find BranchCreditAccountTransaction by id.
	 * 
	 * @param id
	 *            BranchCreditAccountTransaction id.
	 * @return
	 */
	BranchCreditAccountTransaction findBranchCreditAccountTransactionByBranchCreditAccountIdAndExternalTransactionNr(Long branchCreditAccountId,
			String externalTransactionNr);

	/**
	 * Find BranchCreditAccountTransaction by branch id.
	 * 
	 * @param id
	 *            BranchCreditAccountTransaction id.
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

}
