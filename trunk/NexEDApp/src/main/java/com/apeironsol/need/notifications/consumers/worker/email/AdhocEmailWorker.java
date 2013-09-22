/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.consumers.worker.email;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.notifications.consumers.worker.util.EmailAndSMSUtil;
import com.apeironsol.need.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;
import com.apeironsol.framework.exception.ApplicationException;

/**
 * Class for sending email notification for student pending fee.
 * The scope of this bean is prototype as a new bean should be created for
 * sending email as t.
 * 
 * @author Pradeep
 */
@Component
public class AdhocEmailWorker implements EmailWorker {

	/**
	 * Java mail sender for sending mails.
	 */
	@Autowired
	private JavaMailSender		mailSender;

	/**
	 * Field holding from address for sending mail.
	 */
	private static final String	FROM_ADDRESS	= "pradeep.karrey@apeironsol.com";

	/**
	 * Asynchronous method for sending fee pending notification mail for
	 * student.
	 * 
	 * @param student
	 *            student.
	 * @throws MessagingException
	 */
	@Override
	public NotificationMessage sendMail(final StudentAcademicYear studentAcademicYear, final BatchLog batchLog) throws MessagingException {
		NotificationMessage notificationMessage = new NotificationMessage();
		EmailAndSMSUtil emailAndSMSUtil = new EmailAndSMSUtil();
		emailAndSMSUtil.setMailSender(this.mailSender);
		String subject = "Fee paid " + studentAcademicYear.getStudent().getDisplayName();
		String emailText = batchLog.getMessage();
		notificationMessage.setMessage(emailText);
		if (emailText != null && emailText.trim().isEmpty() && studentAcademicYear.getStudent().getAddress().getEmail() != null) {
			notificationMessage.setSentAddress(studentAcademicYear.getStudent().getAddress().getEmail());
			emailAndSMSUtil.sendMail(FROM_ADDRESS, new String[] { studentAcademicYear.getStudent().getAddress().getEmail() }, emailText, subject);
			notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.SUCCESS);
		} else {
			if (emailText == null || emailText.trim().isEmpty()) {
				notificationMessage.setErrorMessage("Message is null.");
			} else {
				notificationMessage.setErrorMessage("No email address defined in contact details.");
			}
			notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
		}
		return notificationMessage;
	}

	@Override
	public String getMessage(final StudentAcademicYear studentAcademicYear, final BatchLog batchLog) throws ApplicationException {
		return batchLog.getMessage();
	}

}
