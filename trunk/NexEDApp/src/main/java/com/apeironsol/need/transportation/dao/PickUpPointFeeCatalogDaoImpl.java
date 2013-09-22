/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.dao;

import java.util.Collection;
import java.util.Date;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.transportation.model.PickUpPointFeeCatalog;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for class pick up point.
 * 
 * @author sandeep
 * 
 */
@Repository("pickUpPointFeeCatalogDao")
public class PickUpPointFeeCatalogDaoImpl extends BaseDaoImpl<PickUpPointFeeCatalog> implements PickUpPointFeeCatalogDao {

	@Override
	public Collection<PickUpPointFeeCatalog> findPickUpPointFeeCatalogsByPickUpPointFeeId(final Long pickUpPointFeeId) {
		try {
			TypedQuery<PickUpPointFeeCatalog> query = this.getEntityManager().createQuery(
					"select pupfp from PickUpPointFeeCatalog pupfp join pupfp.pickUpPointFee pupf where pupf.id= :pickUpPointFeeId)",
					PickUpPointFeeCatalog.class);
			query.setParameter("pickUpPointFeeId", pickUpPointFeeId);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Collection<PickUpPointFeeCatalog> findPickUpPointFeeCatalogsByPickUpPointFeeIdAndDueDate(final Long pickUpPointFeeId, final Date dueDate) {

		try {
			TypedQuery<PickUpPointFeeCatalog> query = this
					.getEntityManager()
					.createQuery(
							"select pupfp from PickUpPointFeeCatalog pupfp join pupfp.pickUpPointFee pupf where pupf.id= :pickUpPointFeeId and pupfp.dueDate <= :dueDate)",
							PickUpPointFeeCatalog.class);
			query.setParameter("pickUpPointFeeId", pickUpPointFeeId);
			query.setParameter("dueDate", dueDate);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
