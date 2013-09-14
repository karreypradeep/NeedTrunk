package com.apeironsol.nexed.academics.service;

import java.util.Collection;

import com.apeironsol.nexed.academics.model.SectionExamSubject;

public interface SectionExamSubjectService {

	/**
	 * Find section exams by section id and exam id.
	 * 
	 * @param sectionExamId
	 *            exam type id.
	 * @return section exams by section id and exam id.
	 */
	Collection<SectionExamSubject> findSectionExamSubjectsBySectionIdAndSubjectExamId(Long sectionExamId);

	/**
	 * Find section exam subject by id.
	 * 
	 * @param sectionExamSubjectId
	 *            sectionExamSubjectId.
	 * @return section exam subject by id.
	 */
	SectionExamSubject findSectionExamSubjectById(Long sectionExamSubjectId);

}
