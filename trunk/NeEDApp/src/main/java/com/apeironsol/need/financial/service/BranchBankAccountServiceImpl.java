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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.service.SequenceGeneratorService;
import com.apeironsol.need.financial.dao.BranchBankAccountDao;
import com.apeironsol.need.financial.model.BranchBankAccount;
import com.apeironsol.need.financial.model.BranchBankAccountTransaction;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.BankAccountTransactionTypeConstant;
import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * 
 * @author sunny
 * 
 *         Service interface implementation for BranchBankAccount.
 * 
 */
@Service("branchBankAccountService")
@Transactional(rollbackFor = Exception.class)
public class BranchBankAccountServiceImpl implements BranchBankAccountService {

	@Resource
	private BranchBankAccountDao				branchBankAccountDao;

	@Resource
	private SequenceGeneratorService			sequenceGeneratorService;

	@Resource
	private BranchBankAccountTransactionService	branchBankAccountTransactionService;

	@Override
	public BranchBankAccount saveBranchBankAccount(final BranchBankAccount branchBankAccount) {
		return this.branchBankAccountDao.persist(branchBankAccount);
	}

	@Override
	public void removeBranchBankAccount(final BranchBankAccount branchBankAccount) throws BusinessException, SystemException {
		final Collection<BranchBankAccountTransaction> branchBankAccountTransactions = this.branchBankAccountTransactionService
				.findBranchBankAccountTransactionsByBranchBankAccountId(branchBankAccount.getId());
		if (branchBankAccountTransactions != null && branchBankAccountTransactions.size() > 0) {
			throw new ApplicationException("Bank account cannot be deleted as transactions have be found.");
		}
		this.branchBankAccountDao.remove(branchBankAccount);
	}

	@Override
	public BranchBankAccount findBranchBankAccountById(final Long id) {
		return this.branchBankAccountDao.findById(id);
	}

	@Override
	public Collection<BranchBankAccount> findAllBranchBankAccounts() {
		return this.branchBankAccountDao.findAll();
	}

	@Override
	public Collection<BranchBankAccount> findBranchBankAccountByBranchId(final Long branchId) {
		return this.branchBankAccountDao.findBranchBankAccountByBranchId(branchId);
	}

	@Override
	public BranchBankAccount processBranchBankAccountTransaction(final Long branchBankAccountId,
			final BankAccountTransactionTypeConstant accountTransactionTypeConstant, final Double amount, final String description,
			final String externalTransactionNumber, final Date transactionDate) {
		if (amount == null) {
			throw new ApplicationException(" Transaction amount cannot be null.");
		}
		final BranchBankAccount branchBankAccount = this.branchBankAccountDao.findById(branchBankAccountId);
		if (transactionDate != null) {
			if (transactionDate.before(branchBankAccount.getAccountOpeningDate())) {
				throw new ApplicationException("Transaction date " + transactionDate + " is before account opening date "
						+ branchBankAccount.getAccountOpeningDate());
			}
		}
		final String transactionNr = this.sequenceGeneratorService.getNextTransactionNumberForBankAccountTransaction();
		final BranchBankAccountTransaction branchBankAccountTransaction = new BranchBankAccountTransaction();
		branchBankAccountTransaction.setAmount(amount);
		branchBankAccountTransaction.setBranchBankAccount(branchBankAccount);
		branchBankAccountTransaction.setDescription(accountTransactionTypeConstant.getLabel() + (description != null ? ("/" + description) : ""));
		branchBankAccountTransaction.setTransactionDate(transactionDate != null ? transactionDate : DateUtil.getSystemDate());
		branchBankAccountTransaction.setTransactionType(accountTransactionTypeConstant);
		branchBankAccountTransaction.setTransactionNr(transactionNr);
		branchBankAccountTransaction.setExternalTransactionNr(externalTransactionNumber);
		this.branchBankAccountTransactionService.saveBranchBankAccountTransaction(branchBankAccountTransaction);
		return branchBankAccount;
	}

}
