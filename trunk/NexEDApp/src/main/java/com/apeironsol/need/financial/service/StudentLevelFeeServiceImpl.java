/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.portal.messages.BusinessMessages;
import com.apeironsol.need.financial.dao.StudentFeeDao;
import com.apeironsol.need.financial.dao.StudentFeeTransactionDetailsDao;
import com.apeironsol.need.financial.dao.StudentLevelFeeCatalogDao;
import com.apeironsol.need.financial.dao.StudentLevelFeeDao;
import com.apeironsol.need.financial.model.StudentFee;
import com.apeironsol.need.financial.model.StudentFeeTransactionDetails;
import com.apeironsol.need.financial.model.StudentLevelFee;
import com.apeironsol.need.financial.model.StudentLevelFeeCatalog;
import com.apeironsol.need.util.constants.FeeClassificationLevelConstant;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * Service interface for branch level fee service implementation.
 * 
 * @author Pradeep
 * 
 */
@Service
@Transactional
public class StudentLevelFeeServiceImpl implements StudentLevelFeeService {

	@Resource
	private StudentLevelFeeDao				studentLevelFeeDao;

	@Resource
	private StudentLevelFeeCatalogDao		studentLevelFeeCatalogDao;

	@Resource
	private StudentFeeDao					studentFeeDao;

	@Resource
	private StudentFeeTransactionDetailsDao	studentFeeTransactionDetailsDao;

	@Resource
	private StudentFinancialService			studentFinancialService;

	@Override
	public StudentLevelFee saveStudentLevelFee(final StudentLevelFee studentLevelFee) throws BusinessException, SystemException {

		boolean newAction = false;
		if (studentLevelFee.getId() == null) {
			newAction = true;
		} else {
			validateAction(studentLevelFee);
		}

		studentLevelFee.validate();

		if (studentLevelFee.getId() != null) {

			this.studentLevelFeeCatalogDao.removeStudentLevelFeeCatalogsByStudentLevelFeeId(studentLevelFee.getId());
		}

		StudentLevelFee studentLevelFeeLocal = this.studentLevelFeeDao.persist(studentLevelFee);

		if (newAction) {

			StudentFee studentFee = new StudentFee();

			studentFee.setFeeClassificationLevel(FeeClassificationLevelConstant.STUDENT_LEVEL);
			studentFee.setStudentAcademicYear(studentLevelFeeLocal.getStudentAcademicYear());
			studentFee.setStudentLevelFee(studentLevelFeeLocal);
			this.studentFeeDao.persist(studentFee);
		}
		this.studentFinancialService.processStudentAcademicYearFeeSummary(studentLevelFeeLocal.getStudentAcademicYear().getId());
		return studentLevelFeeLocal;
	}

	@Override
	public void removeStudentLevelFee(final StudentLevelFee studentLevelFee) throws BusinessException, SystemException {
		
		validateAction(studentLevelFee);
		
		
		StudentFee studentFee = this.studentFeeDao.findStudentFeeByStudentAcadmicYearIdAndStudentFeeId(studentLevelFee.getStudentAcademicYear().getId(),
				studentLevelFee.getId());
		
		this.studentFeeDao.remove(studentFee);
		
		this.studentFinancialService.processStudentAcademicYearFeeSummary(studentLevelFee.getStudentAcademicYear().getId());

		this.studentLevelFeeDao.remove(studentLevelFee);

	}

	private void validateAction(StudentLevelFee studentLevelFee) {
		
		StudentFee studentFee = this.studentFeeDao.findStudentFeeByStudentAcadmicYearIdAndStudentFeeId(studentLevelFee.getStudentAcademicYear().getId(),
				studentLevelFee.getId());

		Collection<StudentFeeTransactionDetails> studentFeeTransactionDetails = this.studentFeeTransactionDetailsDao
				.findStudentFeeTransactionDetailsByStudentFeeId(studentFee.getId());
		if (studentFeeTransactionDetails != null && !studentFeeTransactionDetails.isEmpty()) {
			throw new BusinessException(BusinessMessages.getResourceBundleName(), BusinessMessages.MSG_CANNOT_DELETE_STUDENT_LEVEL_FEE_AS_TRANSACTION_FOUND,
					null);
		}
		
	}

	@Override
	public Collection<StudentLevelFee> findStudentLevelFeesByStudentAcademicYearId(final Long studentAcademicYearId) throws BusinessException, SystemException {
		return this.studentLevelFeeDao.findStudentLevelFeesByStudentAcademicYearId(studentAcademicYearId);
	}

	@Override
	public Collection<StudentLevelFeeCatalog> findStudentLevelFeeCatalogsByStudentLevelFeeId(final Long studentLevelFeeId) {
		return this.studentLevelFeeCatalogDao.findStudentLevelFeeCatalogsByStudentLevelFeeId(studentLevelFeeId);
	}

}
