/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.inventory.dao;

import java.util.Collection;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.inventory.model.BranchAssert;
import com.apeironsol.need.util.searchcriteria.BranchAssertSearchCriteria;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for BranchAssert entity implementation.
 * 
 * @author Pradeep
 * 
 */
public interface BranchAssertDao extends BaseDao<BranchAssert> {

	/**
	 * Find branch asserts by branch Id.
	 * 
	 * @param branchAssertId
	 *            branch assert Id.
	 * @return collection of branch asserts by branch assert Id.
	 */
	Collection<BranchAssert> findBranchAssertsByBranchId(Long branchId);

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
			Long buildingBlockId, final AcademicYear academicYear) throws BusinessException;

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
