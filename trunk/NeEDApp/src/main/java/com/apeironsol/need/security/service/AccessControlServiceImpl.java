/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.security.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.security.dao.AccessControlDao;
import com.apeironsol.need.security.model.AccessControl;
import com.apeironsol.need.security.model.UserAccount;
import com.apeironsol.need.util.constants.AccessControlTypeConstant;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service implementation interface for account access.
 * 
 */
@Service(value = "accessControlService")
@Transactional
public class AccessControlServiceImpl implements AccessControlService {

	@Resource
	private AccessControlDao	accessControlDao;

	@Resource
	private UserAccountService		userAccountService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasOrganizationAccess(final String username)  throws BusinessException{
		UserAccount userAccount = this.userAccountService.findUserAccountByUsername(username);
		AccessControl accessControl = this.accessControlDao.findAccessControlByType(userAccount,
				AccessControlTypeConstant.ORGANIZATION);
		if (accessControl != null) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasBranchAccess(final Long branchId, final String username)  throws BusinessException{
		UserAccount userAccount = this.userAccountService.findUserAccountByUsername(username);

		AccessControl accessControl = this.accessControlDao.findAccessControlByType(userAccount, branchId,
				AccessControlTypeConstant.BRANCH);
		if (accessControl != null) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AccessControl> findAccessControlsByUser(final UserAccount userAccount)  throws BusinessException{
		return this.accessControlDao.findAccessControlsByUser(userAccount);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccessControl saveAccessControl(final AccessControl accessControl)  throws BusinessException{
		return this.accessControlDao.persist(accessControl);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccessControl getAccessControlById(final Long id)  throws BusinessException{
		return this.accessControlDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAccessControlsByUserAccountId(final UserAccount userAccount)  throws BusinessException{
		this.accessControlDao.removeAccessControlsByUserAccountId(userAccount);
	}
}
