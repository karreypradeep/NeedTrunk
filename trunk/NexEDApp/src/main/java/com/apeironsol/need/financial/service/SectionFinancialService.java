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
import com.apeironsol.need.util.dataobject.SectionFinancialDO;

/**
 * Service for Section financial details.
 * 
 * @author Pradeep
 * 
 */
public interface SectionFinancialService {

	/**
	 * Retrieve section fee financial details by academic year.
	 * 
	 * @param sectionId
	 *            section id.
	 * @param academicYearId
	 *            academic year id.
	 * @return branch fee financial details by academic year.
	 */
	SectionFinancialDO getSectionFeeFinancialDetailsBySectionIdAndAcademicYearId(final Long sectionId, final Long academicYearId,
			final StudentStatusConstant studentStatus);

	/**
	 * Retrieve section fee financial details by academic year.
	 * 
	 * @param sectionIds
	 *            section ids.
	 * @param academicYearId
	 *            academic year id.
	 * @return branch fee financial details by academic year.
	 */
	Collection<SectionFinancialDO> getSectionFeeFinancialDetailsBySectionIdsAndAcademicYearId(final Collection<Long> sectionIds, final Long academicYearId,
			final StudentStatusConstant studentStatus);

	/**
	 * Retrieve section fee financial details by academic year.
	 * 
	 * @param sectionId
	 *            section id.
	 * @param academicYearId
	 *            academic year id.
	 * @return branch fee financial details by academic year.
	 */
	SectionFinancialDO getSectionFeeFinancialDetailsBySectionIdAndAcademicYearIdForDueDate(final Long sectionId, final Long academicYearId, final Date dueDate,
			final StudentStatusConstant studentStatus);

	/**
	 * Retrieve section fee financial details by academic year.
	 * 
	 * @param sectionIds
	 *            section ids.
	 * @param academicYearId
	 *            academic year id.
	 * @return branch fee financial details by academic year.
	 */
	Collection<SectionFinancialDO> getSectionFeeFinancialDetailsBySectionIdAndAcademicYearIdForDueDate(final Collection<Long> sectionIds,
			final Long academicYearId, final Date dueDate, final StudentStatusConstant studentStatus);

	/**
	 * 
	 * @param sectionId
	 * @param academicYearId
	 * @param dueDate
	 * @param clearStudentFinancialDOsIndicator
	 * @return
	 */
	SectionFinancialDO getSectionFeeFinancialDetailsBySectionIdAndAcademicYearIdForDueDate(final Long sectionId, final Long academicYearId, final Date dueDate,
			final StudentStatusConstant studentStatus, final boolean clearStudentFinancialDOsIndicator);
}
