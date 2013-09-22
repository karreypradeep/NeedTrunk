/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.service;

import java.util.Collection;

import com.apeironsol.need.notifications.model.BranchNotification;
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service interface for batch log.
 * 
 * @author Pradeep
 */
public interface BranchNotificationService {
	/**
	 * Find batch log by Id.
	 * 
	 * @param id
	 *            batch log Id.
	 * @return batch log with supplied id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchNotification findBranchNotificationById(Long id) throws BusinessException;

	/**
	 * Save batch log.
	 * 
	 * @param BranchNotification
	 *            batch log to be saved.
	 * @return persisted batch log.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 */
	BranchNotification saveBranchNotification(BranchNotification BranchNotification) throws BusinessException, InvalidArgumentException;

	/**
	 * Remove batch log.
	 * 
	 * @param BranchNotification
	 *            batch log to be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeBranchNotification(BranchNotification BranchNotification) throws BusinessException;

	/**
	 * Retrieve all batch notifications for the supplied branch id.
	 * 
	 * @param branchId
	 *            barnch id.
	 * @return collection of batch logs for the supplied branch id.
	 */
	Collection<BranchNotification> findBranchNotificationsByBranchId(Long branchId);

	/**
	 * Save batch log.
	 * 
	 * @param BranchNotification
	 *            batch log to be saved.
	 * @return persisted batch log.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 */
	Collection<BranchNotification> saveBranchNotifications(Collection<BranchNotification> branchNotifications) throws BusinessException,
			InvalidArgumentException;

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
