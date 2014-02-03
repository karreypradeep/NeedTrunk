/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.consumers.worker.sms;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.apache.http.client.ClientProtocolException;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.academics.model.Exam;
import com.apeironsol.need.academics.model.StudentExamSubject;
import com.apeironsol.need.academics.service.StudentExamSubjectService;
import com.apeironsol.need.core.model.SMSProvider;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.notifications.providers.sms.UniversalSMSProvider;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;

/**
 * Class for sending email notification for student pending fee.
 * The scope of this bean is prototype as a new bean should be created for
 * sending email as t.
 * 
 * @author Pradeep
 */
@Component
public class ExamScheduledSMSWorker implements SMSWorker {

	/**
	 * Velocity engine for compiling and merging text with velocity templates.
	 */
	@Autowired
	private VelocityEngine				velocityEngine;

	@Resource
	private StudentExamSubjectService	studentExamSubjectService;

	/**
	 * Velocity template path for notification.
	 */
	private static final String			VELOCITY_TEMPLATE_PATH	= "velocityTemplates/examScheduledSMSTemplate.vm";

	/**
	 * Asynchronous method for sending fee pending notification mail for
	 * student.
	 * 
	 * @param student
	 *            student.
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws MessagingException
	 */
	@Override
	public NotificationMessage sendSMS(final SMSProvider sMSProvider, final StudentAcademicYear studentAcademicYear, final BatchLog batchLog)
			throws ClientProtocolException, URISyntaxException, IOException {
		final NotificationMessage notificationMessage = new NotificationMessage();
		final UniversalSMSProvider universalSMSProvider = new UniversalSMSProvider(sMSProvider);
		String smsText = batchLog.getMessage();
		if ((smsText == null) || smsText.trim().isEmpty()) {
			final Map<String, String> model = new HashMap<String, String>();
			model.put("studentName", studentAcademicYear.getStudent().getDisplayName());
			final Exam exam = batchLog.getExam();
			if (exam != null) {
				model.put("examName", batchLog.getExam().getName());
			} else {
				notificationMessage.setSentAddress("Exam details not available.");
				notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
				notificationMessage.setErrorMessage("Exam is null in batch log.");
				return notificationMessage;
			}
			final Collection<StudentExamSubject> studentExamSubjects = this.studentExamSubjectService.findStudentExamSubjectsByStudentAcademicYearIdAndExamId(
					studentAcademicYear.getId(), exam.getId());
			String subjects = "";
			String scheduleStartDate = "";
			String scheduleEndDate = "";
			for (final StudentExamSubject studentExamSubject : studentExamSubjects) {
				if (scheduleStartDate.trim().length() == 0) {
					scheduleStartDate = DateUtil.getDateAsStringInDefaultFormat(studentExamSubject.getSectionExamSubject().getSectionExam().getStartDate());
					scheduleEndDate = DateUtil.getDateAsStringInDefaultFormat(studentExamSubject.getSectionExamSubject().getSectionExam().getEndDate());
				}
				subjects += (studentExamSubject.getSectionExamSubject().getSectionSubject().getSubject().getName() + " on "
						+ DateUtil.getDateAsStringInDefaultFormat(studentExamSubject.getSectionExamSubject().getScheduledDate()) + ",");
			}
			if (subjects.indexOf(",") > 0) {
				subjects = subjects.substring(0, subjects.lastIndexOf(","));
			}
			model.put("subjects", subjects);
			model.put("fromDate", scheduleStartDate);
			model.put("toDate", scheduleEndDate);
			smsText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, VELOCITY_TEMPLATE_PATH, model);
		}
		notificationMessage.setMessage(smsText);

		if (studentAcademicYear.getStudent().getAddress().getContactNumber() != null) {
			notificationMessage.setSentAddress(studentAcademicYear.getStudent().getAddress().getContactNumber());
			final String smsReturnTest = universalSMSProvider.sendSMS(new String[] { studentAcademicYear.getStudent().getAddress().getContactNumber() },
					smsText);
			if (smsReturnTest.toLowerCase().contains(sMSProvider.getSuccessString().toLowerCase())) {
				notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.SUCCESS);
			} else {
				notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.FAILED);
				notificationMessage.setErrorMessage(smsReturnTest);
			}
		} else {
			notificationMessage.setSentAddress("Not Available");
			notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
			notificationMessage.setErrorMessage("Contact number not available");
		}
		return notificationMessage;
	}

	@Override
	public String getMessage(final StudentAcademicYear studentAcademicYear, final BatchLog batchLog) throws ApplicationException {
		String smsText = batchLog.getMessage();
		if ((smsText == null) || smsText.trim().isEmpty()) {
			final Map<String, String> model = new HashMap<String, String>();
			model.put("studentName", studentAcademicYear.getStudent().getDisplayName());
			model.put("examName", batchLog.getSectionExam().getExam().getName());
			model.put("date", new SimpleDateFormat("dd/mm/yyyy").format(batchLog.getSectionExam().getStartDate()));
			smsText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, VELOCITY_TEMPLATE_PATH, model);
		}
		return smsText;
	}

}
