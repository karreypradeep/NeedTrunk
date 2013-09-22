/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.framework;

import java.io.Serializable;
import java.util.List;

/**
 * This class is the base interface for all data access objects
 * All data access objects should implement this interface.
 * 
 * @author Pradeep
 * 
 * @param <E>
 *            E defines the generic model entity type.
 */
public interface BaseDao<E extends Serializable> {

	/**
	 * Persist the entity.
	 * 
	 * @param e
	 *            entity to be saved.
	 * @return saved entity.
	 */
	E persist(E e);

	/**
	 * Merges the entity.
	 * 
	 * @param e
	 *            the entity to be merged.
	 * @return the entity merged.
	 */
	E merge(E e);

	/**
	 * Removes entity.
	 * 
	 * @param e
	 *            entity to be removed.
	 */
	void remove(E e);

	/**
	 * Find entity by object id.
	 * 
	 * @param id
	 *            object id of entity.
	 * @return entity with object id as id.
	 */
	E findById(Long id);

	/**
	 * Find all the entities.
	 * 
	 * @return collection of all entities.
	 */
	List<E> findAll();

	/**
	 * Flush entity manager
	 */
	void flush();

}
