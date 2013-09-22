/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.dao;

import java.util.Collection;

import com.apeironsol.need.financial.model.StudentLevelFee;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for Student Level Fee implementation.
 * 
 * @author Pradeep
 * 
 */
public interface StudentLevelFeeDao extends BaseDao<StudentLevelFee> {

	Collection<StudentLevelFee> findStudentLevelFeesByStudentAcademicYearId(Long studentAcademicYearId);

}
