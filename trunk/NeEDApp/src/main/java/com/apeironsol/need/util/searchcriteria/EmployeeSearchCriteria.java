/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.util.searchcriteria;

import java.util.Date;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.util.constants.EmploymentTypeConstant;

/**
 * Singleton class for branch employee search criteria..
 * 
 * @author Pradeep
 */
public class EmployeeSearchCriteria implements SearchCriteria {

	/**
	 * Universal serial version id for this class
	 */
	private static final long		serialVersionUID	= 1935584958557990805L;

	/**
	 * Student date of birth.
	 */
	private Date					dateOfBirth;

	/**
	 * Name of student.
	 */
	private String					name;

	/**
	 * Name of student.
	 */
	private String					employeeNumber;

	/**
	 * Student date of birth.
	 */
	private Date					joiningDate;

	/**
	 * Student status.
	 */
	private EmploymentTypeConstant	employmentType;

	/**
	 * Student status.
	 */
	private BuildingBlock			departmentBuildingBlock;

	/**
	 * Student status.
	 */
	private BuildingBlock			designationBuildingBlock;

	/**
	 * Branch.
	 */
	private Branch					branch;

	/**
	 * 
	 * @param branch
	 */
	public EmployeeSearchCriteria(final Branch branch) {
		this.setBranch(branch);
	}

	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	/**
	 * @param dateOfBirth
	 *            the dateOfBirth to set
	 */
	public void setDateOfBirth(final Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetSeacrhCriteria() {
		this.dateOfBirth = null;
		this.name = null;
		this.employeeNumber = null;
		this.joiningDate = null;
		this.designationBuildingBlock = null;
		this.departmentBuildingBlock = null;
		this.employmentType = null;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSearchCriteriaIsEmpty() {
		return (this.name == null || this.name.isEmpty()) && (this.employeeNumber == null || this.employeeNumber.isEmpty()) && this.joiningDate == null
				&& this.designationBuildingBlock == null && this.departmentBuildingBlock == null && this.employmentType == null && this.dateOfBirth == null;
	}

	/**
	 * @return the branch
	 */
	@Override
	public Branch getBranch() {
		return this.branch;
	}

	/**
	 * @return the employeeNumber
	 */
	public String getEmployeeNumber() {
		return this.employeeNumber;
	}

	/**
	 * @param employeeNumber
	 *            the employeeNumber to set
	 */
	public void setEmployeeNumber(final String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	/**
	 * @return the joiningDate
	 */
	public Date getJoiningDate() {
		return this.joiningDate;
	}

	/**
	 * @param joiningDate
	 *            the joiningDate to set
	 */
	public void setJoiningDate(final Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	/**
	 * @return the employmentTypeConstant
	 */
	public EmploymentTypeConstant getEmploymentType() {
		return this.employmentType;
	}

	/**
	 * @param employmentTypeConstant
	 *            the employmentTypeConstant to set
	 */
	public void setEmploymentType(final EmploymentTypeConstant employmentType) {
		this.employmentType = employmentType;
	}

	/**
	 * @return the departmentBuildingBlock
	 */
	public BuildingBlock getDepartmentBuildingBlock() {
		return this.departmentBuildingBlock;
	}

	/**
	 * @param departmentBuildingBlock
	 *            the departmentBuildingBlock to set
	 */
	public void setDepartmentBuildingBlock(final BuildingBlock departmentBuildingBlock) {
		this.departmentBuildingBlock = departmentBuildingBlock;
	}

	/**
	 * @return the designationBuildingBlock
	 */
	public BuildingBlock getDesignationBuildingBlock() {
		return this.designationBuildingBlock;
	}

	/**
	 * @param designationBuildingBlock
	 *            the designationBuildingBlock to set
	 */
	public void setDesignationBuildingBlock(final BuildingBlock designationBuildingBlock) {
		this.designationBuildingBlock = designationBuildingBlock;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

}
