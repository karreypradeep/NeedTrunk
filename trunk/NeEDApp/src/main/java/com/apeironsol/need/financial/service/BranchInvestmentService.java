/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.service;

import java.util.Collection;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.financial.model.BranchInvestment;
import com.apeironsol.need.util.searchcriteria.BranchInvestmentSearchCriteria;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service layer interface for branch investment DAO implementation.
 * 
 * @author Pradeep
 * 
 */
public interface BranchInvestmentService {

	/**
	 * Find branch investments by branch investment Id.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @return collection of branch investments by branch investment Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchInvestment> findBranchInvestmentsByBranchId(Long branchId) throws BusinessException;

	/**
	 * Retrieve branch investment by object id.
	 * 
	 * @param id
	 *            id of branch investment.
	 * @return branch investment by object id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchInvestment findBranchInvestmentById(Long id) throws BusinessException;

	/**
	 * Saves supplied branch investment.
	 * 
	 * @param academicYearInvestment
	 *            branch investment.
	 * @return branch investment saved.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 *             In case supplied parameter is not valid.
	 */
	BranchInvestment saveBranchInvestment(BranchInvestment academicYearInvestment) throws BusinessException,
			InvalidArgumentException;

	/**
	 * Removes branch investment.
	 * 
	 * @param academicYearInvestment
	 *            branch investment to be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeBranchInvestment(BranchInvestment academicYearInvestment) throws BusinessException;

	/**
	 * Retrieves all branch investments.
	 * 
	 * @return collection of all branch investments.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchInvestment> findAllBranchInvestments() throws BusinessException;

	/**
	 * Find branch investments by branch investment Id and building block id and
	 * investment date.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @param buildingBlockId
	 *            building block id.
	 * @param academicYear
	 *            academicYear.
	 * @return collection of branch investments by branch investment Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchInvestment> findBranchInvestmentsByBranchIdBuildingBLockIdAndAcademicYear(Long branchId,
			Long buildingBlockId, AcademicYear academicYear) throws BusinessException;

	/**
	 * Find branch investments by branch investment Id and
	 * investment date.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @param academicYear
	 *            academicYear.
	 * @return collection of branch investments by branch investment Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchInvestment> findBranchInvestmentsByBranchIdAndAcademicYear(Long branchId, AcademicYear academicYear)
			throws BusinessException;

	/**
	 * Returns branch investment by search criteria supplied.
	 * 
	 * @param branchInvestmentSearchCriteria
	 *            branchInvestmentSearchCriteria.
	 * @return branch expenses by search criteria supplied.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchInvestment> findBranchInvestmentBySearchCriteria(
			final BranchInvestmentSearchCriteria branchInvestmentSearchCriteria) throws BusinessException;
}
