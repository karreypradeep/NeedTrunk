package com.apeironsol.nexed.notifications.consumers.worker.email;

import javax.mail.MessagingException;

import com.apeironsol.nexed.core.model.StudentAcademicYear;
import com.apeironsol.nexed.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.nexed.notifications.model.BatchLog;
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
