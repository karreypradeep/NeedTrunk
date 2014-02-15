/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.core.model.StudentRegistration;
import com.apeironsol.need.util.constants.StudentRegistrationStatusConstant;

/**
 * Data access interface for batch implementation entity.
 * 
 * @author Pradeep
 * 
 */
@Repository("studentRegistrationDao")
public class StudentRegistrationDaoImpl extends BaseDaoImpl<StudentRegistration> implements StudentRegistrationDao {

	@Override
	public StudentRegistration findStudentRegistrationById(final Long id) throws BusinessException {
		final TypedQuery<StudentRegistration> query = this.getEntityManager().createQuery("select st from StudentRegistration st where st.id = :id",
				StudentRegistration.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

	@Override
	public Collection<StudentRegistration> findStudentRegistrationesByBranchId(final Long branchId) throws BusinessException {
		final TypedQuery<StudentRegistration> query = this.getEntityManager().createQuery(
				"select st from StudentRegistration st where st.branch.id = :branchId", StudentRegistration.class);
		query.setParameter("branchId", branchId);
		return query.getResultList();
	}

	@Override
	public Collection<StudentRegistration> findStudentRegistrationesByAcademicYearId(final Long academicYearId) throws BusinessException {
		final TypedQuery<StudentRegistration> query = this.getEntityManager().createQuery(
				"select st from StudentRegistration st where st.academicYear.id = :academicYearId", StudentRegistration.class);
		query.setParameter("academicYearId", academicYearId);
		return query.getResultList();
	}

	@Override
	public Collection<StudentRegistration> findStudentRegistrationesByAcademicYearIdAndStatus(final Long academicYearId,
			final StudentRegistrationStatusConstant studentRegistrationStatus) throws BusinessException {
		final TypedQuery<StudentRegistration> query = this.getEntityManager().createQuery(
				"select st from StudentRegistration st where st.academicYear.id = :academicYearId and st.registrationStatus = :registrationStatus",
				StudentRegistration.class);
		query.setParameter("academicYearId", academicYearId);
		query.setParameter("registrationStatus", studentRegistrationStatus);
		return query.getResultList();
	}

}
