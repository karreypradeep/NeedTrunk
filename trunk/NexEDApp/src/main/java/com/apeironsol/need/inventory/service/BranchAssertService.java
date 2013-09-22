/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.inventory.service;

import java.util.Collection;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.inventory.model.BranchAssert;
import com.apeironsol.need.util.searchcriteria.BranchAssertSearchCriteria;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service layer interface for branch assert DAO implementation.
 * 
 * @author Pradeep
 * 
 */
public interface BranchAssertService {

	/**
	 * Find branch asserts by branch assert Id.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @return collection of branch asserts by branch assert Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchAssert> findBranchAssertsByBranchId(Long branchId) throws BusinessException;

	/**
	 * Retrieve branch assert by object id.
	 * 
	 * @param id
	 *            id of branch assert.
	 * @return branch assert by object id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchAssert findBranchAssertById(Long id) throws BusinessException;

	/**
	 * Saves supplied branch assert.
	 * 
	 * @param academicYearAssert
	 *            branch assert.
	 * @return branch assert saved.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 *             In case supplied parameter is not valid.
	 */
	BranchAssert saveBranchAssert(BranchAssert academicYearAssert) throws BusinessException, InvalidArgumentException;

	/**
	 * Removes branch assert.
	 * 
	 * @param academicYearAssert
	 *            branch assert to be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeBranchAssert(BranchAssert academicYearAssert) throws BusinessException;

	/**
	 * Retrieves all branch asserts.
	 * 
	 * @return collection of all branch asserts.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchAssert> findAllBranchAsserts() throws BusinessException;

	/**
	 * Find branch asserts by branch assert Id and building block id and
	 * assert date.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @param buildingBlockId
	 *            building block id.
	 * @param academicYear
	 *            academicYear.
	 * @return collection of branch asserts by branch assert Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchAssert> findBranchAssertsByBranchIdBuildingBLockIdAndAcademicYear(Long branchId,
			Long buildingBlockId, AcademicYear academicYear) throws BusinessException;

	/**
	 * Find branch asserts by branch assert Id and
	 * assert date.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @param academicYear
	 *            academicYear.
	 * @return collection of branch asserts by branch assert Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchAssert> findBranchAssertsByBranchIdAndAcademicYear(Long branchId, AcademicYear academicYear)
			throws BusinessException;

	/**
	 * Returns branch asserts by search criteria supplied.
	 * 
	 * @param branchAssertSearchCriteria
	 *            branchAssertSearchCriteria.
	 * @return branch asserts by search criteria supplied.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchAssert> findBranchAssertsBySearchCriteria(
			final BranchAssertSearchCriteria branchAssertSearchCriteria) throws BusinessException;

}
