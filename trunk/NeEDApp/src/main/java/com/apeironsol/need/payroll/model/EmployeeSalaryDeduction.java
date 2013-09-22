/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.payroll.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for EmployeeSalary
 * 
 * @author Sunny
 * 
 */
@Entity
@Table(name = "EMPLOYEE_SALARY_DEDUCTION")
public class EmployeeSalaryDeduction extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -2630935805697553294L;

	@NotNull(message = "model.amount_mandatory")
	@Min(value = 0, message = "model.amount_cannot_be_lessthen_zero")
	@Column(name = "AMOUNT", nullable = false)
	private Double				amount;

	@NotNull(message = "model.building_block_id_mandatory")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BUILDING_BLOCK_ID", nullable = false)
	private BuildingBlock		decutionBuildingBlock;

	@NotNull(message = "model.employee_salary_id_mandatory")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EMPLOYEE_SALARY_ID", nullable = false)
	private EmployeeSalary		employeeSalary;

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return this.amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the employeeSalary
	 */
	public EmployeeSalary getEmployeeSalary() {
		return this.employeeSalary;
	}

	/**
	 * @param employeeSalary
	 *            the employeeSalary to set
	 */
	public void setEmployeeSalary(final EmployeeSalary employeeSalary) {
		this.employeeSalary = employeeSalary;
	}

	/**
	 * @return the decutionBuildingBlock
	 */
	public BuildingBlock getDecutionBuildingBlock() {
		return this.decutionBuildingBlock;
	}

	/**
	 * @param decutionBuildingBlock
	 *            the decutionBuildingBlock to set
	 */
	public void setDecutionBuildingBlock(final BuildingBlock decutionBuildingBlock) {
		this.decutionBuildingBlock = decutionBuildingBlock;
	}

}
