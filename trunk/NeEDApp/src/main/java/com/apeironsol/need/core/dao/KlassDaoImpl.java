/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.Klass;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for grade implementation entity.
 * 
 * @author Pradeep
 * 
 */
@Repository("klassDao")
public class KlassDaoImpl extends BaseDaoImpl<Klass> implements KlassDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Klass> findKlassByBranchId(final Long branchId) {
		TypedQuery<Klass> query = this.getEntityManager().createQuery(
				"select k from Klass k join k.branch b where b.id = :id", Klass.class);
		query.setParameter("id", branchId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Klass> findActiveKlassByBranchId(final Long branchId) {
		TypedQuery<Klass> query = this.getEntityManager().createQuery(
				"select k from Klass k join k.branch b where b.id = :id and k.active = :active", Klass.class);
		query.setParameter("id", branchId);
		query.setParameter("active", true);
		return query.getResultList();
	}

}
