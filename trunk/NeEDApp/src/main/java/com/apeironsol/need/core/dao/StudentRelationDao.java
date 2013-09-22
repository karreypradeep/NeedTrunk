/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import com.apeironsol.need.core.model.StudentRelation;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for student entity implementation.
 * 
 * @author Pradeep
 */
public interface StudentRelationDao extends BaseDao<StudentRelation> {

	/**
	 * Find student relations by student id.
	 * 
	 * @param studentId
	 *            student id.
	 * @return collection of student relations by student id.
	 */
	public Collection<StudentRelation> findStudentRelationsByStudentId(final Long studentId);

	/**
	 * Find student relations by relation id.
	 * 
	 * @param relationsId
	 *            relation id
	 * @return collection of student relations by relation id.
	 */
	public Collection<StudentRelation> findStudentRelationsByRelationId(final Long relationsId);

	/**
	 * Remove student relations by student id.
	 * 
	 * @param studentId
	 *            student id.
	 * @return
	 */
	int removeStudentRelationsByStudentId(Long studentId);

	/**
	 * Remove student relations by relation id.
	 * 
	 * @param relationId
	 *            relation id.
	 * @return
	 */
	int removeStudentRelationsByRelationId(Long relationId);

	/**
	 * Remove student relations by student id and relation id.
	 * 
	 * @param studentId
	 *            student id.
	 * @param relationId
	 *            relation id.
	 * @return
	 */
	int removeStudentRelationByStudentIdAndRelationId(Long studentId, Long relationId);
}
