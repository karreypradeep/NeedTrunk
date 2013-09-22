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
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.financial.model.KlassLevelFee;
import com.apeironsol.need.financial.model.KlassLevelFeeCatalog;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for class fee
 * 
 * @author Pradeep
 * 
 */
@Repository
public class KlassLevelFeeDaoImpl extends BaseDaoImpl<KlassLevelFee> implements KlassLevelFeeDao {

	private static final Logger	log	= Logger.getLogger(KlassLevelFeeDaoImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<KlassLevelFee> findKlassFeesByKlassIdAndBuildingBlockId(final Long klassId,
			final Long buildingBlockId) {
		try {
			TypedQuery<KlassLevelFee> query = this
					.getEntityManager()
					.createQuery(
							"select kf from KlassLevelFee kf where kf.klass.id = :klassId and kf.buildingBlock.id = :buildingBlockId",
							KlassLevelFee.class);
			query.setParameter("klassId", klassId);
			query.setParameter("buildingBlockId", buildingBlockId);
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
	public KlassLevelFee findKlassFeeByKlassIdAndBuildingBlockIdAndEndDate(final Long klassId,
			final Long buildingBlockId, final Date endDate) {
		try {
			TypedQuery<KlassLevelFee> query = this
					.getEntityManager()
					.createQuery(
							"select kf from KlassLevelFee kf where kf.klass.id = :klassId and kf.buildingBlock.id = :buildingBlockId and kf.endDate = :endDate ",
							KlassLevelFee.class);
			query.setParameter("klassId", klassId);
			query.setParameter("buildingBlockId", buildingBlockId);
			query.setParameter("endDate", endDate);
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
	public Collection<String> findKlassFeeTypes(final Long klassId) {
		try {
			TypedQuery<String> query = this
					.getEntityManager()
					.createQuery(
							"select kf.buildingBlock.name from KlassLevelFee kf where kf.klass.id = :klassId group by kf.buildingBlock.name",
							String.class);
			query.setParameter("klassId", klassId);
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
	public KlassLevelFee findKlassFeeByKlassIdAndBuildingBlockIdAndCurrentAcademicYear(final Long klassId,
			final Long buildingBlockId) {
		try {
			TypedQuery<KlassLevelFee> query = this
					.getEntityManager()
					.createQuery(
							"select kf from KlassLevelFee kf where kf.klass.id = :klassId and "
									+ "kf.buildingBlock.id = :buildingBlockId and kf.academicYear.startDate <= current_date and kf.academicYear.endDate >= current_date",
							KlassLevelFee.class);
			query.setParameter("klassId", klassId);
			query.setParameter("buildingBlockId", buildingBlockId);
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
	public KlassLevelFee findActiveKlassFeeByKlassIdAndBuildingBlockId(final Long klassId, final Long buildingBlockId) {
		try {
			TypedQuery<KlassLevelFee> query = this
					.getEntityManager()
					.createQuery(
							"select kf from KlassLevelFee kf where kf.id = :klassId and kf.buildingBlock.id = :buildingBlockId and kf.endDate is NULL",
							KlassLevelFee.class);
			query.setParameter("klassId", klassId);
			query.setParameter("buildingBlockId", buildingBlockId);
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
	public Collection<BuildingBlock> findAllFeeTypesByKlassId(final Long klassId) {
		try {
			TypedQuery<BuildingBlock> query = this.getEntityManager().createQuery(
					"select kf.buildingBlock from KlassLevelFee kf where kf.klass.id = :klassId)", BuildingBlock.class);
			query.setParameter("klassId", klassId);
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
	public List<KlassLevelFee> findKlassFeeByKlassBetweenStartDateAndEndDate(final Long klassId, final Date startDate,
			final Date endDate) {

		try {
			TypedQuery<KlassLevelFee> query = this.getEntityManager().createQuery(
					"select kf from KlassLevelFee kf where kf.klass.id = :klassId and (kf.startDate <= :endDate)"
							+ " and (kf.endDate >= :startDate or kf.endDate is null)", KlassLevelFee.class);
			query.setParameter("klassId", klassId);
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
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
	public List<KlassLevelFeeCatalog> findAllKlassFeePaymentByKlassFeeId(final Long klassLevelFeeId) {
		try {
			TypedQuery<KlassLevelFeeCatalog> query = this.getEntityManager().createQuery(
					"select kfp from KlassLevelFeeCatalog kfp join kfp.klassLevelFee kf where kf.id= :klassId)",
					KlassLevelFeeCatalog.class);
			query.setParameter("klassId", klassLevelFeeId);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public KlassLevelFee findKlassFeeByKlassidAndBunildingBlockIdAndAcademicYearId(final Long klassId,
			final Long buildingBlockId, final Long academicYearId) {

		try {
			TypedQuery<KlassLevelFee> query = this.getEntityManager().createQuery(
					"select kf from KlassLevelFee kf where kf.klass.id = :klassId and "
							+ "kf.buildingBlock.id = :buildingBlockId and kf.academicYear.id = :academicYearId",
					KlassLevelFee.class);
			query.setParameter("klassId", klassId);
			query.setParameter("buildingBlockId", buildingBlockId);
			query.setParameter("academicYearId", academicYearId);
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
	public Collection<KlassLevelFee> findKlassFeesByKlassIdAndAcademicYearId(final Long klassId,
			final Long academicYearId) {

		TypedQuery<KlassLevelFee> query = this
				.getEntityManager()
				.createQuery(
						"select kf from KlassLevelFee kf where kf.klass.id = :klassId and kf.academicYear.id = :academicYearId",
						KlassLevelFee.class);
		query.setParameter("klassId", klassId);
		query.setParameter("academicYearId", academicYearId);
		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<KlassLevelFee> findKlassFeesByKlassId(final Long klassId) {

		TypedQuery<KlassLevelFee> query = this.getEntityManager().createQuery(
				"select kf from KlassLevelFee kf where kf.klass.id = :klassId", KlassLevelFee.class);
		query.setParameter("klassId", klassId);
		return query.getResultList();

	}
}
