/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import com.apeironsol.need.core.model.EducationHistory;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for academic year implementation.
 * 
 * @author Pradeep
 */
public interface EducationHistoryDao extends BaseDao<EducationHistory> {

	/**
	 * Find education histories by student id.
	 * 
	 * @param studentId
	 *            studentId.
	 * @return collection of education histories by student id.
	 */
	Collection<EducationHistory> findEducationHistoriesByStudentId(final Long studentId);

}
