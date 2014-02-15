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
import com.apeironsol.need.financial.model.StudentFeeTransaction;
import com.apeironsol.need.financial.service.StudentFinancialService;
import com.apeironsol.need.notifications.consumers.worker.util.EmailAndSMSUtil;
import com.apeironsol.need.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.need.notifications.providers.sms.UniversalSMSProvider;
import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;

/**
 * Class for sending email notification for student pending fee.
 * The scope of this bean is prototype as a new bean should be created for
 * sending email as t.
 * 
 * @author Pradeep
 */
@Component
public class FeePaidSMSWorker implements SMSWorker {

	/**
	 * Velocity engine for compiling and merging text with velocity templates.
	 */
	@Autowired
	private VelocityEngine			velocityEngine;

	/**
	 * Velocity template path for notification.
	 */
	private static final String		VELOCITY_TEMPLATE_PATH	= "velocityTemplates/studentFeePaidSMSTemplate.vm";

	@Resource
	private StudentFinancialService	studentFinancialService;

	/**
	 * Asynchronous method for sending fee pending notification mail for
	 * student.
	 * 
	 * @param student
	 *            student.
	 * @throws MessagingException
	 */
	@Override
	public NotificationMessage sendSMS(final NeEDJMSObject neEDJMSObject) throws ClientProtocolException, URISyntaxException, IOException {
		final NotificationMessage notificationMessage = new NotificationMessage();
		final SMSProvider sMSProvider = neEDJMSObject.getSmsProvider() != null ? neEDJMSObject.getSmsProvider() : neEDJMSObject.getBatchLog().getSmsProvider();
		final UniversalSMSProvider universalSMSProvider = new UniversalSMSProvider(sMSProvider);
		final StudentFeeTransaction studentFeeTransaction = this.studentFinancialService.retriveStudentFeeTransactionByTransactionNr(neEDJMSObject
				.getBatchLog().getStudentFeeTransactionNr());
		final Map<String, String> model = new HashMap<String, String>();
		model.put("studentName", neEDJMSObject.getStudentAcademicYear().getStudent().getDisplayName());
		model.put("amount", studentFeeTransaction.getAmount().toString());
		model.put("transactionNr", neEDJMSObject.getBatchLog().getStudentFeeTransactionNr());
		String smsText = neEDJMSObject.getBatchLog().getMessage();
		if ((smsText == null) || smsText.trim().isEmpty()) {
			smsText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, VELOCITY_TEMPLATE_PATH, model);
		}
		notificationMessage.setMessage(smsText);

		if (neEDJMSObject.getStudentAcademicYear().getStudent().getAddress().getContactNumber() != null) {
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
			notificationMessage.setSentAddress("Not Available");
			notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
			notificationMessage.setErrorMessage("Contact number not available");
		}
		return notificationMessage;
	}

	@Override
	public String getMessage(final NeEDJMSObject neEDJMSObject) throws ApplicationException {
		final StudentFeeTransaction studentFeeTransaction = this.studentFinancialService.retriveStudentFeeTransactionByTransactionNr(neEDJMSObject
				.getBatchLog().getStudentFeeTransactionNr());
		new EmailAndSMSUtil();
		final Map<String, String> model = new HashMap<String, String>();
		model.put("studentName", neEDJMSObject.getStudentAcademicYear().getStudent().getDisplayName());
		model.put("amount", studentFeeTransaction.getAmount().toString());
		model.put("transactionNr", neEDJMSObject.getBatchLog().getStudentFeeTransactionNr());
		String smsText = neEDJMSObject.getBatchLog().getMessage();
		if ((smsText == null) || smsText.trim().isEmpty()) {
			smsText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, VELOCITY_TEMPLATE_PATH, model);
		}
		return smsText;
	}

}
