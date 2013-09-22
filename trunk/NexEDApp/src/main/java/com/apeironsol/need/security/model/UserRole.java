/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.security.model;

/**
 * Entity class for user accounts
 * 
 * @author Pradeep
 */
import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.util.constants.AuthorityConstant;
import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "USER_ROLE")
public class UserRole extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -6623470369053407980L;

	@NotNull(message = "model.authority_is_mandatory")
	@Basic
	@Column(name = "USER_ROLE", nullable = false)
	@Enumerated(EnumType.STRING)
	private AuthorityConstant	userRole;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ACCOUNT_ID", nullable = false)
	private UserAccount			userAccount;

	public AuthorityConstant getUserRole() {
		return userRole;
	}

	public void setUserRole(AuthorityConstant userRole) {
		this.userRole = userRole;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
