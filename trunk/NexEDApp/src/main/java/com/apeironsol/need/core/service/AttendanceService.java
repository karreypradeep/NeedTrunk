/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.core.model.Attendance;
import com.apeironsol.need.core.model.Section;

/**
 * Service layer interface for Student Attendance DAO implementation.
 * 
 * @author Pradeep
 * 
 */
public interface AttendanceService {
	Attendance findAttendanceBySectionSubjectIdAndAttendanceDate(final Long sectionSubjectId, final Date attendanceDate);

	Attendance findAttendanceBySectionIdAndAttendanceDateForDailyAttendance(final Long subjectId, final Date attendanceDate);

	/**
	 * 
	 * @param sectionId
	 * @return
	 */
	Collection<Attendance> findAttendancesBySectionId(final Long sectionId);

	Collection<Attendance> findAttendancesBySectionIdAndAttendanceDate(final Long sectionId, final Date attendanceDate);

	Collection<Attendance> findAttendancesBySectionsAndAttendanceDate(final Collection<Section> sections, final Date attendanceDate);
}
