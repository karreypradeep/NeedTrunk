/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.security.dao;

import java.util.Collection;

import com.apeironsol.need.security.model.UserGroup;
import com.apeironsol.need.security.model.UserGroupAuthority;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for user group authority entity.
 * 
 * @author Pradeep
 */
public interface UserGroupAuthorityDao extends BaseDao<UserGroupAuthority> {

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
