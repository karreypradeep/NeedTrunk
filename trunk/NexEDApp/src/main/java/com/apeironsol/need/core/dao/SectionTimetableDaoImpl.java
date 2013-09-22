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

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.SectionTimetable;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("sectionTimetableDao")
public class SectionTimetableDaoImpl extends BaseDaoImpl<SectionTimetable> implements SectionTimetableDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionTimetable> findSectionTimetablesByScetionId(final Long sectionId) {
		TypedQuery<SectionTimetable> query = this.getEntityManager().createQuery(
				"select st from SectionTimetable st where st.sectionTeacher.section.id = :sectionId",
				SectionTimetable.class);
		query.setParameter("sectionId", sectionId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionTimetable> findSectionTimetableByScetionIdAndEmployeeId(final Long sectionId,
			final Long employeeId) {
		TypedQuery<SectionTimetable> query = this.getEntityManager().createQuery(
				"select st from SectionTimetable st where st.sectionTeacher.section.id = :sectionId and"
						+ " sa.employee.id = :employeeId", SectionTimetable.class);
		query.setParameter("sectionId", sectionId);
		query.setParameter("employeeId", employeeId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionTimetable> findSectionTimetablesByScetionIdAndScheduleDate(final Long sectionId,
			final Date scheduleDate) {
		TypedQuery<SectionTimetable> query = this
				.getEntityManager()
				.createQuery(
						"select st from SectionTimetable st where st.sectionTeacher.section.id = :sectionId and st.scheduleDate = :scheduleDate",
						SectionTimetable.class);
		query.setParameter("sectionId", sectionId);
		query.setParameter("scheduleDate", scheduleDate);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionTimetable> findSectionTimetableByScetionIdAndEmployeeIdAndScheduleDate(
			final Long sectionId, final Long employeeId, final Date scheduleDate) {
		TypedQuery<SectionTimetable> query = this
				.getEntityManager()
				.createQuery(
						"select st from SectionTimetable st where st.sectionTeacher.section.id = :sectionId and sa.employee.id = :employeeId and st.scheduleDate = :scheduleDate",
						SectionTimetable.class);
		query.setParameter("sectionId", sectionId);
		query.setParameter("scheduleDate", scheduleDate);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionTimetable> findSectionTimetablesByScetionIdBetweenDates(final Long sectionId,
			final Date startDate, final Date endDate) {
		TypedQuery<SectionTimetable> query = this
				.getEntityManager()
				.createQuery(
						"select st from SectionTimetable st where st.sectionTeacher.section.id = :sectionId and st.scheduleDate >= :startDate and st.scheduleDate <= :endDate ",
						SectionTimetable.class);
		query.setParameter("sectionId", sectionId);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionTimetable> getOverLappingTimeTableForSection(final Long sectionId,
			final Date scheduleStartDate, final Date scheduleEndDate, final Date scheduleStartTime,
			final Date scheduleEndTime) {
		TypedQuery<SectionTimetable> query = this
				.getEntityManager()
				.createQuery(
						"select st from SectionTimetable st where st.sectionTeacher.section.id = :sectionId and (st.scheduleDate >= :startDate and st.scheduleDate <= :endDate)"
								+ " and ((st.startTime >= :startTime and st.startTime <= :endTime) or (st.endTime >= :startTime and st.endTime <= :endTime)) ",
						SectionTimetable.class);
		query.setParameter("sectionId", sectionId);
		query.setParameter("startDate", scheduleStartDate);
		query.setParameter("endDate", scheduleEndDate);
		query.setParameter("startTime", scheduleStartTime);
		query.setParameter("endTime", scheduleEndTime);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionTimetable> getOverLappingTimeTableForTeacher(final Long teacherId,
			final Date scheduleStartDate, final Date scheduleEndDate, final Date scheduleStartTime,
			final Date scheduleEndTime) {
		TypedQuery<SectionTimetable> query = this
				.getEntityManager()
				.createQuery(
						"select st from SectionTimetable st where st.sectionTeacher.employee.id = :teacherId and (st.scheduleDate >= :startDate and st.scheduleDate <= :endDate)"
								+ " and ((st.startTime >= :startTime and st.startTime <= :endTime) or (st.endTime >= :startTime and st.endTime <= :endTime)) ",
						SectionTimetable.class);
		query.setParameter("teacherId", teacherId);
		query.setParameter("startDate", scheduleStartDate);
		query.setParameter("endDate", scheduleEndDate);
		query.setParameter("startTime", scheduleStartTime);
		query.setParameter("endTime", scheduleEndTime);
		return query.getResultList();
	}
}
