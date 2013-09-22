/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;
import java.util.Date;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.Attendance;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.util.constants.AttendanceTypeConstant;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("attendanceDao")
public class AttendanceDaoImpl extends BaseDaoImpl<Attendance> implements AttendanceDao {

	@Override
	public Attendance findAttendanceBySectionSubjectIdAndAttendanceDate(final Long sectionSubjectId, final Date attendanceDate) {
		try {
			TypedQuery<Attendance> query = this.getEntityManager().createQuery(
					"select a from Attendance a where a.sectionSubject.id = :sectionSubjectId and a.attendanceDate = :attendanceDate", Attendance.class);
			query.setParameter("sectionSubjectId", sectionSubjectId);
			query.setParameter("attendanceDate", attendanceDate);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Collection<Attendance> findAttendanceForSectionBetweenDates(final Long sectionId, final Date attendanceStartDate, final Date attendanceEndDate) {
		TypedQuery<Attendance> query = this
				.getEntityManager()
				.createQuery(
						"select a from Attendance a where a.section.id = :sectionId and a.attendanceDate >= :attendanceStartDate and a.attendanceDate <= :attendanceEndDate",
						Attendance.class);
		query.setParameter("sectionId", sectionId);
		query.setParameter("attendanceStartDate", attendanceStartDate);
		query.setParameter("attendanceEndDate", attendanceEndDate);
		return query.getResultList();
	}

	@Override
	public Attendance findAttendanceBySectionIdAndAttendanceDateForDailyAttendance(final Long sectionId, final Date attendanceDate) {
		try {
			TypedQuery<Attendance> query = this.getEntityManager().createQuery(
					"select a from Attendance a where a.section.id = :sectionId and a.attendanceDate = :attendanceDate and a.attendanceType = :attendanceType",
					Attendance.class);
			query.setParameter("sectionId", sectionId);
			query.setParameter("attendanceDate", attendanceDate);
			query.setParameter("attendanceType", AttendanceTypeConstant.DAILY);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Collection<Attendance> findAttendancesBySectionId(final Long sectionId) {
		TypedQuery<Attendance> query = this.getEntityManager().createQuery("select a from Attendance a where a.section.id = :sectionId", Attendance.class);
		query.setParameter("sectionId", sectionId);
		return query.getResultList();
	}

	@Override
	public Collection<Attendance> findAttendancesBySectionIdAndAttendanceDate(final Long sectionId, final Date attendanceDate) {
		TypedQuery<Attendance> query = this.getEntityManager().createQuery(
				"select a from Attendance a where a.section.id = :sectionId and a.attendanceDate = :attendanceDate", Attendance.class);
		query.setParameter("sectionId", sectionId);
		query.setParameter("attendanceDate", attendanceDate);
		return query.getResultList();
	}

	@Override
	public Collection<Attendance> findAttendancesBySectionsAndAttendanceDate(final Collection<Section> sections, final Date attendanceDate) {
		TypedQuery<Attendance> query = this.getEntityManager().createQuery(
				"select a from Attendance a where a.section in :sectionId and a.attendanceDate = :attendanceDate", Attendance.class);
		query.setParameter("sections", sections);
		query.setParameter("attendanceDate", attendanceDate);
		return query.getResultList();
	}

}
