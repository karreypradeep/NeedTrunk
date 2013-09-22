/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.hrms.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for Employee
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "EMPLOYEE_CTC")
public class EmployeeCTC extends BaseEntity {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long				serialVersionUID	= -5686528891278484529L;

	@NotNull(message = "model.employee_id_mandatory")
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee						employee;

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE", nullable = false)
	private Date							startDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE")
	private Date							endDate;

	@Column(name = "TOTAL_CTC")
	private Double							totalCTC;

	@OneToMany(mappedBy = "employeeCTC", cascade = CascadeType.ALL)
	private Collection<EmployeeCTCDetails>	employeeCTCDetails;

	@OneToOne(optional = true)
	@JoinColumn(name = "PREVS_EMPLOYEE_CTC_ID")
	private EmployeeCTC						previousEmployeeCTC;

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
	 * @return the totalCTC
	 */
	public Double getTotalCTC() {
		return this.totalCTC;
	}

	/**
	 * @param totalCTC
	 *            the totalCTC to set
	 */
	public void setTotalCTC(final Double totalCTC) {
		this.totalCTC = totalCTC;
	}

	/**
	 * @return the employeeCTCDetails
	 */
	public Collection<EmployeeCTCDetails> getEmployeeCTCDetails() {
		return this.employeeCTCDetails;
	}

	/**
	 * @param employeeCTCDetails
	 *            the employeeCTCDetails to set
	 */
	public void setEmployeeCTCDetails(final Collection<EmployeeCTCDetails> employeeCTCDetails) {
		this.employeeCTCDetails = employeeCTCDetails;
	}

	/**
	 * @return the previousEmployeeCTC
	 */
	public EmployeeCTC getPreviousEmployeeCTC() {
		return previousEmployeeCTC;
	}

	/**
	 * @param previousEmployeeCTC the previousEmployeeCTC to set
	 */
	public void setPreviousEmployeeCTC(EmployeeCTC previousEmployeeCTC) {
		this.previousEmployeeCTC = previousEmployeeCTC;
	}

}
