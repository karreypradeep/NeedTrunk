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
import com.apeironsol.need.core.dao.BranchDao;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.core.model.BranchDepartment;
import com.apeironsol.need.core.model.BranchExpenseTypePeriodical;
import com.apeironsol.need.core.model.BranchFeeTypePeriodical;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.constants.FeeTypeConstant;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service implementation interface for Branch fee type. This service act as
 * controller for Branch fee type details.
 *
 * @author Pradeep
 *
 */
@Service("branchAssemblyService")
@Transactional
public class BranchAssemblyServiceImpl implements BranchAssemblyService {

	@Resource
	BranchAssemblyDao					branchAssembleDao;

	@Resource
	BranchDao							branchDao;

	@Resource
	BranchFeeTypePeriodicalService		branchFeeService;

	@Resource
	BranchExpenseTypePeriodicalService	branchExpenseTypeService;

	@Resource
	BranchDepartmentService				branchDepartmentService;

	@Resource
	BranchReservationCategoryService	branchReservationCategoryService;

	@Resource
	BuildingBlockService				buildingBlockService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchAssembly saveBranchAssembly(final BranchAssembly branchAssembly) throws BusinessException {
		return this.branchAssembleDao.persist(branchAssembly);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchAssembly findBranchAssemblyById(final Long id) throws BusinessException {
		return this.branchAssembleDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBranchAssembly(final BranchAssembly branchAssembly) throws BusinessException {
		// this.checkForBranchAssemblyDependencies(branchAssembly);
		this.removeBranchAssembly(branchAssembly, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBranchAssembly(final BranchAssembly branchAssembly, final boolean removeAllDependencies)
			throws BusinessException {
		if (removeAllDependencies) {
			removeAllBranchAssemblyDependencies(branchAssembly);
		}
		this.branchAssembleDao.remove(branchAssembly);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchAssembly> findBranchAssembliesByBranch(final Branch branch) throws BusinessException {
		return this.branchAssembleDao.findBranchAssembliesByBranchId(branch.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchAssembly findBranchAssemblyByBuildingBlockIdAndBranch(final BuildingBlock buildingBlock,
			final Branch branch) throws BusinessException {
		return this.branchAssembleDao.findBranchAssemblyByBuildingBlockIdAndBranchId(buildingBlock.getId(), branch.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBranchAssembliesByBranch(final Branch branch) throws BusinessException {
		Collection<BranchAssembly> branchAssemblies = this.branchAssembleDao.findBranchAssembliesByBranchId(branch.getId());
		for (BranchAssembly branchAssembly : branchAssemblies) {
			this.removeBranchAssembly(branchAssembly, true);
		}

	}

	/**
	 * Removes all the branch assembly dependencies before removing branch
	 * assembly.
	 *
	 * @param branchAssembly
	 *            branch assembly to be removed.
	 */
	private void removeAllBranchAssemblyDependencies(final BranchAssembly branchAssembly) throws BusinessException {
		// if
		// (branchAssembly.getBuildingBlock().getType().equals(BuildingBlockConstant.FEE_TYPE))
		// {
		// this.branchFeeService.removeBranchFeeTypeByBranchAssembly(branchAssembly);
		// } else if
		// (branchAssembly.getBuildingBlock().getType().equals(BuildingBlockConstant.EXPENSE_TYPE))
		// {
		// this.branchExpenseTypeService.removeBranchExpenseTypeByBranchAssembly(branchAssembly);
		// } else if
		// (branchAssembly.getBuildingBlock().getType().equals(BuildingBlockConstant.DEPARTMENT))
		// {
		// this.branchDepartmentService.removeBranchDepartmentByBranchAssembly(branchAssembly);
		// } else if
		// (branchAssembly.getBuildingBlock().getType().equals(BuildingBlockConstant.RESERVATION_CATEGORY))
		// {
		// this.branchReservationCategoryService.removeBranchReservationCategoryByBranchAssembly(branchAssembly);
		// }
	}

	/**
	 * Removes all the branch assembly dependencies before removing branch
	 * assembly.
	 *
	 * @param branchAssembly
	 *            branch assembly to be removed.
	 */
	private void checkForBranchAssemblyDependencies(final BranchAssembly branchAssembly,
			final BuildingBlock buildingBlock) throws BusinessException {
		if (buildingBlock.getType().equals(BuildingBlockConstant.FEE_TYPE)) {
			Collection<BranchFeeTypePeriodical> branchFeeTypes = this.branchFeeService
					.findBranchFeeTypeByBranchAssembly(branchAssembly);
			if (branchFeeTypes != null && !branchFeeTypes.isEmpty()) {
				throw new BusinessException(buildingBlock.getName()
						+ " periodicals are defined for branch. Please remove " + buildingBlock.getName()
						+ " periodicals before un-assemble the building block.");
			}
		} else if (buildingBlock.getType().equals(BuildingBlockConstant.EXPENSE_TYPE)) {
			Collection<BranchExpenseTypePeriodical> branchExpenseTypes = this.branchExpenseTypeService
					.findBranchExpenseTypeByBranchAssembly(branchAssembly);
			if (branchExpenseTypes != null && !branchExpenseTypes.isEmpty()) {
				throw new BusinessException(buildingBlock.getName()
						+ " periodical are defined for branch. Please remove " + buildingBlock.getName()
						+ " periodicals before un-assemble the building block.");
			}
		} else if (buildingBlock.getType().equals(BuildingBlockConstant.DEPARTMENT)) {
			Collection<BranchDepartment> branchDepartments = this.branchDepartmentService
					.findBranchDepartmentsByBranchAssembly(branchAssembly);
			if (branchDepartments != null && !branchDepartments.isEmpty()) {
				throw new BusinessException(buildingBlock.getName()
						+ " periodical are defined for branch. Please remove " + buildingBlock.getName()
						+ " periodicals before un-assemble the building block.");
			}
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchAssembly> findBranchAssembliesByBranchAndBuildingBlockType(final Branch branch,
			final BuildingBlockConstant buildingBlockConstant) throws BusinessException {
		return this.branchAssembleDao.findBranchAssembliesByBranchAndBuildingBlockType(branch, buildingBlockConstant);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createOrRemoveBranchAssemblies(final Branch branch, final Collection<BuildingBlock> buildingBlocks)
			throws BusinessException {

		Collection<BuildingBlock> assembledbuildingBlocks = this.buildingBlockService.findBuildingBlocksbyBranchId(branch
				.getId());

		// Find building requested for un-assemble.
		if (assembledbuildingBlocks != null && !assembledbuildingBlocks.isEmpty()) {
			for (BuildingBlock buildingBlock : assembledbuildingBlocks) {
				if (buildingBlocks.contains(buildingBlock)) {
					// Skip from persist
					buildingBlocks.remove(buildingBlock);
				} else {

					// Remove building block.

					// Check if building block can be removed.
					BranchAssembly branchAssembly = this.branchAssembleDao.findBranchAssemblyByBuildingBlockIdAndBranchId(
							buildingBlock.getId(), branch.getId());
					checkForBranchAssemblyDependencies(branchAssembly, buildingBlock);

					this.branchAssembleDao.removeBranchAssembliesByBranchIdAndBuildingBlockId(branch.getId(),
							buildingBlock.getId());
				}
			}
		}

		// Persist new building blocks.
		for (BuildingBlock buildingBlock : buildingBlocks) {
			BranchAssembly branchAssembly = new BranchAssembly();
			branchAssembly.setBranchId(branch.getId());
			branchAssembly.setBuildingBlockId(buildingBlock.getId());
			this.branchAssembleDao.persist(branchAssembly);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchAssembly> findBranchAssembliesTypeFeesByBranchAndFeeType(final Long branchId, final FeeTypeConstant feeType) throws BusinessException {
		return this.branchAssembleDao.findBranchAssembliesTypeFeesByBranchAndFeeType(branchId, feeType);
	}
}
