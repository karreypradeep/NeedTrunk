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

import com.apeironsol.need.financial.model.BranchCreditAccountTransaction;
import com.apeironsol.need.util.constants.CreditAccountTransactionTypeConstant;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * 
 * @author sunny
 * 
 *         Data access interface for branch bank account implementation.
 * 
 */
@Repository("branchCreditAccountTransactionDao")
public class BranchCreditAccountTransactionDaoImpl extends BaseDaoImpl<BranchCreditAccountTransaction> implements BranchCreditAccountTransactionDao {

	@Override
	public Collection<BranchCreditAccountTransaction> findBranchCreditAccountTransactionsByBranchCreditAccountId(final Long branchCreditAccountId) {
		final TypedQuery<BranchCreditAccountTransaction> query = this.getEntityManager().createQuery(
				"select bcat from BranchCreditAccountTransaction bcat where bcat.branchCreditAccount.id = :branchCreditAccountId",
				BranchCreditAccountTransaction.class);
		query.setParameter("branchCreditAccountId", branchCreditAccountId);
		return query.getResultList();
	}

	@Override
	public Collection<BranchCreditAccountTransaction> findBranchCreditAccountTransactionsByFromDateAndToDate(final Long branchCreditAccountId,
			final Date fromDate, final Date toDate, final EnumSet<CreditAccountTransactionTypeConstant> creditAccountTransactionTypes) {
		final TypedQuery<BranchCreditAccountTransaction> query = this
				.getEntityManager()
				.createQuery(
						"select bcat from BranchCreditAccountTransaction bcat where  bcat.branchCreditAccount.id = :branchCreditAccountId and bcat.transactionDate >= :fromDate and bcat.transactionDate <= :toDate and bcat.transactionType in (:creditAccountTransactionTypes)",
						BranchCreditAccountTransaction.class);
		query.setParameter("branchCreditAccountId", branchCreditAccountId);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		query.setParameter("creditAccountTransactionTypes", creditAccountTransactionTypes);
		return query.getResultList();
	}

	@Override
	public BranchCreditAccountTransaction findBranchCreditAccountTransactionByBranchCreditAccountIdAndExternalTransactionNr(final Long branchCreditAccountId,
			final String externalTransactionNr) {
		try {
			final TypedQuery<BranchCreditAccountTransaction> query = this
					.getEntityManager()
					.createQuery(
							"select bcat from BranchCreditAccountTransaction bcat where bcat.branchCreditAccount.id = :branchCreditAccountId and bcat.externalTransactionNr = :externalTransactionNr",
							BranchCreditAccountTransaction.class);
			query.setParameter("branchCreditAccountId", branchCreditAccountId);
			query.setParameter("externalTransactionNr", externalTransactionNr);
			return query.getSingleResult();
		} catch (final NoResultException nre) {
			return null;
		}
	}

}
