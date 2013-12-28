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

import com.apeironsol.need.security.dao.UserGroupAuthorityDao;
import com.apeironsol.need.security.model.UserGroup;
import com.apeironsol.need.security.model.UserGroupAuthority;

/**
 * @author RAMPAGE
 * 
 */
@Service("userGroupAuthorityService")
@Transactional(rollbackFor = Exception.class)
public class UserGroupAuthorityServiceImpl implements UserGroupAuthorityService {


	@Resource
	UserGroupAuthorityDao	userGroupAuthorityDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserGroupAuthority findUserGroupAuthorityById(Long id) {
		return this.userGroupAuthorityDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<UserGroupAuthority> findAllUserGroupAuthoritys() {
		return this.userGroupAuthorityDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserGroupAuthority saveUserGroupAuthority(UserGroupAuthority userGroupAuthority) {
		return this.userGroupAuthorityDao.persist(userGroupAuthority);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteUserGroupAuthority(UserGroupAuthority userGroupAuthority) {
		this.userGroupAuthorityDao.remove(userGroupAuthority);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<UserGroupAuthority> findUserGroupAuthoritiesByUserGroup(UserGroup userGroup) {
		return this.userGroupAuthorityDao.findUserGroupAuthoritiesByUserGroup(userGroup);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeUserGroupAuthoritiesByUserGroup(final UserGroup userGroup) {
		this.userGroupAuthorityDao.removeUserGroupAuthoritiesByUserGroup(userGroup);
	}
}
