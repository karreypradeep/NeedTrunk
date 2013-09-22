/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.StudentPocketMoneyDao;
import com.apeironsol.need.core.portal.messages.BusinessMessages;
import com.apeironsol.need.financial.model.StudentPocketMoneyTransaction;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.StudentPocketMoneyTransactionTypeConstant;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Service("studentPocketMoneyService")
@Transactional
public class StudentPocketMoneyServiceImpl implements StudentPocketMoneyService {

	@Resource
	private StudentPocketMoneyDao	studentPocketMoneyDao;

	@Resource
	SequenceGeneratorService		sequenceGeneratorService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentPocketMoneyTransaction> findStudentPocketMoneyTransactionByStudentSectionId(final Long studentSectionId) throws BusinessException {
		return this.studentPocketMoneyDao.findStudentPocketMoneyTransactionByStudentSectionId(studentSectionId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentPocketMoneyTransaction findStudentPocketMoneyTransactionIncomeById(final Long id) throws BusinessException {
		return this.studentPocketMoneyDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentPocketMoneyTransaction saveStudentPocketMoneyTransaction(final StudentPocketMoneyTransaction studentPocketMoneyTransaction)
			throws BusinessException, InvalidArgumentException {
		this.validateStudentPocketMoneyTransaction(studentPocketMoneyTransaction);
		if (studentPocketMoneyTransaction.getId() == null) {
			studentPocketMoneyTransaction.setTransactionNr(this.sequenceGeneratorService
					.getNextTransactionNumberForStudentPocketMoney(studentPocketMoneyTransaction.getStudentPocketMoneyTransactionTypeConstant()));
			studentPocketMoneyTransaction.setTransactionDate(DateUtil.getSystemDate());
		}
		return this.studentPocketMoneyDao.persist(studentPocketMoneyTransaction);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeStudentPocketMoneyTransaction(final StudentPocketMoneyTransaction studentPocketMoneyTransaction) throws BusinessException {
		this.studentPocketMoneyDao.remove(studentPocketMoneyTransaction);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentPocketMoneyTransaction> findAllStudentPocketMoneyTransactions() throws BusinessException {
		return this.studentPocketMoneyDao.findAll();
	}

	private void validateStudentPocketMoneyTransaction(final StudentPocketMoneyTransaction studentPocketMoneyTransaction) {
		if (studentPocketMoneyTransaction.getAmount() <= 0) {
			throw new BusinessException(BusinessMessages.getResourceBundleName(), BusinessMessages.MSG_AMOUNT_SHOULD_BE_GREATER_THAN_ZERO, null);

		} else if (StudentPocketMoneyTransactionTypeConstant.WITHDRAW.equals(studentPocketMoneyTransaction.getStudentPocketMoneyTransactionTypeConstant())) {
			Collection<StudentPocketMoneyTransaction> studentPocketMoneyTransactionsByStudentSection = this
					.findStudentPocketMoneyTransactionByStudentSectionId(studentPocketMoneyTransaction.getStudentSection().getId());
			double balancePocketMoney = this.calculateBalancePocketMoney(studentPocketMoneyTransactionsByStudentSection);
			if (studentPocketMoneyTransaction.getAmount() > balancePocketMoney) {
				throw new BusinessException(BusinessMessages.getResourceBundleName(), BusinessMessages.MSG_INSUFFICIENT_POCKET_MONEY_TO_WITH_DRAW, null);
			}
		}
	}

	public double calculateBalancePocketMoney(final Collection<StudentPocketMoneyTransaction> studentPocketMoneyTransactionsByStudentSection) {
		double depositedMoney = 0.0, withDrawnMoney = 0.0;
		for (StudentPocketMoneyTransaction pocketMoneyTrans : studentPocketMoneyTransactionsByStudentSection) {
			if (StudentPocketMoneyTransactionTypeConstant.DEPOSIT.equals(pocketMoneyTrans.getStudentPocketMoneyTransactionTypeConstant())) {
				depositedMoney += pocketMoneyTrans.getAmount();
			} else {
				withDrawnMoney += pocketMoneyTrans.getAmount();
			}
		}
		return depositedMoney - withDrawnMoney;
	}
}
