/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.dao;

import java.util.Collection;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.financial.model.StudentFee;
import com.apeironsol.need.financial.model.StudentFeeTransactionDetails;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for student fee transaction entity implementation.
 * 
 * @author Pradeep
 */
@Repository("studentFeeTransactionDetailsDao")
public class StudentFeeTransactionDetailsDaoImp extends BaseDaoImpl<StudentFeeTransactionDetails> implements StudentFeeTransactionDetailsDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentFeeId(final Long studentFeeId) {

		TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select e from StudentFeeTransactionDetails e where e.studentFee.id = :studentFeeId", StudentFeeTransactionDetails.class);
		query.setParameter("studentFeeId", studentFeeId);
		return query.getResultList();
	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentAdditionalFeeId(final Long studentAdditionalFeeId) {

		TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select e from StudentFeeTransactionDetails e where e.studentAdditionalFee.id = :studentAdditionalFeeId", StudentFeeTransactionDetails.class);
		query.setParameter("studentAdditionalFeeId", studentAdditionalFeeId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByKlassFeePaymentId(final Long klassFeePaymentId) {

		TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select e from StudentFeeTransactionDetails e where e.klassLevelFeeCatalog.id = :klassFeePaymentId", StudentFeeTransactionDetails.class);
		query.setParameter("klassFeePaymentId", klassFeePaymentId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentIdAndAcademicYearId(final Long studentId, final Long academicYearId) {

		TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select e from StudentFeeTransactionDetails e where e.studentFee.studentAcademicYear.student.id = :studentId and "
						+ "e.studentFee.studentAcademicYear.academicYear.id = :academicYearId", StudentFeeTransactionDetails.class);
		query.setParameter("studentId", studentId);
		query.setParameter("academicYearId", academicYearId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByKlassFeePaymentIdAndStudentFeeId(final Long klassFeePaymentId,
			final Long studentFeeId) {
		TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select sftd from StudentFeeTransactionDetails sftd where sftd.klassLevelFeeCatalog.id = :klassFeePaymentId and "
						+ "sftd.studentFee.id = :studentFeeId", StudentFeeTransactionDetails.class);
		query.setParameter("klassFeePaymentId", klassFeePaymentId);
		query.setParameter("studentFeeId", studentFeeId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentFeeTransactionId(final Long studentFeeTransactionId) {

		TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select sftd from StudentFeeTransactionDetails sftd where sftd.studentFeeTransaction.id = :id", StudentFeeTransactionDetails.class);
		query.setParameter("id", studentFeeTransactionId);
		return query.getResultList();
	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentFees(final Collection<StudentFee> studentFees) {
		TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select e from StudentFeeTransactionDetails e where e.studentFee in :studentFees", StudentFeeTransactionDetails.class);
		query.setParameter("studentFees", studentFees);
		return query.getResultList();
	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentLevelFeeCatalogIdAndStudentFeeId(
			final Long studentLevelFeeCatalogId, final Long studentFeeId) {

		TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select sftd from StudentFeeTransactionDetails sftd where sftd.studentLevelFeeCatalog.id = :studentLevelFeeCatalogId and "
						+ "sftd.studentFee.id = :studentFeeId", StudentFeeTransactionDetails.class);
		query.setParameter("studentLevelFeeCatalogId", studentLevelFeeCatalogId);
		query.setParameter("studentFeeId", studentFeeId);
		return query.getResultList();

	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByBranchLevelFeeCatalogtIdAndStudentFeeId(
			final Long branchLevelFeeCatalogId, final Long studentFeeId) {
		TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select sftd from StudentFeeTransactionDetails sftd where sftd.branchLevelFeeCatalog.id = :branchLevelFeeCatalogId and "
						+ "sftd.studentFee.id = :studentFeeId", StudentFeeTransactionDetails.class);
		query.setParameter("branchLevelFeeCatalogId", branchLevelFeeCatalogId);
		query.setParameter("studentFeeId", studentFeeId);
		return query.getResultList();
	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByPickUpPointFeeCatalogIdAndStudentFeeId(
			final Long pickUpPointFeeCatalogId, final Long studentFeeId) {

		TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select sftd from StudentFeeTransactionDetails sftd where sftd.pickUpPointFeeCatalog.id = :pickUpPointFeeCatalogId and "
						+ "sftd.studentFee.id = :studentFeeId", StudentFeeTransactionDetails.class);
		query.setParameter("pickUpPointFeeCatalogId", pickUpPointFeeCatalogId);
		query.setParameter("studentFeeId", studentFeeId);

		return query.getResultList();
	}

}
