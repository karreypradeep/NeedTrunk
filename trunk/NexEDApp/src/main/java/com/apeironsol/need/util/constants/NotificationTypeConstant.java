/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.util.constants;

import com.apeironsol.need.util.portal.ViewUtil;

/**
 * @author pradeepk
 * 
 */
public enum NotificationTypeConstant implements Labeled {

	EMAIL_NOTIFICATION("email_notification"), SMS_NOTIFICATION("sms_notification");

	private String	label;

	NotificationTypeConstant(final String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return ViewUtil.getEnumLabel(this.label);
	}

	@Override
	public void setLabel(final String label) {
		this.label = label;
	}

	public static NotificationTypeConstant getConstantByLabel(final String label) {
		NotificationTypeConstant constant = null;
		if ("email_notification".equals(label)) {
			constant = EMAIL_NOTIFICATION;
		} else if ("sms_notification".equals(label)) {
			constant = SMS_NOTIFICATION;
		} else {
			constant = SMS_NOTIFICATION;
		}
		return constant;
	}

}
