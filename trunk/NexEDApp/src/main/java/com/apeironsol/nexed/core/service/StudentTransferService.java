/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.core.service;

import java.util.Collection;

import com.apeironsol.nexed.core.model.Section;
import com.apeironsol.nexed.util.dataobject.StudentFinancialAcademicYearDO;

/**
 * Service interface for student.
 * 
 * @author Pradeep
 * 
 */

public interface StudentTransferService {

	/**
	 * Transfer student to section supplied.
	 * 
	 * @param studentFinancialAcademicYearDO
	 * @param toSection
	 */
	void transferStudent(final Collection<StudentFinancialAcademicYearDO> studentFinancialAcademicYearDOs,
			final Section toSection);

}