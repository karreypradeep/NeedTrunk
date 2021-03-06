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
import com.apeironsol.need.core.model.AdmissionReservationFee;
import com.apeironsol.need.core.model.SMSProvider;
import com.apeironsol.need.core.service.AdmissionReservationFeeService;
import com.apeironsol.need.notifications.consumers.worker.util.EmailAndSMSUtil;
import com.apeironsol.need.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.need.notifications.providers.sms.UniversalSMSProvider;
import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;

/**
 * Class for sending email notification for new admission.
 * The scope of this bean is prototype as a new bean should be created for
 * sending email as t.
 * 
 * @author Pradeep
 */
@Component
public class NewAdmissionAcceptedSMSWorker implements SMSWorker {

	/**
	 * Velocity engine for compiling and merging text with velocity templates.
	 */
	@Autowired
	private VelocityEngine			velocityEngine;

	/**
	 * Velocity template path for notification.
	 */
	private static final String		VELOCITY_TEMPLATE_PATH								= "velocityTemplates/newAdmissionAcceptedSMSTemplate.vm";

	/**
	 * Velocity template path for notification. .vm
	 */
	private static final String		VELOCITY_TEMPLATE_PATH_WITH_RESER_APPL_FEE_DETAILS	= "velocityTemplates/newAdmissionAcceptedWithAppliAndReserFeeSMSTemplate.vm";

	private static final String		VELOCITY_TEMPLATE_PATH_WITH_RESER_FEE_DETAILS		= "velocityTemplates/newAdmissionAcceptedWithReserFeeSMSTemplate.vm";

	private static final String		VELOCITY_TEMPLATE_PATH_WITH_APPL_FORM_FEE_DETAILS	= "velocityTemplates/newAdmissionAcceptedWithApplFormFeeSMSTemplate.vm";

	@Resource
	AdmissionReservationFeeService	admissionReservationFeeService;

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
		final Map<String, String> model = new HashMap<String, String>();
		model.put("organizationName", neEDJMSObject.getStudent().getBranch().getName());
		model.put("studentName", neEDJMSObject.getStudent().getDisplayName());
		model.put("registrationNumber", neEDJMSObject.getStudent().getRegistrationNr());
		final AdmissionReservationFee admissionReservationFee = this.admissionReservationFeeService.findAdmissionReservationFeeByStudentID(neEDJMSObject
				.getStudent().getId());
		String smsText = neEDJMSObject.getBatchLog().getMessage();
		String template = VELOCITY_TEMPLATE_PATH;
		model.put("reservationAmount", (admissionReservationFee.getReservationFee() != null ? admissionReservationFee.getReservationFee() : 0) + "");
		model.put("applicationAmount", (admissionReservationFee.getApplicationFormFee() != null ? admissionReservationFee.getApplicationFormFee() : 0) + "");
		if (((admissionReservationFee.getReservationFee() != null) && (admissionReservationFee.getReservationFee() > 0))
				&& (((admissionReservationFee.getApplicationFeeNotificationSent() == null) || !admissionReservationFee.getApplicationFeeNotificationSent()) && ((admissionReservationFee
						.getApplicationFormFee() != null) || (admissionReservationFee.getApplicationFormFee() > 0)))) {
			template = VELOCITY_TEMPLATE_PATH_WITH_RESER_APPL_FEE_DETAILS;
		} else if ((admissionReservationFee.getReservationFee() != null) && (admissionReservationFee.getReservationFee() > 0)) {
			template = VELOCITY_TEMPLATE_PATH_WITH_RESER_FEE_DETAILS;
		} else if (((admissionReservationFee.getApplicationFeeNotificationSent() == null) || !admissionReservationFee.getApplicationFeeNotificationSent())
				&& ((admissionReservationFee.getApplicationFormFee() != null) || (admissionReservationFee.getApplicationFormFee() > 0))) {
			template = VELOCITY_TEMPLATE_PATH_WITH_APPL_FORM_FEE_DETAILS;
		}
		if ((smsText == null) || smsText.trim().isEmpty()) {
			smsText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, template, model);
		}
		notificationMessage.setMessage(smsText);

		if (neEDJMSObject.getStudent().getAddress().getContactNumber() != null) {
			notificationMessage.setSentAddress(neEDJMSObject.getStudent().getAddress().getContactNumber());
			final String smsReturnTest = universalSMSProvider.sendSMS(new String[] { neEDJMSObject.getStudent().getAddress().getContactNumber() }, smsText);
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
		new EmailAndSMSUtil();
		final Map<String, String> model = new HashMap<String, String>();
		model.put("organizationName", neEDJMSObject.getStudent().getBranch().getName());
		model.put("studentName", neEDJMSObject.getStudent().getDisplayName());
		model.put("registrationNumber", neEDJMSObject.getStudent().getRegistrationNr());
		final AdmissionReservationFee admissionReservationFee = this.admissionReservationFeeService.findAdmissionReservationFeeByStudentID(neEDJMSObject
				.getStudent().getId());
		String smsText = neEDJMSObject.getBatchLog().getMessage();
		String template = VELOCITY_TEMPLATE_PATH;
		model.put("amount", (admissionReservationFee.getReservationFee() != null ? admissionReservationFee.getReservationFee() : 0) + "");
		model.put("reservationAmount", (admissionReservationFee.getReservationFee() != null ? admissionReservationFee.getReservationFee() : 0) + "");
		model.put("applicationAmount", (admissionReservationFee.getApplicationFormFee() != null ? admissionReservationFee.getApplicationFormFee() : 0) + "");
		if (((admissionReservationFee.getReservationFee() != null) && (admissionReservationFee.getReservationFee() > 0))
				&& (((admissionReservationFee.getApplicationFeeNotificationSent() == null) || !admissionReservationFee.getApplicationFeeNotificationSent()) && ((admissionReservationFee
						.getApplicationFormFee() != null) || (admissionReservationFee.getApplicationFormFee() > 0)))) {
			template = VELOCITY_TEMPLATE_PATH_WITH_RESER_APPL_FEE_DETAILS;
		} else if ((admissionReservationFee.getReservationFee() != null) && (admissionReservationFee.getReservationFee() > 0)) {
			template = VELOCITY_TEMPLATE_PATH_WITH_RESER_FEE_DETAILS;
		} else if (((admissionReservationFee.getApplicationFeeNotificationSent() == null) || !admissionReservationFee.getApplicationFeeNotificationSent())
				&& ((admissionReservationFee.getApplicationFormFee() != null) || (admissionReservationFee.getApplicationFormFee() > 0))) {
			template = VELOCITY_TEMPLATE_PATH_WITH_APPL_FORM_FEE_DETAILS;
		}
		if ((smsText == null) || smsText.trim().isEmpty()) {
			smsText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, template, model);
		}
		return smsText;
	}

}
