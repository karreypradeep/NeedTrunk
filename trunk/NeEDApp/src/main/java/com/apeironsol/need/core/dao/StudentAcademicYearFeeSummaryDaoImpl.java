package com.apeironsol.need.core.dao;

import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.need.core.model.StudentAcademicYearFeeSummary;

@Repository("studentAcademicYearFeeSummaryDao")
public class StudentAcademicYearFeeSummaryDaoImpl extends BaseDaoImpl<StudentAcademicYearFeeSummary> implements StudentAcademicYearFeeSummaryDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentAcademicYearFeeSummary findStudentAcademicYearFeeSummaryByStudentAcademicYearId(final Long studentAcademicYearId) {
		try {
			final TypedQuery<StudentAcademicYearFeeSummary> query = this.getEntityManager().createQuery(
					"select s from StudentAcademicYearFeeSummary s where s.studentAcademicYear.id = :studentAcademicYearId",
					StudentAcademicYearFeeSummary.class);
			query.setParameter("studentAcademicYearId", studentAcademicYearId);
			return query.getSingleResult();

		} catch (final NoResultException e) {
			return null;
		}
	}

	@Override
	public Collection<StudentAcademicYearFeeSummary> findStudentAcademicYearFeeSummaryByStudentAcademicYearIds(final Collection<Long> studentAcademicYearIds) {
		final TypedQuery<StudentAcademicYearFeeSummary> query = this.getEntityManager().createQuery(
				"select s from StudentAcademicYearFeeSummary s where s.studentAcademicYear.id in :studentAcademicYearIds", StudentAcademicYearFeeSummary.class);
		query.setParameter("studentAcademicYearIds", studentAcademicYearIds);
		return query.getResultList();
	}

	@Override
	public Collection<StudentAcademicYearFeeSummary> findStudentAcademicYearFeeSummaryByAcademicYearId(final Long academicYearId) {
		final TypedQuery<StudentAcademicYearFeeSummary> query = this.getEntityManager().createQuery(
				"select s from StudentAcademicYearFeeSummary s where s.studentAcademicYear.academicYear.id = :academicYearId",
				StudentAcademicYearFeeSummary.class);
		query.setParameter("academicYearId", academicYearId);
		return query.getResultList();
	}

}
