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
import javax.persistence.NoResultException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.apeironsol.need.security.dao.UserGroupDao;
import com.apeironsol.need.security.model.UserGroup;
import com.apeironsol.need.security.model.UserGroupAuthority;
import com.apeironsol.framework.exception.BusinessException;

/**
 * @author RAMPAGE
 * 
 */
@Service("userGroupService")
@Transactional
public class UserGroupServiceImpl implements UserGroupService {

	@Resource
	UserGroupDao				userGroupDao;

	@Resource
	UserGroupAuthorityService	userGroupAuthorityService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserGroup getUserGroupById(final Long id) throws BusinessException {
		return this.userGroupDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<UserGroup> getUserGroups() throws BusinessException {
		return this.userGroupDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserGroup saveUserGroup(final UserGroup userGroup) throws BusinessException {
		Assert.notNull(userGroup);
		this.checkUserGroupForDuplicateName(userGroup);
		UserGroup result = this.userGroupDao.persist(userGroup);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserGroup saveUserGroup(final UserGroup userGroup, final Collection<UserGroupAuthority> userGroupAuthorities) throws BusinessException {
		Assert.notNull(userGroup);

		this.checkUserGroupForDuplicateName(userGroup);

		if (userGroup.getId() != null) {
			this.userGroupAuthorityService.removeUserGroupAuthoritiesByUserGroup(userGroup);
		}

		for (UserGroupAuthority userGroupAuthority : userGroupAuthorities) {
			userGroupAuthority.setUserGroup(userGroup);
		}

		userGroup.setUserGroupAuthorities(userGroupAuthorities);

		UserGroup result = this.userGroupDao.persist(userGroup);

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteUserGroup(final UserGroup userGroup) {
		this.userGroupDao.remove(userGroup);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserGroup findUserGroupByName(final String name) throws BusinessException {
		return this.userGroupDao.findUserGroupByName(name);
	}

	/**
	 * Checks if the user name is already in use. If yes, the exception is
	 * thrown.
	 * 
	 * @param userAccount
	 *            user account.
	 * @throws BusinessException
	 *             Business exception if user name is already in use.
	 */
	private void checkUserGroupForDuplicateName(final UserGroup userGroup) throws BusinessException {
		UserGroup userGroupFromDB = null;
		try {
			userGroupFromDB = this.findUserGroupByName(userGroup.getName());
		} catch (NoResultException noResultException) {
		}
		if (userGroupFromDB != null) {
			if (userGroup.getId() == null || !userGroupFromDB.getId().equals(userGroup.getId())) {
				throw new BusinessException("User group with name " + userGroup.getName() + " already exists.");
				/*
				 * throw new BusinessException(
				 * BusinessMessages.getResourceBundleName(),
				 * BusinessMessages.MSG_USER_ACCOUNT_USER_NAME_ALREADY_EXISTS,
				 * new Object[] { userAccount.getUsername() } );
				 */
			}
		}
	}

}
