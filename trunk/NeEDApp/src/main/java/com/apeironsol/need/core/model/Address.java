/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

/**
 * Entity class for address
 * 
 * @author Pradeep
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "ADDRESS")
public class Address extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -2027863074161868181L;

	@NotEmpty(message = "model.address_is_mandatory")
	@Column(name = "ADDRESS", length = 200, nullable = false)
	private String				address;

	@NotEmpty(message = "model.city_is_mandatory")
	@Column(name = "CITY", length = 200, nullable = false)
	private String				city;

	@NotEmpty(message = "model.state_is_mandatory")
	@Column(name = "STATE", length = 200, nullable = false)
	private String				state;

	@NotNull(message = "model.country_is_mandatory")
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "COUNTRY_ID", nullable = false)
	private Country				country;

	@NotEmpty(message = "model.zipcode_is_mandatory")
	@Column(name = "ZIPCODE", length = 200, nullable = false)
	private String				zipCode;

	@Column(name = "EMAIL", length = 200)
	private String				email;

	@Column(name = "HOME_PHONE_NR", length = 20)
	private String				homePhoneNr;

	@Column(name = "OFFICE_PHONE_NR", length = 20)
	private String				officePhoneNr;

	@Column(name = "MOBILE_NR", length = 20)
	private String				mobileNr;

	@Column(name = "IS_PRIMARY", nullable = false)
	private boolean				primary;

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getState() {
		return this.state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(final Country country) {
		this.country = country;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(final String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getHomePhoneNr() {
		return this.homePhoneNr;
	}

	public void setHomePhoneNr(final String homePhoneNr) {
		this.homePhoneNr = homePhoneNr;
	}

	public String getOfficePhoneNr() {
		return this.officePhoneNr;
	}

	public void setOfficePhoneNr(final String officePhoneNr) {
		this.officePhoneNr = officePhoneNr;
	}

	public String getMobileNr() {
		return this.mobileNr;
	}

	public void setMobileNr(final String mobileNr) {
		this.mobileNr = mobileNr;
	}

	public boolean isPrimary() {
		return this.primary;
	}

	public void setPrimary(final boolean primary) {
		this.primary = primary;
	}

	public String getYesNoPrimary() {
		return this.primary ? "Yes" : "No";
	}

	public String getDisplayAddress() {
		return this.address + ", " + this.city;
	}

	public String getDisplayRegion() {
		return this.state + ", " + this.country.getName() + ", " + this.zipCode;
	}

	public String getContactNumber() {
		if (!StringUtils.isEmpty(this.mobileNr)) {
			return this.mobileNr;
		}

		if (!StringUtils.isEmpty(this.homePhoneNr)) {
			return this.homePhoneNr;
		}

		if (!StringUtils.isEmpty(this.officePhoneNr)) {
			return this.officePhoneNr;
		}

		return "";
	}

	public String getAllContactNumbers() {
		String contactNumbers = "";
		if (!StringUtils.isEmpty(this.mobileNr)) {
			contactNumbers = this.mobileNr;
		}

		if (!StringUtils.isEmpty(this.homePhoneNr)) {
			contactNumbers = !StringUtils.isEmpty(contactNumbers) ? contactNumbers + "/" + this.homePhoneNr : this.homePhoneNr;
		}

		if (!StringUtils.isEmpty(this.officePhoneNr)) {
			contactNumbers = !StringUtils.isEmpty(contactNumbers) ? contactNumbers + "/" + this.officePhoneNr : this.officePhoneNr;
		}

		return contactNumbers;
	}

}
