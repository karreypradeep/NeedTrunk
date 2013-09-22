/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.consumers.worker.email;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.financial.model.BranchExpense;
import com.apeironsol.need.financial.service.BranchExpenseService;
import com.apeironsol.need.notifications.consumers.worker.util.EmailAndSMSUtil;
import com.apeironsol.need.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.notifications.model.BranchNotification;
import com.apeironsol.need.notifications.service.BranchNotificationService;
import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;
import com.apeironsol.framework.exception.ApplicationException;

/**
 * Class for sending email notification for student pending fee.
 * 
 * @author Pradeep
 */
@Component
public class BranchExpenseIncurredEmailWorker implements EmailWorker {

	/**
	 * Java mail sender for sending mails.
	 */
	@Autowired
	private JavaMailSender				mailSender;

	/**
	 * Field holding from address for sending mail.
	 */
	private static final String			FROM_ADDRESS	= "pradeep.karrey@apeironsol.com";

	@Resource
	private BranchExpenseService		branchExpenseService;

	@Resource
	private BranchNotificationService	branchNotificationService;

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
	public NotificationMessage sendMail(final StudentAcademicYear studentAcademicYear, final BatchLog batchLog) throws MessagingException {
		NotificationMessage notificationMessage = new NotificationMessage();
		if (batchLog.getNotificationLevelId() != null) {
			BranchExpense branchExpense = this.branchExpenseService.findBranchExpenseById(batchLog.getNotificationLevelId());
			if (branchExpense != null) {
				BranchNotification branchNotification = this.branchNotificationService.findBranchNotificationByBranchIdAnsNotificationSubType(batchLog
						.getBranch().getId(), NotificationSubTypeConstant.EXPENSES_INCURRED_NOTIFICATION);
				EmailAndSMSUtil emailAndSMSUtil = new EmailAndSMSUtil();
				emailAndSMSUtil.setMailSender(this.mailSender);
				String emailText = batchLog.getMessage();
				notificationMessage.setMessage(emailText);
				if (emailText != null && emailText.trim().isEmpty() && branchNotification.getContactNumbers() != null) {
					notificationMessage.setSentAddress(branchNotification.getContactNumbers());
					emailAndSMSUtil.sendMail(FROM_ADDRESS,
							branchNotification.getContactNumbers().contains(",") ? branchNotification.getContactNumbers().split(",")
									: new String[] { branchNotification.getContactNumbers() }, emailText, "Expense incurred");
					notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.SUCCESS);
				} else {
					if (emailText == null || emailText.trim().isEmpty()) {
						notificationMessage.setErrorMessage("Message is null.");
					} else {
						notificationMessage.setErrorMessage("No email address defined in contact details.");
					}
					notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
				}
			} else {
				notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
				notificationMessage.setMessage(batchLog.getMessage());
				notificationMessage.setErrorMessage("Branch Expense not present with expense id " + batchLog.getNotificationLevelId());
			}
		} else {
			notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
			notificationMessage.setMessage(batchLog.getMessage());
			notificationMessage.setErrorMessage("Branch Expense id not present in batch log");
		}
		return notificationMessage;
	}

	@Override
	public String getMessage(final StudentAcademicYear studentAcademicYear, final BatchLog batchLog) throws ApplicationException {
		return batchLog.getMessage();
	}
}
