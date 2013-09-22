package com.apeironsol.need.core.dao;

import java.util.Collection;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.StudentRelation;
import com.apeironsol.framework.BaseDaoImpl;

@Repository(value = "studentRelationDao")
public class StudentRelationDaoImpl extends BaseDaoImpl<StudentRelation> implements StudentRelationDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentRelation> findStudentRelationsByStudentId(final Long studentId) {
		TypedQuery<StudentRelation> query = this.getEntityManager().createQuery(
				"select sr from StudentRelation sr where sr.studentRelationPK.studentsId=:studentId",
				StudentRelation.class);
		query.setParameter("studentId", studentId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentRelation> findStudentRelationsByRelationId(final Long relationId) {
		TypedQuery<StudentRelation> query = this.getEntityManager().createQuery(
				"select sr from StudentRelation sr where sr.studentRelationPK.relationsId=:relationId",
				StudentRelation.class);
		query.setParameter("relationId", relationId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int removeStudentRelationsByStudentId(final Long studentId) {
		Query query = this.getEntityManager().createQuery(
				"delete from StudentRelation sr where sr.studentRelationPK.studentsId=:studentId");
		query.setParameter("studentId", studentId);
		return query.executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int removeStudentRelationsByRelationId(final Long relationId) {
		Query query = this.getEntityManager().createQuery(
				"delete from StudentRelation sr where sr.studentRelationPK.relationsId=:relationId");
		query.setParameter("relationId", relationId);
		return query.executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int removeStudentRelationByStudentIdAndRelationId(final Long studentId, final Long relationId) {
		Query query = this
				.getEntityManager()
				.createQuery(
						"delete from StudentRelation sr where sr.studentRelationPK.studentsId=:studentId and sr.studentRelationPK.relationsId=:relationId");
		query.setParameter("studentId", studentId);
		query.setParameter("relationId", relationId);
		return query.executeUpdate();
	}
}
