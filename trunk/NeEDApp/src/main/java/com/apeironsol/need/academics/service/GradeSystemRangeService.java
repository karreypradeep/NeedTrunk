/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.academics.service;

import java.util.Collection;

import com.apeironsol.need.academics.model.GradeSystemRange;
import com.apeironsol.need.util.NonNull;

/**
 * Service interface for GradeSystemRange.
 * 
 * @author Sunny
 * 
 */
public interface GradeSystemRangeService {
	/**
	 * Remove all employee ctc by employee id.
	 * 
	 * @param employeeId
	 *            employee id.
	 */
	Collection<GradeSystemRange> findAllGradingSystemRangesByGradeSystem(@NonNull final Long gradeSystem);
}
