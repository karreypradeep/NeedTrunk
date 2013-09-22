/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.core.model.BranchDepartment;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service Interface for branch department. This service act as controller for
 * branch department.
 * 
 * @author Pradeep
 * 
 */
public interface BranchDepartmentService {

	/**
	 * Retrieves the Branch department by id.
	 * 
	 * @param id
	 *            the id of Branch department to be retrieved.
	 * @return the Branch department.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchDepartment findBranchDepartmentById(Long id) throws BusinessException;

	/**
	 * Retrieves all branch departments by branch assembly.
	 * 
	 * @return all branch departments by branch assembly.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchDepartment> findBranchDepartmentsByBranchAssembly(BranchAssembly branchAssembly)
			throws BusinessException;

	/**
	 * Persists the Branch department.
	 * 
	 * @return the persisted Branch department.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchDepartment saveBranchDepartment(BranchDepartment branchDepartment) throws BusinessException;

	/**
	 * Delete supplied branch department.
	 * 
	 * @param branchDepartment
	 *            branch department.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	public void removeBranchDepartment(final BranchDepartment branchDepartment) throws BusinessException;

	/**
	 * Retrieves the previous branch department periodical for supplied branch
	 * department.
	 * 
	 * @param branchDepartment
	 *            the branch department.
	 * @return the previous branch department periodical for supplied branch
	 *         department.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchDepartment findPreviousBranchDepartmentPeriodical(final BranchDepartment branchDepartment)
			throws BusinessException;

	/**
	 * Retrieves the branch department based on branch assembly and date.
	 * 
	 * @param branchAssembly
	 *            the branch assembly.
	 * @param referenceDate
	 *            the reference date.
	 * @return the the branch department based on branch assembly and date.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchDepartment findBranchDepartmentByBranchAssemblyAndDate(final BranchAssembly branchAssembly,
			final Date referenceDate) throws BusinessException;

	/**
	 * Retrieves the branch departments by branch assembly.
	 * 
	 * @param branchAssembly
	 *            branch assembly.
	 * @return latest branch department.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchDepartment findLatestBranchDepartment(final BranchAssembly branchAssembly) throws BusinessException;

	/**
	 * Removes all branch departments for the given branch assembly.
	 * 
	 * @param branchAssembly
	 *            branch assembly.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeBranchDepartmentByBranchAssembly(final BranchAssembly branchAssembly) throws BusinessException;

	/**
	 * Ends branch department for the branch. Branch department is validated
	 * properly before it is ended.
	 * 
	 * @param branchDepartment
	 *            the branch department.
	 * @return updated branch department.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchDepartment endBranchDepartment(final BranchDepartment branchDepartment) throws BusinessException;

	/**
	 * Retrieves the branch department based on branch assembly and start date.
	 * 
	 * @param branchAssembly
	 *            the branch assembly.
	 * @param referenceDate
	 *            the start date.
	 * @return the branch department based on branch assembly and start date.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchDepartment findBranchDepartmentByBranchAssemblyAndStartDate(final BranchAssembly branchAssembly,
			final Date referenceDate) throws BusinessException;

	/**
	 * Retrieves the branch department based on branch assembly and end date.
	 * 
	 * @param branchAssembly
	 *            the branch assembly.
	 * @param referenceDate
	 *            the end date.
	 * @return the branch department based on branch assembly and end date.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchDepartment findBranchDepartmentByBranchAssemblyAndEndDate(final BranchAssembly branchAssembly,
			final Date referenceDate) throws BusinessException;
}
