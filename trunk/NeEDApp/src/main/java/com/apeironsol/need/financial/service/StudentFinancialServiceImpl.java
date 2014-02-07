/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;
import com.apeironsol.need.core.dao.StudentAcademicYearDao;
import com.apeironsol.need.core.dao.StudentDao;
import com.apeironsol.need.core.dao.StudentSectionDao;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.BranchRule;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.core.model.StudentAcademicYearFeeSummary;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.portal.messages.BusinessMessages;
import com.apeironsol.need.core.service.BranchRuleService;
import com.apeironsol.need.core.service.BranchService;
import com.apeironsol.need.core.service.KlassService;
import com.apeironsol.need.core.service.SequenceGeneratorService;
import com.apeironsol.need.core.service.StudentAcademicYearFeeSummaryService;
import com.apeironsol.need.core.service.StudentService;
import com.apeironsol.need.financial.dao.BranchLevelFeeCatalogDao;
import com.apeironsol.need.financial.dao.KlassLevelFeeCatalogDao;
import com.apeironsol.need.financial.dao.StudentFeeDao;
import com.apeironsol.need.financial.dao.StudentFeeTransactionDao;
import com.apeironsol.need.financial.dao.StudentFeeTransactionDetailsDao;
import com.apeironsol.need.financial.dao.StudentLevelFeeCatalogDao;
import com.apeironsol.need.financial.model.BranchLevelFee;
import com.apeironsol.need.financial.model.BranchLevelFeeCatalog;
import com.apeironsol.need.financial.model.KlassLevelFee;
import com.apeironsol.need.financial.model.KlassLevelFeeCatalog;
import com.apeironsol.need.financial.model.StudentFee;
import com.apeironsol.need.financial.model.StudentFeeTransaction;
import com.apeironsol.need.financial.model.StudentFeeTransactionDetails;
import com.apeironsol.need.financial.model.StudentLevelFee;
import com.apeironsol.need.financial.model.StudentLevelFeeCatalog;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.notifications.model.BranchNotification;
import com.apeironsol.need.notifications.service.BatchLogService;
import com.apeironsol.need.notifications.service.BranchNotificationService;
import com.apeironsol.need.notifications.service.NotificationService;
import com.apeironsol.need.transportation.dao.PickUpPointFeeCatalogDao;
import com.apeironsol.need.transportation.dao.PickUpPointFeeDao;
import com.apeironsol.need.transportation.model.PickUpPointFee;
import com.apeironsol.need.transportation.model.PickUpPointFeeCatalog;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.BatchStatusConstant;
import com.apeironsol.need.util.constants.FeeClassificationLevelConstant;
import com.apeironsol.need.util.constants.FeeTypeConstant;
import com.apeironsol.need.util.constants.NotificationLevelConstant;
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;
import com.apeironsol.need.util.constants.NotificationTypeConstant;
import com.apeironsol.need.util.constants.PaymentMethodConstant;
import com.apeironsol.need.util.constants.StudentFeeTransactionStatusConstant;
import com.apeironsol.need.util.constants.StudentFeeTransactionTypeConstant;
import com.apeironsol.need.util.dataobject.StudentFeeDetailsDO;
import com.apeironsol.need.util.dataobject.StudentFeeTransactionDO;
import com.apeironsol.need.util.dataobject.StudentFeeTransactionDetailsDO;
import com.apeironsol.need.util.dataobject.StudentFinancialAcademicYearDO;
import com.apeironsol.need.util.dataobject.StudentFinancialDO;
import com.apeironsol.need.util.searchcriteria.FeeCollectedSearchCriteria;
import com.apeironsol.need.util.searchcriteria.FeeDueSearchCriteria;
import com.apeironsol.need.util.searchcriteria.StudentFeeTransactionSearchCriteria;
import com.apeironsol.need.util.searchcriteria.StudentSearchCriteria;

/**
 * Service implementation for student financial details.
 * 
 * @author pradeep
 * @author pradeep
 * 
 */
@Service("studentFinancialService")
@Transactional(rollbackFor = Exception.class)
public class StudentFinancialServiceImpl implements StudentFinancialService {

	@Resource
	StudentFeeDao							studentFeeDao;

	@Resource
	BranchLevelFeeCatalogDao				branchLevelFeeCatalogDao;

	@Resource
	KlassLevelFeeCatalogDao					klassLevelFeeCatalogDao;

	@Resource
	StudentLevelFeeCatalogDao				studentLevelFeeCatalogDao;

	@Resource
	StudentFeeTransactionDao				studentFeeTransactionDao;

	@Resource
	StudentFeeTransactionDetailsDao			studentFeeTransactionDetailsDao;

	@Resource
	SequenceGeneratorService				sequenceGeneratorService;

	@Resource
	StudentAcademicYearDao					studentAcademicYearDao;

	@Resource
	StudentDao								studentDao;

	@Resource
	StudentService							studentService;

	@Resource
	KlassService							klassService;

	@Resource
	BranchNotificationService				branchNotificationService;

	@Resource
	NotificationService						notificationService;

	@Resource
	BatchLogService							batchLogService;

	@Resource
	BranchService							branchService;

	@Resource
	BranchRuleService						branchRuleService;

	@Resource
	PickUpPointFeeDao						pickUpPointFeeDao;

	@Resource
	PickUpPointFeeCatalogDao				pickUpPointFeeCatalogDao;

	@Resource
	StudentAcademicYearFeeSummaryService	studentAcademicYearFeeSummaryService;

	@Resource
	private BranchFinancialService			branchFinancialService;

