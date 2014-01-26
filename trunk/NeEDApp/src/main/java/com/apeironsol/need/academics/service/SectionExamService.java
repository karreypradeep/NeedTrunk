package com.apeironsol.need.academics.service;

import java.util.Collection;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;
import com.apeironsol.need.academics.dataobject.SectionExamDO;
import com.apeironsol.need.academics.model.SectionExam;

public interface SectionExamService {

	/**
	 * Find section exams by section id and exam id.
	 * 
	 * @param sectionId
	 *            section id.
	 * @param examTypeId
	 *            exam type id.
	 * @return section exams by section id and exam id.
	 */
	Collection<SectionExam> findSectionExamsBySectionIdAndExamTypeId(Long sectionId, Long examTypeId);

	SectionExam findSectionExamById(Long id) throws BusinessException;

	Collection<SectionExamDO> getSectionExamsBySectionId(Long sectionId) throws BusinessException, SystemException;

	SectionExam findSectionExamsBySectionIdAndExamId(Long sectionId, Long examId);

	/**
	 * Find section exams by section id.
	 * 
	 * @param sectionId
	 *            section id.
	 * @return section exams by section id.
	 */
	Collection<SectionExam> findSectionExamsBySectionId(Long sectionId);

	/**
	 * Find section exams by sections.
	 * 
	 * @param sections
	 *            sections.
	 * @return section exams by sections.
	 */
	Collection<SectionExam> findSectionExamsByKlassId(final Long klassId, final Long academicYearId);

	/**
	 * Find section exams by section id.
	 * 
	 * @param sectionId
	 *            section id.
	 * @return section exams by section id.
	 */
	Collection<SectionExam> findSectionExamsByExamIdsandAcademicYearId(Collection<Long> examIDs, final Long academicYearId);

}
