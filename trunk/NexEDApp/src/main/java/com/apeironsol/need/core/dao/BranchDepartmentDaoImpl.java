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
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.core.model.BranchDepartment;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for branch assemly entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("branchDepartmentDao")
public class BranchDepartmentDaoImpl extends BaseDaoImpl<BranchDepartment> implements BranchDepartmentDao {

	private static final Logger	log	= Logger.getLogger(BranchDepartmentDaoImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchDepartment> findBranchDepartmentsByBranchAssembly(final BranchAssembly branchAssembly) {

		try {
			TypedQuery<BranchDepartment> query = this.getEntityManager().createQuery(
					"select bd from BranchDepartment bd where bd.branchAssembly = :branchAssembly",
					BranchDepartment.class);
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
	public BranchDepartment findPreviousBranchDepartmentPeriodical(final BranchDepartment branchDepartment) {
		try {
			TypedQuery<BranchDepartment> query = this
					.getEntityManager()
					.createQuery(
							"select bd from BranchDepartment bd where bd.branchAssembly = :branchAssembly and bd.startDate<=:startDate and (bd.endDate>:startDate or bd.endDate is null)",
							BranchDepartment.class);
			query.setParameter("branchAssembly", branchDepartment.getBranchAssembly());
			query.setParameter("startdate", branchDepartment.getStartDate());
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
	public BranchDepartment findBranchDepartmentByBranchAssemblyAndDate(final BranchAssembly branchAssembly,
			final Date referenceDate) {
		try {
			TypedQuery<BranchDepartment> query = this.getEntityManager().createQuery(
					"select db from BranchDepartment db where db.branchAssembly = :branchAssembly and db.startDate <= :referenceDate and"
							+ " (db.endDate is null or db.endDate > :referenceDate)", BranchDepartment.class);
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
	public BranchDepartment findLatestBranchDepartment(final BranchAssembly branchAssembly) {
		try {
			TypedQuery<BranchDepartment> query = this
					.getEntityManager()
					.createQuery(
							"select db from BranchDepartment db where db.branchAssembly = :branchAssembly and db.endDate is null",
							BranchDepartment.class);
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
	public void removeBranchDepartmentByBranchAssembly(final BranchAssembly branchAssembly) {
		Query query = this.getEntityManager().createQuery(
				"delete from BranchDepartment db where db.branchAssembly = :branchAssembly");
		query.setParameter("branchAssembly", branchAssembly);
		query.executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchDepartment findBranchDepartmentByBranchAssemblyAndStartDate(final BranchAssembly branchAssembly,
			final Date referenceDate) {
		try {
			TypedQuery<BranchDepartment> query = this
					.getEntityManager()
					.createQuery(
							"select db from BranchDepartment db where db.branchAssembly = :branchAssembly and db.startDate = :referenceDate",
							BranchDepartment.class);
			query.setParameter("branchAssembly", branchAssembly);
			query.setParameter("referenceDate", referenceDate);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchDepartment findBranchDepartmentByBranchAssemblyAndEndDate(final BranchAssembly branchAssembly,
			final Date referenceDate) {
		try {
			TypedQuery<BranchDepartment> query = this
					.getEntityManager()
					.createQuery(
							"select db from BranchDepartment db where db.branchAssembly = :branchAssembly and db.endDate = :referenceDate",
							BranchDepartment.class);
			query.setParameter("branchAssembly", branchAssembly);
			query.setParameter("referenceDate", referenceDate);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}
}
