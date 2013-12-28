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

import com.apeironsol.need.financial.dao.StudentFeeTransactionDetailsDao;
import com.apeironsol.need.financial.model.StudentFee;
import com.apeironsol.need.financial.model.StudentFeeTransactionDetails;

/**
 * Service layer implementation for StudentSection DAO implementation.
 * 
 * @author Pradeep
 * 
 */
@Service("studentFeeTransactionDetailsService")
@Transactional(rollbackFor = Exception.class)
public class StudentFeeTransactionDetailsServiceImpl implements StudentFeeTransactionDetailsService {

	@Resource
	StudentFeeTransactionDetailsDao	studentFeeTransactionDetailsDao;

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByKlassFeePaymentId(final Long klassFeePaymentId) {
		return this.studentFeeTransactionDetailsDao.findStudentFeeTransactionDetailsByKlassFeePaymentId(klassFeePaymentId);
	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentIdAndAcademicYearId(final Long studentId, final Long academicYearId) {
		return this.studentFeeTransactionDetailsDao.findStudentFeeTransactionDetailsByStudentIdAndAcademicYearId(studentId, academicYearId);
	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentFeeId(final Long studentFeeId) {
		return this.studentFeeTransactionDetailsDao.findStudentFeeTransactionDetailsByStudentFeeId(studentFeeId);
	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByKlassFeePaymentIdAndStudentFeeId(final Long klassFeePaymentId,
			final Long studentFeeId) {
		return this.studentFeeTransactionDetailsDao.findStudentFeeTransactionDetailsByKlassFeePaymentIdAndStudentFeeId(klassFeePaymentId, studentFeeId);
	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentFeeTransactionId(final Long studentFeeTransactionId) {
		return this.studentFeeTransactionDetailsDao.findStudentFeeTransactionDetailsByStudentFeeTransactionId(studentFeeTransactionId);
	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentFees(final Collection<StudentFee> studentFees) {
		return this.studentFeeTransactionDetailsDao.findStudentFeeTransactionDetailsByStudentFees(studentFees);
	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentAdditionalFeeId(final Long studentAdditionalFeeId) {
		return this.studentFeeTransactionDetailsDao.findStudentFeeTransactionDetailsByStudentAdditionalFeeId(studentAdditionalFeeId);
	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentLevelFeeCatalogIdAndStudentFeeId(
			final Long studentLevelFeeCatalogId, final Long studentFeeId) {
		return this.studentFeeTransactionDetailsDao.findStudentFeeTransactionDetailsByStudentLevelFeeCatalogIdAndStudentFeeId(studentLevelFeeCatalogId,
				studentFeeId);
	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByBranchLevelFeeCatalogtIdAndStudentFeeId(final Long branchLevelFeeId,
			final Long studentFeeId) {
		return this.studentFeeTransactionDetailsDao.findStudentFeeTransactionDetailsByBranchLevelFeeCatalogtIdAndStudentFeeId(branchLevelFeeId, studentFeeId);
	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByPickUpPointFeeCatalogIdAndStudentFeeId(
			final Long pickUpPointFeeCatalogId, final Long studentFeeId) {
		return this.studentFeeTransactionDetailsDao.findStudentFeeTransactionDetailsByPickUpPointFeeCatalogIdAndStudentFeeId(pickUpPointFeeCatalogId,
				studentFeeId);
	}

	@Override
	public StudentFeeTransactionDetails saveStudentFeeTransactionDetails(final StudentFeeTransactionDetails studentFeeTransactionDetails) {
		return this.studentFeeTransactionDetailsDao.persist(studentFeeTransactionDetails);
	}

	@Override
	public void removeStudentFeeTransactionDetails(final StudentFeeTransactionDetails studentFeeTransactionDetails) {
		this.studentFeeTransactionDetailsDao.remove(studentFeeTransactionDetails);

	}

}
