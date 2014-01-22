/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.hostel.service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.hostel.dao.StudentAcademicYearHostelRoomDao;
import com.apeironsol.need.hostel.model.HostelRoom;
import com.apeironsol.need.hostel.model.StudentAcademicYearHostelRoom;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.NonNull;

/**
 * Service implementation interface for student.
 * 
 * @author pradeep
 * 
 */
@Service("studentAcademicYearHostelRoomService")
@Transactional(rollbackFor = Exception.class)
public class StudentAcademicYearHostelRoomServiceImpl implements StudentAcademicYearHostelRoomService {

	@Resource
	private StudentAcademicYearHostelRoomDao	studentAcademicYearHostelRoomDao;

	@Resource
	private HostelRoomService					hostelRoomService;

	@Override
	public StudentAcademicYearHostelRoom saveStudentAcademicYearHostelRoom(final StudentAcademicYearHostelRoom studentAcademicYearHostelRoom)
			throws BusinessException {
		StudentAcademicYearHostelRoom result = null;
		if (studentAcademicYearHostelRoom.getEndDate() == null) {
			StudentAcademicYearHostelRoom currentStudentAcademicYearHostelRoom = this
					.findCurrentOccupiedStudentAcademicYearHostelRoomForStudent(studentAcademicYearHostelRoom.getStudentAcademicYear().getId());
			// Current allocated room be different to new allocated room.
			if (currentStudentAcademicYearHostelRoom != null
					&& !currentStudentAcademicYearHostelRoom.getHostelRoom().getId().equals(studentAcademicYearHostelRoom.getHostelRoom().getId())) {
				this.validateStudentAcademicYearHostelRoom(studentAcademicYearHostelRoom);
				Calendar cal = Calendar.getInstance();
				cal.setTime(studentAcademicYearHostelRoom.getStartDate());
				cal.add(Calendar.DATE, -1);
				currentStudentAcademicYearHostelRoom.setEndDate(cal.getTime());
				this.studentAcademicYearHostelRoomDao.persist(currentStudentAcademicYearHostelRoom);
			}
			result = this.studentAcademicYearHostelRoomDao.persist(studentAcademicYearHostelRoom);
			// If current allocated room is different then increase beds
			// occupied in hostel room.
			if (!currentStudentAcademicYearHostelRoom.getHostelRoom().getId().equals(studentAcademicYearHostelRoom.getHostelRoom().getId())) {
				HostelRoom hostelRoom = this.hostelRoomService.findHostelRoomById(result.getHostelRoom().getId());
				hostelRoom.setBedsOccupied((hostelRoom.getBedsOccupied() != null ? hostelRoom.getBedsOccupied() : 0) + 1);
				this.hostelRoomService.saveHostelRoom(hostelRoom);
			}
		} else {
			result = this.studentAcademicYearHostelRoomDao.persist(studentAcademicYearHostelRoom);
			HostelRoom hostelRoom = this.hostelRoomService.findHostelRoomById(result.getHostelRoom().getId());
			hostelRoom.setBedsOccupied(hostelRoom.getBedsOccupied() - 1);
			this.hostelRoomService.saveHostelRoom(hostelRoom);
		}
		return result;
	}

	/**
	 * Validates if student can be allocated to hostel room.
	 * 
	 * @param studentAcademicYearHostelRoom
	 */
	private void validateStudentAcademicYearHostelRoom(final StudentAcademicYearHostelRoom studentAcademicYearHostelRoom) {
		if (studentAcademicYearHostelRoom.getHostelRoom().getBedsOccupied() != null
				&& studentAcademicYearHostelRoom.getHostelRoom().getTotalNumberOfBeds() - studentAcademicYearHostelRoom.getHostelRoom().getBedsOccupied() <= 0) {
			throw new BusinessException("There are no beds available in room " + studentAcademicYearHostelRoom.getHostelRoom().getRoomnNumber());
		}
		StudentAcademicYearHostelRoom studentAcademicYearHostelRoomforSamePeriod = this.findStudentAcademicYearHostelRoomForStudentForDate(
				studentAcademicYearHostelRoom.getStudentAcademicYear().getId(), studentAcademicYearHostelRoom.getStartDate());
		if (studentAcademicYearHostelRoomforSamePeriod != null) {
			throw new BusinessException(studentAcademicYearHostelRoom.getStudentAcademicYear().getStudent().getDisplayName() + " was admitted to hostel room "
					+ studentAcademicYearHostelRoomforSamePeriod.getHostelRoom().getRoomnNumber() + " during period "
					+ DateUtil.getDateForDisplayFormat(studentAcademicYearHostelRoomforSamePeriod.getStartDate()) + " - "
					+ studentAcademicYearHostelRoomforSamePeriod.getEndDate() + " . Please check start date.");
		}
	}

	@Override
	public void removeStudentAcademicYearHostelRoom(final StudentAcademicYearHostelRoom studentAcademicYearHostelRoom) throws BusinessException {
		this.studentAcademicYearHostelRoomDao.remove(studentAcademicYearHostelRoom);
	}

	@Override
	public StudentAcademicYearHostelRoom findStudentAcademicYearHostelRoomById(final Long id) throws BusinessException {
		return this.studentAcademicYearHostelRoomDao.findById(id);
	}

	@Override
	public Collection<StudentAcademicYearHostelRoom> findHostelRoomOccupantsForAcademicYear(@NonNull final Long roomId, @NonNull final Long academicYearId) {
		return this.studentAcademicYearHostelRoomDao.findHostelRoomOccupantsForAcademicYear(roomId, academicYearId);
	}

	@Override
	public Collection<StudentAcademicYearHostelRoom> findAllHostelRoomsByStudentAcademicYear(@NonNull final Long studentAcademicYearId) {
		return this.studentAcademicYearHostelRoomDao.findAllHostelRoomsByStudentAcademicYear(studentAcademicYearId);
	}

	@Override
	public Collection<StudentAcademicYearHostelRoom> findStudentAcademicYearHostelRoomsByRoomId(@NonNull final Long roomId) {
		return this.studentAcademicYearHostelRoomDao.findStudentAcademicYearHostelRoomsByRoomId(roomId);
	}

	@Override
	public Collection<StudentAcademicYearHostelRoom> findCurrentOccupantsForHostelRoom(@NonNull final Long roomId) {
		return this.studentAcademicYearHostelRoomDao.findCurrentOccupantsForHostelRoom(roomId);
	}

	@Override
	public StudentAcademicYearHostelRoom findStudentAcademicYearHostelRoomForStudentForDate(@NonNull final Long studentAcademicYearId,
			@NonNull final Date startDate) {
		return this.studentAcademicYearHostelRoomDao.findStudentAcademicYearHostelRoomForStudentForDate(studentAcademicYearId, startDate);
	}

	@Override
	public StudentAcademicYearHostelRoom findCurrentOccupiedStudentAcademicYearHostelRoomForStudent(@NonNull final Long studentAcademicYearId) {
		return this.studentAcademicYearHostelRoomDao.findCurrentOccupiedStudentAcademicYearHostelRoomForStudent(studentAcademicYearId);
	}

}
