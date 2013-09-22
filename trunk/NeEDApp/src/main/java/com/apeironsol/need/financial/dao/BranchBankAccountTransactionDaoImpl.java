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

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.financial.model.BranchBankAccountTransaction;
import com.apeironsol.need.util.constants.BankAccountTransactionTypeConstant;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * 
 * @author sunny
 * 
 *         Data access interface for branch bank account implementation.
 * 
 */
@Repository("branchBankAccountTransactionDao")
public class BranchBankAccountTransactionDaoImpl extends BaseDaoImpl<BranchBankAccountTransaction> implements BranchBankAccountTransactionDao {

	@Override
	public Collection<BranchBankAccountTransaction> findBranchBankAccountTransactionsByBranchBankAccountId(final Long branchBankAccountId) {
		final TypedQuery<BranchBankAccountTransaction> query = this.getEntityManager()
				.createQuery("select bbat from BranchBankAccountTransaction bbat where bbat.branchBankAccount.id = :branchBankAccountId",
						BranchBankAccountTransaction.class);
		query.setParameter("branchBankAccountId", branchBankAccountId);
		return query.getResultList();
	}

	@Override
	public Collection<BranchBankAccountTransaction> findBranchBankAccountTransactionsByFromDateAndToDate(final Long branchBankAccountId, final Date fromDate,
			final Date toDate, final EnumSet<BankAccountTransactionTypeConstant> bankAccountTransactionTypes) {
		final TypedQuery<BranchBankAccountTransaction> query = this
				.getEntityManager()
				.createQuery(
						"select bbat from BranchBankAccountTransaction bbat where  bbat.branchBankAccount.id = :branchBankAccountId and bbat.transactionDate >= :fromDate and bbat.transactionDate <= :toDate  and bbat.transactionType in (:bankAccountTransactionTypes)",
						BranchBankAccountTransaction.class);
		query.setParameter("branchBankAccountId", branchBankAccountId);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		query.setParameter("bankAccountTransactionTypes", bankAccountTransactionTypes);
		return query.getResultList();
	}

	@Override
	public BranchBankAccountTransaction findBranchBankAccountTransactionByBranchBankAccountIdAndExternalTransactionNr(final Long branchBankAccountId,
			final String externalTransactionNr) {
		try {
			final TypedQuery<BranchBankAccountTransaction> query = this
					.getEntityManager()
					.createQuery(
							"select bbat from BranchBankAccountTransaction bbat where bbat.branchBankAccount.id = :branchBankAccountId and bbat.externalTransactionNr = :externalTransactionNr",
							BranchBankAccountTransaction.class);
			query.setParameter("externalTransactionNr", externalTransactionNr);
			query.setParameter("branchBankAccountId", branchBankAccountId);
			return query.getSingleResult();
		} catch (final NoResultException nre) {
			return null;
		}
	}

}
