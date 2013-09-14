/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 */
package com.apeironsol.nexed.notifications.consumers.worker.email;

import com.apeironsol.nexed.util.ApplicationContextUtils;
import com.apeironsol.nexed.util.constants.NotificationSubTypeConstant;

/**
 * @author pradeep
 * 
 */
public class EmailWorkerFactory {

	public static EmailWorker getEmailWorker(final NotificationSubTypeConstant notificationSubTypeConstant) {
		EmailWorker workerClass = null;
		switch (notificationSubTypeConstant) {
			case FEE_DUE_NOTIFICATION:
				workerClass = ApplicationContextUtils.getApplicationContext().getBean(FeeDueEmailWorker.class);
				break;
			case FEE_PAID_NOTIFICATION:
				workerClass = ApplicationContextUtils.getApplicationContext().getBean(FeePaidEmailWorker.class);
				break;
			case ADHOC_NOTIFICATION:
				workerClass = ApplicationContextUtils.getApplicationContext().getBean(AdhocEmailWorker.class);
				break;
			default:
				workerClass = ApplicationContextUtils.getApplicationContext().getBean(FeeDueEmailWorker.class);
		}
		return workerClass;
	}
}
