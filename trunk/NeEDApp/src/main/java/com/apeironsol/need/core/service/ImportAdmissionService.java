/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.io.InputStream;
import java.util.List;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Batch;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.framework.exception.ApplicationException;

/**
 * Service interface for Admission.
 * 
 * @author Pradeep
 * 
 */
public interface ImportAdmissionService {

	/**
	 * Import admissions from supplied excel sheet. The supported excel format
	 * is xls.
	 * 
	 * @param fileInputStream
	 * @param branch
	 * @param applyingForKlass
	 * @param applyingForBatch
	 * @param applyingForAcademicYear
	 * @return
	 * @throws ApplicationException
	 */
	List<String> importAdmissionsFromExcel(final InputStream fileInputStream, final Branch branch, final Klass applyingForKlass, final Batch applyingForBatch,
			final AcademicYear applyingForAcademicYear) throws ApplicationException;

	byte[] getExcelAterImportOfAdmissions(final InputStream fileInputStream, final List<String> importStatus) throws ApplicationException;
}
