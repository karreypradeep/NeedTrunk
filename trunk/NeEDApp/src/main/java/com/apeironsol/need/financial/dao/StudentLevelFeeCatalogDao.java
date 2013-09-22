/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.dao;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.financial.model.StudentLevelFeeCatalog;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for branch level fee catalog implementation.
 * 
 * @author Pradeep
 * 
 */
public interface StudentLevelFeeCatalogDao extends BaseDao<StudentLevelFeeCatalog> {

	void removeStudentLevelFeeCatalogsByStudentLevelFeeId(Long studentLevelFeeId);

	Collection<StudentLevelFeeCatalog> findStudentLevelFeeCatalogsByStudentLevelFeeId(Long studentLevelFeeId);

	Collection<StudentLevelFeeCatalog> findStudentLevelFeeCatalogsByStudentLevelFeeIdAndDueDate(Long studentLevelFeeId, Date dueDate);

}
