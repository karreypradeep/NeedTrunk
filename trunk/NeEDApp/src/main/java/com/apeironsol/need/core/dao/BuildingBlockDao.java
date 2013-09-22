/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.constants.FeeClassificationLevelConstant;
import com.apeironsol.need.util.constants.FeeTypeConstant;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for fee type entity.
 * 
 * @author Pradeep
 * 
 */
public interface BuildingBlockDao extends BaseDao<BuildingBlock> {

	/**
	 * Retrieves all building blocks by building block type.
	 * 
	 * @param buildingBlockType
	 *            building block type.
	 * 
	 * @return all building blocks by building block type.
	 */
	Collection<BuildingBlock> findBuildingBlocksByType(BuildingBlockConstant buildingBlockType);

	/**
	 * Retrieves all building blocks for branch id.
	 * 
	 * @param branchId
	 *            branch object id for which all building blocks have to be
	 *            retrieved.
	 * @return all building blocks for branch id.
	 */
	Collection<BuildingBlock> findBuildingBlocksByBranchId(Long branchId);

	/**
	 * Retrieves all building blocks for branch id and building block type.
	 * 
	 * @param branchId
	 *            branch object id.
	 * @param buildingBlockType
	 *            building block type.
	 * @return all building blocks for branch id and building block type.
	 */
	Collection<BuildingBlock> findBuildingBlocksByBranchIdAndBuildingBlockType(Long branchId, BuildingBlockConstant buildingBlockType);

	/**
	 * Retrieves building block by building block type and code.
	 * 
	 * @param buildingBlockType
	 *            building block type.
	 * 
	 * @param code
	 *            building block code.
	 * @return all building blocks by building block type.
	 */
	BuildingBlock findBuildingBlockByTypeAndCode(BuildingBlockConstant buildingBlockType, String code);

	/**
	 * Retrieve all mandatory building blocks.
	 * 
	 * @return collection of all mandatory building blocks.
	 */
	Collection<BuildingBlock> findAllMandatoryBuildingBlocks();

	/**
	 * 
	 * @param branchId
	 * @param feeClassificationLevel
	 * @return
	 */
	Collection<BuildingBlock> findFeeTypeBuildingBlocksbyBranchIdAndFeeClassificationLevel(Long branchId, FeeClassificationLevelConstant feeClassificationLevel);

	/**
	 * 
	 * @param branchId
	 * @param feeTypeConstant
	 * @return
	 */
	Collection<BuildingBlock> findFeeTypeBuildingBLocksByBranchIdAndFeeType(Long branchId, FeeTypeConstant feeTypeConstant);

	/**
	 * 
	 * @param feeType
	 * @return
	 */
	BuildingBlock findBuildingBlockByFeeType(FeeTypeConstant feeType);

}
