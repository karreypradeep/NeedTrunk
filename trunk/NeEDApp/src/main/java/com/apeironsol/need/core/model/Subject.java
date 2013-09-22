/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotEmpty;

import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for SUBJECT.
 * 
 * @author Pradeep
 */

@Entity
@Table(name = "SUBJECT", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "NAME", "CLASS_ID" }, name = "UQ_SUBJECT_NAME_CLASS_ID"),
		@UniqueConstraint(columnNames = { "CODE", "CLASS_ID" }, name = "UQ_SUBJECT_CODE_CLASS_ID") })
public class Subject extends BaseEntity implements Serializable {

	/**
	 * Universal serial id for course class
	 */
	private static final long	serialVersionUID	= -2611016229811058856L;

	@NotEmpty(message = "Subject code is mandatory.")
	@Column(name = "CODE", length = 30, nullable = false)
	private String				code;

	@NotEmpty(message = "Subject name is mandatory.")
	@Column(name = "NAME", length = 50, nullable = false)
	private String				name;

	@Column(name = "ELECTIVE_IND")
	private boolean				elective;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLASS_ID", nullable = false)
	private Klass				klass;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public Klass getKlass() {
		return klass;
	}

	public void setKlass(final Klass klass) {
		this.klass = klass;
	}

	public boolean isElective() {
		return elective;
	}

	public void setElective(final boolean elective) {
		this.elective = elective;
	}
}
