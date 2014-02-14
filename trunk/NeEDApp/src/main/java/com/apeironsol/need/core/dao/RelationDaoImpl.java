package com.apeironsol.need.core.dao;

import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.need.core.model.Relation;

@Repository
public class RelationDaoImpl extends BaseDaoImpl<Relation> implements RelationDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Relation> findRelationsByStudentId(final Long studentId) {
		final TypedQuery<Relation> query = this.getEntityManager().createQuery("select r from Relation r join r.students s where s.id=:id", Relation.class);
		query.setParameter("id", studentId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Relation findRelationByUsername(final String username) {
		try {
			final TypedQuery<Relation> query = this.getEntityManager().createQuery("select r from Relation r where r.userAccount.username = :username",
					Relation.class);
			query.setParameter("username", username);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

	@Override
	public Collection<Relation> findRelationsByStudentIds(final Collection<Long> studentIds) {
		final TypedQuery<Relation> query = this.getEntityManager().createQuery("select r from Relation r join r.students s where s.id in :studentIds",
				Relation.class);
		query.setParameter("studentIds", studentIds);
		return query.getResultList();
	}

}
