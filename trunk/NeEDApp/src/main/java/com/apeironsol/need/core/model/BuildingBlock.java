/**
 * SMSystem.
 * ' * This document is a part of the source code and related artifacts for
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.constants.EmployeeCTCCategoryTypeConstant;
import com.apeironsol.need.util.constants.EmployeeCTCDefinitionTypeConstant;
import com.apeironsol.need.util.constants.FeeClassificationLevelConstant;
import com.apeironsol.need.util.constants.FeeTypeConstant;
import com.apeironsol.need.util.constants.SalaryDeductionTypeConstant;
import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for Building block.
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "BUILDING_BLOCK", uniqueConstraints = { @UniqueConstraint(columnNames = { "TYPE", "CODE" }), @UniqueConstraint(columnNames = { "NAME", "CODE" }) })
public class BuildingBlock extends BaseEntity implements Serializable {

	/**
	 * Universal serial version number.
	 */
	private static final long					serialVersionUID	= 3727609172287810392L;

	@NotEmpty(message = "model.name_mandatory")
	@Column(name = "NAME", length = 50, nullable = false)
	private String								name;

	@NotEmpty(message = "model.code_mandatory")
	@Column(name = "CODE", length = 50, nullable = false)
	private String								code;

	@NotNull(message = "model.building_block_type_mandatory")
	@Basic
	@Column(name = "TYPE", length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	private BuildingBlockConstant				type;

	@Basic
	@Column(name = "FEE_TYPE", length = 50)
	@Enumerated(EnumType.STRING)
	private FeeTypeConstant						feeType;

	@Basic
	@Column(name = "FEE_CLASSFN_LEVEL", length = 50)
	@Enumerated(EnumType.STRING)
	private FeeClassificationLevelConstant		feeClassificationLevel;

	@Column(name = "MANDATORY", nullable = false)
	private boolean								mandatory;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "BRANCH_ASSEMBLY", joinColumns = @JoinColumn(name = "BUILDING_BLOCK_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "BRANCH_ID", referencedColumnName = "ID"))
	private Collection<Branch>					branchs;

	@Basic
	@Column(name = "EMP_CTC_DEF_TYPE", length = 50)
	@Enumerated(EnumType.STRING)
	private EmployeeCTCDefinitionTypeConstant	ctcDefinitionType;

	@Basic
	@Column(name = "EMP_CTC_CAT_TYPE", length = 50)
	@Enumerated(EnumType.STRING)
	private EmployeeCTCCategoryTypeConstant		ctcCategoryType;

	@Basic
	@Column(name = "SALARU_DEDUC_TYPE", length = 50)
	@Enumerated(EnumType.STRING)
	private SalaryDeductionTypeConstant			salaryDeduction;

	@Column(name = "NEW_ADMISS_FEE")
	private boolean								newAdmissionFee;

	/**
	 * @return the ctcCategoryType
	 */
	public EmployeeCTCCategoryTypeConstant getCtcCategoryType() {
		return this.ctcCategoryType;
	}

	/**
	 * @param ctcCategoryType
	 *            the ctcCategoryType to set
	 */
	public void setCtcCategoryType(final EmployeeCTCCategoryTypeConstant ctcCategoryType) {
		this.ctcCategoryType = ctcCategoryType;
	}

	/**
	 * Returns name of building block.
	 * 
	 * @return name of building block.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets name of building block.
	 * 
	 * @param name
	 *            name of building block.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Return code of building block.
	 * 
	 * @return code of building block
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * Sets code of building block.
	 * 
	 * @param code
	 *            code of building block.
	 */
	public void setCode(final String code) {
		this.code = code;
	}

	/**
	 * Returns fee type for building block type constant Fee.
	 * 
	 * @return fee type for building block type constant Fee.
	 */
	public FeeTypeConstant getFeeType() {
		return this.feeType;
	}

	/**
	 * Sets fee type for building block type constant Fee.
	 * 
	 * @param feeType
	 *            fee type for building block type constant Fee.
	 */
	public void setFeeType(final FeeTypeConstant feeType) {
		this.feeType = feeType;
	}

	/**
	 * Returns building block type.
	 * 
	 * @return building block type.
	 */
	public BuildingBlockConstant getType() {
		return this.type;
	}

	/**
	 * Sets building block type.
	 * 
	 * @param type
	 *            building block type.
	 */
	public void setType(final BuildingBlockConstant type) {
		this.type = type;
	}

	/**
	 * Returns true if building block is mandatory.
	 * 
	 * @return true if building block is mandatory.
	 */
	public boolean isMandatory() {
		return this.mandatory;
	}

	/**
	 * Sets true if building block is mandatory.
	 * 
	 * @param mandatory
	 *            true if building block is mandatory.
	 */
	public void setMandatory(final boolean mandatory) {
		this.mandatory = mandatory;
	}

	/**
	 * Returns collections of branches where the building block is used.
	 * 
	 * @return collections of branches where the building block is used.
	 */
	public Collection<Branch> getBranchs() {
		return this.branchs;
	}

	/**
	 * Sets collections of branches where the building block is used.
	 * 
	 * @param branchs
	 *            collections of branches where the building block is used.
	 */
	public void setBranchs(final Collection<Branch> branchs) {
		this.branchs = branchs;
	}

	/**
	 * @return the feeClassificationLevel
	 */
	public FeeClassificationLevelConstant getFeeClassificationLevel() {
		return this.feeClassificationLevel;
	}

	/**
	 * @param feeClassificationLevel
	 *            the feeClassificationLevel to set
	 */
	public void setFeeClassificationLevel(final FeeClassificationLevelConstant feeClassificationLevel) {
		this.feeClassificationLevel = feeClassificationLevel;
	}

	/**
	 * @return the ctcDefinitionType
	 */
	public EmployeeCTCDefinitionTypeConstant getCtcDefinitionType() {
		return this.ctcDefinitionType;
	}

	/**
	 * @param ctcDefinitionType
	 *            the ctcDefinitionType to set
	 */
	public void setCtcDefinitionType(final EmployeeCTCDefinitionTypeConstant ctcDefinitionType) {
		this.ctcDefinitionType = ctcDefinitionType;
	}

	/**
	 * @return the salaryDeduction
	 */
	public SalaryDeductionTypeConstant getSalaryDeduction() {
		return this.salaryDeduction;
	}

	/**
	 * @param salaryDeduction
	 *            the salaryDeduction to set
	 */
	public void setSalaryDeduction(final SalaryDeductionTypeConstant salaryDeduction) {
		this.salaryDeduction = salaryDeduction;
	}

	/**
	 * @return the newAdmissionFee
	 */
	public boolean isNewAdmissionFee() {
		return this.newAdmissionFee;
	}

	/**
	 * @param newAdmissionFee
	 *            the newAdmissionFee to set
	 */
	public void setNewAdmissionFee(final boolean newAdmissionFee) {
		this.newAdmissionFee = newAdmissionFee;
	}

}
