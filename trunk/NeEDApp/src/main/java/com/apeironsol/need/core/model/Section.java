/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for SECTION.
 * 
 * @author Pradeep
 */

@Entity
@Table(name = "SECTION", uniqueConstraints = { @UniqueConstraint(columnNames = { "NAME", "CLASS_ID", "ACADEMIC_YEAR_ID" }, name = "UQ_SECTION") })
public class Section extends BaseEntity implements Serializable {

	/**
	 * Universal serial id for course class
	 */
	private static final long	serialVersionUID	= -2611016229811058856L;

	@NotEmpty(message = "Session name is mandatory.")
	@Column(name = "NAME", length = 50, nullable = false)
	private String				name;

	@NotNull(message = "model.klass_mandatory")
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "CLASS_ID", nullable = false)
	private Klass				klass;

	@NotNull(message = "model.academic_year_mandatory")
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "ACADEMIC_YEAR_ID", nullable = false)
	private AcademicYear		academicYear;

	@NotNull(message = "model.active_flag_mandatory")
	@Column(name = "ACTIVE", nullable = false)
	private boolean				active;

	@Column(name = "OPEN_FOR_ADMISSION_IND")
	private boolean				openForAdmission;

	public Klass getKlass() {
		return this.klass;
	}

	public void setKlass(final Klass klass) {
		this.klass = klass;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public AcademicYear getAcademicYear() {
		return this.academicYear;
	}

	public void setAcademicYear(final AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(final boolean active) {
		this.active = active;
	}

	public String getDispalyNameWithKlassName() {
		return this.klass.getName() + " (" + this.name + ")";
	}

	/**
	 * @return the openForAdmission
	 */
	public boolean isOpenForAdmission() {
		return this.openForAdmission;
	}

	/**
	 * @param openForAdmission
	 *            the openForAdmission to set
	 */
	public void setOpenForAdmission(final boolean openForAdmission) {
		this.openForAdmission = openForAdmission;
	}
}
