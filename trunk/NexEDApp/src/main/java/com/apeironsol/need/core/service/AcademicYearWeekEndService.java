/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.AcademicYearWeekEnd;
import com.apeironsol.need.util.constants.WeekDayConstant;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service layer interface for academic year weekend DAO implementation.
 * 
 * @author Pradeep
 * 
 */
public interface AcademicYearWeekEndService {

	/**
	 * Find academic year weekends by academic year weekend Id.
	 * 
	 * @param academicYearId
	 *            academic year Id.
	 * @return collection of academic year weekends by academic year weekend Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<AcademicYearWeekEnd> findAcademicYearWeekEndsByAcademicYearId(Long academicYearId)
			throws BusinessException;

	/**
	 * Retrieve academic year weekend by object id.
	 * 
	 * @param id
	 *            id of academic year weekend.
	 * @return academic year weekend by object id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	AcademicYearWeekEnd findAcademicYearWeekEndById(Long id) throws BusinessException;

	/**
	 * Saves supplied academic year weekend.
	 * 
	 * @param academicYearWeekEnd
	 *            academic year weekend.
	 * @return academic year weekend saved.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 *             In case supplied parameter is not valid.
	 */
	AcademicYearWeekEnd saveAcademicYearWeekEnd(AcademicYearWeekEnd academicYearWeekEnd) throws BusinessException,
			InvalidArgumentException;

	/**
	 * Removes academic year weekend.
	 * 
	 * @param academicYearWeekEnd
	 *            academic year weekend to be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeAcademicYearWeekEnd(AcademicYearWeekEnd academicYearWeekEnd) throws BusinessException;

	/**
	 * Retrieves all academic year weekends.
	 * 
	 * @return collection of all academic year weekends.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<AcademicYearWeekEnd> findAllAcademicYearWeekEnds() throws BusinessException;

	/**
	 * Retrieves all academic year weekends.
	 * 
	 * @return collection of all academic year weekends.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<AcademicYearWeekEnd> createAcademicYearWeekEnds(final Date startDate, final Date endDate,
			final AcademicYear academicYear, final List<WeekDayConstant> weekDayConstants) throws BusinessException;

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
	Collection<AcademicYearWeekEnd> findAcademicYearWeekEndsByAcademicYearIdBetweenDates(Long academicYearId,
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
