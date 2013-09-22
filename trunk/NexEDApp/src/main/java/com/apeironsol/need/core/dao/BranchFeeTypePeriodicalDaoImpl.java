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
import com.apeironsol.need.core.model.BranchFeeTypePeriodical;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for branch fee type periodicals
 * 
 * @author Pradeep
 * 
 */
@Repository("branchFeeTypePeriodicalDao")
public class BranchFeeTypePeriodicalDaoImpl extends BaseDaoImpl<BranchFeeTypePeriodical> implements
		BranchFeeTypePeriodicalDao {

	private static final Logger	log	= Logger.getLogger(BranchFeeTypePeriodicalDaoImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchFeeTypePeriodical> findBranchFeeTypePeriodicalsByBranchAssembly(
			final BranchAssembly branchAssembly) {

		try {
			TypedQuery<BranchFeeTypePeriodical> query = this
					.getEntityManager()
					.createQuery(
							"select bf from BranchFeeTypePeriodical bf where bf.branchAssembly = :branchAssembly order by bf.startDate",
							BranchFeeTypePeriodical.class);
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
	public BranchFeeTypePeriodical findActiveBranchFeeTypePeriodicalByBranchAssembly(final BranchAssembly branchAssembly) {
		try {
			TypedQuery<BranchFeeTypePeriodical> query = this
					.getEntityManager()
					.createQuery(
							"select bft from BranchFeeTypePeriodical bft where bft.branchAssembly = :branchAssembly and bft.endDate is NULL",
							BranchFeeTypePeriodical.class);
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
	public BranchFeeTypePeriodical findLatestBranchFeeTypePeriodicalByBranchAssembly(
			final BranchAssembly branchAssembly) {
		try {
			TypedQuery<BranchFeeTypePeriodical> query = this
					.getEntityManager()
					.createQuery(
							"select bft from BranchFeeTypePeriodical bft where bft.branchAssembly = :branchAssembly and bft.startDate = (select MAX(e.startDate) from BranchFeeTypePeriodical e where e.branchAssembly = :branchAssembly )",
							BranchFeeTypePeriodical.class);
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
	public BranchFeeTypePeriodical findBranchFeeByBranchAssemblyAndEndDate(final BranchAssembly branchAssembly,
			final Date referenceDate) {
		try {
			TypedQuery<BranchFeeTypePeriodical> query = this
					.getEntityManager()
					.createQuery(
							"select bft from BranchFeeTypePeriodical bft where bft.branchAssembly = :branchAssembly and bft.endDate = :referenceDate",
							BranchFeeTypePeriodical.class);
			query.setParameter("branchAssembly", branchAssembly);
			query.setParameter("referenceDate", referenceDate);
			return query.getSingleResult();
		} catch (NoResultException e) {
			log.info(e.getMessage());
			return null;
		}

	}

}
