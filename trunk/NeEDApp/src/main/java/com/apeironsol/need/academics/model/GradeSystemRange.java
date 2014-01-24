/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.academics.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for Employee
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "GRADE_SYSTEM_RANGE")
public class GradeSystemRange extends BaseEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -845617126694221633L;

	/**
	 * Universal serial version id for this class.
	 */

	@ManyToOne
	@JoinColumn(name = "GRADE_SYSTEM_ID", nullable = false)
	private GradeSystem			gradeSystem;

	@Column(name = "MINIMUM_RANGE", nullable = false)
	private Integer				minimumRange;

	@Column(name = "MAXIMUM_RANGE", nullable = false)
	private Integer				maximumRange;

	@Column(name = "DISTINCTION")
	private String				distinction;

	/**
	 * @return the gradingSystem
	 */
	public GradeSystem getGradeSystem() {
		return this.gradeSystem;
	}

	/**
	 * @param gradingSystem
	 *            the gradingSystem to set
	 */
	public void setGradeSystem(final GradeSystem gradeSystem) {
		this.gradeSystem = gradeSystem;
	}

	/**
	 * @return the minimumRange
	 */
	public Integer getMinimumRange() {
		return this.minimumRange;
	}

	/**
	 * @param minimumRange
	 *            the minimumRange to set
	 */
	public void setMinimumRange(final Integer minimumRange) {
		this.minimumRange = minimumRange;
	}

	/**
	 * @return the maximumRange
	 */
	public Integer getMaximumRange() {
		return this.maximumRange;
	}

	/**
	 * @param maximumRange
	 *            the maximumRange to set
	 */
	public void setMaximumRange(final Integer maximumRange) {
		this.maximumRange = maximumRange;
	}

	/**
	 * @return the distinction
	 */
	public String getDistinction() {
		return this.distinction;
	}

	/**
	 * @param distinction
	 *            the distinction to set
	 */
	public void setDistinction(final String distinction) {
		this.distinction = distinction;
	}

}
