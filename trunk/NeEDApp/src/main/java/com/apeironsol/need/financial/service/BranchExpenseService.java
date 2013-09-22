/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.service;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.financial.model.BranchExpense;
import com.apeironsol.need.util.searchcriteria.BranchExpenseSearchCriteria;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service layer interface for branch expense DAO implementation.
 * 
 * @author Pradeep
 * 
 */
public interface BranchExpenseService {

	/**
	 * Find branch expenses by branch expense Id.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @return collection of branch expenses by branch expense Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchExpense> findBranchExpensesByBranchId(Long branchId) throws BusinessException;

	/**
	 * Retrieve branch expense by object id.
	 * 
	 * @param id
	 *            id of branch expense.
	 * @return branch expense by object id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchExpense findBranchExpenseById(Long id) throws BusinessException;

	/**
	 * Saves supplied branch expense.
	 * 
	 * @param academicYearExpense
	 *            branch expense.
	 * @return branch expense saved.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 *             In case supplied parameter is not valid.
	 */
	BranchExpense saveBranchExpense(BranchExpense academicYearExpense) throws BusinessException, InvalidArgumentException;

	/**
	 * Removes branch expense.
	 * 
	 * @param academicYearExpense
	 *            branch expense to be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeBranchExpense(BranchExpense academicYearExpense) throws BusinessException;

	/**
	 * Retrieves all branch expenses.
	 * 
	 * @return collection of all branch expenses.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BranchExpense> findAllBranchExpenses() throws BusinessException;

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
	Collection<BranchExpense> findBranchExpensesByBranchIdBuildingBLockIdAndAcademicYear(Long branchId, Long buildingBlockId, AcademicYear academicYear)
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
	 * Returns branch expenses by search criteria supplied.
	 * 
	 * @param branchExpenseSearchCriteria
	 *            branchExpenseSearchCriteria.
	 * @return branch expenses by search criteria supplied.
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
