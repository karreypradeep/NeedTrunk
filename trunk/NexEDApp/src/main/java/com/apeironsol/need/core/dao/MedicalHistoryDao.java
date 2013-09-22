/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import com.apeironsol.need.core.model.MedicalHistory;
import com.apeironsol.framework.BaseDao;

/**
 * 
 * @author sunny
 * 
 *         Data access interface for Student Medical History entity
 *         implementation.
 * 
 */
public interface MedicalHistoryDao extends BaseDao<MedicalHistory> {

	/**
	 * Find MedicalHistory by studentId.
	 * 
	 * @param studentId
	 *            studentId.
	 * @return MedicalHistory of student.
	 */
	MedicalHistory findMedicalHistoryByStudentId(final Long studentId);
}
