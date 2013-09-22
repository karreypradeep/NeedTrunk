/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.security.model;

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

/**
 * Entity class for user group authorization
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "USER_GROUP_AUTHORITY")
public class UserGroupAuthority extends BaseEntity implements Serializable {

	/**
	 * Universal version id for this class.
	 */
	private static final long	serialVersionUID	= 1776981919890737581L;

	@NotNull(message = "model.authority_is_mandatory")
	@Basic
	@Column(name = "AUTHORITY", nullable = false)
	@Enumerated(EnumType.STRING)
	private AuthorityConstant	authority;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "GROUP_ID", nullable = false)
	private UserGroup			userGroup;

	public UserGroup getUserGroup() {
		return this.userGroup;
	}

	public void setUserGroup(final UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public AuthorityConstant getAuthority() {
		return this.authority;
	}

	public void setAuthority(final AuthorityConstant authority) {
		this.authority = authority;
	}

}
