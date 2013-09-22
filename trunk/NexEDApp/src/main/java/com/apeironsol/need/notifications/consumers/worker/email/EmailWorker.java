package com.apeironsol.need.notifications.consumers.worker.email;

import javax.mail.MessagingException;

import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.framework.exception.ApplicationException;

public interface EmailWorker {

	/**
	 * Method for sending fee notification mails for student.
	 * 
	 * @param studentAcademicYear
	 *            studentAcademicYear.
	 * @throws MessagingException
	 */
	NotificationMessage sendMail(final StudentAcademicYear studentAcademicYear, final BatchLog batchLog) throws MessagingException;

	/**
	 * Method for sending fee notification mails for student.
	 * 
	 * @param studentAcademicYear
	 *            studentAcademicYear.
	 * @throws MessagingException
	 */
	String getMessage(final StudentAcademicYear studentAcademicYear, final BatchLog batchLog) throws ApplicationException;
}
