/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.portal;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.Country;
import com.apeironsol.need.core.service.CountryService;

@Named
@Scope(value = "application")
public class CountryBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long	serialVersionUID	= 907806055249585810L;

	@Resource
	CountryService				countryService;

	private Collection<Country>	countries;

	@PostConstruct
	public void init() {
		this.countries = this.countryService.getCountries();
	}

	public Collection<Country> getCountries() {
		return this.countries;
	}

	public void setCountries(final Collection<Country> countries) {
		this.countries = countries;
	}

	public Country getCountryByIso3Code(final String iso3code) {
		for (Country country : this.countries) {
			if (country.getIso3Code().equals(iso3code)) {
				return country;
			}
		}
		return null;
	}

	public void loadCountries() {
		this.countries = this.countryService.getCountries();
	}
}
