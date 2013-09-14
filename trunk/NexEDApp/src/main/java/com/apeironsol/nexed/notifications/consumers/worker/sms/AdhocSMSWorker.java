/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.nexed.notifications.consumers.worker.sms;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.mail.MessagingException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Component;

import com.apeironsol.nexed.core.model.StudentAcademicYear;
import com.apeironsol.nexed.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.nexed.notifications.model.BatchLog;
import com.apeironsol.nexed.notifications.providers.sms.SMSProvider;
import com.apeironsol.nexed.notifications.providers.sms.SMSProviderFactory;
import com.apeironsol.nexed.util.constants.BatchLogMessageStatusConstant;
import com.apeironsol.framework.exception.ApplicationException;

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
	public NotificationMessage sendSMS(final String smsProviderName, final StudentAcademicYear studentAcademicYear, final BatchLog batchLog)
			throws ClientProtocolException, URISyntaxException, IOException {
		NotificationMessage notificationMessage = new NotificationMessage();
		SMSProvider smsProvider = SMSProviderFactory.getSMSWorker(smsProviderName == null ? "smscountry" : smsProviderName);
		String smsText = batchLog.getMessage();
		if (studentAcademicYear.getStudent().getAddress().getContactNumber() != null) {
			notificationMessage.setSentAddress(studentAcademicYear.getStudent().getAddress().getContactNumber());
			String smsReturnTest = smsProvider.sendSMS(new String[] { studentAcademicYear.getStudent().getAddress().getContactNumber() }, smsText);
			if (smsReturnTest.contains(":OK")) {
				notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.SUCCESS);
				notificationMessage.setMessage(batchLog.getMessage());
			} else {
				notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.FAILED);
				notificationMessage.setMessage(batchLog.getMessage());
				notificationMessage.setErrorMessage(smsReturnTest);
			}
		} else {
			notificationMessage.setSentAddress("Not Available");
			notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
			notificationMessage.setMessage(batchLog.getMessage());
			notificationMessage.setErrorMessage("Contact number not available");
		}
		return notificationMessage;
	}

	@Override
	public String getMessage(final StudentAcademicYear studentAcademicYear, final BatchLog batchLog) throws ApplicationException {
		return batchLog.getMessage();
	}
}
