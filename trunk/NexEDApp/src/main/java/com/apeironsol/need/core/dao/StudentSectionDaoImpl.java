package com.apeironsol.need.core.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.util.constants.StudentSectionStatusConstant;
import com.apeironsol.need.util.searchcriteria.StudentSearchCriteria;

@Repository("studentSectionDao")
public class StudentSectionDaoImpl extends BaseDaoImpl<StudentSection> implements StudentSectionDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentSection> findStudentSectionByStudendAcademicYearId(final Long studentAcademicYearId) {

		final TypedQuery<StudentSection> query = this.getEntityManager().createQuery(
				"select s from StudentSection s where s.studentAcademicYear.id = :studentAcademicYearId", StudentSection.class);
		query.setParameter("studentAcademicYearId", studentAcademicYearId);
		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeStudentSectionByStudendAcademicYearId(final Long studentAcademicYearId) {

		final Query query = this.getEntityManager().createQuery("delete from StudentSection s where s.studentAcademicYear.id = :studentAcademicYearId");
		query.setParameter("studentAcademicYearId", studentAcademicYearId);
		query.executeUpdate();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentSection> findStudentSectionsBySectionId(final Long sectionId) {

		final TypedQuery<StudentSection> query = this.getEntityManager().createQuery("select s from StudentSection s where s.section.id = :sectionId",
				StudentSection.class);
		query.setParameter("sectionId", sectionId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentSection> findStudentSectionsBySectionIdAndStateActive(final Long sectionId) {
		final TypedQuery<StudentSection> query = this.getEntityManager().createQuery(
				"select s from StudentSection s where s.section.id = :sectionId and s.studentSectionStatus = :studentSectionStatus", StudentSection.class);
		query.setParameter("sectionId", sectionId);
		query.setParameter("studentSectionStatus", StudentSectionStatusConstant.ACTIVE);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentSection findStudentSectionByStudentAcademicYearIdAndSectionId(final Long studentAcademicYearId, final Long sectionId) {
		try {
			final TypedQuery<StudentSection> query = this.getEntityManager().createQuery(
					"select s from StudentSection s where s.studentAcademicYear.id = :studentAcademicYearId and s.section.id = :sectionId",
					StudentSection.class);
			query.setParameter("studentAcademicYearId", studentAcademicYearId);
			query.setParameter("sectionId", sectionId);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentSection> findStudentSectionsBySectionIdsAndStatus(final Collection<Long> sectionIds, final StudentSectionStatusConstant status) {
		final TypedQuery<StudentSection> query = this.getEntityManager().createQuery(
				"select s from StudentSection s where s.section.id in :sectionIds and s.studentSectionStatus = :status", StudentSection.class);
		query.setParameter("sectionIds", sectionIds);
		query.setParameter("status", status);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int findNumberOfActiveStudentsBySectionId(final Long sectionId) {
		final Query query = this.getEntityManager().createQuery(
				"select count(s) from StudentSection s where s.section.id = :sectionId and s.studentSectionStatus = :studentSectionStatus");
		query.setParameter("sectionId", sectionId);
		query.setParameter("studentSectionStatus", StudentSectionStatusConstant.ACTIVE);
		final List<Object> obs = query.getResultList();
		final long count = (Long) obs.get(0);
		return (int) count;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Long, Integer> findNumberOfActiveStudentsBySectionIds(final Collection<Long> sectionIds) {
		final Map<Long, Integer> studentsCount = new HashMap<Long, Integer>();
		for (final Long sectionId : sectionIds) {
			studentsCount.put(sectionId, this.findNumberOfActiveStudentsBySectionId(sectionId));
		}
		return studentsCount;
	}

	/**
	 * {@inheritDoc} TODO have to overwrite this method to use criteria queries.
	 */
	@Override
	public Collection<StudentSection> findStudentSectionsBySearchCriteria(final StudentSearchCriteria studentSearchCriteria) throws BusinessException {
		StringBuilder queryString = new StringBuilder("select ss from StudentSection ss ");
		boolean whereClasuseAdded = false;
		// ACTIVE("active"), COMPLETED("completed")
		StudentSectionStatusConstant studentSectionStatusConstant = null;
		final Collection<StudentSectionStatusConstant> studentSectionStatusConstants = new ArrayList<StudentSectionStatusConstant>();
		if (studentSearchCriteria.getStudentStatus() == null) {
			studentSectionStatusConstant = StudentSectionStatusConstant.ACTIVE;
		} else {
			studentSectionStatusConstant = StudentSectionStatusConstant.getStudentSectionStatusForStudentStatus(studentSearchCriteria.getStudentStatus());
		}
		if (studentSectionStatusConstant == null) {
			studentSectionStatusConstant = StudentSectionStatusConstant.ACTIVE;
		}
		studentSectionStatusConstants.add(studentSectionStatusConstant);
		if (studentSearchCriteria.getBranch() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" ss.studentAcademicYear.student.branch = :branch ");
			whereClasuseAdded = true;
		}

		if (!whereClasuseAdded) {
			queryString = queryString.append(" where ");
		} else {
			queryString = queryString.append(" and ");
		}
		queryString = queryString.append(" ss.studentSectionStatus in :stuSectionStatus ");
		whereClasuseAdded = true;

		if (studentSearchCriteria.getName() != null && !studentSearchCriteria.getName().trim().isEmpty()) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" ( ");
			queryString = queryString.append(" ss.studentAcademicYear.student.firstName like '%" + studentSearchCriteria.getName() + "%'");
			queryString = queryString.append(" or ss.studentAcademicYear.student.lastName like '%" + studentSearchCriteria.getName() + "%'");
			queryString = queryString.append(" or ss.studentAcademicYear.student.middleName like '%" + studentSearchCriteria.getName() + "%'");
			queryString = queryString.append(" ) ");
			whereClasuseAdded = true;
		}

		if (studentSearchCriteria.getAdmissionNumber() != null && !studentSearchCriteria.getAdmissionNumber().trim().isEmpty()) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" ( ");
			queryString = queryString.append(" ss.studentAcademicYear.student.admissionNr like '%" + studentSearchCriteria.getAdmissionNumber() + "%'");
			queryString = queryString.append("or ss.studentAcademicYear.student.externalAdmissionNr like '%" + studentSearchCriteria.getAdmissionNumber()
					+ "%'");
			queryString = queryString.append(" ) ");
			whereClasuseAdded = true;
		}

		if (studentSearchCriteria.getRegistrationNumber() != null && !studentSearchCriteria.getRegistrationNumber().trim().isEmpty()) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" ss.studentAcademicYear.student.registrationNr like '%" + studentSearchCriteria.getRegistrationNumber() + "%'");
			whereClasuseAdded = true;
		}

		if (studentSearchCriteria.getDateOfBirth() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" ss.studentAcademicYear.student.dateOfBirth = :dateOfBirth ");
			whereClasuseAdded = true;
		}

		if (studentSearchCriteria.getAcademicYear() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" ss.studentAcademicYear.academicYear = :academicYear ");
			whereClasuseAdded = true;
		}

