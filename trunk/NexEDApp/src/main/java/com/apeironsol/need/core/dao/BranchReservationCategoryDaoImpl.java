/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.core.model.BranchReservationCategory;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for branch reservation category entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("branchReservationCategoryDao")
public class BranchReservationCategoryDaoImpl extends BaseDaoImpl<BranchReservationCategory> implements
BranchReservationCategoryDao {

	private static final Logger	log	= Logger.getLogger(BranchReservationCategoryDaoImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchReservationCategory> findBranchReservationCategoriesByBranchAssembly(
			final BranchAssembly branchAssembly) {

		try {
			final TypedQuery<BranchReservationCategory> query = getEntityManager().createQuery(
					"select brc from BranchReservationCategory brc where brc.branchAssembly = :branchAssembly",
					BranchReservationCategory.class);
			query.setParameter("branchAssembly", branchAssembly);
			return query.getResultList();
		} catch (final NoResultException e) {
			log.info(e.getMessage());
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchReservationCategory findPreviousBranchReservationCategoryPeriodical(
			final BranchReservationCategory branchReservationCategory) {
		try {
			final TypedQuery<BranchReservationCategory> query = getEntityManager()
					.createQuery(
							"select brc from BranchReservationCategory brc where brc.branchAssembly = :branchAssembly and brc.startDate<=:startDate and (brc.endDate>:startDate or brc.endDate is null)",
							BranchReservationCategory.class);
			query.setParameter("branchAssembly", branchReservationCategory.getBranchAssembly());
			query.setParameter("startdate", branchReservationCategory.getStartDate());
			return query.getSingleResult();
		} catch (final NoResultException e) {
			log.info(e.getMessage());
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchReservationCategory findBranchReservationCategoryByBranchAssemblyAndDate(
			final BranchAssembly branchAssembly, final Date referenceDate) {
		try {
			final TypedQuery<BranchReservationCategory> query = getEntityManager()
					.createQuery(
							"select brc from BranchReservationCategory brc where brc.branchAssembly = :branchAssembly and brc.startDate <= :referenceDate and"
									+ " (brc.endDate is null or bft.endDate > :referenceDate)",
									BranchReservationCategory.class);
			query.setParameter("branchAssembly", branchAssembly);
			query.setParameter("referenceDate", referenceDate);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			log.info(e.getMessage());
			return null;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchReservationCategory findLatestBranchReservationCategory(final BranchAssembly branchAssembly) {
		try {
			final TypedQuery<BranchReservationCategory> query = getEntityManager()
					.createQuery(
							"select brc from BranchReservationCategory brc where brc.branchAssembly = :branchAssembly and brc.endDate is null",
							BranchReservationCategory.class);
			query.setParameter("branchAssembly", branchAssembly);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			log.info(e.getMessage());
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBranchReservationCategoryByBranchAssembly(final BranchAssembly branchAssembly) {
		final Query query = getEntityManager().createQuery(
				"delete from BranchReservationCategory brc where brc.branchAssembly = :branchAssembly");
		query.setParameter("branchAssembly", branchAssembly);
		query.executeUpdate();
	}
}
