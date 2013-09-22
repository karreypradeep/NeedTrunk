/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.security.service;

import java.util.Collection;

import com.apeironsol.need.security.model.UserGroup;
import com.apeironsol.need.security.model.UserGroupAuthority;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service Interface for user groups. This service act as controller for user
 * account details.
 * 
 * @author Sandeep
 * 
 */
public interface UserGroupService {

	/**
	 * Retrieves the user account by id.
	 * 
	 * @param id
	 *            the id of user account to be retrieved.
	 * @return
	 */
	UserGroup getUserGroupById(Long id) throws BusinessException;

	/**
	 * Retrieves all user accounts available.
	 * 
	 * @return all user accounts available.
	 */
	Collection<UserGroup> getUserGroups() throws BusinessException;

	/**
	 * Saves the user account.
	 * 
	 * @return the saved user account.
	 */
	UserGroup saveUserGroup(UserGroup userGroup) throws BusinessException;

	/**
	 * Deletes the user account.
	 * 
	 */
	void deleteUserGroup(UserGroup userGroup) throws BusinessException;

	/**
	 * Retrieves the user group by named.
	 * 
	 * @param name
	 *            the name of user group to be retrieved.
	 * @return
	 */
	UserGroup findUserGroupByName(String name) throws BusinessException;

	/**
	 * 
	 * @param userGroup
	 * @param userGroupAuthority
	 * @return
	 * @throws BusinessException
	 */
	UserGroup saveUserGroup(UserGroup userGroup, Collection<UserGroupAuthority> userGroupAuthorities) throws BusinessException;

}
