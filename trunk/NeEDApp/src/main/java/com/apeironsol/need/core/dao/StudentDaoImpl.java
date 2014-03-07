/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.util.constants.StudentStatusConstant;
import com.apeironsol.need.util.searchcriteria.AdmissionSearchCriteria;

/**
 * Data access interface for student entity implementation.
 * 
 * @author Pradeep
 */
@Repository("studentDao")
public class StudentDaoImpl extends BaseDaoImpl<Student> implements StudentDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Student> findStudentsByBranchId(final Long branchId) {
		final TypedQuery<Student> query = this.getEntityManager().createQuery("select s from Student s where s.branch.id = :id", Student.class);
		query.setParameter("id", branchId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Student findStudentByAdmissionId(final Long admissionId) {
		final TypedQuery<Student> query = this.getEntityManager().createQuery("select s from Student s where s.admission.id = :id", Student.class);
		query.setParameter("id", admissionId);
		return query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Student> findActiveStudentsByBranchId(final Long branchId) {
		final TypedQuery<Student> query = this.getEntityManager().createQuery(
				"select s from Student s where s.branch.id = :id and s.studentStatus = :studentStatus", Student.class);
		query.setParameter("id", branchId);
		query.setParameter("studentStatus", StudentStatusConstant.ACTIVE);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Student> findAdmissionsBySearchCriteria(final AdmissionSearchCriteria admissionSearchCriteria) {
		StringBuilder queryString = new StringBuilder("select s from Student s ");
		boolean whereClasuseAdded = false;

		if (admissionSearchCriteria.getBranch() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" s.branch = :branch ");
			whereClasuseAdded = true;
		}

		if ((admissionSearchCriteria.getName() != null) && !admissionSearchCriteria.getName().trim().isEmpty()) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" ( ");
			queryString = queryString.append(" s.firstName like '%" + admissionSearchCriteria.getName() + "%'");
			queryString = queryString.append(" or s.lastName like '%" + admissionSearchCriteria.getName() + "%'");
			queryString = queryString.append(" or s.middleName like '%" + admissionSearchCriteria.getName() + "%'");
			queryString = queryString.append(" ) ");
			whereClasuseAdded = true;
		}

		if ((admissionSearchCriteria.getAdmissionNumber() != null) && !admissionSearchCriteria.getAdmissionNumber().trim().isEmpty()) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" ( ");
			queryString = queryString.append(" s.admissionNr like '%" + admissionSearchCriteria.getAdmissionNumber() + "%'");
			queryString = queryString.append("or s.externalAdmissionNr like '%" + admissionSearchCriteria.getAdmissionNumber() + "%'");
			queryString = queryString.append(" ) ");
			whereClasuseAdded = true;
		}

		if ((admissionSearchCriteria.getRegistrationNumber() != null) && !admissionSearchCriteria.getRegistrationNumber().trim().isEmpty()) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" s.registrationNr like '%" + admissionSearchCriteria.getRegistrationNumber() + "%'");
			whereClasuseAdded = true;
		}

		if (admissionSearchCriteria.getDateOfBirth() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" s.dateOfBirth = :dateOfBirth ");
			whereClasuseAdded = true;
		}

		if (admissionSearchCriteria.getAcademicYear() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" s.appliedForAcademicYear = :academicYear ");
			whereClasuseAdded = true;
		}

		if (admissionSearchCriteria.getAdmissionStatusConstant() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" s.admissionStatus = :admissionStatus ");
			whereClasuseAdded = true;
		}

		if (admissionSearchCriteria.getAcceptedForKlass() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" s.acceptedForKlass in :acceptedForKlass");
			whereClasuseAdded = true;
		}

		if (admissionSearchCriteria.getAppliedForClass() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" s.applyingForKlass in :applyingForKlass");
			whereClasuseAdded = true;
		}

		final TypedQuery<Student> query = this.getEntityManager().createQuery(queryString.toString(), Student.class);
		if (admissionSearchCriteria.getBranch() != null) {
			query.setParameter("branch", admissionSearchCriteria.getBranch());
		}
		if (admissionSearchCriteria.getDateOfBirth() != null) {
			query.setParameter("dateOfBirth", admissionSearchCriteria.getDateOfBirth());
		}
		if (admissionSearchCriteria.getAcceptedForKlass() != null) {
			query.setParameter("klass", admissionSearchCriteria.getAcceptedForKlass());
		}
		if (admissionSearchCriteria.getAdmissionStatusConstant() != null) {
			query.setParameter("admissionStatus", admissionSearchCriteria.getAdmissionStatusConstant());
		}
		if (admissionSearchCriteria.getAcademicYear() != null) {
			query.setParameter("academicYear", admissionSearchCriteria.getAcademicYear());
		}
		if (admissionSearchCriteria.getAcceptedForKlass() != null) {
			query.setParameter("acceptedForKlass", admissionSearchCriteria.getAcceptedForKlass());
		}
		if (admissionSearchCriteria.getAppliedForClass() != null) {
			query.setParameter("applyingForKlass", admissionSearchCriteria.getAppliedForClass());
		}
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Student findActiveStudentByAdmissionNumber(final String admissionNumber) {
		try {
			final TypedQuery<Student> query = this.getEntityManager().createQuery(
					"select s from Student s where s.admissionNr = :admissionNumber and s.studentStatus = :studentStatus", Student.class);
			query.setParameter("admissionNumber", admissionNumber);
			query.setParameter("studentStatus", StudentStatusConstant.ACTIVE);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Student findStudentByUsername(final String username) {
		try {
			final TypedQuery<Student> query = this.getEntityManager().createQuery("select s from Student s where s.userAccount.username = :username",
					Student.class);
			query.setParameter("username", username);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

	@Override
	public Long findActiveStudentsCountForBranchId(final Long branchId) {
		final Query query = this.getEntityManager().createQuery("select count(s) from Student s where s.branch.id = :id and s.studentStatus = :studentStatus");
		query.setParameter("id", branchId);
		query.setParameter("studentStatus", StudentStatusConstant.ACTIVE);
		return Long.valueOf(query.getSingleResult().toString());
	}

	@Override
	public Student findActiveStudentByExternalAdmissionNumberAndBranchId(final String externalAdmissionNumber, final Long branchId) {
		try {
			final TypedQuery<Student> query = this.getEntityManager().createQuery(
					"select s from Student s where s.externalAdmissionNr = :externalAdmissionNr and s.branch.id = :branchId", Student.class);
			query.setParameter("externalAdmissionNr", externalAdmissionNumber);
			query.setParameter("branchId", branchId);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}
}
