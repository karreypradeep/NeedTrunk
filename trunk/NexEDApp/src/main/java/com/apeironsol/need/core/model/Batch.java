package com.apeironsol.need.core.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.apeironsol.framework.BaseEntity;

/**
 * 
 * @author pradeep
 * 
 */
@Entity
@Table(name = "BATCH")
public class Batch extends BaseEntity {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -2099472187022483551L;

	@NotEmpty(message = "model.name_is_mandatory")
	@Basic(optional = false)
	@Column(name = "NAME", nullable = false)
	private String				name;

	@NotNull(message = "model.start_academic_year_mandatory")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "START_ACADEMIC_YEAR_ID", nullable = false)
	private AcademicYear		startAcademicYear;

	@NotNull(message = "model.end_academic_year_mandatory")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "END_ACADEMIC_YEAR_ID", nullable = false)
	private AcademicYear		endAcademicYear;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch				branch;

	@NotNull(message = "model.locked_mandatory")
	@Column(name = "LOCKED")
	private boolean				locked;

	/**
	 * @return the locked
	 */
	public boolean isLocked() {
		return this.locked;
	}

	/**
	 * @param locked
	 *            the locked to set
	 */
	public void setLocked(final boolean locked) {
		this.locked = locked;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Branch getBranch() {
		return this.branch;
	}

	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	/**
	 * @return the startAcademicYear
	 */
	public AcademicYear getStartAcademicYear() {
		return this.startAcademicYear;
	}

	/**
	 * @param startAcademicYear
	 *            the startAcademicYear to set
	 */
	public void setStartAcademicYear(final AcademicYear startAcademicYear) {
		this.startAcademicYear = startAcademicYear;
	}

	/**
	 * Return Academic year.
	 * 
	 * @return the endAcademicYear
	 */
	public AcademicYear getEndAcademicYear() {
		return this.endAcademicYear;
	}

	/**
	 * @param endAcademicYear
	 *            the endAcademicYear to set
	 */
	public void setEndAcademicYear(final AcademicYear endAcademicYear) {
		this.endAcademicYear = endAcademicYear;
	}
}
