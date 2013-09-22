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

import com.apeironsol.need.core.model.AcademicYearWeekEnd;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for AcademicYearWeekEnd entity implementation.
 * 
 * @author Pradeep
 * 
 */
public interface AcademicYearWeekEndDao extends BaseDao<AcademicYearWeekEnd> {

	/**
	 * Find academic year weekends by academic year Id.
	 * 
	 * @param academicYearWeekEndId
	 *            academic year weekend Id.
	 * @return collection of academic year weekends by academic year weekend Id.
	 */
	Collection<AcademicYearWeekEnd> findAcademicYearWeekEndsByAcademicYearId(Long academicYearId);

	/**
	 * Find academic year weekends by academic year id and between dates.
	 * 
	 * @param academicYearId
	 *            academicYearId.
	 * @param startDate
	 *            startDate.
	 * @param endDate
	 *            endDate.
	 * @return collection academic year weekends by academic year id and between
	 *         dates.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<AcademicYearWeekEnd> findAcademicYearWeekEndsByAcademisYearIdBetweenDates(Long academicYearId,
			final Date startDate, final Date endDate) throws BusinessException;

	/**
	 * Find academic year weekends by academic year id and week end date.
	 * 
	 * @param academicYearId
	 *            academicYearId.
	 * @param weekEndDate
	 *            weekEndDate.
	 * @return collection academic year weekends by academic year id and week
	 *         end date.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	AcademicYearWeekEnd findAcademicYearWeekEndByAcademicYearIdAndWeekEndDate(Long academicYearId,
			final Date weekEndDate) throws BusinessException;
}
