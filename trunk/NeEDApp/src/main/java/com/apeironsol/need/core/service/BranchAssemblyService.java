/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 *
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.constants.FeeTypeConstant;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service Interface for Branch assemblies. This service act as controller for
 * Branch assemblies.
 *
 * @author Pradeep
 *
 */
public interface BranchAssemblyService {

	/**
	 * Retrieves the branch assembly by id.
	 *
	 * @param id
	 *            the id of branch assembly to be retrieved.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchAssembly findBranchAssemblyById(Long id) throws BusinessException;

	/**
	 * Persists the branch assembly.
	 *
	 * @return the persisted branch assembly.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchAssembly saveBranchAssembly(BranchAssembly branchAssembly) throws BusinessException;

	/**
	 * Removes the branch assembly.
	 *
	 * @param branchAssembly
	 *            the branch assembly.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeBranchAssembly(final BranchAssembly branchAssembly) throws BusinessException;

	/**
	 * Removes the branch assembly.
	 *
	 * @param branchAssembly
	 *            the branch assembly.
	 * @param removeAllDependencies
	 *            if true all the dependencies will also be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeBranchAssembly(final BranchAssembly branchAssembly, final boolean removeAllDependencies)
			throws BusinessException;

	/**
	 * Retrieves all branch assemblies available for branch.
	 *
	 * @param branch
	 *            branch.
	 * @return all all branch assemblies available for branch.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchAssembly> findBranchAssembliesByBranch(final Branch branch) throws BusinessException;

	/**
	 * Retrieves the branch assembly by id.
	 *
	 * @param id
	 *            the id of branch assembly to be retrieved.
	 * @param retrieveBuildingBlock
	 *            if true then building block will also be retrieved.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchAssembly findBranchAssemblyByBuildingBlockIdAndBranch(final BuildingBlock buildingBlock, final Branch branch)
			throws BusinessException;

	/**
	 * Removes all the branch assemblies by branch.
	 *
	 * @param branch
	 *            branch.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeBranchAssembliesByBranch(final Branch branch) throws BusinessException;

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
	 * This method will assemble or remove building blocks from branch.
	 *
	 * @param branch
	 * @param buildingBlocks
	 * @throws BusinessException
	 */
	void createOrRemoveBranchAssemblies(Branch branch, Collection<BuildingBlock> buildingBlocks)
			throws BusinessException;

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
