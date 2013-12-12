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
import com.apeironsol.need.core.model.Attendance;
import com.apeironsol.need.core.model.StudentAbsent;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.service.AttendanceService;
import com.apeironsol.need.core.service.StudentAbsentService;
import com.apeironsol.need.core.service.StudentSectionService;
import com.apeironsol.need.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.notifications.providers.sms.SMSProvider;
import com.apeironsol.need.notifications.providers.sms.SMSProviderFactory;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;
import com.apeironsol.need.util.constants.StudentSectionStatusConstant;

/**
 * Class for sending email notification for student pending fee.
 * The scope of this bean is prototype as a new bean should be created for
 * sending email as t.
 * 
 * @author Pradeep
 */
@Component
public class StudentAbsentSMSWorker implements SMSWorker {

	/**
	 * Velocity engine for compiling and merging text with velocity templates.
	 */
	@Autowired
	private VelocityEngine			velocityEngine;

	/**
	 * Velocity template path for notification.
	 */
	private static final String		VELOCITY_TEMPLATE_PATH	= "velocityTemplates/studentAbsentSMSTemplate.vm";

	@Resource
	private StudentAbsentService	studentAbsentService;

	@Resource
	StudentSectionService			studentSectionService;

	@Resource
	AttendanceService				attendanceService;

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
	public NotificationMessage sendSMS(final String smsProviderName, final StudentAcademicYear studentAcademicYear, final BatchLog batchLog)
			throws ClientProtocolException, URISyntaxException, IOException {
		if (batchLog.getAttendanceDate() == null) {
			batchLog.setAttendanceDate(DateUtil.getSystemDate());
		}
		NotificationMessage notificationMessage = new NotificationMessage();
		SMSProvider smsProvider = SMSProviderFactory.getSMSWorker(smsProviderName == null ? "smscountry" : smsProviderName);
		Map<String, String> model = new HashMap<String, String>();
		model.put("studentName", studentAcademicYear.getStudent().getDisplayName());
		model.put("date", new SimpleDateFormat("dd/MMM/yyyy").format(batchLog.getAttendanceDate()));
		StudentSection studentSection = this.studentSectionService.findStudentSectionByStudentAcademicYearIdAndStatus(studentAcademicYear.getId(),
				StudentSectionStatusConstant.ACTIVE);
		Attendance attendance = null;
		StudentAbsent studentAbsent = null;
		if (studentSection != null) {
			attendance = this.attendanceService.findAttendanceBySectionIdAndAttendanceDateForDailyAttendance(studentSection.getSection().getId(),
					batchLog.getAttendanceDate());
			studentAbsent = this.studentAbsentService.findStudentAttendanceByAttendanceIdAndStudentAcademicYearId(attendance.getId(),
					studentAcademicYear.getId());
			if (studentAbsent != null) {
				model.put("reason", studentAbsent.getAbsentReason());
			}
		}
		String smsText = batchLog.getMessage();
		if (smsText == null || smsText.trim().isEmpty()) {
			smsText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, VELOCITY_TEMPLATE_PATH, model);
		}
		notificationMessage.setMessage(smsText);
		if (attendance == null) {
			notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
			notificationMessage.setErrorMessage("Attendance not taken.");
			notificationMessage.setMessage("Attendance not taken.");
		} else if (studentAbsent == null) {
			notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
			notificationMessage.setErrorMessage("Student present in class.");
			notificationMessage.setMessage("Student present in class.");
		} else if (studentAcademicYear.getStudent().getAddress().getContactNumber() != null) {
			// smodel.put("reason",
			// batchLog.getBranch().getBranchTypeConstant().getLabel());
			notificationMessage.setSentAddress(studentAcademicYear.getStudent().getAddress().getContactNumber());
			String smsReturnTest = smsProvider.sendSMS(new String[] { studentAcademicYear.getStudent().getAddress().getContactNumber() }, smsText);
			if (smsReturnTest.contains("status")) {
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
		Map<String, String> model = new HashMap<String, String>();
		model.put("studentName", studentAcademicYear.getStudent().getDisplayName());
		model.put("instituteType", "College");
		model.put("date", new SimpleDateFormat("dd/MMM/yyyy").format(batchLog.getAttendanceDate()));
		String smsText = batchLog.getMessage();
		if (smsText == null || smsText.trim().isEmpty()) {
			smsText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, VELOCITY_TEMPLATE_PATH, model);
		}
		return smsText;
	}

}
