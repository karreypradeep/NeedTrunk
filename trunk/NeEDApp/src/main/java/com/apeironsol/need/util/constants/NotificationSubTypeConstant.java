/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.util.constants;

import java.util.ArrayList;
import java.util.List;

import com.apeironsol.need.util.portal.ViewUtil;

/**
 * @author pradeepk
 * 
 */
public enum NotificationSubTypeConstant implements Labeled {

	FEE_DUE_NOTIFICATION("fee_due_notification", true, false, true, false, false, true),
	FEE_PAID_NOTIFICATION("fee_paid_notification", false, false, true, true, false, false),
	FEE_REFUND_NOTIFICATION("fee_refund_notification", false, false, true, true, false, false),
	MARKS_NOTIFICATION("marks_notification", true, false, false, false, false, false),
	HOLIDAY_NOTIFICATION("holiday_notification", true, true, false, false, false, false),
	EXPENSES_INCURRED_NOTIFICATION("expenses_incurred_notification", true, false, true, true, true, false),
	INVOICE_NOTIFICATION("expenses_incurred_notification", true, false, true, true, true, false),
	ABSENT_NOTIFICATION("absent_notification", true, false, false, false, false, false),
	EXAM_ABSENT_NOTIFICATION("exam_absent_notification", true, false, false, false, false, false),
	EXAM_SCHEDULE_NOTIFICATION("exam_schedule_notification", true, false, false, false, false, false),
	NEW_ADMISSION_ACCEPTED_NOTIFICATION("new_admission_accepted_notification", false, false, false, true, false, false),
	NEW_ADMISSION_SUBMITTED_NOTIFICATION("new_admission_submitted_notification", false, false, false, true, false, false),
	NEW_ADMISSION_NOTIFICATION("new_admission_admitted_notification", false, false, false, true, false, false),
	REPORT_CARD_NOTIFICATION("report_card_notification", true, false, false, false, false, false),
	EXAM_RESULT_NOTIFICATION("exam_result_notification", true, false, false, false, false, false),
	ADHOC_NOTIFICATION("adhoc_notification", true, true, false, false, false, false);

	NotificationSubTypeConstant(final String label, final boolean groupMessage, final boolean messageRequired, final boolean minimumAmountRequired,
			final boolean isImplicitMessage, final boolean contactDetalisRequired, final boolean isAllowedTobeScheduled) {
		this.label = label;
		this.groupMessage = groupMessage;
		this.messageRequired = messageRequired;
		this.minimumAmountRequired = minimumAmountRequired;
		this.isImplicitMessage = isImplicitMessage;
		this.contactDetalisRequired = contactDetalisRequired;
		this.isAllowedTobeScheduled = isAllowedTobeScheduled;
	}

	private String											label;

	// Specifies if the message can be send as group also.
	private final boolean									groupMessage;
	private final boolean									messageRequired;
	private final boolean									minimumAmountRequired;
	// If true then message is notification is used as a part of some event in
	// system. If false then this notification can be used to send explicit
	// messages.
	private final boolean									isImplicitMessage;

	// If the message requires contact details like for sending expenses record,
	// set this variable to true.
	private final boolean									contactDetalisRequired;

	// If message is allowed to be scheduled at a particular time, then set this
	// attribute as true.
	private final boolean									isAllowedTobeScheduled;

	private static final List<NotificationSubTypeConstant>	brachNotifications	= new ArrayList<NotificationSubTypeConstant>();

	static {
		brachNotifications.add(FEE_DUE_NOTIFICATION);
		brachNotifications.add(FEE_PAID_NOTIFICATION);
		brachNotifications.add(FEE_REFUND_NOTIFICATION);
		brachNotifications.add(MARKS_NOTIFICATION);
		brachNotifications.add(ADHOC_NOTIFICATION);
		brachNotifications.add(HOLIDAY_NOTIFICATION);
		brachNotifications.add(EXPENSES_INCURRED_NOTIFICATION);
		brachNotifications.add(ABSENT_NOTIFICATION);
		brachNotifications.add(EXAM_ABSENT_NOTIFICATION);
		brachNotifications.add(EXAM_SCHEDULE_NOTIFICATION);
		brachNotifications.add(NEW_ADMISSION_ACCEPTED_NOTIFICATION);
		brachNotifications.add(NEW_ADMISSION_SUBMITTED_NOTIFICATION);
		brachNotifications.add(NEW_ADMISSION_NOTIFICATION);
		brachNotifications.add(REPORT_CARD_NOTIFICATION);
		brachNotifications.add(EXAM_RESULT_NOTIFICATION);
	}

	/**
	 * @return the brachLevelNotifications
	 */
	public static List<NotificationSubTypeConstant> getBrachNotifications() {
		return brachNotifications;
	}

	/**
	 * @return the brachLevelNotifications
	 */
	public static NotificationSubTypeConstant[] getBrachGroupNotifications() {
		final List<NotificationSubTypeConstant> brachGroupNotifications = new ArrayList<NotificationSubTypeConstant>();
		for (final NotificationSubTypeConstant constant : brachNotifications) {
			if (constant.isGroupMessage()) {
				brachGroupNotifications.add(constant);
			}
		}
		return brachGroupNotifications.toArray(new NotificationSubTypeConstant[brachGroupNotifications.size()]);
	}

	@Override
	public String getLabel() {
		return ViewUtil.getEnumLabel(this.label);
	}

	@Override
	public void setLabel(final String label) {
		this.label = label;
	}

	/**
	 * @return the groupMessage
	 */
	public boolean isGroupMessage() {
		return this.groupMessage;
	}

	/**
	 * @return the messageRequired
	 */
	public boolean isMessageRequired() {
		return this.messageRequired;
	}

	/**
	 * @return the minimumAmountRequired
	 */
	public boolean isMinimumAmountRequired() {
		return this.minimumAmountRequired;
	}

	/**
	 * @return the isImplicitMessage
	 */
	public boolean isImplicitMessage() {
		return this.isImplicitMessage;
	}

	/**
	 * @return the contactDetalisRequired
	 */
	public boolean isContactDetalisRequired() {
		return this.contactDetalisRequired;
	}

	/**
	 * @return the isAllowedTobeScheduled
	 */
	public boolean isAllowedTobeScheduled() {
		return this.isAllowedTobeScheduled;
	}

}
