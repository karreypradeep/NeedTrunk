/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

/**
 * class for state
 * 
 * @author Pradeep
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "STATE")
public class State implements Serializable {

	/**
	 * Unique universal id for this class.
	 */
	private static final long	serialVersionUID	= 2825822121611108402L;

	@Id
	@Column(name = "ID", length = 3)
	private Long				id;

	@Column(name = "ISO2_CODE", nullable = false)
	private String				iso2Code;

	@Column(name = "NAME", nullable = false)
	private String				name;

	@Column(name = "PRINTABLE_NAME", nullable = false)
	private String				printableName;

	@ManyToOne
	@JoinColumn(name = "COUNTRY_ID", nullable = false)
	private Country				country;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getIso2Code() {
		return this.iso2Code;
	}

	public void setIso2Code(final String iso2Code) {
		this.iso2Code = iso2Code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPrintableName() {
		return this.printableName;
	}

	public void setPrintableName(final String printableName) {
		this.printableName = printableName;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(final Country country) {
		this.country = country;
	}

}
