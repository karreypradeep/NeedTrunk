/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.AcademicYearWeekEndDao;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.AcademicYearWeekEnd;
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
@Service("academicYearWeekEndService")
@Transactional(rollbackFor = Exception.class)
public class AcademicYearWeekEndServiceImpl implements AcademicYearWeekEndService {

	@Resource
	private AcademicYearWeekEndDao	academicYearWeekEndDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AcademicYearWeekEnd> findAcademicYearWeekEndsByAcademicYearId(final Long academicYearId)
			throws BusinessException {
		return this.academicYearWeekEndDao.findAcademicYearWeekEndsByAcademicYearId(academicYearId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AcademicYearWeekEnd findAcademicYearWeekEndById(final Long id) throws BusinessException {
		return this.academicYearWeekEndDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AcademicYearWeekEnd saveAcademicYearWeekEnd(final AcademicYearWeekEnd academicYearWeekEnd)
			throws BusinessException, InvalidArgumentException {
		return this.academicYearWeekEndDao.persist(academicYearWeekEnd);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAcademicYearWeekEnd(final AcademicYearWeekEnd academicYearWeekEnd) throws BusinessException {
		this.academicYearWeekEndDao.remove(academicYearWeekEnd);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AcademicYearWeekEnd> findAllAcademicYearWeekEnds() throws BusinessException {
		return this.academicYearWeekEndDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AcademicYearWeekEnd> createAcademicYearWeekEnds(final Date startDate, final Date endDate,
			final AcademicYear academicYear, final List<WeekDayConstant> weekDayConstants) throws BusinessException {
		Collection<AcademicYearWeekEnd> tempSaveAcademicYearWeekEnds = new ArrayList<AcademicYearWeekEnd>();
		// 1 is added to include end date
		int noOfDays = DateUtil.dateDiffInDays(startDate, endDate) + 1;
		Calendar scheduleStartDate = Calendar.getInstance();
		AcademicYearWeekEnd newEntity;
		int eventCount = 0;
		do {
			scheduleStartDate.setTime(startDate);
			DateUtil.clearTimeInfo(scheduleStartDate);
			scheduleStartDate.add(Calendar.DATE, eventCount);
			if (this.findAcademicYearWeekEndByAcademicYearIdAndWeekEndDate(academicYear.getId(),
					scheduleStartDate.getTime()) == null
					&& weekDayConstants != null
					&& weekDayConstants
							.contains(WeekDayConstant.getWeekDay(scheduleStartDate.get(Calendar.DAY_OF_WEEK)))) {
				newEntity = new AcademicYearWeekEnd();
				newEntity.setAcademicYear(academicYear);
				newEntity.setWeekEndDate(scheduleStartDate.getTime());
				tempSaveAcademicYearWeekEnds.add(this.saveAcademicYearWeekEnd(newEntity));
			}
			eventCount++;
		} while (eventCount < noOfDays);
		return tempSaveAcademicYearWeekEnds;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AcademicYearWeekEnd> findAcademicYearWeekEndsByAcademicYearIdBetweenDates(
			final Long academicYearId, final Date startDate, final Date endDate) throws BusinessException {
		return this.academicYearWeekEndDao.findAcademicYearWeekEndsByAcademisYearIdBetweenDates(academicYearId,
				startDate, endDate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AcademicYearWeekEnd findAcademicYearWeekEndByAcademicYearIdAndWeekEndDate(final Long academicYearId,
			final Date weekEndDate) throws BusinessException {
		return this.academicYearWeekEndDao.findAcademicYearWeekEndByAcademicYearIdAndWeekEndDate(academicYearId,
				weekEndDate);
	}

}
