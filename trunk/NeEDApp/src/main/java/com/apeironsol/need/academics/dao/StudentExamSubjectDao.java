package com.apeironsol.need.academics.dao;

import java.util.Collection;

import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.academics.model.StudentExamSubject;

public interface StudentExamSubjectDao extends BaseDao<StudentExamSubject> {

	/**
	 * Return collection of student exam subjects by student academic year Id.
	 * 
	 * @param studentAcademicYearId
	 *            student academic year Id.
	 * @return collection of student exam subjects by student academic year Id.
	 */
	Collection<StudentExamSubject> findStudentExamSubjectsByStudentAcademicYearId(final Long studentAcademicYearId);

	/**
	 * Return collection of student exam subjects by student academic year Id
	 * and exam type id.
	 * 
	 * @param studentAcademicYearId
	 *            student academic year Id.
	 * @param examTypeId
	 *            exam type id.
	 * @return collection of student exam subjects by student academic year Id
	 *         and exam type id.
	 */
	Collection<StudentExamSubject> findStudentExamSubjectsByStudentAcademicYearIdAndExamTypeId(final Long studentAcademicYearId, final Long examTypeId);

	/**
	 * Return collection of student exam subjects by student academic year Id
	 * and section subject id.
	 * 
	 * @param studentAcademicYearId
	 *            student academic year Id.
	 * @param sectionSubjectId
	 *            section subject id.
	 * @return
	 */
	Collection<StudentExamSubject> findStudentExamSubjectsByStudentAcademicYearIdAndSectionSubjectId(final Long studentAcademicYearId,
			final Long sectionSubjectId);

	/**
	 * 
	 * @param sectionExamId
	 * @return
	 */
	Collection<StudentExamSubject> findStudentExamSubjectBySectionExamIdAndScoredMarksNotNull(Long sectionExamId);

	/**
	 * 
	 * @param sectionExamId
	 */
	Collection<StudentExamSubject> findStudentExamSubjectsBySectionExamId(Long sectionExamId);

	/**
	 * 
	 * @param sectionExamSubjectId
	 * @return
	 */
	Collection<StudentExamSubject> findStudentExamSubjectsBySectionExamSubjectId(Long sectionExamSubjectId);

	/**
	 * 
	 * @param sectionExamSubjectId
	 * @return
	 */
	StudentExamSubject findStudentExamSubjectByStudentAcademicYearIdAndSectionExamSubjectId(Long studentAcademicYearId, Long sectionExamSubjectId);

	/**
	 * 
	 * @param subjectId
	 * @param studentAcademicYearId
	 * @return
	 */
	Collection<StudentExamSubject> findStudentExamSubjectsBySubjectIdAndStudentAcademicYearId(Long subjectId, Long studentAcademicYearId);

	/**
	 * Find section exams by section id and exam id.
	 * 
	 * @param sectionExamId
	 *            exam type id.
	 * @return section exams by section id and exam id.
	 */
	Collection<StudentExamSubject> findStudentExamAllSubjectsDOsBySubjectExamSubjectIds(final Collection<Long> subjectExamSubjectIds);

	/**
	 * Find student exam subjects by section exam subject id.
	 * 
	 * @param sectionExamSubjectId
	 *            section exam subject id.
	 * @return student exam subjects by section exam subject id.
	 */
	Collection<StudentExamSubject> findAbsentStudentSubjectsForSectionExamIds(Collection<Long> sectionExamIds) throws ApplicationException;

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
