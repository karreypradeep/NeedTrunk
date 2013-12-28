/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.consumers.worker.sms;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.apache.http.client.ClientProtocolException;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.core.model.SMSProvider;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.financial.service.StudentFinancialService;
import com.apeironsol.need.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.notifications.providers.sms.UniversalSMSProvider;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;

/**
 * Class for sending email notification for student pending fee.
 * The scope of this bean is prototype as a new bean should be created for
 * sending email as t.
 * 
 * @author Pradeep
 */
@Component
public class FeeDueSMSWorker implements SMSWorker {

	/**
	 * Velocity engine for compiling and merging text with velocity templates.
	 */
	@Autowired
	private VelocityEngine			velocityEngine;

	@Resource
	private StudentFinancialService	studentFinancialService;

	/**
	 * Velocity template path for notification.
	 */
	private static final String		VELOCITY_TEMPLATE_PATH	= "velocityTemplates/studentFeeDueSMSTemplate.vm";

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
	public NotificationMessage sendSMS(final SMSProvider sMSProvider, final StudentAcademicYear studentAcademicYear, final BatchLog batchLog)
			throws ClientProtocolException, URISyntaxException, IOException {
		NotificationMessage notificationMessage = new NotificationMessage();
		UniversalSMSProvider universalSMSProvider = new UniversalSMSProvider(sMSProvider);
		Double feeDue = this.studentFinancialService.getStudentFeeDue(studentAcademicYear.getStudent(), studentAcademicYear.getAcademicYear(),
				DateUtil.getSystemDate());
		Map<String, String> model = new HashMap<String, String>();
		model.put("studentName", studentAcademicYear.getStudent().getDisplayName());
		model.put("feeDue", feeDue.toString());
		String smsText = batchLog.getMessage();
		if (smsText == null || smsText.trim().isEmpty()) {
			smsText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, VELOCITY_TEMPLATE_PATH, model);
		}
		notificationMessage.setMessage(smsText);
		if (studentAcademicYear.getStudent().getAddress().getContactNumber() != null) {
			if (feeDue > 0) {
				notificationMessage.setSentAddress(studentAcademicYear.getStudent().getAddress().getContactNumber());
				String smsReturnTest = universalSMSProvider.sendSMS(new String[] { studentAcademicYear.getStudent().getAddress().getContactNumber() }, smsText);
				if (smsReturnTest.toLowerCase().contains(sMSProvider.getSuccessString().toLowerCase())) {
					notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.SUCCESS);
				} else {
					notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.FAILED);
					notificationMessage.setErrorMessage(smsReturnTest);
				}
			} else {
				notificationMessage.setMessage("No fee Due");
				notificationMessage.setErrorMessage("No fee Due");
				notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
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
		Double feeDue = this.studentFinancialService.getStudentFeeDue(studentAcademicYear.getStudent(), studentAcademicYear.getAcademicYear(),
				DateUtil.getSystemDate());
		Map<String, String> model = new HashMap<String, String>();
		model.put("studentName", studentAcademicYear.getStudent().getDisplayName());
		model.put("feeDue", feeDue.toString());
		String smsText = batchLog.getMessage();
		if (smsText == null || smsText.trim().isEmpty()) {
			smsText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, VELOCITY_TEMPLATE_PATH, model);
		}
		return smsText;
	}

}
