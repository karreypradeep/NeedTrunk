/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.need.financial.model.StudentPocketMoneyTransaction;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service layer interface for branch income DAO implementation.
 * 
 * @author Pradeep
 * 
 */
public interface StudentPocketMoneyService {

	/**
	 * Find student pocket money transactions by student section Id.
	 * 
	 * @param studentSectionId
	 *            student section Id.
	 * @return collection of student pocket money transactions by student
	 *         section Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<StudentPocketMoneyTransaction> findStudentPocketMoneyTransactionByStudentSectionId(Long studentSectionId)
			throws BusinessException;

	/**
	 * Retrieve student pocket money transaction by object id.
	 * 
	 * @param id
	 *            id of student pocket money transaction.
	 * @return student pocket money transaction by object id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	StudentPocketMoneyTransaction findStudentPocketMoneyTransactionIncomeById(Long id) throws BusinessException;

	/**
	 * Saves supplied student pocket money transaction.
	 * 
	 * @param studentPocketMoneyTransaction
	 *            student pocket money transaction.
	 * @return student pocket money transaction saved.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 *             In case supplied parameter is not valid.
	 */
	StudentPocketMoneyTransaction saveStudentPocketMoneyTransaction(
			StudentPocketMoneyTransaction studentPocketMoneyTransaction) throws BusinessException,
			InvalidArgumentException;

	/**
	 * Removes student pocket money transaction.
	 * 
	 * @param studentPocketMoneyTransaction
	 *            student pocket money transaction to be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeStudentPocketMoneyTransaction(StudentPocketMoneyTransaction studentPocketMoneyTransaction)
			throws BusinessException;

	/**
	 * Retrieves all student pocket money transactions.
	 * 
	 * @return collection of all student pocket money transactions.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<StudentPocketMoneyTransaction> findAllStudentPocketMoneyTransactions() throws BusinessException;

}
