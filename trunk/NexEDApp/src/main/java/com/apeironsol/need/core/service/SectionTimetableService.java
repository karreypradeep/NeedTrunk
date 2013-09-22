/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.core.model.SectionTimetable;
import com.apeironsol.need.util.constants.WeekDayConstant;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 *  Service layer interface for section timetable DAO implementation.
 * 
 * @author Pradeep
 * 
 */
public interface SectionTimetableService {

	/**
	 * Find section timetables by section object id.
	 * 
	 * @param sectionId
	 *            section object id.
	 * @return collection of section timetables by section object id.
	 * @throws BusinessException In case of exception.
	 */
	Collection<SectionTimetable> findSectionTimetablesByScetionId(Long sectionId) throws BusinessException ;

	/**
	 * Find section timetables by section object id.
	 * 
	 * @param sectionId
	 *            section object id.
	 * @param scheduleDate
	 *            scheduleDate.
	 * @return collection of section timetables by section object id and
	 *         scheduleDate.
	 * @throws BusinessException In case of exception.
	 */
	Collection<SectionTimetable> findSectionTimetablesByScetionIdAndScheduleDate(Long sectionId, final Date scheduleDate) throws BusinessException ;

	/**
	 * Find section timetables by section object id.
	 * 
	 * @param sectionId
	 *            section object id.
	 * @param startDate
	 *            startDate.
	 * @param endDate
	 *            endDate.
	 * @return collection of section timetables by section object id and
	 *         scheduleDate.
	 * @throws BusinessException In case of exception.
	 */
	Collection<SectionTimetable> findSectionTimetablesByScetionIdBetweenDates(Long sectionId, final Date startDate,
			final Date endDate) throws BusinessException ;

	/**
	 * Find section timetable by section object id and employee id.
	 * 
	 * @param sectionId
	 *            section object id.
	 * @param employeeId
	 *            employee id.
	 * @return section timetable by section object id and employee id.
	 * @throws BusinessException In case of exception.
	 */
	Collection<SectionTimetable> findSectionTimetableByScetionIdAndEmployeeId(Long sectionId, Long employeeId)throws BusinessException ;

	/**
	 * Find section timetable by section object id and employee id.
	 * 
	 * @param sectionId
	 *            section object id.
	 * @param employeeId
	 *            employee id.
	 * @param scheduleDate
	 *            scheduleDate.
	 * @return section timetable by section object id and employee id and
	 *         scheduleDate.
	 * @throws BusinessException In case of exception.
	 */
	Collection<SectionTimetable> findSectionTimetableByScetionIdAndEmployeeIdAndScheduleDate(Long sectionId,
			Long employeeId, final Date scheduleDate)throws BusinessException ;

	/**
	 * Retrieve section timetable by object id.
	 * 
	 * @param id
	 *            id of section timetable.
	 * @return section timetable by object id.
	 * @throws BusinessException In case of exception.
	 */
	SectionTimetable findSectionTimetableById(Long id) throws BusinessException;

	/**
	 * Saves supplied section timetable.
	 * 
	 * @param SectionTimetable
	 *            section timetable.
	 * @return section timetable.
	 * @throws BusinessException In case of exception.
	 * @throws InvalidArgumentException
	 */
	SectionTimetable saveSectionTimetable(SectionTimetable sectionTimetable) throws BusinessException,
			InvalidArgumentException;

	/**
	 * Removes section timetable.
	 * 
	 * @param SectionTimetable
	 *            section timetable to be removed.
	 * @throws BusinessException In case of exception.
	 */
	void removeSectionTimetable(SectionTimetable sectionTimetable) throws BusinessException;

	/**
	 * Retrieves all section timetable.
	 * 
	 * @return collection of all section timetable.
	 * @throws BusinessException In case of exception.
	 */
	Collection<SectionTimetable> findAllSectionTimetables() throws BusinessException;

	/**
	 * Saves supplied section timetable.
	 * 
	 * @param SectionTimetable
	 *            section timetable.
	 * @return section timetable.
	 * @throws BusinessException In case of exception.
	 * @throws InvalidArgumentException
	 */
	Collection<SectionTimetable> saveSectionTimetables(Collection<SectionTimetable> sectionTimetables)
			throws BusinessException, InvalidArgumentException;

	/**
	 * Validates schedule events for time overlapping in section.
	 * 
	 * @return true the timetable event is valid.
	 * @throws BusinessException In case of exception.
	 */
	Collection<SectionTimetable> getOverLappingTimeTableForSection(final Long sectionId, final Date scheduleStartDate,
			final Date scheduleEndDate, final Date scheduleStartTime, final Date scheduleEndTime)throws BusinessException ;

	/**
	 * Validates schedule events for time overlapping for teacher.
	 * 
	 * @return true the timetable event is valid.
	 * @throws BusinessException In case of exception.
	 */
	Collection<SectionTimetable> getOverLappingTimeTableForTeacher(final Long teacherId, final Date scheduleStartDate,
			final Date scheduleEndDate, final Date scheduleStartTime, final Date scheduleEndTime) throws BusinessException;

	/**
	 * Create section timetables based on start date and end date supplied and
	 * section timetable.
	 * 
	 * @param scheduleStartDate schedule start date.
	 * @param scheduleEndDate schedule end date.
	 * @param sectionTimetable section timetable.
	 * @param weekDaysAllowed week days allowed. 
	 * @return
	 * @throws BusinessException In case of exception.
	 * @throws InvalidArgumentException
	 */
	Collection<SectionTimetable> createSectionTimetables(final Date scheduleStartDate, final Date scheduleEndDate,
			final SectionTimetable sectionTimetable, final Collection<WeekDayConstant> weekDaysAllowed)
			throws BusinessException, InvalidArgumentException;

}
