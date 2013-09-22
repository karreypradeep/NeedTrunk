/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

/**
 * class for organization
 * 
 * @author Pradeep
 */
import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.apeironsol.need.util.constants.RegistrationTypeConstant;
import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "ORGANIZATION")
public class Organization extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long			serialVersionUID	= -1990368921588500463L;

	@NotEmpty(message = "model.organization_name_mandatory")
	@Column(name = "NAME", nullable = false, length = 100, unique = true)
	private String						name;

	@NotNull(message = "model.registration_type_mandatory")
	@Basic
	@Column(name = "REGISTRATION_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private RegistrationTypeConstant	registrationType;

	@NotNull(message = "model.locked_mandatory")
	@Column(name = "LOCKED")
	private boolean						locked;

	public String getName() {
		return name;
	}

	public void setName(final String orgName) {
		name = orgName;
	}

	public RegistrationTypeConstant getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(final RegistrationTypeConstant registrationType) {
		this.registrationType = registrationType;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(final boolean locked) {
		this.locked = locked;
	}

}
