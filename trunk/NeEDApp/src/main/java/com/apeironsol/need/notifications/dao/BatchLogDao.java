/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.dao;

import java.util.Collection;

import com.apeironsol.framework.BaseDao;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.util.constants.NotificationLevelConstant;

/**
 * Data access interface for batch log.
 * 
 * @author Pradeep
 */
public interface BatchLogDao extends BaseDao<BatchLog> {

	/**
	 * Retrieve all batch logs for the supplied branch id.
	 * 
	 * @param branchId
	 *            barnch id.
	 * @return collection of batch logs for the supplied branch id.
	 */
	Collection<BatchLog> findBatchLogsByBranchId(Long branchId);

	/**
	 * Retrieve all batch logs for the supplied notification level constant and
	 * notification level id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @param notificationLevelConstant
	 *            notificationLevelConstant.
	 * @param notificationLevelId
	 *            notificationLevelId.
	 * @return collection of batch logs for the supplied branch id.
	 */
	Collection<BatchLog> findBatchLogsByNotificationLevelAndNotificationLevelId(final Long branchId, final NotificationLevelConstant notificationLevelConstant,
			final Long notificationLevelId);

	/**
	 * 
	 * @param branchId
	 * @param notificationLevelConstant
	 * @param notificationLevelId
	 * @param reportCardId
	 * @return
	 */
	Collection<BatchLog> findBatchLogsForReportCardByNotificationLevelAndNotificationLevelId(final Long branchId,
			final NotificationLevelConstant notificationLevelConstant, final Long notificationLevelId, final Long reportCardId);

	/**
	 * 
	 * @param reportCardId
	 * @return
	 */
	Collection<BatchLog> findBatchLogsForReportCardId(final Long reportCardId);
}
