/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.dao;

import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.financial.model.StudentFeeTransaction;
import com.apeironsol.need.util.constants.StudentFeeTransactionStatusConstant;
import com.apeironsol.need.util.searchcriteria.FeeCollectedSearchCriteria;
import com.apeironsol.need.util.searchcriteria.StudentFeeTransactionSearchCriteria;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for student fee transaction entity implementation.
 * 
 * @author Pradeep
 */
@Repository("studentFeeTransactionDao")
public class StudentFeeTransactionDaoImp extends BaseDaoImpl<StudentFeeTransaction> implements StudentFeeTransactionDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentFeeTransaction findStudentFeeTransactionsByTransactionNr(final String transactionNr) {

		final TypedQuery<StudentFeeTransaction> query = getEntityManager().createQuery(
				"select sft from StudentFeeTransaction sft where sft.transactionNr = :transactionNr", StudentFeeTransaction.class);
		query.setParameter("transactionNr", transactionNr);
		return query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentFeeTransaction> findStudentFeeTransactionsByBranchIdAndTransactionStatusBetweenDates(final Long branchId,
			final EnumSet<StudentFeeTransactionStatusConstant> studentFeeTransactionStatusConstants, final Date fromDate, final Date toDate) {
		final TypedQuery<StudentFeeTransaction> query = getEntityManager().createQuery(
				"select sft from StudentFeeTransaction sft where sft.studentAcademicYear.student.branch.id = :branchId"
						+ " and sft.studentFeeTransactionStatus in :studentFeeTransactionStatusConstants and"
						+ " sft.transactionDate >= :fromDate  and sft.transactionDate <= :toDate ", StudentFeeTransaction.class);
		query.setParameter("studentFeeTransactionStatusConstants", studentFeeTransactionStatusConstants);
		query.setParameter("branchId", branchId);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		return query.getResultList();
	}

	@Override
	public Collection<StudentFeeTransaction> findStudentFeeTransactionsBySearchCriteria(
			final StudentFeeTransactionSearchCriteria studentFeeTransactionSearchCriteria) throws BusinessException {
		StringBuilder queryString = new StringBuilder("select sft from StudentFeeTransaction sft ");
		boolean whereClasuseAdded = false;

		if (studentFeeTransactionSearchCriteria.getBranch() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" sft.studentAcademicYear.student.branch = :branch ");
			whereClasuseAdded = true;
		}

