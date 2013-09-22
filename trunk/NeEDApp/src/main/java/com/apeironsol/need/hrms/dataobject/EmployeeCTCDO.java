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
import java.util.Iterator;

import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.hrms.model.EmployeeCTC;
import com.apeironsol.need.hrms.model.EmployeeCTCDetails;
import com.apeironsol.need.util.constants.EmployeeCTCDefinitionTypeConstant;

/**
 * Entity class for Employee
 * 
 * @author Pradeep
 */
public class EmployeeCTCDO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long				serialVersionUID	= 4838558749717255838L;

	private EmployeeCTC						employeeCTC;

	private Collection<EmployeeCTCDetails>	employeeCTCDefForMonthlySalary;

	private Collection<EmployeeCTCDetails>	employeeCTCDefForAnnualBenefits;

	private Collection<EmployeeCTCDetails>	employeeCTCDefForAdditionalBenefits;

	private Collection<EmployeeCTCDetails>	employeeCTCDefForRetiralBenefits;

	private Collection<EmployeeCTCDetails>	allEmployeeCTCDefinitionDetails;

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
	 * @return the employeeCTCDefForMonthlySalary
	 */
	public Collection<EmployeeCTCDetails> getEmployeeCTCDefForMonthlySalary() {
		return this.employeeCTCDefForMonthlySalary;
	}

	/**
	 * @param employeeCTCDefForMonthlySalary
	 *            the employeeCTCDefForMonthlySalary to set
	 */
	public void setEmployeeCTCDefForMonthlySalary(final Collection<EmployeeCTCDetails> employeeCTCDefForMonthlySalary) {
		this.employeeCTCDefForMonthlySalary = employeeCTCDefForMonthlySalary;
	}

	/**
	 * @return the employeeCTCDefForAnnualBenefits
	 */
	public Collection<EmployeeCTCDetails> getEmployeeCTCDefForAnnualBenefits() {
		return this.employeeCTCDefForAnnualBenefits;
	}

	/**
	 * @param employeeCTCDefForAnnualBenefits
	 *            the employeeCTCDefForAnnualBenefits to set
	 */
	public void setEmployeeCTCDefForAnnualBenefits(final Collection<EmployeeCTCDetails> employeeCTCDefForAnnualBenefits) {
		this.employeeCTCDefForAnnualBenefits = employeeCTCDefForAnnualBenefits;
	}

	/**
	 * @return the employeeCTCDefForAdditionalBenefits
	 */
	public Collection<EmployeeCTCDetails> getEmployeeCTCDefForAdditionalBenefits() {
		return this.employeeCTCDefForAdditionalBenefits;
	}

	/**
	 * @param employeeCTCDefForAdditionalBenefits
	 *            the employeeCTCDefForAdditionalBenefits to set
	 */
	public void setEmployeeCTCDefForAdditionalBenefits(final Collection<EmployeeCTCDetails> employeeCTCDefForAdditionalBenefits) {
		this.employeeCTCDefForAdditionalBenefits = employeeCTCDefForAdditionalBenefits;
	}

	/**
	 * @return the employeeCTCDefForRetiralBenefits
	 */
	public Collection<EmployeeCTCDetails> getEmployeeCTCDefForRetiralBenefits() {
		return this.employeeCTCDefForRetiralBenefits;
	}

	/**
	 * @param employeeCTCDefForRetiralBenefits
	 *            the employeeCTCDefForRetiralBenefits to set
	 */
	public void setEmployeeCTCDefForRetiralBenefits(final Collection<EmployeeCTCDetails> employeeCTCDefForRetiralBenefits) {
		this.employeeCTCDefForRetiralBenefits = employeeCTCDefForRetiralBenefits;
	}

	public void addMonthlySalaryType(final EmployeeCTCDetails cTCDefForMonthlySalary) {
		if (this.employeeCTCDefForMonthlySalary == null) {
			this.employeeCTCDefForMonthlySalary = new ArrayList<EmployeeCTCDetails>();
		}
		this.employeeCTCDefForMonthlySalary.add(cTCDefForMonthlySalary);
	}

	public void addAdditionalBenefitType(final EmployeeCTCDetails cTCDefForAdditionalBenefit) {
		if (this.employeeCTCDefForAdditionalBenefits == null) {
			this.employeeCTCDefForAdditionalBenefits = new ArrayList<EmployeeCTCDetails>();
		}
		this.employeeCTCDefForAdditionalBenefits.add(cTCDefForAdditionalBenefit);
	}

	public void addAnnualBenefitType(final EmployeeCTCDetails cTCDefForAnnualBenefit) {
		if (this.employeeCTCDefForAnnualBenefits == null) {
			this.employeeCTCDefForAnnualBenefits = new ArrayList<EmployeeCTCDetails>();
		}
		this.employeeCTCDefForAnnualBenefits.add(cTCDefForAnnualBenefit);
	}

	public void addRetiralBenefitType(final EmployeeCTCDetails cTCDefForRetiralBenefit) {
		if (this.employeeCTCDefForRetiralBenefits == null) {
			this.employeeCTCDefForRetiralBenefits = new ArrayList<EmployeeCTCDetails>();
		}
		this.employeeCTCDefForRetiralBenefits.add(cTCDefForRetiralBenefit);
	}

	/**
	 * @return the allEmployeeCTCDefinitionDetails
	 */
	public Collection<EmployeeCTCDetails> getAllEmployeeCTCDefinitionDetails() {
		this.allEmployeeCTCDefinitionDetails = new ArrayList<EmployeeCTCDetails>();
		if (this.employeeCTCDefForRetiralBenefits != null) {
			this.allEmployeeCTCDefinitionDetails.addAll(this.employeeCTCDefForRetiralBenefits);
		}
		if (this.employeeCTCDefForAnnualBenefits != null) {
			this.allEmployeeCTCDefinitionDetails.addAll(this.employeeCTCDefForAnnualBenefits);
		}
		if (this.employeeCTCDefForAdditionalBenefits != null) {
			this.allEmployeeCTCDefinitionDetails.addAll(this.employeeCTCDefForAdditionalBenefits);
		}
		if (this.employeeCTCDefForMonthlySalary != null) {
			this.allEmployeeCTCDefinitionDetails.addAll(this.employeeCTCDefForMonthlySalary);
		}

		Iterator<EmployeeCTCDetails> iterator = this.allEmployeeCTCDefinitionDetails.iterator();
		while (iterator.hasNext()) {
			EmployeeCTCDetails employeeCTCDetails = iterator.next();
			if (employeeCTCDetails.getAmount() <= 0) {
				iterator.remove();
			}
		}
		return this.allEmployeeCTCDefinitionDetails;
	}

	public EmployeeCTCDetails getEmployeeCTCDefinitionDetailsByBBId(final BuildingBlock buildingBlock) {
		EmployeeCTCDetails result = null;
		if (this.employeeCTC.getEmployeeCTCDetails() != null) {
			for (EmployeeCTCDetails employeeCTCDetails : this.employeeCTC.getEmployeeCTCDetails()) {
				if (buildingBlock.getId().equals(employeeCTCDetails.getCtcDefinitionType().getId())) {
					result = employeeCTCDetails;
					break;
				}
			}
		}
		return result;
	}

	public double getEmployeeCTCAmountNotAllotted() {
		double result = this.employeeCTC.getTotalCTC() != null ? this.employeeCTC.getTotalCTC() : 0;
		if (this.employeeCTC.getEmployeeCTCDetails() != null && result > 0) {
			for (EmployeeCTCDetails employeeCTCDetails : this.employeeCTC.getEmployeeCTCDetails()) {
				result = result - (employeeCTCDetails.getAmount() != null ? employeeCTCDetails.getAmount() : 0);
			}
		}
		return result;
	}

	/**
	 * @param allEmployeeCTCDetails
	 *            the allEmployeeCTCDetails to set
	 */
	public void setAllEmployeeCTCDefinitionDetails(final Collection<EmployeeCTCDetails> allEmployeeCTCDetails) {
		this.allEmployeeCTCDefinitionDetails = allEmployeeCTCDetails;
		for (EmployeeCTCDetails employeeCTCDetails : this.allEmployeeCTCDefinitionDetails) {
			if (EmployeeCTCDefinitionTypeConstant.MONTHLY_SALARY_STRUCTURE.equals(employeeCTCDetails.getCtcDefinitionType().getCtcDefinitionType())) {
				this.addMonthlySalaryType(employeeCTCDetails);
			} else if (EmployeeCTCDefinitionTypeConstant.ADDITIONAL_BENEFITS.equals(employeeCTCDetails.getCtcDefinitionType().getCtcDefinitionType())) {
				this.addAdditionalBenefitType(employeeCTCDetails);
			} else if (EmployeeCTCDefinitionTypeConstant.ANNUAL_BENEFITS.equals(employeeCTCDetails.getCtcDefinitionType().getCtcDefinitionType())) {
				this.addAnnualBenefitType(employeeCTCDetails);
			} else if (EmployeeCTCDefinitionTypeConstant.RETTIRAL_BENEFITS.equals(employeeCTCDetails.getCtcDefinitionType().getCtcDefinitionType())) {
				this.addRetiralBenefitType(employeeCTCDetails);
			}

		}

	}
}
