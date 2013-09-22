/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.AcademicYearHolidayDao;
import com.apeironsol.need.core.model.AcademicYearHoliday;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Service("academicYearHolidayService")
@Transactional
public class AcademicYearHolidayServiceImpl implements AcademicYearHolidayService {

	@Resource
	private AcademicYearHolidayDao	academicYearHolidayDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AcademicYearHoliday> findAcademicYearHolidaysByAcademicYearId(final Long academicYearId)
			throws BusinessException {
		return this.academicYearHolidayDao.findAcademicYearHolidaysByAcademicYearId(academicYearId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AcademicYearHoliday findAcademicYearHolidayById(final Long id) throws BusinessException {
		return this.academicYearHolidayDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AcademicYearHoliday saveAcademicYearHoliday(final AcademicYearHoliday academicYearHoliday)
			throws BusinessException, InvalidArgumentException {
		return this.academicYearHolidayDao.persist(academicYearHoliday);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAcademicYearHoliday(final AcademicYearHoliday academicYearHoliday) throws BusinessException {
		this.academicYearHolidayDao.remove(academicYearHoliday);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AcademicYearHoliday> findAllAcademicYearHolidays() throws BusinessException {
		return this.academicYearHolidayDao.findAll();
	}

}
