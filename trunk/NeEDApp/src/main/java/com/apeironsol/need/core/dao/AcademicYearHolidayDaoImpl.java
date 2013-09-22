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

import com.apeironsol.need.core.model.AcademicYearHoliday;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("academicYearHolidayDao")
public class AcademicYearHolidayDaoImpl extends BaseDaoImpl<AcademicYearHoliday> implements AcademicYearHolidayDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AcademicYearHoliday> findAcademicYearHolidaysByAcademicYearId(final Long academicYearId) {
		TypedQuery<AcademicYearHoliday> query = this.getEntityManager().createQuery(
				"select ayh from AcademicYearHoliday ayh where ayh.academicYear.id = :academicYearId",
				AcademicYearHoliday.class);
		query.setParameter("academicYearId", academicYearId);
		return query.getResultList();
	}

}
