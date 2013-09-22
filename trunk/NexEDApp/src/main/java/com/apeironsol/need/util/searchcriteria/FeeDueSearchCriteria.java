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
 * Class for fees Due search criteria..
 * 
 * @author Pradeep
 */
public class FeeDueSearchCriteria implements SearchCriteria {

	/**
	 * Universal serial version id for this class
	 */
	private static final long	serialVersionUID	= 5176156681559639755L;

	/**
	 * Branch.
	 */
	private Branch				branch;

	/**
	 * @param branch
	 *            the branch to set
	 */
	@Override
	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	/**
	 * Student date of birth.
	 */
	private Date			dueDate;

	/**
	 * academicYear.
	 */
	private AcademicYear	academicYear;

	/**
	 * Minimum due amount.
	 */
	private double			minimumDueAmount;

	/**
	 * Minimum due amount.
	 */
	private double			feeDuePercetage;

	/**
	 * Klass.
	 */
	private Klass			klass;

	/**
	 * Section.
	 */
	private Section			section;

	/**
	 * 
	 * @param branch
	 */
	public FeeDueSearchCriteria(final Branch branch) {
		this.branch = branch;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetSeacrhCriteria() {
		this.dueDate = null;
		this.setKlass(null);
		this.academicYear = null;
		this.minimumDueAmount = 0.0;
		this.setSection(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSearchCriteriaIsEmpty() {
		return this.dueDate == null && this.getKlass() == null && this.getSection() == null;
	}

	/**
	 * @return the branch
	 */
	@Override
	public Branch getBranch() {
		return this.branch;
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
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return this.dueDate;
	}

	/**
	 * @param dueDate
	 *            the dueDate to set
	 */
	public void setDueDate(final Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @return the minimumDueAmount
	 */
	public double getMinimumDueAmount() {
		return this.minimumDueAmount;
	}

	/**
	 * @param minimumDueAmount
	 *            the minimumDueAmount to set
	 */
	public void setMinimumDueAmount(final double minimumDueAmount) {
		this.minimumDueAmount = minimumDueAmount;
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

	public double getFeeDuePercetage() {
		return this.feeDuePercetage;
	}

	public void setFeeDuePercetage(final double feeDuePercetage) {
		this.feeDuePercetage = feeDuePercetage;
	}

}
