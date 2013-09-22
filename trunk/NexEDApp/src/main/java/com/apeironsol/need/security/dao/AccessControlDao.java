/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.security.dao;

import java.util.Collection;

import com.apeironsol.need.security.model.AccessControl;
import com.apeironsol.need.security.model.UserAccount;
import com.apeironsol.need.util.constants.AccessControlTypeConstant;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for access control.
 * 
 * @author Pradeep
 */
public interface AccessControlDao extends BaseDao<AccessControl> {

	/**
	 * Return access control by access control type for the given user account
	 * and reference id.
	 * 
	 * @param userAccount
	 *            user account.
	 * @param refId
	 *            reference id.
	 * @param accessType
	 *            access control type.
	 * @return access control by access control type for the given user account
	 *         and reference id.
	 */
	AccessControl findAccessControlByType(UserAccount userAccount, Long refId, AccessControlTypeConstant accessType);

	/**
	 * Return access control by access control type for the given user account.
	 * 
	 * @param userAccount
	 *            user account.
	 * @param accessType
	 *            access control type.
	 * @return access control by access control type for the given user account.
	 */
	AccessControl findAccessControlByType(UserAccount userAccount, AccessControlTypeConstant accessType);

	/**
	 * Returns collection of access control for given user account.
	 * 
	 * @param userAccount
	 *            user account.
	 * @return collection of access control for given user account.
	 */
	Collection<AccessControl> findAccessControlsByUser(final UserAccount userAccount);

	/**
	 * Removes all access controls for the given user account.
	 * 
	 * @param userAccount
	 *            user account.
	 */
	void removeAccessControlsByUserAccountId(final UserAccount userAccount);

}
