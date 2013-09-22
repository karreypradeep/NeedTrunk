/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.security.model;

/**
 * class for security role
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

import com.apeironsol.need.util.constants.AccessControlTypeConstant;
import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "ACCESS_CONTROL")
public class AccessControl extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long			serialVersionUID	= -927045001321641282L;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "USER_ACCOUNT_ID", nullable = false)
	private UserAccount					userAccount;

	@Column(name = "REF_ID", nullable = false)
	private Long						refId;

	@Basic
	@Column(name = "ACCESS_CONTROL_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private AccessControlTypeConstant	accessControlType;

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public Long getRefId() {
		return this.refId;
	}

	public void setRefId(final Long refId) {
		this.refId = refId;
	}

	public AccessControlTypeConstant getAccessControlType() {
		return this.accessControlType;
	}

	public void setAccessControlType(final AccessControlTypeConstant accessControlType) {
		this.accessControlType = accessControlType;
	}

}
