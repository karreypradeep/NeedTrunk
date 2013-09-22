/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.util.dataobject.StudentFinancialAcademicYearDO;

/**
 * Service interface for student.
 * 
 * @author Pradeep
 * 
 */

public interface StudentPromotionService {

	/**
	 * Promote students to section supplied.
	 * 
	 * @param studentFinancialAcademicYearDOs
	 * @param toSection
	 */
	void promoteStudents(final Collection<StudentFinancialAcademicYearDO> studentFinancialAcademicYearDOs,
			final Section toSection);

}