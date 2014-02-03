/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.academics.dao;

import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.academics.model.StudentExamSubject;

/**
 * Service interface implementation for StudentExamSubjectDao.
 * 
 * @author Pradeep
 * 
 */
@Repository
public class StudentExamSubjectDaoImpl extends BaseDaoImpl<StudentExamSubject> implements StudentExamSubjectDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentExamSubject> findStudentExamSubjectsByStudentAcademicYearId(final Long studentAcademicYearId) {
		try {
			final TypedQuery<StudentExamSubject> query = getEntityManager()
					.createQuery(
							"select ses from StudentExamSubject ses where ses.studentAcademicYear.id = :studentAcademicYearId order by ses.sectionExamSubject.scheduledDate",
							StudentExamSubject.class);
			query.setParameter("studentAcademicYearId", studentAcademicYearId);
			return query.getResultList();
		} catch (final NoResultException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentExamSubject> findStudentExamSubjectsByStudentAcademicYearIdAndExamTypeId(final Long studentAcademicYearId, final Long examTypeId) {
		try {
			final TypedQuery<StudentExamSubject> query = getEntityManager()
					.createQuery(
							"select ses from StudentExamSubject ses where ses.studentAcademicYear.id = :studentAcademicYearId "
									+ " and ses.sectionExamSubject in (select seces SectionExamSubject seces where seces.sectionExam.exam.buildingBlock.id = :examTypeId) order by ses.sectionExamSubject.scheduledDate",
							StudentExamSubject.class);
			query.setParameter("studentAcademicYearId", studentAcademicYearId);
			query.setParameter("examTypeId", examTypeId);
			return query.getResultList();
		} catch (final NoResultException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentExamSubject> findStudentExamSubjectsByStudentAcademicYearIdAndSectionSubjectId(final Long studentAcademicYearId,
			final Long sectionSubjectId) {
		try {
			final TypedQuery<StudentExamSubject> query = getEntityManager()
					.createQuery(
							"select ses from StudentExamSubject ses where ses.studentAcademicYear.id = :studentAcademicYearId "
									+ " and ses.sectionExamSubject in (select seces SectionExamSubject seces where seces.sectionSubject.id = :sectionSubjectId) order by ses.sectionExamSubject.scheduledDate",
							StudentExamSubject.class);
			query.setParameter("studentAcademicYearId", studentAcademicYearId);
			query.setParameter("sectionSubjectId", sectionSubjectId);
			return query.getResultList();
		} catch (final NoResultException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentExamSubject> findStudentExamSubjectBySectionExamIdAndScoredMarksNotNull(final Long sectionExamId) {

		final TypedQuery<StudentExamSubject> query = getEntityManager().createQuery(
				"select e from StudentExamSubject e where e.sectionExamSubject.sectionExam.id = :sectionExamId and e.scoredMarks is not null",
				StudentExamSubject.class);
		query.setParameter("sectionExamId", sectionExamId);
		return query.getResultList();

	}

	@Override
	public Collection<StudentExamSubject> findStudentExamSubjectsBySectionExamId(final Long sectionExamId) {

		final TypedQuery<StudentExamSubject> query = getEntityManager().createQuery(
				"select e from StudentExamSubject e where e.sectionExamSubject.sectionExam.id = :sectionExamId", StudentExamSubject.class);
		query.setParameter("sectionExamId", sectionExamId);
		return query.getResultList();

	}

	@Override
	public Collection<StudentExamSubject> findStudentExamSubjectsBySectionExamSubjectId(final Long sectionExamSubjectId) {
		final TypedQuery<StudentExamSubject> query = getEntityManager().createQuery(
				"select e from StudentExamSubject e where e.sectionExamSubject.id = :sectionExamSubjectId", StudentExamSubject.class);
		query.setParameter("sectionExamSubjectId", sectionExamSubjectId);
		return query.getResultList();
	}

	@Override
	public StudentExamSubject findStudentExamSubjectByStudentAcademicYearIdAndSectionExamSubjectId(final Long studentAcademicYearId,
			final Long sectionExamSubjectId) {
		try {
			final TypedQuery<StudentExamSubject> query = getEntityManager()
					.createQuery(
							"select e from StudentExamSubject e where e.studentAcademicYear.id = :studentAcademicYearId and e.sectionExamSubject.id = :sectionExamSubjectId",
							StudentExamSubject.class);
			query.setParameter("studentAcademicYearId", studentAcademicYearId);
			query.setParameter("sectionExamSubjectId", sectionExamSubjectId);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

	@Override
	public Collection<StudentExamSubject> findStudentExamSubjectsBySubjectIdAndStudentAcademicYearId(final Long subjectId, final Long studentAcademicYearId) {
		final TypedQuery<StudentExamSubject> query = getEntityManager()
				.createQuery(
						"select e from StudentExamSubject e where e.sectionExamSubject.sectionSubject.subject.id = :subjectId and e.studentAcademicYear.id = :studentAcademicYearId",
						StudentExamSubject.class);
		query.setParameter("subjectId", subjectId);
		query.setParameter("studentAcademicYearId", studentAcademicYearId);
		return query.getResultList();
	}

	@Override
	public Collection<StudentExamSubject> findStudentExamAllSubjectsDOsBySubjectExamSubjectIds(final Collection<Long> subjectExamSubjectIds) {
		final TypedQuery<StudentExamSubject> query = getEntityManager().createQuery(
				"select e from StudentExamSubject e where e.sectionExamSubject.id in :subjectExamSubjectIds", StudentExamSubject.class);
		query.setParameter("subjectExamSubjectIds", subjectExamSubjectIds);
		return query.getResultList();
	}

	@Override
	public Collection<StudentExamSubject> findAbsentStudentSubjectsForSectionExamIds(final Collection<Long> sectionExamIds) throws ApplicationException {
		final TypedQuery<StudentExamSubject> query = getEntityManager().createQuery(
				"select e from StudentExamSubject e where e.sectionExamSubject.sectionExam.id in :sectionExamIds and e.studentExamSubjectStatus = 'ABSENT' ",
				StudentExamSubject.class);
		query.setParameter("sectionExamIds", sectionExamIds);
		return query.getResultList();
	}

	@Override
	public Collection<StudentExamSubject> findStudentExamSubjectsByStudentAcademicYearIdAndExamId(final Long studentAcademicYearId, final Long examId) {
		final TypedQuery<StudentExamSubject> query = getEntityManager()
				.createQuery(
						"select e from StudentExamSubject e where e.sectionExamSubject.sectionExam.exam.id = :examId and e.studentAcademicYear.id = :studentAcademicYearId ",
						StudentExamSubject.class);
		query.setParameter("examId", examId);
		query.setParameter("studentAcademicYearId", studentAcademicYearId);
		return query.getResultList();
	}

	@Override
	public Collection<StudentExamSubject> findAllStudentExamSubjectsForSectionExamIds(final Collection<Long> sectionExamIds) throws ApplicationException {
		final TypedQuery<StudentExamSubject> query = getEntityManager().createQuery(
				"select e from StudentExamSubject e where e.sectionExamSubject.sectionExam.id in :sectionExamIds", StudentExamSubject.class);
		query.setParameter("sectionExamIds", sectionExamIds);
		return query.getResultList();
	}

}
