/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.apeironsol.framework.BaseEntity;


/**
 * 
 * @author sunny
 * 
 *         class for medical history
 * 
 */
@Entity
@Table(name = "MEDICAL_HISTORY")
public class MedicalHistory extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= 9214936880110813117L;

	@Column(name = "CURRENTLY_USING_MEDICINES", length = 200)
	private String				currentlyUsingMedicines;

	@Column(name = "KNOWN_ALLERGIES", length = 200)
	private String				knownAllergies;

	@Column(name = "CURRENT_MEDICAL_CONDITION", length = 200)
	private String				currentMedicalCondition;

	@Column(name = "PREVIOUS_OPERATATION_DETAILS", length = 200)
	private String				previousOperatationDetails;

	@Column(name = "RELATIVES_HEALTH_DETAILS", length = 200)
	private String				relativesHealthDetails;

	@JoinColumn(name = "STUDENT_ID", nullable = false)
	@OneToOne(fetch = FetchType.LAZY)
	private Student				student;

	public Student getStudent() {
		return student;
	}

	public void setStudent(final Student student) {
		this.student = student;
	}

	/**
	 * @return the currentlyUsingMedicines
	 */
	public String getCurrentlyUsingMedicines() {
		return currentlyUsingMedicines;
	}

	/**
	 * @param currentlyUsingMedicines
	 *            the currentlyUsingMedicines to set
	 */
	public void setCurrentlyUsingMedicines(final String currentlyUsingMedicines) {
		this.currentlyUsingMedicines = currentlyUsingMedicines;
	}

	/**
	 * @return the knownAllergies
	 */
	public String getKnownAllergies() {
		return knownAllergies;
	}

	/**
	 * @param knownAllergies
	 *            the knownAllergies to set
	 */
	public void setKnownAllergies(final String knownAllergies) {
		this.knownAllergies = knownAllergies;
	}

	/**
	 * @return the previousOperatationDetails
	 */
	public String getPreviousOperatationDetails() {
		return previousOperatationDetails;
	}

	/**
	 * @return the currentMedicalCondition
	 */
	public String getCurrentMedicalCondition() {
		return currentMedicalCondition;
	}

	/**
	 * @param currentMedicalCondition
	 *            the currentMedicalCondition to set
	 */
	public void setCurrentMedicalCondition(final String currentMedicalCondition) {
		this.currentMedicalCondition = currentMedicalCondition;
	}

	/**
	 * @param previousOperatationDetails
	 *            the previousOperatationDetails to set
	 */
	public void setPreviousOperatationDetails(final String previousOperatationDetails) {
		this.previousOperatationDetails = previousOperatationDetails;
	}

	/**
	 * @return the relativesHealthDetails
	 */
	public String getRelativesHealthDetails() {
		return relativesHealthDetails;
	}

	/**
	 * @param relativesHealthDetails
	 *            the relativesHealthDetails to set
	 */
	public void setRelativesHealthDetails(final String relativesHealthDetails) {
		this.relativesHealthDetails = relativesHealthDetails;
	}

}
