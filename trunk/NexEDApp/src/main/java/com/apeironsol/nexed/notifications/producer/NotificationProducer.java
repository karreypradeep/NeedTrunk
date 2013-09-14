/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.notifications.producer;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.nexed.core.model.Klass;
import com.apeironsol.nexed.core.model.Section;
import com.apeironsol.nexed.core.model.StudentAcademicYear;
import com.apeironsol.nexed.core.model.StudentSection;
import com.apeironsol.nexed.notifications.model.BatchLog;
import com.apeironsol.nexed.notifications.producer.util.NotificationProducerUtil;
import com.apeironsol.nexed.util.constants.NotificationSubTypeConstant;
import com.apeironsol.framework.exception.ApplicationException;

public interface NotificationProducer {

	/**
	 * @return the notificationProducerUtil
	 */
	NotificationProducerUtil getNotificationProducerUtil();

	/**
	 * @param notificationProducerUtil
	 *            the notificationProducerUtil to set
	 */
	void setNotificationProducerUtil(NotificationProducerUtil notificationProducerUtil);

	/**
	 * 
	 * @param branchId
	 * @param studentAcademicYear
	 * @param batchLog
	 * @param notificationSubTypeConstant
	 * @param message
	 * @throws ApplicationException
	 */
	BatchLog sendNotificationForStudent(Long branchId, StudentAcademicYear studentAcademicYear, BatchLog batchLog,
			NotificationSubTypeConstant notificationSubTypeConstant, final String message) throws ApplicationException;

	/**
	 * 
	 * @param branchId
	 * @param studentAcademicYears
	 * @param batchLog
	 * @param notificationSubTypeConstant
	 * @param message
	 * @throws ApplicationException
	 */
	BatchLog sendNotificationForStudent(Long branchId, final Collection<StudentAcademicYear> studentAcademicYears, BatchLog batchLog,
			NotificationSubTypeConstant notificationSubTypeConstant, final String message) throws ApplicationException;

	/**
	 * 
	 * @param branchId
	 * @param studentAcademicYear
	 * @param batchLog
	 * @param notificationSubTypeConstant
	 * @param message
	 * @throws ApplicationException
	 */
	BatchLog sendNotificationForStudent(Section section, BatchLog batchLog) throws ApplicationException;

	/**
	 * 
	 * @param branchId
	 * @param studentAcademicYear
	 * @param batchLog
	 * @param notificationSubTypeConstant
	 * @param message
	 * @throws ApplicationException
	 */
	BatchLog sendNotificationForStudent(Long branchId, Long academicYearId, Klass klass, BatchLog batchLog,
			NotificationSubTypeConstant notificationSubTypeConstant, final String message) throws ApplicationException;

	/**
	 * 
	 * @param branchId
	 * @param studentAcademicYears
	 * @param batchLog
	 * @param notificationSubTypeConstant
	 * @param message
	 * @throws ApplicationException
	 */
	BatchLog sendNotificationForStudent(Long branchId, Long academicYearId, NotificationSubTypeConstant notificationSubTypeConstant, BatchLog batchLog,
			final String message) throws ApplicationException;

	/**
	 * 
	 * @param branchId
	 * @param studentAcademicYears
	 * @param batchLog
	 * @param notificationSubTypeConstant
	 * @param message
	 * @throws ApplicationException
	 */
	BatchLog sendNotification(Long branchId, NotificationSubTypeConstant notificationSubTypeConstant, BatchLog batchLog, final String message)
			throws ApplicationException;

	/**
	 * 
	 * @param branchId
	 * @param studentAcademicYear
	 * @param batchLog
	 * @throws ApplicationException
	 */
	BatchLog sendAbsentNotificationForStudent(Long branchId, StudentSection studentSection, BatchLog batchLog, Date attendanceDate) throws ApplicationException;

	/**
	 * 
	 * @param branchId
	 * @param section
	 * @param batchLog
	 * @throws ApplicationException
	 */
	BatchLog sendAbsentNotificationForSection(Long branchId, Section section, BatchLog batchLog, Date attendanceDate) throws ApplicationException;

	/**
	 * 
	 * @param branchId
	 * @param section
	 * @param batchLog
	 * @throws ApplicationException
	 */
	BatchLog sendAbsentNotificationForKlass(Long branchId, final Long academicYearId, Klass klass, BatchLog batchLog, Date attendanceDate)
			throws ApplicationException;
}
