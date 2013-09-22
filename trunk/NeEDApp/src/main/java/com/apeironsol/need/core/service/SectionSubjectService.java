/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.need.core.model.SectionSubject;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service layer interface for section subject DAO implementation.
 * 
 * @author Pradeep
 * 
 */
public interface SectionSubjectService {
	/**
	 * Find section subjects by section object id.
	 * 
	 * @param sectionId
	 *            section object id.
	 * @return collection of student subjects by section object id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<SectionSubject> findSectionSubjectsByScetionId(Long sectionId) throws BusinessException;

	/**
	 * Retrieve section subject by object id.
	 * 
	 * @param id
	 *            id of section subject.
	 * @return section subject by object id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	SectionSubject findSectionSubjectById(Long id) throws BusinessException;

	/**
	 * Saves supplied section subject.
	 * 
	 * @param SectionSubject
	 *            section subject.
	 * @return section subject.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 */
	SectionSubject saveSectionSubject(SectionSubject sectionSubject) throws BusinessException, InvalidArgumentException;

	/**
	 * Removes section subject.
	 * 
	 * @param SectionSubject
	 *            section subject to be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeSectionSubject(SectionSubject sectionSubject) throws BusinessException;

	/**
	 * Retrieves all section subject.
	 * 
	 * @return collection of all section subject.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<SectionSubject> findAllSectionSubjects() throws BusinessException;

	/**
	 * Remove section subjects by section object id.
	 * 
	 * @param sectionId
	 *            section object id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeSectionSubjectsByScetionId(Long sectionId) throws BusinessException;

}
