/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;
import java.util.Date;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.core.model.BranchExpenseTypePeriodical;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for branch expense entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("branchExpenseTypePeriodicalDao")
public class BranchExpenseTypePeriodicalDaoImpl extends BaseDaoImpl<BranchExpenseTypePeriodical> implements
		BranchExpenseTypePeriodicalDao {

	private static final Logger	log	= Logger.getLogger(BranchExpenseTypePeriodicalDaoImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchExpenseTypePeriodical> findBranchExpenseTypePeriodicalsByBranchAssembly(
			final BranchAssembly branchAssembly) {

		try {
			TypedQuery<BranchExpenseTypePeriodical> query = this
					.getEntityManager()
					.createQuery(
							"select bf from BranchExpenseTypePeriodical bf where bf.branchAssembly = :branchAssembly order by bf.startDate",
							BranchExpenseTypePeriodical.class);
			query.setParameter("branchAssembly", branchAssembly);
			return query.getResultList();
		} catch (NoResultException e) {
			log.info(e.getMessage());
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchExpenseTypePeriodical findCurrentBranchExpenseTypePeriodicalByBranchAssembly(
			final BranchAssembly branchAssembly) {
		try {
			TypedQuery<BranchExpenseTypePeriodical> query = this
					.getEntityManager()
					.createQuery(
							"select bft from BranchExpenseTypePeriodical bft where bft.branchAssembly = :branchAssembly and bft.endDate is NULL",
							BranchExpenseTypePeriodical.class);
			query.setParameter("branchAssembly", branchAssembly);
			return query.getSingleResult();
		} catch (NoResultException e) {
			log.info(e.getMessage());
			return null;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchExpenseTypePeriodical findBranchExpenseByBranchAssemblyAndEndDate(final BranchAssembly branchAssembly,
			final Date referenceDate) {
		try {
			TypedQuery<BranchExpenseTypePeriodical> query = this
					.getEntityManager()
					.createQuery(
							"select bft from BranchExpenseTypePeriodical bft where bft.branchAssembly = :branchAssembly and bft.endDate = :referenceDate",
							BranchExpenseTypePeriodical.class);
			query.setParameter("branchAssembly", branchAssembly);
			query.setParameter("referenceDate", referenceDate);
			return query.getSingleResult();
		} catch (NoResultException e) {
			log.info(e.getMessage());
			return null;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchExpenseTypePeriodical findLatestBranchExpenseTypePeriodicalByBranchAssembly(
			final BranchAssembly branchAssembly) {
		try {
			TypedQuery<BranchExpenseTypePeriodical> query = this
					.getEntityManager()
					.createQuery(
							"select bft from BranchExpenseTypePeriodical bft where bft.branchAssembly = :branchAssembly and bft.startDate = (select MAX(e.startDate) from BranchExpenseTypePeriodical e where e.branchAssembly = :branchAssembly)",
							BranchExpenseTypePeriodical.class);
			query.setParameter("branchAssembly", branchAssembly);
			return query.getSingleResult();
		} catch (NoResultException e) {
			log.info(e.getMessage());
			return null;
		}

	}

}
