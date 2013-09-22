/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.dao;

import java.util.Collection;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.financial.model.BranchInvestment;
import com.apeironsol.need.util.searchcriteria.BranchInvestmentSearchCriteria;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for BranchInvestment entity implementation.
 * 
 * @author Pradeep
 * 
 */
public interface BranchInvestmentDao extends BaseDao<BranchInvestment> {

	/**
	 * Find branch investments by branch Id.
	 * 
	 * @param branchInvestmentId
	 *            branch investment Id.
	 * @return collection of branch investments by branch investment Id.
	 */
	Collection<BranchInvestment> findBranchInvestmentsByBranchId(Long branchId);

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
			Long buildingBlockId, final AcademicYear academicYear) throws BusinessException;

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
	 * @return branch investment by search criteria supplied.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchInvestment> findBranchInvestmentBySearchCriteria(
			final BranchInvestmentSearchCriteria branchInvestmentSearchCriteria) throws BusinessException;
}
