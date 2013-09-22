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
import javax.inject.Named;
import javax.validation.constraints.AssertTrue;

import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.Address;
import com.apeironsol.need.core.model.Country;
import com.apeironsol.need.core.service.CountryService;

@Named
@Scope(value = "session")
public class AddressBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long	serialVersionUID	= 1688607656529827991L;

	private static final Logger						log					= Logger.getLogger(AddressBean.class);

	private Address				address;

	private Collection<Country>	countries;

	private String				countryCode;

	@Autowired
	private CountryService		countryService;

	@PostConstruct
	public void initialize() {
		this.address = new Address();

		this.countries = this.countryService.getCountries();
	}

	@AssertTrue(message = "Atlease one phone number should is requried.")
	public boolean validateContactDetails() {
		if (this.address.getHomePhoneNr() != null || this.address.getOfficePhoneNr() != null
				|| this.address.getMobileNr() != null) {
			return false;
		}
		return true;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(final Address address) {
		this.address = address;
	}

	public Collection<Country> getCountries() {
		return this.countries;
	}

	public void setCountries(final Collection<Country> countries) {
		this.countries = countries;
	}

	@NotEmpty(message = "Country is mandatory.")
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	public void handelCountryChange() {
		log.info("handle country cange here");
	}

	public Country getCountry() {
		for (Country country : this.countries) {
			if (country.getIso3Code().equals(this.countryCode)) {
				return country;
			}
		}
		return null;
	}

}
