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

import javax.mail.MessagingException;

import org.apache.http.client.ClientProtocolException;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.notifications.providers.sms.SMSProvider;
import com.apeironsol.need.notifications.providers.sms.SMSProviderFactory;
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
public class ExamScheduledSMSWorker implements SMSWorker {

	/**
	 * Velocity engine for compiling and merging text with velocity templates.
	 */
	@Autowired
	private VelocityEngine		velocityEngine;

	/**
	 * Velocity template path for notification.
	 */
	private static final String	VELOCITY_TEMPLATE_PATH	= "velocityTemplates/examScheduledSMSTemplate.vm";

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
	public NotificationMessage sendSMS(final String smsProviderName, final StudentAcademicYear studentAcademicYear, final BatchLog batchLog)
			throws ClientProtocolException, URISyntaxException, IOException {
		NotificationMessage notificationMessage = new NotificationMessage();
		SMSProvider smsProvider = SMSProviderFactory.getSMSWorker(smsProviderName == null ? "smscountry" : smsProviderName);
		String smsText = batchLog.getMessage();
		if (smsText == null || smsText.trim().isEmpty()) {
			Map<String, String> model = new HashMap<String, String>();
			model.put("studentName", studentAcademicYear.getStudent().getDisplayName());
			model.put("examName", batchLog.getSectionExam().getExam().getName());
			model.put("date", new SimpleDateFormat("dd/mm/yyyy").format(batchLog.getSectionExam().getStartDate()));
			smsText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, VELOCITY_TEMPLATE_PATH, model);
		}
		notificationMessage.setMessage(smsText);

		if (studentAcademicYear.getStudent().getAddress().getContactNumber() != null) {
			notificationMessage.setSentAddress(studentAcademicYear.getStudent().getAddress().getContactNumber());
			String smsReturnTest = smsProvider.sendSMS(new String[] { studentAcademicYear.getStudent().getAddress().getContactNumber() }, smsText);
			if (smsReturnTest.contains(":OK")) {
				notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.SUCCESS);
			} else {
				notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.FAILED);
				notificationMessage.setErrorMessage(smsReturnTest);
			}
		} else {
			notificationMessage.setSentAddress("Not Available");
			notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
			notificationMessage.setErrorMessage("Contact number not available");
		}
		return notificationMessage;
	}

	@Override
	public String getMessage(final StudentAcademicYear studentAcademicYear, final BatchLog batchLog) throws ApplicationException {
		String smsText = batchLog.getMessage();
		if (smsText == null || smsText.trim().isEmpty()) {
			Map<String, String> model = new HashMap<String, String>();
			model.put("studentName", studentAcademicYear.getStudent().getDisplayName());
			model.put("examName", batchLog.getSectionExam().getExam().getName());
			model.put("date", new SimpleDateFormat("dd/mm/yyyy").format(batchLog.getSectionExam().getStartDate()));
			smsText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, VELOCITY_TEMPLATE_PATH, model);
		}
		return smsText;
	}

}
