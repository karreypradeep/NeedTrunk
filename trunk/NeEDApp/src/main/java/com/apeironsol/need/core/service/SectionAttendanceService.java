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

import com.apeironsol.need.core.dataobject.SectionAttendanceReportMonthlyDO;

/**
 * Service layer interface for Student Attendance DAO implementation.
 * 
 * @author Pradeep
 * 
 */
public interface SectionAttendanceService {

	SectionAttendanceReportMonthlyDO generateSectionAttendanceReportForMonth(final Long sectionId, final Date monthDate);

	Collection<SectionAttendanceReportMonthlyDO> generateSectionAttendanceReportForAcademicYear(final Long sectionId, final Long academicYearId);

}
