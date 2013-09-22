/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 *
 */
package com.apeironsol.need.hrms.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.apeironsol.need.hrms.model.Employee;
import com.apeironsol.need.hrms.model.EmployeeDesignation;

/**
 * Entity class for Employee
 */
public class EmployeeDO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long				serialVersionUID	= -3980315202635005046L;


	private Employee						employee;

	private EmployeeDesignation employeeCurrentDesignation;

	private Date joiningDate;


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

	public EmployeeDesignation getEmployeeCurrentDesignation() {
		return this.employeeCurrentDesignation;
	}

	public void setEmployeeCurrentDesignation(final EmployeeDesignation employeeCurrentDesignation) {
		this.employeeCurrentDesignation = employeeCurrentDesignation;
	}

	public Date getJoiningDate() {
		return this.joiningDate;
	}

	public void setJoiningDate(final Date joiningDate) {
		this.joiningDate = joiningDate;
	}



}
