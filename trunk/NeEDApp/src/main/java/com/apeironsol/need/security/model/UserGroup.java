/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.security.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for user group
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "USER_GROUP")
public class UserGroup extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long				serialVersionUID	= -3402426923062071876L;

	@Column(name = "NAME", nullable = false, unique = true)
	private String							name;

	@ManyToMany(mappedBy = "userGroups")
	private Collection<UserAccount>			userAccounts;

	@OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<UserGroupAuthority>	userGroupAuthorities;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Collection<UserGroupAuthority> getUserGroupAuthorities() {
		return this.userGroupAuthorities;
	}

	public void setUserGroupAuthorities(final Collection<UserGroupAuthority> userGroupAuthorities) {
		this.userGroupAuthorities = userGroupAuthorities;
	}

	public Collection<UserAccount> getUserAccounts() {
		return this.userAccounts;
	}

	public void setUserAccounts(final Collection<UserAccount> userAccounts) {
		this.userAccounts = userAccounts;
	}
}
