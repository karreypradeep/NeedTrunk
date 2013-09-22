/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.hrms.dataobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.apeironsol.need.hrms.model.EmployeeCTC;
import com.apeironsol.need.payroll.model.EmployeeSalary;
import com.apeironsol.need.payroll.model.EmployeeSalaryComponent;
import com.apeironsol.need.payroll.model.EmployeeSalaryDeduction;

/**
 * Entity class for Employee
 * 
 * @author Pradeep
 */
public class EmployeeSalaryDO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long					serialVersionUID	= -1742635823464559836L;

	private EmployeeCTC							employeeCTC;

	private EmployeeSalary						employeeSalary;

	private Collection<EmployeeSalary>			advanceEmployeeSalaries;

	private Collection<EmployeeSalaryComponent>	employeeSalaryComponents;

	private Collection<EmployeeSalaryDeduction>	employeeSalaryDeductions;

	private Collection<EmployeeSalaryDeduction>	advanceEmployeeSalaryDeductions;

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
	 * @return the employeeSalaryComponents
	 */
	public Collection<EmployeeSalaryComponent> getEmployeeSalaryComponents() {
		return this.employeeSalaryComponents;
	}

	/**
	 * @param employeeSalaryComponents
	 *            the employeeSalaryComponents to set
	 */
	public void setEmployeeSalaryComponents(final Collection<EmployeeSalaryComponent> employeeMonthlySalaryComponents) {
		this.employeeSalaryComponents = employeeMonthlySalaryComponents;
	}

	/**
	 * @param employeeSalaryComponents
	 *            the employeeSalaryComponents to set
	 */
	public void addEmployeeSalaryComponent(final EmployeeSalaryComponent employeeSalaryComponent) {
		if (this.employeeSalaryComponents == null) {
			this.employeeSalaryComponents = new ArrayList<EmployeeSalaryComponent>();
		}
		this.employeeSalaryComponents.add(employeeSalaryComponent);
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
	 * @return the employeeSalaryDeductions
	 */
	public Collection<EmployeeSalaryDeduction> getEmployeeSalaryDeductions() {
		return this.employeeSalaryDeductions;
	}

	/**
	 * @param employeeSalaryDeductions
	 *            the employeeSalaryDeductions to set
	 */
	public void setEmployeeSalaryDeductions(final Collection<EmployeeSalaryDeduction> employeeSalaryDeductions) {
		this.employeeSalaryDeductions = employeeSalaryDeductions;
	}

	/**
	 * @param employeeSalaryDeduction
	 *            the employeeSalaryDeduction to add
	 */
	public void addEmployeeSalaryDeduction(final EmployeeSalaryDeduction employeeSalaryDeduction) {
		if (this.employeeSalaryDeductions == null) {
			this.employeeSalaryDeductions = new ArrayList<EmployeeSalaryDeduction>();
		}
		this.employeeSalaryDeductions.add(employeeSalaryDeduction);
	}

	public Double getGrossSalary() {
		Double totalGrossSal = 0.0;
		if (this.employeeSalaryComponents != null) {
			for (final EmployeeSalaryComponent employeeSalaryComponent : this.employeeSalaryComponents) {
				totalGrossSal += employeeSalaryComponent.getEmployeeCTCDetails().getAmount()
						/ (employeeSalaryComponent.getEmployeeCTCDetails().getSalaryPaymentFrequency() != null ? employeeSalaryComponent
								.getEmployeeCTCDetails().getSalaryPaymentFrequency().getFrequency() : 12);
			}
		}
		return totalGrossSal;
	}

	public Double getNetSalary() {
		Double totalNetSal = 0.0;
		if (this.employeeSalaryComponents != null) {
			for (final EmployeeSalaryComponent employeeSalaryComponent : this.employeeSalaryComponents) {
				totalNetSal += employeeSalaryComponent.getAmount() != null ? employeeSalaryComponent.getAmount() : 0;
			}
		}
		return totalNetSal;
	}

	public Double getTakeHomeSalary() {
		return this.getNetSalary() - this.getTotalDeduction();
	}

	public Double getTotalDeduction() {
		Double totalDeduction = 0.0;
		if (this.employeeSalaryDeductions != null) {
			for (final EmployeeSalaryDeduction employeeSalaryDeduction : this.employeeSalaryDeductions) {
				totalDeduction += employeeSalaryDeduction.getAmount() != null ? employeeSalaryDeduction.getAmount() : 0;
			}
		}
		if (this.advanceEmployeeSalaryDeductions != null) {
			for (final EmployeeSalaryDeduction employeeSalaryDeduction : this.advanceEmployeeSalaryDeductions) {
				totalDeduction += employeeSalaryDeduction.getAmount() != null ? employeeSalaryDeduction.getAmount() : 0;
			}
		}
		return totalDeduction;
	}

	public Double getAdvanceDeduction() {
		Double advanceDeduction = 0.0;
		if (this.advanceEmployeeSalaryDeductions != null) {
			for (final EmployeeSalaryDeduction employeeSalaryDeduction : this.advanceEmployeeSalaryDeductions) {
				advanceDeduction += employeeSalaryDeduction.getAmount() != null ? employeeSalaryDeduction.getAmount() : 0;
			}
		}
		return advanceDeduction;
	}

	/**
	 * @return the advanceEmployeeSalaries
	 */
	public Collection<EmployeeSalary> getAdvanceEmployeeSalaries() {
		return this.advanceEmployeeSalaries;
	}

	/**
	 * @param advanceEmployeeSalaries
	 *            the advanceEmployeeSalaries to set
	 */
	public void setAdvanceEmployeeSalaries(final Collection<EmployeeSalary> advanceEmployeeSalaries) {
		this.advanceEmployeeSalaries = advanceEmployeeSalaries;
	}

	/**
	 * @param advanceEmployeeSalaries
	 *            the advanceEmployeeSalaries to set
	 */
	public void addAdvanceEmployeeSalary(final EmployeeSalary advanceEmployeeSalary) {
		if (this.advanceEmployeeSalaries == null) {
			this.advanceEmployeeSalaries = new ArrayList<EmployeeSalary>();
		}
		this.advanceEmployeeSalaries.add(advanceEmployeeSalary);
	}

	public Double getTotalAdvanceSalary() {
		Double totalAdvanceSalary = 0.0;
		if (this.advanceEmployeeSalaries != null) {
			for (final EmployeeSalary employeeSalary : this.advanceEmployeeSalaries) {
				totalAdvanceSalary += employeeSalary.getAmount() != null ? employeeSalary.getAmount() : 0;
			}
		}
		return totalAdvanceSalary;
	}

	/**
	 * @return the advanceEmployeeSalaryDeductions
	 */
	public Collection<EmployeeSalaryDeduction> getAdvanceEmployeeSalaryDeductions() {
		return this.advanceEmployeeSalaryDeductions;
	}

	/**
	 * @param advanceEmployeeSalaryDeductions
	 *            the advanceEmployeeSalaryDeductions to set
	 */
	public void setAdvanceEmployeeSalaryDeductions(final Collection<EmployeeSalaryDeduction> advanceEmployeeSalaryDeductions) {
		this.advanceEmployeeSalaryDeductions = advanceEmployeeSalaryDeductions;
	}
}
