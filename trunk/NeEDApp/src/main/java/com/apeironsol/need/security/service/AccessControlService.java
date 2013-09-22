/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.security.service;

import java.util.Collection;

import com.apeironsol.need.security.model.AccessControl;
import com.apeironsol.need.security.model.UserAccount;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service implementation interface for account access.
 * 
 */
public interface AccessControlService {

	/**
	 * Returns true if user has organization access.
	 * @param username user name.
	 * @return true if user has organization access.
	 * @throws BusinessException In case of exception. 
	 */
	boolean hasOrganizationAccess(String username) throws BusinessException;

	/**
	 * Returns true if user has branch access.
	 * @param branchId branch.
	 * @param username user name.
	 * @return true if user has branch access.
	 * @throws BusinessException In case of exception. 
	 */
	boolean hasBranchAccess(final Long branchId, final String username) throws BusinessException;

	/**
	 * Returns collection of access control for given user account.
	 * 
	 * @param userAccount
	 *            user account.
	 * @return collection of access control for given user account.
	 * @throws BusinessException In case of exception. 
	 */
	Collection<AccessControl> findAccessControlsByUser(final UserAccount userAccount) throws BusinessException;

	/**
	 * Saves the access control.
	 * 
	 * @return the saved access control.
	 * @throws BusinessException In case of exception. 
	 */
	AccessControl saveAccessControl(final AccessControl accessControl) throws BusinessException;

	/**
	 * Gets access control by id.
	 * 
	 * @return the access control.
	 * @throws BusinessException In case of exception. 
	 */
	AccessControl getAccessControlById(Long id) throws BusinessException;

	/**
	 * Removes all access controls for the given user account.
	 * 
	 * @param userAccount
	 *            user account.
	 * @throws BusinessException In case of exception. 
	 */
	void removeAccessControlsByUserAccountId(final UserAccount userAccount) throws BusinessException;
}
