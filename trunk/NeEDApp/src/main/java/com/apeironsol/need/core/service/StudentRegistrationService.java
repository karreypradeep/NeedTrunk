/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.core.model.StudentRegistration;
import com.apeironsol.need.util.constants.StudentRegistrationStatusConstant;

/**
 * Service interface for studentRegistration.
 * 
 * @author Pradeep
 * 
 */
public interface StudentRegistrationService {

	/**
	 * Save the studentRegistration.
	 * 
	 * @param studentRegistration
	 *            studentRegistration.
	 * @return saved studentRegistration.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	StudentRegistration saveStudentRegistration(StudentRegistration studentRegistration) throws BusinessException;

	/**
	 * Removes studentRegistration.
	 * 
	 * @param studentRegistration
	 *            studentRegistration.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeStudentRegistration(StudentRegistration studentRegistration) throws BusinessException;

	/**
	 * Find studentRegistration by id.
	 * 
	 * @param id
	 *            studentRegistration id.
	 * @return studentRegistration by id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	StudentRegistration findStudentRegistrationById(Long id) throws BusinessException;

	/**
	 * Retrieves all studentRegistrationes of supplied branch id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return all studentRegistrationes of supplied branch id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<StudentRegistration> findStudentRegistrationesByBranchId(Long branchId) throws BusinessException;

	/**
	 * Retrieves all studentRegistrationes of supplied branch id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return all studentRegistrationes of supplied branch id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<StudentRegistration> findStudentRegistrationesByAcademicYearId(Long academicYearId) throws BusinessException;

	/**
	 * Retrieves all studentRegistrationes of supplied branch id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return all studentRegistrationes of supplied branch id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<StudentRegistration> findStudentRegistrationesByAcademicYearIdAndStatus(Long academicYearId,
			StudentRegistrationStatusConstant studentRegistrationStatus) throws BusinessException;

}
