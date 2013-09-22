/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 *
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.constants.FeeTypeConstant;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for branch assembly implementation.
 *
 * @author Pradeep
 *
 */
public interface BranchAssemblyDao extends BaseDao<BranchAssembly> {

	/**
	 * Retrieves all Branch assemblies available for branch.
	 *
	 * @return all all Branch assemblies available for branch.
	 */
	Collection<BranchAssembly> findBranchAssembliesByBranchId(final Long branchId);

	/**
	 * Retrieves the branch assembly by building block and branch.
	 *
	 * @param buildingBlockId
	 *            building block id.
	 * @param branchId
	 *            branch id.
	 * @return branch assembly by building block and branch
	 */
	BranchAssembly findBranchAssemblyByBuildingBlockIdAndBranchId(final Long buildingBlockId, final Long branchId);

	/**
	 * Find all branch assemblies by building block id.
	 *
	 * @param buildingBlockId
	 *            building block id.
	 * @return all branch assemblies by building block id.
	 */
	Collection<BranchAssembly> findBranchAssemblyByBuildingBlockId(final Long buildingBlockId);

	/**
	 * Retrieves branch assemblies by branch and building block type.
	 *
	 * @param branch
	 *            branch.
	 * @param buildingBlockConstant
	 *            building block constant.
	 * @return collection of branch assemblies.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchAssembly> findBranchAssembliesByBranchAndBuildingBlockType(final Branch branch,
			final BuildingBlockConstant buildingBlockConstant) throws BusinessException;

	/**
	 * Removes all branch assemblies by branch id.
	 *
	 * @param branchId
	 *            branch id.
	 * @return number of branch assemblies deleted.
	 */
	int removeBranchAssembliesByBranchId(Long branchId);

	/**
	 * Remove branch assembly by branch id and building block id.
	 *
	 * @param branchId
	 *            branch id.
	 * @param buildingBockId
	 *            building block id.
	 * @return
	 */
	int removeBranchAssembliesByBranchIdAndBuildingBlockId(Long branchId, Long buildingBockId);

	/**
	 * Retrieves branch assemblies for building block type type by branch and fee type.
	 *
	 * @param branch
	 *            branch.
	 * @param buildingBlockConstant
	 *            building block constant.
	 * @return collection of branch assemblies.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchAssembly> findBranchAssembliesTypeFeesByBranchAndFeeType(final Long branchId,
			final FeeTypeConstant feeType) throws BusinessException ;

}
