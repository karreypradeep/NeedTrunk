/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.framework;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Implementation class for {@link BaseDao}
 * 
 * @author Pradeep
 * 
 * @param <E>
 *            E defines the generic model entity type.
 */
public class BaseDaoImpl<E extends Serializable> implements BaseDao<E> {

	/**
	 * Entity manager.
	 */
	@PersistenceContext
	private EntityManager	entityManager;

	/**
	 * Entity.
	 */
	private final Class<E>	entity;

	@SuppressWarnings("unused")
	private Long			id;

	/**
	 * Default constructor.
	 */
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		final ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.entity = (Class<E>) parameterizedType.getActualTypeArguments()[0];

	}

	/**
	 * Returns entity manager.
	 * 
	 * @return entity manager.
	 */
	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	/**
	 * Returns entity.
	 * 
	 * @return entity.
	 */
	public Class<E> getEntity() {
		return this.entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E persist(final E e) {
		final E t = this.entityManager.merge(e);
		this.entityManager.persist(t);
		// this.entityManager.flush();
		return t;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E merge(final E e) {
		final E t = this.entityManager.merge(e);
		// this.entityManager.flush();
		return t;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(final E e) {
		final E t = this.merge(e);
		this.entityManager.remove(t);
		// this.entityManager.flush();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E findById(final Long id) {
		return this.entityManager.find(this.entity, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<E> findAll() {
		final Query query = this.entityManager.createQuery("SELECT e FROM " + this.entity.getName() + " e");
		return query.getResultList();

	}

	@Override
	public void flush() {
		this.entityManager.flush();

	}

}
