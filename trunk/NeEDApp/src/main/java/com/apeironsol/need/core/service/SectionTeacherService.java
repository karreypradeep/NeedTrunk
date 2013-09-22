/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.need.core.model.SectionTeacher;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service layer interface for section teacher DAO implementation.
 * 
 * @author Pradeep
 * 
 */
public interface SectionTeacherService {
	/**
	 * Find section teachers by section object id.
	 * 
	 * @param sectionId
	 *            section object id.
	 * @return collection of section teachers by section object id.
	 * @throws BusinessException In case of exception.
	 */
	Collection<SectionTeacher> findSectionTeachersByScetionId(Long sectionId)throws BusinessException;

	/**
	 * Find section teacher by section object id and employee id.
	 * 
	 * @param sectionId
	 *            section object id.
	 * @param employeeId
	 *            employee id.
	 * @return section teacher by section object id and employee id.
	 * @throws BusinessException In case of exception.
	 */
	SectionTeacher findSectionTeacherByScetionIdAndEmployeeId(Long sectionId, Long employeeId)throws BusinessException;

	/**
	 * Retrieve section teacher by object id.
	 * 
	 * @param id
	 *            id of section teacher.
	 * @return section teacher by object id.
	 * @throws BusinessException In case of exception.
	 */
	SectionTeacher findSectionTeacherById(Long id) throws BusinessException;

	/**
	 * Saves supplied section teacher.
	 * 
	 * @param SectionTeacher
	 *            section teacher.
	 * @return section teacher.
	 * @throws BusinessException In case of exception.
	 * @throws InvalidArgumentException
	 */
	SectionTeacher saveSectionTeacher(SectionTeacher sectionTeacher) throws BusinessException, InvalidArgumentException;

	/**
	 * Removes section teacher.
	 * 
	 * @param SectionTeacher
	 *            section teacher to be removed.
	 * @throws BusinessException In case of exception.
	 */
	void removeSectionTeacher(SectionTeacher sectionTeacher) throws BusinessException;

	/**
	 * Retrieves all section teacher.
	 * 
	 * @return collection of all section teacher.
	 * @throws BusinessException In case of exception.
	 */
	Collection<SectionTeacher> findAllSectionTeachers() throws BusinessException;

}