	@Resource
	private StudentSectionDao				studentSectionDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentFeeTransaction approveDeduction(final Long studentId, final StudentFeeTransaction studentFeeTransaction,
			final Collection<StudentFeeDetailsDO> studentFeeDetailsDOs) throws BusinessException, SystemException {
		return this.processDeductionForRequestOrApproval(studentId, studentFeeTransaction, studentFeeDetailsDOs,
				StudentFeeTransactionStatusConstant.DEDUCTION_PROCESSED);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentFeeTransaction approveRefund(final Long studentId, final StudentFeeTransaction studentFeeTransaction,
			final Collection<StudentFeeDetailsDO> studentFeeDetailsDOs) throws BusinessException, SystemException {
		return this.processRefundForRequestOrApproval(studentId, studentFeeTransaction, studentFeeDetailsDOs,
				StudentFeeTransactionStatusConstant.REFUND_PENDING);
	}

	private StudentFeeDetailsDO computeBranchLevelFee(final StudentFee studentFee, final BranchLevelFeeCatalog branchLevelFeeCatalog) {

		final Collection<StudentFeeTransactionDetails> studentFeeTransactionDetailsCollection = this.studentFeeTransactionDetailsDao
				.findStudentFeeTransactionDetailsByBranchLevelFeeCatalogtIdAndStudentFeeId(branchLevelFeeCatalog.getId(), studentFee.getId());

		return this.computeStudentFeeTransactionDetails(studentFeeTransactionDetailsCollection, FeeClassificationLevelConstant.BRANCH_LEVEL, studentFee,
				branchLevelFeeCatalog, null, null, null);
	}

	/**
	 * 
	 * @param studentFeeTransactionDetailsCollection
	 * @param studentAdditionalFee
	 * @return
	 */
	private StudentFeeDetailsDO computeKlassLevelFee(final StudentFee studentFee, final KlassLevelFeeCatalog klassLevelFeeCatalog) {

		final Collection<StudentFeeTransactionDetails> studentFeeTransactionDetailsCollection = this.studentFeeTransactionDetailsDao
				.findStudentFeeTransactionDetailsByKlassFeePaymentIdAndStudentFeeId(klassLevelFeeCatalog.getId(), studentFee.getId());

		return this.computeStudentFeeTransactionDetails(studentFeeTransactionDetailsCollection, FeeClassificationLevelConstant.KLASS_LEVEL, studentFee, null,
				klassLevelFeeCatalog, null, null);
	}

	/**
	 * 
	 * @param studentFee
	 * @param studentLevelFeeCatalog
	 * @return
	 */
	private StudentFeeDetailsDO computePickUpPointFee(final StudentFee studentFee, final PickUpPointFeeCatalog pickUpPointFeeCatalog) {

		final Collection<StudentFeeTransactionDetails> studentFeeTransactionDetailsCollection = this.studentFeeTransactionDetailsDao
				.findStudentFeeTransactionDetailsByPickUpPointFeeCatalogIdAndStudentFeeId(pickUpPointFeeCatalog.getId(), studentFee.getId());

		return this.computeStudentFeeTransactionDetails(studentFeeTransactionDetailsCollection, FeeClassificationLevelConstant.TRANSPORTATION_LEVEL,
				studentFee, null, null, null, pickUpPointFeeCatalog);
	}

	/**
	 * 
	 * @param studentFeeTransactionDetailsCollection
	 * @param studentFeeType
	 * @param studentFee
	 * @param klassLevelFeeCatalog
	 * @param studentAdditionalFee
	 * @return
	 */
	private StudentFeeDetailsDO computeStudentFeeTransactionDetails(final Collection<StudentFeeTransactionDetails> studentFeeTransactionDetailsCollection,
			final FeeClassificationLevelConstant feeClassificationLevel, final StudentFee studentFee, final BranchLevelFeeCatalog branchLevelFeeCatalog,
			final KlassLevelFeeCatalog klassLevelFeeCatalog, final StudentLevelFeeCatalog studentLevelFeeCatalog,
			final PickUpPointFeeCatalog pickUpPointFeeCatalog) {

		final StudentFeeDetailsDO studentFeeDetailsDO = new StudentFeeDetailsDO();

		double totalPaymentAmountPerFeePayment = 0;

		double totalPaymentPendingAmountPerFeePayment = 0;

		double totalRefundAmountPerFeePayment = 0;

		double totalRefundRequestAmountPerFeePayment = 0;

		double totalRefundPendingAmountPerFeePayment = 0;

		double totalDeductionAmountPerFeePayment = 0;

		double totalDeductionRequestAmountPerFeePayment = 0;

		for (final StudentFeeTransactionDetails studentFeeTransactionDetails : studentFeeTransactionDetailsCollection) {

			final StudentFeeTransactionTypeConstant studentFeeTransactionType = studentFeeTransactionDetails.getStudentFeeTransaction()
					.getStudentFeeTransactionType();

			final StudentFeeTransactionStatusConstant studentFeeTransactionStatus = studentFeeTransactionDetails.getStudentFeeTransaction()
					.getStudentFeeTransactionStatus();

			// Payment processed
			if (StudentFeeTransactionTypeConstant.PAYMENT.equals(studentFeeTransactionType)
					&& StudentFeeTransactionStatusConstant.PAYMENT_PROCESSED.equals(studentFeeTransactionStatus)) {

				totalPaymentAmountPerFeePayment = totalPaymentAmountPerFeePayment + studentFeeTransactionDetails.getAmount();

				// Payment pending
			} else if (StudentFeeTransactionTypeConstant.PAYMENT.equals(studentFeeTransactionType)
					&& StudentFeeTransactionStatusConstant.PAYMENT_PENDING.equals(studentFeeTransactionStatus)) {

				totalPaymentPendingAmountPerFeePayment = totalPaymentPendingAmountPerFeePayment + studentFeeTransactionDetails.getAmount();

				// Refund
			} else if (StudentFeeTransactionTypeConstant.REFUND.equals(studentFeeTransactionType)
					&& StudentFeeTransactionStatusConstant.REFUND_PROCESSED.equals(studentFeeTransactionStatus)) {

				totalRefundAmountPerFeePayment = totalRefundAmountPerFeePayment + studentFeeTransactionDetails.getAmount();

				// Refund request
			} else if (StudentFeeTransactionTypeConstant.REFUND.equals(studentFeeTransactionType)
					&& StudentFeeTransactionStatusConstant.REFUND_REQUEST.equals(studentFeeTransactionStatus)) {

				totalRefundRequestAmountPerFeePayment = totalRefundRequestAmountPerFeePayment + studentFeeTransactionDetails.getAmount();

				// Refund pending
			} else if (StudentFeeTransactionTypeConstant.REFUND.equals(studentFeeTransactionType)
					&& StudentFeeTransactionStatusConstant.REFUND_PENDING.equals(studentFeeTransactionStatus)) {

				totalRefundPendingAmountPerFeePayment = totalRefundPendingAmountPerFeePayment + studentFeeTransactionDetails.getAmount();

				// Deduction processed
			} else if (StudentFeeTransactionTypeConstant.DEDUCT.equals(studentFeeTransactionType)
					&& StudentFeeTransactionStatusConstant.DEDUCTION_PROCESSED.equals(studentFeeTransactionStatus)) {

				totalDeductionAmountPerFeePayment = totalDeductionAmountPerFeePayment + studentFeeTransactionDetails.getAmount();

			} else if (StudentFeeTransactionTypeConstant.DEDUCT.equals(studentFeeTransactionType)
					&& StudentFeeTransactionStatusConstant.DEDUCTION_REQUEST.equals(studentFeeTransactionStatus)) {

				totalDeductionRequestAmountPerFeePayment = totalDeductionRequestAmountPerFeePayment + studentFeeTransactionDetails.getAmount();

			}

		}

		studentFeeDetailsDO.setStudentFee(studentFee);

		if (FeeClassificationLevelConstant.BRANCH_LEVEL.equals(feeClassificationLevel)) {

			studentFeeDetailsDO.setFeeName(branchLevelFeeCatalog.getBranchLevelFee().getBuildingBlock().getName());

			studentFeeDetailsDO.setBranchLevelFeeCatalog(branchLevelFeeCatalog);

			studentFeeDetailsDO.setFee(branchLevelFeeCatalog.getAmount());

			studentFeeDetailsDO.setDueDate(branchLevelFeeCatalog.getDueDate());

		}

		if (FeeClassificationLevelConstant.KLASS_LEVEL.equals(feeClassificationLevel)) {

			studentFeeDetailsDO.setFeeName(klassLevelFeeCatalog.getKlassFee().getBuildingBlock().getName());

			studentFeeDetailsDO.setKlassLevelFeeCatalog(klassLevelFeeCatalog);

			studentFeeDetailsDO.setFee(klassLevelFeeCatalog.getAmount());

			studentFeeDetailsDO.setDueDate(klassLevelFeeCatalog.getDueDate());

		}

		if (FeeClassificationLevelConstant.STUDENT_LEVEL.equals(feeClassificationLevel)) {

			studentFeeDetailsDO.setFeeName(studentLevelFeeCatalog.getStudentLevelFee().getBuildingBlock().getName());

			studentFeeDetailsDO.setStudentLevelFeeCatalog(studentLevelFeeCatalog);

			studentFeeDetailsDO.setFee(studentLevelFeeCatalog.getAmount());

			studentFeeDetailsDO.setDueDate(studentLevelFeeCatalog.getDueDate());

		}

		if (FeeClassificationLevelConstant.TRANSPORTATION_LEVEL.equals(feeClassificationLevel)) {

			studentFeeDetailsDO.setFeeName(pickUpPointFeeCatalog.getPickUpPointFee().getPickUpPoint().getName());

			studentFeeDetailsDO.setPickUpPointFeeCatalog(pickUpPointFeeCatalog);

			studentFeeDetailsDO.setFee(pickUpPointFeeCatalog.getAmount());

			studentFeeDetailsDO.setDueDate(pickUpPointFeeCatalog.getDueDate());

		}

		studentFeeDetailsDO.setStudentFeeTransactions(studentFeeTransactionDetailsCollection);

		studentFeeDetailsDO.setTotalFeePaymentAmount(totalPaymentAmountPerFeePayment);

		studentFeeDetailsDO.setTotalFeePaymentPendingAmount(totalPaymentPendingAmountPerFeePayment);

		studentFeeDetailsDO.setTotalFeeRefundAmount(totalRefundAmountPerFeePayment);

		studentFeeDetailsDO.setTotalFeeRefundRequestAmount(totalRefundRequestAmountPerFeePayment);

		studentFeeDetailsDO.setTotalFeeRefundPendingAmount(totalRefundPendingAmountPerFeePayment);

		studentFeeDetailsDO.setTotalFeeDeductedAmount(totalDeductionAmountPerFeePayment);

		studentFeeDetailsDO.setTotalFeeDeductionRequestAmount(totalDeductionRequestAmountPerFeePayment);

		return studentFeeDetailsDO;

	}

	/**
	 * This method compute the student financial details and
	 * 
	 * @param studentFinancialDOs
	 * @param studentId
	 * @param academicYearId
	 * @param dueDate
	 */
	private void computeStudentFianacialDetailsForBranchLevelFee(final Collection<StudentFinancialDO> studentFinancialDOs, final StudentFee studentFee,
			final Date dueDate) {

		final BranchLevelFee branchLevelFee = studentFee.getBranchLevelFee();

		final FeeTypeConstant feeType = branchLevelFee.getBuildingBlock().getFeeType();

		// Get class fee payment patterns
		Collection<BranchLevelFeeCatalog> branchLevelFeeCatalogs = null;
		if (dueDate == null) {
			branchLevelFeeCatalogs = this.branchLevelFeeCatalogDao.findBranchFeePaymentsByBranchLevelFeeId(branchLevelFee.getId());
		} else {
			branchLevelFeeCatalogs = this.branchLevelFeeCatalogDao.findBranchFeePaymentsByKlassFeeIdAndDueDate(branchLevelFee.getId(), dueDate);
		}

		double totalPaymentAmount = 0;

		double totalPaymentPendingAmount = 0;

		double totalRefundAmount = 0;

		double totalRefundRequestAmount = 0;

		double totalRefundPendingAmount = 0;

		double totalDeductedAmount = 0;

		double totalDeductionRequestAmount = 0;

		double totalFeeAmount = 0;

		final Collection<StudentFeeDetailsDO> studentFeeDetailsDOs = new ArrayList<StudentFeeDetailsDO>();

		for (final BranchLevelFeeCatalog branchLevelFeeCatalog : branchLevelFeeCatalogs) {

			final StudentFeeDetailsDO studentFeeDetailsDO = this.computeBranchLevelFee(studentFee, branchLevelFeeCatalog);

			// Calculate Totals
			// Totals for payments

			totalPaymentAmount = totalPaymentAmount + studentFeeDetailsDO.getTotalFeePaymentAmount();
			totalPaymentPendingAmount = totalPaymentPendingAmount + studentFeeDetailsDO.getTotalFeePaymentPendingAmount();

			// Totals for refunds.
			totalRefundAmount = totalRefundAmount + studentFeeDetailsDO.getTotalFeeRefundAmount();
			totalRefundRequestAmount = totalRefundRequestAmount + studentFeeDetailsDO.getTotalFeeRefundRequestAmount();
			totalRefundPendingAmount = totalRefundPendingAmount + studentFeeDetailsDO.getTotalFeeRefundPendingAmount();

			// Totals for deductions
			totalDeductedAmount = totalDeductedAmount + studentFeeDetailsDO.getTotalFeeDeductedAmount();
			totalDeductionRequestAmount = totalDeductionRequestAmount + studentFeeDetailsDO.getTotalFeeDeductionRequestAmount();

			// Total Fee
			totalFeeAmount += studentFeeDetailsDO.getFee();

			studentFeeDetailsDOs.add(studentFeeDetailsDO);

		}

		final StudentFinancialDO studentFinancialDO = this.createStudentFinancialDO(studentFee, feeType, totalPaymentAmount, totalPaymentPendingAmount,
				totalRefundAmount, totalRefundRequestAmount, totalRefundPendingAmount, totalDeductedAmount, totalDeductionRequestAmount, totalFeeAmount,
				studentFeeDetailsDOs);

		studentFinancialDOs.add(studentFinancialDO);

	}

	/**
	 * This method compute the student financial details and
	 * 
	 * @param studentFinancialDOs
	 * @param studentId
	 * @param academicYearId
	 * @param dueDate
	 */
	private void computeStudentFianacialDetailsForKlassLevelFee(final Collection<StudentFinancialDO> studentFinancialDOs, final StudentFee studentFee,
			final Date dueDate) {

		final KlassLevelFee klassLevelFee = studentFee.getKlassFee();

		final FeeTypeConstant feeType = klassLevelFee.getBuildingBlock().getFeeType();

		// Get class fee payment patterns
		Collection<KlassLevelFeeCatalog> klassLevelFeeCatalogs = null;
		if (dueDate == null) {
			klassLevelFeeCatalogs = this.klassLevelFeeCatalogDao.findKlassFeePaymentsByKlassFeeId(klassLevelFee.getId());
		} else {
			klassLevelFeeCatalogs = this.klassLevelFeeCatalogDao.findKlassFeePaymentsByKlassFeeIdAndDueDate(klassLevelFee.getId(), dueDate);
		}

		double totalPaymentAmount = 0;

		double totalPaymentPendingAmount = 0;

		double totalRefundAmount = 0;

		double totalRefundRequestAmount = 0;

		double totalRefundPendingAmount = 0;

		double totalDeductedAmount = 0;

		double totalDeductionRequestAmount = 0;

		double totalFeeAmount = 0;

		final Collection<StudentFeeDetailsDO> studentFeeDetailsDOs = new ArrayList<StudentFeeDetailsDO>();

		for (final KlassLevelFeeCatalog klassLevelFeeCatalog : klassLevelFeeCatalogs) {

			final StudentFeeDetailsDO studentFeeDetailsDO = this.computeKlassLevelFee(studentFee, klassLevelFeeCatalog);

			// Calculate Totals
			// Totals for payments
			totalPaymentAmount = totalPaymentAmount + studentFeeDetailsDO.getTotalFeePaymentAmount();
			totalPaymentPendingAmount = totalPaymentPendingAmount + studentFeeDetailsDO.getTotalFeePaymentPendingAmount();

			// Totals for refunds.
			totalRefundAmount = totalRefundAmount + studentFeeDetailsDO.getTotalFeeRefundAmount();
			totalRefundRequestAmount = totalRefundRequestAmount + studentFeeDetailsDO.getTotalFeeRefundRequestAmount();
			totalRefundPendingAmount = totalRefundPendingAmount + studentFeeDetailsDO.getTotalFeeRefundPendingAmount();

			// Totals for deductions
			totalDeductedAmount = totalDeductedAmount + studentFeeDetailsDO.getTotalFeeDeductedAmount();
			totalDeductionRequestAmount = totalDeductionRequestAmount + studentFeeDetailsDO.getTotalFeeDeductionRequestAmount();

			// Total Fee
			totalFeeAmount += studentFeeDetailsDO.getFee();

			studentFeeDetailsDOs.add(studentFeeDetailsDO);

		}

		final StudentFinancialDO studentFinancialDO = this.createStudentFinancialDO(studentFee, feeType, totalPaymentAmount, totalPaymentPendingAmount,
				totalRefundAmount, totalRefundRequestAmount, totalRefundPendingAmount, totalDeductedAmount, totalDeductionRequestAmount, totalFeeAmount,
				studentFeeDetailsDOs);

		studentFinancialDOs.add(studentFinancialDO);

	}

	/**
	 * This method compute the student financial details and
	 * 
	 * @param studentFinancialDOs
	 * @param studentId
	 * @param academicYearId
	 * @param dueDate
	 */
	private void computeStudentFianacialDetailsForStudentLevelFee(final Collection<StudentFinancialDO> studentFinancialDOs, final StudentFee studentFee,
			final Date dueDate) {

		final StudentLevelFee studentLevelFee = studentFee.getStudentLevelFee();

		final FeeTypeConstant feeType = studentLevelFee.getBuildingBlock().getFeeType();

		// Get class fee payment patterns
		Collection<StudentLevelFeeCatalog> studentLevelFeeCatalogs = null;
		if (dueDate == null) {
			studentLevelFeeCatalogs = this.studentLevelFeeCatalogDao.findStudentLevelFeeCatalogsByStudentLevelFeeId(studentLevelFee.getId());
		} else {
			studentLevelFeeCatalogs = this.studentLevelFeeCatalogDao.findStudentLevelFeeCatalogsByStudentLevelFeeIdAndDueDate(studentLevelFee.getId(), dueDate);
		}

		double totalPaymentAmount = 0;

		double totalPaymentPendingAmount = 0;

		double totalRefundAmount = 0;

		double totalRefundRequestAmount = 0;

		double totalRefundPendingAmount = 0;

		double totalDeductedAmount = 0;

		double totalDeductionRequestAmount = 0;

		double totalFeeAmount = 0;

		final Collection<StudentFeeDetailsDO> studentFeeDetailsDOs = new ArrayList<StudentFeeDetailsDO>();

		for (final StudentLevelFeeCatalog studentLevelFeeCatalog : studentLevelFeeCatalogs) {

			final StudentFeeDetailsDO studentFeeDetailsDO = this.computeStudentLevelFee(studentFee, studentLevelFeeCatalog);

			// Calculate Totals
			// Totals for payments
			totalPaymentAmount = totalPaymentAmount + studentFeeDetailsDO.getTotalFeePaymentAmount();
			totalPaymentPendingAmount = totalPaymentPendingAmount + studentFeeDetailsDO.getTotalFeePaymentPendingAmount();

			// Totals for refunds.
			totalRefundAmount = totalRefundAmount + studentFeeDetailsDO.getTotalFeeRefundAmount();
			totalRefundRequestAmount = totalRefundRequestAmount + studentFeeDetailsDO.getTotalFeeRefundRequestAmount();
			totalRefundPendingAmount = totalRefundPendingAmount + studentFeeDetailsDO.getTotalFeeRefundPendingAmount();

			// Totals for deductions
			totalDeductedAmount = totalDeductedAmount + studentFeeDetailsDO.getTotalFeeDeductedAmount();
			totalDeductionRequestAmount = totalDeductionRequestAmount + studentFeeDetailsDO.getTotalFeeDeductionRequestAmount();

			// Total Fee
			totalFeeAmount += studentFeeDetailsDO.getFee();

			studentFeeDetailsDOs.add(studentFeeDetailsDO);

		}

		final StudentFinancialDO studentFinancialDO = this.createStudentFinancialDO(studentFee, feeType, totalPaymentAmount, totalPaymentPendingAmount,
				totalRefundAmount, totalRefundRequestAmount, totalRefundPendingAmount, totalDeductedAmount, totalDeductionRequestAmount, totalFeeAmount,
				studentFeeDetailsDOs);

		studentFinancialDOs.add(studentFinancialDO);

	}

	/**
	 * 
	 * @param studentFinancialDOs
	 * @param studentFee
	 * @param dueDate
	 */
	private void computeStudentFianacialDetailsForTransportationLevelFee(final Collection<StudentFinancialDO> studentFinancialDOs, final StudentFee studentFee,
			final Date dueDate) {

		final PickUpPointFee pickUpPointFee = studentFee.getPickUpPointFee();

		final FeeTypeConstant feeType = FeeTypeConstant.TRANSPORTATION_FEE;

		// Get class fee payment patterns
		Collection<PickUpPointFeeCatalog> pickUpPointFeeCatalogs = null;
		if (dueDate == null) {
			pickUpPointFeeCatalogs = this.pickUpPointFeeCatalogDao.findPickUpPointFeeCatalogsByPickUpPointFeeId(pickUpPointFee.getId());
		} else {
			pickUpPointFeeCatalogs = this.pickUpPointFeeCatalogDao.findPickUpPointFeeCatalogsByPickUpPointFeeIdAndDueDate(pickUpPointFee.getId(), dueDate);
		}

		double totalPaymentAmount = 0;

		double totalPaymentPendingAmount = 0;

		double totalRefundAmount = 0;

		double totalRefundRequestAmount = 0;

		double totalRefundPendingAmount = 0;

		double totalDeductedAmount = 0;

		double totalDeductionRequestAmount = 0;

		double totalFeeAmount = 0;

		final Collection<StudentFeeDetailsDO> studentFeeDetailsDOs = new ArrayList<StudentFeeDetailsDO>();

		for (final PickUpPointFeeCatalog pickUpPointFeeCatalog : pickUpPointFeeCatalogs) {

			final StudentFeeDetailsDO studentFeeDetailsDO = this.computePickUpPointFee(studentFee, pickUpPointFeeCatalog);

			// Calculate Totals
			// Totals for payments
			totalPaymentAmount = totalPaymentAmount + studentFeeDetailsDO.getTotalFeePaymentAmount();
			totalPaymentPendingAmount = totalPaymentPendingAmount + studentFeeDetailsDO.getTotalFeePaymentPendingAmount();

			// Totals for refunds.
			totalRefundAmount = totalRefundAmount + studentFeeDetailsDO.getTotalFeeRefundAmount();
			totalRefundRequestAmount = totalRefundRequestAmount + studentFeeDetailsDO.getTotalFeeRefundRequestAmount();
			totalRefundPendingAmount = totalRefundPendingAmount + studentFeeDetailsDO.getTotalFeeRefundPendingAmount();

			// Totals for deductions
			totalDeductedAmount = totalDeductedAmount + studentFeeDetailsDO.getTotalFeeDeductedAmount();
			totalDeductionRequestAmount = totalDeductionRequestAmount + studentFeeDetailsDO.getTotalFeeDeductionRequestAmount();

			// Total Fee
			totalFeeAmount += studentFeeDetailsDO.getFee();

			studentFeeDetailsDOs.add(studentFeeDetailsDO);

		}

		final StudentFinancialDO studentFinancialDO = this.createStudentFinancialDO(studentFee, feeType, totalPaymentAmount, totalPaymentPendingAmount,
				totalRefundAmount, totalRefundRequestAmount, totalRefundPendingAmount, totalDeductedAmount, totalDeductionRequestAmount, totalFeeAmount,
				studentFeeDetailsDOs);

		studentFinancialDOs.add(studentFinancialDO);

	}

	/**
	 * 
	 * @param studentId
	 * @param academicYearId
	 * @param dueDate
	 * @return
	 */
	private Collection<StudentFinancialDO> computeStudentFinancialDetaislByAcademicYearAndDueDate(final Long studentId, final Long academicYearId,
			final Date dueDate) {

		final Collection<StudentFinancialDO> studentFinancialDOs = new ArrayList<StudentFinancialDO>();

		final Collection<StudentFee> studentFees = this.studentFeeDao.findStudentFeesByStudentIdAndAcadmicYearId(studentId, academicYearId);

		for (final StudentFee studentFee : studentFees) {

			if (FeeClassificationLevelConstant.BRANCH_LEVEL.equals(studentFee.getFeeClassificationLevel())) {

				// Calculating class level fee, due , deduction and refund.
				this.computeStudentFianacialDetailsForBranchLevelFee(studentFinancialDOs, studentFee, dueDate);

			} else if (FeeClassificationLevelConstant.KLASS_LEVEL.equals(studentFee.getFeeClassificationLevel())) {

				// Calculating class level fee, due , deduction and refund.
				this.computeStudentFianacialDetailsForKlassLevelFee(studentFinancialDOs, studentFee, dueDate);

			} else if (FeeClassificationLevelConstant.STUDENT_LEVEL.equals(studentFee.getFeeClassificationLevel())) {

				// Calculate student level additional fee , due, deduction and
				// refund.
				this.computeStudentFianacialDetailsForStudentLevelFee(studentFinancialDOs, studentFee, dueDate);

			} else if (FeeClassificationLevelConstant.TRANSPORTATION_LEVEL.equals(studentFee.getFeeClassificationLevel())) {

				// Calculate transportation level additional fee , due,
				// deduction and
				// refund.
				this.computeStudentFianacialDetailsForTransportationLevelFee(studentFinancialDOs, studentFee, dueDate);

			}

			// New level goes here, library fee etc.

		}

		return studentFinancialDOs;
	}

	/**
	 * 
	 * @param studentFeeTransactionDetailsCollection
	 * @param studentAdditionalFee
	 * @return
	 */
	private StudentFeeDetailsDO computeStudentLevelFee(final StudentFee studentFee, final StudentLevelFeeCatalog studentLevelFeeCatalog) {

		final Collection<StudentFeeTransactionDetails> studentFeeTransactionDetailsCollection = this.studentFeeTransactionDetailsDao
				.findStudentFeeTransactionDetailsByStudentLevelFeeCatalogIdAndStudentFeeId(studentLevelFeeCatalog.getId(), studentFee.getId());

		return this.computeStudentFeeTransactionDetails(studentFeeTransactionDetailsCollection, FeeClassificationLevelConstant.STUDENT_LEVEL, studentFee, null,
				null, studentLevelFeeCatalog, null);
	}

	/**
	 * Create batch log for the messages to be sent.
	 * 
	 * @param batchSize
	 *            batch size.
	 * @param branchId
	 *            branch id.
	 * @return batch log created.
	 */
	private BatchLog createBatchLog(final Long batchSize, final Long branchId, final Long notificationLevelId,
			final NotificationTypeConstant notificationTypeConstant, final NotificationLevelConstant notificationLevelConstant,
			final NotificationSubTypeConstant notificationSubTypeConstant, final String messageToBeSent, final String studentFeeTransactionNr) {
		final BranchRule branchRule = this.branchRuleService.findBranchRuleByBranchId(branchId);
		BatchLog batchLog = new BatchLog();
		if ((branchRule != null) && (branchRule.getSmsProvider() != null)) {
			batchLog.setSmsProvider(branchRule.getSmsProvider());
			batchLog.setBatchStatusConstant(batchSize > 0 ? BatchStatusConstant.CREATED : BatchStatusConstant.FINISHED);
			batchLog.setMessage(messageToBeSent);
		} else {
			batchLog.setMessage("SMS Provider is not selected for the branch.");
			batchLog.setBatchStatusConstant(BatchStatusConstant.CANCELLED);
		}
		batchLog.setBranch(this.branchService.findBranchById(branchId));
		batchLog.setCompletedIndicator(false);
		batchLog.setExecutionStartDate(DateUtil.getSystemDate());
		batchLog.setNotificationLevelConstant(notificationLevelConstant);
		batchLog.setNotificationSubTypeConstant(notificationSubTypeConstant);
		batchLog.setNotificationTypeConstant(notificationTypeConstant);
		batchLog.setNrElements(batchSize);
		batchLog.setNotificationLevelId(notificationLevelId);

		batchLog.setStudentFeeTransactionNr(studentFeeTransactionNr);
		batchLog = this.batchLogService.saveBatchLogInNewTransaction(batchLog);
		return batchLog;
	}

	/**
	 * 
	 * @param studentFee
	 * @param feeType
	 * @param totalPaymentAmount
	 * @param totalPaymentPendingAmount
	 * @param totalRefundAmount
	 * @param totalRefundRequestAmount
	 * @param totalRefundPendingAmount
	 * @param totalDeductedAmount
	 * @param totalDeductionRequestAmount
	 * @param totalDeductionPendingAmount
	 * @param totalFeeAmount
	 * @param studentFeeDetailsDOs
	 * @return
	 */
	private StudentFinancialDO createStudentFinancialDO(final StudentFee studentFee, final FeeTypeConstant feeType, final double totalPaymentAmount,
			final double totalPaymentPendingAmount, final double totalRefundAmount, final double totalRefundRequestAmount,
			final double totalRefundPendingAmount, final double totalDeductedAmount, final double totalDeductionRequestAmount, final double totalFeeAmount,
			final Collection<StudentFeeDetailsDO> studentFeeDetailsDOs) {

		final StudentFinancialDO studentFinancialDO = new StudentFinancialDO();

		studentFinancialDO.setFeeClassificationLevel(studentFee.getFeeClassificationLevel());

		studentFinancialDO.setStudentFee(studentFee);

		if (FeeClassificationLevelConstant.BRANCH_LEVEL.equals(studentFee.getFeeClassificationLevel())) {

			studentFinancialDO.setFeeName(studentFee.getBranchLevelFee().getBuildingBlock().getName());

			studentFinancialDO.setPaymentFrequency(studentFee.getBranchLevelFee().getPaymentFrequency());

		} else if (FeeClassificationLevelConstant.KLASS_LEVEL.equals(studentFee.getFeeClassificationLevel())) {

			studentFinancialDO.setFeeName(studentFee.getKlassFee().getBuildingBlock().getName());

			studentFinancialDO.setPaymentFrequency(studentFee.getKlassFee().getPaymentFrequency());

		} else if (FeeClassificationLevelConstant.STUDENT_LEVEL.equals(studentFee.getFeeClassificationLevel())) {

			studentFinancialDO.setFeeName(studentFee.getStudentLevelFee().getBuildingBlock().getName());

			studentFinancialDO.setPaymentFrequency(studentFee.getStudentLevelFee().getPaymentFrequency());

		} else if (FeeClassificationLevelConstant.TRANSPORTATION_LEVEL.equals(studentFee.getFeeClassificationLevel())) {

			studentFinancialDO.setFeeName(studentFee.getPickUpPointFee().getPickUpPoint().getName());

			studentFinancialDO.setPaymentFrequency(studentFee.getPickUpPointFee().getPaymentFrequency());
		}

		studentFinancialDO.setFeeType(feeType);

		studentFinancialDO.setStudentFeeDetailsDOs(studentFeeDetailsDOs);

		studentFinancialDO.setFee(totalFeeAmount);

		studentFinancialDO.setTotalFeePaymentAmount(totalPaymentAmount);

		studentFinancialDO.setTotalFeePaymentPendingAmount(totalPaymentPendingAmount);

		studentFinancialDO.setTotalFeeDueAmount(totalFeeAmount - totalPaymentAmount);

		studentFinancialDO.setTotalFeeRefundRequestAmount(totalRefundRequestAmount);

		studentFinancialDO.setTotalFeeRefundPendingAmount(totalRefundPendingAmount);

		studentFinancialDO.setTotalFeeRefundAmount(totalRefundAmount);

		studentFinancialDO.setTotalFeeDeductedAmount(totalDeductedAmount);

		studentFinancialDO.setTotalFeeDeductionRequestAmount(totalDeductionRequestAmount);

		return studentFinancialDO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentFeeTransaction depositPayments(final Long branchId, final Long studentAcademicYearId, final StudentFeeTransaction studentFeeTransaction,
			final Collection<StudentFeeDetailsDO> studentFeeDetailsDOs) throws BusinessException, SystemException {

		final Date studentFeePaidDate = studentFeeTransaction.getExternalTransactionDate() != null ? studentFeeTransaction.getExternalTransactionDate()
				: DateUtil.getSystemDate();
		DateUtil.clearTimeInfo(studentFeePaidDate);
		this.branchFinancialService.checkIfBranchFinancialTransactionsAreAllowed(branchId, studentFeePaidDate);

		boolean payingAmounTransactionsFound = false;
		final StudentAcademicYear studentAcademicYear = this.studentAcademicYearDao.findById(studentAcademicYearId);

		final String transactionNr = this.sequenceGeneratorService.getNextTransactionNumberForFeeDeposit();

		// Compute total paying amount.

		double totolPayment = 0d;
		for (final StudentFeeDetailsDO studentFeeDetailsDO : studentFeeDetailsDOs) {

			final Double payingAmount = studentFeeDetailsDO.getPayingAmount();

			if ((payingAmount != null) && (payingAmount != 0)) {

				this.validatePayingAmount(studentFeeDetailsDO, payingAmount);
				totolPayment = totolPayment + payingAmount;
			}
		}

		studentFeeTransaction.setAmount(totolPayment);

		studentFeeTransaction.setStudentFeeTransactionType(StudentFeeTransactionTypeConstant.PAYMENT);

		if (PaymentMethodConstant.CHEQUE.equals(studentFeeTransaction.getPaymentMethod())) {
			studentFeeTransaction.setStudentFeeTransactionStatus(StudentFeeTransactionStatusConstant.PAYMENT_PENDING);
		} else {
			studentFeeTransaction.setStudentFeeTransactionStatus(StudentFeeTransactionStatusConstant.PAYMENT_PROCESSED);
		}

		studentFeeTransaction.setTransactionDate(DateUtil.getSystemDate());

		studentFeeTransaction.setTransactionNr(transactionNr);

		studentFeeTransaction.setStudentAcademicYear(studentAcademicYear);

		final StudentFeeTransaction studentFeeTransactionLocal = this.studentFeeTransactionDao.persist(studentFeeTransaction);
		for (final StudentFeeDetailsDO studentFeeDetailsDO : studentFeeDetailsDOs) {

			final Double payingAmount = studentFeeDetailsDO.getPayingAmount();

			if ((payingAmount != null) && (payingAmount != 0)) {

				this.validatePayingAmount(studentFeeDetailsDO, payingAmount);

				payingAmounTransactionsFound = true;

				final StudentFeeTransactionDetails studentFeeTransactionDetails = new StudentFeeTransactionDetails();

				studentFeeTransactionDetails.setAmount(payingAmount);

				studentFeeTransactionDetails.setStudentFee(studentFeeDetailsDO.getStudentFee());

				studentFeeTransactionDetails.setBranchLevelFeeCatalog(studentFeeDetailsDO.getBranchLevelFeeCatalog());

				studentFeeTransactionDetails.setKlassLevelFeeCatalog(studentFeeDetailsDO.getKlassLevelFeeCatalog());

				studentFeeTransactionDetails.setStudentLevelFeeCatalog(studentFeeDetailsDO.getStudentLevelFeeCatalog());

				studentFeeTransactionDetails.setPickUpPointFeeCatalog(studentFeeDetailsDO.getPickUpPointFeeCatalog());

				studentFeeTransactionDetails.setStudentFeeTransaction(studentFeeTransactionLocal);

				this.studentFeeTransactionDetailsDao.persist(studentFeeTransactionDetails);

			}

		}

		if (!payingAmounTransactionsFound) {
			throw new BusinessException("Paying amount need to be specified on atlease on one of the fee payments.");
		}

		try {
			final BranchNotification branchNotification = this.branchNotificationService.findBranchNotificationByBranchIdAnsNotificationSubType(branchId,
					NotificationSubTypeConstant.FEE_PAID_NOTIFICATION);
			if ((branchNotification != null) && branchNotification.getSmsIndicator()) {
				final BatchLog batchLog = this.createBatchLog(Long.valueOf(1), branchId, studentAcademicYear.getId(),
						NotificationTypeConstant.SMS_NOTIFICATION, NotificationLevelConstant.STUDENT_ACADEMIC_YEAR,
						NotificationSubTypeConstant.FEE_PAID_NOTIFICATION, null, studentFeeTransactionLocal.getTransactionNr());
				if (BatchStatusConstant.CREATED.equals(batchLog.getBatchStatusConstant())
						|| BatchStatusConstant.DISTRIBUTED.equals(batchLog.getBatchStatusConstant())) {
					this.notificationService.sendNotificationForStudent(studentAcademicYear, batchLog);
				}
			}
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.processStudentAcademicYearFeeSummary(studentAcademicYearId);
		return studentFeeTransactionLocal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentFeeTransaction> findFeesCollectedBySearchCriteria(final FeeCollectedSearchCriteria feeCollectedSearchCriteria)
			throws BusinessException {
		return this.studentFeeTransactionDao.findFeesCollectedBySearchCriteria(feeCollectedSearchCriteria);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentFeeTransaction> findStudentFeeTransactionsBySearchCriteria(
			final StudentFeeTransactionSearchCriteria studentFeeTransactionSearchCriteria) throws BusinessException {
		return this.studentFeeTransactionDao.findStudentFeeTransactionsBySearchCriteria(studentFeeTransactionSearchCriteria);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentFinancialAcademicYearDO> getFeeDefaultersBySearchCriteria(final FeeDueSearchCriteria feeDueSearchCriteria) {
		final Collection<StudentFinancialAcademicYearDO> studentFinancialAcademicYearDOs = new ArrayList<StudentFinancialAcademicYearDO>();
		final StudentSearchCriteria studentSearchCriteria = new StudentSearchCriteria(feeDueSearchCriteria.getBranch());
		if (feeDueSearchCriteria.getAcademicYear() != null) {
			studentSearchCriteria.setAcademicYear(feeDueSearchCriteria.getAcademicYear());
		}
		if (feeDueSearchCriteria.getKlass() != null) {
			studentSearchCriteria.setKlass(feeDueSearchCriteria.getKlass());
		}
		if (feeDueSearchCriteria.getSection() != null) {
			studentSearchCriteria.setSection(feeDueSearchCriteria.getSection());
		}
		final Collection<StudentSection> studentSections = this.studentService.findStudentSectionsBySearchCriteria(studentSearchCriteria);

		final Collection<StudentFinancialAcademicYearDO> allStudentFinancialAcademicYearDO = this
				.getStudnetFeeFinancialAcademicYearDetailsByAcademicYearIdForDueDate(studentSections, feeDueSearchCriteria.getDueDate());
		for (final StudentFinancialAcademicYearDO studentFinancialAcademicYearDO : allStudentFinancialAcademicYearDO) {
			if ((feeDueSearchCriteria.getFeeDuePercetage() > 0) && (feeDueSearchCriteria.getFeeDuePercetage() < 100)) {
				if (((studentFinancialAcademicYearDO.getNetFeeDue() * 100) / studentFinancialAcademicYearDO.getNetFee()) >= feeDueSearchCriteria
						.getFeeDuePercetage()) {
					studentFinancialAcademicYearDOs.add(studentFinancialAcademicYearDO);
				}

			} else if ((feeDueSearchCriteria.getMinimumDueAmount() > 0)
					&& (studentFinancialAcademicYearDO.getNetFeeDue() >= feeDueSearchCriteria.getMinimumDueAmount())) {
				studentFinancialAcademicYearDOs.add(studentFinancialAcademicYearDO);
			} else if ((feeDueSearchCriteria.getMinimumDueAmount() == 0) && (studentFinancialAcademicYearDO.getNetFeeDue() > 0)) {
				studentFinancialAcademicYearDOs.add(studentFinancialAcademicYearDO);
			}
		}
		return studentFinancialAcademicYearDOs;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double getStudentFeeDue(final Student student, final AcademicYear academicYear, final Date dueDate) {

		Double result = 0.0;
		final Collection<StudentFinancialDO> studentFinancialDOs = this.computeStudentFinancialDetaislByAcademicYearAndDueDate(student.getId(),
				academicYear.getId(), dueDate);
		if ((studentFinancialDOs != null) && !studentFinancialDOs.isEmpty()) {
			for (final StudentFinancialDO studentFinancialDO : studentFinancialDOs) {
				result += studentFinancialDO.getNetFeeDue();
			}
		}
		return result;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentFeeTransactionDetails> getStudentFeeTransactionByStudentIdAndAcademicYearId(final Long studentId, final Long academicYearId) {

		return this.studentFeeTransactionDetailsDao.findStudentFeeTransactionDetailsByStudentIdAndAcademicYearId(studentId, academicYearId);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentFeeTransaction> getStudentFeeTransactionsByStudentAcademicYearId(final Long studentAcademicYearId) {

		final Collection<StudentFee> studentFeesForStudentAcademicYear = this.studentFeeDao.findStudentFeesByStudentAcadmicYearId(studentAcademicYearId);

		final Collection<StudentFeeTransactionDetails> studentFeeTransactionDetailsForStudentAcademicYear = this.studentFeeTransactionDetailsDao
				.findStudentFeeTransactionDetailsByStudentFees(studentFeesForStudentAcademicYear);

		final Collection<StudentFeeTransaction> result = new ArrayList<StudentFeeTransaction>();

		for (final StudentFeeTransactionDetails studentFeeTransactionDetail : studentFeeTransactionDetailsForStudentAcademicYear) {

			final StudentFeeTransaction txn = studentFeeTransactionDetail.getStudentFeeTransaction();

			if (!result.contains(txn)) {

				result.add(studentFeeTransactionDetail.getStudentFeeTransaction());
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentFeeTransaction> getStudentFeeTransactionsByStudentIdAndAcademicYearId(final Long studentId, final Long academicYearId) {
		final Collection<StudentFee> studentFeesForStudentAcademicYear = this.studentFeeDao.findStudentFeesByStudentIdAndAcadmicYearId(studentId,
				academicYearId);
		final Collection<StudentFeeTransactionDetails> studentFeeTransactionDetailsForStudentAcademicYear = this.studentFeeTransactionDetailsDao
				.findStudentFeeTransactionDetailsByStudentFees(studentFeesForStudentAcademicYear);
		final Collection<StudentFeeTransaction> studentFeeTransactionsForStudentAcademicYear = new ArrayList<StudentFeeTransaction>();
		final Collection<Long> studentFeeTransactionIds = new ArrayList<Long>();
		for (final StudentFeeTransactionDetails studentFeeTransactionDetail : studentFeeTransactionDetailsForStudentAcademicYear) {
			if (!studentFeeTransactionIds.contains(studentFeeTransactionDetail.getStudentFeeTransaction().getId())) {
				studentFeeTransactionsForStudentAcademicYear.add(studentFeeTransactionDetail.getStudentFeeTransaction());
			}
		}
		return studentFeeTransactionsForStudentAcademicYear;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentFinancialDO> getStudentFinancialDetailsByStudentIdAndAcadmicYearId(final Long studentId, final Long acadmicYearId) {
		return this.computeStudentFinancialDetaislByAcademicYearAndDueDate(studentId, acadmicYearId, null);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentFinancialDO> getStudentFinancialDetailsByStudentIdAndAcadmicYearIdForDueDate(final Long studentId, final Long acadmicYearId,
			final Date dueDate) {
		return this.computeStudentFinancialDetaislByAcademicYearAndDueDate(studentId, acadmicYearId, dueDate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentFinancialAcademicYearDO> getStudnetFeeFinancialAcademicYearDetailsByAcademicYearIdForDueDate(
			final Collection<StudentSection> studentSections, final Date dueDate) {
		final Collection<StudentFinancialAcademicYearDO> studentFinancialAcademicYearDOs = new ArrayList<StudentFinancialAcademicYearDO>();
		final Map<Long, StudentSection> studentAcademicYearIds = new HashMap<Long, StudentSection>();
		StudentFinancialAcademicYearDO studentFinancialAcademicYearDO = null;
		for (final StudentSection studentSection : studentSections) {
			studentAcademicYearIds.put(studentSection.getStudentAcademicYear().getId(), studentSection);
		}
		Collection<StudentAcademicYearFeeSummary> studentAcademicYearFeeSummaryByStuAcYearIds = new ArrayList<StudentAcademicYearFeeSummary>();
		if ((studentAcademicYearIds.keySet() != null) && (studentAcademicYearIds.keySet().size() > 0)) {
			studentAcademicYearFeeSummaryByStuAcYearIds = this.studentAcademicYearFeeSummaryService
					.findStudentAcademicYearFeeSummaryByStudentAcademicYearIds(studentAcademicYearIds.keySet());
			if ((studentAcademicYearFeeSummaryByStuAcYearIds != null) && (studentAcademicYearFeeSummaryByStuAcYearIds.size() != studentAcademicYearIds.size())) {
				final Map<Long, StudentAcademicYearFeeSummary> retrievedStudentAcademicYearFeeSummary = new HashMap<Long, StudentAcademicYearFeeSummary>();
				for (final StudentAcademicYearFeeSummary studentAcademicYearFeeSummary : studentAcademicYearFeeSummaryByStuAcYearIds) {
					retrievedStudentAcademicYearFeeSummary.put(studentAcademicYearFeeSummary.getStudentAcademicYear().getId(), studentAcademicYearFeeSummary);
				}
				for (final Long stuAcaYearId : studentAcademicYearIds.keySet()) {
					if (retrievedStudentAcademicYearFeeSummary.get(stuAcaYearId) == null) {
						studentAcademicYearFeeSummaryByStuAcYearIds.add(this.studentAcademicYearFeeSummaryService
								.findOrCreateStudentAcademicYearFeeSummaryByStudentAcademicYearId(stuAcaYearId));
					}
				}
			}

			for (final StudentAcademicYearFeeSummary studentAcademicYearFeeSummary : studentAcademicYearFeeSummaryByStuAcYearIds) {
				studentFinancialAcademicYearDO = new StudentFinancialAcademicYearDO();
				studentFinancialAcademicYearDO.setStudentAcademicYearFeeSummary(studentAcademicYearFeeSummary);
				studentFinancialAcademicYearDO.calculateStudentFeeForAcademicYear();
				studentFinancialAcademicYearDO.setStudentSection(studentAcademicYearIds.get(studentAcademicYearFeeSummary.getStudentAcademicYear().getId()));
				studentFinancialAcademicYearDOs.add(studentFinancialAcademicYearDO);
			}
		}
		return studentFinancialAcademicYearDOs;
	}

	/**
	 * 
	 * @param student
	 * @param academicYearId
	 * @param dueDate
	 * @return
	 */
	@Override
	public StudentFinancialAcademicYearDO getStudnetFeeFinancialAcademicYearDetailsByAcademicYearIdForDueDate(final StudentSection studentSection,
			final Date dueDate) {
		final StudentAcademicYearFeeSummary studentAcademicYearFeeSummary = this.studentAcademicYearFeeSummaryService
				.findOrCreateStudentAcademicYearFeeSummaryByStudentAcademicYearId(studentSection.getStudentAcademicYear().getId());
		final StudentFinancialAcademicYearDO studentFinancialAcademicYearDO = new StudentFinancialAcademicYearDO();
		if (studentAcademicYearFeeSummary == null) {
			final Collection<StudentFinancialDO> studentFinancialDOs = this.getStudentFinancialDetailsByStudentIdAndAcadmicYearIdForDueDate(studentSection
					.getStudentAcademicYear().getStudent().getId(), studentSection.getStudentAcademicYear().getAcademicYear().getId(), dueDate);
			studentFinancialAcademicYearDO.setStudentFinancialDOs(studentFinancialDOs);
		} else {
			studentFinancialAcademicYearDO.setStudentAcademicYearFeeSummary(studentAcademicYearFeeSummary);
		}
		studentFinancialAcademicYearDO.calculateStudentFeeForAcademicYear();
		studentFinancialAcademicYearDO.setStudentSection(studentSection);
		return studentFinancialAcademicYearDO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasOutstandingFeeDue(final Long studentId, final Long academicYearId) {

		double feeDue = 0d;

		final Collection<StudentFinancialDO> stuentFinancialDOs = this.getStudentFinancialDetailsByStudentIdAndAcadmicYearId(studentId, academicYearId);

		for (final StudentFinancialDO studentFinancialDO : stuentFinancialDOs) {

			feeDue = feeDue + studentFinancialDO.getNetFeeDue();
		}

		if (feeDue > 0d) {

			return true;
		}

		return false;

	}

	/**
	 * {@inheritDoc}
	 */
	private StudentFeeTransaction processDeductionForRequestOrApproval(final Long studentAcademicYearId, final StudentFeeTransaction studentFeeTransaction,
			final Collection<StudentFeeDetailsDO> studentFeeDetailsDOs, final StudentFeeTransactionStatusConstant processingStatus) throws BusinessException,
			SystemException {

		boolean refundAmountTransactionsFound = false;

		final StudentAcademicYear studentAcademicYear = this.studentAcademicYearDao.findById(studentAcademicYearId);

		// TODO Pradeep , Student fee transaction
		// studentFeeTransactionLocal.setStudentAcademicYear(studentAcademicYear);

		final String transactionNr = this.sequenceGeneratorService.getNextTransactionNumberForFeeDeduct();

		// Compute total deduction amount.

		double totolDeduction = 0d;
		for (final StudentFeeDetailsDO studentFeeDetailsDO : studentFeeDetailsDOs) {

			final Double deductAmount = studentFeeDetailsDO.getDeductingAmount();

			if ((deductAmount != null) && (deductAmount != 0)) {

				this.validateDeductionAmount(studentFeeDetailsDO, deductAmount);
				totolDeduction = totolDeduction + deductAmount;
			}
		}

		studentFeeTransaction.setStudentAcademicYear(studentAcademicYear);

		studentFeeTransaction.setAmount(totolDeduction);

		studentFeeTransaction.setStudentFeeTransactionType(StudentFeeTransactionTypeConstant.DEDUCT);

		studentFeeTransaction.setStudentFeeTransactionStatus(processingStatus);

		studentFeeTransaction.setTransactionDate(DateUtil.getSystemDate());

		studentFeeTransaction.setTransactionNr(transactionNr);

		final StudentFeeTransaction studentFeeTransactionLocal = this.studentFeeTransactionDao.persist(studentFeeTransaction);

		for (final StudentFeeDetailsDO studentFeeDetailsDO : studentFeeDetailsDOs) {

			final Double deductingAmount = studentFeeDetailsDO.getDeductingAmount();

			if ((deductingAmount != null) && (deductingAmount != 0)) {

				this.validateDeductionAmount(studentFeeDetailsDO, deductingAmount);

				refundAmountTransactionsFound = true;

				final StudentFeeTransactionDetails studentFeeTransactionDetails = new StudentFeeTransactionDetails();

				studentFeeTransactionDetails.setAmount(deductingAmount);

				studentFeeTransactionDetails.setStudentFee(studentFeeDetailsDO.getStudentFee());

				studentFeeTransactionDetails.setBranchLevelFeeCatalog(studentFeeDetailsDO.getBranchLevelFeeCatalog());

				studentFeeTransactionDetails.setKlassLevelFeeCatalog(studentFeeDetailsDO.getKlassLevelFeeCatalog());

				studentFeeTransactionDetails.setStudentLevelFeeCatalog(studentFeeDetailsDO.getStudentLevelFeeCatalog());

				studentFeeTransactionDetails.setStudentFeeTransaction(studentFeeTransactionLocal);

				this.studentFeeTransactionDetailsDao.persist(studentFeeTransactionDetails);

			}

		}

		if (!refundAmountTransactionsFound) {
			throw new BusinessException("Deducting amount need to be specified on atlease on one of the fee's.");
		}
		this.processStudentAcademicYearFeeSummary(studentAcademicYearId);
		return studentFeeTransactionLocal;
	}

	/**
	 * 
	 * @param studentId
	 * @param studentFeeTransaction
	 * @param studentFeeDetailsDOs
	 * @param processingStatus
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	private StudentFeeTransaction processRefundForRequestOrApproval(final Long studentAcademicYearId, final StudentFeeTransaction studentFeeTransaction,
			final Collection<StudentFeeDetailsDO> studentFeeDetailsDOs, final StudentFeeTransactionStatusConstant processingStatus) throws BusinessException,
			SystemException {

		boolean refundAmountTransactionsFound = false;

		final StudentAcademicYear studentAcademicYear = this.studentAcademicYearDao.findById(studentAcademicYearId);

		final String transactionNr = this.sequenceGeneratorService.getNextTransactionNumberForFeeDeposit();

		// Compute total refund amount.

		double totolRefund = 0d;
		for (final StudentFeeDetailsDO studentFeeDetailsDO : studentFeeDetailsDOs) {

			final Double refundAmount = studentFeeDetailsDO.getRefundAmount();

			if ((refundAmount != null) && (refundAmount != 0)) {

				this.validateRefundAmount(studentFeeDetailsDO, refundAmount);
				totolRefund = totolRefund + refundAmount;
			}
		}

		studentFeeTransaction.setAmount(totolRefund);

		studentFeeTransaction.setStudentFeeTransactionType(StudentFeeTransactionTypeConstant.REFUND);

		studentFeeTransaction.setStudentFeeTransactionStatus(processingStatus);

		studentFeeTransaction.setTransactionDate(DateUtil.getSystemDate());

		studentFeeTransaction.setTransactionNr(transactionNr);

		studentFeeTransaction.setStudentAcademicYear(studentAcademicYear);

		final StudentFeeTransaction studentFeeTransactionLocal = this.studentFeeTransactionDao.persist(studentFeeTransaction);

		for (final StudentFeeDetailsDO studentFeeDetailsDO : studentFeeDetailsDOs) {

			final Double refundAmount = studentFeeDetailsDO.getRefundAmount();

			if ((refundAmount != null) && (refundAmount != 0)) {

				this.validateRefundAmount(studentFeeDetailsDO, refundAmount);

				refundAmountTransactionsFound = true;

				final StudentFeeTransactionDetails studentFeeTransactionDetails = new StudentFeeTransactionDetails();

				studentFeeTransactionDetails.setAmount(refundAmount);

				studentFeeTransactionDetails.setStudentFee(studentFeeDetailsDO.getStudentFee());

				studentFeeTransactionDetails.setBranchLevelFeeCatalog(studentFeeDetailsDO.getBranchLevelFeeCatalog());

				studentFeeTransactionDetails.setKlassLevelFeeCatalog(studentFeeDetailsDO.getKlassLevelFeeCatalog());

				studentFeeTransactionDetails.setStudentLevelFeeCatalog(studentFeeDetailsDO.getStudentLevelFeeCatalog());

				studentFeeTransactionDetails.setStudentFeeTransaction(studentFeeTransactionLocal);

				this.studentFeeTransactionDetailsDao.persist(studentFeeTransactionDetails);

			}

		}

		if (!refundAmountTransactionsFound) {
			throw new BusinessException("Refund amount need to be specified on atlease on one of the fee's.");
		}
		this.processStudentAcademicYearFeeSummary(studentAcademicYearId);
		return studentFeeTransactionLocal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentAcademicYearFeeSummary processStudentAcademicYearFeeSummary(final Long studentAcademicYearId) {
		StudentAcademicYearFeeSummary studentAcademicYearFeeSummary = this.studentAcademicYearFeeSummaryService
				.findStudentAcademicYearFeeSummaryByStudentAcademicYearId(studentAcademicYearId);
		if (studentAcademicYearFeeSummary == null) {
			studentAcademicYearFeeSummary = this.studentAcademicYearFeeSummaryService
					.createStudentAcademicYearFeeSummaryForStudentAcademicYearId(studentAcademicYearId);
		} else {
			studentAcademicYearFeeSummary = this.studentAcademicYearFeeSummaryService
					.updateStudentAcademicYearFeeSummaryForStudentAcademicYearId(studentAcademicYearId);
		}
		return studentAcademicYearFeeSummary;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentFeeTransaction revertStudentFeeTransaction(final StudentFeeTransaction studentFeeTransaction) {
		StudentFeeTransaction studentFeeTransactionLocal = this.studentFeeTransactionDao.findById(studentFeeTransaction.getId());
		studentFeeTransactionLocal = this.updateStudentFeeTransactionStatus(studentFeeTransactionLocal,
				StudentFeeTransactionStatusConstant.TRANSACTOIN_CANCELLED, true);
		return studentFeeTransactionLocal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentFeeTransaction requestForDeduction(final Long studentId, final StudentFeeTransaction studentFeeTransaction,
			final Collection<StudentFeeDetailsDO> studentFeeDetailsDOs) throws BusinessException, SystemException {

		return this.processDeductionForRequestOrApproval(studentId, studentFeeTransaction, studentFeeDetailsDOs,
				StudentFeeTransactionStatusConstant.DEDUCTION_REQUEST);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentFeeTransaction requestForRefund(final Long studentId, final StudentFeeTransaction studentFeeTransaction,
			final Collection<StudentFeeDetailsDO> studentFeeDetailsDOs) throws BusinessException, SystemException {
		return this.processRefundForRequestOrApproval(studentId, studentFeeTransaction, studentFeeDetailsDOs,
				StudentFeeTransactionStatusConstant.REFUND_REQUEST);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentFeeTransaction retriveStudentFeeTransactionByTransactionNr(final String transactionNr) {
		return this.studentFeeTransactionDao.findStudentFeeTransactionsByTransactionNr(transactionNr);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentFeeTransactionDO retriveStudentFeeTransactionDetailsByTransactionId(final Long transactionId) {

		final StudentFeeTransaction studentFeeTransaction = this.studentFeeTransactionDao.findById(transactionId);
		return this.retriveStudentFeeTransactionDetailsByTransactionNr(studentFeeTransaction.getTransactionNr());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentFeeTransactionDO retriveStudentFeeTransactionDetailsByTransactionNr(final String transactionNr) {

		final StudentFeeTransaction studentFeeTransaction = this.studentFeeTransactionDao.findStudentFeeTransactionsByTransactionNr(transactionNr);

		final Collection<StudentFeeTransactionDetails> studentFeeTransactions = this.studentFeeTransactionDetailsDao
				.findStudentFeeTransactionDetailsByStudentFeeTransactionId(studentFeeTransaction.getId());

		final Collection<StudentFeeTransactionDetailsDO> studentFeeTransactionDetailsDOs = new ArrayList<StudentFeeTransactionDetailsDO>();

		for (final StudentFeeTransactionDetails studentFeeTransactionDetails : studentFeeTransactions) {

			final StudentFeeTransactionDetailsDO studentFeePaymentDO = new StudentFeeTransactionDetailsDO();

			if (FeeClassificationLevelConstant.BRANCH_LEVEL.equals(studentFeeTransactionDetails.getStudentFee().getFeeClassificationLevel())) {

				studentFeePaymentDO.setFeeName(studentFeeTransactionDetails.getBranchLevelFeeCatalog().getBranchLevelFee().getBuildingBlock().getName());
				studentFeePaymentDO.setFeeDueDate(studentFeeTransactionDetails.getBranchLevelFeeCatalog().getDueDate());

			} else if (FeeClassificationLevelConstant.KLASS_LEVEL.equals(studentFeeTransactionDetails.getStudentFee().getFeeClassificationLevel())) {

				studentFeePaymentDO.setFeeName(studentFeeTransactionDetails.getKlassLevelFeeCatalog().getKlassFee().getBuildingBlock().getName());
				studentFeePaymentDO.setFeeDueDate(studentFeeTransactionDetails.getKlassLevelFeeCatalog().getDueDate());

			} else if (FeeClassificationLevelConstant.STUDENT_LEVEL.equals(studentFeeTransactionDetails.getStudentFee().getFeeClassificationLevel())) {

				studentFeePaymentDO.setFeeName(studentFeeTransactionDetails.getStudentLevelFeeCatalog().getStudentLevelFee().getBuildingBlock().getName());
				studentFeePaymentDO.setFeeDueDate(studentFeeTransactionDetails.getStudentLevelFeeCatalog().getDueDate());

			} else if (FeeClassificationLevelConstant.TRANSPORTATION_LEVEL.equals(studentFeeTransactionDetails.getStudentFee().getFeeClassificationLevel())) {

				studentFeePaymentDO.setFeeName(studentFeeTransactionDetails.getPickUpPointFeeCatalog().getPickUpPointFee().getPickUpPoint().getName());
				studentFeePaymentDO.setFeeDueDate(studentFeeTransactionDetails.getPickUpPointFeeCatalog().getDueDate());
			}

			studentFeePaymentDO.setStudentFeeTransactionDetails(studentFeeTransactionDetails);

			studentFeeTransactionDetailsDOs.add(studentFeePaymentDO);
		}

		final StudentAcademicYear studentAcademicYear = studentFeeTransaction.getStudentAcademicYear();

		final StudentSection studentSection = this.studentSectionDao.findLatestStudentSectionByStudentAcademicYearId(studentAcademicYear.getId());

		final StudentFeeTransactionDO studentFeeTransactionDO = new StudentFeeTransactionDO();

		studentFeeTransactionDO.setStudentFeeTransaction(studentFeeTransaction);

		studentFeeTransactionDO.setStudentFeeTransactionDetailsDOs(studentFeeTransactionDetailsDOs);

		studentFeeTransactionDO.setClassName(studentSection.getSection().getKlass().getName());

		studentFeeTransactionDO.setSectionName(studentSection.getSection().getName());

		return studentFeeTransactionDO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentFeeTransaction updateStudentFeeTransactionStatus(final StudentFeeTransaction studentFeeTransaction,
			final StudentFeeTransactionStatusConstant transactionStatus, final boolean updateStudentAcademicYearFeeSummary) throws BusinessException,
			SystemException {
		if (transactionStatus != null) {
			studentFeeTransaction.setStudentFeeTransactionStatus(transactionStatus);
		}
		final StudentFeeTransaction result = this.studentFeeTransactionDao.persist(studentFeeTransaction);

		if (updateStudentAcademicYearFeeSummary) {
			this.processStudentAcademicYearFeeSummary(studentFeeTransaction.getStudentAcademicYear().getId());
		}
		return result;
	}

	private void validateDeductionAmount(final StudentFeeDetailsDO studentFeeDetailsDO, final Double deductAmount) {
		final double allowedDeduction = studentFeeDetailsDO.getTotalNetAmount()
				- (studentFeeDetailsDO.getTotalFeeDeductionRequestAmount() + studentFeeDetailsDO.getTotalFeePaymentPendingAmount());

		if (deductAmount > allowedDeduction) {
			throw new BusinessException(BusinessMessages.getResourceBundleName(),
					BusinessMessages.MSG_DEDUCTING_AMOUNT_SHOULD_NOT_BE_GREATER_THAN_THE_TOTOAL_AMOUNT, new Object[] { studentFeeDetailsDO.getFeeName(),
							allowedDeduction, studentFeeDetailsDO.getKlassLevelFeeCatalog().getDueDate() });
		}
	}

	private void validatePayingAmount(final StudentFeeDetailsDO studentFeeDetailsDO, final Double payingAmount) {
		final double totalAmount = studentFeeDetailsDO.getTotalNetDue()
				- (studentFeeDetailsDO.getTotalFeePaymentPendingAmount() + studentFeeDetailsDO.getTotalFeeDeductionRequestAmount());

		if (payingAmount > totalAmount) {

			throw new BusinessException(BusinessMessages.getResourceBundleName(),
					BusinessMessages.MSG_PAYMENT_AMOUNT_SHOULD_NOT_BE_GREATER_THAN_THE_DUE_AMOUNT, new Object[] { studentFeeDetailsDO.getFeeName(),
							totalAmount, studentFeeDetailsDO.getKlassLevelFeeCatalog().getDueDate() });
		}
	}

	private void validateRefundAmount(final StudentFeeDetailsDO studentFeeDetailsDO, final Double refundAmount) {
		final double allowedRefund = studentFeeDetailsDO.getTotalFeePaymentAmount()
				- (studentFeeDetailsDO.getTotalFeeRefundPendingAmount() + studentFeeDetailsDO.getTotalFeeRefundRequestAmount() + studentFeeDetailsDO
						.getTotalFeePaymentPendingAmount());

		if (refundAmount > allowedRefund) {
			throw new BusinessException(BusinessMessages.getResourceBundleName(),
					BusinessMessages.MSG_REFUND_AMOUNT_SHOULD_NOT_BE_GREATER_THAN_THE_TOTOAL_AMOUNT, new Object[] { studentFeeDetailsDO.getFeeName(),
							allowedRefund, studentFeeDetailsDO.getKlassLevelFeeCatalog().getDueDate() });
		}
	}

	@Override
	public StudentFeeTransaction rejectRefundRequest(final StudentFeeTransaction studentFeeTransaction) throws BusinessException, SystemException {

		if (StudentFeeTransactionTypeConstant.REFUND.equals(studentFeeTransaction.getStudentFeeTransactionType())
				&& StudentFeeTransactionStatusConstant.REFUND_REQUEST.equals(studentFeeTransaction.getStudentFeeTransactionStatus())) {

			studentFeeTransaction.setStudentFeeTransactionStatus(StudentFeeTransactionStatusConstant.REJECT_REFUND);
			return this.studentFeeTransactionDao.persist(studentFeeTransaction);

		} else {
			throw new BusinessException("Transactions status can be changed to refund rejected only if the current transactions is status is refund request.");
		}

	}

	@Override
	public StudentFeeTransaction approveRefundRequest(final StudentFeeTransaction studentFeeTransaction) throws BusinessException, SystemException {

		if (StudentFeeTransactionTypeConstant.REFUND.equals(studentFeeTransaction.getStudentFeeTransactionType())
				&& StudentFeeTransactionStatusConstant.REFUND_REQUEST.equals(studentFeeTransaction.getStudentFeeTransactionStatus())) {

			studentFeeTransaction.setStudentFeeTransactionStatus(StudentFeeTransactionStatusConstant.REFUND_PENDING);
			return this.studentFeeTransactionDao.persist(studentFeeTransaction);

		} else {
			throw new BusinessException("Transactions status can be changed to refund pending only if the current transactions is status is refund request.");
		}
	}

	@Override
	public StudentFeeTransaction rejectDeductionRequest(final StudentFeeTransaction studentFeeTransaction) throws BusinessException, SystemException {

		if (StudentFeeTransactionTypeConstant.DEDUCT.equals(studentFeeTransaction.getStudentFeeTransactionType())
				&& StudentFeeTransactionStatusConstant.DEDUCTION_REQUEST.equals(studentFeeTransaction.getStudentFeeTransactionStatus())) {

			studentFeeTransaction.setStudentFeeTransactionStatus(StudentFeeTransactionStatusConstant.REJECT_DEDUCTION);
			return this.studentFeeTransactionDao.persist(studentFeeTransaction);

		} else {
			throw new BusinessException(
					"Transactions status can be changed to reject deduction only if the current transactions is status is deduction request.");
		}

	}

	@Override
	public StudentFeeTransaction approveDeductionRequest(final StudentFeeTransaction studentFeeTransaction) throws BusinessException, SystemException {

		if (StudentFeeTransactionTypeConstant.DEDUCT.equals(studentFeeTransaction.getStudentFeeTransactionType())
				&& StudentFeeTransactionStatusConstant.DEDUCTION_REQUEST.equals(studentFeeTransaction.getStudentFeeTransactionStatus())) {

			studentFeeTransaction.setStudentFeeTransactionStatus(StudentFeeTransactionStatusConstant.DEDUCTION_PROCESSED);
			return this.studentFeeTransactionDao.persist(studentFeeTransaction);

		} else {
			throw new BusinessException(
					"Transactions status can be changed to deduction approved only if the current transactions is status is deduction request.");
		}

	}

	@Override
	public StudentFeeTransaction processPaymentRequest(final StudentFeeTransaction studentFeeTransaction) throws BusinessException, SystemException {

		if (StudentFeeTransactionTypeConstant.PAYMENT.equals(studentFeeTransaction.getStudentFeeTransactionType())
				&& StudentFeeTransactionStatusConstant.PAYMENT_PENDING.equals(studentFeeTransaction.getStudentFeeTransactionStatus())) {
			studentFeeTransaction.setStudentFeeTransactionStatus(StudentFeeTransactionStatusConstant.PAYMENT_PROCESSED);
			return this.studentFeeTransactionDao.persist(studentFeeTransaction);
		} else {
			throw new BusinessException(
					"Transactions status can be changed to payment processed only if the current transactions is status is payment pending.");
		}

	}

	@Override
	public StudentFeeTransaction processRefundRequest(final StudentFeeTransaction studentFeeTransaction) throws BusinessException, SystemException {

		if (StudentFeeTransactionTypeConstant.REFUND.equals(studentFeeTransaction.getStudentFeeTransactionType())
				&& StudentFeeTransactionStatusConstant.REFUND_PENDING.equals(studentFeeTransaction.getStudentFeeTransactionStatus())) {

			studentFeeTransaction.setStudentFeeTransactionStatus(StudentFeeTransactionStatusConstant.REFUND_PROCESSED);
			return this.studentFeeTransactionDao.persist(studentFeeTransaction);

		} else {
			throw new BusinessException("Transactions status can be changed to refund processed only if the current transactions is status is refund pending.");
		}

	}
}
