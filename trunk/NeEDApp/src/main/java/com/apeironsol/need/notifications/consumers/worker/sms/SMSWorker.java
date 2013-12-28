package com.apeironsol.need.notifications.consumers.worker.sms;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.mail.MessagingException;

import org.apache.http.client.ClientProtocolException;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.core.model.SMSProvider;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.need.notifications.model.BatchLog;

public interface SMSWorker {

	/**
	 * Method for sending fee notification mails for student.
	 * 
	 * @param studentAcademicYear
	 *            studentAcademicYear.
	 * @throws MessagingException
	 */
	NotificationMessage sendSMS(final SMSProvider smsProvider, final StudentAcademicYear studentAcademicYear, final BatchLog batchLog)
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
