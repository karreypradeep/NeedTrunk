/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.dao;

import java.util.Collection;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.financial.model.StudentLevelFee;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for Student Level Fee Implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository
public class StudentLevelFeeDaoImpl extends BaseDaoImpl<StudentLevelFee> implements StudentLevelFeeDao {

	@Override
	public Collection<StudentLevelFee> findStudentLevelFeesByStudentAcademicYearId(final Long studentAcademicYearId) {
		TypedQuery<StudentLevelFee> query = this.getEntityManager().createQuery(
				"select slf from StudentLevelFee slf where slf.studentAcademicYear.id = :studentAcademicYearId ", StudentLevelFee.class);
		query.setParameter("studentAcademicYearId", studentAcademicYearId);
		return query.getResultList();
	}

}
