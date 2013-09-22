/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.Address;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for address entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("addressDao")
public class AddressDaoImpl extends BaseDaoImpl<Address> implements AddressDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Address findAddressByBranch(final Branch branch) {
		TypedQuery<Address> query = this.getEntityManager().createQuery(
				"select a from Branch b join b.address a where b = :branch", Address.class);
		query.setParameter("branch", branch);
		return query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Address findRelationAddressByRelationId(final Long relationId) {
		try {
			TypedQuery<Address> query = this.getEntityManager().createQuery(
					"select a from Relation r join r.address a where r.id = :relationId", Address.class);
			query.setParameter("relationId", relationId);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Address findStudentAddressByStudentId(final Long studentId) {
		try {
			TypedQuery<Address> query = this.getEntityManager().createQuery(
					"select a from Student s join s.address a where s.id = :studentId", Address.class);
			query.setParameter("studentId", studentId);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
