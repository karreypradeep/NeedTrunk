/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

/**
 * Service interface for student.
 * 
 * @author pradeep
 * 
 */
import java.util.Collection;
import java.util.Map;

import com.apeironsol.need.core.model.Address;
import com.apeironsol.need.core.model.EducationHistory;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.util.searchcriteria.StudentSearchCriteria;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

public interface StudentService {

	/**
	 * Saves student.
	 * 
	 * @param student
	 *            student to be saved.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Student saveStudent(Student student) throws BusinessException;

	/**
	 * Retrieve student by id.
	 * 
	 * @param id
	 *            student id.
	 * @return
	 */
	Student findStudentById(Long id);

	/**
	 * Find student address by student id.
	 * 
	 * @param studentId
	 *            student id.
	 * @return
	 */
	Address findStudentAddressByStudentId(final Long studentId);

	/**
	 * Find students by branch id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return collection of students for branch.
	 */
	Collection<Student> findStudentsByBranchId(final Long branchId);

	/**
	 * Find active students by branch id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return collection of active students for branch.
	 */
	Collection<Student> findActiveStudentsByBranchId(final Long branchId);

	/**
	 * Find previous education history by student id.
	 * 
	 * @param studentId
	 *            student id.
	 * @return
	 */
	Collection<EducationHistory> findPreviousEducationHistoryByStudentId(final Long studentId);

	/**
	 * Save education history.
	 * 
	 * @param educationHistory
	 *            education history.
	 * @return
	 */
	EducationHistory saveEducationHistory(final EducationHistory educationHistory);

	/**
	 * Find active students by section id.
	 * 
	 * @param sectionId
	 *            section id.
	 * @return collection of active students by section id.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws SystemException
	 *             In case of exception.
	 */
	Collection<Student> findActiveStudentsBySectionId(Long sectionId) throws BusinessException, SystemException;

	/**
	 * Return collection of student academic years with active status for
	 * section.
	 * 
	 * @param sectionId
	 *            section id.
	 * @return collection of student academic years with active status for
	 *         section.
	 * @throws BusinessException
	 * @throws SystemException
	 */
	Collection<StudentAcademicYear> findStudentAcademicYearsWithActiveStatusBySectionId(Long sectionId) throws BusinessException, SystemException;

	/**
	 * Find all active student sections for branch and academic year.
	 * 
	 * @param branchId
	 *            branch id.
	 * @param academicYearId
	 *            academicYearId
	 * @return collection of all active student sections for branch and academic
	 *         year.
	 * @throws BusinessException
	 *             In case of Exception.
	 */
	Collection<StudentSection> findActiveStudentSectionsForAcademicYearId(final Long academicYearId) throws BusinessException;

	/**
	 * Return number of all active students by section.
	 * 
	 * @param sectionId
	 *            section id.
	 * @return number of all active students by section.
	 * @throws BusinessException
	 *             In case of Exception.
	 */
	int findNumberOfActiveStudentsBySectionId(Long sectionId) throws BusinessException;

	/**
	 * Find number of active students by sections id.
	 * 
	 * @param sectionIds
	 *            section id.
	 * @return
	 */
	Map<Long, Integer> findNumberOfActiveStudentsBySectionIds(final Collection<Long> sectionIds);

	/**
	 * Find all students by section id.
	 * 
	 * @param sectionId
	 *            section id.
	 * @return collection of all students by section id.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws SystemException
	 *             In case of exception.
	 */
	Collection<Student> findAllStudentsBySectionId(Long sectionId) throws BusinessException, SystemException;

	/**
	 * Find all student sections that match the search criteria.
	 * 
	 * @param studentSearchCriteria
	 *            studentSearchCriteria.
	 * @return collection of all student sections that match the search
	 *         criteria.
	 * @throws BusinessException
	 *             In case of Exception.
	 */
	Collection<StudentSection> findStudentSectionsBySearchCriteria(final StudentSearchCriteria studentSearchCriteria) throws BusinessException;

	/**
	 * Find student section by student academic year id and active state.
	 * 
	 * @param studentId
	 *            student id.
	 * @return student section by student id and active state.
	 */
	StudentSection findStudentSectionByStudentAcademicYearIdAndActiveStatus(final Long studentId);

	Collection<StudentSection> findActiveStudentSectionsBySectionId(final Long sectionId) throws BusinessException, SystemException;

	Collection<StudentSection> findAllStudentSectionsBySectionId(final Long sectionId) throws BusinessException, SystemException;

	Student findStudentByUsername(String username);

	boolean isActiveStudentsForBranchId(final Long branchId);

	/**
	 * Return collection of student academic years with active status for
	 * section.
	 * 
	 * @param sectionId
	 *            section id.
	 * @return collection of student academic years with active status for
	 *         section.
	 * @throws BusinessException
	 * @throws SystemException
	 */
	Collection<StudentAcademicYear> findStudentAcademicYearsWithActiveStatusBySectionIds(final Collection<Long> sectionIds) throws BusinessException,
			SystemException;

	/**
	 * 
	 * @param sectionIds
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	Collection<StudentSection> findAllStudentSectionsBySectionIds(final Collection<Long> sectionIds) throws BusinessException, SystemException;

	/**
	 * 
	 * @param studentId
	 * @param actionComment
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	Student processDropoutStudent(Long studentId, final String actionComment) throws BusinessException, SystemException;

	/**
	 * 
	 * @param studentId
	 * @param actionComment
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	Student dropoutStudent(Long studentId, final String actionComment) throws BusinessException, SystemException;

	/**
	 * 
	 * @param studentId
	 * @param actionComment
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	Student rollBackDropoutStudent(final Long studentId, final String actionComment) throws BusinessException, SystemException;
}