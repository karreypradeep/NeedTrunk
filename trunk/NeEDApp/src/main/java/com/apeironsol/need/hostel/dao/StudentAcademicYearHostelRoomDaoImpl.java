/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.hostel.dao;

import java.util.Collection;
import java.util.Date;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.need.hostel.model.StudentAcademicYearHostelRoom;
import com.apeironsol.need.util.NonNull;

/**
 * Data access interface for employee salary entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("studentAcademicYearHostelRoomDao")
public class StudentAcademicYearHostelRoomDaoImpl extends BaseDaoImpl<StudentAcademicYearHostelRoom> implements StudentAcademicYearHostelRoomDao {

	@Override
	public Collection<StudentAcademicYearHostelRoom> findHostelRoomOccupantsForAcademicYear(@NonNull final Long hostelRoomId, @NonNull final Long academicYearId) {
		TypedQuery<StudentAcademicYearHostelRoom> query = this
				.getEntityManager()
				.createQuery(
						"select sayhr from StudentAcademicYearHostelRoom sayhr where sayhr.studentAcademicYear.academicYear.id  = :academicYearId and sayhr.hostelRoom.id  = :hostelRoomId",
						StudentAcademicYearHostelRoom.class);
		query.setParameter("hostelRoomId", hostelRoomId);
		query.setParameter("academicYearId", academicYearId);
		return query.getResultList();

	}

	@Override
	public Collection<StudentAcademicYearHostelRoom> findAllHostelRoomsByStudentAcademicYear(@NonNull final Long studentAcademicYearId) {
		TypedQuery<StudentAcademicYearHostelRoom> query = this.getEntityManager().createQuery(
				"select sayhr from StudentAcademicYearHostelRoom sayhr where  sayhr.studentAcademicYear.id  = :studentAcademicYearId",
				StudentAcademicYearHostelRoom.class);
		query.setParameter("studentAcademicYearId", studentAcademicYearId);
		return query.getResultList();
	}

	@Override
	public Collection<StudentAcademicYearHostelRoom> findStudentAcademicYearHostelRoomsByRoomId(@NonNull final Long hostelRoomId) {
		TypedQuery<StudentAcademicYearHostelRoom> query = this.getEntityManager().createQuery(
				"select sayhr from StudentAcademicYearHostelRoom sayhr where  sayhr.hostelRoom.id  = :hostelRoomId", StudentAcademicYearHostelRoom.class);
		query.setParameter("hostelRoomId", hostelRoomId);
		return query.getResultList();
	}

	@Override
	public Collection<StudentAcademicYearHostelRoom> findCurrentOccupantsForHostelRoom(@NonNull final Long roomId) {
		TypedQuery<StudentAcademicYearHostelRoom> query = this.getEntityManager().createQuery(
				"select sayhr from StudentAcademicYearHostelRoom sayhr where  sayhr.hostelRoom.id  = :hostelRoomId and sayhr.endDate is null",
				StudentAcademicYearHostelRoom.class);
		query.setParameter("hostelRoomId", roomId);
		return query.getResultList();
	}

	@Override
	public StudentAcademicYearHostelRoom findStudentAcademicYearHostelRoomForStudentForDate(@NonNull final Long studentAcademicYearId,
			@NonNull final Date startDate) {
		StudentAcademicYearHostelRoom result = null;
		try {
			TypedQuery<StudentAcademicYearHostelRoom> query = this
					.getEntityManager()
					.createQuery(
							"select sayhr from StudentAcademicYearHostelRoom sayhr where  sayhr.studentAcademicYear.id  = :studentAcademicYearId and (sayhr.endDate >= :startDate)",
							StudentAcademicYearHostelRoom.class);
			query.setParameter("studentAcademicYearId", studentAcademicYearId);
			query.setParameter("startDate", startDate);
			result = query.getSingleResult();
		} catch (NoResultException nre) {

		} catch (NonUniqueResultException nure) {

		}
		return result;
	}

	@Override
	public StudentAcademicYearHostelRoom findCurrentOccupiedStudentAcademicYearHostelRoomForStudent(@NonNull final Long studentAcademicYearId) {
		StudentAcademicYearHostelRoom result = null;
		try {
			TypedQuery<StudentAcademicYearHostelRoom> query = this
					.getEntityManager()
					.createQuery(
							"select sayhr from StudentAcademicYearHostelRoom sayhr where  sayhr.studentAcademicYear.id  = :studentAcademicYearId and (sayhr.endDate is null)",
							StudentAcademicYearHostelRoom.class);
			query.setParameter("studentAcademicYearId", studentAcademicYearId);
			result = query.getSingleResult();
		} catch (NoResultException nre) {

		} catch (NonUniqueResultException nure) {

		}
		return result;
	}
}
