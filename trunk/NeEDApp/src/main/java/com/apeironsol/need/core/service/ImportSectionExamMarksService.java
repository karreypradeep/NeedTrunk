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

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.academics.model.SectionExam;

/**
 * Service interface for Admission.
 * 
 * @author Pradeep
 * 
 */
public interface ImportSectionExamMarksService {

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
	List<String> importSectionMarksFromExcel(final InputStream fileInputStream, final SectionExam sectionExam) throws ApplicationException;

	byte[] getSectionMarksExcelForImport(final InputStream fileInputStream, final SectionExam sectionExam) throws ApplicationException;

	byte[] getExcelAterImportOfSectionMarks(final InputStream fileInputStream, final SectionExam sectionExam, final List<String> importStatus)
			throws ApplicationException;
}
