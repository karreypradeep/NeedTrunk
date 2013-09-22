/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.util.searchcriteria;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.Klass;

/**
 * Singleton class for branch expense search criteria..
 * 
 * @author Pradeep
 */
public class SectionSearchCriteria implements SearchCriteria {

	/**
	 * Universal serial version id for this class
	 */
	private static final long	serialVersionUID	= 6070822367619472726L;

	/**
	 * Name of student.
	 */
	private Klass				klass;

	/**
	 * academicYear.
	 */
	private AcademicYear		academicYear;

	/**
	 * Branch.
	 */
	private Branch				branch;

	/**
	 * 
	 * @param branch
	 */
	public SectionSearchCriteria(final Branch branch) {
		this.setBranch(branch);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetSeacrhCriteria() {
		this.setKlass(null);
		this.academicYear = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSearchCriteriaIsEmpty() {
		return this.getKlass() == null && this.academicYear == null;
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
	 * @return the branch
	 */
	@Override
	public Branch getBranch() {
		return this.branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	@Override
	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	/**
	 * @return the klasses
	 */
	public Klass getKlass() {
		return this.klass;
	}

	/**
	 * @param klasses
	 *            the klasses to set
	 */
	public void setKlass(final Klass klass) {
		this.klass = klass;
	}

}
