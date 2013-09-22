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

/**
 * Service Interface for user accounts. This service act as controller for user
 * account details.
 * 
 * @author Sandeep
 * 
 */
public interface UserGroupAuthorityService {

	/**
	 * Retrieves the user group authority by id.
	 * 
	 * @param id
	 *            the id of user group authority to be retrieved.
	 * @return
	 */
	UserGroupAuthority findUserGroupAuthorityById(Long id);

	/**
	 * Retrieves all user group authorities available.
	 * 
	 * @return all user group authorities available.
	 */
	Collection<UserGroupAuthority> findAllUserGroupAuthoritys();

	/**
	 * Saves the user group authority.
	 * 
	 * @return the saved user group authority.
	 */
	UserGroupAuthority saveUserGroupAuthority(UserGroupAuthority userGroupAuthority);

	/**
	 * Deletes the user group authority.
	 * 
	 */
	void deleteUserGroupAuthority(UserGroupAuthority userGroupAuthority);

	/**
	 * Returns the user group authorities by group.
	 * 
	 * @param userGroup
	 *            user group.
	 * @return the user group authorities by group.
	 */
	Collection<UserGroupAuthority> findUserGroupAuthoritiesByUserGroup(UserGroup userGroup);

	/**
	 * Removes all user group authorities by group.
	 * 
	 * @param userGroup
	 *            user group.
	 */
	void removeUserGroupAuthoritiesByUserGroup(final UserGroup userGroup);
}
