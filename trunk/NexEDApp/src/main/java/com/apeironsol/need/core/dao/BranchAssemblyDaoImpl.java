/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 *
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.constants.FeeTypeConstant;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for branch assembly entity implementation.
 *
 * @author Pradeep
 *
 */
@Repository("branchAssemblyDao")
public class BranchAssemblyDaoImpl extends BaseDaoImpl<BranchAssembly> implements BranchAssemblyDao {

	private static final Logger	log	= Logger.getLogger(BranchAssemblyDaoImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchAssembly> findBranchAssembliesByBranchId(final Long branchId) {
		try {
			TypedQuery<BranchAssembly> query = getEntityManager().createQuery(
					"select ba from BranchAssembly ba where ba.branchId = :branchId", BranchAssembly.class);
			query.setParameter("branchId", branchId);
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
	public BranchAssembly findBranchAssemblyByBuildingBlockIdAndBranchId(final Long buildingBlockId, final Long branchId) {
		try {
			TypedQuery<BranchAssembly> query = getEntityManager()
					.createQuery(
							"select ba from BranchAssembly ba where ba.branchId = :branchId and ba.buildingBlockId = :buildingBlockId",
							BranchAssembly.class);
			query.setParameter("buildingBlockId", buildingBlockId);
			query.setParameter("branchId", branchId);
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
	public Collection<BranchAssembly> findBranchAssembliesByBranchAndBuildingBlockType(final Branch branch,
			final BuildingBlockConstant buildingBlockConstant) throws BusinessException {
		TypedQuery<BranchAssembly> query = getEntityManager()
				.createQuery(
						"select ba from BranchAssembly ba where ba.branchId = :branch and ba.buildingBlockId in (select bb1.id from BuildingBlock bb1 where bb1.type = :type)",
						BranchAssembly.class);
		query.setParameter("branchId", branch.getId());
		query.setParameter("type", buildingBlockConstant);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int removeBranchAssembliesByBranchId(final Long branchId) {
		Query query = getEntityManager()
				.createQuery("delete from BranchAssembly ba where ba.branchId = :branchId");
		query.setParameter("branchId", branchId);
		return query.executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int removeBranchAssembliesByBranchIdAndBuildingBlockId(final Long branchId, final Long buildingBlockId) {
		Query query = getEntityManager()
				.createQuery(
						"delete from BranchAssembly ba where ba.branchId = :branchId and ba.buildingBlockId = :buildingBlockId");
		query.setParameter("branchId", branchId);
		query.setParameter("buildingBlockId", buildingBlockId);
		return query.executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchAssembly> findBranchAssemblyByBuildingBlockId(final Long buildingBlockId) {
		TypedQuery<BranchAssembly> query = getEntityManager().createQuery(
				"select ba from BranchAssembly ba where ba.buildingBlockId = :buildingBlockId", BranchAssembly.class);
		query.setParameter("buildingBlockId", buildingBlockId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchAssembly> findBranchAssembliesTypeFeesByBranchAndFeeType(final Long branchId,
			final FeeTypeConstant feeType) throws BusinessException {
		TypedQuery<BranchAssembly> query = getEntityManager()
				.createQuery(
						"select ba from BranchAssembly ba where ba.branchId = :branchId and ba.buildingBlockId in (select bb1.id from BuildingBlock bb1 where bb1.type = 'FEE_TYPE' and bb1.feeType = :feeType)",
						BranchAssembly.class);
		query.setParameter("branchId", branchId);
		query.setParameter("feeType", feeType);
		return query.getResultList();
	}



}
