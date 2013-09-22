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

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for calendar year entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("academicYearDao")
public class AcademicYearDaoImpl extends BaseDaoImpl<AcademicYear> implements AcademicYearDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AcademicYear> findAcademicYearsByBranchId(final Long branchId) {
		TypedQuery<AcademicYear> query = this.getEntityManager().createQuery("select ay from AcademicYear ay join ay.branch br where br.id = :id",
				AcademicYear.class);
		query.setParameter("id", branchId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AcademicYear> findActiveAcademicYearsByBranchIdAndAdmissionsOpen(final Long branchId) {
		Date currentDate = DateUtil.getSystemDate();
		TypedQuery<AcademicYear> query = this.getEntityManager().createQuery(
				"select ay from AcademicYear ay join ay.branch br where br.id = :id and ay.active = :active and ay.admissionStartDate <= :date and ay.admissionEndDate >= :date", AcademicYear.class);
		query.setParameter("id", branchId);
		query.setParameter("date", currentDate);
		query.setParameter("active", true);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AcademicYear findAcademicYearByBranchIdAndDate(final Long branchId, final Date date) {
		try {
			TypedQuery<AcademicYear> query = this.getEntityManager().createQuery(
					"select ay from AcademicYear ay join ay.branch br where br.id = :id and ay.startDate <= :date and ay.endDate >= :date", AcademicYear.class);
			query.setParameter("id", branchId);
			query.setParameter("date", date);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AcademicYear> findAcademicYearsByBranchIdAndActiveStatus(final Long branchId, final boolean active) {
		TypedQuery<AcademicYear> query = this.getEntityManager().createQuery(
				"select ay from AcademicYear ay join ay.branch br where br.id = :id and ay.active = :active", AcademicYear.class);
		query.setParameter("id", branchId);
		query.setParameter("active", active);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AcademicYear> findOverLappingAcademicYearForBranchIdAndAcademicYear(final Long branchId, final AcademicYear academicYear) {
		TypedQuery<AcademicYear> query = this.getEntityManager().createQuery(
				"select ay from AcademicYear ay where ay.branch.id = :branchId and " + "((ay.startDate <= :startDate and ay.endDate >= :startDate) or "
						+ "(ay.startDate <= :endDate and ay.endDate >= :endDate))", AcademicYear.class);
		query.setParameter("branchId", branchId);
		query.setParameter("startDate", academicYear.getStartDate());
		query.setParameter("endDate", academicYear.getEndDate());
		return query.getResultList();
	}

	@Override
	public AcademicYear findLatestAcademicYear(final Long branchId) throws BusinessException {
		try {
			TypedQuery<AcademicYear> query = this
					.getEntityManager()
					.createQuery(
							"select ay from AcademicYear ay join ay.branch br where br.id = :branchId and ay.endDate = (select max(a.endDate) from AcademicYear a where a.branch.id = :branchId)",
							AcademicYear.class);
			query.setParameter("branchId", branchId);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AcademicYear> findAcademicYearsForBatchId(final Long batchId, final Long branchId) {
		TypedQuery<AcademicYear> query = this.getEntityManager().createQuery(
				"select ay from AcademicYear ay where ay.branch.id = :branchId "
						+ "and ay.startDate >= (select b.startAcademicYear.startDate from Batch b where b.id = :batchId) "
						+ "and ay.endDate <= (select b.endAcademicYear.endDate from Batch b where b.id = :batchId)", AcademicYear.class);
		query.setParameter("branchId", branchId);
		query.setParameter("batchId", batchId);
		return query.getResultList();
	}

}
