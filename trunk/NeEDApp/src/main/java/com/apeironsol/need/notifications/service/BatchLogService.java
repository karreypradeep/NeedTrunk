/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.service;

import java.util.Collection;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.util.constants.NotificationLevelConstant;

/**
 * Service interface for batch log.
 * 
 * @author Pradeep
 */
public interface BatchLogService {
	/**
	 * Find batch log by Id.
	 * 
	 * @param id
	 *            batch log Id.
	 * @return batch log with supplied id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BatchLog findBatchLogById(Long id) throws BusinessException;

	/**
	 * Save batch log.
	 * 
	 * @param batchLog
	 *            batch log to be saved.
	 * @return persisted batch log.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 */
	BatchLog saveBatchLog(BatchLog batchLog) throws BusinessException, InvalidArgumentException;

	/**
	 * Save batch log in new transaction.
	 * 
	 * @param batchLog
	 *            batch log to be saved.
	 * @return persisted batch log.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 */
	BatchLog saveBatchLogInNewTransaction(BatchLog batchLog) throws BusinessException, InvalidArgumentException;

	/**
	 * Remove batch log.
	 * 
	 * @param batchLog
	 *            batch log to be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeBatchLog(BatchLog batchLog) throws BusinessException;

	/**
	 * Find all batch logs
	 * 
	 * @return collection of all batch logs.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BatchLog> findAllBatchLogs() throws BusinessException;

	/**
	 * Find all batch logs for branch.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return collection of all batch logs for branch.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BatchLog> findBatchLogsByBranchId(final Long branchId) throws BusinessException;

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
	Collection<BatchLog> findBatchLogsForReportCardByNotificationLevelAndNotificationLevelId(final Long branchId,
			final NotificationLevelConstant notificationLevelConstant, final Long notificationLevelId, final Long reportCardId);

	/**
	 * 
	 * @param reportCardId
	 * @return
	 */
	Collection<BatchLog> findBatchLogsForReportCardId(final Long reportCardId);
}
