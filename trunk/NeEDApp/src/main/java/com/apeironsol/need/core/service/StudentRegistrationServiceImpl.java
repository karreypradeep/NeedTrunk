/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */

package com.apeironsol.need.core.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.core.dao.StudentRegistrationDao;
import com.apeironsol.need.core.model.BranchRule;
import com.apeironsol.need.core.model.StudentRegistration;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.notifications.model.BranchNotification;
import com.apeironsol.need.notifications.producer.util.BatchLogBuilder;
import com.apeironsol.need.notifications.service.BatchLogService;
import com.apeironsol.need.notifications.service.BranchNotificationService;
import com.apeironsol.need.notifications.service.NotificationService;
import com.apeironsol.need.util.constants.BatchStatusConstant;
import com.apeironsol.need.util.constants.NotificationLevelConstant;
import com.apeironsol.need.util.constants.NotificationSentForConstant;
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;
import com.apeironsol.need.util.constants.NotificationTypeConstant;
import com.apeironsol.need.util.constants.StudentRegistrationStatusConstant;

/**
 * Service interface implementation for Batch.
 * 
 * @author Pradeep
 * 
 */
@Service("studentRegistrationService")
@Transactional(rollbackFor = Exception.class)
public class StudentRegistrationServiceImpl implements StudentRegistrationService {

	@Resource
	private StudentRegistrationDao	studentRegistrationDao;

	@Resource
	SequenceGeneratorService		sequenceGeneratorService;

	@Resource
	BranchNotificationService		branchNotificationService;

	@Resource
	NotificationService				notificationService;

	@Resource
	BatchLogService					batchLogService;

	@Resource
	BranchRuleService				branchRuleService;

	@Override
	public StudentRegistration saveStudentRegistration(final StudentRegistration studentRegistration) throws BusinessException {
		boolean sendNotification = false;
		StudentRegistration result = studentRegistration;
		if (studentRegistration.getRegistrationNr() == null) {
			final String registrationNr = this.sequenceGeneratorService.getNextRegistrationNumber(studentRegistration.getBranch().getCode());
			studentRegistration.setRegistrationNr(registrationNr);
			sendNotification = true;
		}
		result = this.studentRegistrationDao.persist(studentRegistration);
		result.setBranch(studentRegistration.getBranch());
		if (sendNotification) {
			try {
				final BranchNotification branchNotification = this.branchNotificationService.findBranchNotificationByBranchIdAnsNotificationSubType(
						studentRegistration.getBranch().getId(), NotificationSubTypeConstant.NEW_STUDENT_REGISTRATION_NOTIFICATION);
				if ((branchNotification != null) && branchNotification.getSmsIndicator()) {
					final BranchRule branchRule = this.branchRuleService.findBranchRuleByBranchId(result.getBranch().getId());
					BatchLog batchLog = new BatchLogBuilder().branch(studentRegistration.getBranch())
							.notificationLevelConstant(NotificationLevelConstant.STUDENT_REGISTRATION).notificationLevelId(result.getId())
							.notificationSubTypeConstant(NotificationSubTypeConstant.NEW_STUDENT_REGISTRATION_NOTIFICATION)
							.notificationSendForAcademicYear(studentRegistration.getAcademicYear())
							.notificationTypeConstant(NotificationTypeConstant.SMS_NOTIFICATION).nrElements(Long.valueOf(1))
							.notificationSentFor(NotificationSentForConstant.STUDENT_REGISTRATIONS).smsProvider(branchRule.getSmsProvider()).build();

					if (BatchStatusConstant.CREATED.equals(batchLog.getBatchStatusConstant())
							|| BatchStatusConstant.DISTRIBUTED.equals(batchLog.getBatchStatusConstant())) {
						this.notificationService.sendNotificationForStudentRegistration(result, batchLog);
					} else {
						batchLog = this.batchLogService.saveBatchLogInNewTransaction(batchLog);
					}
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public void removeStudentRegistration(final StudentRegistration studentRegistration) throws BusinessException {
		this.studentRegistrationDao.remove(studentRegistration);
	}

	@Override
	public StudentRegistration findStudentRegistrationById(final Long id) throws BusinessException {
		return this.studentRegistrationDao.findStudentRegistrationById(id);
	}

	@Override
	public Collection<StudentRegistration> findStudentRegistrationsByBranchId(final Long branchId) throws BusinessException {
		return this.studentRegistrationDao.findStudentRegistrationesByBranchId(branchId);
	}

	@Override
	public Collection<StudentRegistration> findStudentRegistrationsByAcademicYearId(final Long academicYearId) throws BusinessException {
		return this.studentRegistrationDao.findStudentRegistrationesByAcademicYearId(academicYearId);
	}

	@Override
	public Collection<StudentRegistration> findStudentRegistrationesByAcademicYearIdAndStatus(final Long academicYearId,
			final StudentRegistrationStatusConstant studentRegistrationStatus) throws BusinessException {
		return this.studentRegistrationDao.findStudentRegistrationesByAcademicYearIdAndStatus(academicYearId, studentRegistrationStatus);
	}

	@Override
	public Collection<StudentRegistration> findStudentRegistrationesByStudentRegistrationStatus(
			final StudentRegistrationStatusConstant studentRegistrationStatus) throws BusinessException {
		return this.studentRegistrationDao.findStudentRegistrationesByStudentRegistrationStatus(studentRegistrationStatus);
	}

}
