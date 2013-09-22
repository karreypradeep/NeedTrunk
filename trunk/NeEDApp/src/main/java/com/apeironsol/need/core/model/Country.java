/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

/**
 * class for country table
 * 
 * @author Pradeep
 */
import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "COUNTRY")
public class Country implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= 6854911159338676671L;

	@Id
	@Column(name = "ISO3_CODE", length = 3)
	private String				iso3Code;

	@Column(name = "ISO2_CODE", length = 2)
	private String				iso2Code;

	@Column(name = "NAME")
	private String				name;

	@Column(name = "NUM_CODE")
	private int					numCode;

	@OneToMany(mappedBy = "country")
	private Collection<State>	states;

	public String getIso3Code() {
		return this.iso3Code;
	}

	public void setIso3Code(final String iso3Code) {
		this.iso3Code = iso3Code;
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

	public int getNumCode() {
		return this.numCode;
	}

	public void setNumCode(final int numCode) {
		this.numCode = numCode;
	}

	public Collection<State> getStates() {
		return this.states;
	}

	public void setStates(final Collection<State> states) {
		this.states = states;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.iso3Code == null) ? 0 : this.iso3Code.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Country other = (Country) obj;
		if (this.iso3Code == null) {
			if (other.iso3Code != null) {
				return false;
			}
		} else if (!this.iso3Code.equals(other.iso3Code)) {
			return false;
		}
		return true;
	}

}
