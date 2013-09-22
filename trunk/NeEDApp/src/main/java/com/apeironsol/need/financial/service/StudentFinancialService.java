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
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentAcademicYearFeeSummary;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.financial.model.StudentFeeTransaction;
import com.apeironsol.need.financial.model.StudentFeeTransactionDetails;
import com.apeironsol.need.util.constants.StudentFeeTransactionStatusConstant;
import com.apeironsol.need.util.dataobject.StudentFeeDetailsDO;
import com.apeironsol.need.util.dataobject.StudentFeeTransactionDO;
import com.apeironsol.need.util.dataobject.StudentFinancialAcademicYearDO;
import com.apeironsol.need.util.dataobject.StudentFinancialDO;
import com.apeironsol.need.util.searchcriteria.FeeCollectedSearchCriteria;
import com.apeironsol.need.util.searchcriteria.FeeDueSearchCriteria;
import com.apeironsol.need.util.searchcriteria.StudentFeeTransactionSearchCriteria;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * Service for student financial details.
 *
 * @author pradeep
 *
 */
public interface StudentFinancialService {

	/**
	 * Retrieve student financial data by student id and academic year id.
	 *
	 * @param studentId
	 *            student id.
	 * @param acadmicYearId
	 *            academic year id.
	 * @return collection of student financial data by student id and academic
	 *         year id.
	 */
	Collection<StudentFinancialDO> getStudentFinancialDetailsByStudentIdAndAcadmicYearId(Long studentId, Long acadmicYearId);

	/**
	 * Retrieve student fee transactions by student id and academic year id.
	 *
	 * @param studentId
	 *            student id.
	 * @param acadmicYearId
	 *            academic year id.
	 * @return collection of student fee transactions by student id and academic
	 *         year id.
	 */
	Collection<StudentFeeTransactionDetails> getStudentFeeTransactionByStudentIdAndAcademicYearId(Long studentId, Long academicYearId);

	/**
	 * Deposit payments.
	 *
	 * @param studentFinancialTransactionDOs
	 *            studentFinancialTransactionDOs.
	 * @return transaction number if the deposit is successful.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws SystemException
	 *             In case of exception.
	 */
	StudentFeeTransaction depositPayments(final Long branchId, final Long studentAcademicYearId, final StudentFeeTransaction studentFeeTransaction,
			final Collection<StudentFeeDetailsDO> studentFeeDetailsDOs) throws BusinessException, SystemException;

	/**
	 * Refund payments.
	 *
	 * @param studentFinancialTransactionDOs
	 *            studentFinancialTransactionDOs.
	 * @return transaction number if the deposit is successful.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws SystemException
	 *             In case of exception.
	 */
	StudentFeeTransaction requestForRefund(final Long studentAcademicYearId, final StudentFeeTransaction studentFeeTransaction,
			final Collection<StudentFeeDetailsDO> studentFeeDetailsDOs) throws BusinessException, SystemException;

	/**
	 * Deduct payments.
	 *
	 * @param studentFinancialTransactionDOs
	 *            studentFinancialTransactionDOs.
	 * @return transaction number if the deposit is successful.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws SystemException
	 *             In case of exception.
	 */
	StudentFeeTransaction requestForDeduction(final Long studentAcademicYearId, final StudentFeeTransaction studentFeeTransaction,
			final Collection<StudentFeeDetailsDO> studentFeeDetailsDOs) throws BusinessException, SystemException;

	// /**
	// * Get student fee transactions by student fees.
	// *
	// * @param studentFees
	// * student fees.
	// * @return collection of student fee transactions by student fees.
	// */
	// Collection<StudentFeeTransactionDO>
	// getStudentFeeTransactionsByStudentFees(Collection<StudentFee>
	// studentFees);

	/**
	 * Return total fees due till due date for the student and academic year
	 * specified.
	 *
	 * @param student
	 *            student.
	 * @param academicYear
	 *            academic year.
	 * @param dueDate
	 *            due date.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws SystemException
	 *             In case of exception.
	 */
	Double getStudentFeeDue(final Student student, final AcademicYear academicYear, final Date dueDate) throws BusinessException, SystemException;

	/**
	 * Retrieve fee payment details by transaction number.
	 *
	 * @param transactionNr
	 *            the transaction number.
	 * @return Student fee payment transaction by transaction number.
	 */
	StudentFeeTransactionDO retriveStudentFeeTransactionDetailsByTransactionNr(String transactionNr);

	/**
	 * Retrieve student fee transaction details by student id.
	 *
	 * @param studentId
	 *            the student id.
	 * @return student fee transaction detail objects.
	 */
	Collection<StudentFeeTransaction> getStudentFeeTransactionsByStudentIdAndAcademicYearId(final Long studentId, final Long academicYearId);

	/**
	 * Save the student fee transaction.
	 *
	 * @param studentFeeTransaction
	 *            student fee transaction to be updated.
	 * @return Updated the student fee transaction.
	 */
	StudentFeeTransaction updateStudentFeeTransactionStatus(final StudentFeeTransaction studentFeeTransaction,
			final StudentFeeTransactionStatusConstant transactionStatus, final boolean updateStudentAcademicYearFeeSummary);

