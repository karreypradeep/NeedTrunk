package com.apeironsol.need.core.dao;

import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.Relation;
import com.apeironsol.framework.BaseDaoImpl;

@Repository
public class RelationDaoImpl extends BaseDaoImpl<Relation> implements RelationDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Relation> findRelationsByStudentId(final Long studentId) {
		TypedQuery<Relation> query = this.getEntityManager().createQuery(
				"select r from Relation r join r.students s where s.id=:id", Relation.class);
		query.setParameter("id", studentId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Relation findRelationByUsername(final String username) {
		try {
			TypedQuery<Relation> query = this.getEntityManager().createQuery(
					"select r from Relation r where r.userAccount.username = :username", Relation.class);
			query.setParameter("username", username);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
