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

import com.apeironsol.need.core.dao.StudentAcademicYearFeeSummaryDao;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.core.model.StudentAcademicYearFeeSummary;
import com.apeironsol.need.financial.service.StudentFinancialService;
import com.apeironsol.need.util.dataobject.StudentFinancialAcademicYearDO;

/**
 * Service implementation for student academic year.
 * 
 * @author Pradeep
 * 
 */
@Service("studentAcademicYearFeeSummaryService")
@Transactional
public class StudentAcademicYearFeeSummaryServiceImp implements StudentAcademicYearFeeSummaryService {

	@Resource
	StudentAcademicYearFeeSummaryDao	studentAcademicYearFeeSummaryDao;

	@Resource
	StudentAcademicYearService			studentAcademicYearService;

	@Resource
	StudentFinancialService				studentFinancialService;

	@Override
	public StudentAcademicYearFeeSummary findStudentAcademicYearFeeSummaryById(final Long id) {
		return this.studentAcademicYearFeeSummaryDao.findById(id);
	}

	@Override
	public StudentAcademicYearFeeSummary findStudentAcademicYearFeeSummaryByStudentAcademicYearId(final Long studentAcademicYearId) {
		return this.studentAcademicYearFeeSummaryDao.findStudentAcademicYearFeeSummaryByStudentAcademicYearId(studentAcademicYearId);
	}

	@Override
	public StudentAcademicYearFeeSummary saveStudentAcademicYearFeeSummary(final StudentAcademicYearFeeSummary studentAcademicYearFeeSummary) {
		return this.studentAcademicYearFeeSummaryDao.persist(studentAcademicYearFeeSummary);
	}

	@Override
	public StudentAcademicYearFeeSummary createStudentAcademicYearFeeSummaryForStudentAcademicYearId(final Long studentAcademicYearId) {
		StudentAcademicYear studentAcademicYear = this.studentAcademicYearService.findStudentAcademicYearById(studentAcademicYearId);
		StudentFinancialAcademicYearDO studentFinancialAcademicYearDO = new StudentFinancialAcademicYearDO();
		studentFinancialAcademicYearDO.setStudentFinancialDOs(this.studentFinancialService.getStudentFinancialDetailsByStudentIdAndAcadmicYearIdForDueDate(
				studentAcademicYear.getStudent().getId(), studentAcademicYear.getAcademicYear().getId(), null));
		studentFinancialAcademicYearDO.calculateStudentFeeForAcademicYear();
		StudentAcademicYearFeeSummary studentAcademicYearFeeSummary = new StudentAcademicYearFeeSummary();
		studentAcademicYearFeeSummary.setStudentAcademicYear(studentAcademicYear);
		studentAcademicYearFeeSummary.setTotalFeePayable(studentFinancialAcademicYearDO.getFee());
		studentAcademicYearFeeSummary.setTotalFeePaid(studentFinancialAcademicYearDO.getTotalFeePaymentAmount());
		studentAcademicYearFeeSummary.setTotalFeeWaived(studentFinancialAcademicYearDO.getTotalFeeDeductedAmount());
		studentAcademicYearFeeSummary.setTotalFeeWaivedRequestAmount(studentFinancialAcademicYearDO.getTotalFeeDeductionRequestAmount());
		studentAcademicYearFeeSummary.setTotalFeeRefunded(studentFinancialAcademicYearDO.getTotalFeeRefundAmount());
		studentAcademicYearFeeSummary.setTotalFeeRefundPendingAmount(studentFinancialAcademicYearDO.getTotalFeeRefundPendingAmount());
		studentAcademicYearFeeSummary.setTotalFeeRefundRequestAmount(studentFinancialAcademicYearDO.getTotalFeeRefundRequestAmount());
		studentAcademicYearFeeSummary.setTotalFeePaymentPendingAmount(studentFinancialAcademicYearDO.getTotalFeePaymentPendingAmount());
		studentAcademicYearFeeSummary = this.saveStudentAcademicYearFeeSummary(studentAcademicYearFeeSummary);
		return studentAcademicYearFeeSummary;
	}

