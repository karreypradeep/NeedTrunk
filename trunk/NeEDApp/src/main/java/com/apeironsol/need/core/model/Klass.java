/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for class
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "CLASS", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "CODE", "BRANCH_ID" }, name = "UQ_CLASS_CODE_BRANCH_ID"),
		@UniqueConstraint(columnNames = { "NAME", "BRANCH_ID" }, name = "UQ_CLASS_NAME_BRANCH_ID") })
public class Klass extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= 6528433009597872522L;

	@NotEmpty(message = "Class code is mandatory.")
	@Column(name = "CODE", length = 30, nullable = false)
	private String				code;

	@NotEmpty(message = "Class name is mandatory.")
	@Column(name = "NAME", length = 50, nullable = false)
	private String				name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch				branch;

	@OneToMany(mappedBy = "klass", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<Subject>	subjects;

	@OneToMany(mappedBy = "klass", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private Collection<Section>	sections;

	@NotNull(message = "model.active_flag_mandatory")
	@Column(name = "ACTIVE", nullable = false)
	private boolean				active;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public Collection<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(final Collection<Subject> subjects) {
		this.subjects = subjects;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(final boolean active) {
		this.active = active;
	}

	public Collection<Section> getSections() {
		return sections;
	}

	public void setSections(final Collection<Section> sections) {
		this.sections = sections;
	}
}
