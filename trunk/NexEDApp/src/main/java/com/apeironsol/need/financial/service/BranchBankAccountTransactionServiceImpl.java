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

import com.apeironsol.need.financial.dao.BranchBankAccountDao;
import com.apeironsol.need.financial.dao.BranchBankAccountTransactionDao;
import com.apeironsol.need.financial.model.BranchBankAccountTransaction;
import com.apeironsol.need.util.constants.BankAccountTransactionTypeConstant;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * 
 * @author sunny
 * 
 *         Service interface implementation for BranchBankAccountTransaction.
 * 
 */
@Service("branchBankAccountTransactionService")
@Transactional
public class BranchBankAccountTransactionServiceImpl implements BranchBankAccountTransactionService {

	@Resource
	private BranchBankAccountTransactionDao	branchBankAccountTransactionDao;

	@Resource
	private BranchBankAccountDao			branchBankAccountDao;

	@Override
	public BranchBankAccountTransaction saveBranchBankAccountTransaction(final BranchBankAccountTransaction branchBankAccountTransaction) {
		return this.branchBankAccountTransactionDao.persist(branchBankAccountTransaction);
	}

	@Override
	public void removeBranchBankAccountTransaction(final BranchBankAccountTransaction branchBankAccountTransaction) throws BusinessException, SystemException {
		this.branchBankAccountTransactionDao.remove(branchBankAccountTransaction);
	}

	@Override
	public BranchBankAccountTransaction findBranchBankAccountTransactionById(final Long id) {
		return this.branchBankAccountTransactionDao.findById(id);
	}

	@Override
	public Collection<BranchBankAccountTransaction> findBranchBankAccountTransactionsByBranchBankAccountId(final Long BranchBankAccountId) {
		return this.branchBankAccountTransactionDao.findBranchBankAccountTransactionsByBranchBankAccountId(BranchBankAccountId);
	}

	@Override
	public Collection<BranchBankAccountTransaction> findBranchBankAccountTransactionsByFromDateAndToDate(final Long branchBankAccountId, final Date fromDate,
			final Date toDate, final EnumSet<BankAccountTransactionTypeConstant> bankAccountTransactionTypes) {
		return this.branchBankAccountTransactionDao.findBranchBankAccountTransactionsByFromDateAndToDate(branchBankAccountId, fromDate, toDate,
				bankAccountTransactionTypes);
	}

	@Override
	public BranchBankAccountTransaction findBranchBankAccountTransactionByBranchBankAccountIdAndExternalTransactionNr(final Long branchBankAccountId,
			final String externalTransactionNr) {
		return this.branchBankAccountTransactionDao.findBranchBankAccountTransactionByBranchBankAccountIdAndExternalTransactionNr(branchBankAccountId,
				externalTransactionNr);
	}
}
