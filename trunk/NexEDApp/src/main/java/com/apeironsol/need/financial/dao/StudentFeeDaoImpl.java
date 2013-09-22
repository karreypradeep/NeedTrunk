/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.dao;

import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.financial.model.StudentFee;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for student fee entity implementation.
 * 
 * @author Pradeep
 */
@Repository("studentFeeDao")
public class StudentFeeDaoImpl extends BaseDaoImpl<StudentFee> implements StudentFeeDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentFee> findStudentFeesByStudentIdAndAcadmicYearId(final Long studentId, final Long academicYearId) {
		TypedQuery<StudentFee> query = this.getEntityManager().createQuery(
				"select s from StudentFee s where s.studentAcademicYear.student.id = :studentId and s.studentAcademicYear.academicYear.id = :academicYearId",
				StudentFee.class);
		query.setParameter("studentId", studentId);
		query.setParameter("academicYearId", academicYearId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentFee> findStudentFeesByStudentAcadmicYearId(final Long studentAcadmicYearId) {
		TypedQuery<StudentFee> query = this.getEntityManager().createQuery("select s from StudentFee s where s.studentAcademicYear.id = :studentAcadmicYearId",
				StudentFee.class);
		query.setParameter("studentAcadmicYearId", studentAcadmicYearId);
		return query.getResultList();
	}

	@Override
	public StudentFee findStudentFeeByStudentAcadmicYearIdAndKlassFeeId(final Long studentAcadmicYearId, final Long klassFeeId) {
		try {
			TypedQuery<StudentFee> query = this.getEntityManager().createQuery(
					"select s from StudentFee s where s.studentAcademicYear.id = :studentAcadmicYearId and s.klassLevelFee.id = :klassFeeId", StudentFee.class);
			query.setParameter("studentAcadmicYearId", studentAcadmicYearId);
			query.setParameter("klassFeeId", klassFeeId);
			return query.getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}

	@Override
	public StudentFee findStudentFeeByStudentAcadmicYearIdAndBranchFeeId(final Long studentAcadmicYearId, final Long branchFeeId) {
		try {
			TypedQuery<StudentFee> query = this.getEntityManager().createQuery(
					"select s from StudentFee s where s.studentAcademicYear.id = :studentAcadmicYearId and s.branchLevelFee.id = :branchFeeId",
					StudentFee.class);
			query.setParameter("studentAcadmicYearId", studentAcadmicYearId);
			query.setParameter("branchFeeId", branchFeeId);
			return query.getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}

	@Override
	public StudentFee findStudentFeeByStudentAcadmicYearIdAndStudentFeeId(final Long studentAcadmicYearId, final Long studentFeeId) {
		try {
			TypedQuery<StudentFee> query = this.getEntityManager().createQuery(
					"select s from StudentFee s where s.studentAcademicYear.id = :studentAcadmicYearId and s.studentLevelFee.id = :studentFeeId",
					StudentFee.class);
			query.setParameter("studentAcadmicYearId", studentAcadmicYearId);
			query.setParameter("studentFeeId", studentFeeId);
			return query.getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}
	
	@Override
	public StudentFee findStudentFeeByStudentAcadmicYearIdAndPickupPointFeeId(final Long studentAcadmicYearId, final Long pickupPointFeeId) {
		try {
			TypedQuery<StudentFee> query = this.getEntityManager().createQuery(
					"select s from StudentFee s where s.studentAcademicYear.id = :studentAcadmicYearId and s.pickUpPointFee.id = :pickupPointFeeId", StudentFee.class);
			query.setParameter("studentAcadmicYearId", studentAcadmicYearId);
			query.setParameter("pickupPointFeeId", pickupPointFeeId);
			return query.getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}
	
	@Override
	public Collection<StudentFee> findStudentFeeByAcadmicYearIdAndPickupPointFeeId(final Long acadmicYearId, final Long pickupPointFeeId) {
		try {
			TypedQuery<StudentFee> query = this.getEntityManager().createQuery(
					"select s from StudentFee s where s.studentAcademicYear.academicYear.id = :acadmicYearId and s.pickUpPointFee.id = :pickupPointFeeId", StudentFee.class);
			query.setParameter("acadmicYearId", acadmicYearId);
			query.setParameter("pickupPointFeeId", pickupPointFeeId);
			return query.getResultList();
		} catch (NoResultException noResultException) {
			return null;
		}
	}

}
