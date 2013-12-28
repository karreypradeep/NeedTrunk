/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.financial.model.StudentFeeTransaction;
import com.apeironsol.need.util.constants.StudentFeeTransactionStatusConstant;
import com.apeironsol.need.util.searchcriteria.FeeCollectedSearchCriteria;
import com.apeironsol.need.util.searchcriteria.StudentFeeTransactionSearchCriteria;

/**
 * Service Interface forStudentSection.
 * 
 * @author Pradeep
 * 
 */
public interface StudentFeeTransactionService {

	StudentFeeTransaction saveStudentFeeTransaction(final StudentFeeTransaction studentFeeTransaction);

	void removeStudentFeeTransaction(final StudentFeeTransaction studentFeeTransaction);

	/**
	 * Retrieve student fee transactions by transaction number.
	 * 
	 * @param transactionNr
	 * @return collection of student fee transactions.
	 */
	StudentFeeTransaction findStudentFeeTransactionsByTransactionNr(String transactionNr);

	/**
	 * Retrieve student fee transactions by transaction status.
	 * 
	 * @param studentFeeTransactionStatusConstants
	 *            studentFeeTransactionStatusConstants.
	 * @return student fee transactions by transaction status.
	 */
	Collection<StudentFeeTransaction> findStudentFeeTransactionsByBranchIdAndTransactionStatusBetweenDates(Long branchId,
			EnumSet<StudentFeeTransactionStatusConstant> studentFeeTransactionStatusConstants, Date fromDate, Date toDate);

	/**
	 * Find all student fee transactions that match the search criteria.
	 * 
	 * @param studentFeeTransactionSearchCriteria
	 *            studentFeeTransactionSearchCriteria.
	 * @return collection of all student fee transactions that match the search
	 *         criteria.
	 * @throws BusinessException
	 *             In case of Exception.
	 */
	Collection<StudentFeeTransaction> findStudentFeeTransactionsBySearchCriteria(final StudentFeeTransactionSearchCriteria studentFeeTransactionSearchCriteria)
			throws BusinessException;

	Collection<StudentFeeTransaction> findFeesCollectedBySearchCriteria(final FeeCollectedSearchCriteria feeCollectedSearchCriteria) throws BusinessException;
}
