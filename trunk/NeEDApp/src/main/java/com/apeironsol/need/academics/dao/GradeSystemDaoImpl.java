/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.academics.dao;

import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.need.academics.model.GradeSystem;
import com.apeironsol.need.util.NonNull;

/**
 * Data access interface for employee salary entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("gradeSystemDao")
public class GradeSystemDaoImpl extends BaseDaoImpl<GradeSystem> implements GradeSystemDao {

	@Override
	public Collection<GradeSystem> findAllGradeSystems(@NonNull final Long branchId) {
		TypedQuery<GradeSystem> query = this.getEntityManager().createQuery("select gs from GradeSystem gs where gs.branch.id  = :branchId", GradeSystem.class);
		query.setParameter("branchId", branchId);
		return query.getResultList();
	}

	@Override
	public GradeSystem findDefaultGradeSystemForBranch(@NonNull final Long branchId) {
		GradeSystem result = null;
		try {
			TypedQuery<GradeSystem> query = this.getEntityManager().createQuery(
					"select gs from GradeSystem gs where gs.branch.id  = :branchId and gs.defaultGrade = true", GradeSystem.class);
			query.setParameter("branchId", branchId);
			result = query.getSingleResult();
		} catch (NoResultException nre) {

		}
		return result;
	}

}
