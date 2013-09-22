/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */

package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;
import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.util.searchcriteria.SectionSearchCriteria;

/**
 * Service interface for section.
 * 
 * @author Pradeep
 */
public interface SectionService {

	/**
	 * Save section.
	 * 
	 * @param section
	 *            section to be saved.
	 * @return
	 */
	Section saveSection(Section section);

	/**
	 * Save section.
	 * 
	 * @param section
	 *            section to be saved.
	 * @throws BusinessException
	 * @throws SystemException
	 */
	void removeSection(Section section) throws BusinessException, SystemException;

	/**
	 * Find section by id.
	 * 
	 * @param id
	 *            section id.
	 * @return
	 */
	Section findSectionById(Long id);

	/**
	 * Find all sections.
	 * 
	 * @return
	 */
	Collection<Section> findAllSections();

	/**
	 * Find all sections by class id.
	 * 
	 * @param classId
	 *            class id.
	 * @return collection of all sections by class id.
	 */
	Collection<Section> findAllSectionsByKlassId(Long id);

	/**
	 * Find all sections by branch id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return collection of all sections by branch id.
	 */
	Collection<Section> findAllSectionsByBranchId(Long id);

	/**
	 * Find all active sections by class id.
	 * 
	 * @param klassId
	 *            class id.
	 * @return collection of all active sections by class id.
	 */
	Collection<Section> findActiveSectionsByKlassId(Long id);

	/**
	 * Find all active sections by class id and academic year id.
	 * 
	 * @param klassId
	 *            class id.
	 * @param academicYearId
	 *            academic year id.
	 * @return collection of all active sections by class id and academic year
	 *         id.
	 */
	Collection<Section> findActiveSectionsByKlassIdAndAcademicYearId(Long klassId, Long academicYearId);

	/**
	 * Find all active sections by class ids and academic year id.
	 * 
	 * @param klassIds
	 *            class ids.
	 * @param academicYearId
	 *            academic year id.
	 * @return collection of all active sections by class id and academic year
	 *         id.
	 */
	Collection<Section> findActiveSectionsByKlassIdsAndAcademicYearId(Collection<Long> klassIds, Long academicYearId);

	/**
	 * Activate section.
	 * 
	 * @param section
	 *            section to be activated.
	 * @return
	 */
	Section activateSection(Section section);

	/**
	 * De-activate section.
	 * 
	 * @param section
	 *            section to be de activated.
	 * @return
	 */
	Section deactivateSection(Section section);

	/**
	 * Find section exams by section id.
	 * 
	 * @param sectionId
	 *            section id.
	 * @return
	 */
	Collection<SectionExam> findSectionExamsBySectionId(Long sectionId);

	/**
	 * Retrieve active sections for which the exam with id as examId is not
	 * assigned to.
	 * 
	 * @param klassId
	 *            class id.
	 * @param examId
	 *            exam id.
	 * @return
	 */
	Collection<Section> findActiveAndExamUnAssignedSectionsByKlassIdAndExamId(Long klassId, Long examId);

	/**
	 * Retrieve active sections for which the exam with id as examId is
	 * assigned.
	 * 
	 * @param klassId
	 *            class id.
	 * @param examId
	 *            exam id.
	 * @return
	 */
	Collection<Section> findExamAssignedSectionsByKlassIdAndExamId(Long klassId, Long examId);

	/**
	 * Validates sections of class for activation of academic year.
	 * 
	 * @param academicYear
	 *            academic year.
	 * @param klass
	 *            class.
	 */
	void validateSectionForAcademicYearValidation(final AcademicYear academicYear, final Klass klass) throws BusinessException;

	/**
	 * Find all sections for academic year.
	 * 
	 * @param academicYearId
	 *            academic year id.
	 * @return academic year id.
	 */
	Collection<Section> findAllSectionsByAcademicYearId(Long academicYearId);

	/**
	 * Find all sections for academic year with status supplied.
	 * 
	 * @param academicYearId
	 *            academic year id.
	 * @param status
	 *            status.
	 * @return academic year id.
	 */
	Collection<Section> findAllSectionsByAcademicYearIdAndStatus(Long academicYearId, final boolean status);

	/**
	 * Find all sections by class id and academic year id.
	 * 
	 * @param klassId
	 *            class id.
	 * @param academicYearId
	 *            academic year id.
	 * @return collection of all active sections by class id and academic year
	 *         id.
	 */
	Collection<Section> findAllSectionsByKlassIdAndAcademicYearId(Long klassId, Long academicYearId);

	/**
	 * Find all active sections by class ids and academic year id.
	 * 
	 * @param klassIds
	 *            class ids.
	 * @param academicYearId
	 *            academic year id.
	 * @return collection of all active sections by class id and academic year
	 *         id.
	 */
	Collection<Section> findSectionsBySearchCriteria(SectionSearchCriteria sectionSearchCriteria);

}
