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

import com.apeironsol.need.financial.model.BranchCreditAccount;
import com.apeironsol.need.util.constants.CreditAccountTransactionTypeConstant;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * 
 * @author sunny
 * 
 *         Service interface for BranchCreditAccount.
 * 
 */
public interface BranchCreditAccountService {

	/**
	 * Save BranchCreditAccount.
	 * 
	 * @param branchCreditAccount
	 *            branchCreditAccount to be saved.
	 * @return
	 */
	BranchCreditAccount saveBranchCreditAccount(BranchCreditAccount branchCreditAccount);

	/**
	 * Save BranchCreditAccount.
	 * 
	 * @param branchCreditAccount
	 *            branchCreditAccount to be saved.
	 * @throws BusinessException
	 * @throws SystemException
	 */
	void removeBranchCreditAccount(BranchCreditAccount branchCreditAccount) throws BusinessException, SystemException;

	/**
	 * Find BranchCreditAccount by id.
	 * 
	 * @param id
	 *            branchCreditAccount id.
	 * @return
	 */
	BranchCreditAccount findBranchCreditAccountById(Long id);

	/**
	 * Find all branchCreditAccounts.
	 * 
	 * @return
	 */
	Collection<BranchCreditAccount> findAllBranchCreditAccounts();

	/**
	 * Find BranchCreditAccount by branch id.
	 * 
	 * @param id
	 *            branchCreditAccount id.
	 * @return
	 */
	Collection<BranchCreditAccount> findBranchCreditAccountByBranchId(Long branchId);

	/**
	 * Save BranchCreditAccount.
	 * 
	 * @param branchCreditAccount
	 *            branchCreditAccount to be saved.
	 * @return
	 */
	BranchCreditAccount processBranchCreditAccountTransaction(final Long branchCreditAccountId,
			CreditAccountTransactionTypeConstant creditAccountTransactionTypeConstant, Double amount, final String description,
			final String externalTransactionNumber, final Date transactionDate);

}
