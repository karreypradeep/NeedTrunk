/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.need.core.model.Country;
import com.apeironsol.need.core.model.State;

/**
 * Service interface for country.
 * This service act as controller for all country and state transactions.
 * 
 * @author pradeep
 * 
 */
public interface CountryService {

	/**
	 * Retrieve all countries.
	 * 
	 * @return collection of all countries.
	 */
	Collection<Country> getCountries();

	/**
	 * Retrieve ISO3 codes as string array.
	 * 
	 * @return array of ISO3 codes.
	 */
	String[] getISO3CodesAsArray();

	/**
	 * Find country by ISO3 code.
	 * 
	 * @param iso3Code
	 *            ISO3code.
	 * @return country by ISO3 code.
	 */
	Country findCountryByIso3(String iso3Code);

	/**
	 * Find states by ISO3 country code.
	 * 
	 * @param iso3Code
	 *            ISO3code.
	 * @return collection of states by ISO3 country code.
	 */
	Collection<State> findStatesByIso3CountryCode(String iso3Code);

	/**
	 * Retrieve state id's as array by country ISO3 code.
	 * 
	 * @param iso3Code
	 *            ISO3code.
	 * @return array of state id's by country ISO3 code.
	 */
	Long[] getStateIdsAsArray(String iso3Code);
}
