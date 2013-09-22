/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.util.searchcriteria.AdmissionSearchCriteria;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for student entity.
 * 
 * @author Pradeep
 */
public interface StudentDao extends BaseDao<Student> {

	/**
	 * Find students by branch id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return collection of students for branch.
	 */
	Collection<Student> findStudentsByBranchId(Long branchId);

	/**
	 * Find student by admission id.
	 * 
	 * @param admissionId
	 *            admission id.
	 * @return student by admission id.
	 */
	Student findStudentByAdmissionId(Long admissionId);

	/**
	 * Find active students by branch id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return collection of active students for branch.
	 */
	Collection<Student> findActiveStudentsByBranchId(final Long branchId);

	/**
	 * Find all admissions that match the search criteria.
	 * 
	 * @param admissionSearchCriteria
	 *            admission search criteria.
	 * @return collection of all admissions that match the search
	 *         criteria.
	 * @throws BusinessException
	 *             In case of Exception.
	 */
	Collection<Student> findAdmissionsBySearchCriteria(final AdmissionSearchCriteria admissionSearchCriteria);

	/**
	 * 
	 * @param admissionNumber
	 * @return
	 */
	Student findActiveStudentByAdmissionNumber(String admissionNumber);

	/**
	 * Find student by username
	 * 
	 * @param username
	 * @return
	 */
	Student findStudentByUsername(String username);

	/**
	 * 
	 * @param branchId
	 * @return
	 */
	Long findActiveStudentsCountForBranchId(Long branchId);

	/**
	 * 
	 * @param admissionNumber
	 * @return
	 */
	Student findActiveStudentByExternalAdmissionNumberAndBranchId(String externalAdmissionNumber, Long branchId);

}
