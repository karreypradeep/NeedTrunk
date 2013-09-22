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

import com.apeironsol.need.financial.model.BranchBankAccountTransaction;
import com.apeironsol.need.util.constants.BankAccountTransactionTypeConstant;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * 
 * @author sunny
 * 
 *         Service interface for BranchBankAccountTransaction.
 * 
 */
public interface BranchBankAccountTransactionService {

	/**
	 * Save BranchBankAccountTransaction.
	 * 
	 * @param BranchBankAccountTransaction
	 *            BranchBankAccountTransaction to be saved.
	 * @return
	 */
	BranchBankAccountTransaction saveBranchBankAccountTransaction(BranchBankAccountTransaction branchBankAccountTransaction);

	/**
	 * Save BranchBankAccountTransaction.
	 * 
	 * @param BranchBankAccountTransaction
	 *            BranchBankAccountTransaction to be saved.
	 * @throws BusinessException
	 * @throws SystemException
	 */
	void removeBranchBankAccountTransaction(BranchBankAccountTransaction branchBankAccountTransaction) throws BusinessException, SystemException;

	/**
	 * Find BranchBankAccountTransaction by id.
	 * 
	 * @param id
	 *            BranchBankAccountTransaction id.
	 * @return
	 */
	BranchBankAccountTransaction findBranchBankAccountTransactionById(Long id);

	/**
	 * Find BranchBankAccountTransaction by id.
	 * 
	 * @param id
	 *            BranchBankAccountTransaction id.
	 * @return
	 */
	BranchBankAccountTransaction findBranchBankAccountTransactionByBranchBankAccountIdAndExternalTransactionNr(Long branchBankAccountId,
			String externalTransactionNr);

	/**
	 * Find BranchBankAccountTransaction by branch id.
	 * 
	 * @param id
	 *            BranchBankAccountTransaction id.
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

}
