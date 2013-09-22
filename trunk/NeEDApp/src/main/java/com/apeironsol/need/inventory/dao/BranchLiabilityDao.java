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
import com.apeironsol.need.inventory.model.BranchLiability;
import com.apeironsol.need.util.searchcriteria.BranchLiabilitySearchCriteria;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for BranchLiability entity implementation.
 * 
 * @author Pradeep
 * 
 */
public interface BranchLiabilityDao extends BaseDao<BranchLiability> {

	/**
	 * Find branch liabilities by branch Id.
	 * 
	 * @param branchLiabilityId
	 *            branch liability Id.
	 * @return collection of branch liabilities by branch liability Id.
	 */
	Collection<BranchLiability> findBranchLiabilitiesByBranchId(Long branchId);

	/**
	 * Find branch liabilities by branch liability Id and building block id and
	 * liability date.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @param buildingBlockId
	 *            building block id.
	 * @param academicYear
	 *            academicYear.
	 * @return collection of branch liabilities by branch liability Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchLiability> findBranchLiabilitiesByBranchIdBuildingBLockIdAndAcademicYear(Long branchId,
			Long buildingBlockId, final AcademicYear academicYear) throws BusinessException;

	/**
	 * Find branch liabilities by branch liability Id and
	 * liability date.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @param academicYear
	 *            academicYear.
	 * @return collection of branch liabilities by branch liability Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchLiability> findBranchLiabilitiesByBranchIdAndAcademicYear(Long branchId, AcademicYear academicYear)
			throws BusinessException;

	/**
	 * Returns branch liabilities by search criteria supplied.
	 * 
	 * @param branchLiabilitySearchCriteria
	 *            branchLiabilitySearchCriteria.
	 * @return branch liabilities by search criteria supplied.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchLiability> findBranchLiabilitiesBySearchCriteria(
			final BranchLiabilitySearchCriteria branchLiabilitySearchCriteria) throws BusinessException;
}
