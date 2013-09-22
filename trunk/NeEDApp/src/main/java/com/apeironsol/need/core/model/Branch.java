/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

/**
 * class for Branch.
 * 
 * @author Sunny
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.apeironsol.need.util.constants.BranchTypeConstant;
import com.apeironsol.need.util.constants.RegistrationTypeConstant;
import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "BRANCH")
public class Branch extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class
	 */
	private static final long			serialVersionUID	= -7680904906984285622L;

	@NotEmpty(message = "model.branch_name_mandatory")
	@Column(name = "NAME", nullable = false, length = 100, unique = true)
	private String						name;

	@NotEmpty(message = "model.branch_code_mandatory")
	@Size(message = "model.branch_code_max_size", max = 10)
	@Column(name = "CODE", nullable = false, length = 10, unique = true)
	private String						code;

	@NotNull(message = "model.branch_start_date_code_mandatory")
	@Column(name = "START_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date						startDate;

	@NotEmpty(message = "model.recognised_by_mandatory")
	@Column(name = "RECOGNISED_BY", nullable = false, length = 100)
	private String						recognisedBy;

	@Column(name = "AFFILIATED_TO", length = 100)
	private String						affiliatedTo;

	@NotNull(message = "model.branch_registration_type_mandatory")
	@Basic
	@Column(name = "REGISTRATION_TYPE", length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	private RegistrationTypeConstant	registrationTypeConstant;

	@NotNull(message = "model.registration_date_code_mandatory")
	@Column(name = "REGISTRATION_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date						registrationDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGANIZATION_ID", nullable = false)
	private Organization				organization;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "ADDRESS_ID")
	private Address						address;

	@Column(name = "ACTIVE")
	private boolean						active;

	@NotNull(message = "model.branch_type_mandatory")
	@Basic
	@Column(name = "BRANCH_TYPE", length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	private BranchTypeConstant			branchTypeConstant;

	/**
	 * @return the branchTypeConstant
	 */
	public BranchTypeConstant getBranchTypeConstant() {
		return this.branchTypeConstant;
	}

	/**
	 * @param branchTypeConstant
	 *            the branchTypeConstant to set
	 */
	public void setBranchTypeConstant(final BranchTypeConstant branchTypeConstant) {
		this.branchTypeConstant = branchTypeConstant;
	}

	/**
	 * Returns the name of the branch.
	 * 
	 * @return the name of the branch.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of the branch.
	 * 
	 * @param name
	 *            the name.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Returns the organization to which the branch belongs.
	 * 
	 * @return the organization to which the branch belongs.
	 */
	public Organization getOrganization() {
		return this.organization;
	}

	/**
	 * Sets the organization to which the branch belongs.
	 * 
	 * @param organization
	 *            the organization.
	 */
	public void setOrganization(final Organization organization) {
		this.organization = organization;
	}

	/**
	 * Returns the code of the branch.
	 * 
	 * @return the code of the branch.
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * Sets the code of the branch.
	 * 
	 * @param code
	 *            the code.
	 */
	public void setCode(final String code) {
		this.code = code;
	}

	/**
	 * Returns the start date of the branch.
	 * 
	 * @return the start date of the branch.
	 */
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * Sets the start date of the branch.
	 * 
	 * @param startDate
	 *            the start date.
	 */
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Returns the organization which recognised the branch.
	 * 
	 * @return the organization which recognised the branch.
	 */
	public String getRecognisedBy() {
		return this.recognisedBy;
	}

	/**
	 * Sets the organization which recognised the branch.
	 * 
	 * @param recognisedBy
	 *            the organization which recognised the branch.
	 */
	public void setRecognisedBy(final String recognisedBy) {
		this.recognisedBy = recognisedBy;
	}

	/**
	 * Returns the organization to which the branch is affiliated to.
	 * 
	 * @return the organization to which the branch is affiliated to.
	 */
	public String getAffiliatedTo() {
		return this.affiliatedTo;
	}

	/**
	 * Sets the organization to which the branch is affiliated to.
	 * 
	 * @param affiliatedTo
	 *            the organization to which the branch is affiliated to.
	 */
	public void setAffiliatedTo(final String affiliatedTo) {
		this.affiliatedTo = affiliatedTo;
	}

	/**
	 * Returns the registration type of the branch.
	 * 
	 * @return the registration type of the branch.
	 */
	public RegistrationTypeConstant getRegistrationTypeConstant() {
		return this.registrationTypeConstant;
	}

	/**
	 * Sets the registration type of the branch.
	 * 
	 * @param registrationTypeConstant
	 *            the registration type of the branch.
	 */
	public void setRegistrationTypeConstant(final RegistrationTypeConstant registrationTypeConstant) {
		this.registrationTypeConstant = registrationTypeConstant;
	}

	/**
	 * Returns the date on which branch got registered.
	 * 
	 * @return the date on which branch got registered.
	 */
	public Date getRegistrationDate() {
		return this.registrationDate;
	}

	/**
	 * Sets the registration date on which branch got registered.
	 * 
	 * @param registrationDate
	 *            the registration date on which branch got registered.
	 */
	public void setRegistrationDate(final Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(final Address address) {
		this.address = address;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(final boolean active) {
		this.active = active;
	}

}