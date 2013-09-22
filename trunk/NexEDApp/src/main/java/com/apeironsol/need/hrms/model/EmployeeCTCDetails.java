/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.hrms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.util.constants.SalaryFrequencyConstant;
import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for Employee
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "EMPLOYEE_CTC_DETAILS")
public class EmployeeCTCDetails extends BaseEntity {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long		serialVersionUID	= -496361283468587017L;

	@NotNull(message = "model.employee_ctc_mandatory")
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_CTC_ID", nullable = false)
	private EmployeeCTC				employeeCTC;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CTC_DEF_BUILD_BLK_ID", nullable = false)
	private BuildingBlock			ctcDefinitionType;

	@Column(name = "AMOUNT", nullable = false)
	private Double					amount;

	@Column(name = "SALARY_FREQUENCY", length = 50)
	@Enumerated(EnumType.STRING)
	private SalaryFrequencyConstant	salaryPaymentFrequency;

	/**
	 * @return the employeeCTC
	 */
	public EmployeeCTC getEmployeeCTC() {
		return this.employeeCTC;
	}

	/**
	 * @param employeeCTC
	 *            the employeeCTC to set
	 */
	public void setEmployeeCTC(final EmployeeCTC employeeCTC) {
		this.employeeCTC = employeeCTC;
	}

	/**
	 * @return the ctcDefinitionType
	 */
	public BuildingBlock getCtcDefinitionType() {
		return this.ctcDefinitionType;
	}

	/**
	 * @param ctcDefinitionType
	 *            the ctcDefinitionType to set
	 */
	public void setCtcDefinitionType(final BuildingBlock ctcDefinitionType) {
		this.ctcDefinitionType = ctcDefinitionType;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return this.amount != null ? this.amount : 0;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the salaryPaymentFrequency
	 */
	public SalaryFrequencyConstant getSalaryPaymentFrequency() {
		return this.salaryPaymentFrequency;
	}

	/**
	 * @param salaryPaymentFrequency
	 *            the salaryPaymentFrequency to set
	 */
	public void setSalaryPaymentFrequency(final SalaryFrequencyConstant salaryPaymentFrequency) {
		this.salaryPaymentFrequency = salaryPaymentFrequency;
	}

}
