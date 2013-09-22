/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.hrms.model;

/**
 * Entity class for Employee
 * 
 * @author Sunny
 */
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.Person;
import com.apeironsol.need.util.constants.EmploymentCurrentStatusConstant;

@Entity
@Table(name = "EMPLOYEE")
public class Employee extends Person implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long				serialVersionUID	= -3111623027984971493L;

	@Column(name = "EMPLOYEE_NUMBER", length = 20, nullable = false)
	private String							employeeNumber;

	@NotNull(message = "model.organization_unit_mandatory")
	@ManyToOne
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch							branch;

	@NotNull(message = "model.current_status_mandatory")
	@Column(name = "CURRENT_STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
	private EmploymentCurrentStatusConstant	currentStatus;

	@Temporal(TemporalType.DATE)
	@NotNull(message = "Date of birth is mandatory.")
	@Past(message = "Please specify a valid birth date(Hint: Past date).")
	@Column(name = "DATE_OF_BIRTH", nullable = false)
	private Date							dateOfBirth;

	/**
	 * @return the employeeNumber
	 */
	public String getEmployeeNumber() {
		return this.employeeNumber;
	}

	/**
	 * @param employeeNumber
	 *            the employeeNumber to setaddress.address
	 */
	public void setEmployeeNumber(final String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	/**
	 * @return the currentStatus
	 */
	public EmploymentCurrentStatusConstant getCurrentStatus() {
		return this.currentStatus;
	}

	/**
	 * @param currentStatus
	 *            the currentStatus to set
	 */
	public void setCurrentStatus(final EmploymentCurrentStatusConstant currentStatus) {
		this.currentStatus = currentStatus;
	}

	/**
	 * Returns branch.
	 * 
	 * @return the branch.
	 */
	public Branch getBranch() {
		return this.branch;
	}

	/**
	 * Sets branch.
	 * 
	 * @param branch
	 *            the branch to set.
	 */
	public void setBranch(final Branch branch) {
		this.branch = branch;
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
