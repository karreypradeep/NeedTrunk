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
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.constants.FeeClassificationLevelConstant;
import com.apeironsol.need.util.constants.FeeTypeConstant;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for fee type entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("buildingBlockDao")
public class BuildingBlockDaoImpl extends BaseDaoImpl<BuildingBlock> implements BuildingBlockDao {

	private static final Logger	log	= Logger.getLogger(BuildingBlockDaoImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BuildingBlock> findBuildingBlocksByType(final BuildingBlockConstant buildingBlockType) {
		try {
			final TypedQuery<BuildingBlock> query = this.getEntityManager().createQuery("select bb from BuildingBlock bb where bb.type= :buildingBlockType",
					BuildingBlock.class);
			query.setParameter("buildingBlockType", buildingBlockType);
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
	public Collection<BuildingBlock> findBuildingBlocksByBranchId(final Long branchId) {
		try {
			final TypedQuery<BuildingBlock> query = this.getEntityManager().createQuery(
					"select bb from BuildingBlock bb join bb.branchs b where b.id = :branchId", BuildingBlock.class);
			query.setParameter("branchId", branchId);
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
	public Collection<BuildingBlock> findBuildingBlocksByBranchIdAndBuildingBlockType(final Long branchId, final BuildingBlockConstant buildingBlockType) {
		try {
			final TypedQuery<BuildingBlock> query = this.getEntityManager().createQuery(
					"select bb from BuildingBlock bb join bb.branchs b where b.id = :branchId and bb.type= :buildingBlockType", BuildingBlock.class);
			query.setParameter("branchId", branchId);
			query.setParameter("buildingBlockType", buildingBlockType);
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
	public BuildingBlock findBuildingBlockByTypeAndCode(final BuildingBlockConstant buildingBlockType, final String code) {
		try {
			final TypedQuery<BuildingBlock> query = this.getEntityManager().createQuery(
					"select bb from BuildingBlock bb where bb.type= :buildingBlockType and bb.code=:code", BuildingBlock.class);
			query.setParameter("buildingBlockType", buildingBlockType);
			query.setParameter("code", code);
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
	public Collection<BuildingBlock> findAllMandatoryBuildingBlocks() {
		try {
			final TypedQuery<BuildingBlock> query = this.getEntityManager().createQuery("select bb from BuildingBlock bb where bb.mandatory= true",
					BuildingBlock.class);
			return query.getResultList();
		} catch (final NoResultException e) {
			log.info(e.getMessage());
			return null;
		}
	}

	@Override
	public Collection<BuildingBlock> findFeeTypeBuildingBlocksbyBranchIdAndFeeClassificationLevel(final Long branchId,
			final FeeClassificationLevelConstant feeClassificationLevel) {

		final TypedQuery<BuildingBlock> query = this.getEntityManager().createQuery(
				"select bb from BuildingBlock bb join bb.branchs b where b.id = :branchId and "
						+ "bb.type= :buildingBlockType and bb.feeClassificationLevel = :feeClassificationLevel", BuildingBlock.class);
		query.setParameter("branchId", branchId);
		query.setParameter("buildingBlockType", BuildingBlockConstant.FEE_TYPE);
		query.setParameter("feeClassificationLevel", feeClassificationLevel);
		return query.getResultList();

	}

	@Override
	public Collection<BuildingBlock> findFeeTypeBuildingBLocksByBranchIdAndFeeType(final Long branchId, final FeeTypeConstant feeTypeConstant) {
		final TypedQuery<BuildingBlock> query = this.getEntityManager().createQuery(
				"select bb from BuildingBlock bb join bb.branchs b where b.id = :branchId and "
						+ "bb.type= :buildingBlockType and bb.feeType = :feeTypeConstant", BuildingBlock.class);
		query.setParameter("branchId", branchId);
		query.setParameter("buildingBlockType", BuildingBlockConstant.FEE_TYPE);
		query.setParameter("feeTypeConstant", feeTypeConstant);
		return query.getResultList();
	}

	@Override
	public BuildingBlock findBuildingBlockByFeeType(final FeeTypeConstant feeType) {

		try {

			final TypedQuery<BuildingBlock> query = this.getEntityManager().createQuery(
					"select bb from BuildingBlock bb where bb.type= :buildingBlockType and bb.feeType = :feeType", BuildingBlock.class);
			query.setParameter("buildingBlockType", BuildingBlockConstant.FEE_TYPE);
			query.setParameter("feeType", feeType);
			query.setMaxResults(1);
			return query.getSingleResult();

		} catch (final NoResultException e) {
			return null;
		}
	}

}
