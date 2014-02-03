/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.dao;

import java.util.Collection;
import java.util.Date;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.util.constants.NotificationLevelConstant;
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;

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
		final TypedQuery<BatchLog> query = getEntityManager().createQuery("select bl from BatchLog bl where bl.branch.id = :id", BatchLog.class);
		query.setParameter("id", branchId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BatchLog> findBatchLogsByNotificationLevelAndNotificationLevelId(final Long branchId,
			final NotificationLevelConstant notificationLevelConstant, final Long notificationLevelId) {
		final TypedQuery<BatchLog> query = getEntityManager().createQuery(
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
		final TypedQuery<BatchLog> query = getEntityManager().createQuery(
				"select bl from BatchLog bl where bl.branch.id = :id and  bl.notificationLevelConstant = :notificationLevelConstant "
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
		final TypedQuery<BatchLog> query = getEntityManager().createQuery("select bl from BatchLog bl where  bl.reportCard.id = :reportCardId", BatchLog.class);
		query.setParameter("reportCardId", reportCardId);
		return query.getResultList();
	}

	@Override
	public Collection<BatchLog> findBatchLogsForAttendanceDate(final Long branchId, final Date attendanceDate) {
		final TypedQuery<BatchLog> query = getEntityManager()
				.createQuery(
						"select bl from BatchLog bl where bl.branch.id = :branchId and  bl.attendanceDate = :attendanceDate and bl.notificationSubTypeConstant = 'ABSENT_NOTIFICATION' ",
						BatchLog.class);
		query.setParameter("branchId", branchId);
		query.setParameter("attendanceDate", attendanceDate);
		return query.getResultList();
	}

	@Override
	public Collection<BatchLog> findBatchLogsForExamAndNotificationAcademicYear(final Long examId, final Long academicYearId,
			final NotificationSubTypeConstant notificationSubTypeConstant) {
		final TypedQuery<BatchLog> query = getEntityManager()
				.createQuery(
						"select bl from BatchLog bl where bl.exam.id = :examId and  bl.notificationSendForAcademicYear.id = :academicYearId and bl.notificationSubTypeConstant = :notificationSubTypeConstant ",
						BatchLog.class);
		query.setParameter("examId", examId);
		query.setParameter("academicYearId", academicYearId);
		query.setParameter("notificationSubTypeConstant", notificationSubTypeConstant);
		return query.getResultList();
	}
}
