package com.apeironsol.need.core.dao;

import java.util.Collection;

import com.apeironsol.need.core.model.Relation;
import com.apeironsol.framework.BaseDao;

public interface RelationDao extends BaseDao<Relation> {

	/**
	 * Retrieve relations by student id.
	 * 
	 * @param studentId
	 *            student id.
	 * @return collection of relations by student id.
	 */
	Collection<Relation> findRelationsByStudentId(final Long studentId);

	/**
	 * 
	 * @param username
	 * @return
	 */
	Relation findRelationByUsername(String username);

}
