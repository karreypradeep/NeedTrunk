/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.academics.dao;

import java.util.Collection;

import com.apeironsol.framework.BaseDao;
import com.apeironsol.need.academics.model.GradeSystemRange;
import com.apeironsol.need.util.NonNull;

/**
 * Data access interface for employee salary entity.
 * 
 * @author Pradeep
 * 
 */
public interface GradeSystemRangeDao extends BaseDao<GradeSystemRange> {
	/**
	 * Remove all employee ctc by employee id.
	 * 
	 * @param employeeId
	 *            employee id.
	 */
	Collection<GradeSystemRange> findAllGradingSystemRangesByGradeSystem(@NonNull final Long gradeSystem);
}
