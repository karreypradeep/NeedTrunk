/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.portal;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.AcademicYearHoliday;
import com.apeironsol.need.core.service.AcademicYearHolidayService;

/**
 * JSF managed for academic year holiday.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class AcademicYearHolidayBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long			serialVersionUID	= 1964206129200014640L;

	@Resource
	private AcademicYearBean			academicYearBean;

	private AcademicYearHoliday			academicYearHoliday;

	@Resource
	private AcademicYearHolidayService	academicYearHolidayService;

	private boolean						loadAcademinYearHolidaysFromDB;

	/**
	 * @return the loadAcademinYearHolidaysFromDB
	 */
	public boolean isLoadAcademinYearHolidaysFromDB() {
		return this.loadAcademinYearHolidaysFromDB;
	}

	/**
	 * @param loadAcademinYearHolidaysFromDB
	 *            the loadAcademinYearHolidaysFromDB to set
	 */
	public void setLoadAcademinYearHolidaysFromDB(final boolean loadAcademinYearHolidaysFromDB) {
		this.loadAcademinYearHolidaysFromDB = loadAcademinYearHolidaysFromDB;
	}

	private Collection<AcademicYearHoliday>	academicYearHolidays;

	@PostConstruct
	public void init() {

	}

	@Override
	public void onTabChange() {

	}

	/**
	 * Load academic year holidays from database only if
	 * loadAcademinYearHolidaysFromDB is true.
	 */
	public void loadAcademicYearHolidays() {
		if (this.loadAcademinYearHolidaysFromDB) {
			this.academicYearHolidays = this.academicYearHolidayService
					.findAcademicYearHolidaysByAcademicYearId(this.academicYearBean.getAcademicYear() == null ? this.sessionBean.getCurrentAcademicYear()
							.getId() : this.academicYearBean.getAcademicYear().getId());
		}
	}

	/**
	 * @return the academicYearHolidays
	 */
	public Collection<AcademicYearHoliday> getAcademicYearHolidays() {
		return this.academicYearHolidays;
	}

	/**
	 * @param academicYearHolidays
	 *            the academicYearHolidays to set
	 */
	public void setAcademicYearHolidays(final Collection<AcademicYearHoliday> academicYearHolidays) {
		this.academicYearHolidays = academicYearHolidays;
	}

	/**
	 * @return the academicYearHoliday
	 */
	public AcademicYearHoliday getAcademicYearHoliday() {
		return this.academicYearHoliday;
	}

	/**
	 * @param academicYearHoliday
	 *            the academicYearHoliday to set
	 */
	public void setAcademicYearHoliday(final AcademicYearHoliday academicYearHoliday) {
		this.academicYearHoliday = academicYearHoliday;
	}

	/**
	 * Create new academic year holiday.
	 */
	public String newAcademicYearHoliday() {
		this.academicYearHoliday = new AcademicYearHoliday();
		this.academicYearHoliday.setAcademicYear(this.academicYearBean.getAcademicYear());
		this.setViewOrNewAction(true);
		return null;
	}

	/**
	 * Saves academic year holiday to database.
	 */
	public String saveAcademicYearHoliday() {
		this.academicYearHoliday = this.academicYearHolidayService.saveAcademicYearHoliday(this.academicYearHoliday);
		this.setViewOrNewAction(false);
		this.loadAcademinYearHolidaysFromDB = true;
		return null;
	}

	/**
	 * Removes academic year holiday from database.
	 */
	public String removeAcademicYearHoliday() {
		this.academicYearHolidayService.removeAcademicYearHoliday(this.academicYearHoliday);
		this.setViewOrNewAction(false);
		this.loadAcademinYearHolidaysFromDB = true;
		return null;
	}

	/**
	 * Removes academic year holiday from database.
	 */
	public String calcelAction() {
		this.setViewOrNewAction(false);
		return null;
	}

	/**
	 * Removes academic year holiday from database.
	 */
	public String viewAcademicYearHoliday() {
		this.setViewOrNewAction(true);
		return null;
	}

}
