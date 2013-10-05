/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.util.searchcriteria;

import java.util.Date;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Section;

/**
 * Class for fees collected search criteria..
 * 
 * @author Pradeep
 */
public class FeeCollectedSearchCriteria implements SearchCriteria {

	/**
	 * Universal serial version id for this class
	 */
	private static final long	serialVersionUID	= 5176156681559639755L;

	/**
	 * Branch.
	 */
	private Branch				branch;

	/**
	 * Student date of birth.
	 */
	private Date				fromDate;

	/**
	 * Student date of birth.
	 */
	private Date				toDate;

	/**
	 * Klass.
	 */
	private Klass				klass;

	/**
	 * Section.
	 */
	private Section				section;

	/**
	 * academicYear.
	 */
	private AcademicYear		academicYear;

	/**
	 * 
	 * @param branch
	 */
	public FeeCollectedSearchCriteria(final Branch branch) {
		this.setBranch(branch);
	}

	/**
	 * 
	 * @param branch
	 */
	public FeeCollectedSearchCriteria() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetSeacrhCriteria() {
		this.fromDate = null;
		this.toDate = null;
		this.klass = null;
		this.section = null;
		this.academicYear = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSearchCriteriaIsEmpty() {
		return this.toDate == null && this.fromDate == null && this.klass == null && this.section == null;
	}

	/**
	 * @return the branch
	 */
	@Override
	public Branch getBranch() {
		return this.branch;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return this.fromDate;
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(final Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return this.toDate;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(final Date toDate) {
		this.toDate = toDate;
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
	 * @return the klass
	 */
	public Klass getKlass() {
		return this.klass;
	}

	/**
	 * @param klass
	 *            the klass to set
	 */
	public void setKlass(final Klass klass) {
		this.klass = klass;
	}

	/**
	 * @return the section
	 */
	public Section getSection() {
		return this.section;
	}

	/**
	 * @param section
	 *            the section to set
	 */
	public void setSection(final Section section) {
		this.section = section;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	@Override
	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

}
