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

import com.apeironsol.need.core.dao.SubjectDao;
import com.apeironsol.need.core.model.Subject;

/**
 * Service interface implementation for subject.
 * 
 * @author pradeep
 * 
 */
@Service("subjectService")
@Transactional(rollbackFor = Exception.class)
public class SubjectServiceImpl implements SubjectService {

	@Resource
	private SubjectDao	subjectDao;

	@Override
	public Subject saveSubject(final Subject subject) {
		return this.subjectDao.persist(subject);
	}

	@Override
	public void removeSubject(final Subject subject) {
		this.subjectDao.remove(subject);
	}

	@Override
	public Subject findSubjectById(final Long id) {

		return this.subjectDao.findById(id);
	}

	@Override
	public Collection<Subject> findAllSubjects() {
		return this.subjectDao.findAll();
	}

	@Override
	public Collection<Subject> findAllSubjectsByKlassId(final Long id) {
		return this.subjectDao.findAllSubjectsByKlassId(id);
	}

	@Override
	public Collection<Subject> findElectiveSubjectsByKlassId(final Long id) {
		return this.subjectDao.findElectiveSubjectsByKlassId(id);

	}
}
