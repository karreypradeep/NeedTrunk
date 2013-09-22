package com.apeironsol.need.notifications.providers.sms;

import javax.mail.MessagingException;

import com.apeironsol.framework.exception.ApplicationException;

public interface SMSProvider {

	/**
	 * Method for sending fee notification mails for student.
	 * 
	 * @param studentAcademicYear
	 *            studentAcademicYear.
	 * @throws MessagingException
	 */
	String sendSMS(final String[] phoneNumbers, final String message) throws ApplicationException;

}
