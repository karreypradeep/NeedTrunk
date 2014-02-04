/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.notifications.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.academics.model.ReportCardExam;
import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.academics.model.StudentExamSubject;
import com.apeironsol.need.academics.service.SectionExamService;
import com.apeironsol.need.academics.service.StudentExamSubjectService;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Attendance;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.StudentAbsent;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.core.service.AttendanceService;
import com.apeironsol.need.core.service.BranchService;
import com.apeironsol.need.core.service.KlassService;
import com.apeironsol.need.core.service.SectionService;
import com.apeironsol.need.core.service.StudentAbsentService;
import com.apeironsol.need.core.service.StudentService;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.notifications.model.BatchLogMessage;
import com.apeironsol.need.notifications.producer.util.NotificationProducerUtil;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.BatchStatusConstant;
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;
import com.apeironsol.need.util.constants.NotificationTypeConstant;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {

	@Resource(name = "jmsTemplateSMS")
	JmsTemplate							jmsTemplateSMS;

	@Resource(name = "smsDestination")
	Destination							smsDestination;

	@Resource(name = "jmsTemplateEmail")
	JmsTemplate							jmsTemplateEmail;

	@Resource(name = "emailDestination")
	Destination							emailDestination;

	@Resource(name = "jmsConnectionFactory")
	ConnectionFactory					jmsConnectionFactory;

	@Autowired
	private BranchService				branchService;

	@Autowired
	private BatchLogService				batchLogService;

	@Autowired
	private BatchLogMessageService		batchLogMessageService;

	@Autowired
	private StudentService				studentService;

	@Autowired
	private KlassService				klassService;

	@Autowired
	private SectionService				sectionService;

	@Autowired
	private AttendanceService			attendanceService;

	@Autowired
	private StudentAbsentService		studentAbsentService;

	@Autowired
	private SectionExamService			sectionExamService;

	@Autowired
	private StudentExamSubjectService	studentExamSubjectService;

	private NotificationProducerUtil createNotificationProducerUtil(final NotificationTypeConstant notificationTypeConstant) {
		final NotificationProducerUtil notificationProducerUtil = new NotificationProducerUtil();
		notificationProducerUtil.setBatchLogService(this.batchLogService);
		if (NotificationTypeConstant.SMS_NOTIFICATION.equals(notificationTypeConstant)) {
			notificationProducerUtil.setDestination(this.smsDestination);
		} else {
			notificationProducerUtil.setDestination(this.emailDestination);
		}
		notificationProducerUtil.setJmsConnectionFactory(this.jmsConnectionFactory);
		return notificationProducerUtil;
	}

	@Override
	public BatchLog sendNotificationForStudent(final StudentAcademicYear studentAcademicYear, final BatchLog batchLog) throws ApplicationException {
		BatchLog result = batchLog;
		if (result == null) {
			throw new ApplicationException("BatchLog cannot be null");
		}

		final NotificationProducerUtil notificationProducerUtil = createNotificationProducerUtil(batchLog.getNotificationTypeConstant());
		if (result.getId() == null) {
			result.setNrElements(Long.valueOf(1));
			result.setBatchStatusConstant(result.getNrElements() > 0 ? BatchStatusConstant.CREATED : BatchStatusConstant.FINISHED);
			result.setCompletedIndicator(result.getNrElements() > 0 ? false : true);
			if (result.getNrElements() == 0) {
				result.setExecutionTime(Long.valueOf(0));
			}
			result = notificationProducerUtil.createBatchLog(result);
		}
		notificationProducerUtil.sendNotificationJMS(studentAcademicYear, result);
		return result;
	}

	@Override
	public BatchLog sendNotificationForStudent(final Collection<StudentAcademicYear> studentAcademicYears, final BatchLog batchLog) throws ApplicationException {
		BatchLog result = batchLog;
		if (result == null) {
			throw new ApplicationException("BatchLog cannot be null");
		}
		final NotificationProducerUtil notificationProducerUtil = createNotificationProducerUtil(batchLog.getNotificationTypeConstant());
		if (result.getId() == null) {
			result.setNrElements(Long.valueOf(studentAcademicYears.size()));
			result.setBatchStatusConstant(result.getNrElements() > 0 ? BatchStatusConstant.CREATED : BatchStatusConstant.FINISHED);
			result.setCompletedIndicator(result.getNrElements() > 0 ? false : true);
			if (result.getNrElements() == 0) {
				result.setExecutionTime(Long.valueOf(0));
			}
			result = notificationProducerUtil.createBatchLog(result);
		}
		if (studentAcademicYears.size() > 0) {
			notificationProducerUtil.sendNotificationAsBatch(studentAcademicYears, result);
		}
		return result;
	}

	@Override
	public BatchLog sendNotificationForStudent(final Section section, final BatchLog batchLog) throws ApplicationException {
		BatchLog result = batchLog;
		if (result == null) {
			throw new ApplicationException("BatchLog cannot be null");
		}
		final NotificationProducerUtil notificationProducerUtil = createNotificationProducerUtil(batchLog.getNotificationTypeConstant());
		final Collection<Section> sections = new ArrayList<Section>();
		sections.add(section);
		if (batchLog.getNotificationSubTypeConstant().equals(NotificationSubTypeConstant.EXAM_ABSENT_NOTIFICATION)) {
			result = sendExamAbsentNotification(sections, batchLog);
		} else if (batchLog.getNotificationSubTypeConstant().equals(NotificationSubTypeConstant.EXAM_SCHEDULE_NOTIFICATION)) {
			result = sendExamScheduleNotification(sections, batchLog);
		} else if (batchLog.getNotificationSubTypeConstant().equals(NotificationSubTypeConstant.EXAM_RESULT_NOTIFICATION)) {
			result = sendExamResultNotification(sections, batchLog);
		} else {
			final Collection<StudentAcademicYear> studentAcademicYears = getStudentAcademicYearsToSendNotification(sections, batchLog);
			if (result.getId() == null) {
				result.setNrElements(Long.valueOf(studentAcademicYears.size()));
				result.setBatchStatusConstant(result.getNrElements() > 0 ? BatchStatusConstant.CREATED : BatchStatusConstant.FINISHED);
				result.setCompletedIndicator(result.getNrElements() > 0 ? false : true);
				if (result.getNrElements() == 0) {
					result.setExecutionTime(Long.valueOf(0));
				}
				result = notificationProducerUtil.createBatchLog(result);
			}
			if (studentAcademicYears.size() > 0) {
				notificationProducerUtil.sendNotificationAsBatch(studentAcademicYears, result);
			}
		}

		return result;
	}

	@Override
	public BatchLog sendNotificationForStudent(final Long academicYearId, final Klass klass, final BatchLog batchLog) throws ApplicationException {

		BatchLog result = batchLog;
		if (result == null) {
			throw new ApplicationException("BatchLog cannot be null");
		}
		final NotificationProducerUtil notificationProducerUtil = createNotificationProducerUtil(batchLog.getNotificationTypeConstant());
		final Collection<Section> sections = this.sectionService.findActiveSectionsByKlassIdAndAcademicYearId(klass.getId(), academicYearId);
		if (batchLog.getNotificationSubTypeConstant().equals(NotificationSubTypeConstant.EXAM_ABSENT_NOTIFICATION)) {
			result = sendExamAbsentNotification(sections, batchLog);
		} else if (batchLog.getNotificationSubTypeConstant().equals(NotificationSubTypeConstant.EXAM_SCHEDULE_NOTIFICATION)) {
			result = sendExamScheduleNotification(sections, batchLog);
		} else if (batchLog.getNotificationSubTypeConstant().equals(NotificationSubTypeConstant.EXAM_RESULT_NOTIFICATION)) {
			result = sendExamResultNotification(sections, batchLog);
		} else {
			final Collection<StudentAcademicYear> studentAcademicYears = getStudentAcademicYearsToSendNotification(sections, batchLog);

			if (result.getId() == null) {
				result.setNrElements(Long.valueOf(studentAcademicYears.size()));
				result.setBatchStatusConstant(result.getNrElements() > 0 ? BatchStatusConstant.CREATED : BatchStatusConstant.FINISHED);
				result.setCompletedIndicator(result.getNrElements() > 0 ? false : true);
				if (result.getNrElements() == 0) {
					result.setExecutionTime(Long.valueOf(0));
				}
				result = notificationProducerUtil.createBatchLog(result);
			}
			if (studentAcademicYears.size() > 0) {
				notificationProducerUtil.sendNotificationAsBatch(studentAcademicYears, result);
			}
		}
		return result;
	}

	@Override
	public BatchLog sendNotificationForStudent(final Long academicYearId, final BatchLog batchLog) throws ApplicationException {
		BatchLog result = batchLog;
		if (result == null) {
			throw new ApplicationException("BatchLog cannot be null");
		}
		final NotificationProducerUtil notificationProducerUtil = createNotificationProducerUtil(batchLog.getNotificationTypeConstant());
		final Collection<Long> klasseIDs = new ArrayList<Long>();
		final Collection<Klass> klasses = this.klassService.findActiveKlassesByBranchId(batchLog.getBranch().getId());
		for (final Klass klass : klasses) {
			klasseIDs.add(klass.getId());
		}
		final Collection<Section> sections = this.sectionService.findActiveSectionsByKlassIdsAndAcademicYearId(klasseIDs, academicYearId);

		if (batchLog.getNotificationSubTypeConstant().equals(NotificationSubTypeConstant.EXAM_ABSENT_NOTIFICATION)) {
			result = sendExamAbsentNotification(sections, batchLog);
		} else if (batchLog.getNotificationSubTypeConstant().equals(NotificationSubTypeConstant.EXAM_SCHEDULE_NOTIFICATION)) {
			result = sendExamScheduleNotification(sections, batchLog);
		} else if (batchLog.getNotificationSubTypeConstant().equals(NotificationSubTypeConstant.EXAM_RESULT_NOTIFICATION)) {
			result = sendExamResultNotification(sections, batchLog);
		} else {
			final Collection<StudentAcademicYear> studentAcademicYears = getStudentAcademicYearsToSendNotification(sections, batchLog);

			if (result.getId() == null) {
				result.setNrElements(Long.valueOf(studentAcademicYears.size()));
				result.setBatchStatusConstant(result.getNrElements() > 0 ? BatchStatusConstant.CREATED : BatchStatusConstant.FINISHED);
				result.setCompletedIndicator(result.getNrElements() > 0 ? false : true);
				if (result.getNrElements() == 0) {
					result.setExecutionTime(Long.valueOf(0));
				}
				result = notificationProducerUtil.createBatchLog(result);
			}
			if (studentAcademicYears.size() > 0) {
				notificationProducerUtil.sendNotificationAsBatch(studentAcademicYears, result);
			}
		}
		return result;
	}

	@Override
	public BatchLog sendNotification(final BatchLog batchLog) throws ApplicationException {
		BatchLog result = batchLog;
		if (result == null) {
			throw new ApplicationException("BatchLog cannot be null");
		}
		final NotificationProducerUtil notificationProducerUtil = createNotificationProducerUtil(batchLog.getNotificationTypeConstant());
		if (result.getId() == null) {
			result = notificationProducerUtil.createBatchLog(result);
		}
		notificationProducerUtil.sendNotificationJMS(result);
		return result;
	}

	@Override
	public BatchLog sendNotificationForStudent(final BatchLog batchLog, final Collection<Section> sections) throws ApplicationException {
		BatchLog result = batchLog;
		if (result == null) {
			throw new ApplicationException("BatchLog cannot be null");
		}
		final NotificationProducerUtil notificationProducerUtil = createNotificationProducerUtil(batchLog.getNotificationTypeConstant());
		final Collection<StudentAcademicYear> studentAcademicYears = getStudentAcademicYearsToSendNotification(sections, batchLog);
		if (result.getId() == null) {
			result.setNrElements(Long.valueOf(studentAcademicYears.size()));
			result.setBatchStatusConstant(result.getNrElements() > 0 ? BatchStatusConstant.CREATED : BatchStatusConstant.FINISHED);
			result.setCompletedIndicator(result.getNrElements() > 0 ? false : true);
			if (result.getNrElements() == 0) {
				result.setExecutionTime(Long.valueOf(0));
			}
			result = notificationProducerUtil.createBatchLog(result);
		}
		if (studentAcademicYears.size() > 0) {
			notificationProducerUtil.sendNotificationAsBatch(studentAcademicYears, result);
		}
		return result;
	}

	/**
	 * Returns student academic year for which notifications have to be sent.
	 * Students with status active are retrieved.
	 * 
	 * @param sections
	 * @param batchLog
	 * @return
	 */
	private Collection<StudentAcademicYear> getStudentAcademicYearsToSendNotification(final Collection<Section> sections, final BatchLog batchLog) {
		Collection<StudentAcademicYear> studentAcademicYears = null;
		Collection<Long> alreadySendAbsentStudentAcademicYears = null;
		if (batchLog.getNotificationSubTypeConstant().equals(NotificationSubTypeConstant.ABSENT_NOTIFICATION)) {
			// Check if absent message has already been send. If yes then skip
			// the meessage to those student for whom message has already been
			// send.
			final Collection<BatchLog> attendanceBatchLogs = this.batchLogService.findBatchLogsForAttendanceDate(batchLog.getBranch().getId(),
					batchLog.getAttendanceDate());
			alreadySendAbsentStudentAcademicYears = getAlreadySendAbsentStudentAcademicYearIds(attendanceBatchLogs);

			studentAcademicYears = new ArrayList<StudentAcademicYear>();
			for (final Section section : sections) {
				final Attendance attendance = this.attendanceService.findAttendanceBySectionIdAndAttendanceDateForDailyAttendance(section.getId(),
						batchLog.getAttendanceDate());
				if (attendance != null) {
					final Collection<StudentAbsent> studentAbsents = this.studentAbsentService.findStudentAttendanceByAttendance(attendance);
					for (final StudentAbsent studentAbsent : studentAbsents) {
						// Check if absent message has already been send. If yes
						// then skip the meessage to those student for whom
						// message has already been send.
						if (alreadySendAbsentStudentAcademicYears != null) {
							// Send notification only if not send earlier
							if (!alreadySendAbsentStudentAcademicYears.contains(studentAbsent.getStudentAcademicYear().getId())) {
								studentAcademicYears.add(studentAbsent.getStudentAcademicYear());
							}
						} else {
							studentAcademicYears.add(studentAbsent.getStudentAcademicYear());
						}

					}
				}
			}

		} else {
			final Collection<Long> sectionIDs = new ArrayList<Long>();
			for (final Section section : sections) {
				sectionIDs.add(section.getId());
			}
			studentAcademicYears = this.studentService.findStudentAcademicYearsWithActiveStatusBySectionIds(sectionIDs);
		}
		return studentAcademicYears;
	}

	@Override
	public BatchLog sendReportCardNotificationForStudent(final BatchLog batchLog) throws ApplicationException {
		BatchLog result = batchLog;
		if (result == null) {
			throw new ApplicationException("BatchLog cannot be null");
		}
		final Collection<Long> examIds = new ArrayList<Long>();
		for (final ReportCardExam reportCardExam : batchLog.getReportCard().getReportCardExams()) {
			examIds.add(reportCardExam.getExam().getId());
		}
		final Collection<SectionExam> sectionExams = this.sectionExamService.findSectionExamsByExamIdsandAcademicYearId(examIds, batchLog.getReportCard()
				.getAcademicYear().getId());
		final Collection<Section> sections = new ArrayList<Section>();
		for (final SectionExam sectionExam : sectionExams) {
			sections.add(sectionExam.getSection());
		}
		result = this.sendNotificationForStudent(batchLog, sections);
		return result;
	}

	/**
	 * Send notifications for students who are absent for the exam.
	 * 
	 * @param sections
	 * @param batchLog
	 * @return
	 * @throws ApplicationException
	 */
	private BatchLog sendExamAbsentNotification(final Collection<Section> sections, final BatchLog batchLog) throws ApplicationException {
		BatchLog result = batchLog;
		final Collection<Long> listOfIDs = new ArrayList<Long>();
		AcademicYear notificationSendForAcademicYear = null;
		Collection<Long> alreadySendAbsentStudentAcademicYears = null;
		for (final Section section : sections) {
			if (notificationSendForAcademicYear == null) {
				notificationSendForAcademicYear = section.getAcademicYear();
			}
			listOfIDs.add(section.getId());
		}
		if (batchLog.getExam() == null) {
			result.setBatchStatusConstant(BatchStatusConstant.CANCELLED);
			result.setNrElements(0l);
			result = this.batchLogService.saveBatchLog(result);
			return result;
		}
		final Collection<SectionExam> sectionExams = this.sectionExamService.findSectionExamsBySectionIdsAndExamId(listOfIDs, batchLog.getExam().getId());
		listOfIDs.clear();
		for (final SectionExam sectionExam : sectionExams) {
			if (notificationSendForAcademicYear == null) {
				notificationSendForAcademicYear = sectionExam.getSection().getAcademicYear();
			}
			listOfIDs.add(sectionExam.getId());
		}

		if (notificationSendForAcademicYear != null) {
			final Collection<BatchLog> attendanceBatchLogs = this.batchLogService.findBatchLogsForExamAndNotificationAcademicYear(batchLog.getExam().getId(),
					notificationSendForAcademicYear.getId(), NotificationSubTypeConstant.EXAM_ABSENT_NOTIFICATION);
			alreadySendAbsentStudentAcademicYears = getAlreadySendAbsentStudentAcademicYearIds(attendanceBatchLogs);
		}
		final Collection<StudentExamSubject> studentExamSubjects = this.studentExamSubjectService.findAbsentStudentSubjectsForSectionExamIds(listOfIDs);
		final Map<Long, StudentAcademicYear> studentAcademicYearMap = new HashMap<Long, StudentAcademicYear>();
		for (final StudentExamSubject studentExamSubject : studentExamSubjects) {
			// Check if absent message has already been send. If yes
			// then skip the message to those student for whom
			// message has already been send.
			if (alreadySendAbsentStudentAcademicYears != null) {
				if (!alreadySendAbsentStudentAcademicYears.contains(studentExamSubject.getStudentAcademicYear().getId())) {
					studentAcademicYearMap.put(studentExamSubject.getStudentAcademicYear().getId(), studentExamSubject.getStudentAcademicYear());
				}
			} else {
				studentAcademicYearMap.put(studentExamSubject.getStudentAcademicYear().getId(), studentExamSubject.getStudentAcademicYear());
			}

		}

		final NotificationProducerUtil notificationProducerUtil = createNotificationProducerUtil(batchLog.getNotificationTypeConstant());
		if (result.getId() == null) {
			result.setNrElements(Long.valueOf(studentAcademicYearMap.values().size()));
			result.setBatchStatusConstant(result.getNrElements() > 0 ? BatchStatusConstant.CREATED : BatchStatusConstant.FINISHED);
			result.setCompletedIndicator(result.getNrElements() > 0 ? false : true);
			result.setNotificationSendForAcademicYear(notificationSendForAcademicYear);
			if (result.getNrElements() == 0) {
				result.setExecutionTime(Long.valueOf(0));
			}
			result = notificationProducerUtil.createBatchLog(result);
			if (studentAcademicYearMap.values().size() > 0) {
				notificationProducerUtil.sendNotificationAsBatch(studentAcademicYearMap.values(), result);
			}
		}
		return result;
	}

	/**
	 * Send exam notification to students of scheduled sections.
	 * 
	 * @param sections
	 * @param batchLog
	 * @return
	 * @throws ApplicationException
	 */
	private BatchLog sendExamScheduleNotification(final Collection<Section> sections, final BatchLog batchLog) throws ApplicationException {
		BatchLog result = batchLog;
		final Collection<Long> listOfIDs = new ArrayList<Long>();
		for (final Section section : sections) {
			listOfIDs.add(section.getId());
		}
		if (batchLog.getExam() == null) {
			result.setBatchStatusConstant(BatchStatusConstant.CANCELLED);
			result.setNrElements(0l);
			result = this.batchLogService.saveBatchLog(result);
			return result;
		}

		final Collection<SectionExam> sectionExams = this.sectionExamService.findSectionExamsBySectionIdsAndExamId(listOfIDs, batchLog.getExam().getId());
		listOfIDs.clear();
		AcademicYear notificationSendForAcademicYear = null;
		Collection<Long> alreadySendAbsentStudentAcademicYears = null;
		for (final SectionExam sectionExam : sectionExams) {
			if (notificationSendForAcademicYear == null) {
				notificationSendForAcademicYear = sectionExam.getSection().getAcademicYear();
			}
			if (!sectionExam.getStartDate().before(DateUtil.getSystemDate())) {
				listOfIDs.add(sectionExam.getId());
			}
		}
		if (notificationSendForAcademicYear != null) {
			final Collection<BatchLog> attendanceBatchLogs = this.batchLogService.findBatchLogsForExamAndNotificationAcademicYear(batchLog.getExam().getId(),
					notificationSendForAcademicYear.getId(), NotificationSubTypeConstant.EXAM_SCHEDULE_NOTIFICATION);
			alreadySendAbsentStudentAcademicYears = getAlreadySendAbsentStudentAcademicYearIds(attendanceBatchLogs);
		}
		final Collection<StudentExamSubject> studentExamSubjects = this.studentExamSubjectService.findAllStudentExamSubjectsForSectionExamIds(listOfIDs);
		final Map<Long, StudentAcademicYear> studentAcademicYearMap = new HashMap<Long, StudentAcademicYear>();
		for (final StudentExamSubject studentExamSubject : studentExamSubjects) {
			// Check if absent message has already been send. If yes
			// then skip the message to those student for whom
			// message has already been send.
			if (alreadySendAbsentStudentAcademicYears != null) {
				if (!alreadySendAbsentStudentAcademicYears.contains(studentExamSubject.getStudentAcademicYear().getId())) {
					studentAcademicYearMap.put(studentExamSubject.getStudentAcademicYear().getId(), studentExamSubject.getStudentAcademicYear());
				}
			} else {
				studentAcademicYearMap.put(studentExamSubject.getStudentAcademicYear().getId(), studentExamSubject.getStudentAcademicYear());
			}
		}

		final NotificationProducerUtil notificationProducerUtil = createNotificationProducerUtil(batchLog.getNotificationTypeConstant());
		if (result.getId() == null) {
			result.setNrElements(Long.valueOf(studentAcademicYearMap.values().size()));
			result.setBatchStatusConstant(result.getNrElements() > 0 ? BatchStatusConstant.CREATED : BatchStatusConstant.FINISHED);
			result.setCompletedIndicator(result.getNrElements() > 0 ? false : true);
			result.setNotificationSendForAcademicYear(notificationSendForAcademicYear);
			if (result.getNrElements() == 0) {
				result.setExecutionTime(Long.valueOf(0));
			}
			result = notificationProducerUtil.createBatchLog(result);
			if (studentAcademicYearMap.values().size() > 0) {
				notificationProducerUtil.sendNotificationAsBatch(studentAcademicYearMap.values(), result);
			}
		}
		return result;
	}

	/**
	 * Send exam notification to students of scheduled sections.
	 * 
	 * @param sections
	 * @param batchLog
	 * @return
	 * @throws ApplicationException
	 */
	private BatchLog sendExamResultNotification(final Collection<Section> sections, final BatchLog batchLog) throws ApplicationException {
		BatchLog result = batchLog;
		try {
			final Collection<Long> listOfIDs = new ArrayList<Long>();
			for (final Section section : sections) {
				listOfIDs.add(section.getId());
			}
			if (batchLog.getExam() == null) {
				result.setBatchStatusConstant(BatchStatusConstant.CANCELLED);
				result.setNrElements(0l);
				result = this.batchLogService.saveBatchLog(result);
				return result;
			}

			final Collection<SectionExam> sectionExams = this.sectionExamService.findSectionExamsBySectionIdsAndExamId(listOfIDs, batchLog.getExam().getId());
			listOfIDs.clear();
			AcademicYear notificationSendForAcademicYear = null;
			Collection<Long> alreadySendAbsentStudentAcademicYears = null;
			for (final SectionExam sectionExam : sectionExams) {
				if (notificationSendForAcademicYear == null) {
					notificationSendForAcademicYear = sectionExam.getSection().getAcademicYear();
				}
				listOfIDs.add(sectionExam.getId());
			}
			if (notificationSendForAcademicYear != null) {
				final Collection<BatchLog> attendanceBatchLogs = this.batchLogService.findBatchLogsForExamAndNotificationAcademicYear(batchLog.getExam()
						.getId(), notificationSendForAcademicYear.getId(), NotificationSubTypeConstant.EXAM_RESULT_NOTIFICATION);
				alreadySendAbsentStudentAcademicYears = getAlreadySendAbsentStudentAcademicYearIds(attendanceBatchLogs);
			}
			final Collection<StudentExamSubject> studentExamSubjects = this.studentExamSubjectService.findAllStudentExamSubjectsForSectionExamIds(listOfIDs);
			final Map<Long, StudentAcademicYear> studentAcademicYearMap = new HashMap<Long, StudentAcademicYear>();
			for (final StudentExamSubject studentExamSubject : studentExamSubjects) {
				// Check if absent message has already been send. If yes
				// then skip the message to those student for whom
				// message has already been send.
				if (alreadySendAbsentStudentAcademicYears != null) {
					if (!alreadySendAbsentStudentAcademicYears.contains(studentExamSubject.getStudentAcademicYear().getId())) {
						studentAcademicYearMap.put(studentExamSubject.getStudentAcademicYear().getId(), studentExamSubject.getStudentAcademicYear());
					}
				} else {
					studentAcademicYearMap.put(studentExamSubject.getStudentAcademicYear().getId(), studentExamSubject.getStudentAcademicYear());
				}
			}

			final NotificationProducerUtil notificationProducerUtil = createNotificationProducerUtil(batchLog.getNotificationTypeConstant());
			if (result.getId() == null) {
				result.setNrElements(Long.valueOf(studentAcademicYearMap.values().size()));
				result.setBatchStatusConstant(result.getNrElements() > 0 ? BatchStatusConstant.CREATED : BatchStatusConstant.FINISHED);
				result.setCompletedIndicator(result.getNrElements() > 0 ? false : true);
				result.setNotificationSendForAcademicYear(notificationSendForAcademicYear);
				if (result.getNrElements() == 0) {
					result.setExecutionTime(Long.valueOf(0));
				}
				result = notificationProducerUtil.createBatchLog(result);
				if (studentAcademicYearMap.values().size() > 0) {
					notificationProducerUtil.sendNotificationAsBatch(studentAcademicYearMap.values(), result);
				}
			}
		} catch (final Exception e) {
			throw new ApplicationException("Could not send noifications. Exception occured: " + e.getMessage());
		}
		return result;
	}

	private Collection<Long> getAlreadySendAbsentStudentAcademicYearIds(final Collection<BatchLog> batchLogs) {
		final Collection<Long> alreadySendAbsentStudentAcademicYears = new ArrayList<Long>();
		if ((batchLogs != null) && (batchLogs.size() > 0)) {
			final Collection<Long> batchLogIDs = new ArrayList<Long>();
			for (final BatchLog batchLog : batchLogs) {
				batchLogIDs.add(batchLog.getId());
			}

			final Collection<BatchLogMessage> alreadySendAbsentNotoficationMessages = this.batchLogMessageService
					.findBatchLogMessagesByBatchLogIds(batchLogIDs);
			for (final BatchLogMessage batchLogMessage : alreadySendAbsentNotoficationMessages) {
				alreadySendAbsentStudentAcademicYears.add(batchLogMessage.getStudentAcademicYear().getId());
			}
		}
		return alreadySendAbsentStudentAcademicYears;
	}

}
