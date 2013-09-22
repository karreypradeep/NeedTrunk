/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import com.apeironsol.need.core.model.SectionTeacher;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for Student teacher entity implementation.
 * 
 * @author Pradeep
 * 
 */
public interface SectionTeacherDao extends BaseDao<SectionTeacher> {

	/**
	 * Find student teachers by section object id.
	 * 
	 * @param sectionId
	 *            section object id.
	 * @return collection of student teachers by section object id.
	 */
	Collection<SectionTeacher> findSectionTeachersByScetionId(Long sectionId);

	/**
	 * Find student teacher by section object id and employee id.
	 * 
	 * @param sectionId
	 *            section object id.
	 * @param employeeId
	 *            employee id.
	 * @return student teacher by section object id and employee id.
	 */
	SectionTeacher findSectionTeacherByScetionIdAndEmployeeId(Long sectionId, Long employeeId);

}
