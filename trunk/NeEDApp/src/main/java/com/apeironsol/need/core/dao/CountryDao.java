/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import com.apeironsol.need.core.model.Country;

/**
 * Data access interface for country entity.
 * 
 * @author Pradeep
 * 
 */
public interface CountryDao {

	/**
	 * Returns all countries.
	 * 
	 * @return collection of all countries.
	 */
	Collection<Country> findAll();

	/**
	 * Returns country by iso3 code.
	 * 
	 * @param iso3Code
	 *            iso3 code.
	 * @return country by iso3 code.
	 */
	Country findCountryByIso3(String iso3Code);

}
