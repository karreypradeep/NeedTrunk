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

import com.apeironsol.framework.NeEDJMSObject;
import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.core.model.Attendance;
import com.apeironsol.need.core.model.SMSProvider;
import com.apeironsol.need.core.model.StudentAbsent;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.service.AttendanceService;
import com.apeironsol.need.core.service.StudentAbsentService;
import com.apeironsol.need.core.service.StudentSectionService;
import com.apeironsol.need.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.need.notifications.providers.sms.UniversalSMSProvider;
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
	public NotificationMessage sendSMS(final NeEDJMSObject neEDJMSObject) throws ClientProtocolException, URISyntaxException, IOException {
		if (neEDJMSObject.getBatchLog().getAttendanceDate() == null) {
			neEDJMSObject.getBatchLog().setAttendanceDate(DateUtil.getSystemDate());
		}
		final SMSProvider sMSProvider = neEDJMSObject.getSmsProvider() != null ? neEDJMSObject.getSmsProvider() : neEDJMSObject.getBatchLog().getSmsProvider();
		final UniversalSMSProvider universalSMSProvider = new UniversalSMSProvider(sMSProvider);
		final NotificationMessage notificationMessage = new NotificationMessage();
		final Map<String, String> model = new HashMap<String, String>();
		model.put("studentName", neEDJMSObject.getStudentAcademicYear().getStudent().getDisplayName());
		model.put("date", new SimpleDateFormat("dd/MMM/yyyy").format(neEDJMSObject.getBatchLog().getAttendanceDate()));
		final StudentSection studentSection = this.studentSectionService.findStudentSectionByStudentAcademicYearIdAndStatus(neEDJMSObject
				.getStudentAcademicYear().getId(), StudentSectionStatusConstant.ACTIVE);
		Attendance attendance = null;
		StudentAbsent studentAbsent = null;
		if (studentSection != null) {
			attendance = this.attendanceService.findAttendanceBySectionIdAndAttendanceDateForDailyAttendance(studentSection.getSection().getId(), neEDJMSObject
					.getBatchLog().getAttendanceDate());
			studentAbsent = this.studentAbsentService.findStudentAttendanceByAttendanceIdAndStudentAcademicYearId(attendance.getId(), neEDJMSObject
					.getStudentAcademicYear().getId());
			if (studentAbsent != null) {
				model.put("reason", studentAbsent.getAbsentReason());
			}
		}
		String smsText = neEDJMSObject.getBatchLog().getMessage();
		if ((smsText == null) || smsText.trim().isEmpty()) {
			smsText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, VELOCITY_TEMPLATE_PATH, model);
		}
		notificationMessage.setMessage(smsText);
		if (attendance == null) {
			notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
			notificationMessage.setErrorMessage("Attendance not taken.");
			notificationMessage.setMessage("Attendance not taken.");
		} else if (neEDJMSObject.getStudentAcademicYear().getStudent().getAddress().getContactNumber() == null) {
			notificationMessage.setSentAddress("Not Available");
			notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
			notificationMessage.setErrorMessage("Contact number not available");
		} else if (studentAbsent == null) {
			notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.CANCELLED);
			notificationMessage.setErrorMessage("Student present in class.");
			notificationMessage.setMessage("Student present in class.");
		} else if (neEDJMSObject.getStudentAcademicYear().getStudent().getAddress().getContactNumber() != null) {
			notificationMessage.setSentAddress(neEDJMSObject.getStudentAcademicYear().getStudent().getAddress().getContactNumber());
			final String smsReturnTest = universalSMSProvider.sendSMS(new String[] { neEDJMSObject.getStudentAcademicYear().getStudent().getAddress()
					.getContactNumber() }, smsText);
			if (smsReturnTest.toLowerCase().contains(sMSProvider.getSuccessString().toLowerCase())) {
				notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.SUCCESS);
			} else {
				notificationMessage.setBatchLogMessageStatus(BatchLogMessageStatusConstant.FAILED);
				notificationMessage.setErrorMessage(smsReturnTest);
			}
		}

		return notificationMessage;
	}

	@Override
	public String getMessage(final NeEDJMSObject neEDJMSObject) throws ApplicationException {
		final Map<String, String> model = new HashMap<String, String>();
		model.put("studentName", neEDJMSObject.getStudentAcademicYear().getStudent().getDisplayName());
		model.put("instituteType", "College");
		model.put("date", new SimpleDateFormat("dd/MMM/yyyy").format(neEDJMSObject.getBatchLog().getAttendanceDate()));
		String smsText = neEDJMSObject.getBatchLog().getMessage();
		if ((smsText == null) || smsText.trim().isEmpty()) {
			smsText = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, VELOCITY_TEMPLATE_PATH, model);
		}
		return smsText;
	}

}
