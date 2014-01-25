/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.academics.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.apeironsol.framework.BaseEntity;
import com.apeironsol.need.core.model.Branch;

/**
 * Entity class for Employee
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "GRADE_SYSTEM")
public class GradeSystem extends BaseEntity {

	/**
	 * 
	 */
	private static final long				serialVersionUID	= -6563178495113132888L;

	/**
	 * Universal serial version id for this class.
	 */

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch							branch;

	@Column(name = "NAME", length = 50, nullable = false)
	private String							name;

	@Column(name = "DEFAULT_GRADE")
	private boolean							defaultGrade;

	@OneToMany(mappedBy = "gradeSystem", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private Collection<GradeSystemRange>	gradeSystemRange;

	/**
	 * @return the branch
	 */
	public Branch getBranch() {
		return this.branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the gradeSystemRange
	 */
	public Collection<GradeSystemRange> getGradeSystemRange() {
		return this.gradeSystemRange;
	}

	/**
	 * @param gradeSystemRange
	 *            the gradeSystemRange to set
	 */
	public void setGradeSystemRange(final Collection<GradeSystemRange> gradeSystemRange) {
		this.gradeSystemRange = gradeSystemRange;
	}

	/**
	 * @return the defaultGrade
	 */
	public boolean isDefaultGrade() {
		return this.defaultGrade;
	}

	/**
	 * @param defaultGrade
	 *            the defaultGrade to set
	 */
	public void setDefaultGrade(final boolean defaultGrade) {
		this.defaultGrade = defaultGrade;
	}

}
