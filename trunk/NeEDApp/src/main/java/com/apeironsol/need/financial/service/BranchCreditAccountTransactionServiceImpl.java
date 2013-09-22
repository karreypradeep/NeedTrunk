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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.financial.dao.BranchCreditAccountDao;
import com.apeironsol.need.financial.dao.BranchCreditAccountTransactionDao;
import com.apeironsol.need.financial.model.BranchCreditAccount;
import com.apeironsol.need.financial.model.BranchCreditAccountTransaction;
import com.apeironsol.need.util.constants.CreditAccountTransactionTypeConstant;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * 
 * @author sunny
 * 
 *         Service interface implementation for BranchCreditAccountTransaction.
 * 
 */
@Service("branchCreditAccountTransactionService")
@Transactional
public class BranchCreditAccountTransactionServiceImpl implements BranchCreditAccountTransactionService {

	@Resource
	private BranchCreditAccountTransactionDao	branchCreditAccountTransactionDao;

	@Resource
	private BranchCreditAccountService			branchCreditAccountService;

	@Resource
	private BranchCreditAccountDao				branchCreditAccountDao;

	@Override
	public BranchCreditAccountTransaction saveBranchCreditAccountTransaction(final BranchCreditAccountTransaction branchCreditAccountTransaction) {
		return this.branchCreditAccountTransactionDao.persist(branchCreditAccountTransaction);
	}

	@Override
	public void removeBranchCreditAccountTransaction(final BranchCreditAccountTransaction branchCreditAccountTransaction) throws BusinessException,
			SystemException {
		final BranchCreditAccount branchCreditAccount = branchCreditAccountTransaction.getBranchCreditAccount();
		final double accountBalance = branchCreditAccount.getAccountBalance();
		branchCreditAccount.setAccountBalance(accountBalance + branchCreditAccountTransaction.getAmount());
		this.branchCreditAccountService.saveBranchCreditAccount(branchCreditAccount);
		this.branchCreditAccountTransactionDao.remove(branchCreditAccountTransaction);
	}

	@Override
	public BranchCreditAccountTransaction findBranchCreditAccountTransactionById(final Long id) {
		return this.branchCreditAccountTransactionDao.findById(id);
	}

	@Override
	public Collection<BranchCreditAccountTransaction> findBranchCreditAccountTransactionsByBranchCreditAccountId(final Long branchCreditAccountId) {
		return this.branchCreditAccountTransactionDao.findBranchCreditAccountTransactionsByBranchCreditAccountId(branchCreditAccountId);
	}

	@Override
	public Collection<BranchCreditAccountTransaction> findBranchCreditAccountTransactionsByFromDateAndToDate(final Long branchCreditAccountId,
			final Date fromDate, final Date toDate, final EnumSet<CreditAccountTransactionTypeConstant> creditAccountTransactionTypes) {
		return this.branchCreditAccountTransactionDao.findBranchCreditAccountTransactionsByFromDateAndToDate(branchCreditAccountId, fromDate, toDate,
				creditAccountTransactionTypes);
	}

	@Override
	public BranchCreditAccountTransaction findBranchCreditAccountTransactionByBranchCreditAccountIdAndExternalTransactionNr(final Long branchCreditAccountId,
			final String externalTransactionNr) {
		return this.branchCreditAccountTransactionDao.findBranchCreditAccountTransactionByBranchCreditAccountIdAndExternalTransactionNr(branchCreditAccountId,
				externalTransactionNr);
	}

}
