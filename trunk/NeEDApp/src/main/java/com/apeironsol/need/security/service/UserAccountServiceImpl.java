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

import org.jasypt.spring.security3.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.apeironsol.need.core.dao.RelationDao;
import com.apeironsol.need.core.dao.StudentDao;
import com.apeironsol.need.core.model.Relation;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.hrms.dao.EmployeeDao;
import com.apeironsol.need.hrms.model.Employee;
import com.apeironsol.need.security.dao.UserAccountDao;
import com.apeironsol.need.security.dao.UserGroupDao;
import com.apeironsol.need.security.model.UserAccount;
import com.apeironsol.need.security.model.UserGroup;
import com.apeironsol.need.util.constants.UserAccountTypeConstant;
import com.apeironsol.framework.exception.BusinessException;

/**
 * @author sandeep
 * 
 */
@Service("userAccountService")
@Transactional(rollbackFor = Exception.class)
public class UserAccountServiceImpl implements UserAccountService {

	@Resource
	private AccessControlService	accessControlService;

	@Resource
	UserAccountDao					userAccountDao;

	@Resource
	UserGroupDao					userGroupDao;

	@Resource
	private EmployeeDao				employeeDao;

	@Resource
	private StudentDao				studentDao;

	@Resource
	private RelationDao				relationDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserAccount findUserAccountById(final Long id) throws BusinessException {
		return this.userAccountDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<UserAccount> findAllUserAccounts() throws BusinessException {
		return this.userAccountDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserAccount saveUserAccount(final UserAccount userAccount, final boolean savePassword)
			throws BusinessException {
		Assert.notNull(userAccount);
		this.checkUserAccountForDuplicateName(userAccount);
		if (savePassword) {
			PasswordEncoder encoder = new PasswordEncoder();
			String encodedPassword = encoder.encodePassword(userAccount.getPassword(), null);
			userAccount.setPassword(encodedPassword);
		}
		// Delete existing access controls for user account.
		if (userAccount.getId() != null) {
			this.accessControlService.removeAccessControlsByUserAccountId(userAccount);
		}
		return this.userAccountDao.persist(userAccount);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteUserAccount(final Long id) throws BusinessException {

		UserAccount userAccount = this.userAccountDao.findById(id);

		if (UserAccountTypeConstant.EMPLOYEE.equals(userAccount.getUserAccountType())) {
			Employee emp = this.employeeDao.findEmployeeByUsername(userAccount.getUsername());
			if (emp != null) {
				emp.setUserAccount(null);
				this.employeeDao.persist(emp);
			}

		} else if (UserAccountTypeConstant.STUDENT.equals(userAccount.getUserAccountType())) {
			Student stu = this.studentDao.findStudentByUsername(userAccount.getUsername());

			if (stu != null) {
				stu.setUserAccount(null);
				this.studentDao.persist(stu);

			}

		} else if (UserAccountTypeConstant.PARENT.equals(userAccount.getUserAccountType())) {

			Relation rel = this.relationDao.findRelationByUsername(userAccount.getUsername());

			if (rel != null) {

				rel.setUserAccount(null);
				this.relationDao.persist(rel);
			}

		}

		this.userAccountDao.remove(userAccount);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserAccount findUserAccountByName(final String name) throws BusinessException {
		return this.userAccountDao.findUserAccountByUsername(name);
	}

	/**
	 * Retrieves the user account by id.
	 * 
	 * @param id
	 *            the id of user account to be retrieved.
	 * @param retrieveUserGroups
	 * @return
	 */
	@Override
	public UserAccount findUserAccountById(final Long id, final boolean retrieveUserGroups) throws BusinessException {
		UserAccount userAccount = this.userAccountDao.findById(id);
		if (retrieveUserGroups) {
			userAccount.setUserGroups(this.userGroupDao.findUserGroupsByUserAccountId(userAccount.getId()));

		}
		return userAccount;
	}

	/**
	 * Returns true is user group is assigned to any user.
	 * 
	 * @param userGroup
	 *            user group.
	 * @return Boolean.
	 */
	@Override
	public Boolean isUserGroupAssignedToUser(final UserGroup userGroup) throws BusinessException {
		return this.userAccountDao.isUserGroupAssignedToUser(userGroup);
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
	private void checkUserAccountForDuplicateName(final UserAccount userAccount) throws BusinessException {
		UserAccount userAccountFromDB = null;
		try {
			userAccountFromDB = this.findUserAccountByName(userAccount.getUsername());
		} catch (NoResultException noResultException) {
		}
		if (userAccountFromDB != null) {
			if (userAccount.getId() == null || !userAccountFromDB.getId().equals(userAccount.getId())) {
				throw new BusinessException("User account with user name " + userAccount.getUsername()
						+ " already exists.");
				/*
				 * throw new BusinessException(
				 * BusinessMessages.getResourceBundleName(),
				 * BusinessMessages.MSG_USER_ACCOUNT_USER_NAME_ALREADY_EXISTS,
				 * new Object[] { userAccount.getUsername() } );
				 */
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserAccount findUserAccountByUsername(final String username) {
		return this.userAccountDao.findUserAccountByUsername(username);
	}

}
