package com.apeironsol.need.academics.service;

import java.util.Collection;

import com.apeironsol.need.academics.model.SectionExamSubject;

public interface SectionExamSubjectService {

	/**
	 * Find section exams by section id and exam id.
	 * 
	 * @param sectionExamId
	 *            exam type id.
	 * @return section exams by section id and exam id.
	 */
	Collection<SectionExamSubject> findSectionExamSubjectsBySubjectExamId(Long sectionExamId);

	/**
	 * Find section exam subject by id.
	 * 
	 * @param sectionExamSubjectId
	 *            sectionExamSubjectId.
	 * @return section exam subject by id.
	 */
	SectionExamSubject findSectionExamSubjectById(Long sectionExamSubjectId);

}
