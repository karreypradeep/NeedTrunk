/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.Country;

/**
 * Data access interface for country entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("countryDao")
public class CountryDaoImpl implements CountryDao {

	@PersistenceContext
	private EntityManager	entityManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Country> findAll() {
		TypedQuery<Country> query = entityManager.createQuery("select c from Country c", Country.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Country findCountryByIso3(final String iso3Code) {
		TypedQuery<Country> query = entityManager.createQuery("select c from Country c where c.iso3Code = :iso3Code",
				Country.class);
		query.setParameter("iso3Code", iso3Code);
		return query.getSingleResult();
	}

}
