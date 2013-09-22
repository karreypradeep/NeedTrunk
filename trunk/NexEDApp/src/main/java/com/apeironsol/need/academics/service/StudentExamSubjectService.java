package com.apeironsol.need.academics.service;

import java.util.Collection;

import com.apeironsol.need.academics.model.StudentExamSubject;
import com.apeironsol.framework.exception.ApplicationException;

public interface StudentExamSubjectService {

	/**
	 * Find student exam subjects by section exam subject id.
	 * 
	 * @param sectionExamSubjectId
	 *            section exam subject id.
	 * @return student exam subjects by section exam subject id.
	 */
	Collection<StudentExamSubject> findStudentExamSubjectsByStudentIdAndSubjectExamId(Long sectionExamSubjectId);

	/**
	 * Find student exam subjects by section exam subject id.
	 * 
	 * @param sectionExamSubjectId
	 *            section exam subject id.
	 * @return student exam subjects by section exam subject id.
	 */
	Collection<StudentExamSubject> saveStudentExamSubjects(Collection<StudentExamSubject> studentExamSubjects) throws ApplicationException;
}
