/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

/**
 * Service interface for organization.
 * 
 * @author pradeep
 * 
 */
import java.util.Collection;

import javax.annotation.Resource;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.BranchAssemblyDao;
import com.apeironsol.need.core.dao.BranchDao;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.core.model.BranchRule;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.portal.messages.BusinessMessages;
import com.apeironsol.need.util.NonNull;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

@Service("branchService")
@Transactional(rollbackFor = Exception.class)
public class BranchServiceImpl implements BranchService {

	@Resource
	private BranchDao				branchDao;

	@Resource
	private BranchAssemblyDao		branchAssemblyDao;

	@Resource
	private BranchAssemblyService	branchAssemblyService;

	@Resource
	private BuildingBlockService	buildingBlockService;

	@Resource
	private BranchRuleService		branchRuleService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Branch saveBranch(@NonNull final Branch branch) throws BusinessException {
		return this.saveBranch(branch, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Branch saveBranch(final Branch branch, final boolean validateBranch) throws BusinessException {
		if (validateBranch) {
			this.checkForUniqueNameAndCode(branch);
		}
		return this.branchDao.persist(branch);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Branch> findAllBranchs() throws BusinessException {
		return this.branchDao.findAll();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Branch findBranchById(final Long id) throws BusinessException {
		return this.branchDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Branch findBranchByName(final String branchName) throws BusinessException {
		return this.branchDao.findBranchByName(branchName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Branch findBranchByCode(final String branchCode) throws BusinessException {
		return this.branchDao.findBranchByCode(branchCode);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBranch(final Long id) throws BusinessException {
		this.branchDao.remove(this.branchDao.findById(id));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBranch(final Branch branch) throws BusinessException {
		this.removeAllBranchDependencies(branch);
		this.removeBranch(branch.getId());
	}

	/**
	 * Checks if the user name is already in use. If yes, the exception is
	 * thrown.
	 * 
	 * @param userAccount
	 *            user account.
	 * @throws BusinessException
	 *             Business exception if user name is already in use.
	 */
	private void checkForUniqueNameAndCode(final Branch branch) throws BusinessException {
		Branch branchFromDB = null;
		try {
			branchFromDB = this.findBranchByName(branch.getName());
		} catch (NoResultException noResultException) {
		}
		if (branchFromDB != null) {
			if (branch.getId() == null || !branchFromDB.getId().equals(branch.getId())) {
				throw new BusinessException(BusinessMessages.getResourceBundleName(), BusinessMessages.MSG_BRANCH_WITH_NAME_ALREADY_EXISTS,
						new Object[] { branch.getName() });
			}
		}
		try {
			branchFromDB = this.findBranchByCode(branch.getCode());
		} catch (NoResultException noResultException) {
		}
		if (branchFromDB != null) {
			if (branch.getId() == null || !branchFromDB.getId().equals(branch.getId())) {
				throw new BusinessException("Branch with co " + branch.getCode() + " already exists.");
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws SystemException
	 */
	@Override
	public void performSanityCheck(final Branch branch) throws BusinessException, SystemException {

		Collection<BuildingBlock> buildingBlocks = this.buildingBlockService.findAllMandatoryBuildingBlocks();

		for (BuildingBlock buildingBlock : buildingBlocks) {

			BranchAssembly branchAssembly = this.branchAssemblyDao.findBranchAssemblyByBuildingBlockIdAndBranchId(buildingBlock.getId(), branch.getId());

			if (branchAssembly == null) {
				throw new BusinessException(BusinessMessages.getResourceBundleName(), BusinessMessages.MSG_BUILDBLOCK_IS_NOT_ASSEMBLED_TO_BRANCH,
						new Object[] { buildingBlock.getName() });
			}

		}
		
		BranchRule branchRule = this.branchRuleService.findBranchRuleByBranchId(branch.getId());
		
		if (branchRule == null) {
			throw new BusinessException("Please define branch rules for branch " + branch.getName() + ".");
		}
	}

	/**
	 * Removes all branch dependencies.
	 * 
	 * @param branch
	 *            branch.
	 */
	private void removeAllBranchDependencies(final Branch branch) throws BusinessException {
		this.branchAssemblyService.removeBranchAssembliesByBranch(branch);
		this.branchRuleService.removeBranchRuleByBranchId(branch.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Branch> findAllBranchsByActiveState(final Boolean activeState) throws BusinessException {
		return this.branchDao.findAllBranchsByActiveState(activeState);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Branch createNewBranch(final Branch branch) throws BusinessException, SystemException {
		try {
			Branch result = this.branchDao.persist(branch);

			Collection<BuildingBlock> buildingBlocks = this.buildingBlockService.findAllMandatoryBuildingBlocks();

			this.branchAssemblyService.createOrRemoveBranchAssemblies(result, buildingBlocks);

			return result;
		} catch (Throwable e) {
			throw new SystemException(e);
		}
	}

	@Override
	public Branch activateBranch(final Branch branch) throws BusinessException, SystemException {
		performSanityCheck(branch);
		branch.setActive(Boolean.TRUE);
		this.checkForUniqueNameAndCode(branch);
		return this.branchDao.persist(branch);
	}

}
