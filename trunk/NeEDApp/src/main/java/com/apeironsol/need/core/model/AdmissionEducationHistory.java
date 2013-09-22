/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

/**
 * class for education history
 * 
 * @author Pradeep
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.apeironsol.need.util.constants.MediumConstant;
import com.apeironsol.need.util.constants.SchoolTypeConstant;
import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "ADMISSION_EDUCATION_HISTORY")
public class AdmissionEducationHistory extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -2497534692851225093L;

	@NotEmpty(message = "model.school_name_mandatory")
	@Column(name = "SCHOOL_NAME", length = 20, nullable = false)
	@Enumerated(EnumType.STRING)
	private String				schoolName;

	@NotNull(message = "model.school_type_mandatory")
	@Column(name = "SCHOOL_TYPE", length = 20, nullable = false)
	@Enumerated(EnumType.STRING)
	private SchoolTypeConstant	schoolType;

	@NotNull(message = "model.medium_mandatory")
	@Column(name = "MEDIAM", length = 20, nullable = false)
	@Enumerated(EnumType.STRING)
	private MediumConstant		mediam;

	@NotEmpty(message = "model.highest_calss_promoted_is_mandatory")
	@Column(name = "HIGHEST_CLASS", length = 20, nullable = false)
	private String				highestKlass;

	@NotNull(message = "model.data_of_joining_mandatory")
	@Column(name = "DATE_OF_JOINING", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date				dateOfjoining;

	@NotNull(message = "model.data_of_relieving_mandatory")
	@Column(name = "DATE_OF_RELIVING", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date				dateOfRelieving;

	@Column(name = "CON_PERSON_NAME", length = 40)
	private String				contactPersonName;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "ADDRESS_ID", nullable = false)
	private Address				address;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADMISSION_ID", nullable = false)
	private Admission			admission;

	public String getSchoolName() {
		return this.schoolName;
	}

	public void setSchoolName(final String schoolName) {
		this.schoolName = schoolName;
	}

	public SchoolTypeConstant getSchoolType() {
		return this.schoolType;
	}

	public void setSchoolType(final SchoolTypeConstant schoolType) {
		this.schoolType = schoolType;
	}

	public MediumConstant getMediam() {
		return this.mediam;
	}

	public void setMediam(final MediumConstant mediam) {
		this.mediam = mediam;
	}

	public Date getDateOfjoining() {
		return this.dateOfjoining;
	}

	public void setDateOfjoining(final Date dateOfjoining) {
		this.dateOfjoining = dateOfjoining;
	}

	public Date getDateOfRelieving() {
		return this.dateOfRelieving;
	}

	public void setDateOfRelieving(final Date dateOfRelieving) {
		this.dateOfRelieving = dateOfRelieving;
	}

	public String getContactPersonName() {
		return this.contactPersonName;
	}

	public void setContactPersonName(final String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(final Address address) {
		this.address = address;
	}

	public String getHighestKlass() {
		return this.highestKlass;
	}

	public void setHighestKlass(final String highestKlass) {
		this.highestKlass = highestKlass;
	}

	public Admission getAdmission() {
		return this.admission;
	}

	public void setAdmission(final Admission admission) {
		this.admission = admission;
	}

}
