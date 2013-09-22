package com.apeironsol.need.core.dao;

import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.StudentAcademicYearFeeSummary;
import com.apeironsol.framework.BaseDaoImpl;

@Repository("studentAcademicYearFeeSummaryDao")
public class StudentAcademicYearFeeSummaryDaoImpl extends BaseDaoImpl<StudentAcademicYearFeeSummary> implements StudentAcademicYearFeeSummaryDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentAcademicYearFeeSummary findStudentAcademicYearFeeSummaryByStudentAcademicYearId(final Long studentAcademicYearId) {
		try {
			TypedQuery<StudentAcademicYearFeeSummary> query = this.getEntityManager().createQuery(
					"select s from StudentAcademicYearFeeSummary s where s.studentAcademicYear.id = :studentAcademicYearId",
					StudentAcademicYearFeeSummary.class);
			query.setParameter("studentAcademicYearId", studentAcademicYearId);
			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Collection<StudentAcademicYearFeeSummary> findStudentAcademicYearFeeSummaryByStudentAcademicYearIds(final Collection<Long> studentAcademicYearIds) {
		TypedQuery<StudentAcademicYearFeeSummary> query = this.getEntityManager().createQuery(
				"select s from StudentAcademicYearFeeSummary s where s.studentAcademicYear.id in :studentAcademicYearIds", StudentAcademicYearFeeSummary.class);
		query.setParameter("studentAcademicYearIds", studentAcademicYearIds);
		return query.getResultList();
	}

}
