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

import com.apeironsol.need.financial.model.BranchBankAccount;
import com.apeironsol.need.util.constants.BankAccountTransactionTypeConstant;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * 
 * @author sunny
 * 
 *         Service interface for BranchBankAccount.
 * 
 */
public interface BranchBankAccountService {

	/**
	 * Save BranchBankAccount.
	 * 
	 * @param branchBankAccount
	 *            branchBankAccount to be saved.
	 * @return
	 */
	BranchBankAccount saveBranchBankAccount(BranchBankAccount branchBankAccount);

	/**
	 * Save BranchBankAccount.
	 * 
	 * @param branchBankAccount
	 *            branchBankAccount to be saved.
	 * @throws BusinessException
	 * @throws SystemException
	 */
	void removeBranchBankAccount(BranchBankAccount branchBankAccount) throws BusinessException, SystemException;

	/**
	 * Find BranchBankAccount by id.
	 * 
	 * @param id
	 *            branchBankAccount id.
	 * @return
	 */
	BranchBankAccount findBranchBankAccountById(Long id);

	/**
	 * Find all branchBankAccounts.
	 * 
	 * @return
	 */
	Collection<BranchBankAccount> findAllBranchBankAccounts();

	/**
	 * Find BranchBankAccount by branch id.
	 * 
	 * @param id
	 *            branchBankAccount id.
	 * @return
	 */
	Collection<BranchBankAccount> findBranchBankAccountByBranchId(Long branchId);

	/**
	 * Save BranchBankAccount.
	 * 
	 * @param branchBankAccount
	 *            branchBankAccount to be saved.
	 * @return
	 */
	BranchBankAccount processBranchBankAccountTransaction(final Long branchBankAccountId, BankAccountTransactionTypeConstant accountTransactionTypeConstant,
			Double amount, final String description, final String externalTransactionNumber, final Date transactionDate);

}
