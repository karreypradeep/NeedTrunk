/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */

package com.apeironsol.need.core.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.core.dao.StudentRegistrationDao;
import com.apeironsol.need.core.model.StudentRegistration;
import com.apeironsol.need.util.constants.StudentRegistrationStatusConstant;

/**
 * Service interface implementation for Batch.
 * 
 * @author Pradeep
 * 
 */
@Service("studentRegistrationService")
@Transactional(rollbackFor = Exception.class)
public class StudentRegistrationServiceImpl implements StudentRegistrationService {

	@Resource
	private StudentRegistrationDao	studentRegistrationDao;

	@Override
	public StudentRegistration saveStudentRegistration(final StudentRegistration studentRegistration) throws BusinessException {
		return this.studentRegistrationDao.persist(studentRegistration);
	}

	@Override
	public void removeStudentRegistration(final StudentRegistration studentRegistration) throws BusinessException {
		this.studentRegistrationDao.remove(studentRegistration);
	}

	@Override
	public StudentRegistration findStudentRegistrationById(final Long id) throws BusinessException {
		return this.studentRegistrationDao.findStudentRegistrationById(id);
	}

	@Override
	public Collection<StudentRegistration> findStudentRegistrationesByBranchId(final Long branchId) throws BusinessException {
		return this.studentRegistrationDao.findStudentRegistrationesByBranchId(branchId);
	}

	@Override
	public Collection<StudentRegistration> findStudentRegistrationesByAcademicYearId(final Long academicYearId) throws BusinessException {
		return this.studentRegistrationDao.findStudentRegistrationesByAcademicYearId(academicYearId);
	}

	@Override
	public Collection<StudentRegistration> findStudentRegistrationesByAcademicYearIdAndStatus(final Long academicYearId,
			final StudentRegistrationStatusConstant studentRegistrationStatus) throws BusinessException {
		return this.studentRegistrationDao.findStudentRegistrationesByAcademicYearIdAndStatus(academicYearId, studentRegistrationStatus);
	}

}
