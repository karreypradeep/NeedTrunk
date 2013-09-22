/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

/**
 * Entity class for student
 * 
 * @author Pradeep
 */
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.apeironsol.need.util.constants.AdmissionStatusConstant;
import com.apeironsol.need.util.constants.ResidenceConstant;

@Entity
@Table(name = "ADMISSION", uniqueConstraints = { @UniqueConstraint(columnNames = { "BRANCH_ID", "EXTERNAL_ADMISSION_NR" }, name = "UQ_EXT_ADMIS_NR_BRANCH_ID") })
public class Admission extends Person implements Serializable {

	/**
	 * Universal serial version id for this class
	 */
	private static final long		serialVersionUID	= 6198842711792055608L;

	@Column(name = "REGISTRATION_NR", length = 50, unique = true)
	private String					registrationNr;

	@Column(name = "PREVIOUS_QUALIFIED_EDUCATION", length = 40)
	private String					previousQualifiedEducation;

	@Basic
	@Column(name = "ADMISSION_STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
	private AdmissionStatusConstant	admissionStatus;

	@Temporal(TemporalType.DATE)
	@Column(name = "SUBMITTED_DATE")
	private Date					submittedDate;

	@NotNull(message = "model.applying_for_class_mandatory")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "APPLIED_CLASS_ID", nullable = false)
	private Klass					applyingForKlass;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "APPLIED_BATCH_ID")
	private Batch					appliedForBatch;

	@NotNull(message = "model.academic_year_mandatory")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "APPLIED_ACADEMIC_YEAR_ID", nullable = false)
	private AcademicYear			appliedForAcademicYear;

	@NotNull(message = "Residence type is mandatory.")
	@Basic
	@Column(name = "RESIDENCE", nullable = false)
	@Enumerated(EnumType.STRING)
	private ResidenceConstant		residence;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "COUNTRY_OF_BIRTH")
	private Country					countryOfBirth;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "ADDRESS_ID")
	private Address					address;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	private Collection<Relation>	relations;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch					branch;

	@Column(name = "EXTERNAL_ADMISSION_NR", length = 20)
	private String					externalAdmissionNr;

	@Column(name = "NATIONALITY", length = 50, nullable = false)
	private String					nationality;

	@Column(name = "RELIGION", length = 50, nullable = false)
	private String					religion;

	@Column(name = "MOTHER_TONGUE", length = 50, nullable = false)
	private String					motherTongue;

	@Column(name = "RESERVATION", length = 50, nullable = false)
	private String					reservation;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ACCEPTED_CLASS_ID")
	private Klass					acceptedForKlass;

	@Temporal(TemporalType.DATE)
	@NotNull(message = "Date of birth is mandatory.")
	@Past(message = "Please specify a valid birth date(Hint: Past date).")
	@Column(name = "DATE_OF_BIRTH", nullable = false)
	private Date					dateOfBirth;

	public Klass getAcceptedForKlass() {
		return this.acceptedForKlass;
	}

	public void setAcceptedForKlass(final Klass acceptedForKlass) {
		this.acceptedForKlass = acceptedForKlass;
	}

	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(final String nationality) {
		this.nationality = nationality;
	}

	public String getReligion() {
		return this.religion;
	}

	public void setReligion(final String religion) {
		this.religion = religion;
	}

	public String getMotherTongue() {
		return this.motherTongue;
	}

	public void setMotherTongue(final String motherTongue) {
		this.motherTongue = motherTongue;
	}

	public String getReservation() {
		return this.reservation;
	}

	public void setReservation(final String reservation) {
		this.reservation = reservation;
	}

	/**
	 * @return the externalAdmissionNr
	 */
	public String getExternalAdmissionNr() {
		return this.externalAdmissionNr;
	}

	/**
	 * @param externalAdmissionNr
	 *            the externalAdmissionNr to set
	 */
	public void setExternalAdmissionNr(final String externalAdmissionNr) {
		this.externalAdmissionNr = externalAdmissionNr;
	}

	public String getRegistrationNr() {
		return this.registrationNr;
	}

	public void setRegistrationNr(final String registrationNr) {
		this.registrationNr = registrationNr;
	}

	public String getPreviousQualifiedEducation() {
		return this.previousQualifiedEducation;
	}

	public void setPreviousQualifiedEducation(final String previousQualifiedEducation) {
		this.previousQualifiedEducation = previousQualifiedEducation;
	}

	public AdmissionStatusConstant getAdmissionStatus() {
		return this.admissionStatus;
	}

	public void setAdmissionStatus(final AdmissionStatusConstant admissionStatus) {
		this.admissionStatus = admissionStatus;
	}

	public Date getSubmittedDate() {
		return this.submittedDate;
	}

	public void setSubmittedDate(final Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public Klass getApplyingForKlass() {
		return this.applyingForKlass;
	}

	public void setApplyingForKlass(final Klass applyingForKlass) {
		this.applyingForKlass = applyingForKlass;
	}

	public AcademicYear getAppliedForAcademicYear() {
		return this.appliedForAcademicYear;
	}

	public void setAppliedForAcademicYear(final AcademicYear appliedForAcademicYear) {
		this.appliedForAcademicYear = appliedForAcademicYear;
	}

	public ResidenceConstant getResidence() {
		return this.residence;
	}

	public void setResidence(final ResidenceConstant residence) {
		this.residence = residence;
	}

	public Country getCountryOfBirth() {
		return this.countryOfBirth;
	}

	public void setCountryOfBirth(final Country countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(final Address address) {
		this.address = address;
	}

	public Collection<Relation> getRelations() {
		return this.relations;
	}

	public void setRelations(final Collection<Relation> relations) {
		this.relations = relations;
	}

	public Branch getBranch() {
		return this.branch;
	}

	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	public Batch getAppliedForBatch() {
		return this.appliedForBatch;
	}

	public void setAppliedForBatch(final Batch appliedForBatch) {
		this.appliedForBatch = appliedForBatch;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(final Date dateofBirth) {
		Calendar theDateOfBirth = null;
		if (dateofBirth != null) {
			theDateOfBirth = Calendar.getInstance();
			theDateOfBirth.setTime(dateofBirth);
			final Date toDay = new Date(System.currentTimeMillis());

			if (theDateOfBirth.after(toDay)) {
				// TODO throw exception
			}
			this.dateOfBirth = theDateOfBirth.getTime();
		}

	}
}
