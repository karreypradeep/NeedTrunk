/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

/**
 * class for user
 * 
 * @author Pradeep
 */
import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import com.apeironsol.need.security.model.UserAccount;
import com.apeironsol.need.util.constants.GenderConstant;
import com.apeironsol.framework.BaseEntity;

@MappedSuperclass
public class Person extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class
	 */
	private static final long	serialVersionUID	= 5617540542297994036L;

	@NotEmpty(message = "First name is mandatory.")
	@Column(name = "FIRST_NAME", nullable = false)
	private String				firstName;

	@NotEmpty(message = "Last name is mandatory.")
	@Column(name = "LAST_NAME", nullable = false)
	private String				lastName;

	@Column(name = "MIDDLE_NAME")
	private String				middleName;

	@NotNull(message = "Gender is mandatory.")
	@Basic
	@Column(name = "GENDER", length = 10, nullable = false)
	@Enumerated(EnumType.STRING)
	private GenderConstant		gender;

	@OneToOne
	@JoinColumn(name = "USER_ACCOUNT_ID")
	private UserAccount			userAccount;

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	public GenderConstant getGender() {
		return this.gender;
	}

	public void setGender(final GenderConstant gender) {
		this.gender = gender;
	}

	public String getDisplayName() {
		if (StringUtils.isEmpty(this.firstName) && StringUtils.isEmpty(this.lastName)) {
			return "";
		} else if (StringUtils.isEmpty(this.middleName)) {
			return this.firstName + " " + this.lastName;
		} else {
			return this.firstName + " " + this.middleName + " " + this.lastName;

		}
	}

	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
