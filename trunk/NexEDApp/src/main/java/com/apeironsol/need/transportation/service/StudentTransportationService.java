/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.service;

import java.util.Collection;

import com.apeironsol.need.core.model.StudentTransportation;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * @author Sunny
 * 
 *         Service Interface for StudentTransportation. This service act as
 *         controller for
 *         StudentTransportation details.
 * 
 */
public interface StudentTransportationService {

	/**
	 * Retrieves the student transportation by id.
	 * 
	 * @param id
	 *            the id of student transportation to be retrieved.
	 * @return
	 */
	StudentTransportation findStudentTransportationById(Long id) throws BusinessException;

	/**
	 * Retrieves all student transportations available.
	 * 
	 * @return all student transportations available.
	 */
	Collection<StudentTransportation> findAllStudentTransportations() throws BusinessException;

	/**
	 * Assign the student transportation.
	 * 
	 * @return the saved student transportation.
	 */
	StudentTransportation assignStudentTransportation(StudentTransportation studentTransportation)
			throws BusinessException;

	/**
	 * Deletes the student transportation.
	 * 
	 */
	void unassignStudentTransportation(final Long id) throws BusinessException;

	/**
	 * Retrieves all student transportations available.
	 * 
	 * @return all student transportations available.
	 */
	Collection<StudentTransportation> findAllStudentTransportationsByStudentAcadmicYearId(
			final Long studentAcademicYearId) throws BusinessException;
	
	/**
	 * 
	 * @param studentAcademicYearId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	StudentTransportation findAssignedStudentTransportationsByStudentAcadmicYearId(Long studentAcademicYearId) throws BusinessException, SystemException;

}
