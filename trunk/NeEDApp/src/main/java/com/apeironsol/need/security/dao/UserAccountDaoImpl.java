/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.security.dao;

import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.security.model.UserAccount;
import com.apeironsol.need.security.model.UserGroup;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for user account entity implementation.
 * 
 * @author Pradeep
 */
@Repository("userAccountDao")
public class UserAccountDaoImpl extends BaseDaoImpl<UserAccount> implements UserAccountDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserAccount findUserAccountByUsername(final String username) {
		try {
			TypedQuery<UserAccount> query = this.getEntityManager().createQuery(
					"select ua from UserAccount ua where ua.username = :username", UserAccount.class);
			query.setParameter("username", username);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean isUserGroupAssignedToUser(final UserGroup userGroup) {
		TypedQuery<UserAccount> query = this.getEntityManager().createQuery(
				"select ua from UserAccount ua join ua.userGroups ug where ug = :userGroup", UserAccount.class);
		query.setParameter("userGroup", userGroup);
		Collection<UserAccount> userAccounts = query.getResultList();

		if (userAccounts != null && !userAccounts.isEmpty()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
