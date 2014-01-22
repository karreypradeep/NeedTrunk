/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.hostel.dao;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.hostel.model.HostelRoom;
import com.apeironsol.need.util.NonNull;

/**
 * Data access interface for employee salary entity.
 * 
 * @author Pradeep
 * 
 */
public interface HostelRoomDao extends BaseDao<HostelRoom> {

	/**
	 * Remove all employee ctc by employee id.
	 * 
	 * @param employeeId
	 *            employee id.
	 */
	Collection<HostelRoom> findAllHostelRooms(@NonNull final Long branchId);

	/**
	 * Remove all employee ctc by employee id.
	 * 
	 * @param employeeId
	 *            employee id.
	 */
	Collection<HostelRoom> findAllHostelRoomsWithAvailableStatus(@NonNull final Long branchId, @NonNull final Boolean available);

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
