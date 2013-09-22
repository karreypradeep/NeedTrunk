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
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.apeironsol.need.util.constants.UserAccountTypeConstant;
import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "USER_ACCOUNT")
public class UserAccount extends BaseEntity implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long			serialVersionUID	= 2324994579940286558L;

	@Column(name = "USERNAME", nullable = false, length = 40, unique = true)
	private String						username;

	@Column(name = "PASSWORD", nullable = false, length = 100)
	private String						password;

	@Column(name = "ACTIVE", nullable = false)
	private Boolean						active;

	@Column(name = "ACC_EXPIRED", nullable = false)
	private boolean						accountExpired;

	@Column(name = "CRDTLS_EXPIRED", nullable = false)
	private boolean						credentialsExpired;

	@Column(name = "ACC_LOCKED", nullable = false)
	private boolean						accountLocked;

	@OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<UserRole>		userRoles;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	private Collection<UserGroup>		userGroups;

	@OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<AccessControl>	accessControls;

	@Column(name = "USER_ACCOUNT_TYPE")
	@Enumerated(EnumType.STRING)
	private UserAccountTypeConstant		userAccountType;

	public Collection<AccessControl> getAccessControls() {
		return this.accessControls;
	}

	public void setAccessControls(final Collection<AccessControl> accessControls) {
		this.accessControls = accessControls;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setActive(final Boolean active) {
		this.active = active;
	}

	public Boolean isActive() {
		return this.active;
	}

	public boolean isAccountExpired() {
		return this.accountExpired;
	}

	public void setAccountExpired(final boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public boolean isCredentialsExpired() {
		return this.credentialsExpired;
	}

	public void setCredentialsExpired(final boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public boolean isAccountLocked() {
		return this.accountLocked;
	}

	public void setAccountLocked(final boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public Collection<UserGroup> getUserGroups() {
		return this.userGroups;
	}

	public void setUserGroups(final Collection<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public UserAccountTypeConstant getUserAccountType() {
		return this.userAccountType;
	}

	public void setUserAccountType(final UserAccountTypeConstant userAccountType) {
		this.userAccountType = userAccountType;
	}

	public Collection<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Collection<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

}
