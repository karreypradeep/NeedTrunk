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
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * Service Interface for Branch. This service act as controller for
 * Branch.
 * 
 * @author Pradeep
 * 
 */
public interface BranchService {

	/**
	 * Persists branch into database.
	 * 
	 * @param branch
	 *            branch.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Branch saveBranch(Branch branch) throws BusinessException;

	/**
	 * Persists branch into database.
	 * 
	 * @param branch
	 *            branch.
	 * @param validateBranch
	 *            if true the branch is validated.
	 * @return branch.
	 * @throws BusinessException
	 */
	Branch saveBranch(final Branch branch, final boolean validateBranch) throws BusinessException;

	/**
	 * Retrieves all branches from database.
	 * 
	 * @return all branches from database.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<Branch> findAllBranchs() throws BusinessException;

	/**
	 * Retrieves branch with given id.
	 * 
	 * @param id
	 *            branch id.
	 * @return branch with given id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Branch findBranchById(Long id) throws BusinessException;

	/**
	 * Removes branch with d.
	 * 
	 * @param id
	 *            branch id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeBranch(Long id) throws BusinessException;

	/**
	 * Removes the branch.
	 * 
	 * @param branch
	 *            branch.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeBranch(Branch branch) throws BusinessException;

	/**
	 * Returns branch by name.
	 * 
	 * @param branchName
	 *            branch name.
	 * @return branch by name.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Branch findBranchByName(String branchName) throws BusinessException;

	/**
	 * Returns branch by code.
	 * 
	 * @param branchCode
	 *            branch code.
	 * @return branch by code.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Branch findBranchByCode(String branchCode) throws BusinessException;

	/**
	 * Performs validation check on the branch. Returns true if the check is
	 * passed. If validation failed then the messages are added to messages.
	 * 
	 * @param branch
	 *            branch.
	 * @param messages
	 *            contains validation messages.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws SystemException
	 */
	void performSanityCheck(final Branch branch) throws BusinessException, SystemException;

	/**
	 * Retrieves all branches by supplied active state from database.
	 * 
	 * @return all branches by supplied active state from database.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<Branch> findAllBranchsByActiveState(final Boolean activeState) throws BusinessException;

	/**
	 * Create a new branch.
	 * 
	 * @param branch
	 *            branch to be saved.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws SystemException
	 *             In case of exception.
	 */
	Branch createNewBranch(Branch branch) throws BusinessException, SystemException;

	/**
	 * Create a new branch.
	 * 
	 * @param branch
	 *            branch to be saved.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws SystemException
	 *             In case of exception.
	 */
	Branch activateBranch(Branch branch) throws BusinessException, SystemException;
}
