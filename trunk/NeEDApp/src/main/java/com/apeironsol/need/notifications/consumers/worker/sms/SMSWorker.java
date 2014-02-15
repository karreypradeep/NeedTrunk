package com.apeironsol.need.notifications.consumers.worker.sms;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.mail.MessagingException;

import org.apache.http.client.ClientProtocolException;

import com.apeironsol.framework.NeEDJMSObject;
import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.notifications.consumers.worker.util.NotificationMessage;

public interface SMSWorker {

	/**
	 * Method for sending fee notification mails for student.
	 * 
	 * @param studentAcademicYear
	 *            studentAcademicYear.
	 * @throws MessagingException
	 */
	NotificationMessage sendSMS(final NeEDJMSObject neEDJMSObject) throws ClientProtocolException, URISyntaxException, IOException;

	/**
	 * Method for sending fee notification mails for student.
	 * 
	 * @param studentAcademicYear
	 *            studentAcademicYear.
	 * @throws MessagingException
	 */
	String getMessage(final NeEDJMSObject neEDJMSObject) throws ApplicationException;
}
