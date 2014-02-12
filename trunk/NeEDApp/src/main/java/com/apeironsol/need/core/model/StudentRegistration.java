/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

/**
 * class for user
 * 
 * @author Pradeep
 */
import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import com.apeironsol.framework.BaseEntity;
import com.apeironsol.need.util.constants.RegistrationStatusConstant;

@Entity
@Table(name = "STUDENT_REGISTRATION")
public class StudentRegistration extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class
	 */
	private static final long			serialVersionUID	= 250845254808368057L;

	@NotEmpty(message = "Student First name is mandatory.")
	@Column(name = "STUDENT_FIRST_NAME", nullable = false)
	private String						studentFirstName;

	@NotEmpty(message = "Student Last name is mandatory.")
	@Column(name = "STUDENT_LAST_NAME", nullable = false)
	private String						studentLastName;

	@Column(name = "STUDENT_MIDDLE_NAME")
	private String						studentMiddleName;

	@NotEmpty(message = "Father First name is mandatory.")
	@Column(name = "FATHER_FIRST_NAME", nullable = false)
	private String						fatherFirstName;

	@NotEmpty(message = "Father Last name is mandatory.")
	@Column(name = "FATHER_LAST_NAME", nullable = false)
	private String						fatherLastName;

	@Column(name = "FATHER_MIDDLE_NAME")
	private String						fatherMiddleName;

	@Column(name = "FATHER_OCCUPATION")
	private String						fatherOccupation;

	@Column(name = "ADDRESS")
	private String						address;

	@Column(name = "PRIMARY_CONTACT", nullable = false)
	private String						primaryContactNumber;

	@Column(name = "SECONDARY_CONTACT")
	private String						secondaryContactNumber;

	@Column(name = "SCHOOL_NAME")
	private String						schoolName;

	@Column(name = "AREA")
	private String						area;

	@Column(name = "LOCATION")
	private String						location;

	@Column(name = "MPC_GROUP_IND")
	private boolean						interestedInMPCGroup;

	@Column(name = "MEC_GROUP_IND")
	private boolean						interestedInMECGroup;

	@Column(name = "BIPC_GROUP_IND")
	private boolean						interestedInBiPCGroup;

	@Column(name = "CEC_GROUP_IND")
	private boolean						interestedInCECGroup;

	@Basic
	@Column(name = "STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
	private RegistrationStatusConstant	registrationStatus;

	@Column(name = "COLLEGE_JOINED_NAME")
	private String						collegeJoinedName;

	@Column(name = "REASON")
	private String						reason;

	public String getStudentDisplayName() {
		if (StringUtils.isEmpty(this.studentFirstName) && StringUtils.isEmpty(this.studentLastName)) {
			return "";
		} else if (StringUtils.isEmpty(this.studentMiddleName)) {
			return this.studentFirstName + " " + this.studentLastName;
		} else {
			return this.studentFirstName + " " + this.studentMiddleName + " " + this.studentLastName;

		}
	}

	public String getFatherDisplayName() {
		if (StringUtils.isEmpty(this.fatherFirstName) && StringUtils.isEmpty(this.fatherLastName)) {
			return "";
		} else if (StringUtils.isEmpty(this.fatherMiddleName)) {
			return this.fatherFirstName + " " + this.fatherLastName;
		} else {
			return this.fatherFirstName + " " + this.fatherMiddleName + " " + this.fatherLastName;

		}
	}

	/**
	 * @return the studentFirstName
	 */
	public String getStudentFirstName() {
		return this.studentFirstName;
	}

	/**
	 * @param studentFirstName
	 *            the studentFirstName to set
	 */
	public void setStudentFirstName(final String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}

	/**
	 * @return the studentLastName
	 */
	public String getStudentLastName() {
		return this.studentLastName;
	}

	/**
	 * @param studentLastName
	 *            the studentLastName to set
	 */
	public void setStudentLastName(final String studentLastName) {
		this.studentLastName = studentLastName;
	}

	/**
	 * @return the studentMiddleName
	 */
	public String getStudentMiddleName() {
		return this.studentMiddleName;
	}

	/**
	 * @param studentMiddleName
	 *            the studentMiddleName to set
	 */
	public void setStudentMiddleName(final String studentMiddleName) {
		this.studentMiddleName = studentMiddleName;
	}

	/**
	 * @return the fatherFirstName
	 */
	public String getFatherFirstName() {
		return this.fatherFirstName;
	}

	/**
	 * @param fatherFirstName
	 *            the fatherFirstName to set
	 */
	public void setFatherFirstName(final String fatherFirstName) {
		this.fatherFirstName = fatherFirstName;
	}

	/**
	 * @return the fatherLastName
	 */
	public String getFatherLastName() {
		return this.fatherLastName;
	}

	/**
	 * @param fatherLastName
	 *            the fatherLastName to set
	 */
	public void setFatherLastName(final String fatherLastName) {
		this.fatherLastName = fatherLastName;
	}

	/**
	 * @return the fatherMiddleName
	 */
	public String getFatherMiddleName() {
		return this.fatherMiddleName;
	}

	/**
	 * @param fatherMiddleName
	 *            the fatherMiddleName to set
	 */
	public void setFatherMiddleName(final String fatherMiddleName) {
		this.fatherMiddleName = fatherMiddleName;
	}

	/**
	 * @return the fatherOccupation
	 */
	public String getFatherOccupation() {
		return this.fatherOccupation;
	}

	/**
	 * @param fatherOccupation
	 *            the fatherOccupation to set
	 */
	public void setFatherOccupation(final String fatherOccupation) {
		this.fatherOccupation = fatherOccupation;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(final String address) {
		this.address = address;
	}

	/**
	 * @return the primaryContactNumber
	 */
	public String getPrimaryContactNumber() {
		return this.primaryContactNumber;
	}

	/**
	 * @param primaryContactNumber
	 *            the primaryContactNumber to set
	 */
	public void setPrimaryContactNumber(final String primaryContactNumber) {
		this.primaryContactNumber = primaryContactNumber;
	}

	/**
	 * @return the secondaryContactNumber
	 */
	public String getSecondaryContactNumber() {
		return this.secondaryContactNumber;
	}

	/**
	 * @param secondaryContactNumber
	 *            the secondaryContactNumber to set
	 */
	public void setSecondaryContactNumber(final String secondaryContactNumber) {
		this.secondaryContactNumber = secondaryContactNumber;
	}

	/**
	 * @return the schoolName
	 */
	public String getSchoolName() {
		return this.schoolName;
	}

	/**
	 * @param schoolName
	 *            the schoolName to set
	 */
	public void setSchoolName(final String schoolName) {
		this.schoolName = schoolName;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return this.area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(final String area) {
		this.area = area;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return this.location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(final String location) {
		this.location = location;
	}

	/**
	 * @return the interestedInMPCGroup
	 */
	public boolean isInterestedInMPCGroup() {
		return this.interestedInMPCGroup;
	}

	/**
	 * @param interestedInMPCGroup
	 *            the interestedInMPCGroup to set
	 */
	public void setInterestedInMPCGroup(final boolean interestedInMPCGroup) {
		this.interestedInMPCGroup = interestedInMPCGroup;
	}

	/**
	 * @return the interestedInMECGroup
	 */
	public boolean isInterestedInMECGroup() {
		return this.interestedInMECGroup;
	}

	/**
	 * @param interestedInMECGroup
	 *            the interestedInMECGroup to set
	 */
	public void setInterestedInMECGroup(final boolean interestedInMECGroup) {
		this.interestedInMECGroup = interestedInMECGroup;
	}

	/**
	 * @return the interestedInBiPCGroup
	 */
	public boolean isInterestedInBiPCGroup() {
		return this.interestedInBiPCGroup;
	}

	/**
	 * @param interestedInBiPCGroup
	 *            the interestedInBiPCGroup to set
	 */
	public void setInterestedInBiPCGroup(final boolean interestedInBiPCGroup) {
		this.interestedInBiPCGroup = interestedInBiPCGroup;
	}

	/**
	 * @return the interestedInCECGroup
	 */
	public boolean isInterestedInCECGroup() {
		return this.interestedInCECGroup;
	}

	/**
	 * @param interestedInCECGroup
	 *            the interestedInCECGroup to set
	 */
	public void setInterestedInCECGroup(final boolean interestedInCECGroup) {
		this.interestedInCECGroup = interestedInCECGroup;
	}

	/**
	 * @return the registrationStatus
	 */
	public RegistrationStatusConstant getRegistrationStatus() {
		return this.registrationStatus;
	}

	/**
	 * @param registrationStatus
	 *            the registrationStatus to set
	 */
	public void setRegistrationStatus(final RegistrationStatusConstant registrationStatus) {
		this.registrationStatus = registrationStatus;
	}

	/**
	 * @return the collegeJoinedName
	 */
	public String getCollegeJoinedName() {
		return this.collegeJoinedName;
	}

	/**
	 * @param collegeJoinedName
	 *            the collegeJoinedName to set
	 */
	public void setCollegeJoinedName(final String collegeJoinedName) {
		this.collegeJoinedName = collegeJoinedName;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return this.reason;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */
	public void setReason(final String reason) {
		this.reason = reason;
	}

}
