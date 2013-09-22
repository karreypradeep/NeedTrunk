/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.financial.service;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.util.constants.StudentStatusConstant;
import com.apeironsol.need.util.dataobject.ClassFinancialDO;

/**
 * Service for class financial details.
 * 
 * @author Pradeep
 * 
 */
public interface ClassFinancialService {

	/**
	 * Retrieve class fee financial details by academic year.
	 * 
	 * @param classId
	 *            class id.
	 * @param academicYearId
	 *            academic year id.
	 * @return branch fee financial details by academic year.
	 */
	ClassFinancialDO getClassFeeFinancialDetailsByClassIdAndAcademicYearId(final Long classId, final Collection<Long> sectionIds, final Long academicYearId,
			final StudentStatusConstant studentStatus);

	/**
	 * Retrieve class fee financial details by academic year.
	 * 
	 * @param classIds
	 *            class ids.
	 * @param academicYearId
	 *            academic year id.
	 * @return branch fee financial details by academic year.
	 */
	Collection<ClassFinancialDO> getClassFeeFinancialDetailsByClassIdsAndAcademicYearId(final Collection<Long> classIds, final Collection<Long> sectionIds,
			final Long academicYearId, final StudentStatusConstant studentStatus);

	/**
	 * Retrieve class fee financial details by academic year.
	 * 
	 * @param classId
	 *            class id.
	 * @param academicYearId
	 *            academic year id.
	 * @return branch fee financial details by academic year.
	 */
	ClassFinancialDO getClassFeeFinancialDetailsByClassIdAndAcademicYearIdForDueDate(final Long classId, final Collection<Long> sectionIds,
			final Long academicYearId, final Date dueDate, final StudentStatusConstant studentStatus);

	/**
	 * Retrieve class fee financial details by academic year.
	 * 
	 * @param classIds
	 *            class ids.
	 * @param academicYearId
	 *            academic year id.
	 * @return branch fee financial details by academic year.
	 */
	Collection<ClassFinancialDO> getClassFeeFinancialDetailsByClassIdsAndAcademicYearIdForDueDate(final Collection<Long> classIds,
			final Collection<Long> sectionIds, final Long academicYearId, final Date dueDate, final StudentStatusConstant studentStatus);

}
