/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.AddressDao;
import com.apeironsol.need.core.dao.RelationDao;
import com.apeironsol.need.core.dao.StudentRelationDao;
import com.apeironsol.need.core.model.Address;
import com.apeironsol.need.core.model.Relation;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentRelation;
import com.apeironsol.need.core.model.StudentRelationPK;

/**
 * Service implementation interface for calendar year.
 * This service act as controller for calendar year details.
 * 
 * @author Pradeep
 * 
 */
@Service("relationService")
@Transactional(rollbackFor = Exception.class)
public class RelationServiceImpl implements RelationService {

	@Resource
	RelationDao			relationDao;

	@Resource
	StudentRelationDao	studentRelationDao;

	@Resource
	AddressDao			addressDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Relation> findRelationByStudentId(final Long id) {
		return this.relationDao.findRelationsByStudentId(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Relation saveRelation(final Relation relation, final Student student) {

		final Relation relationResult = this.relationDao.persist(relation);

		final StudentRelation studentRelation = new StudentRelation();
		final StudentRelationPK studentRelationPK = new StudentRelationPK();
		studentRelationPK.setRelationsId(relationResult.getId());
		studentRelationPK.setStudentsId(student.getId());
		studentRelation.setStudentRelationPK(studentRelationPK);
		this.studentRelationDao.persist(studentRelation);

		return relationResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeRelation(final Relation relation, final Student student) {

		this.studentRelationDao.removeStudentRelationByStudentIdAndRelationId(student.getId(), relation.getId());

		final Collection<StudentRelation> studentRelations = this.studentRelationDao.findStudentRelationsByRelationId(relation.getId());
		if (studentRelations == null || studentRelations.isEmpty()) {
			this.relationDao.remove(relation);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAllRelations(final Student student) {

		final Collection<Relation> relations = this.relationDao.findRelationsByStudentId(student.getId());

		if (relations != null && relations.isEmpty()) {

			for (final Relation relation : relations) {
				this.studentRelationDao.removeStudentRelationByStudentIdAndRelationId(student.getId(), relation.getId());

				final Collection<StudentRelation> studentRelations = this.studentRelationDao.findStudentRelationsByRelationId(relation.getId());
				if (studentRelations == null || studentRelations.isEmpty()) {
					this.relationDao.remove(relation);
				}
			}

		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Address findRelationAddressByRelationId(final Long relationId) {
		return this.addressDao.findRelationAddressByRelationId(relationId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Relation> findRelationsDetailedByStudentId(final Long id) {
		final Collection<Relation> relations = this.relationDao.findRelationsByStudentId(id);
		for (final Relation relation : relations) {
			relation.setAddress(this.findRelationAddressByRelationId(relation.getId()));
		}
		return relations;
	}
}
