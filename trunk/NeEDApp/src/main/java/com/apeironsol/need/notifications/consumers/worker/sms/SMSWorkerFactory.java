/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 */
package com.apeironsol.need.notifications.consumers.worker.sms;

import com.apeironsol.need.util.ApplicationContextUtils;
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;

/**
 * @author pradeep
 * 
 */
public class SMSWorkerFactory {

	public static SMSWorker getSMSWorker(final NotificationSubTypeConstant notificationSubTypeConstant) {
		SMSWorker workerClass = null;
		switch (notificationSubTypeConstant) {
			case FEE_DUE_NOTIFICATION:
				workerClass = ApplicationContextUtils.getApplicationContext().getBean(FeeDueSMSWorker.class);
				break;
			case FEE_PAID_NOTIFICATION:
				workerClass = ApplicationContextUtils.getApplicationContext().getBean(FeePaidSMSWorker.class);
				break;
			case EXPENSES_INCURRED_NOTIFICATION:
				workerClass = ApplicationContextUtils.getApplicationContext().getBean(BranchExpenseIncurredSMSWorker.class);
				break;
			case ADHOC_NOTIFICATION:
				workerClass = ApplicationContextUtils.getApplicationContext().getBean(AdhocSMSWorker.class);
				break;
			case ABSENT_NOTIFICATION:
				workerClass = ApplicationContextUtils.getApplicationContext().getBean(StudentAbsentSMSWorker.class);
				break;
			case EXAM_ABSENT_NOTIFICATION:
				workerClass = ApplicationContextUtils.getApplicationContext().getBean(StudentAbsentForExamSMSWorker.class);
				break;
			case EXAM_SCHEDULE_NOTIFICATION:
				workerClass = ApplicationContextUtils.getApplicationContext().getBean(ExamScheduledSMSWorker.class);
				break;
			case EXAM_RESULT_NOTIFICATION:
				workerClass = ApplicationContextUtils.getApplicationContext().getBean(ExamResultSMSWorker.class);
				break;
			case NEW_ADMISSION_ACCEPTED_NOTIFICATION:
				workerClass = ApplicationContextUtils.getApplicationContext().getBean(NewAdmissionAcceptedSMSWorker.class);
				break;
			case NEW_ADMISSION_SUBMITTED_NOTIFICATION:
				workerClass = ApplicationContextUtils.getApplicationContext().getBean(NewAdmissionSubmittedSMSWorker.class);
				break;
			case NEW_STUDENT_REGISTRATION_NOTIFICATION:
				workerClass = ApplicationContextUtils.getApplicationContext().getBean(NewStudentRegistrationSubmittedSMSWorker.class);
				break;
			case REPORT_CARD_NOTIFICATION:
				workerClass = ApplicationContextUtils.getApplicationContext().getBean(ReportCardSMSWorker.class);
				break;
			case NEW_ADMISSION_NOTIFICATION:
				workerClass = ApplicationContextUtils.getApplicationContext().getBean(NewStudentAdmittedSMSWorker.class);
				break;
			default:
				workerClass = ApplicationContextUtils.getApplicationContext().getBean(FeeDueSMSWorker.class);
		}
		return workerClass;
	}
}
