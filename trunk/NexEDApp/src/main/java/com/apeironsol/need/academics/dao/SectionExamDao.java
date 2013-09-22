package com.apeironsol.need.academics.dao;

import java.util.Collection;

import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.framework.BaseDao;

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

}
