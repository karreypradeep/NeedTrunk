/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.apeironsol.need.transportation.model.PickUpPoint;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for pickup point entity implementation.
 * 
 * @author sandeep
 */
@Repository("pickUpPointDao")
public class PickUpPointDaoImpl extends BaseDaoImpl<PickUpPoint> implements PickUpPointDao {

	private static final Logger	log	= Logger.getLogger(PickUpPointDaoImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PickUpPoint> findPickUpPointsByNameOrCode(final String name, final String code) {
		try {
			TypedQuery<PickUpPoint> query = this.getEntityManager().createQuery(
					"select pup from PickUpPoint pup where pup.name = :name or pup.code = :code", PickUpPoint.class);
			query.setParameter("name", name);
			query.setParameter("code", code);
			return query.getResultList();
		} catch (NoResultException ex) {
			log.info(ex.getMessage());
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PickUpPoint> findPickUpPointsByBranchId(final Long branchId) {
		TypedQuery<PickUpPoint> query = this.getEntityManager().createQuery(
				"select pup from PickUpPoint pup join pup.branch b where b.id = :id", PickUpPoint.class);
		query.setParameter("id", branchId);
		return query.getResultList();
	}

	@Override
	public Collection<PickUpPoint> findPickUpPointsByRouteId(final Long id) {
		TypedQuery<PickUpPoint> query = this.getEntityManager().createQuery(
				"select p from PickUpPoint p join p.routes r where r.id=:id", PickUpPoint.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	@Override
	public List<PickUpPoint> findActivePickUpPointsByBranchId(final Long branchId) throws BusinessException {
		TypedQuery<PickUpPoint> query = this.getEntityManager().createQuery(
				"select p from PickUpPoint p join p.branch b where b.id = :id and p.active = :active", PickUpPoint.class);
		query.setParameter("id", branchId);
		query.setParameter("active", true);
		return query.getResultList();
	}

}
