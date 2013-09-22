/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.util.constants.StudentSectionStatusConstant;

/**
 * Service Interface forStudentSection.
 * 
 * @author Pradeep
 * 
 */
public interface StudentSectionService {

	/**
	 * Find student section by student academic year and status.
	 * 
	 * @param studentAcademicYearId
	 *            student academic year id.
	 * @param status
	 *            status.
	 * @return
	 */
	StudentSection findStudentSectionByStudentAcademicYearIdAndStatus(final Long studentAcademicYearId, final StudentSectionStatusConstant status);

	/**
	 * Find latest student section by student academic year.
	 * 
	 * @param studentAcademicYearId
	 *            student academic year id.
	 * @return
	 */
	StudentSection findLatestStudentSectionByStudentAcademicYearId(final Long studentAcademicYearId);

	/**
	 * Find student section by student and section.
	 * 
	 * @param studentId
	 *            student id.
	 * @param sectionId
	 *            sectionId.
	 * @return
	 */
	StudentSection findStudentSectionByStudentIdAndSectionId(final Long studentId, final Long sectionId);

	/**
	 * Find student sections by student academic year.
	 * 
	 * @param studentAcademicYearId
	 *            student academic year id.
	 * @return
	 */
	Collection<StudentSection> findStudentSectionsByStudentAcademicYearId(final Long studentAcademicYearId);

	/**
	 * Find student sections by student academic year.
	 * 
	 * @param studentAcademicYearId
	 *            student academic year id.
	 * @return
	 */
	Collection<StudentSection> findStudentStudentSectionStatusAndSection(final StudentSectionStatusConstant studentSectionStatus, final Section section);
}
