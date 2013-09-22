/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.security.service;

import java.util.Collection;

import com.apeironsol.need.security.model.UserAccount;
import com.apeironsol.need.security.model.UserGroup;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service Interface for user accounts. This service act as controller for user
 * account details.
 * 
 * @author Sandeep
 * 
 */
public interface UserAccountService {

	/**
	 * Retrieves the user account by id.
	 * 
	 * @param id
	 *            the id of user account to be retrieved.
	 * @return
	 */
	UserAccount findUserAccountById(Long id) throws BusinessException;

	/**
	 * Retrieves all user accounts available.
	 * 
	 * @return all user accounts available.
	 */
	Collection<UserAccount> findAllUserAccounts() throws BusinessException;

	/**
	 * Saves the user account.
	 * 
	 * @return the saved user account.
	 */
	UserAccount saveUserAccount(UserAccount userAccount, final boolean savePassword) throws BusinessException;

	/**
	 * Deletes the user account.
	 * 
	 */
	void deleteUserAccount(final Long id) throws BusinessException;

	/**
	 * Retrieves the user account by named.
	 * 
	 * @param name
	 *            the name of user account to be retrieved.
	 * @return
	 */
	UserAccount findUserAccountByName(String name) throws BusinessException;

	/**
	 * Retrieves the user account by id.
	 * 
	 * @param id
	 *            the id of user account to be retrieved.
	 * @param retrieveUserGroups
	 * @return
	 */
	UserAccount findUserAccountById(Long id, final boolean retrieveUserGroups) throws BusinessException;

	/**
	 * Returns true is user group is assigned to any user.
	 * 
	 * @param userGroup
	 *            user group.
	 * @return Boolean.
	 */
	Boolean isUserGroupAssignedToUser(UserGroup userGroup) throws BusinessException;

	/**
	 * Returns user account by user name.
	 * 
	 * @param username
	 *            user name.
	 * @return user account by user name.
	 */
	UserAccount findUserAccountByUsername(String username);

}
