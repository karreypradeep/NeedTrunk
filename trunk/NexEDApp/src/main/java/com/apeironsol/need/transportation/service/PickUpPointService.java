/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.service;

import java.util.Collection;
import java.util.List;

import com.apeironsol.need.transportation.model.PickUpPoint;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service Interface for pickup point. This service act as controller for
 * pickup point details.
 * 
 * @author Sandeep
 * 
 */
public interface PickUpPointService {

	/**
	 * Retrieves the pickup point by id.
	 * 
	 * @param id
	 *            the id of pickup point to be retrieved.
	 * @return
	 */
	PickUpPoint findPickUpPointById(Long id) throws BusinessException;

	/**
	 * Retrieves all pickup points available.
	 * 
	 * @return all pickup points available.
	 */
	Collection<PickUpPoint> findAllPickUpPoints() throws BusinessException;

	/**
	 * Saves the pickup point.
	 * 
	 * @return the saved pickup point.
	 */
	PickUpPoint savePickUpPoint(PickUpPoint pickUpPoint) throws BusinessException;

	/**
	 * Deletes the pickup point.
	 * 
	 */
	void deletePickUpPoint(final Long id) throws BusinessException;

	/**
	 * 
	 * @param name
	 * @param code
	 * @return
	 * @throws BusinessException
	 */
	Collection<PickUpPoint> findPickUpPointsByNameOrCode(final String name, final String code) throws BusinessException;

	/**
	 * Retrieves all pickup points for branch.
	 * 
	 * @return all pickup points for branch.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	List<PickUpPoint> findPickUpPointsByBranchId(final Long branchId) throws BusinessException;

	List<PickUpPoint> findActivePickUpPointsByBranchId(final Long branchId) throws BusinessException;

	PickUpPoint activatePickUpPoint(final PickUpPoint pickUpPoint) throws BusinessException;

	PickUpPoint deactivatePickUpPoint(final PickUpPoint pickUpPoint) throws BusinessException;
}
