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

import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.need.financial.model.StudentFee;
import com.apeironsol.need.financial.model.StudentFeeTransactionDetails;

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

		final TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select e from StudentFeeTransactionDetails e where e.studentFee.id = :studentFeeId", StudentFeeTransactionDetails.class);
		query.setParameter("studentFeeId", studentFeeId);
		return query.getResultList();
	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentAdditionalFeeId(final Long studentAdditionalFeeId) {

		final TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select e from StudentFeeTransactionDetails e where e.studentAdditionalFee.id = :studentAdditionalFeeId", StudentFeeTransactionDetails.class);
		query.setParameter("studentAdditionalFeeId", studentAdditionalFeeId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByKlassFeePaymentId(final Long klassFeePaymentId) {

		final TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select e from StudentFeeTransactionDetails e where e.klassLevelFeeCatalog.id = :klassFeePaymentId", StudentFeeTransactionDetails.class);
		query.setParameter("klassFeePaymentId", klassFeePaymentId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentIdAndAcademicYearId(final Long studentId, final Long academicYearId) {

		final TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
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
		final TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
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

		final TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select sftd from StudentFeeTransactionDetails sftd where sftd.studentFeeTransaction.id = :id", StudentFeeTransactionDetails.class);
		query.setParameter("id", studentFeeTransactionId);
		return query.getResultList();
	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentFees(final Collection<StudentFee> studentFees) {
		final TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select e from StudentFeeTransactionDetails e where e.studentFee in :studentFees", StudentFeeTransactionDetails.class);
		query.setParameter("studentFees", studentFees);
		return query.getResultList();
	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentLevelFeeCatalogIdAndStudentFeeId(
			final Long studentLevelFeeCatalogId, final Long studentFeeId) {

		final TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select sftd from StudentFeeTransactionDetails sftd where sftd.studentLevelFeeCatalog.id = :studentLevelFeeCatalogId and "
						+ "sftd.studentFee.id = :studentFeeId", StudentFeeTransactionDetails.class);
		query.setParameter("studentLevelFeeCatalogId", studentLevelFeeCatalogId);
		query.setParameter("studentFeeId", studentFeeId);
		return query.getResultList();

	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByBranchLevelFeeCatalogtIdAndStudentFeeId(
			final Long branchLevelFeeCatalogId, final Long studentFeeId) {
		final TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select sftd from StudentFeeTransactionDetails sftd where sftd.branchLevelFeeCatalog.id = :branchLevelFeeCatalogId and "
						+ "sftd.studentFee.id = :studentFeeId", StudentFeeTransactionDetails.class);
		query.setParameter("branchLevelFeeCatalogId", branchLevelFeeCatalogId);
		query.setParameter("studentFeeId", studentFeeId);
		return query.getResultList();
	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByPickUpPointFeeCatalogIdAndStudentFeeId(
			final Long pickUpPointFeeCatalogId, final Long studentFeeId) {

		final TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select sftd from StudentFeeTransactionDetails sftd where sftd.pickUpPointFeeCatalog.id = :pickUpPointFeeCatalogId and "
						+ "sftd.studentFee.id = :studentFeeId", StudentFeeTransactionDetails.class);
		query.setParameter("pickUpPointFeeCatalogId", pickUpPointFeeCatalogId);
		query.setParameter("studentFeeId", studentFeeId);

		return query.getResultList();
	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByKlassLevelFeeId(final Long klassLevelFeeId) {
		final TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select sftd from StudentFeeTransactionDetails sftd where sftd.klassLevelFeeCatalog.klassLevelFee.id = :klassLevelFeeId ",
				StudentFeeTransactionDetails.class);
		query.setParameter("klassLevelFeeId", klassLevelFeeId);
		return query.getResultList();
	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByBranchLevelFeeId(final Long branchLevelFeeId) {
		final TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select sftd from StudentFeeTransactionDetails sftd where sftd.branchLevelFeeCatalog.branchLevelFee.id = :branchLevelFeeId ",
				StudentFeeTransactionDetails.class);
		query.setParameter("branchLevelFeeId", branchLevelFeeId);
		return query.getResultList();
	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByStudentLevelFeeId(final Long studentLevelFeeId) {
		final TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select sftd from StudentFeeTransactionDetails sftd where sftd.studentLevelFeeCatalog.studentLevelFee.id = :studentLevelFeeId ",
				StudentFeeTransactionDetails.class);
		query.setParameter("studentLevelFeeId", studentLevelFeeId);
		return query.getResultList();
	}

	@Override
	public Collection<StudentFeeTransactionDetails> findStudentFeeTransactionDetailsByPickUpPointFeeId(final Long pickUpPointFeeId) {
		final TypedQuery<StudentFeeTransactionDetails> query = this.getEntityManager().createQuery(
				"select sftd from StudentFeeTransactionDetails sftd where sftd.pickUpPointFeeCatalog.pickUpPointFee.id = :pickUpPointFeeId ",
				StudentFeeTransactionDetails.class);
		query.setParameter("pickUpPointFeeId", pickUpPointFeeId);
		return query.getResultList();
	}

}
