/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.constants.FeeClassificationLevelConstant;
import com.apeironsol.need.util.constants.FeeTypeConstant;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * Service Interface for organization fees type. This service act as controller
 * for organization fees type details.
 * 
 * @author Pradeep
 * 
 */
public interface BuildingBlockService {

	/**
	 * Retrieves the building block by id.
	 * 
	 * @param id
	 *            the id of building block to be retrieved.
	 * @return building block.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BuildingBlock findBuildingBlockById(Long id) throws BusinessException;

	/**
	 * Retrieves all building blocks available.
	 * 
	 * @return all building blocks available.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BuildingBlock> findAllBuildingBlocks() throws BusinessException;

	/**
	 * Saves the building block.
	 * 
	 * @return the persisted building block.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BuildingBlock saveBuildingBlock(BuildingBlock buildingBlock) throws BusinessException;

	/**
	 * Removes the building block.
	 * 
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeBuildingBlock(BuildingBlock buildingBlock) throws BusinessException;

	/**
	 * Retrieves all building blocks by building block type.
	 * 
	 * @return all building blocks by building block type.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BuildingBlock> findBuildingBlocksByType(BuildingBlockConstant buildingBlockType) throws BusinessException;

	/**
	 * Retrieves all building blocks for branch id.
	 * 
	 * @param branchId
	 *            branch object id for which all building blocks have to be
	 *            retrieved.
	 * @return all building blocks for branch id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BuildingBlock> findBuildingBlocksbyBranchId(Long branchId) throws BusinessException;

	/**
	 * Retrieves all building blocks for branch id and building block type.
	 * 
	 * @param branchId
	 *            branch object id.
	 * @param buildingBlockType
	 *            building block type.
	 * @return all building blocks for branch id and building block type.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BuildingBlock> findBuildingBlocksbyBranchIdAndBuildingBlockType(Long branchId, BuildingBlockConstant buildingBlockType) throws BusinessException;

	/**
	 * Retrieves building block by building block type and code.
	 * 
	 * @param buildingBlockType
	 *            building block type.
	 * 
	 * @param code
	 *            building block code.
	 * @return all building blocks by building block type.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BuildingBlock findBuildingBlockByTypeAndCode(BuildingBlockConstant buildingBlockType, String code) throws BusinessException;

	/**
	 * Retrieve all mandatory building blocks.
	 * 
	 * @return collection of all mandatory building blocks.
	 */
	Collection<BuildingBlock> findAllMandatoryBuildingBlocks() throws SystemException;

	/**
	 * Retrieve all fee Type building blocks for a branch by fee classification.
	 * 
	 * @param branchId
	 *            the branch id.
	 * @param feeClassificationLevel
	 *            the fee classification level constant.
	 * @return
	 * @throws BusinessException
	 *             in case of any business exceptions.
	 */
	Collection<BuildingBlock> findFeeTypeBuildingBlocksbyBranchIdAndFeeClassificationLevel(final Long branchId,
			FeeClassificationLevelConstant feeClassificationLevel) throws BusinessException, SystemException;

	/**
	 * Retrieve all fee Type building block for a branch by fee type.
	 * 
	 * @param branchId
	 *            the branch id.
	 * @param feeClassificationLevel
	 *            the fee classification level constant.
	 * @return
	 * @throws BusinessException
	 *             in case of any business exceptions.
	 */
	Collection<BuildingBlock> findFeeTypeBuildingBlocksbyBranchIdAndFeeType(Long branchId, final FeeTypeConstant feeTypeConstant) throws BusinessException,
			SystemException;
}
