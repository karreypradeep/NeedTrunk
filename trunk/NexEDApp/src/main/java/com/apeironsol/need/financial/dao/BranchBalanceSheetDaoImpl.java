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

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.financial.model.BranchBalanceSheet;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * 
 * @author pradeep
 * 
 *         Data access interface for branch bank account implementation.
 * 
 */
@Repository("branchBalanceSheetDao")
public class BranchBalanceSheetDaoImpl extends BaseDaoImpl<BranchBalanceSheet> implements BranchBalanceSheetDao {

	@Override
	public Collection<BranchBalanceSheet> findBranchBalanceSheetByBranchId(final Long branchId) {
		final TypedQuery<BranchBalanceSheet> query = getEntityManager().createQuery("select bbs from BranchBalanceSheet bbs where bbs.branch.id = :branchId",
				BranchBalanceSheet.class);
		query.setParameter("branchId", branchId);
		return query.getResultList();
	}

	@Override
	public BranchBalanceSheet findLatestBranchBalanceSheetByBranchIdAndProcessedState(final Long branchId, final Boolean balanceSheetProcessed) {
		try {
			final TypedQuery<BranchBalanceSheet> query = getEntityManager()
					.createQuery(
							"select bbs from BranchBalanceSheet bbs where bbs.branch.id = :branchId and bbs.balanceSheetClosedIndicator = :balanceSheetClosedIndicator and bbs.endDate = (select max(bbs1.endDate) from BranchBalanceSheet bbs1 where bbs1.branch.id = :branchId and bbs1.balanceSheetClosedIndicator = :balanceSheetClosedIndicator)",
							BranchBalanceSheet.class);
			query.setParameter("branchId", branchId);
			query.setParameter("balanceSheetClosedIndicator", balanceSheetProcessed);
			return query.getSingleResult();
		} catch (final NoResultException nre) {
			return null;
		}
	}

	@Override
	public BranchBalanceSheet findBranchBalanceSheetsByBranchIdAndDate(final Long branchId, final Date date) {
		try {
			final TypedQuery<BranchBalanceSheet> query = getEntityManager().createQuery(
					"select bbs from BranchBalanceSheet bbs where bbs.branch.id = :branchId and bbs.startDate <= :date and bbs.endDate >= :date",
					BranchBalanceSheet.class);
			query.setParameter("branchId", branchId);
			query.setParameter("date", date);
			return query.getSingleResult();
		} catch (final NoResultException nre) {
			return null;
		}
	}

	@Override
	public BranchBalanceSheet findActiveBranchBalanceSheetByBranchId(final Long branchId) {
		try {
			final TypedQuery<BranchBalanceSheet> query = getEntityManager().createQuery(
					"select bbs from BranchBalanceSheet bbs where bbs.branch.id = :branchId and bbs.balanceSheetClosedIndicator = :balanceSheetClosedIndicator",
					BranchBalanceSheet.class);
			query.setParameter("branchId", branchId);
			query.setParameter("balanceSheetClosedIndicator", Boolean.FALSE);
			return query.getSingleResult();
		} catch (final NoResultException nre) {
			return null;
		}
	}

}
