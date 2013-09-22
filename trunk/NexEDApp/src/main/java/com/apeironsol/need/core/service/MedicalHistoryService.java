/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.need.core.model.MedicalHistory;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service Interface for Medical History. This service act as controller for
 * Medica lHistory details.
 * 
 * @author Sunny
 * 
 */
public interface MedicalHistoryService {

	MedicalHistory findMedicalHistoryById(Long id) throws BusinessException;

	Collection<MedicalHistory> findAllMedicalHistorys() throws BusinessException;

	MedicalHistory saveMedicalHistory(MedicalHistory medicalHistory) throws BusinessException;

	void deleteMedicalHistory(final Long id) throws BusinessException;

	/**
	 * Find MedicalHistory by studentId.
	 * 
	 * @param studentId
	 *            studentId.
	 * @return MedicalHistory of student.
	 */
	MedicalHistory findMedicalHistoryByStudentId(final Long studentId);

}
