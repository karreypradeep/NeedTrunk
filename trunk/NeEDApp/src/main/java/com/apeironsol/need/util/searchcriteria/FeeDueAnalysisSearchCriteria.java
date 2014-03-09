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
import com.apeironsol.need.util.constants.FeeDueAnalysisTypeConstant;

/**
 * Singleton class for Admission search criteria..
 * 
 * @author Pradeep
 */
public class FeeDueAnalysisSearchCriteria implements SearchCriteria {

	/**
	 * Universal serial version id for this class
	 */
	private static final long			serialVersionUID	= -813730913845160632L;

	/**
	 * academicYear.
	 */
	private AcademicYear				academicYear;

	/**
	 * Student status.
	 */
	private FeeDueAnalysisTypeConstant	feeDueAnalysisType;

	/**
	 * Branch.
	 */
	private Branch						branch;

	/**
	 * 
	 * @param branch
	 */
	public FeeDueAnalysisSearchCriteria(final Branch branch) {
		this.setBranch(branch);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetSeacrhCriteria() {
		this.academicYear = null;
		this.feeDueAnalysisType = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSearchCriteriaIsEmpty() {
		return ((this.academicYear == null) && (this.feeDueAnalysisType == null));
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
	 * @return the feeDueAnalysisType
	 */
	public FeeDueAnalysisTypeConstant getFeeDueAnalysisType() {
		return this.feeDueAnalysisType;
	}

	/**
	 * @param feeDueAnalysisType
	 *            the feeDueAnalysisType to set
	 */
	public void setFeeDueAnalysisType(final FeeDueAnalysisTypeConstant feeDueAnalysisType) {
		this.feeDueAnalysisType = feeDueAnalysisType;
	}

}
