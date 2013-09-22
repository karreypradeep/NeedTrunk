/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import com.apeironsol.need.util.constants.StudentPocketMoneyTransactionTypeConstant;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service interface for batch.
 * 
 * @author Pradeep
 * 
 */
public interface SequenceGeneratorService {

	/**
	 * Retrieve next registration number for sequence sub type.
	 * 
	 * @param sequenceSubType
	 *            sequence sub type.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 */
	String getNextRegistrationNumber(final String sequenceSubType) throws BusinessException;

	/**
	 * Retrieve next admission number for sequence sub type.
	 * 
	 * @param sequenceSubType
	 *            sequence sub type.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 */
	String getNextAdmissionNumber(final String sequenceSubType) throws BusinessException;

	/**
	 * Generate next sequence number for fee deposit related transactions
	 * 
	 * @return Transactions Number.
	 * @throws BusinessException
	 *             in case of exception.
	 */
	String getNextTransactionNumberForFeeDeposit() throws BusinessException;

	/**
	 * Generate next sequence number for fee refund related transactions
	 * 
	 * @return Transactions Number.
	 * @throws BusinessException
	 *             in case of exception.
	 */
	String getNextTransactionNumberForFeeRefund() throws BusinessException;

	/**
	 * Generate next sequence number for fee deduct related transactions
	 * 
	 * @return Transactions Number.
	 * @throws BusinessException
	 *             in case of exception.
	 */
	String getNextTransactionNumberForFeeDeduct() throws BusinessException;

	/**
	 * Generate next sequence number for purchase order
	 * 
	 * @return Transactions Number.
	 * @throws BusinessException
	 *             in case of exception.
	 */
	String getNextPurchaseOrderNumber() throws BusinessException;

	/**
	 * Generate Unique employee number with in the system.
	 * 
	 * @return Employee number.
	 * @throws BusinessException
	 *             in case of exception.
	 */
	String getNextEmployeeNumber(final String sequenceSubType) throws BusinessException;

	/**
	 * Generate next sequence number for pocket money related transactions
	 * 
	 * @return Transactions Number.
	 * @throws BusinessException
	 *             in case of exception.
	 */
	String getNextTransactionNumberForStudentPocketMoney(final StudentPocketMoneyTransactionTypeConstant studentPocketMoneyTransactionTypeConstant)
			throws BusinessException;

	/**
	 * Generate next sequence number for salary paid related transactions
	 * 
	 * @return Transactions Number.
	 * @throws BusinessException
	 *             in case of exception.
	 */
	String getNextTransactionNumberForSalaryPaid() throws BusinessException;

	String getNextTransactionNumberForBankAccountTransaction() throws BusinessException;

	String getNextTransactionNumberForExpense() throws BusinessException;

	String getNextTransactionNumberForInvoice() throws BusinessException;

	String getNextTransactionNumberForInvestment() throws BusinessException;

	String getNextTransactionNumberForCreditAccountTransaction() throws BusinessException;

}
