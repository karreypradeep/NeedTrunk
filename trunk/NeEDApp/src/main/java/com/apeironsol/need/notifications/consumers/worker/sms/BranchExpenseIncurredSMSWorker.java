/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.consumers.worker.sms;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.apache.http.client.ClientProtocolException;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.apeironsol.framework.NeEDJMSObject;
import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.core.model.SMSProvider;
import com.apeironsol.need.financial.model.BranchExpense;
import com.apeironsol.need.financial.service.BranchExpenseService;
import com.apeironsol.need.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.need.notifications.model.BranchNotification;
import com.apeironsol.need.notifications.providers.sms.UniversalSMSProvider;
import com.apeironsol.need.notifications.service.BranchNotificationService;
import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;

/**
 * Class for sending email notification for student pending fee.
 * 
 * @author Pradeep
 */
@Component
public class BranchExpenseIncurredSMSWorker implements SMSWorker {

	@Resource
	private BranchExpenseService		branchExpenseService;

	@Resource
	private BranchNotificationService	branchNotificationService;

	/**
	 * Velocity template path for notification.
	 */
	private static final String			VELOCITY_TEMPLATE_PATH	= "velocityTemplates/expenseSpendSMSTemplate.vm";

	/**
	 * Velocity engine for compiling and merging text with velocity templates.
	 */
	@Autowired
	private VelocityEngine				velocityEngine;

	/**
	 * Asynchronous method for sending fee pending notification mail for
	 * student.
	 * 
	 * @param student
	 *            student.
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws MessagingException
	 */
	@Override
	public NotificationMessage sendSMS(final NeEDJMSObject neEDJMSObject) throws ClientProtocolException, URISyntaxException, IOException {
		final NotificationMessage notificationMessage = new NotificationMessage();
		final SMSProvider sMSProvider = neEDJMSObject.getSmsProvider() != null ? neEDJMSObject.getSmsProvider() : neEDJMSObject.getBatchLog().getSmsProvider();
		if (neEDJMSObject.getBatchLog().getNotificationLevelId() != null) {
			final BranchExpense branchExpense = this.branchExpenseService.findBranchExpenseById(neEDJMSObject.getBatchLog().getNotificationLevelId());
			if (branchExpense != null) {
				String smsText = neEDJMSObject.getBatchLog().getMessage();
				final Map<String, String> model = new HashMap<String, String>();
				model.put("amount", branchExpense.getAmount().toString());
				model.put("branchName", neEDJMSObject.getBatchLog().getBranch().getName());
				model.put("date", new SimpleDateFormat("dd/mm/yyyy").format(branchExpense.getExpenseDate()));
				model.put("expenseName", branchExpense.getName());
				model.put("userName", branchExpense.getAuditUsername());
				if ((smsText == null) || smsText.trim().isEmpty()) {
					smsText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, VELOCITY_TEMPLATE_PATH, model);
				}
				notificationMessage.setMessage(smsText);
				final UniversalSMSProvider universalSMSProvider = new UniversalSMSProvider(sMSProvider);
				final BranchNotification branchNotification = this.branchNotificationService.findBranchNotificationByBranchIdAnsNotificationSubType(
						neEDJMSObject.getBatchLog().getBranch().getId(), NotificationSubTypeConstant.EXPENSES_INCURRED_NOTIFICATION);
				if ((branchNotification.getMinimumAmount() != null) && (branchExpense.getAmount() < branchNotification.getMinimumAmount())) {
					notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
					notificationMessage.setErrorMessage("Expense entered is less than minimum amount required for sending SMS.");
				} else if (branchNotification.getContactNumbers() != null) {
					notificationMessage.setSentAddress(branchNotification.getContactNumbers());
					final String smsReturnTest = universalSMSProvider.sendSMS(branchNotification.getContactNumbers().contains(",") ? branchNotification
							.getContactNumbers().split(",") : new String[] { branchNotification.getContactNumbers() }, smsText);
					if (smsReturnTest.toLowerCase().contains(sMSProvider.getSuccessString().toLowerCase())) {
						notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.SUCCESS);
					} else {
						notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.FAILED);
						notificationMessage.setErrorMessage(smsReturnTest);
					}
				} else {
					notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
					notificationMessage.setErrorMessage("No Contact numbers specified in branch notification settings for expense.");
				}
			} else {
				notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
				notificationMessage.setErrorMessage("Branch Expense with expense id " + neEDJMSObject.getBatchLog().getNotificationLevelId()
						+ " is not available.");
			}
		} else {
			notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
			notificationMessage.setErrorMessage("Branch Expense id not present in batch log");
		}
		return notificationMessage;
	}

	@Override
	public String getMessage(final NeEDJMSObject neEDJMSObject) throws ApplicationException {
		String smsText = neEDJMSObject.getBatchLog().getMessage();
		final Map<String, String> model = new HashMap<String, String>();
		if ((smsText == null) || smsText.trim().isEmpty()) {
			smsText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, VELOCITY_TEMPLATE_PATH, model);
		}
		neEDJMSObject.getBatchLog().setMessage(smsText);
		return neEDJMSObject.getBatchLog().getMessage();
	}
}
