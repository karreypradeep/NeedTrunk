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

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.core.model.SMSProvider;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.need.notifications.model.BatchLog;
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
	public NotificationMessage sendSMS(final SMSProvider sMSProvider, final StudentAcademicYear studentAcademicYear, final BatchLog batchLog)
			throws ClientProtocolException, URISyntaxException, IOException {
		NotificationMessage notificationMessage = new NotificationMessage();
		UniversalSMSProvider universalSMSProvider = new UniversalSMSProvider(sMSProvider);
		String smsText = batchLog.getMessage();
		if (studentAcademicYear.getStudent().getAddress().getContactNumber() != null) {
			notificationMessage.setSentAddress(studentAcademicYear.getStudent().getAddress().getContactNumber());
			String smsReturnTest = universalSMSProvider.sendSMS(new String[] { studentAcademicYear.getStudent().getAddress().getContactNumber() }, smsText);
			if (smsReturnTest.toLowerCase().contains(sMSProvider.getSuccessString().toLowerCase())) {
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
