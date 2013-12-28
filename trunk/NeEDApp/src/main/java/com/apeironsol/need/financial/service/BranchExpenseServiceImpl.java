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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.service.SequenceGeneratorService;
import com.apeironsol.need.financial.dao.BranchExpenseDao;
import com.apeironsol.need.financial.model.BranchExpense;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.notifications.model.BranchNotification;
import com.apeironsol.need.notifications.service.BatchLogService;
import com.apeironsol.need.notifications.service.BranchNotificationService;
import com.apeironsol.need.notifications.service.NotificationService;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.BatchStatusConstant;
import com.apeironsol.need.util.constants.BranchAccountTypeConstant;
import com.apeironsol.need.util.constants.NotificationLevelConstant;
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;
import com.apeironsol.need.util.constants.NotificationTypeConstant;
import com.apeironsol.need.util.constants.PaymentMethodConstant;
import com.apeironsol.need.util.searchcriteria.BranchExpenseSearchCriteria;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Service("branchExpenseService")
@Transactional(rollbackFor = Exception.class)
public class BranchExpenseServiceImpl implements BranchExpenseService {

	@Resource
	private BranchExpenseDao			branchExpenseDao;

	@Resource
	private BranchNotificationService	branchNotificationService;

	@Resource
	private BatchLogService				batchLogService;

	@Resource
	private BranchAccountService		branchAccountService;

	@Resource
	private NotificationService			notificationService;

	@Resource
	private SequenceGeneratorService	sequenceGeneratorService;

	@Resource
	private BranchFinancialService		branchFinancialService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchExpense> findBranchExpensesByBranchId(final Long branchId) throws BusinessException {
		return this.branchExpenseDao.findBranchExpensesByBranchId(branchId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchExpense findBranchExpenseById(final Long id) throws BusinessException {
		return this.branchExpenseDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchExpense saveBranchExpense(final BranchExpense branchExpense) throws BusinessException, InvalidArgumentException {

		this.branchFinancialService.checkIfBranchFinancialTransactionsAreAllowed(branchExpense.getBranch().getId(), branchExpense.getExpenseDate());

		if (branchExpense.getId() == null) {
			final String transactionNr = this.sequenceGeneratorService.getNextTransactionNumberForExpense();
			branchExpense.setTransactionNr(transactionNr);
		}
		final BranchExpense result = this.branchExpenseDao.persist(branchExpense);
		if (PaymentMethodConstant.CHEQUE.equals(branchExpense.getPaymentMethod())) {
			this.branchAccountService.updateBranchAccountTransaction(result);
		}

		this.sendNotification(branchExpense);

		return result;
	}

	/**
	 * Send notification for expense incurred.
	 * 
	 * @param branchExpense
	 *            branch expense.
	 */
	private void sendNotification(final BranchExpense branchExpense) {
		try {
			final BranchNotification branchNotification = this.branchNotificationService.findBranchNotificationByBranchIdAnsNotificationSubType(branchExpense
					.getBranch().getId(), NotificationSubTypeConstant.EXPENSES_INCURRED_NOTIFICATION);
			if (branchNotification != null && branchNotification.getSmsIndicator()) {
				if (branchNotification.getSmsIndicator()) {
					final BatchLog batchLog = this.createBatchLog(Long.valueOf(1), branchExpense.getBranch(), branchExpense.getId(),
							NotificationTypeConstant.SMS_NOTIFICATION, NotificationLevelConstant.BRANCH,
							NotificationSubTypeConstant.EXPENSES_INCURRED_NOTIFICATION, null, null);
					this.notificationService.sendNotification(batchLog);

				}
				if (branchNotification.getEmailIndicator()) {
					final BatchLog batchLog = this.createBatchLog(Long.valueOf(1), branchExpense.getBranch(), branchExpense.getId(),
							NotificationTypeConstant.EMAIL_NOTIFICATION, NotificationLevelConstant.BRANCH,
							NotificationSubTypeConstant.EXPENSES_INCURRED_NOTIFICATION, null, null);
					this.notificationService.sendNotification(batchLog);
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBranchExpense(final BranchExpense branchExpense) throws BusinessException {
		this.branchFinancialService.checkIfBranchFinancialTransactionsAreAllowed(branchExpense.getBranch().getId(), branchExpense.getExpenseDate());
		if (branchExpense.getId() != null && PaymentMethodConstant.CHEQUE.equals(branchExpense.getPaymentMethod())) {
			Long accountId = null;
			if (BranchAccountTypeConstant.BANK_ACCOUNT.equals(branchExpense.getBranchAccountType())) {
				accountId = branchExpense.getBranchBankAccount().getId();
			} else {
				accountId = branchExpense.getBranchCreditAccount().getId();
			}
			this.branchAccountService.deleteBranchAccountTransactionByExternalTransactionAndAccountId(branchExpense.getBranchAccountType(), accountId,
					branchExpense.getTransactionNr());
		}
		this.branchExpenseDao.remove(branchExpense);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchExpense> findAllBranchExpenses() throws BusinessException {
		return this.branchExpenseDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchExpense> findBranchExpensesByBranchIdBuildingBLockIdAndAcademicYear(final Long branchId, final Long buildingBlockId,
			final AcademicYear academicYear) throws BusinessException {
		return this.branchExpenseDao.findBranchExpensesByBranchIdBuildingBLockIdAndAcademicYear(branchId, buildingBlockId, academicYear);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchExpense> findBranchExpensesByBranchIdAndAcademicYear(final Long branchId, final AcademicYear academicYear) throws BusinessException {
		return this.branchExpenseDao.findBranchExpensesByBranchIdAndAcademicYear(branchId, academicYear);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchExpense> findBranchExpensesBySearchCriteria(final BranchExpenseSearchCriteria branchExpenseSearchCriteria) throws BusinessException {
		return this.branchExpenseDao.findBranchExpensesBySearchCriteria(branchExpenseSearchCriteria);
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
	private BatchLog createBatchLog(final Long batchSize, final Branch branch, final Long notificationLevelId,
			final NotificationTypeConstant notificationTypeConstant, final NotificationLevelConstant notificationLevelConstant,
			final NotificationSubTypeConstant notificationSubTypeConstant, final String messageToBeSent, final String studentFeeTransactionNr) {
		BatchLog batchLog = new BatchLog();
		batchLog.setBatchStatusConstant(batchSize > 0 ? BatchStatusConstant.CREATED : BatchStatusConstant.FINISHED);
		batchLog.setBranch(branch);
		batchLog.setCompletedIndicator(false);
		batchLog.setExecutionStartDate(DateUtil.getSystemDate());
		batchLog.setNotificationLevelConstant(notificationLevelConstant);
		batchLog.setNotificationSubTypeConstant(notificationSubTypeConstant);
		batchLog.setNotificationTypeConstant(notificationTypeConstant);
		batchLog.setNrElements(batchSize);
		batchLog.setNotificationLevelId(notificationLevelId);
		batchLog.setMessage(messageToBeSent);
		batchLog.setStudentFeeTransactionNr(studentFeeTransactionNr);
		batchLog = this.batchLogService.saveBatchLogInNewTransaction(batchLog);
		return batchLog;
	}

	@Override
	public Collection<BranchExpense> findBranchExpensesByBranchIdBetweenDates(final Long branchId, final Date fromDate, final Date toDate)
			throws BusinessException {
		return this.branchExpenseDao.findBranchExpensesByBranchIdBetweenDates(branchId, fromDate, toDate);
	}

}
