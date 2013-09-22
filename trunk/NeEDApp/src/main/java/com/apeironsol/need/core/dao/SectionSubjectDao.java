/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import com.apeironsol.need.core.model.SectionSubject;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for section subject entity implementation.
 * 
 * @author Pradeep
 * 
 */
public interface SectionSubjectDao extends BaseDao<SectionSubject> {

	/**
	 * Retrieve section subjects by section id.
	 * 
	 * @param sectionId
	 *            section id.
	 * @return collection of section subjects by section id.
	 */
	Collection<SectionSubject> findSectionSubjectsBySectionId(Long sectionId);

}
