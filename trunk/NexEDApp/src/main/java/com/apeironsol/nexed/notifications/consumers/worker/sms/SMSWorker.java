package com.apeironsol.nexed.notifications.consumers.worker.sms;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.mail.MessagingException;

import org.apache.http.client.ClientProtocolException;

import com.apeironsol.nexed.core.model.StudentAcademicYear;
import com.apeironsol.nexed.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.nexed.notifications.model.BatchLog;
import com.apeironsol.framework.exception.ApplicationException;

public interface SMSWorker {

	/**
	 * Method for sending fee notification mails for student.
	 * 
	 * @param studentAcademicYear
	 *            studentAcademicYear.
	 * @throws MessagingException
	 */
	NotificationMessage sendSMS(final String smsProviderName, final StudentAcademicYear studentAcademicYear, final BatchLog batchLog)
			throws ClientProtocolException, URISyntaxException, IOException;

	/**
	 * Method for sending fee notification mails for student.
	 * 
	 * @param studentAcademicYear
	 *            studentAcademicYear.
	 * @throws MessagingException
	 */
	String getMessage(final StudentAcademicYear studentAcademicYear, final BatchLog batchLog) throws ApplicationException;
}
