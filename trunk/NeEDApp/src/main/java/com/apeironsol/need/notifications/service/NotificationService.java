/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.notifications.service;

import java.util.Collection;

import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.framework.exception.ApplicationException;

public interface NotificationService {

	BatchLog sendNotificationForStudent(StudentAcademicYear studentAcademicYear, BatchLog batchLog) throws ApplicationException;

	BatchLog sendNotificationForStudent(Collection<StudentAcademicYear> studentAcademicYears, BatchLog batchLog) throws ApplicationException;

	BatchLog sendNotificationForStudent(final Section section, BatchLog batchLog) throws ApplicationException;

	BatchLog sendNotificationForStudent(Long academicYearId, Klass klass, BatchLog batchLog) throws ApplicationException;

	BatchLog sendNotificationForStudent(Long academicYearId, BatchLog batchLog) throws ApplicationException;

	BatchLog sendNotification(BatchLog batchLog) throws ApplicationException;
}
