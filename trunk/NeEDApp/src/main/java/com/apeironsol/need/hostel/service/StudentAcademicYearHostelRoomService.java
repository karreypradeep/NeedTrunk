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
import com.apeironsol.need.hostel.model.StudentAcademicYearHostelRoom;
import com.apeironsol.need.util.NonNull;

/**
 * Service interface for StudentAcademicYearHostelRoom.
 * 
 * @author Sunny
 * 
 */
public interface StudentAcademicYearHostelRoomService {

	/**
	 * Save StudentAcademicYearHostelRoom.
	 * 
	 * @param StudentAcademicYearHostelRoom
	 *            hostelRoom to be saved.
	 * @return persisted StudentAcademicYearHostelRoom.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	StudentAcademicYearHostelRoom saveStudentAcademicYearHostelRoom(StudentAcademicYearHostelRoom hostelRoom) throws BusinessException;

	/**
	 * Delete StudentAcademicYearHostelRoom.
	 * 
	 * @param StudentAcademicYearHostelRoom
	 *            hostelRoom to be deleted.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeStudentAcademicYearHostelRoom(StudentAcademicYearHostelRoom hostelRoom) throws BusinessException;

	/**
	 * Find StudentAcademicYearHostelRoom by Id.
	 * 
	 * @param id
	 *            StudentAcademicYearHostelRoom Id.
	 * @return StudentAcademicYearHostelRoom with supplied Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	StudentAcademicYearHostelRoom findStudentAcademicYearHostelRoomById(Long id) throws BusinessException;

	/**
	 * Remove all employee ctc by employee id.
	 * 
	 * @param employeeId
	 *            employee id.
	 */
	Collection<StudentAcademicYearHostelRoom> findHostelRoomOccupantsForAcademicYear(@NonNull final Long roomId, @NonNull final Long academicYearId);

	/**
	 * Remove all employee ctc by employee id.
	 * 
	 * @param employeeId
	 *            employee id.
	 */
	Collection<StudentAcademicYearHostelRoom> findStudentAcademicYearHostelRoomsByRoomId(@NonNull final Long roomId);

	/**
	 * Remove all employee ctc by employee id.
	 * 
	 * @param employeeId
	 *            employee id.
	 */
	Collection<StudentAcademicYearHostelRoom> findAllHostelRoomsByStudentAcademicYear(@NonNull final Long studentAcademicYearId);

	/**
	 * Remove all employee ctc by employee id.
	 * 
	 * @param employeeId
	 *            employee id.
	 */
	Collection<StudentAcademicYearHostelRoom> findCurrentOccupantsForHostelRoom(@NonNull final Long roomId);

	StudentAcademicYearHostelRoom findStudentAcademicYearHostelRoomForStudentForDate(@NonNull final Long studentAcademicYearId, @NonNull final Date startDate);

	StudentAcademicYearHostelRoom findCurrentOccupiedStudentAcademicYearHostelRoomForStudent(@NonNull final Long studentAcademicYearId);

}
