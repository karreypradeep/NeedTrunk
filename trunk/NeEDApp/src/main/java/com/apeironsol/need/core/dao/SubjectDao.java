/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.Subject;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for course entity.
 * 
 * @author Pradeep
 * 
 */
public interface SubjectDao extends BaseDao<Subject> {

	/**
	 * Find all subjects by class id.
	 * 
	 * @param classId
	 *            class id.
	 * @return all subjects by class id.
	 */
	Collection<Subject> findAllSubjectsByKlassId(Long classId);

	/**
	 * Find all elective subjects by class id.
	 * 
	 * @param classId
	 *            class id.
	 * @return all elective subjects by class id.
	 */
	Collection<Subject> findElectiveSubjectsByKlassId(Long classId);

	Collection<Subject> findDistenctSubjectsAmongSections(Collection<Section> sections);

}
