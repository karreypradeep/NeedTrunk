/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.need.financial.model.StudentFee;
import com.apeironsol.need.financial.model.StudentFeeTransactionDetails;

/**
 * Service Interface forStudentSection.
 * 
 * @author Pradeep
 * 
 */
public interface StudentFeeTransactionDetailsService {

	StudentFeeTransactionDetails saveStudentFeeTransactionDetails(final StudentFeeTransactionDetails studentFeeTransactionDetails);

	void removeStudentFeeTransactionDetails(final StudentFeeTransactionDetails studentFeeTransactionDetails);

	/**
	 * Retrieve student fee transactions by class fee id.
	 * 
	 * @param klassFeePaymentId
	 *            class fee payment id.
	 * @return collection of student fee transactions by class fee id.
	 */
	Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByKlassFeePaymentId(final Long klassFeePaymentId);

	/**
	 * Retrieve student fee transactions by student id and academic year id.
	 * 
	 * @param studentId
	 *            student id.
	 * @param academicYearId
	 *            academic year id.
	 * @return collection of student fee transactions by student id and academic
	 *         year id.
	 */
	Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentIdAndAcademicYearId(Long studentId, Long academicYearId);

	/**
	 * Retrieve student fee transactions by student fee id.
	 * 
	 * @param studentFeeId
	 *            student fee id.
	 * @return collection of student fee transactions by student fee id.
	 */
	Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentFeeId(Long studentFeeId);

	/**
	 * Retrieve student fee transactions by class fee id and student fee id.
	 * 
	 * @param klassFeePaymentId
	 *            class fee payment id.
	 * @param studentFeeId
	 *            student fee id.
	 * @return collection of student fee transactions by class fee id and
	 *         student fee id.
	 */
	Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByKlassFeePaymentIdAndStudentFeeId(final Long klassFeePaymentId,
			final Long studentFeeId);

	/**
	 * Retrieve student fee transactions by transaction number.
	 * 
	 * @param transactionNr
	 * @return collection of student fee transactions.
	 */
	Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentFeeTransactionId(Long studentFeeTransactionId);

	/**
	 * Retrieve student fee transactions by student fee id.
	 * 
	 * @param studentFeeId
	 *            student fee id.
	 * @return collection of student fee transactions by student fee id.
	 */
	Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentFees(final Collection<StudentFee> studentFees);

	/**
	 * 
	 * @param studentAdditionalFeeId
	 * @return
	 */
	Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentAdditionalFeeId(Long studentAdditionalFeeId);

	/**
	 * 
	 * @param studentLevelFeeCatalogId
	 * @param studentFeeId
	 * @return
	 */
	Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentLevelFeeCatalogIdAndStudentFeeId(Long studentLevelFeeCatalogId,
			Long studentFeeId);

	/**
	 * 
	 * @param branchLevelFeeId
	 * @param studentFeeId
	 * @return
	 */
	Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByBranchLevelFeeCatalogtIdAndStudentFeeId(Long branchLevelFeeId, Long studentFeeId);

	/**
	 * 
	 * @param branchLevelFeeId
	 * @param studentFeeId
	 * @return
	 */
	Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByPickUpPointFeeCatalogIdAndStudentFeeId(Long pickUpPointFeeCatalogId,
			Long studentFeeId);
}
