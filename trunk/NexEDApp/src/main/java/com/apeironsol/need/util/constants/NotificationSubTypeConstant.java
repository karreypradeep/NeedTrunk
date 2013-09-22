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

	FEE_DUE_NOTIFICATION("fee_due_notification", true, false, true, false),
	FEE_PAID_NOTIFICATION("fee_paid_notification", false, false, true, true),
	FEE_REFUND_NOTIFICATION("fee_refund_notification", false, false, true, true),
	MARKS_NOTIFICATION("marks_notification", true, false, false, false),
	HOLIDAY_NOTIFICATION("holiday_notification", true, true, false, false),
	EXPENSES_INCURRED_NOTIFICATION("expenses_incurred_notification", true, false, true, true),
	INVOICE_NOTIFICATION("expenses_incurred_notification", true, false, true, true),
	ABSENT_NOTIFICATION("absent_notification", true, false, false, false),
	EXAM_ABSENT_NOTIFICATION("exam_absent_notification", true, false, false, false),
	EXAM_SCHEDULE_NOTIFICATION("exam_schedule_notification", true, false, false, false),
	ADHOC_NOTIFICATION("adhoc_notification", true, true, false, false);

	private String											label;

	// Specifies if the message can be send as group also.
	private final boolean									groupMessage;
	private final boolean									messageRequired;
	private final boolean									minimumAmountRequired;
	// If true then message is notification is used as a part of some event in
	// system. If false then this notification can be used to send explicit
	// messages.
	private final boolean									isImplicitMessage;

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
		List<NotificationSubTypeConstant> brachGroupNotifications = new ArrayList<NotificationSubTypeConstant>();
		for (NotificationSubTypeConstant constant : brachNotifications) {
			if (constant.isGroupMessage()) {
				brachGroupNotifications.add(constant);
			}
		}
		return brachGroupNotifications.toArray(new NotificationSubTypeConstant[brachGroupNotifications.size()]);
	}

	NotificationSubTypeConstant(final String label, final boolean groupMessage, final boolean messageRequired, final boolean minimumAmountRequired,
			final boolean isImplicitMessage) {
		this.label = label;
		this.groupMessage = groupMessage;
		this.messageRequired = messageRequired;
		this.minimumAmountRequired = minimumAmountRequired;
		this.isImplicitMessage = isImplicitMessage;
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

}
