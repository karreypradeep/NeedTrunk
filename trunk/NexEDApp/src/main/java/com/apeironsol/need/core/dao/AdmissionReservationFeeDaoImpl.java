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

import com.apeironsol.need.core.model.AdmissionReservationFee;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * @author sunny
 * Data access interface for admission reservation fee registration fee entity
 *         implementation.
 *
 */
@Repository
public class AdmissionReservationFeeDaoImpl  extends BaseDaoImpl<AdmissionReservationFee> implements AdmissionReservationFeeDao {

	@Override
	public AdmissionReservationFee findAdmissionReservationFeeByStudentID(final Long studentId) {
		try {
			TypedQuery<AdmissionReservationFee> query = getEntityManager().createQuery(
					"select arf from AdmissionReservationFee arf where arf.student.id = :studentId", AdmissionReservationFee.class);
			query.setParameter("studentId", studentId);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
