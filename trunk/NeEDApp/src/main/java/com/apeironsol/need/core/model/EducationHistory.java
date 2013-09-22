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

import org.hibernate.validator.constraints.NotEmpty;

import com.apeironsol.need.util.constants.MediumConstant;
import com.apeironsol.need.util.constants.SchoolTypeConstant;
import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "EDUCATION_HISTORY")
public class EducationHistory extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -2497534692851225093L;

	@NotEmpty(message = "model.school_name_mandatory")
	@Column(name = "SCHOOL_NAME", length = 255, nullable = false)
	@Enumerated(EnumType.STRING)
	private String				schoolName;

	@Column(name = "SCHOOL_TYPE", length = 20)
	@Enumerated(EnumType.STRING)
	private SchoolTypeConstant	schoolType;

	@Column(name = "MEDIUM", length = 20)
	@Enumerated(EnumType.STRING)
	private MediumConstant		medium;

	@Column(name = "HIGHEST_CLASS", length = 20)
	private String				highestKlass;

	@Column(name = "DATE_OF_JOINING")
	@Temporal(TemporalType.DATE)
	private Date				dateOfjoining;

	@Column(name = "DATE_OF_RELIVING")
	@Temporal(TemporalType.DATE)
	private Date				dateOfRelieving;

	@Column(name = "CON_PERSON_NAME", length = 40)
	private String				contactPersonName;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "ADDRESS_ID")
	private Address				address;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STUDENT_ID", nullable = false)
	private Student				student;

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

	public MediumConstant getMedium() {
		return this.medium;
	}

	public void setMedium(final MediumConstant medium) {
		this.medium = medium;
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

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(final Student student) {
		this.student = student;
	}

}
