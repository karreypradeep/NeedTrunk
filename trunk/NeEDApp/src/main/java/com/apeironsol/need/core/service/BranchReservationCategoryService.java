/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.sql.Date;
import java.util.Collection;

import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.core.model.BranchReservationCategory;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service Interface for branch reservation category. This service act as
 * controller for branch reservation category.
 * 
 * @author Pradeep
 * 
 */
public interface BranchReservationCategoryService {

	/**
	 * Retrieves the Branch reservation category by id.
	 * 
	 * @param id
	 *            the id of Branch reservation category to be retrieved.
	 * @return the Branch reservation category.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchReservationCategory findBranchReservationCategoryById(Long id) throws BusinessException;

	/**
	 * Retrieves all branch reservation categories by branch assembly.
	 * 
	 * @return all branch reservation categories by branch assembly.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchReservationCategory> findBranchReservationCategoriesByBranchAssembly(BranchAssembly branchAssembly)
			throws BusinessException;

	/**
	 * Persists the Branch reservation category.
	 * 
	 * @return the persisted Branch reservation category.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchReservationCategory saveBranchReservationCategory(BranchReservationCategory branchReservationCategory)
			throws BusinessException;

	/**
	 * Delete supplied branch reservation category.
	 * 
	 * @param branchReservationCategory
	 *            branch reservation category.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	public void removeBranchReservationCategory(final BranchReservationCategory branchReservationCategory)
			throws BusinessException;

	/**
	 * Retrieves the previous branch reservation category periodical for
	 * supplied branch reservation category.
	 * 
	 * @param branchReservationCategory
	 *            the branch reservation category.
	 * @return the previous branch reservation category periodical for supplied
	 *         branch reservation category.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchReservationCategory findPreviousBranchReservationCategoryPeriodical(
			final BranchReservationCategory branchReservationCategory) throws BusinessException;

	/**
	 * Retrieves the branch reservation category based on branch assembly and
	 * date.
	 * 
	 * @param branchAssembly
	 *            the branch assembly.
	 * @param referenceDate
	 *            the reference date.
	 * @return the branch reservation category based on branch assembly and
	 *         date.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchReservationCategory findBranchReservationCategoryByBranchAssemblyAndDate(final BranchAssembly branchAssembly,
			final Date referenceDate) throws BusinessException;

	/**
	 * Retrieves the branch reservation category by id.
	 * 
	 * @param branchAssembly
	 *            branch assembly.
	 * @return the latest branch reservation category.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchReservationCategory findLatestBranchReservationCategory(final BranchAssembly branchAssembly)
			throws BusinessException;

	/**
	 * Removes all branch reservation category for the given branch assembly.
	 * 
	 * @param branchAssembly
	 *            branch assembly.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeBranchReservationCategoryByBranchAssembly(final BranchAssembly branchAssembly) throws BusinessException;
}
