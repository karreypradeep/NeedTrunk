/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.consumers.worker.email;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.notifications.consumers.worker.util.EmailAndSMSUtil;
import com.apeironsol.need.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;
import com.apeironsol.framework.exception.ApplicationException;

/**
 * Class for sending email notification for student pending fee.
 * 
 * @author Pradeep
 */
@Component
public class FeePaidEmailWorker implements EmailWorker {

	/**
	 * Java mail sender for sending mails.
	 */
	@Autowired
	private JavaMailSender		mailSender;

	/**
	 * Velocity engine for compiling and merging text with velocity templates.
	 */
	@Autowired
	private VelocityEngine		velocityEngine;

	/**
	 * Field holding from address for sending mail.
	 */
	private static final String	FROM_ADDRESS			= "pradeep.karrey@apeironsol.com";

	/**
	 * Velocity template path for notification.
	 */
	private static final String	VELOCITY_TEMPLATE_PATH	= "velocityTemplates/studentFeePaidEmailTemplate.vm";

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
		Map<String, String> model = new HashMap<String, String>();
		model.put("studentName", studentAcademicYear.getStudent().getDisplayName());
		model.put("emailAddress", studentAcademicYear.getStudent().getAddress().getEmail());
		String emailText = batchLog.getMessage();
		if (emailText == null || emailText.trim().isEmpty()) {
			emailText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, VELOCITY_TEMPLATE_PATH, model);
		}
		notificationMessage.setMessage(emailText);
		if (studentAcademicYear.getStudent().getAddress().getEmail() != null) {
			String subject = "Fee Notificaton " + studentAcademicYear.getStudent().getDisplayName();
			notificationMessage.setSentAddress(studentAcademicYear.getStudent().getAddress().getEmail());
			emailAndSMSUtil.sendMail(FROM_ADDRESS, new String[] { studentAcademicYear.getStudent().getAddress().getEmail() }, emailText, subject);
			notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.SUCCESS);
		} else {
			notificationMessage.setErrorMessage("No email address defined in contact details.");
			notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
		}
		return notificationMessage;
	}

	@Override
	public String getMessage(final StudentAcademicYear studentAcademicYear, final BatchLog batchLog) throws ApplicationException {
		Map<String, String> model = new HashMap<String, String>();
		model.put("studentName", studentAcademicYear.getStudent().getDisplayName());
		model.put("emailAddress", studentAcademicYear.getStudent().getAddress().getEmail());
		String emailText = batchLog.getMessage();
		if (emailText == null || emailText.trim().isEmpty()) {
			emailText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, VELOCITY_TEMPLATE_PATH, model);
		}
		return emailText;
	}

}
