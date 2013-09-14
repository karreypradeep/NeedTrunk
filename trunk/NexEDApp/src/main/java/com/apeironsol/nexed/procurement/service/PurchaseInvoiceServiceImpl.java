/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.procurement.service;

import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.nexed.core.model.AcademicYear;
import com.apeironsol.nexed.core.model.Branch;
import com.apeironsol.nexed.core.service.SequenceGeneratorService;
import com.apeironsol.nexed.financial.service.BranchAccountService;
import com.apeironsol.nexed.financial.service.BranchFinancialService;
import com.apeironsol.nexed.notifications.model.BatchLog;
import com.apeironsol.nexed.notifications.model.BranchNotification;
import com.apeironsol.nexed.notifications.service.BatchLogService;
import com.apeironsol.nexed.notifications.service.BranchNotificationService;
import com.apeironsol.nexed.notifications.service.NotificationService;
import com.apeironsol.nexed.procurement.dao.PurchaseInvoiceDao;
import com.apeironsol.nexed.procurement.model.PurchaseInvoice;
import com.apeironsol.nexed.util.DateUtil;
import com.apeironsol.nexed.util.constants.BatchStatusConstant;
import com.apeironsol.nexed.util.constants.BranchAccountTypeConstant;
import com.apeironsol.nexed.util.constants.NotificationLevelConstant;
import com.apeironsol.nexed.util.constants.NotificationSubTypeConstant;
import com.apeironsol.nexed.util.constants.NotificationTypeConstant;
import com.apeironsol.nexed.util.constants.PaymentMethodConstant;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Service("purchaseInvoiceService")
@Transactional
public class PurchaseInvoiceServiceImpl implements PurchaseInvoiceService {

	@Resource
	private PurchaseInvoiceDao			purchaseInvoiceDao;

	@Resource
	SequenceGeneratorService			sequenceGeneratorService;

	@Resource
	private BranchNotificationService	branchNotificationService;

	@Resource
	BranchAccountService				branchAccountService;

	@Resource
	NotificationService					notificationService;

	@Resource
	BatchLogService						batchLogService;

	@Resource
	private BranchFinancialService		branchFinancialService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PurchaseInvoice> findPurchaseInvoicesByBranchId(final Long branchId) throws BusinessException {
		return this.purchaseInvoiceDao.findPurchaseInvoicesByBranchId(branchId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PurchaseInvoice findPurchaseInvoiceById(final Long id) throws BusinessException {
		return this.purchaseInvoiceDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PurchaseInvoice savePurchaseInvoice(final PurchaseInvoice purchaseInvoice) throws BusinessException, InvalidArgumentException {

		this.branchFinancialService.checkIfBranchFinancialTransactionsAreAllowed(purchaseInvoice.getBranch().getId(), purchaseInvoice.getInvoiceDate());

		if (purchaseInvoice.getId() == null) {
			final String transactionNr = this.sequenceGeneratorService.getNextTransactionNumberForInvoice();
			purchaseInvoice.setTransactionNr(transactionNr);
		}

		final PurchaseInvoice result = this.purchaseInvoiceDao.persist(purchaseInvoice);
		try {
			if (PaymentMethodConstant.CHEQUE.equals(result.getPaymentMethod())) {
				this.branchAccountService.updateBranchAccountTransaction(result);
			}

			final BranchNotification branchNotification = this.branchNotificationService.findBranchNotificationByBranchIdAnsNotificationSubType(purchaseInvoice
					.getBranch().getId(), NotificationSubTypeConstant.INVOICE_NOTIFICATION);

			if (branchNotification != null && branchNotification.getSmsIndicator()) {
				if (branchNotification.getSmsIndicator()) {
					final BatchLog batchLog = this.createBatchLog(Long.valueOf(1), purchaseInvoice.getBranch(), result.getId(),
							NotificationTypeConstant.SMS_NOTIFICATION, NotificationLevelConstant.BRANCH, NotificationSubTypeConstant.INVOICE_NOTIFICATION,
							null, null);
					this.notificationService.sendNotification(batchLog);

				} else if (branchNotification.getEmailIndicator()) {
					final BatchLog batchLog = this.createBatchLog(Long.valueOf(1), purchaseInvoice.getBranch(), result.getId(),
							NotificationTypeConstant.EMAIL_NOTIFICATION, NotificationLevelConstant.BRANCH, NotificationSubTypeConstant.INVOICE_NOTIFICATION,
							null, null);
					this.notificationService.sendNotification(batchLog);
				}
			}
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removePurchaseInvoice(final PurchaseInvoice purchaseInvoice) throws BusinessException {
		if (purchaseInvoice.getId() != null && PaymentMethodConstant.CHEQUE.equals(purchaseInvoice.getPaymentMethod())) {
			Long accountId = null;
			if (BranchAccountTypeConstant.BANK_ACCOUNT.equals(purchaseInvoice.getBranchAccountType())) {
				accountId = purchaseInvoice.getBranchBankAccount().getId();
			} else {
				accountId = purchaseInvoice.getBranchCreditAccount().getId();
			}
			this.branchAccountService.deleteBranchAccountTransactionByExternalTransactionAndAccountId(purchaseInvoice.getBranchAccountType(), accountId,
					purchaseInvoice.getTransactionNr());
		}
		this.purchaseInvoiceDao.remove(purchaseInvoice);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PurchaseInvoice> findAllPurchaseInvoices() throws BusinessException {
		return this.purchaseInvoiceDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PurchaseInvoice> findPurchaseInvoicesByBranchIdAndAcademicYear(final Long branchId, final AcademicYear academicYear)
			throws BusinessException {
		return this.purchaseInvoiceDao.findPurchaseInvoicesByBranchIdAndAcademicYear(branchId, academicYear);
	}

	@Override
	public PurchaseInvoice findPurchaseInvoiceByPurchaseInvoiceNumber(final String purchaseInvoiceNumber) throws BusinessException {
		return this.purchaseInvoiceDao.findPurchaseInvoiceByPurchaseInvoiceNumber(purchaseInvoiceNumber);
	}

	@Override
	public Collection<PurchaseInvoice> findPurchaseInvoiceByPurchaseOrderNumber(final String purchaseOrderNumber) throws BusinessException {
		return this.purchaseInvoiceDao.findPurchaseInvoiceByPurchaseOrderNumber(purchaseOrderNumber);
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
	public Collection<PurchaseInvoice> findPurchaseInvoicesByBranchIdAndAcademicYear(final Long branchId, final Date startDate, final Date toDate)
			throws BusinessException {
		return this.purchaseInvoiceDao.findPurchaseInvoicesByBranchIdAndAcademicYear(branchId, startDate, toDate);
	}

}
