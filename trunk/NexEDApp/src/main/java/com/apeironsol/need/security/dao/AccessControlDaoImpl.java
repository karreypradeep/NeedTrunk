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

import com.apeironsol.need.security.model.AccessControl;
import com.apeironsol.need.security.model.UserAccount;
import com.apeironsol.need.util.constants.AccessControlTypeConstant;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for organization access entity implementation.
 * 
 * @author Pradeep
 */
@Repository("accessControlDao")
public class AccessControlDaoImpl extends BaseDaoImpl<AccessControl> implements AccessControlDao {

	private static final Logger	log	= Logger.getLogger(AccessControlDaoImpl.class);

	public AccessControlDaoImpl() {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccessControl findAccessControlByType(final UserAccount userAccount, final Long refId,
			final AccessControlTypeConstant accessType) {
		try {
			TypedQuery<AccessControl> query = this
					.getEntityManager()
					.createQuery(
							"select oa from AccessControl oa where oa.userAccount = :userAccount and oa.refId =:refId and oa.accessControlType=:accessControlType",
							AccessControl.class);
			query.setParameter("userAccount", userAccount);
			query.setParameter("refId", refId);
			query.setParameter("accessControlType", accessType);
			return query.getSingleResult();
		} catch (NoResultException e) {
			log.info(e.getMessage());
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccessControl findAccessControlByType(final UserAccount userAccount,
			final AccessControlTypeConstant accessType) {
		try {
			TypedQuery<AccessControl> query = this
					.getEntityManager()
					.createQuery(
							"select oa from AccessControl oa where oa.userAccount = :userAccount and oa.accessControlType=:accessControlType",
							AccessControl.class);
			query.setParameter("userAccount", userAccount);
			query.setParameter("accessControlType", accessType);
			return query.getSingleResult();
		} catch (NoResultException e) {
			log.info(e.getMessage());
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AccessControl> findAccessControlsByUser(final UserAccount userAccount) {
		try {
			TypedQuery<AccessControl> query = this.getEntityManager().createQuery(
					"select ac from AccessControl ac where ac.userAccount = :userAccount", AccessControl.class);
			query.setParameter("userAccount", userAccount);
			return query.getResultList();
		} catch (NoResultException e) {
			log.info(e.getMessage());
			return null;
		}
	}

	/**
	 * {@inheritDoc} em.createQuery("DELETE FROM Country").executeUpdate();
	 */
	@Override
	public void removeAccessControlsByUserAccountId(final UserAccount userAccount) {
		Query query = this.getEntityManager().createQuery(
				"delete from AccessControl ac where ac.userAccount = :userAccount");
		query.setParameter("userAccount", userAccount);
		query.executeUpdate();
	}

}
