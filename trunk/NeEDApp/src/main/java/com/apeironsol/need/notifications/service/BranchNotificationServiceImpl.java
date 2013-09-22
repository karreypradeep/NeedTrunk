/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.notifications.dao.BranchNotificationDao;
import com.apeironsol.need.notifications.model.BranchNotification;
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service class for batch log.
 * 
 * @author Pradeep
 */
@Service("branchNotificationService")
@Transactional
public class BranchNotificationServiceImpl implements BranchNotificationService {

	@Autowired
	private BranchNotificationDao	branchNotificationDao;

	@Override
	public BranchNotification findBranchNotificationById(final Long id) throws BusinessException {
		return this.branchNotificationDao.findById(id);
	}

	@Override
	public BranchNotification saveBranchNotification(final BranchNotification branchNotification) throws BusinessException, InvalidArgumentException {
		return this.branchNotificationDao.persist(branchNotification);
	}

	@Override
	public void removeBranchNotification(final BranchNotification BranchNotification) throws BusinessException {
		this.branchNotificationDao.remove(BranchNotification);
	}

	@Override
	public Collection<BranchNotification> findBranchNotificationsByBranchId(final Long branchId) throws BusinessException {
		return this.branchNotificationDao.findBranchNotificationsByBranchId(branchId);
	}

	@Override
	public Collection<BranchNotification> saveBranchNotifications(final Collection<BranchNotification> branchNotifications) throws BusinessException,
			InvalidArgumentException {
		Collection<BranchNotification> result = branchNotifications != null ? new ArrayList<BranchNotification>() : null;
		if (branchNotifications != null) {
			for (BranchNotification branchNotification : branchNotifications) {
				result.add(this.branchNotificationDao.persist(branchNotification));
			}
		}

		return result;
	}

	@Override
	public BranchNotification findBranchNotificationByBranchIdAnsNotificationSubType(final Long branchId,
			final NotificationSubTypeConstant notificationSubTypeConstant) {
		return this.branchNotificationDao.findBranchNotificationByBranchIdAnsNotificationSubType(branchId, notificationSubTypeConstant);
	}
}
