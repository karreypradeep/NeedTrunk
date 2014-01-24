/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.academics.dao;

import java.util.Collection;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.need.academics.model.GradeSystemRange;
import com.apeironsol.need.util.NonNull;

/**
 * Data access interface for employee salary entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("gradeSystemRangeDao")
public class GradeSystemRangeDaoImpl extends BaseDaoImpl<GradeSystemRange> implements GradeSystemRangeDao {

	@Override
	public Collection<GradeSystemRange> findAllGradingSystemRangesByGradeSystem(@NonNull final Long gradeSystemId) {
		TypedQuery<GradeSystemRange> query = this.getEntityManager().createQuery(
				"select gs from GradeSystemRange gs where gs.gradeSystem.id  = :gradeSystemId", GradeSystemRange.class);
		query.setParameter("gradeSystemId", gradeSystemId);
		return query.getResultList();
	}

}
