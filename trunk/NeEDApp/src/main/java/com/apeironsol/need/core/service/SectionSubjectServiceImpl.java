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

import com.apeironsol.need.core.dao.SectionSubjectDao;
import com.apeironsol.need.core.model.SectionSubject;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service layer implementation for section subject DAO implementation.
 * 
 * @author Pradeep
 * 
 */
@Service("sectionSubjectService")
@Transactional
public class SectionSubjectServiceImpl implements SectionSubjectService {

	@Resource
	private SectionSubjectDao	sectionSubjectDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionSubject> findSectionSubjectsByScetionId(final Long sectionId) throws BusinessException {
		return this.sectionSubjectDAO.findSectionSubjectsBySectionId(sectionId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SectionSubject findSectionSubjectById(final Long id) throws BusinessException {
		return this.sectionSubjectDAO.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SectionSubject saveSectionSubject(final SectionSubject sectionSubject) throws BusinessException, InvalidArgumentException {
		return this.sectionSubjectDAO.persist(sectionSubject);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeSectionSubject(final SectionSubject sectionSubject) throws BusinessException {
		this.sectionSubjectDAO.remove(sectionSubject);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionSubject> findAllSectionSubjects() throws BusinessException {
		return this.sectionSubjectDAO.findAll();
	}

	@Override
	public void removeSectionSubjectsByScetionId(final Long sectionId) throws BusinessException {
		Collection<SectionSubject> sectionSubjects = this.findSectionSubjectsByScetionId(sectionId);
		if (sectionSubjects != null && !sectionSubjects.isEmpty()) {
			for (SectionSubject sectionSubject : sectionSubjects) {
				this.sectionSubjectDAO.remove(sectionSubject);
			}
		}

	}

}
