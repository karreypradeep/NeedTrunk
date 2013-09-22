/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import com.apeironsol.need.core.model.AcademicYearHoliday;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for AcademicYearHoliday entity implementation.
 * 
 * @author Pradeep
 * 
 */
public interface AcademicYearHolidayDao extends BaseDao<AcademicYearHoliday> {

	/**
	 * Find academic year holidays by academic year Id.
	 * 
	 * @param academicYearHolidayId
	 *            academic year holiday Id.
	 * @return collection of academic year holidays by academic year holiday Id.
	 */
	Collection<AcademicYearHoliday> findAcademicYearHolidaysByAcademicYearId(Long academicYearId);

}
