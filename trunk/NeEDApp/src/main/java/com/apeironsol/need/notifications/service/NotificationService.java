/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.notifications.service;

import java.util.Collection;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.notifications.model.BatchLog;

public interface NotificationService {

	BatchLog sendNotificationForStudentAdmission(Student student, BatchLog batchLog) throws ApplicationException;

	BatchLog sendNotificationForStudentAdmission(Collection<Student> students, BatchLog batchLog) throws ApplicationException;

	BatchLog sendNotificationForStudent(StudentAcademicYear studentAcademicYear, BatchLog batchLog) throws ApplicationException;

	BatchLog sendNotificationForStudent(Collection<StudentAcademicYear> studentAcademicYears, BatchLog batchLog) throws ApplicationException;

	BatchLog sendNotificationForStudent(final Section section, BatchLog batchLog) throws ApplicationException;

	BatchLog sendNotificationForStudent(Long academicYearId, Klass klass, BatchLog batchLog) throws ApplicationException;

	BatchLog sendNotificationForStudent(BatchLog batchLog, Collection<Section> sections) throws ApplicationException;

	BatchLog sendNotificationForStudent(Long academicYearId, BatchLog batchLog) throws ApplicationException;

	BatchLog sendReportCardNotificationForStudent(BatchLog batchLog) throws ApplicationException;

	BatchLog sendNotification(BatchLog batchLog) throws ApplicationException;
}
