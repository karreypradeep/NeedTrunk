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

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.core.service.BranchService;
import com.apeironsol.need.core.service.KlassService;
import com.apeironsol.need.core.service.SectionService;
import com.apeironsol.need.core.service.StudentService;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.notifications.producer.util.NotificationProducerUtil;
import com.apeironsol.need.util.constants.BatchStatusConstant;
import com.apeironsol.need.util.constants.NotificationTypeConstant;
import com.apeironsol.framework.exception.ApplicationException;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {

	@Resource(name = "jmsTemplateSMS")
	JmsTemplate				jmsTemplateSMS;

	@Resource(name = "smsDestination")
	Destination				smsDestination;

	@Resource(name = "jmsTemplateEmail")
	JmsTemplate				jmsTemplateEmail;

	@Resource(name = "emailDestination")
	Destination				emailDestination;

	@Resource(name = "jmsConnectionFactory")
	ConnectionFactory		jmsConnectionFactory;

	@Autowired
	private BranchService	branchService;

	@Autowired
	private BatchLogService	batchLogService;

	@Autowired
	private StudentService	studentService;

	@Autowired
	private KlassService	klassService;

	@Autowired
	private SectionService	sectionService;

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
		if (batchLog == null) {
			throw new ApplicationException("BatchLog cannot be null");
		}
		final NotificationProducerUtil notificationProducerUtil = this.createNotificationProducerUtil(batchLog.getNotificationTypeConstant());
		if (result.getId() == null) {
			result.setNrElements(Long.valueOf(1));
			result.setBatchStatusConstant(result.getNrElements() > 0 ? BatchStatusConstant.CREATED : BatchStatusConstant.FINISHED);
			result.setCompletedIndicator(result.getNrElements() > 0 ? false : true);
			result = notificationProducerUtil.createBatchLog(result);
		}
		notificationProducerUtil.sendNotificationJMS(studentAcademicYear, result);
		return result;
	}

	@Override
	public BatchLog sendNotificationForStudent(final Collection<StudentAcademicYear> studentAcademicYears, final BatchLog batchLog) throws ApplicationException {
		BatchLog result = batchLog;
		if (batchLog == null) {
			throw new ApplicationException("BatchLog cannot be null");
		}
		final NotificationProducerUtil notificationProducerUtil = this.createNotificationProducerUtil(batchLog.getNotificationTypeConstant());
		if (result.getId() == null) {
			result.setNrElements(Long.valueOf(studentAcademicYears.size()));
			result.setBatchStatusConstant(result.getNrElements() > 0 ? BatchStatusConstant.CREATED : BatchStatusConstant.FINISHED);
			result.setCompletedIndicator(result.getNrElements() > 0 ? false : true);
			result = notificationProducerUtil.createBatchLog(result);
		}
		notificationProducerUtil.sendNotificationAsBatch(studentAcademicYears, result);
		return result;
	}

	@Override
	public BatchLog sendNotificationForStudent(final Section section, final BatchLog batchLog) throws ApplicationException {
		BatchLog result = batchLog;
		if (batchLog == null) {
			throw new ApplicationException("BatchLog cannot be null");
		}
		final NotificationProducerUtil notificationProducerUtil = this.createNotificationProducerUtil(batchLog.getNotificationTypeConstant());
		final Collection<StudentAcademicYear> studentAcademicYears = this.studentService.findStudentAcademicYearsWithActiveStatusBySectionId(section.getId());
		if (result.getId() == null) {
			result.setNrElements(Long.valueOf(studentAcademicYears.size()));
			result.setBatchStatusConstant(result.getNrElements() > 0 ? BatchStatusConstant.CREATED : BatchStatusConstant.FINISHED);
			result.setCompletedIndicator(result.getNrElements() > 0 ? false : true);
			result = notificationProducerUtil.createBatchLog(result);
		}
		notificationProducerUtil.sendNotificationAsBatch(studentAcademicYears, result);
		return result;
	}

	@Override
	public BatchLog sendNotificationForStudent(final Long academicYearId, final Klass klass, final BatchLog batchLog) throws ApplicationException {

		BatchLog result = batchLog;
		if (batchLog == null) {
			throw new ApplicationException("BatchLog cannot be null");
		}
		final NotificationProducerUtil notificationProducerUtil = this.createNotificationProducerUtil(batchLog.getNotificationTypeConstant());
		final Collection<Long> sectionIDs = new ArrayList<Long>();
		final Collection<Section> sections = this.sectionService.findActiveSectionsByKlassIdAndAcademicYearId(klass.getId(), academicYearId);
		for (final Section section : sections) {
			sectionIDs.add(section.getId());
		}
		final Collection<StudentAcademicYear> studentAcademicYears = this.studentService.findStudentAcademicYearsWithActiveStatusBySectionIds(sectionIDs);
		if (result.getId() == null) {
			result.setNrElements(Long.valueOf(studentAcademicYears.size()));
			result.setBatchStatusConstant(result.getNrElements() > 0 ? BatchStatusConstant.CREATED : BatchStatusConstant.FINISHED);
			result.setCompletedIndicator(result.getNrElements() > 0 ? false : true);
			result = notificationProducerUtil.createBatchLog(result);
		}
		notificationProducerUtil.sendNotificationAsBatch(studentAcademicYears, result);
		return result;
	}

	@Override
	public BatchLog sendNotificationForStudent(final Long academicYearId, final BatchLog batchLog) throws ApplicationException {
		BatchLog result = batchLog;
		if (batchLog == null) {
			throw new ApplicationException("BatchLog cannot be null");
		}
		final NotificationProducerUtil notificationProducerUtil = this.createNotificationProducerUtil(batchLog.getNotificationTypeConstant());
		final Collection<Long> klasseIDs = new ArrayList<Long>();
		final Collection<Klass> klasses = this.klassService.findActiveKlassesByBranchId(batchLog.getBranch().getId());
		for (final Klass klass : klasses) {
			klasseIDs.add(klass.getId());
		}
		final Collection<Section> sections = this.sectionService.findActiveSectionsByKlassIdsAndAcademicYearId(klasseIDs, academicYearId);
		final Collection<Long> sectionIDs = new ArrayList<Long>();
		for (final Section section : sections) {
			sectionIDs.add(section.getId());
		}
		final Collection<StudentAcademicYear> studentAcademicYears = this.studentService.findStudentAcademicYearsWithActiveStatusBySectionIds(sectionIDs);
		if (result.getId() == null) {
			result.setNrElements(Long.valueOf(studentAcademicYears.size()));
			result.setBatchStatusConstant(result.getNrElements() > 0 ? BatchStatusConstant.CREATED : BatchStatusConstant.FINISHED);
			result.setCompletedIndicator(result.getNrElements() > 0 ? false : true);
			result = notificationProducerUtil.createBatchLog(result);
		}

		notificationProducerUtil.sendNotificationAsBatch(studentAcademicYears, result);
		return result;
	}

	@Override
	public BatchLog sendNotification(final BatchLog batchLog) throws ApplicationException {
		BatchLog result = batchLog;
		if (batchLog == null) {
			throw new ApplicationException("BatchLog cannot be null");
		}
		final NotificationProducerUtil notificationProducerUtil = this.createNotificationProducerUtil(batchLog.getNotificationTypeConstant());
		if (result.getId() == null) {
			result = notificationProducerUtil.createBatchLog(result);
		}
		notificationProducerUtil.sendNotificationJMS(result);
		return result;
	}
}
