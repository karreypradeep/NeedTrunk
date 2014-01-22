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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.hostel.dao.HostelRoomDao;
import com.apeironsol.need.hostel.model.HostelRoom;
import com.apeironsol.need.hostel.model.StudentAcademicYearHostelRoom;

/**
 * Service implementation interface for student.
 * 
 * @author pradeep
 * 
 */
@Service("hostelRoomService")
@Transactional(rollbackFor = Exception.class)
public class HostelRoomServiceImpl implements HostelRoomService {

	@Resource
	private HostelRoomDao							hostelRoomDao;

	@Resource
	private StudentAcademicYearHostelRoomService	studentAcademicYearHostelRoomService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HostelRoom saveHostelRoom(final HostelRoom hostelRoom) {
		if (hostelRoom.getId() != null) {
			Collection<StudentAcademicYearHostelRoom> studentAcademicYearHostelRooms = this.studentAcademicYearHostelRoomService
					.findStudentAcademicYearHostelRoomsByRoomId(hostelRoom.getId());
			if (studentAcademicYearHostelRooms != null) {
				for (StudentAcademicYearHostelRoom studentAcademicYearHostelRoom : studentAcademicYearHostelRooms) {
					if (studentAcademicYearHostelRoom.getStartDate().before(hostelRoom.getStartDate())) {
						throw new BusinessException("Hostel room start date is before hostel room allocated date "
								+ studentAcademicYearHostelRoom.getStartDate() + " to student "
								+ studentAcademicYearHostelRoom.getStudentAcademicYear().getStudent().getDisplayName() + ".");
					}
				}
			}

		}
		return this.hostelRoomDao.persist(hostelRoom);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeHostelRoom(final HostelRoom hostelRoom) {
		Collection<StudentAcademicYearHostelRoom> studentAcademicYearHostelRooms = this.studentAcademicYearHostelRoomService
				.findStudentAcademicYearHostelRoomsByRoomId(hostelRoom.getId());
		if (studentAcademicYearHostelRooms != null) {
			throw new BusinessException("Hostel room is allocated to students, so cannot be deleted.");
		}
		this.hostelRoomDao.remove(hostelRoom);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HostelRoom findHostelRoomById(final Long id) {
		return this.hostelRoomDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<HostelRoom> findAllHostelRooms(final Long branchId) {
		return this.hostelRoomDao.findAllHostelRooms(branchId);
	}

	@Override
	public Collection<HostelRoom> findAllHostelRoomsByTypeAndBetweenDates(final Long branchId, final Long buildingBlockId, final Date startDate,
			final Date endDate) throws BusinessException {
		return this.hostelRoomDao.findAllHostelRoomsByTypeAndBetweenDates(branchId, buildingBlockId, startDate, endDate);
	}

	@Override
	public Collection<HostelRoom> findAllHostelRoomsByType(final Long branchId, final Long buildingBlockId) throws BusinessException {
		return this.hostelRoomDao.findAllHostelRoomsByType(branchId, buildingBlockId);
	}
}
