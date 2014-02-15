/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.core.model.StudentRegistration;
import com.apeironsol.need.util.constants.StudentRegistrationStatusConstant;

/**
 * Data access interface for batch entity.
 * 
 * @author Pradeep
 * 
 */
public interface StudentRegistrationDao extends BaseDao<StudentRegistration> {

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
