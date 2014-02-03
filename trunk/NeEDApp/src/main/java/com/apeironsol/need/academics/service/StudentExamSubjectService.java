package com.apeironsol.need.academics.service;

import java.util.Collection;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.academics.dataobject.StudentExamAllSubjectsDO;
import com.apeironsol.need.academics.model.StudentExamSubject;

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

	/**
	 * Find section exams by section id and exam id.
	 * 
	 * @param sectionExamId
	 *            exam type id.
	 * @return section exams by section id and exam id.
	 */
	Collection<StudentExamAllSubjectsDO> findStudentExamAllSubjectsDOsBySubjectExamId(Long sectionExamId);

	/**
	 * Find student exam subjects by section exam subject id.
	 * 
	 * @param sectionExamSubjectId
	 *            section exam subject id.
	 * @return student exam subjects by section exam subject id.
	 */
	Collection<StudentExamAllSubjectsDO> saveStudentExamAllSubjectsDOs(Collection<StudentExamAllSubjectsDO> studentExamAllSubjectsDOs)
			throws ApplicationException;

	/**
	 * Find student exam subjects by section exam subject id.
	 * 
	 * @param sectionExamSubjectId
	 *            section exam subject id.
	 * @return student exam subjects by section exam subject id.
	 */
	Collection<StudentExamSubject> findAbsentStudentSubjectsForSectionExamIds(Collection<Long> sectionExamIds) throws ApplicationException;

	/**
	 * Find student exam subjects by section exam subject id.
	 * 
	 * @param sectionExamSubjectId
	 *            section exam subject id.
	 * @return student exam subjects by section exam subject id.
	 */
	Collection<StudentExamSubject> findStudentExamSubjectsByStudentAcademicYearIdAndExamId(final Long studentAcademicYearId, final Long examId);

	/**
	 * Find student exam subjects by section exam subject id.
	 * 
	 * @param sectionExamSubjectId
	 *            section exam subject id.
	 * @return student exam subjects by section exam subject id.
	 */
	Collection<StudentExamSubject> findAllStudentExamSubjectsForSectionExamIds(Collection<Long> sectionExamIds) throws ApplicationException;

}
