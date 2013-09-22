/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.need.core.model.AcademicYearHoliday;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service layer interface for academic year holiday DAO implementation.
 * 
 * @author Pradeep
 * 
 */
public interface AcademicYearHolidayService {

	/**
	 * Find academic year holidays by academic year holiday Id.
	 * 
	 * @param academicYearId
	 *            academic year Id.
	 * @return collection of academic year holidays by academic year holiday Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<AcademicYearHoliday> findAcademicYearHolidaysByAcademicYearId(Long academicYearId)
			throws BusinessException;

	/**
	 * Retrieve academic year holiday by object id.
	 * 
	 * @param id
	 *            id of academic year holiday.
	 * @return academic year holiday by object id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	AcademicYearHoliday findAcademicYearHolidayById(Long id) throws BusinessException;

	/**
	 * Saves supplied academic year holiday.
	 * 
	 * @param academicYearHoliday
	 *            academic year holiday.
	 * @return academic year holiday saved.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 *             In case supplied parameter is not valid.
	 */
	AcademicYearHoliday saveAcademicYearHoliday(AcademicYearHoliday academicYearHoliday) throws BusinessException,
			InvalidArgumentException;

	/**
	 * Removes academic year holiday.
	 * 
	 * @param academicYearHoliday
	 *            academic year holiday to be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeAcademicYearHoliday(AcademicYearHoliday academicYearHoliday) throws BusinessException;

	/**
	 * Retrieves all academic year holidays.
	 * 
	 * @return collection of all academic year holidays.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<AcademicYearHoliday> findAllAcademicYearHolidays() throws BusinessException;

}
