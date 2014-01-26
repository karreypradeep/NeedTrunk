/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.dao;

import java.util.Collection;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.util.constants.NotificationLevelConstant;

/**
 * Data access class for batch log.
 * 
 * @author Pradeep
 */
@Repository("batchLogDao")
public class BatchLogDaoImpl extends BaseDaoImpl<BatchLog> implements BatchLogDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BatchLog> findBatchLogsByBranchId(final Long branchId) {
		TypedQuery<BatchLog> query = this.getEntityManager().createQuery("select bl from BatchLog bl where bl.branch.id = :id", BatchLog.class);
		query.setParameter("id", branchId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BatchLog> findBatchLogsByNotificationLevelAndNotificationLevelId(final Long branchId,
			final NotificationLevelConstant notificationLevelConstant, final Long notificationLevelId) {
		TypedQuery<BatchLog> query = this.getEntityManager().createQuery(
				"select bl from BatchLog bl where bl.branch.id = :id and" + " bl.notificationLevelConstant = :notificationLevelConstant "
						+ "and bl.notificationLevelId = :notificationLevelId order by bl.executionStartDate DESC", BatchLog.class);
		query.setParameter("id", branchId);
		query.setParameter("notificationLevelId", notificationLevelId);
		query.setParameter("notificationLevelConstant", notificationLevelConstant);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BatchLog> findBatchLogsForReportCardByNotificationLevelAndNotificationLevelId(final Long branchId,
			final NotificationLevelConstant notificationLevelConstant, final Long notificationLevelId, final Long reportCardId) {
		TypedQuery<BatchLog> query = this.getEntityManager().createQuery(
				"select bl from BatchLog bl where bl.branch.id = :id and" + " bl.notificationLevelConstant = :notificationLevelConstant "
						+ "and bl.notificationLevelId = :notificationLevelId and bl.reportCard.id = :reportCardId order by bl.executionStartDate DESC",
				BatchLog.class);
		query.setParameter("id", branchId);
		query.setParameter("notificationLevelId", notificationLevelId);
		query.setParameter("reportCardId", reportCardId);
		query.setParameter("notificationLevelConstant", notificationLevelConstant);
		return query.getResultList();
	}

	@Override
	public Collection<BatchLog> findBatchLogsForReportCardId(final Long reportCardId) {
		TypedQuery<BatchLog> query = this.getEntityManager().createQuery("select bl from BatchLog bl where  bl.reportCard.id = :reportCardId", BatchLog.class);
		query.setParameter("reportCardId", reportCardId);
		return query.getResultList();
	}
}
