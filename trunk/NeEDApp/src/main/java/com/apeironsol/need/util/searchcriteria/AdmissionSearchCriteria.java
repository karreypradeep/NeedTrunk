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
import com.apeironsol.need.util.constants.AdmissionStatusConstant;

/**
 * Singleton class for Admission search criteria..
 * 
 * @author Pradeep
 */
public class AdmissionSearchCriteria implements SearchCriteria {

	/**
	 * Universal serial version id for this class
	 */
	private static final long		serialVersionUID	= 6070822367619472726L;

	/**
	 * Student date of birth.
	 */
	private Date					dateOfBirth;

	/**
	 * Name of student.
	 */
	private String					name;

	/**
	 * Name of student.
	 */
	private String					admissionNumber;

	/**
	 * Name of student.
	 */
	private String					registrationNumber;

	/**
	 * Name of student.
	 */
	private Klass					acceptedForKlass;

	/**
	 * academicYear.
	 */
	private AcademicYear			academicYear;

	/**
	 * Student status.
	 */
	private AdmissionStatusConstant	admissionStatus;

	/**
	 * Branch.
	 */
	private Branch					branch;

	/**
	 * Name of student.
	 */
	private Klass					appliedForClass;

	/**
	 * 
	 * @param branch
	 */
	public AdmissionSearchCriteria(final Branch branch) {
		this.setBranch(branch);
	}

	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	/**
	 * @param dateOfBirth
	 *            the dateOfBirth to set
	 */
	public void setDateOfBirth(final Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetSeacrhCriteria() {
		this.dateOfBirth = null;
		this.name = null;
		this.admissionNumber = null;
		this.registrationNumber = null;
		this.acceptedForKlass = null;
		this.academicYear = null;
		this.admissionStatus = null;
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
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSearchCriteriaIsEmpty() {
		return (this.name == null || this.name.isEmpty()) && (this.admissionNumber == null || this.admissionNumber.isEmpty()) && this.acceptedForKlass == null
				&& this.academicYear == null && this.admissionStatus == null && (this.registrationNumber == null || this.registrationNumber.isEmpty())
				&& this.dateOfBirth == null;
	}

	/**
	 * @return the admissionNumber
	 */
	public String getAdmissionNumber() {
		return this.admissionNumber;
	}

	/**
	 * @param admissionNumber
	 *            the admissionNumber to set
	 */
	public void setAdmissionNumber(final String admissionNumber) {
		this.admissionNumber = admissionNumber;
	}

	/**
	 * @return the registrationNumber
	 */
	public String getRegistrationNumber() {
		return this.registrationNumber;
	}

	/**
	 * @param registrationNumber
	 *            the registrationNumber to set
	 */
	public void setRegistrationNumber(final String registrationNumber) {
		this.registrationNumber = registrationNumber;
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
	 * @return the admissionStatus
	 */
	public AdmissionStatusConstant getAdmissionStatusConstant() {
		return this.admissionStatus;
	}

	/**
	 * @param admissionStatus
	 *            the admissionStatus to set
	 */
	public void setAdmissionStatusConstant(final AdmissionStatusConstant admissionStatus) {
		this.admissionStatus = admissionStatus;
	}

	/**
	 * @return the branch
	 */
	@Override
	public Branch getBranch() {
		return this.branch;
	}

	/**
	 * @return the acceptedForKlass
	 */
	public Klass getAcceptedForKlass() {
		return this.acceptedForKlass;
	}

	/**
	 * @param acceptedForKlass
	 *            the acceptedForKlass to set
	 */
	public void setAcceptedForKlass(final Klass acceptedForKlass) {
		this.acceptedForKlass = acceptedForKlass;
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
	 * @return the appliedForClass
	 */
	public Klass getAppliedForClass() {
		return appliedForClass;
	}

	/**
	 * @param appliedForClass the appliedForClass to set
	 */
	public void setAppliedForClass(Klass appliedForClass) {
		this.appliedForClass = appliedForClass;
	}

}
