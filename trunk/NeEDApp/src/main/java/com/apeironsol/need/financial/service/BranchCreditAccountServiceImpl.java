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
import com.apeironsol.need.financial.dao.BranchCreditAccountDao;
import com.apeironsol.need.financial.model.BranchCreditAccount;
import com.apeironsol.need.financial.model.BranchCreditAccountTransaction;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.CreditAccountTransactionTypeConstant;
import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * 
 * @author sunny
 * 
 *         Service interface implementation for BranchCreditAccount.
 * 
 */
@Service("branchCreditAccountService")
@Transactional(rollbackFor = Exception.class)
public class BranchCreditAccountServiceImpl implements BranchCreditAccountService {

	@Resource
	private BranchCreditAccountDao					branchCreditAccountDao;

	@Resource
	private SequenceGeneratorService				sequenceGeneratorService;

	@Resource
	private BranchCreditAccountTransactionService	branchCreditAccountTransactionService;

	@Override
	public BranchCreditAccount saveBranchCreditAccount(final BranchCreditAccount branchCreditAccount) {
		return this.branchCreditAccountDao.persist(branchCreditAccount);
	}

	@Override
	public void removeBranchCreditAccount(final BranchCreditAccount branchCreditAccount) throws BusinessException, SystemException {
		final Collection<BranchCreditAccountTransaction> branchCreditAccountTransactions = this.branchCreditAccountTransactionService
				.findBranchCreditAccountTransactionsByBranchCreditAccountId(branchCreditAccount.getId());
		if (branchCreditAccountTransactions != null && branchCreditAccountTransactions.size() > 0) {
			throw new ApplicationException("Credit account cannot be deleted as transactions have be found.");
		}
		this.branchCreditAccountDao.remove(branchCreditAccount);
	}

	@Override
	public BranchCreditAccount findBranchCreditAccountById(final Long id) {
		return this.branchCreditAccountDao.findById(id);
	}

	@Override
	public Collection<BranchCreditAccount> findAllBranchCreditAccounts() {
		return this.branchCreditAccountDao.findAll();
	}

	@Override
	public Collection<BranchCreditAccount> findBranchCreditAccountByBranchId(final Long branchId) {
		return this.branchCreditAccountDao.findBranchCreditAccountByBranchId(branchId);
	}

	@Override
	public BranchCreditAccount processBranchCreditAccountTransaction(final Long branchCreditAccountId,
			final CreditAccountTransactionTypeConstant creditAccountTransactionTypeConstant, final Double amount, final String description,
			final String externalTransactionNumber, final Date transactionDate) {

		if (amount == null || amount <= 0) {
			throw new ApplicationException(" Transaction amount cannot be null.");
		}
		final BranchCreditAccount branchCreditAccount = this.branchCreditAccountDao.findById(branchCreditAccountId);
		if (transactionDate != null) {
			if (transactionDate.before(branchCreditAccount.getAccountOpeningDate())) {
				throw new ApplicationException("Transaction date " + transactionDate + " is before account opening date "
						+ branchCreditAccount.getAccountOpeningDate());
			}
		}
		if (CreditAccountTransactionTypeConstant.WITHDRAW.equals(creditAccountTransactionTypeConstant)) {
			if (branchCreditAccount.getAccountBalance() < amount) {
				throw new ApplicationException(" Transaction amount " + amount + " for with draw cannot be more than balance available "
						+ branchCreditAccount.getAccountBalance() + "  for Credit Account with number " + branchCreditAccount.getAccountNumber());
			}
			branchCreditAccount.setAccountBalance(branchCreditAccount.getAccountBalance() - amount);
		} else {
			branchCreditAccount.setAccountBalance(branchCreditAccount.getAccountBalance() + amount);
		}

		final String transactionNr = this.sequenceGeneratorService.getNextTransactionNumberForCreditAccountTransaction();
		final BranchCreditAccountTransaction branchCreditAccountTransaction = new BranchCreditAccountTransaction();
		branchCreditAccountTransaction.setAmount(amount);
		branchCreditAccountTransaction.setBranchCreditAccount(branchCreditAccount);
		branchCreditAccountTransaction.setDescription(creditAccountTransactionTypeConstant.getLabel() + (description != null ? ("/" + description) : ""));
		branchCreditAccountTransaction.setTransactionDate(transactionDate != null ? transactionDate : DateUtil.getSystemDate());
		branchCreditAccountTransaction.setTransactionType(creditAccountTransactionTypeConstant);
		branchCreditAccountTransaction.setTransactionNr(transactionNr);
		branchCreditAccountTransaction.setExternalTransactionNr(externalTransactionNumber);
		this.branchCreditAccountTransactionService.saveBranchCreditAccountTransaction(branchCreditAccountTransaction);
		this.saveBranchCreditAccount(branchCreditAccount);
		return branchCreditAccount;
	}

}
