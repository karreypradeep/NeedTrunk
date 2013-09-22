/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.util.constants.AdmissionStatusConstant;
import com.apeironsol.framework.exception.ApplicationException;

/**
 * Service interface for Admission.
 * 
 * @author Pradeep
 * 
 */
public interface BranchExportService {

	byte[] exportStudents(final AcademicYear academicYear, final Klass klass, final Section section) throws ApplicationException;

	byte[] exportAdmissions(final AcademicYear academicYear, final Klass klass, final AdmissionStatusConstant admissionStatus) throws ApplicationException;
}
