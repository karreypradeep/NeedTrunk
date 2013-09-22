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
import com.apeironsol.need.inventory.model.BranchLiability;
import com.apeironsol.need.util.searchcriteria.BranchLiabilitySearchCriteria;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service layer interface for branch liability DAO implementation.
 * 
 * @author Pradeep
 * 
 */
public interface BranchLiabilityService {

	/**
	 * Find branch liabilities by branch liability Id.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @return collection of branch liabilities by branch liability Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchLiability> findBranchLiabilitiesByBranchId(Long branchId) throws BusinessException;

	/**
	 * Retrieve branch liability by object id.
	 * 
	 * @param id
	 *            id of branch expense.
	 * @return branch liability by object id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchLiability findBranchLiabilityById(Long id) throws BusinessException;

	/**
	 * Saves supplied branch expense.
	 * 
	 * @param academicYearLiability
	 *            branch expense.
	 * @return branch liability saved.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 *             In case supplied parameter is not valid.
	 */
	BranchLiability saveBranchLiability(BranchLiability academicYearLiability) throws BusinessException,
			InvalidArgumentException;

	/**
	 * Removes branch expense.
	 * 
	 * @param academicYearLiability
	 *            branch liability to be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeBranchLiability(BranchLiability academicYearLiability) throws BusinessException;

	/**
	 * Retrieves all branch liabilities.
	 * 
	 * @return collection of all branch liabilities.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchLiability> findAllBranchLiabilities() throws BusinessException;

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
			Long buildingBlockId, AcademicYear academicYear) throws BusinessException;

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
