/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.portal;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.service.AcademicYearService;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.ApplicationException;

/**
 * JSF managed for calendar year.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class AcademicYearBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long	serialVersionUID	= -6269656898436874439L;

	@Resource
	private AcademicYearService	academicYearService;

	@Resource
	private SessionBean			sessionBean;

	private AcademicYear		academicYear;

	private boolean				displayNewacademicYear;

	public void saveAcademicYear() {
		Branch currentBranch = this.sessionBean.getCurrentBranch();
		this.academicYear.setBranch(currentBranch);
		try {
			this.academicYearService.saveAcademicYear(this.academicYear);
			this.displayNewacademicYear = false;
			this.loadAcademicYearDetails();
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void removeAcademicYear() {
		try {
			this.academicYearService.removeAcademicYear(this.academicYear);
			this.loadAcademicYearDetails();
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void resetAcademicYear() {
		this.academicYear = new AcademicYear();
	}

	public boolean isDisplayNewacademicYear() {
		return this.displayNewacademicYear;
	}

	public void setDisplayNewacademicYear(final boolean displayNewacademicYear) {
		this.displayNewacademicYear = displayNewacademicYear;
	}

	public void activateAcademicYear() {
		try {
			this.academicYear = this.academicYearService.activateAcademicYear(this.academicYear);
			this.loadAcademicYearDetails();
			ViewUtil.addMessage("Academic year is activated sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (Throwable e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void deactivateAcademicYear() {
		try {
			this.academicYear = this.academicYearService.deactivateAcademicYear(this.academicYear);
			this.loadAcademicYearDetails();
			ViewUtil.addMessage("Academic year is set to development sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
			this.academicYear = this.academicYearService.findAcademicYearById(this.academicYear.getId());

		} catch (Throwable e) {
			ViewExceptionHandler.handle(e);
			this.academicYear = this.academicYearService.findAcademicYearById(this.academicYear.getId());

		}
	}

	@Override
	public void onTabChange() {

	}

	public boolean isDisableSubTab() {
		if (this.academicYear != null && this.academicYear.getId() != null) {
			return false;
		}
		return true;
	}

	/**
	 * @return the academicYear
	 */
	public AcademicYear getAcademicYear() {
		return this.academicYear;
	}

	/**
	 * @param academicYear
	 *            the academicYear to set
	 */
	public void setAcademicYear(final AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	/**
	 * loadAcademicYearDetails.
	 */
	private void loadAcademicYearDetails() {
		this.setLoadAllAcademicYearsFlag(true);
		this.setLoadActiveAcademicYearsFlag(true);
		this.loadActiveAcademicYearsForCurrentBranch();
		this.loadAllAcademicYearsForCurrentBranch();
		this.setLoadAllAcademicYearsFlag(false);
		this.setLoadActiveAcademicYearsFlag(false);

	}
}
