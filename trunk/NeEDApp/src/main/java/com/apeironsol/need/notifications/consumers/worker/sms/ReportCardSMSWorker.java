/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.consumers.worker.sms;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
import com.apeironsol.need.academics.dataobject.ReportCardDO;
import com.apeironsol.need.academics.dataobject.StudentAcademicExamDO;
import com.apeironsol.need.academics.model.ReportCard;
import com.apeironsol.need.academics.model.ReportCardExam;
import com.apeironsol.need.academics.service.StudentAcademicService;
import com.apeironsol.need.core.model.SMSProvider;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.notifications.providers.sms.UniversalSMSProvider;
import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;
import com.apeironsol.need.util.constants.StudentSubjectExamResultConstant;

/**
 * Class for sending email notification for student pending fee.
 * The scope of this bean is prototype as a new bean should be created for
 * sending email as t.
 * 
 * @author Pradeep
 */
@Component
public class ReportCardSMSWorker implements SMSWorker {

	/**
	 * Velocity engine for compiling and merging text with velocity templates.
	 */
	@Autowired
	private VelocityEngine			velocityEngine;

	/**
	 * Velocity template path for notification.
	 */
	private static final String		VELOCITY_TEMPLATE_PATH	= "velocityTemplates/sudentReportCardSMSTemplate.vm";

	@Resource
	private StudentAcademicService	studentAcademicService;

	/**
	 * Asynchronous method for sending fee pending notification mail for
	 * student.
	 * 
	 * @param student
	 *            student.
	 * @throws MessagingException
	 */
	@Override
	public NotificationMessage sendSMS(final SMSProvider sMSProvider, final StudentAcademicYear studentAcademicYear, final BatchLog batchLog)
			throws ClientProtocolException, URISyntaxException, IOException {
		final NotificationMessage notificationMessage = new NotificationMessage();
		final UniversalSMSProvider universalSMSProvider = new UniversalSMSProvider(sMSProvider);
		final ReportCard reportCard = batchLog.getReportCard();
		if (reportCard != null) {
			final ReportCardDO reportCardDO = getReportCardDO(studentAcademicYear, batchLog);
			reportCardDO.computeReportCard();
			if (!StudentSubjectExamResultConstant.NOT_APPLICABLE.equals(reportCardDO.getStudentReportCardResult())) {
				final String smsText = getNotificationMessage(reportCardDO, studentAcademicYear, batchLog);
				notificationMessage.setMessage(smsText);
				if (studentAcademicYear.getStudent().getAddress().getContactNumber() != null) {
					notificationMessage.setSentAddress(studentAcademicYear.getStudent().getAddress().getContactNumber());
					final String smsReturnTest = universalSMSProvider.sendSMS(
							new String[] { studentAcademicYear.getStudent().getAddress().getContactNumber() }, smsText);
					if (smsReturnTest.toLowerCase().contains(sMSProvider.getSuccessString().toLowerCase())) {
						notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.SUCCESS);
					} else {
						notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.FAILED);
						notificationMessage.setErrorMessage(smsReturnTest);
					}
				} else {
					notificationMessage.setSentAddress("Not Available.");
					notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
					notificationMessage.setErrorMessage("Contact number not available.");
				}
			} else {
				notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
				notificationMessage.setErrorMessage("Report card not available.");
			}
		} else {
			notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
			notificationMessage.setErrorMessage("No report card details.");
		}
		return notificationMessage;
	}

	/**
	 * 
	 * @param studentAcademicYear
	 * @param batchLog
	 * @return
	 */
	private ReportCardDO getReportCardDO(final StudentAcademicYear studentAcademicYear, final BatchLog batchLog) {
		ReportCardDO reportCardDO = null;
		final ReportCard reportCard = batchLog.getReportCard();
		if (reportCard != null) {
			reportCardDO = new ReportCardDO();
			final Collection<Long> examIDs = new ArrayList<Long>();
			for (final ReportCardExam reportCardExam : reportCard.getReportCardExams()) {
				if (!examIDs.contains(reportCardExam.getExam().getId())) {
					examIDs.add(reportCardExam.getExam().getId());
				}
			}

			final Collection<StudentAcademicExamDO> studentAcademicExamDOs = this.studentAcademicService.getStudentAcademicDetailsByExams(
					studentAcademicYear.getId(), examIDs);
			final Map<Long, StudentAcademicExamDO> examByStudentExamDOs = new HashMap<Long, StudentAcademicExamDO>();
			for (final StudentAcademicExamDO studentAcademicExamDO : studentAcademicExamDOs) {
				examByStudentExamDOs.put(studentAcademicExamDO.getExam().getId(), studentAcademicExamDO);
			}
			final Map<Long, StudentAcademicExamDO> examByStudentAcademicExamDOMap = new HashMap<Long, StudentAcademicExamDO>();
			final Map<Long, Integer> examPercentages = new HashMap<Long, Integer>();
			reportCardDO.setReportCard(reportCard);
			for (final ReportCardExam reportCardExam : reportCard.getReportCardExams()) {
				examByStudentAcademicExamDOMap.put(reportCardExam.getExam().getId(), examByStudentExamDOs.get(reportCardExam.getExam().getId()));
				examPercentages.put(reportCardExam.getExam().getId(), reportCardExam.getPercentage());
			}
			reportCardDO.setExamByStudentAcademicExamDOMap(examByStudentAcademicExamDOMap);
			reportCardDO.setExamPercentages(examPercentages);
		}
		return reportCardDO;
	}

	/**
	 * 
	 * @param reportCardDO
	 * @param studentAcademicYear
	 * @param batchLog
	 * @return
	 */
	private String getNotificationMessage(final ReportCardDO reportCardDO, final StudentAcademicYear studentAcademicYear, final BatchLog batchLog) {
		reportCardDO.computeReportCard();
		final Map<String, String> model = new HashMap<String, String>();
		model.put("reportCardName", reportCardDO.getReportCard().getName());
		model.put("studentName", studentAcademicYear.getStudent().getDisplayName());
		model.put("percentageScored", reportCardDO.getScoredPercentageForReportCard() + "");
		model.put("grade", reportCardDO.getGradeForReportCard());
		model.put("maximumMarks", reportCardDO.getTotalMarksForReportCard() + "");
		model.put("marksScore", reportCardDO.getScoredMarksForReportCard() + "");
		model.put("result", reportCardDO.getStudentReportCardResult().toString());
		String smsText = batchLog.getMessage();
		if ((smsText == null) || smsText.trim().isEmpty()) {
			smsText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, VELOCITY_TEMPLATE_PATH, model);
		}
		return smsText;

	}

	@Override
	public String getMessage(final StudentAcademicYear studentAcademicYear, final BatchLog batchLog) throws ApplicationException {
		final ReportCardDO reportCardDO = getReportCardDO(studentAcademicYear, batchLog);
		return getNotificationMessage(reportCardDO, studentAcademicYear, batchLog);
	}

}
