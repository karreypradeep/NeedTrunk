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
import com.apeironsol.need.hostel.model.StudentAcademicYearHostelRoom;
import com.apeironsol.need.util.NonNull;

/**
 * Data access interface for employee salary entity.
 * 
 * @author Pradeep
 * 
 */
public interface StudentAcademicYearHostelRoomDao extends BaseDao<StudentAcademicYearHostelRoom> {

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
	Collection<StudentAcademicYearHostelRoom> findAllHostelRoomsByStudentAcademicYear(@NonNull final Long studentAcademicYearId);

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
	Collection<StudentAcademicYearHostelRoom> findCurrentOccupantsForHostelRoom(@NonNull final Long roomId);

	StudentAcademicYearHostelRoom findStudentAcademicYearHostelRoomForStudentForDate(@NonNull final Long studentAcademicYearId, @NonNull final Date startDate);

	StudentAcademicYearHostelRoom findCurrentOccupiedStudentAcademicYearHostelRoomForStudent(@NonNull final Long studentAcademicYearId);

}
