/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.need.core.model.EducationHistory;

/**
 * Service Interface forStudentSection.
 * 
 * @author Pradeep
 * 
 */
public interface EducationHistoryService {
	/**
	 * Find education histories by student id.
	 * 
	 * @param studentId
	 *            studentId.
	 * @return collection of education histories by student id.
	 */
	Collection<EducationHistory> findEducationHistoriesByStudentId(final Long studentId);

	EducationHistory saveEducationHistory(final EducationHistory educationHistory);

	void removeEducationHistory(final EducationHistory educationHistory);
}
