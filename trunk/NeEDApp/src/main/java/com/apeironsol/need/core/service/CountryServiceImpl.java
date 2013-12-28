/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

/**
 * Service implementation interface for country.
 * This service act as controller for implementing all country and state
 * transactions.
 * 
 * @author pradeep
 * 
 */
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.CountryDao;
import com.apeironsol.need.core.model.Country;
import com.apeironsol.need.core.model.State;

@Service("countryService")
@Transactional(rollbackFor = Exception.class)
public class CountryServiceImpl implements CountryService {

	@Resource
	private CountryDao	countryDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Country> getCountries() {
		return this.countryDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getISO3CodesAsArray() {
		Collection<Country> countries = this.countryDao.findAll();
		String[] iso3Codes = new String[countries.size()];
		int i = 0;
		for (Country country : countries) {
			iso3Codes[i++] = country.getIso3Code();
		}
		return iso3Codes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<State> findStatesByIso3CountryCode(final String iso3CountryCode) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long[] getStateIdsAsArray(final String iso3CountryCode) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Country findCountryByIso3(final String iso3Code) {
		return this.countryDao.findCountryByIso3(iso3Code);
	}
}
