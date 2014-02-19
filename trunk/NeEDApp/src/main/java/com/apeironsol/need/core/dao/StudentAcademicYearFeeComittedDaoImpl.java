package com.apeironsol.need.core.dao;

import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.need.core.model.StudentAcademicYearFeeComitted;

@Repository("studentAcademicYearFeeComittedDao")
public class StudentAcademicYearFeeComittedDaoImpl extends BaseDaoImpl<StudentAcademicYearFeeComitted> implements StudentAcademicYearFeeComittedDao {

	@Override
	public StudentAcademicYearFeeComitted findStudentAcademicYearFeeComittedByStudentIdAndAcademicYearId(final Long studentId, final Long academicYearId) {
		try {
			final TypedQuery<StudentAcademicYearFeeComitted> query = this.getEntityManager().createQuery(
					"select s from StudentAcademicYearFeeComitted s where s.academicYear.id = :academicYearId and s.student.id = :studentId",
					StudentAcademicYearFeeComitted.class);
			query.setParameter("academicYearId", academicYearId);
			query.setParameter("studentId", studentId);
			return query.getSingleResult();

		} catch (final NoResultException e) {
			return null;
		}
	}

	@Override
	public Collection<StudentAcademicYearFeeComitted> findStudentAcademicYearFeeComittedByStudentId(final Long studentId) {
		final TypedQuery<StudentAcademicYearFeeComitted> query = this.getEntityManager().createQuery(
				"select s from StudentAcademicYearFeeComitted s where s.student.id = :studentId", StudentAcademicYearFeeComitted.class);
		query.setParameter("studentId", studentId);
		return query.getResultList();
	}

	@Override
	public Collection<StudentAcademicYearFeeComitted> findStudentAcademicYearFeeComittedByStudentIds(final Long studentIds) {
		final TypedQuery<StudentAcademicYearFeeComitted> query = this.getEntityManager().createQuery(
				"select s from StudentAcademicYearFeeComitted s where s.student.id in :studentIds", StudentAcademicYearFeeComitted.class);
		query.setParameter("studentIds", studentIds);
		return query.getResultList();
	}

}
