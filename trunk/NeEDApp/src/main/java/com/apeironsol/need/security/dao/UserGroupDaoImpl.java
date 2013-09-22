package com.apeironsol.need.security.dao;

import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.security.model.UserGroup;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for user group entity.
 * 
 * @author Pradeep
 */
@Repository(value = "userGroupDao")
public class UserGroupDaoImpl extends BaseDaoImpl<UserGroup> implements UserGroupDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<UserGroup> findUserGroupsByUserAccountId(final Long userAccountId) {
		TypedQuery<UserGroup> query = this.getEntityManager().createQuery(
				"select distinct ug from UserAccount ua join ua.userGroups ug where ua.id=:id", UserGroup.class);
		query.setParameter("id", userAccountId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserGroup findUserGroupByName(final String name) {
		try {
			TypedQuery<UserGroup> query = this.getEntityManager().createQuery(
					"select ug from UserGroup ug where ug.name = :name", UserGroup.class);
			query.setParameter("name", name);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
