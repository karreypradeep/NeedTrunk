/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

/**
 * class for relation
 * 
 * @author Pradeep
 */
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;

import com.apeironsol.need.util.constants.RelationTypeConstant;

@Entity
@Table(name = "RELATION")
public class Relation extends Person implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long		serialVersionUID	= 8416585263133851170L;

	@ManyToMany(mappedBy = "relations")
	private Collection<Student>		students;

	@Column(name = "RELATION_TYPE", length = 20)
	@Enumerated(EnumType.STRING)
	private RelationTypeConstant	relationType;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "ADDRESS_ID")
	private Address					address;

	@Temporal(TemporalType.DATE)
	@Past(message = "Please specify a valid birth date(Hint: Past date).")
	@Column(name = "DATE_OF_BIRTH")
	private Date					dateOfBirth;

	@Min(value = 0, message = "model.amount_cannot_be_lessthen_zero")
	@Column(name = "ANNUAL_SALARY")
	private Double					annualIncome;

	@Column(name = "OCCUPATION")
	private String					occupation;

	/**
	 * @return the occupation
	 */
	public String getOccupation() {
		return this.occupation;
	}

	/**
	 * @param occupation
	 *            the occupation to set
	 */
	public void setOccupation(final String occupation) {
		this.occupation = occupation;
	}

	/**
	 * @return the annualIncome
	 */
	public Double getAnnualIncome() {
		return this.annualIncome;
	}

	/**
	 * @param annualIncome
	 *            the annualIncome to set
	 */
	public void setAnnualIncome(final Double annualIncome) {
		this.annualIncome = annualIncome;
	}

	public Collection<Student> getStudents() {
		return this.students;
	}

	public void setStudents(final Collection<Student> students) {
		this.students = students;
	}

	public RelationTypeConstant getRelationType() {
		return this.relationType;
	}

	public void setRelationType(final RelationTypeConstant relationType) {
		this.relationType = relationType;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(final Address address) {
		this.address = address;
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
