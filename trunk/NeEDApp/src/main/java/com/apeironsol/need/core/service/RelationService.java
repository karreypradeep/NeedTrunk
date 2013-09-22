/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.need.core.model.Address;
import com.apeironsol.need.core.model.Relation;
import com.apeironsol.need.core.model.Student;

public interface RelationService {

	/**
	 * Find relation by student id.
	 * 
	 * @param studentId
	 *            student id.
	 * @return
	 */
	Collection<Relation> findRelationByStudentId(Long studentId);

	/**
	 * Save relation.
	 * 
	 * @param relation
	 *            relation.
	 * @param student
	 *            student.
	 * @return
	 */
	Relation saveRelation(Relation relation, Student student);

	/**
	 * Find relation address by relation id.
	 * 
	 * @param relationId
	 *            relation id.
	 * @return
	 */
	Address findRelationAddressByRelationId(Long relationId);

	/**
	 * Remove relation.
	 * 
	 * @param relation
	 *            relation.
	 * @param student
	 *            student.
	 */
	void removeRelation(Relation relation, Student student);

	/**
	 * Remove all relation for student.
	 * 
	 * @param student
	 *            student.
	 */
	void removeAllRelations(Student student);

	/**
	 * Find relations detailed by student id.
	 * 
	 * @param studentId
	 *            student id.
	 * @return
	 */
	Collection<Relation> findRelationsDetailedByStudentId(Long studentId);

}
