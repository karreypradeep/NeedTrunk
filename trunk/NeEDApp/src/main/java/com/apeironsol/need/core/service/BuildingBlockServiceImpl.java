/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.BranchAssemblyDao;
import com.apeironsol.need.core.dao.BuildingBlockDao;
import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.portal.messages.BusinessMessages;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.constants.FeeClassificationLevelConstant;
import com.apeironsol.need.util.constants.FeeTypeConstant;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * Service implementation interface for organization fee type. This service act
 * as controller for organization fee type details.
 * 
 * @author Pradeep
 * 
 */
@Service("buildingBlockService")
@Transactional(rollbackFor = Exception.class)
public class BuildingBlockServiceImpl implements BuildingBlockService {

	@Resource
	BuildingBlockDao	buildingBlockDao;

	@Resource
	BranchAssemblyDao	branchAssemblyDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BuildingBlock saveBuildingBlock(final BuildingBlock buildingBlock) throws BusinessException {

		this.validateBuildingBlock(buildingBlock);

		return this.buildingBlockDao.persist(buildingBlock);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BuildingBlock findBuildingBlockById(final Long id) throws BusinessException {
		return this.buildingBlockDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BuildingBlock> findAllBuildingBlocks() throws BusinessException {
		return this.buildingBlockDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBuildingBlock(final BuildingBlock buildingBlock) throws BusinessException {

		final Collection<BranchAssembly> branchAssemblies = this.branchAssemblyDao.findBranchAssemblyByBuildingBlockId(buildingBlock.getId());

		if (branchAssemblies != null && !branchAssemblies.isEmpty()) {
			throw new BusinessException(BusinessMessages.getResourceBundleName(), BusinessMessages.MSG_BUILDBLOCK_IS_ASSEMBLED_TO_BRANCH,
					new Object[] { buildingBlock.getName() });
		}

		this.buildingBlockDao.remove(buildingBlock);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BuildingBlock> findBuildingBlocksByType(final BuildingBlockConstant buildingBlockType) throws BusinessException {
		return this.buildingBlockDao.findBuildingBlocksByType(buildingBlockType);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BuildingBlock> findBuildingBlocksbyBranchId(final Long branchId) throws BusinessException {
		return this.buildingBlockDao.findBuildingBlocksByBranchId(branchId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BuildingBlock> findBuildingBlocksbyBranchIdAndBuildingBlockType(final Long branchId, final BuildingBlockConstant buildingBlockType)
			throws BusinessException {
		return this.buildingBlockDao.findBuildingBlocksByBranchIdAndBuildingBlockType(branchId, buildingBlockType);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BuildingBlock findBuildingBlockByTypeAndCode(final BuildingBlockConstant buildingBlockType, final String code) throws BusinessException {
		return this.buildingBlockDao.findBuildingBlockByTypeAndCode(buildingBlockType, code);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BuildingBlock> findAllMandatoryBuildingBlocks() throws SystemException {
		try {

			return this.buildingBlockDao.findAllMandatoryBuildingBlocks();

		} catch (final Throwable e) {
			throw new SystemException(e);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BuildingBlock> findFeeTypeBuildingBlocksbyBranchIdAndFeeClassificationLevel(final Long branchId,
			final FeeClassificationLevelConstant feeClassificationLevel) throws BusinessException, SystemException {
		return this.buildingBlockDao.findFeeTypeBuildingBlocksbyBranchIdAndFeeClassificationLevel(branchId, feeClassificationLevel);
	}

	/**
	 * Validates building block.
	 * 
	 * @param buildingBlock
	 *            building block.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	private void validateBuildingBlock(final BuildingBlock buildingBlock) throws BusinessException {
		this.checkForDuplicateCodeForSameBuildingBlockType(buildingBlock);

		if (BuildingBlockConstant.FEE_TYPE.equals(buildingBlock.getType())) {
			this.validateFeeTypeBuildingBlocks(buildingBlock);
		}

	}

	private void validateFeeTypeBuildingBlocks(final BuildingBlock buildingBlock) {

		final BuildingBlock buildingBlockFeeType = this.buildingBlockDao.findBuildingBlockByFeeType(buildingBlock.getFeeType());
		if (buildingBlock.getId() == null || !buildingBlock.getId().equals(buildingBlockFeeType.getId())) {
			if (buildingBlockFeeType != null
					&& (FeeTypeConstant.APPLICATION_FEE.equals(buildingBlock.getFeeType()) || FeeTypeConstant.PAST_DUE.equals(buildingBlock.getFeeType()) || FeeTypeConstant.RESERVATION_FEE
							.equals(buildingBlock.getFeeType()))) {
				throw new BusinessException("Fee type " + buildingBlock.getFeeType().getLabel() + " fee can be defined only once.");
			}
		}

	}

	/**
	 * Validates building block for duplicate code for same building block
	 * type..
	 * 
	 * @param buildingBlock
	 *            building block.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	private void checkForDuplicateCodeForSameBuildingBlockType(final BuildingBlock buildingBlock) throws BusinessException {
		if (buildingBlock.getId() == null) {
			final BuildingBlock duplicateBuildingBlock = this.buildingBlockDao.findBuildingBlockByTypeAndCode(buildingBlock.getType(), buildingBlock.getCode());
			if (duplicateBuildingBlock != null) {
				throw new BusinessException(BusinessMessages.getResourceBundleName(), BusinessMessages.MSG_CODE_ALREADY_EXIST_FOR_BUILDING_BLOCK_TYPE,
						new Object[] { buildingBlock.getCode(), buildingBlock.getType() });

			}
		}
	}

	@Override
	public Collection<BuildingBlock> findFeeTypeBuildingBlocksbyBranchIdAndFeeType(final Long branchId, final FeeTypeConstant feeTypeConstant)
			throws BusinessException, SystemException {
		return this.buildingBlockDao.findFeeTypeBuildingBLocksByBranchIdAndFeeType(branchId, feeTypeConstant);
	}

}
