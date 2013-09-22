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

import com.apeironsol.need.core.model.SectionTimetable;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for Student timetable entity implementation.
 * 
 * @author Pradeep
 * 
 */
public interface SectionTimetableDao extends BaseDao<SectionTimetable> {

	/**
	 * Find student timetables by section object id.
	 * 
	 * @param sectionId
	 *            section object id.
	 * @return collection of student timetables by section object id.
	 */
	Collection<SectionTimetable> findSectionTimetablesByScetionId(Long sectionId);

	/**
	 * Find student timetables by section object id.
	 * 
	 * @param sectionId
	 *            section object id.
	 * @param scheduleDate
	 *            scheduleDate.
	 * @return collection of student timetables by section object id and
	 *         scheduleDate.
	 */
	Collection<SectionTimetable> findSectionTimetablesByScetionIdAndScheduleDate(Long sectionId, final Date scheduleDate);

	/**
	 * Find student timetables by section object id.
	 * 
	 * @param sectionId
	 *            section object id.
	 * @param startDate
	 *            startDate.
	 * @param endDate
	 *            endDate.
	 * @return collection of student timetables by section object id and
	 *         scheduleDate.
	 */
	Collection<SectionTimetable> findSectionTimetablesByScetionIdBetweenDates(Long sectionId, final Date startDate,
			final Date endDate);

	/**
	 * Find student timetable by section object id and employee id.
	 * 
	 * @param sectionId
	 *            section object id.
	 * @param employeeId
	 *            employee id.
	 * @return student timetable by section object id and employee id.
	 */
	Collection<SectionTimetable> findSectionTimetableByScetionIdAndEmployeeId(Long sectionId, Long employeeId);

	/**
	 * Find student timetable by section object id and employee id.
	 * 
	 * @param sectionId
	 *            section object id.
	 * @param employeeId
	 *            employee id.
	 * @param scheduleDate
	 *            scheduleDate.
	 * @return student timetable by section object id and employee id and
	 *         scheduleDate.
	 */
	Collection<SectionTimetable> findSectionTimetableByScetionIdAndEmployeeIdAndScheduleDate(Long sectionId,
			Long employeeId, final Date scheduleDate);

	/**
	 * Validates schedule events for time overlapping in section.
	 * 
	 * @return true the timetable event is valid.
	 */
	Collection<SectionTimetable> getOverLappingTimeTableForSection(final Long sectionId, final Date scheduleStartDate,
			final Date scheduleEndDate, final Date scheduleStartTime, final Date scheduleEndTime);

	/**
	 * Validates schedule events for time overlapping in section.
	 * 
	 * @return true the timetable event is valid.
	 */
	Collection<SectionTimetable> getOverLappingTimeTableForTeacher(final Long teacherId, final Date scheduleStartDate,
			final Date scheduleEndDate, final Date scheduleStartTime, final Date scheduleEndTime);
}
