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
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.apeironsol.need.security.model.UserGroup;
import com.apeironsol.need.security.model.UserGroupAuthority;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface implementation for user group authority entity.
 * 
 * @author Pradeep
 */
@Repository("userGroupAuthorityDao")
public class UserGroupAuthorityDaoImpl extends BaseDaoImpl<UserGroupAuthority> implements UserGroupAuthorityDao {

	private static final Logger	log	= Logger.getLogger(UserGroupAuthorityDaoImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<UserGroupAuthority> findUserGroupAuthoritiesByUserGroup(UserGroup userGroup) {
		try {
			TypedQuery<UserGroupAuthority> query = this.getEntityManager()
					.createQuery("select uga from UserGroupAuthority uga where uga.userGroup = :userGroup",
							UserGroupAuthority.class);
			query.setParameter("userGroup", userGroup);
			return query.getResultList();
		} catch (NoResultException e) {
			log.info(e.getMessage());
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeUserGroupAuthoritiesByUserGroup(final UserGroup userGroup) {
		Query query = this.getEntityManager().createQuery(
				"delete from UserGroupAuthority uga where uga.userGroup = :userGroup");
		query.setParameter("userGroup", userGroup);
		query.executeUpdate();
	}
}
