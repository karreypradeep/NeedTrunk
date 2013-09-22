/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.dao;

import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.notifications.model.BranchNotification;
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access class for batch notification.
 * 
 * @author Pradeep
 */
@Repository("branchNotificationDao")
public class BranchNotificationDaoImpl extends BaseDaoImpl<BranchNotification> implements BranchNotificationDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchNotification> findBranchNotificationsByBranchId(final Long branchId) {
		TypedQuery<BranchNotification> query = this.getEntityManager().createQuery("select bl from BranchNotification bl where bl.branch.id = :id",
				BranchNotification.class);
		query.setParameter("id", branchId);
		return query.getResultList();
	}

	@Override
	public BranchNotification findBranchNotificationByBranchIdAnsNotificationSubType(final Long branchId,
			final NotificationSubTypeConstant notificationSubTypeConstant) {
		try {
			TypedQuery<BranchNotification> query = this.getEntityManager()
					.createQuery("select bl from BranchNotification bl where bl.branch.id = :id and bl.notificationSubType = :notificationSubType",
							BranchNotification.class);
			query.setParameter("id", branchId);
			query.setParameter("notificationSubType", notificationSubTypeConstant);
			return query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}
}
