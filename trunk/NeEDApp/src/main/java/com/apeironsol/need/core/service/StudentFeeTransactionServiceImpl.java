/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.financial.dao.StudentFeeTransactionDao;
import com.apeironsol.need.financial.model.StudentFeeTransaction;
import com.apeironsol.need.util.constants.StudentFeeTransactionStatusConstant;
import com.apeironsol.need.util.searchcriteria.FeeCollectedSearchCriteria;
import com.apeironsol.need.util.searchcriteria.StudentFeeTransactionSearchCriteria;

/**
 * Service layer implementation for StudentSection DAO implementation.
 * 
 * @author Pradeep
 * 
 */
@Service("studentFeeTransactionService")
@Transactional(rollbackFor = Exception.class)
public class StudentFeeTransactionServiceImpl implements StudentFeeTransactionService {

	@Resource
	StudentFeeTransactionDao	studentFeeTransactionDao;

	@Override
	public StudentFeeTransaction saveStudentFeeTransaction(final StudentFeeTransaction studentFeeTransaction) {
		return this.studentFeeTransactionDao.persist(studentFeeTransaction);
	}

	@Override
	public void removeStudentFeeTransaction(final StudentFeeTransaction studentFeeTransaction) {
		this.studentFeeTransactionDao.remove(studentFeeTransaction);

	}

	@Override
	public StudentFeeTransaction findStudentFeeTransactionsByTransactionNr(final String transactionNr) {
		return this.studentFeeTransactionDao.findStudentFeeTransactionsByTransactionNr(transactionNr);
	}

	@Override
	public Collection<StudentFeeTransaction> findStudentFeeTransactionsByBranchIdAndTransactionStatusBetweenDates(final Long branchId,
			final EnumSet<StudentFeeTransactionStatusConstant> studentFeeTransactionStatusConstants, final Date fromDate, final Date toDate) {
		return this.studentFeeTransactionDao.findStudentFeeTransactionsByBranchIdAndTransactionStatusBetweenDates(branchId,
				studentFeeTransactionStatusConstants, fromDate, toDate);
	}

	@Override
	public Collection<StudentFeeTransaction> findStudentFeeTransactionsBySearchCriteria(
			final StudentFeeTransactionSearchCriteria studentFeeTransactionSearchCriteria) throws BusinessException {
		return this.studentFeeTransactionDao.findStudentFeeTransactionsBySearchCriteria(studentFeeTransactionSearchCriteria);
	}

	@Override
	public Collection<StudentFeeTransaction> findFeesCollectedBySearchCriteria(final FeeCollectedSearchCriteria feeCollectedSearchCriteria)
			throws BusinessException {
		return this.studentFeeTransactionDao.findFeesCollectedBySearchCriteria(feeCollectedSearchCriteria);
	}
}
