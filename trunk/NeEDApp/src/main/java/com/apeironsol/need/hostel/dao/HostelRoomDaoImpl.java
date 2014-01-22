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

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.hostel.model.HostelRoom;
import com.apeironsol.need.util.NonNull;

/**
 * Data access interface for employee salary entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("hostelRoomDao")
public class HostelRoomDaoImpl extends BaseDaoImpl<HostelRoom> implements HostelRoomDao {

	@Override
	public Collection<HostelRoom> findAllHostelRooms(@NonNull final Long branchId) {
		TypedQuery<HostelRoom> query = this.getEntityManager().createQuery("select hr from HostelRoom hr where hr.branch.id  = :branchId", HostelRoom.class);
		query.setParameter("branchId", branchId);
		return query.getResultList();
	}

	@Override
	public Collection<HostelRoom> findAllHostelRoomsWithAvailableStatus(@NonNull final Long branchId, @NonNull final Boolean available) {
		TypedQuery<HostelRoom> query = this.getEntityManager().createQuery(
				"select hr from HostelRoom hr where hr.branch.id  = :branchId and hr.available  = :available", HostelRoom.class);
		query.setParameter("branchId", branchId);
		query.setParameter("available", available);
		return query.getResultList();
	}

	@Override
	public Collection<HostelRoom> findAllHostelRoomsByTypeAndBetweenDates(final Long branchId, final Long buildingBlockId, final Date startDate,
			final Date endDate) throws BusinessException {
		TypedQuery<HostelRoom> query = this
				.getEntityManager()
				.createQuery(
						"select hr from HostelRoom hr where hr.branch.id  = :branchId and hr.hostelRoomTypebuildingBlock.id = :buildingBlockId and hr.startDate >= :startDate and hr.startDate <= :endDate ",
						HostelRoom.class);
		query.setParameter("branchId", branchId);
		query.setParameter("buildingBlockId", buildingBlockId);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}

	@Override
	public Collection<HostelRoom> findAllHostelRoomsByType(final Long branchId, final Long buildingBlockId) throws BusinessException {
		TypedQuery<HostelRoom> query = this.getEntityManager().createQuery(
				"select hr from HostelRoom hr where hr.branch.id  = :branchId and hr.hostelRoomTypebuildingBlock.id = :buildingBlockId ", HostelRoom.class);
		query.setParameter("branchId", branchId);
		query.setParameter("buildingBlockId", buildingBlockId);
		return query.getResultList();
	}

}
