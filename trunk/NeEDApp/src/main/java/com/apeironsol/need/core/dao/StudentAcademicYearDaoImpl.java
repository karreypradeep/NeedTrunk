package com.apeironsol.need.core.dao;

import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.framework.BaseDaoImpl;

@Repository("studentAcademicYearDao")
public class StudentAcademicYearDaoImpl extends BaseDaoImpl<StudentAcademicYear> implements StudentAcademicYearDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentAcademicYear> findStudentAcademicYearByStudentId(final Long studentId) {

		TypedQuery<StudentAcademicYear> query = getEntityManager().createQuery(
				"select s from StudentAcademicYear s where s.student.id = :studentId", StudentAcademicYear.class);
		query.setParameter("studentId", studentId);
		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentAcademicYear findStudentCurrentOrMostRecentAcademicYearByStudentId(final Long studentId) {
		try {
			TypedQuery<StudentAcademicYear> query = getEntityManager()
					.createQuery(
							"select say from StudentAcademicYear say where say.student.id = :studentId and say.sequenceNr = (select max(say1.sequenceNr)  from StudentAcademicYear say1 where say1.student.id = :studentId )",
							StudentAcademicYear.class);
			query.setParameter("studentId", studentId);
			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentAcademicYear findStudentAcademicYearByStudentIdAndAcademicYearId(final Long studentId,
			final Long academicYearId) {
		try {
			TypedQuery<StudentAcademicYear> query = getEntityManager()
					.createQuery(
							"select s from StudentAcademicYear s where s.student.id = :studentId and s.academicYear.id = :academicYearId",
							StudentAcademicYear.class);
			query.setParameter("studentId", studentId);
			query.setParameter("academicYearId", academicYearId);
			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeStudentAcademicYearByStudendIdAndAcademicYearId(final Long studentId, final Long academicYearId) {
		Query query = getEntityManager()
				.createQuery(
						"delete from StudentAcademicYear s where s.student.id = :studentId and s.academicYear.id = :academicYearId");
		query.setParameter("studentId", studentId);
		query.setParameter("academicYearId", academicYearId);
		query.executeUpdate();
	}

}