	/**
	 *
	 * @param studentAcademicYearId
	 * @param studentFeeTransaction
	 * @param studentFeeDetailsDOs
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	StudentFeeTransaction approveRefund(final Long studentAcademicYearId, final StudentFeeTransaction studentFeeTransaction,
			final Collection<StudentFeeDetailsDO> studentFeeDetailsDOs) throws BusinessException, SystemException;

	/**
	 * Deduct payments.
	 *
	 * @param studentFinancialTransactionDOs
	 *            studentFinancialTransactionDOs.
	 * @return transaction number if the deposit is successful.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws SystemException
	 *             In case of exception.
	 */
	StudentFeeTransaction approveDeduction(final Long studentAcademicYearId, final StudentFeeTransaction studentFeeTransaction,
			final Collection<StudentFeeDetailsDO> studentFeeDetailsDOs) throws BusinessException, SystemException;

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
	Collection<StudentFeeTransaction> findFeesCollectedBySearchCriteria(final FeeCollectedSearchCriteria feeCollectedSearchCriteria) throws BusinessException;

	/**
	 * Retrieve student fee transaction details by student academic year id.
	 *
	 * @param studentAcademicYearId
	 *            student academic year id.
	 * @return student fee transaction detail objects.
	 */
	Collection<StudentFeeTransaction> getStudentFeeTransactionsByStudentAcademicYearId(final Long studentAcademicYearId);

	/**
	 * Return student financial details by academic year and student for due
	 * date.
	 *
	 * @param studentId
	 * @param acadmicYearId
	 * @param dueDate
	 * @return
	 */
	Collection<StudentFinancialDO> getStudentFinancialDetailsByStudentIdAndAcadmicYearIdForDueDate(final Long studentId, final Long acadmicYearId,
			final Date dueDate);

	/**
	 * Retrieve branch fee financial details by academic year.
	 *
	 * @param student
	 *            student.
	 * @param academicYearId
	 *            academic year id.
	 * @return branch fee financial details by academic year.
	 */
	StudentFinancialAcademicYearDO getStudnetFeeFinancialAcademicYearDetailsByAcademicYearIdForDueDate(final StudentSection studentSection, final Date dueDate);

	/**
	 * Retrieve fee defaulters by FeeDueSearchCriteria.
	 *
	 * @param feeDueSearchCriteria
	 *            feeDueSearchCriteria.
	 * @return branch fee financial details by feeDueSearchCriteria.
	 */
	Collection<StudentFinancialAcademicYearDO> getFeeDefaultersBySearchCriteria(final FeeDueSearchCriteria feeDueSearchCriteria);

	/**
	 * Retrieve student fee financial details by academic year.
	 *
	 * @param studentSections
	 * @param academicYearId
	 * @param dueDate
	 * @return
	 */
	Collection<StudentFinancialAcademicYearDO> getStudnetFeeFinancialAcademicYearDetailsByAcademicYearIdForDueDate(
			final Collection<StudentSection> studentSections, final Date dueDate);

	/**
	 * Revert Student Fee transaction.
	 *
	 * @param studentFeeTransaction
	 * @return
	 */
	StudentFeeTransaction revertStudentFeeTransaction(final StudentFeeTransaction studentFeeTransaction);

	/**
	 * Retrieve fee payment details by transaction number.
	 *
	 * @param transactionId
	 *            the transaction id.
	 * @return Student fee payment transaction by transaction number.
	 */
	StudentFeeTransactionDO retriveStudentFeeTransactionDetailsByTransactionId(final Long transactionId);

	/**
	 *
	 * @param transactionNr
	 * @return
	 */
	StudentFeeTransaction retriveStudentFeeTransactionByTransactionNr(final String transactionNr);

	/**
	 *
	 * @param studentAcademicYearId
	 * @return
	 */
	StudentAcademicYearFeeSummary processStudentAcademicYearFeeSummary(final Long studentAcademicYearId);

	/**
	 *
	 * @param studentId
	 * @param academicYearId
	 * @return
	 */
	boolean hasOutstandingFeeDue(Long studentId, Long academicYearId);

	/**
	 * Reject refund for student fee.
	 *
	 * @param studentFeeTransaction
	 * @return
	 */
	StudentFeeTransaction rejectRefundRequest(final StudentFeeTransaction studentFeeTransaction) throws BusinessException, SystemException;

	/**
	 * Approve refund for student fee.
	 *
	 * @param studentFeeTransaction
	 * @return
	 */
	StudentFeeTransaction approveRefundRequest(final StudentFeeTransaction studentFeeTransaction) throws BusinessException, SystemException;

	/**
	 * Reject deduction for student fee.
	 *
	 * @param studentFeeTransaction
	 * @return
	 */
	StudentFeeTransaction rejectDeductionRequest(final StudentFeeTransaction studentFeeTransaction) throws BusinessException, SystemException;

	/**
	 * Approve deduction for student fee.
	 *
	 * @param studentFeeTransaction
	 * @return
	 */
	StudentFeeTransaction approveDeductionRequest(final StudentFeeTransaction studentFeeTransaction) throws BusinessException, SystemException;

	/**
	 * Process payment for student fee
	 *
	 * @param studentFeeTransaction
	 * @return
	 */
	StudentFeeTransaction processPaymentRequest(final StudentFeeTransaction studentFeeTransaction) throws BusinessException, SystemException;

	/**
	 * Process refund request
	 * @param studentFeeTransaction
	 * @return
	 */
	StudentFeeTransaction processRefundRequest(StudentFeeTransaction studentFeeTransaction) throws BusinessException, SystemException;

}
