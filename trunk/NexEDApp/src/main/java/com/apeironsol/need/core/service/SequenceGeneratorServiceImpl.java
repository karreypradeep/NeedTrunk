/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */

package com.apeironsol.need.core.service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.SequenceGeneratorDao;
import com.apeironsol.need.core.model.SequenceGenerator;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.SequancialKeyLockableFactory;
import com.apeironsol.need.util.SequancialKeyLockableFactory.EntityLockable;
import com.apeironsol.need.util.constants.SequenceTypeConstant;
import com.apeironsol.need.util.constants.StudentPocketMoneyTransactionTypeConstant;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service interface implementation for Batch.
 * 
 * @author Pradeep
 * @author Pradeep
 * 
 */
@Service("sequenceGeneratorService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService {

	@Resource
	private SequenceGeneratorDao	sequenceGeneratorDao;

	/**
	 * Get next sequence number.
	 * 
	 * @param sequenceType
	 *            sequence type.
	 * @param sequenceSubType
	 *            sequence sub type.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 */
	private Long getNextSequenceNumber(final SequenceTypeConstant sequenceType, final String sequenceSubType) throws BusinessException {
		Long result = null;
		final EntityLockable lockable = SequancialKeyLockableFactory.getInstance().getSynchronizableEntity(SequenceGenerator.class, sequenceType,
				sequenceSubType);

		synchronized (lockable) {
			SequenceGenerator sequenceGenerator = this.sequenceGeneratorDao.findSequentialGeneratorBySequenceTypeAndSequenceSubType(sequenceType,
					sequenceSubType);
			if (sequenceGenerator == null) {
				result = Long.valueOf(1);
				sequenceGenerator = new SequenceGenerator();
				sequenceGenerator.setSequenceType(sequenceType);
				sequenceGenerator.setSequenceSubType(sequenceSubType);
				sequenceGenerator.setCurrentSequence(new Long(1));
				sequenceGenerator.setNextSequence(new Long(2));
			} else {
				result = sequenceGenerator.getNextSequence();
				sequenceGenerator.setCurrentSequence(result);
				sequenceGenerator.setNextSequence(result + 1);
			}
			this.sequenceGeneratorDao.persist(sequenceGenerator);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNextRegistrationNumber(final String sequenceSubType) throws BusinessException {
		final String subType = "R" + sequenceSubType;
		final Long sequenceNumber = this.getNextSequenceNumber(SequenceTypeConstant.REGISTRATION_NUMBER, subType);
		final String formatedNumber = this.formateNumber(sequenceNumber);
		return subType + formatedNumber;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNextAdmissionNumber(final String sequenceSubType) throws BusinessException {
		final String subType = "A" + sequenceSubType;
		final Long sequenceNumber = this.getNextSequenceNumber(SequenceTypeConstant.ADMISSION_NUMBER, subType);
		final String formatedNumber = this.formateNumber(sequenceNumber);
		return subType + formatedNumber;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNextEmployeeNumber(final String sequenceSubType) throws BusinessException {
		final String subType = "EMP" + sequenceSubType;
		final Long sequenceNumber = this.getNextSequenceNumber(SequenceTypeConstant.EMPLOYEE_NUMBER, subType);
		final String formatedNumber = this.formateNumber(sequenceNumber);
		return subType + formatedNumber;
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public String getNextTransactionNumberForFeeDeposit() throws BusinessException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		String sequenceSubType = format.format(DateUtil.getSystemDate());
		String subType = "TXFP" + sequenceSubType;
		final Long sequenceNumber = this.getNextSequenceNumber(SequenceTypeConstant.FEE_DEPOSIT, subType);

		format = new SimpleDateFormat("ddMMyyyy");
		sequenceSubType = format.format(DateUtil.getSystemDate());
		subType = "TXFP" + sequenceSubType;
		return subType + sequenceNumber;
	}

	/**
	 * Format number.
	 * 
	 * @param value
	 *            number to be formatted.
	 * @return
	 */
	private String formateNumber(final Long value) {
		final NumberFormat format = new DecimalFormat("0000000");

		return format.format(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNextTransactionNumberForFeeRefund() throws BusinessException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		String sequenceSubType = format.format(DateUtil.getSystemDate());
		String subType = "TXFR" + sequenceSubType;

		final Long sequenceNumber = this.getNextSequenceNumber(SequenceTypeConstant.FEE_REFUND, subType);

		format = new SimpleDateFormat("ddMMyyyy");
		sequenceSubType = format.format(DateUtil.getSystemDate());
		subType = "TXFR" + sequenceSubType;
		return subType + sequenceNumber;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNextTransactionNumberForFeeDeduct() throws BusinessException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		String sequenceSubType = format.format(DateUtil.getSystemDate());
		String subType = "TXFD" + sequenceSubType;

		final Long sequenceNumber = this.getNextSequenceNumber(SequenceTypeConstant.FEE_DEDUCT, subType);

		format = new SimpleDateFormat("ddMMyyyy");
		sequenceSubType = format.format(DateUtil.getSystemDate());
		subType = "TXFD" + sequenceSubType;
		return subType + sequenceNumber;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNextPurchaseOrderNumber() throws BusinessException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		String sequenceSubType = format.format(DateUtil.getSystemDate());
		String subType = "PORDER-" + sequenceSubType + "-";

		final Long sequenceNumber = this.getNextSequenceNumber(SequenceTypeConstant.PURCHASE_ORDER_NUMBER, subType);

		format = new SimpleDateFormat("ddMMyyyy");
		sequenceSubType = format.format(DateUtil.getSystemDate());
		subType = "PORDER-" + sequenceSubType + "-";
		return subType + sequenceNumber;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNextTransactionNumberForStudentPocketMoney(final StudentPocketMoneyTransactionTypeConstant studentPocketMoneyTransactionTypeConstant)
			throws BusinessException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		String sequenceSubType = format.format(DateUtil.getSystemDate());
		String subType = null;
		if (StudentPocketMoneyTransactionTypeConstant.DEPOSIT.equals(studentPocketMoneyTransactionTypeConstant)) {
			subType = "TXPMD" + sequenceSubType;
		} else {
			subType = "TXPMW" + sequenceSubType;
		}

		final Long sequenceNumber = this.getNextSequenceNumber(SequenceTypeConstant.FEE_DEDUCT, subType);

		format = new SimpleDateFormat("ddMMyyyy");
		sequenceSubType = format.format(DateUtil.getSystemDate());
		if (StudentPocketMoneyTransactionTypeConstant.DEPOSIT.equals(studentPocketMoneyTransactionTypeConstant)) {
			subType = "TXPMD" + sequenceSubType;
		} else {
			subType = "TXPMW" + sequenceSubType;
		}
		return subType + sequenceNumber;

	}

	@Override
	public String getNextTransactionNumberForSalaryPaid() throws BusinessException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		String sequenceSubType = format.format(DateUtil.getSystemDate());
		String subType = "SAL" + sequenceSubType;
		final Long sequenceNumber = this.getNextSequenceNumber(SequenceTypeConstant.SALARY, subType);

		format = new SimpleDateFormat("ddMMyyyy");
		sequenceSubType = format.format(DateUtil.getSystemDate());
		subType = "SAL" + sequenceSubType;
		return subType + sequenceNumber;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNextTransactionNumberForBankAccountTransaction() throws BusinessException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		String sequenceSubType = format.format(DateUtil.getSystemDate());
		String subType = "TXFBA" + sequenceSubType;
		final Long sequenceNumber = this.getNextSequenceNumber(SequenceTypeConstant.BANK_ACCOUNT_TRANSACTION, subType);

		format = new SimpleDateFormat("ddMMyyyy");
		sequenceSubType = format.format(DateUtil.getSystemDate());
		subType = "TXFBA" + sequenceSubType;
		return subType + sequenceNumber;
	}

	@Override
	public String getNextTransactionNumberForExpense() throws BusinessException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		String sequenceSubType = format.format(DateUtil.getSystemDate());
		String subType = "EXP" + sequenceSubType;
		final Long sequenceNumber = this.getNextSequenceNumber(SequenceTypeConstant.BRANCH_EXPENSE, subType);

		format = new SimpleDateFormat("ddMMyyyy");
		sequenceSubType = format.format(DateUtil.getSystemDate());
		subType = "EXP" + sequenceSubType;
		return subType + sequenceNumber;
	}

	@Override
	public String getNextTransactionNumberForInvoice() throws BusinessException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		String sequenceSubType = format.format(DateUtil.getSystemDate());
		String subType = "INVO" + sequenceSubType;
		final Long sequenceNumber = this.getNextSequenceNumber(SequenceTypeConstant.BRANCH_INVOICE, subType);

		format = new SimpleDateFormat("ddMMyyyy");
		sequenceSubType = format.format(DateUtil.getSystemDate());
		subType = "INVO" + sequenceSubType;
		return subType + sequenceNumber;
	}

	@Override
	public String getNextTransactionNumberForInvestment() throws BusinessException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		String sequenceSubType = format.format(DateUtil.getSystemDate());
		String subType = "INVST" + sequenceSubType;
		final Long sequenceNumber = this.getNextSequenceNumber(SequenceTypeConstant.BRANCH_INVESTMENT, subType);

		format = new SimpleDateFormat("ddMMyyyy");
		sequenceSubType = format.format(DateUtil.getSystemDate());
		subType = "INVST" + sequenceSubType;
		return subType + sequenceNumber;
	}

	@Override
	public String getNextTransactionNumberForCreditAccountTransaction() throws BusinessException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		String sequenceSubType = format.format(DateUtil.getSystemDate());
		String subType = "TXFCA" + sequenceSubType;
		final Long sequenceNumber = this.getNextSequenceNumber(SequenceTypeConstant.CREDIT_ACCOUNT_TRANSACTION, subType);

		format = new SimpleDateFormat("ddMMyyyy");
		sequenceSubType = format.format(DateUtil.getSystemDate());
		subType = "TXFCA" + sequenceSubType;
		return subType + sequenceNumber;
	}

}
