/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.security.dao;

import com.apeironsol.need.security.model.UserAccount;
import com.apeironsol.need.security.model.UserGroup;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for user account entity.
 * 
 * @author Pradeep
 */
public interface UserAccountDao extends BaseDao<UserAccount> {

	/**
	 * Returns user account by user name.
	 * 
	 * @param username
	 *            user name.
	 * @return user account by user name.
	 */
	UserAccount findUserAccountByUsername(String username);

	/**
	 * Returns true is user group is assigned to any user.
	 * 
	 * @param userGroup
	 *            user group.
	 * @return Boolean.
	 */
	Boolean isUserGroupAssignedToUser(UserGroup userGroup);

}
