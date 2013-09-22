/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.EducationHistory;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for calendar year entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("educationHistoryDao")
public class EducationHistoryDaoImpl extends BaseDaoImpl<EducationHistory> implements EducationHistoryDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<EducationHistory> findEducationHistoriesByStudentId(final Long studentId) {

		TypedQuery<EducationHistory> query = this.getEntityManager().createQuery(
				"select eh from EducationHistory eh join eh.student a where a.id = :id", EducationHistory.class);
		query.setParameter("id", studentId);
		return query.getResultList();
	}

}