	@Override
	public StudentAcademicYearFeeSummary findOrCreateStudentAcademicYearFeeSummaryByStudentAcademicYearId(final Long studentAcademicYearId) {
		StudentAcademicYearFeeSummary studentAcademicYearFeeSummary = this.studentAcademicYearFeeSummaryDao
				.findStudentAcademicYearFeeSummaryByStudentAcademicYearId(studentAcademicYearId);
		if (studentAcademicYearFeeSummary == null) {
			studentAcademicYearFeeSummary = this.createStudentAcademicYearFeeSummaryForStudentAcademicYearId(studentAcademicYearId);
		}
		return studentAcademicYearFeeSummary;
	}

	@Override
	public StudentAcademicYearFeeSummary updateStudentAcademicYearFeeSummaryForStudentAcademicYearId(final Long studentAcademicYearId) {
		StudentAcademicYearFeeSummary studentAcademicYearFeeSummary = this.studentAcademicYearFeeSummaryDao
				.findStudentAcademicYearFeeSummaryByStudentAcademicYearId(studentAcademicYearId);
		if (studentAcademicYearFeeSummary == null) {
			studentAcademicYearFeeSummary = this.createStudentAcademicYearFeeSummaryForStudentAcademicYearId(studentAcademicYearId);
		} else {
			StudentAcademicYear studentAcademicYear = this.studentAcademicYearService.findStudentAcademicYearById(studentAcademicYearId);
			StudentFinancialAcademicYearDO studentFinancialAcademicYearDO = new StudentFinancialAcademicYearDO();
			studentFinancialAcademicYearDO.setStudentFinancialDOs(this.studentFinancialService.getStudentFinancialDetailsByStudentIdAndAcadmicYearIdForDueDate(
					studentAcademicYear.getStudent().getId(), studentAcademicYear.getAcademicYear().getId(), null));
			studentFinancialAcademicYearDO.calculateStudentFeeForAcademicYear();
			studentAcademicYearFeeSummary.setStudentAcademicYear(studentAcademicYear);
			studentAcademicYearFeeSummary.setTotalFeePayable(studentFinancialAcademicYearDO.getFee());
			studentAcademicYearFeeSummary.setTotalFeePaid(studentFinancialAcademicYearDO.getTotalFeePaymentAmount());
			studentAcademicYearFeeSummary.setTotalFeeWaived(studentFinancialAcademicYearDO.getTotalFeeDeductedAmount());
			studentAcademicYearFeeSummary.setTotalFeeWaivedRequestAmount(studentFinancialAcademicYearDO.getTotalFeeDeductionRequestAmount());
			studentAcademicYearFeeSummary.setTotalFeeRefunded(studentFinancialAcademicYearDO.getTotalFeeRefundAmount());
			studentAcademicYearFeeSummary.setTotalFeeRefundPendingAmount(studentFinancialAcademicYearDO.getTotalFeeRefundPendingAmount());
			studentAcademicYearFeeSummary.setTotalFeeRefundRequestAmount(studentFinancialAcademicYearDO.getTotalFeeRefundRequestAmount());
			studentAcademicYearFeeSummary.setTotalFeePaymentPendingAmount(studentFinancialAcademicYearDO.getTotalFeePaymentPendingAmount());
			studentAcademicYearFeeSummary = this.saveStudentAcademicYearFeeSummary(studentAcademicYearFeeSummary);
		}
		return studentAcademicYearFeeSummary;
	}

	@Override
	public Collection<StudentAcademicYearFeeSummary> findStudentAcademicYearFeeSummaryByStudentAcademicYearIds(final Collection<Long> studentAcademicYearIds) {
		return this.studentAcademicYearFeeSummaryDao.findStudentAcademicYearFeeSummaryByStudentAcademicYearIds(studentAcademicYearIds);
	}

}
