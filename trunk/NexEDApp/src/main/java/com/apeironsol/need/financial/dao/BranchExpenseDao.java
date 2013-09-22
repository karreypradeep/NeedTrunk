/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.dao;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.financial.model.BranchExpense;
import com.apeironsol.need.util.searchcriteria.BranchExpenseSearchCriteria;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for BranchExpense entity implementation.
 * 
 * @author Pradeep
 * 
 */
public interface BranchExpenseDao extends BaseDao<BranchExpense> {

	/**
	 * Find branch expenses by branch Id.
	 * 
	 * @param branchExpenseId
	 *            branch expense Id.
	 * @return collection of branch expenses by branch expense Id.
	 */
	Collection<BranchExpense> findBranchExpensesByBranchId(Long branchId);

	/**
	 * Find branch expenses by branch expense Id and building block id and
	 * expense date.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @param buildingBlockId
	 *            building block id.
	 * @param academicYear
	 *            academicYear.
	 * @return collection of branch expenses by branch expense Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchExpense> findBranchExpensesByBranchIdBuildingBLockIdAndAcademicYear(Long branchId, Long buildingBlockId, final AcademicYear academicYear)
			throws BusinessException;

	/**
	 * Find branch expenses by branch expense Id and
	 * expense date.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @param academicYear
	 *            academicYear.
	 * @return collection of branch expenses by branch expense Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchExpense> findBranchExpensesByBranchIdAndAcademicYear(Long branchId, AcademicYear academicYear) throws BusinessException;

	/**
	 * Returns branch expense by search criteria supplied.
	 * 
	 * @param branchExpenseSearchCriteria
	 *            branchIncomeSearchCriteria.
	 * @return branch expense by search criteria supplied.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchExpense> findBranchExpensesBySearchCriteria(final BranchExpenseSearchCriteria branchExpenseSearchCriteria) throws BusinessException;

	/**
	 * Find branch expenses by branch expense Id and
	 * expense date.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @param academicYear
	 *            academicYear.
	 * @return collection of branch expenses by branch expense Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchExpense> findBranchExpensesByBranchIdBetweenDates(Long branchId, Date fromDate, Date toDate) throws BusinessException;

}
