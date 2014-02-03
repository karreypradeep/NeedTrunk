package com.apeironsol.need.academics.dao;

import java.util.Collection;

import com.apeironsol.framework.BaseDao;
import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.core.model.Section;

public interface SectionExamDao extends BaseDao<SectionExam> {

	Collection<SectionExam> findSectionExamsByBranchId(Long branchId);

	Collection<SectionExam> findSectionExamsBySectionId(Long sectionId);

	Collection<SectionExam> findSectionExamsByKlassIdAndExamId(Long klassId, Long examId);

	Collection<Section> findSectionsByKlassIdAndExamId(Long klassId, Long examId);

	Collection<SectionExam> findSectionExamsByBranchIdAndExamId(Long branchId, Long examId);

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

	SectionExam findSectionExamsBySectionIdAndExamId(Long sectionId, Long examId);

	Collection<SectionExam> findSectionExamsByKlassId(Long klassId, final Long academicYearId);

	/**
	 * Find section exams by section id.
	 * 
	 * @param sectionId
	 *            section id.
	 * @return section exams by section id.
	 */
	Collection<SectionExam> findSectionExamsByExamIdsandAcademicYearId(Collection<Long> examIDs, final Long academicYearId);

	/**
	 * Find section exams by academic year id.
	 * 
	 * @param sectionId
	 *            section id.
	 * @return section exams by section id.
	 */
	Collection<SectionExam> findSectionExamsByAcademicYearId(final Long academicYearId);

	Collection<SectionExam> findSectionExamsBySectionIdsAndExamId(Collection<Long> sectionIds, Long examId);
}
