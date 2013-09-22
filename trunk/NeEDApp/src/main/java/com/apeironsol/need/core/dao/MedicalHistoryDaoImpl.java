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

import com.apeironsol.need.core.model.MedicalHistory;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * 
 * @author sunny
 *         Data access interface for Student Medical History entity
 *         implementation.
 * 
 */
@Repository("medicalHistoryDao")
public class MedicalHistoryDaoImpl extends BaseDaoImpl<MedicalHistory> implements MedicalHistoryDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MedicalHistory findMedicalHistoryByStudentId(final Long studentId) {
		try {
			TypedQuery<MedicalHistory> query = this.getEntityManager().createQuery("select mh from MedicalHistory mh where mh.student.id = :studentId",
					MedicalHistory.class);
			query.setParameter("studentId", studentId);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