		if (studentSearchCriteria.getStudentStatus() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" ss.studentAcademicYear.student.studentStatus = :studentStatus ");
			whereClasuseAdded = true;
		}

		if (studentSearchCriteria.getKlass() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" ss.section.klass = :klass");
			whereClasuseAdded = true;
		}
		if (studentSearchCriteria.getSection() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" ss.section = :section");
			whereClasuseAdded = true;
		}

		final TypedQuery<StudentSection> query = this.getEntityManager().createQuery(queryString.toString(), StudentSection.class);

		if (studentSearchCriteria.getBranch() != null) {
			query.setParameter("branch", studentSearchCriteria.getBranch());
		}
		query.setParameter("stuSectionStatus", studentSectionStatusConstants);
		if (studentSearchCriteria.getDateOfBirth() != null) {
			query.setParameter("dateOfBirth", studentSearchCriteria.getDateOfBirth());
		}
		if (studentSearchCriteria.getKlass() != null) {
			query.setParameter("klass", studentSearchCriteria.getKlass());
		}
		if (studentSearchCriteria.getSection() != null) {
			query.setParameter("section", studentSearchCriteria.getSection());
		}
		if (studentSearchCriteria.getStudentStatus() != null) {
			query.setParameter("studentStatus", studentSearchCriteria.getStudentStatus());
		}
		if (studentSearchCriteria.getAcademicYear() != null) {
			query.setParameter("academicYear", studentSearchCriteria.getAcademicYear());
		}
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentSection findStudentSectionByStudentAcademicYearIdAndStatus(final Long studentAcademicYearId, final StudentSectionStatusConstant status) {
		try {
			final TypedQuery<StudentSection> query = this
					.getEntityManager()
					.createQuery(
							"select s from StudentSection s where s.studentAcademicYear.id = :studentAcademicYearId and s.studentSectionStatus = :studentSectionStatus",
							StudentSection.class);
			query.setParameter("studentAcademicYearId", studentAcademicYearId);
			query.setParameter("studentSectionStatus", status);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentSection findStudentSectionByStudentAcademicYearIdAndActiveStatus(final Long studentAcademicYearId) {
		try {
			final TypedQuery<StudentSection> query = this
					.getEntityManager()
					.createQuery(
							"select s from StudentSection s where s.studentAcademicYear.id = :studentAcademicYearId and s.studentSectionStatus = :studentSectionStatus",
							StudentSection.class);
			query.setParameter("studentAcademicYearId", studentAcademicYearId);
			query.setParameter("studentSectionStatus", StudentSectionStatusConstant.ACTIVE);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

	@Override
	public StudentSection findLatestStudentSectionByStudentAcademicYearId(final Long studentAcademicYearId) {
		final TypedQuery<StudentSection> query = this
				.getEntityManager()
				.createQuery(
						"select ss from StudentSection ss where ss.studentAcademicYear.id = :studentAcademicYearId and ss.sequenceNr = (select max(ss1.sequenceNr) from StudentSection ss1 where ss1.studentAcademicYear.id = :studentAcademicYearId)",
						StudentSection.class);
		query.setParameter("studentAcademicYearId", studentAcademicYearId);
		return query.getSingleResult();
	}

	@Override
	public StudentSection findStudentSectionByStudentIdAndSectionId(final Long studentId, final Long sectionId) {
		try {
			final TypedQuery<StudentSection> query = this.getEntityManager().createQuery(
					"select s from StudentSection s where s.studentAcademicYear.student.id = :studentId and s.section.id = :sectionId", StudentSection.class);
			query.setParameter("studentId", studentId);
			query.setParameter("sectionId", sectionId);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentSection> findAllStudentSectionsBySectionIds(final Collection<Long> sectionIds) {
		final TypedQuery<StudentSection> query = this.getEntityManager().createQuery("select s from StudentSection s where s.section.id in :sectionIds",
				StudentSection.class);
		query.setParameter("sectionIds", sectionIds);
		return query.getResultList();
	}

}
