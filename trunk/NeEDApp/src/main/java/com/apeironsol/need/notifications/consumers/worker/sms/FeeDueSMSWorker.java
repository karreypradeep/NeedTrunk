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

import com.apeironsol.framework.NeEDJMSObject;
import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.core.model.SMSProvider;
import com.apeironsol.need.financial.service.StudentFinancialService;
import com.apeironsol.need.notifications.consumers.worker.util.NotificationMessage;
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
	public NotificationMessage sendSMS(final NeEDJMSObject neEDJMSObject) throws ClientProtocolException, URISyntaxException, IOException {
		final NotificationMessage notificationMessage = new NotificationMessage();
		final SMSProvider sMSProvider = neEDJMSObject.getSmsProvider() != null ? neEDJMSObject.getSmsProvider() : neEDJMSObject.getBatchLog().getSmsProvider();
		final UniversalSMSProvider universalSMSProvider = new UniversalSMSProvider(sMSProvider);
		final Double feeDue = this.studentFinancialService.getStudentFeeDue(neEDJMSObject.getStudentAcademicYear().getStudent(), neEDJMSObject
				.getStudentAcademicYear().getAcademicYear(), DateUtil.getSystemDate());
		final Map<String, String> model = new HashMap<String, String>();
		model.put("studentName", neEDJMSObject.getStudentAcademicYear().getStudent().getDisplayName());
		model.put("feeDue", feeDue.toString());
		String smsText = neEDJMSObject.getBatchLog().getMessage();
		if ((smsText == null) || smsText.trim().isEmpty()) {
			smsText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, VELOCITY_TEMPLATE_PATH, model);
		}
		notificationMessage.setMessage(smsText);
		if (neEDJMSObject.getStudentAcademicYear().getStudent().getAddress().getContactNumber() != null) {
			if (feeDue > 0) {
				notificationMessage.setSentAddress(neEDJMSObject.getStudentAcademicYear().getStudent().getAddress().getContactNumber());
				final String smsReturnTest = universalSMSProvider.sendSMS(new String[] { neEDJMSObject.getStudentAcademicYear().getStudent().getAddress()
						.getContactNumber() }, smsText);
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
	public String getMessage(final NeEDJMSObject neEDJMSObject) throws ApplicationException {
		final Double feeDue = this.studentFinancialService.getStudentFeeDue(neEDJMSObject.getStudentAcademicYear().getStudent(), neEDJMSObject
				.getStudentAcademicYear().getAcademicYear(), DateUtil.getSystemDate());
		final Map<String, String> model = new HashMap<String, String>();
		model.put("studentName", neEDJMSObject.getStudentAcademicYear().getStudent().getDisplayName());
		model.put("feeDue", feeDue.toString());
		String smsText = neEDJMSObject.getBatchLog().getMessage();
		if ((smsText == null) || smsText.trim().isEmpty()) {
			smsText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, VELOCITY_TEMPLATE_PATH, model);
		}
		return smsText;
	}

}
