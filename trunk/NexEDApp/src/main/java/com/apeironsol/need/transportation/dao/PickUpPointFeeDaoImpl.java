/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.dao;

import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.transportation.model.PickUpPointFee;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for pickup point entity implementation.
 * 
 * @author sandeep
 */
@Repository("pickUpPointFeeDao")
public class PickUpPointFeeDaoImpl extends BaseDaoImpl<PickUpPointFee> implements PickUpPointFeeDao {

	@Override
	public Collection<PickUpPointFee> findPickUpPointFeesByPickUpPointId(final Long pickUpPointId) {
		try {
			TypedQuery<PickUpPointFee> query = this.getEntityManager().createQuery(
					"select pupf from PickUpPointFee pupf join pupf.pickUpPoint pup where pup.id= :pickUpPointId)",
					PickUpPointFee.class);
			query.setParameter("pickUpPointId", pickUpPointId);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Collection<PickUpPointFee> findPickUpPointFeesByAcademicYearId(final Long academicYearId) {
		try {
			TypedQuery<PickUpPointFee> query = this.getEntityManager().createQuery(
					"select pupf from PickUpPointFee pupf join pupf.academicYear a where a.id = :academicYearId)",
					PickUpPointFee.class);
			query.setParameter("academicYearId", academicYearId);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public PickUpPointFee findPickUpPointFeeByPickUpPointIdAndAcademicYearId(final Long pickUpPointId,
			final Long academicYearId) {
		try {
			TypedQuery<PickUpPointFee> query = this
					.getEntityManager()
					.createQuery(
							"select pupf from PickUpPointFee pupf where pupf.pickUpPoint.id = :pickUpPointId and pupf.academicYear.id = :academicYearId)",
							PickUpPointFee.class);
			query.setParameter("pickUpPointId", pickUpPointId);
			query.setParameter("academicYearId", academicYearId);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
