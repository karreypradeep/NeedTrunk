/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.nexed.notifications.dao;

import java.util.Collection;

import com.apeironsol.nexed.notifications.model.BranchNotification;
import com.apeironsol.nexed.util.constants.NotificationSubTypeConstant;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for batch log.
 * 
 * @author Pradeep
 */
public interface BranchNotificationDao extends BaseDao<BranchNotification> {

	/**
	 * Retrieve all batch notifications for the supplied branch id.
	 * 
	 * @param branchId
	 *            barnch id.
	 * @return collection of batch logs for the supplied branch id.
	 */
	Collection<BranchNotification> findBranchNotificationsByBranchId(Long branchId);

	/**
	 * Retrieve batch notification for the supplied branch id and
	 * notificationSubTypeConstant.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return batch notification for the supplied branch id and
	 *         notificationSubTypeConstant.
	 */
	BranchNotification findBranchNotificationByBranchIdAnsNotificationSubType(final Long branchId, NotificationSubTypeConstant notificationSubTypeConstant);

}
