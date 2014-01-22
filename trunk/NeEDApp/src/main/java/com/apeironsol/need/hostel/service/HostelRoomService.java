/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.hostel.service;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.hostel.model.HostelRoom;

/**
 * Service interface for HostelRoom.
 * 
 * @author Sunny
 * 
 */
public interface HostelRoomService {

	/**
	 * Save HostelRoom.
	 * 
	 * @param HostelRoom
	 *            hostelRoom to be saved.
	 * @return persisted HostelRoom.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	HostelRoom saveHostelRoom(HostelRoom hostelRoom) throws BusinessException;

	/**
	 * Delete HostelRoom.
	 * 
	 * @param HostelRoom
	 *            hostelRoom to be deleted.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeHostelRoom(HostelRoom hostelRoom) throws BusinessException;

	/**
	 * Find HostelRoom by Id.
	 * 
	 * @param id
	 *            HostelRoom Id.
	 * @return HostelRoom with supplied Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	HostelRoom findHostelRoomById(Long id) throws BusinessException;

	/**
	 * Find all HostelRooms.
	 * 
	 * @return collections of all HostelRooms
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<HostelRoom> findAllHostelRooms(Long branchId) throws BusinessException;

	/**
	 * Find all HostelRooms.
	 * 
	 * @return collections of all HostelRooms
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<HostelRoom> findAllHostelRoomsByTypeAndBetweenDates(Long branchId, Long buildingBlockId, Date startDate, Date endDate) throws BusinessException;

	/**
	 * Find all HostelRooms.
	 * 
	 * @return collections of all HostelRooms
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<HostelRoom> findAllHostelRoomsByType(Long branchId, Long buildingBlockId) throws BusinessException;

}
