/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 *
 */
package com.apeironsol.need.hrms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.util.constants.EmploymentTypeConstant;
import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for EmployeeDesignation
 *
 * @author Sunny
 *
 */
@Entity
@Table(name = "EMPLOYEE_DESIGNATION")
public class EmployeeDesignation extends BaseEntity implements Serializable {

	/**
	 *
	 */
	private static final long		serialVersionUID	= -4268414192094782092L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DESIG_TYPE_BUILD_BLK_ID", nullable = false)
	private BuildingBlock			designation;

	@NotNull(message = "model.employment_type_mandatory")
	@Column(name = "EMPLOYMENT_TYPE", length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	private EmploymentTypeConstant	employmentType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DEPARTMENT_BUILD_BLK_ID", nullable = false)
	private BuildingBlock			department;

	@NotNull(message = "model.start_date_mandatory")
	@Column(name = "START_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date					startDate;

	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	private Date					endDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee				employee;


	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return this.endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Returns designation.
	 *
	 * @return the designation.
	 */
	public BuildingBlock getDesignation() {
		return this.designation;
	}

	/**
	 * Sets designation.
	 *
	 * @param designation
	 *            the designation to set.
	 */
	public void setDesignation(final BuildingBlock designation) {
		this.designation = designation;
	}

	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return this.employee;
	}

	/**
	 * @param employee
	 *            the employee to set
	 */
	public void setEmployee(final Employee employee) {
		this.employee = employee;
	}

	/**
	 * @return the department
	 */
	public BuildingBlock getDepartment() {
		return this.department;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(final BuildingBlock department) {
		this.department = department;
	}

	/**
	 * @return the employmentType
	 */
	public EmploymentTypeConstant getEmploymentType() {
		return this.employmentType;
	}

	/**
	 * @param employmentType
	 *            the employmentType to set
	 */
	public void setEmploymentType(final EmploymentTypeConstant employmentType) {
		this.employmentType = employmentType;
	}

}
