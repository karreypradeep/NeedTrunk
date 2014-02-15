/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.consumers.worker.sms;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.mail.MessagingException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Component;

import com.apeironsol.framework.NeEDJMSObject;
import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.core.model.SMSProvider;
import com.apeironsol.need.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.need.notifications.providers.sms.UniversalSMSProvider;
import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;

/**
 * Class for sending email notification for student pending fee.
 * 
 * @author Pradeep
 */
@Component
public class AdhocSMSWorker implements SMSWorker {

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
		final String smsText = neEDJMSObject.getBatchLog().getMessage();
		if (neEDJMSObject.getStudentAcademicYear().getStudent().getAddress().getContactNumber() != null) {
			notificationMessage.setSentAddress(neEDJMSObject.getStudentAcademicYear().getStudent().getAddress().getContactNumber());
			final String smsReturnTest = universalSMSProvider.sendSMS(new String[] { neEDJMSObject.getStudentAcademicYear().getStudent().getAddress()
					.getContactNumber() }, smsText);
			if (smsReturnTest.toLowerCase().contains(sMSProvider.getSuccessString().toLowerCase())) {
				notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.SUCCESS);
				notificationMessage.setMessage(neEDJMSObject.getBatchLog().getMessage());
			} else {
				notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.FAILED);
				notificationMessage.setMessage(neEDJMSObject.getBatchLog().getMessage());
				notificationMessage.setErrorMessage(smsReturnTest);
			}
		} else {
			notificationMessage.setSentAddress("Not Available");
			notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
			notificationMessage.setMessage(neEDJMSObject.getBatchLog().getMessage());
			notificationMessage.setErrorMessage("Contact number not available");
		}
		return notificationMessage;
	}

	@Override
	public String getMessage(final NeEDJMSObject neEDJMSObject) throws ApplicationException {
		return neEDJMSObject.getBatchLog().getMessage();
	}
}
