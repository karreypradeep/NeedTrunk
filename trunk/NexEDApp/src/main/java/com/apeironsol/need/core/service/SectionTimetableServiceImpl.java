/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.SectionTimetableDao;
import com.apeironsol.need.core.model.SectionTimetable;
import com.apeironsol.need.core.portal.messages.BusinessMessages;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.WeekDayConstant;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Service("sectionTimetableService")
@Transactional
public class SectionTimetableServiceImpl implements SectionTimetableService {

	@Resource
	private SectionTimetableDao	sectionTimetableDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SectionTimetable findSectionTimetableById(final Long id) throws BusinessException {
		return this.sectionTimetableDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SectionTimetable saveSectionTimetable(final SectionTimetable sectionTimetable) throws BusinessException,
			InvalidArgumentException {
		this.validateSchedule(sectionTimetable.getScheduleDate(), sectionTimetable.getScheduleDate(), sectionTimetable);
		return this.sectionTimetableDao.persist(sectionTimetable);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeSectionTimetable(final SectionTimetable sectionTimetable) throws BusinessException {
		this.sectionTimetableDao.remove(sectionTimetable);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionTimetable> findAllSectionTimetables() throws BusinessException {
		return this.sectionTimetableDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionTimetable> findSectionTimetablesByScetionId(final Long sectionId)  throws BusinessException{
		return this.sectionTimetableDao.findSectionTimetablesByScetionId(sectionId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionTimetable> findSectionTimetablesByScetionIdAndScheduleDate(final Long sectionId,
			final Date scheduleDate)  throws BusinessException{
		return this.sectionTimetableDao.findSectionTimetablesByScetionIdAndScheduleDate(sectionId, scheduleDate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionTimetable> findSectionTimetablesByScetionIdBetweenDates(final Long sectionId,
			final Date startDate, final Date endDate)  throws BusinessException{
		return this.sectionTimetableDao.findSectionTimetablesByScetionIdBetweenDates(sectionId, startDate, endDate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionTimetable> findSectionTimetableByScetionIdAndEmployeeId(final Long sectionId,
			final Long employeeId)  throws BusinessException{
		return this.sectionTimetableDao.findSectionTimetableByScetionIdAndEmployeeId(sectionId, employeeId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionTimetable> findSectionTimetableByScetionIdAndEmployeeIdAndScheduleDate(
			final Long sectionId, final Long employeeId, final Date scheduleDate)  throws BusinessException{
		return this.sectionTimetableDao.findSectionTimetableByScetionIdAndEmployeeIdAndScheduleDate(sectionId,
				employeeId, scheduleDate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionTimetable> saveSectionTimetables(final Collection<SectionTimetable> sectionTimetables)
			throws BusinessException, InvalidArgumentException {
		Collection<SectionTimetable> result = new ArrayList<SectionTimetable>();
		for (SectionTimetable sectionTimetable : sectionTimetables) {
			result.add(this.saveSectionTimetable(sectionTimetable));
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionTimetable> getOverLappingTimeTableForSection(final Long sectionId,
			final Date scheduleStartDate, final Date scheduleEndDate, final Date scheduleStartTime,
			final Date scheduleEndTime)  throws BusinessException{
		return this.sectionTimetableDao.getOverLappingTimeTableForSection(sectionId, scheduleStartDate,
				scheduleEndDate, scheduleStartTime, scheduleEndTime);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionTimetable> getOverLappingTimeTableForTeacher(final Long teacherId,
			final Date scheduleStartDate, final Date scheduleEndDate, final Date scheduleStartTime,
			final Date scheduleEndTime)  throws BusinessException{
		return this.sectionTimetableDao.getOverLappingTimeTableForTeacher(teacherId, scheduleStartDate,
				scheduleEndDate, scheduleStartTime, scheduleEndTime);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionTimetable> createSectionTimetables(final Date scheduleStartDate,
			final Date scheduleEndDate, final SectionTimetable sectionTimetable,
			final Collection<WeekDayConstant> weelDaysAllowed) throws BusinessException, InvalidArgumentException {
		this.validateSchedule(scheduleStartDate, scheduleEndDate, sectionTimetable);

		Collection<SectionTimetable> tempSaveSectionTimetables = new ArrayList<SectionTimetable>();
		// 1 is added to include end date
		int noOfDays = DateUtil.dateDiffInDays(scheduleStartDate, scheduleEndDate) + 1;
		Calendar scheduleStartTime = Calendar.getInstance();
		SectionTimetable newEntity;
		int eventCount = 0;
		do {
			scheduleStartTime.setTime(scheduleStartDate);
			scheduleStartTime.add(Calendar.DATE, eventCount);
			if (weelDaysAllowed != null
					&& weelDaysAllowed
							.contains(WeekDayConstant.getWeekDay(scheduleStartTime.get(Calendar.DAY_OF_WEEK)))) {
				newEntity = new SectionTimetable();
				newEntity.setEndTime(sectionTimetable.getEndTime());
				newEntity.setScheduleDate(scheduleStartTime.getTime());
				newEntity.setSectionSubject(sectionTimetable.getSectionSubject());
				newEntity.setSectionTeacher(sectionTimetable.getSectionTeacher());
				newEntity.setStartTime(sectionTimetable.getStartTime());
				tempSaveSectionTimetables.add(this.saveSectionTimetable(newEntity));
			}
			eventCount++;
		} while (eventCount < noOfDays);
		return tempSaveSectionTimetables;
	}

	/**
	 * Validates schedule. Checks for not null for start date, end date, start
	 * time and end time. Also checks for valid dates and time.
	 * 
	 * @return
	 */
	private void validateSchedule(final Date scheduleStartDate, final Date scheduleEndDate,
			final SectionTimetable sectionTimetable) throws BusinessException {
		if (scheduleStartDate == null || scheduleEndDate == null) {
			throw new BusinessException(BusinessMessages.getResourceBundleName(),
					BusinessMessages.MSG_START_DATE_END_DATE_CANNOT_BE_EMPTY,null);
		} else if (scheduleStartDate.compareTo(scheduleEndDate) <= 0) {
			if (sectionTimetable.getStartTime() == null || sectionTimetable.getEndTime() == null) {
				throw new BusinessException(BusinessMessages.getResourceBundleName(),
						BusinessMessages.MSG_START_TIME_END_TIME_CANNOT_BE_EMPTY,null);
			} else if (!(sectionTimetable.getStartTime().compareTo(sectionTimetable.getEndTime()) < 0)) {
				throw new BusinessException(BusinessMessages.getResourceBundleName(),
						BusinessMessages.MSG_SCHEDULE_END_TIME_CANNOT_BE_BEFORE_START_TIME,null);
			} else {
				this.validateScheduleForOverLapping(scheduleStartDate, scheduleEndDate, sectionTimetable);
			}
		} else {
			throw new BusinessException(BusinessMessages.getResourceBundleName(),
					BusinessMessages.MSG_SCHEDULE_END_DATE_CANNOT_BE_BEFORE_START_DATE,null);
		}
	}

	/**
	 * Validates schedule for overlapping.
	 * @param scheduleStartDate schedule start date.
	 * @param scheduleEndDate schedule end date.
	 * @param sectionTimetable section time table.
	 * @throws BusinessException
	 */
	private void validateScheduleForOverLapping(final Date scheduleStartDate, final Date scheduleEndDate,
			final SectionTimetable sectionTimetable) throws BusinessException {
		Collection<SectionTimetable> overLappingTimeTablesForSection = this.getOverLappingTimeTableForSection(
				sectionTimetable.getSectionSubject().getSection().getId(), scheduleStartDate, scheduleEndDate,
				sectionTimetable.getStartTime(), sectionTimetable.getEndTime());
		//If there is no overlapping with section time table then check with section teacher if there is
		//schedule overlapping for teacher.
		if (overLappingTimeTablesForSection.isEmpty()) {
			Collection<SectionTimetable> overLappingTimeTablesForTeacher = this.getOverLappingTimeTableForTeacher(
					sectionTimetable.getSectionTeacher().getEmployee().getId(), scheduleStartDate, scheduleEndDate,
					sectionTimetable.getStartTime(), sectionTimetable.getEndTime());
			if (!overLappingTimeTablesForTeacher.isEmpty()) {
				//If the schedule is new, then throw error message with overlapping schedule.
				if (sectionTimetable.getId() == null) {
					SectionTimetable overLappingTimeTableForTeacher = overLappingTimeTablesForTeacher.iterator().next();
					throw new BusinessException(BusinessMessages.getResourceBundleName(),
							BusinessMessages.MSG_OVERLAPPING_SCHEDULE_FOR_TEACHER,
							new Object[] { new SimpleDateFormat("dd/MM/yyyy").format(overLappingTimeTableForTeacher
									.getScheduleDate()), new SimpleDateFormat("HH:mm").format(overLappingTimeTableForTeacher.getStartTime()),new SimpleDateFormat("HH:mm").format(overLappingTimeTableForTeacher.getEndTime()),sectionTimetable.getSectionTeacher().getEmployee().getDisplayName()});
				} else {
					//If schedule is old then do not compare with supplied schedule itself.
					for (SectionTimetable overLappingTimeTableForTeacher : overLappingTimeTablesForTeacher) {
						if (!sectionTimetable.getId().equals(overLappingTimeTableForTeacher.getId())) {
							throw new BusinessException(BusinessMessages.getResourceBundleName(),
									BusinessMessages.MSG_OVERLAPPING_SCHEDULE_FOR_TEACHER,
									new Object[] { new SimpleDateFormat("dd/MM/yyyy").format(overLappingTimeTableForTeacher
											.getScheduleDate()), new SimpleDateFormat("HH:mm").format(overLappingTimeTableForTeacher.getStartTime()),new SimpleDateFormat("HH:mm").format(overLappingTimeTableForTeacher.getEndTime()),sectionTimetable.getSectionTeacher().getEmployee().getDisplayName()});
						}
					}
				}
			}
		} else {
			//If the schedule is new, then throw error message with overlapping schedule.
			if (sectionTimetable.getId() == null) {
				SectionTimetable overLappingTimeTableForSection = overLappingTimeTablesForSection.iterator().next();
				throw new BusinessException(BusinessMessages.getResourceBundleName(),
						BusinessMessages.MSG_OVERLAPPING_SCHEDULE_FOR_TEACHER,
						new Object[] { new SimpleDateFormat("dd/MM/yyyy").format(overLappingTimeTableForSection
								.getScheduleDate()), new SimpleDateFormat("HH:mm").format(overLappingTimeTableForSection.getStartTime()),new SimpleDateFormat("HH:mm").format(overLappingTimeTableForSection.getEndTime())});
			} else {
				//If schedule is old then do not compare with supplied schedule itself.
				for (SectionTimetable overLappingTimeTableForSection : overLappingTimeTablesForSection) {
					if (!sectionTimetable.getId().equals(overLappingTimeTableForSection.getId())) {
						throw new BusinessException(BusinessMessages.getResourceBundleName(),
								BusinessMessages.MSG_OVERLAPPING_SCHEDULE_FOR_TEACHER,
								new Object[] { new SimpleDateFormat("dd/MM/yyyy").format(overLappingTimeTableForSection
										.getScheduleDate()), new SimpleDateFormat("HH:mm").format(overLappingTimeTableForSection.getStartTime()),new SimpleDateFormat("HH:mm").format(overLappingTimeTableForSection.getEndTime())});
					}
				}
			}

		}

	}
}
