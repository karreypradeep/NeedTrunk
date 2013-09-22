/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;
import java.util.Date;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.AcademicYearWeekEnd;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("academicYearWeekEndDao")
public class AcademicYearWeekEndDaoImpl extends BaseDaoImpl<AcademicYearWeekEnd> implements AcademicYearWeekEndDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AcademicYearWeekEnd> findAcademicYearWeekEndsByAcademicYearId(final Long academicYearId) {
		TypedQuery<AcademicYearWeekEnd> query = this.getEntityManager().createQuery(
				"select ayh from AcademicYearWeekEnd ayh where ayh.academicYear.id = :academicYearId",
				AcademicYearWeekEnd.class);
		query.setParameter("academicYearId", academicYearId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AcademicYearWeekEnd> findAcademicYearWeekEndsByAcademisYearIdBetweenDates(
			final Long academicYearId, final Date startDate, final Date endDate) throws BusinessException {
		TypedQuery<AcademicYearWeekEnd> query = this
				.getEntityManager()
				.createQuery(
						"select aywe from AcademicYearWeekEnd aywe where aywe.academicYear.id = :academicYearId and aywe.weekEndDate >= :startDate and aywe.weekEndDate <= :endDate ",
						AcademicYearWeekEnd.class);
		query.setParameter("academicYearId", academicYearId);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AcademicYearWeekEnd findAcademicYearWeekEndByAcademicYearIdAndWeekEndDate(final Long academicYearId,
			final Date weekEndDate) throws BusinessException {
		try {
			TypedQuery<AcademicYearWeekEnd> query = this
					.getEntityManager()
					.createQuery(
							"select aywe from AcademicYearWeekEnd aywe where aywe.academicYear.id = :academicYearId and aywe.weekEndDate = :weekEndDate",
							AcademicYearWeekEnd.class);
			query.setParameter("academicYearId", academicYearId);
			query.setParameter("weekEndDate", weekEndDate);
			return query.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}
}