		if (studentFeeTransactionSearchCriteria.getName() != null && !studentFeeTransactionSearchCriteria.getName().trim().isEmpty()) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" ( ");
			queryString = queryString.append(" sft.studentAcademicYear.student.firstName like '%" + studentFeeTransactionSearchCriteria.getName() + "%'");
			queryString = queryString.append(" or sft.studentAcademicYear.student.lastName like '%" + studentFeeTransactionSearchCriteria.getName() + "%'");
			queryString = queryString.append(" or sft.studentAcademicYear.student.middleName like '%" + studentFeeTransactionSearchCriteria.getName() + "%'");
			queryString = queryString.append(" ) ");
			whereClasuseAdded = true;
		}

		if (studentFeeTransactionSearchCriteria.getAdmissionNumber() != null && !studentFeeTransactionSearchCriteria.getAdmissionNumber().trim().isEmpty()) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" ( ");
			queryString = queryString.append(" sft.studentAcademicYear.student.admissionNr like '%" + studentFeeTransactionSearchCriteria.getAdmissionNumber()
					+ "%'");
			queryString = queryString.append("or sft.studentAcademicYear.student.externalAdmissionNr like '%"
					+ studentFeeTransactionSearchCriteria.getAdmissionNumber() + "%'");
			queryString = queryString.append(" ) ");
			whereClasuseAdded = true;
		}

		if (studentFeeTransactionSearchCriteria.getTransactionNumber() != null && !studentFeeTransactionSearchCriteria.getTransactionNumber().trim().isEmpty()) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" sft.transactionNr like '%" + studentFeeTransactionSearchCriteria.getTransactionNumber() + "%'");
			whereClasuseAdded = true;
		}

		if (studentFeeTransactionSearchCriteria.getFromDate() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString
					.append("( (sft.externalTransactionDate is null and  sft.transactionDate >= :fromDate) or (sft.externalTransactionDate is not null and  sft.externalTransactionDate >= :fromDate)) ");
			whereClasuseAdded = true;
		}

		if (studentFeeTransactionSearchCriteria.getToDate() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString
					.append("( (sft.externalTransactionDate is null and  sft.transactionDate <= :toDate) or (sft.externalTransactionDate is not null and  sft.externalTransactionDate <= :toDate)) ");
			whereClasuseAdded = true;
		}

		if (studentFeeTransactionSearchCriteria.getTransactionTypes() != null && !studentFeeTransactionSearchCriteria.getTransactionTypes().isEmpty()) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" sft.studentFeeTransactionType in :studentFeeTransactionTypes");
			whereClasuseAdded = true;
		}

		if (true) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" sft.studentFeeTransactionStatus in :studentFeeTransactionStatusConstants");
			whereClasuseAdded = true;
		}
		final TypedQuery<StudentFeeTransaction> query = getEntityManager().createQuery(queryString.toString(), StudentFeeTransaction.class);

		if (studentFeeTransactionSearchCriteria.getBranch() != null) {
			query.setParameter("branch", studentFeeTransactionSearchCriteria.getBranch());
		}
		if (studentFeeTransactionSearchCriteria.getFromDate() != null) {
			query.setParameter("fromDate", studentFeeTransactionSearchCriteria.getFromDate());
		}
		if (studentFeeTransactionSearchCriteria.getToDate() != null) {
			query.setParameter("toDate", studentFeeTransactionSearchCriteria.getToDate());
		}
		if (studentFeeTransactionSearchCriteria.getTransactionTypes() != null && !studentFeeTransactionSearchCriteria.getTransactionTypes().isEmpty()) {
			query.setParameter("studentFeeTransactionTypes", studentFeeTransactionSearchCriteria.getTransactionTypes());
		}
		if (studentFeeTransactionSearchCriteria.getStudentDeeTransactionStatus() != null
				&& !studentFeeTransactionSearchCriteria.getStudentDeeTransactionStatus().isEmpty()) {
			query.setParameter("studentFeeTransactionStatusConstants", studentFeeTransactionSearchCriteria.getStudentDeeTransactionStatus());
		} else {
			query.setParameter("studentFeeTransactionStatusConstants", StudentFeeTransactionStatusConstant.getAllStatusForRequestAndPending());

		}
		return query.getResultList();
	}

	@Override
	public Collection<StudentFeeTransaction> findFeesCollectedBySearchCriteria(final FeeCollectedSearchCriteria feeCollectedSearchCriteria)
			throws BusinessException {
		StringBuilder queryString = new StringBuilder("select sft from StudentFeeTransaction sft ");
		boolean whereClasuseAdded = false;

		if (feeCollectedSearchCriteria.getBranch() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" sft.studentAcademicYear.student.branch = :branch ");
			whereClasuseAdded = true;
		}

		if (true) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" sft.studentFeeTransactionStatus = :studentFeeTransactionStatusConstant");
			whereClasuseAdded = true;
		}

		if (feeCollectedSearchCriteria.getAcademicYear() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" sft.studentAcademicYear.academicYear = :academicYear ");
			whereClasuseAdded = true;
		}

		if (feeCollectedSearchCriteria.getFromDate() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString
					.append(" ((sft.transactionDate >= :fromDate and sft.externalTransactionDate is null) or sft.externalTransactionDate >= :fromDate) ");
			whereClasuseAdded = true;
		}

		if (feeCollectedSearchCriteria.getToDate() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString
					.append(" ((sft.transactionDate <= :toDate and sft.externalTransactionDate is null)  or sft.externalTransactionDate <= :toDate) ");
			whereClasuseAdded = true;
		}

		if (feeCollectedSearchCriteria.getKlass() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString
					.append(" sft.studentAcademicYear in ( select ss.studentAcademicYear from StudentSection ss where ss.section.klass = :klass ) ");
			whereClasuseAdded = true;
		}

		if (feeCollectedSearchCriteria.getSection() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString
					.append(" sft.studentAcademicYear in ( select ss.studentAcademicYear from StudentSection ss where ss.section = :section ) ");
			whereClasuseAdded = true;
		}

		final TypedQuery<StudentFeeTransaction> query = getEntityManager().createQuery(queryString.toString(), StudentFeeTransaction.class);

		if (feeCollectedSearchCriteria.getBranch() != null) {
			query.setParameter("branch", feeCollectedSearchCriteria.getBranch());
		}
		if (feeCollectedSearchCriteria.getFromDate() != null) {
			query.setParameter("fromDate", feeCollectedSearchCriteria.getFromDate());
		}
		if (feeCollectedSearchCriteria.getToDate() != null) {
			query.setParameter("toDate", feeCollectedSearchCriteria.getToDate());
		}

		if (feeCollectedSearchCriteria.getAcademicYear() != null) {
			query.setParameter("academicYear", feeCollectedSearchCriteria.getAcademicYear());
		}

		if (feeCollectedSearchCriteria.getKlass() != null) {
			query.setParameter("klass", feeCollectedSearchCriteria.getKlass());
		}

		if (feeCollectedSearchCriteria.getSection() != null) {
			query.setParameter("section", feeCollectedSearchCriteria.getSection());
		}

		query.setParameter("studentFeeTransactionStatusConstant", StudentFeeTransactionStatusConstant.PAYMENT_PROCESSED);

		return query.getResultList();
	}

}
