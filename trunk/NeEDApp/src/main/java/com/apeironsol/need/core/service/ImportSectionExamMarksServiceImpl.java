/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */

package com.apeironsol.need.core.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.academics.dataobject.StudentExamAllSubjectsDO;
import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.academics.model.SectionExamSubject;
import com.apeironsol.need.academics.model.StudentExamSubject;
import com.apeironsol.need.academics.service.SectionExamService;
import com.apeironsol.need.academics.service.SectionExamSubjectService;
import com.apeironsol.need.academics.service.StudentExamSubjectService;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.util.comparator.SectionExamSubjectComparator;
import com.apeironsol.need.util.comparator.StudentExamAllSubjectsDOComparator;
import com.apeironsol.need.util.constants.ImportSectionExamsColumnConstant;
import com.apeironsol.need.util.constants.StudentExamSubjectStatusConstant;

/**
 * Service interface implementation for admissions.
 * 
 * @author Pradeep
 * 
 */
@Service("importSectionExamMarksService")
@Transactional(rollbackFor = Exception.class)
public class ImportSectionExamMarksServiceImpl implements ImportSectionExamMarksService {

	@Resource
	private StudentAcademicYearService	studentAcademicYearService;

	@Resource
	private SectionExamService			sectionExamService;

	@Resource
	private StudentSectionService		studentSectionService;

	@Resource
	private SectionExamSubjectService	sectionExamSubjectService;

	@Resource
	private StudentExamSubjectService	studentExamSubjectService;

	@Resource
	private SectionService				sectionService;

	@Override
	public List<String> importSectionMarksFromExcel(final InputStream fileInputStream, final SectionExam sectionExam) throws ApplicationException {
		final List<String> importStatus = new ArrayList<String>();
		try {

			final POIFSFileSystem fs = new POIFSFileSystem(fileInputStream);
			final HSSFWorkbook wb = new HSSFWorkbook(fs);
			final HSSFSheet sheet = wb.getSheetAt(0);

			HSSFRow row;

			int rows; // No of rows
			final Collection<SectionExamSubject> sectionExamSubjects = this.sectionExamSubjectService.findSectionExamSubjectsBySubjectExamId(sectionExam
					.getId());
			Collections.sort((List<SectionExamSubject>) sectionExamSubjects, new SectionExamSubjectComparator(SectionExamSubjectComparator.Order.ID));
			final Collection<StudentExamAllSubjectsDO> studentExamAllSubjectsDOs = this.studentExamSubjectService
					.findStudentExamAllSubjectsDOsBySubjectExamId(sectionExam.getId());
			final Map<Long, StudentExamAllSubjectsDO> studentExamAllSubjectsDOMap = new HashMap<Long, StudentExamAllSubjectsDO>();
			for (final StudentExamAllSubjectsDO studentExamAllSubjectsDO : studentExamAllSubjectsDOs) {
				studentExamAllSubjectsDOMap.put(studentExamAllSubjectsDO.getStudentAcademicYear().getId(), studentExamAllSubjectsDO);
			}
			final Collection<StudentExamAllSubjectsDO> updatedStudentExamAllSubjectsDOs = new ArrayList<StudentExamAllSubjectsDO>();
			rows = sheet.getPhysicalNumberOfRows();
			this.validateSectionAndClass(sheet.getRow(0), sheet.getRow(1), sectionExam);
			this.validateColumnHeaders(sheet.getRow(2), sectionExamSubjects);

			StudentAcademicYear studentAcademicYear = null;
			boolean updatedAtleastOneStudentMarks = false;
			for (int i = 3; i < rows; i++) {
				row = sheet.getRow(i);
				if (row != null) {
					try {
						studentAcademicYear = this.getStudentAcademicYear(row);
					} catch (final Exception ex) {
						importStatus.add("NOK: " + ex.getMessage());
						continue;
					}
					if (studentExamAllSubjectsDOMap.get(studentAcademicYear.getId()) != null) {
						updatedAtleastOneStudentMarks = true;
						try {
							updatedStudentExamAllSubjectsDOs.add(this.updateStudentAcademicYearMarks(
									studentExamAllSubjectsDOMap.get(studentAcademicYear.getId()), sheet.getRow(2), row));
							importStatus.add("OK: Marks for " + studentAcademicYear.getStudent().getDisplayName() + " imported.");
						} catch (final Exception e) {
							importStatus.add("NOK: " + e.getMessage());
						}
					} else {
						importStatus.add("NOK: " + studentAcademicYear.getStudent().getDisplayName() + " does not have record for exam.");
					}
				}
			}
			if (updatedAtleastOneStudentMarks && (updatedStudentExamAllSubjectsDOs != null) && (updatedStudentExamAllSubjectsDOs.size() > 0)) {
				this.studentExamSubjectService.saveStudentExamAllSubjectsDOs(updatedStudentExamAllSubjectsDOs);
			}
		} catch (final Exception ioe) {
			throw new ApplicationException(ioe);
		}
		return importStatus;
	}

	private StudentExamAllSubjectsDO updateStudentAcademicYearMarks(final StudentExamAllSubjectsDO studentExamAllSubjectsDO, final HSSFRow columnHeadersRow,
			final HSSFRow studentRow) {
		final StudentExamAllSubjectsDO result = studentExamAllSubjectsDO;
		boolean importedAtleastForOneSubject = false;
		for (int c = 3; c < columnHeadersRow.getPhysicalNumberOfCells(); c++) {
			if ((studentRow.getCell(c) != null)) {
				for (final StudentExamSubject studentExamSubject : result.getStudentExamSubjects()) {
					if (columnHeadersRow.getCell(c).getStringCellValue().contains(studentExamSubject.getSectionExamSubject().getId() + "")) {
						String scoredValueAsString = null;
						if (Cell.CELL_TYPE_NUMERIC == studentRow.getCell(c).getCellType()) {
							scoredValueAsString = studentRow.getCell(c).getNumericCellValue() + "";
						} else if (Cell.CELL_TYPE_STRING == studentRow.getCell(c).getCellType()) {
							scoredValueAsString = studentRow.getCell(c).getStringCellValue();
						}
						if ((scoredValueAsString.equalsIgnoreCase("na"))) {
							studentExamSubject.setStudentExamSubjectStatus(StudentExamSubjectStatusConstant.NOT_APPLICABLE);
							studentExamSubject.setScoredMarks(null);
						} else if ((scoredValueAsString.equalsIgnoreCase("ab"))) {
							studentExamSubject.setStudentExamSubjectStatus(StudentExamSubjectStatusConstant.ABSENT);
							studentExamSubject.setScoredMarks(null);
						} else {
							try {
								final Double scoredMarks = Double.valueOf(scoredValueAsString);
								studentExamSubject.setScoredMarks(scoredMarks);
								studentExamSubject.setStudentExamSubjectStatus(StudentExamSubjectStatusConstant.TAKEN);
							} catch (final NumberFormatException e) {
								throw new ApplicationException("Invalid value for subject "
										+ studentExamSubject.getSectionExamSubject().getSectionSubject().getSubject().getName() + " is empty.");
							}
						}
						importedAtleastForOneSubject = true;
					}
				}
			}
		}
		if (!importedAtleastForOneSubject) {
			throw new ApplicationException("Please enter marks for atleast one subject");

		}
		return result;
	}

	private StudentAcademicYear getStudentAcademicYear(final HSSFRow row) {
		StudentAcademicYear studentAcademicYear = null;
		if ((row.getCell(ImportSectionExamsColumnConstant.STUDENT_ACADEMIC_YEAR_ID.getColumnNumber()) != null)
				&& (row.getCell(ImportSectionExamsColumnConstant.STUDENT_ACADEMIC_YEAR_ID.getColumnNumber()).toString() != null)) {
			Long stuAcaYearId = null;
			try {
				stuAcaYearId = Long.valueOf(row.getCell(ImportSectionExamsColumnConstant.STUDENT_ACADEMIC_YEAR_ID.getColumnNumber()).toString());
			} catch (final NumberFormatException e) {
				throw new ApplicationException("Student Academic Year Id is altered.");
			}
			if (stuAcaYearId != null) {
				studentAcademicYear = this.studentAcademicYearService.findStudentAcademicYearById(stuAcaYearId);
			}
		} else {
			throw new ApplicationException("Student Academic Year Id is altered.");
		}
		if (studentAcademicYear == null) {
			throw new ApplicationException("Student Academic Year Id is altered.");
		}

		if ((row.getCell(ImportSectionExamsColumnConstant.STUDENT_NAME.getColumnNumber()) != null)
				&& (row.getCell(ImportSectionExamsColumnConstant.STUDENT_NAME.getColumnNumber()).toString() != null)) {
			final String studetDisplayName = row.getCell(ImportSectionExamsColumnConstant.STUDENT_NAME.getColumnNumber()).toString();
			if ((studetDisplayName.trim().length() == 0) || !studetDisplayName.equals(studentAcademicYear.getStudent().getDisplayName())) {
				throw new ApplicationException("Student Name is altered.");
			}
		} else {
			throw new ApplicationException("Student Name is altered.");
		}

		if ((row.getCell(ImportSectionExamsColumnConstant.ADMISSION_NUMBER.getColumnNumber()) != null)
				&& (row.getCell(ImportSectionExamsColumnConstant.ADMISSION_NUMBER.getColumnNumber()).toString() != null)) {
			final String studetAdmissionNumber = row.getCell(ImportSectionExamsColumnConstant.ADMISSION_NUMBER.getColumnNumber()).toString();
			if ((studetAdmissionNumber.trim().length() == 0) || !studetAdmissionNumber.equals(studentAcademicYear.getStudent().getAdmissionNr())) {
				throw new ApplicationException("Admission Number is altered.");
			}

		} else {
			throw new ApplicationException("Admission Number is altered.");
		}
		return studentAcademicYear;
	}

	/**
	 * Validates columns for row.
	 * 
	 * @param row
	 * @throws ApplicationException
	 */
	private void validateColumnHeaders(final HSSFRow row, final Collection<SectionExamSubject> sectionExamSubjects) throws ApplicationException {
		if (!row.getCell(0).getStringCellValue().equalsIgnoreCase("Student Name")) {
			throw new ApplicationException("'Student Name' is expected at row 3 and column 1 ");
		}
		if (!row.getCell(1).getStringCellValue().equalsIgnoreCase("Admission Number")) {
			throw new ApplicationException("'Admission Number' is expected at row 3 and column 2 ");
		}

		if (!row.getCell(2).getStringCellValue().equalsIgnoreCase("Student Academic Year Id")) {
			throw new ApplicationException("'Student Academic Year Id' is expected at row 3 and column 3 ");
		}
		int i = 3;
		for (final SectionExamSubject sectionExamSubject : sectionExamSubjects) {
			final String cellData = row.getCell(i).getStringCellValue();
			final String expectedValue = sectionExamSubject.getSectionSubject().getSubject().getName() + "(" + sectionExamSubject.getId() + ")" + "/Max Marks("
					+ sectionExamSubject.getMaximumMarks() + ")";
			if (!cellData.equals(expectedValue)) {
				throw new ApplicationException("Subjects column has been changed for subject " + sectionExamSubject.getSectionSubject().getSubject().getName());
			}
			i++;
		}
	}

	/**
	 * Validates columns for row.
	 * 
	 * @param row
	 * @throws ApplicationException
	 */
	private void validateSectionAndClass(final HSSFRow sectionRow, final HSSFRow classRow, final SectionExam sectionExam) throws ApplicationException {
		if (sectionRow.getCell(2) != null) {
			final Long sectionId = Long.valueOf(sectionRow.getCell(2).getStringCellValue());
			final Section section = this.sectionService.findSectionById(sectionId);
			if (section == null) {
				throw new ApplicationException("Invalid Section Id.Section Id has been altered.");
			} else if (!sectionExam.getSection().getId().equals(section.getId())) {
				throw new ApplicationException("Invalid Section Id.Section Id has been altered.");
			}
			if (classRow.getCell(2) != null) {
				final Long klassId = Long.valueOf(classRow.getCell(2).getStringCellValue());
				if (klassId == null) {
					throw new ApplicationException("Invalid class Id.Class Id has been altered.");
				}
				if (!section.getKlass().getId().equals(klassId)) {
					throw new ApplicationException("Invalid class Id.Class Id has been altered.");
				}
			} else {
				throw new ApplicationException("Invalid class Id.Class Id has been altered.");
			}
		} else {
			throw new ApplicationException("Invalid Section Id.Section Id has been altered.");
		}
	}

	@Override
	public byte[] getSectionMarksExcelForImport(final InputStream fileInputStream, final SectionExam sectionExam) throws ApplicationException {
		byte[] bytes = null;
		try {
			final POIFSFileSystem fs = new POIFSFileSystem(fileInputStream);
			final HSSFWorkbook wb = new HSSFWorkbook(fs);
			final HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			// write section name and Id
			row = sheet.getRow(0);
			final HSSFCell sectionNameCell = row.createCell(1);
			sectionNameCell.setCellValue(sectionExam.getSection().getName());
			final HSSFCell sectionIdCell = row.createCell(2);
			sectionIdCell.setCellValue(sectionExam.getSection().getId() + "");
			row = sheet.getRow(1);
			// write class name and Id
			final HSSFCell classNameCell = row.createCell(1);
			classNameCell.setCellValue(sectionExam.getSection().getKlass().getName());
			final HSSFCell classIdCell = row.createCell(2);
			classIdCell.setCellValue(sectionExam.getSection().getKlass().getId() + "");

			row = sheet.createRow(2);
			final HSSFCell studentNameCell = row.createCell(0);
			studentNameCell.setCellValue("Student Name");
			final HSSFCell studentAdmNrCell = row.createCell(1);
			studentAdmNrCell.setCellValue("Admission Number");
			final HSSFCell studentAcaYearIdCell = row.createCell(2);
			studentAcaYearIdCell.setCellValue("Student Academic Year Id");

			final Collection<SectionExamSubject> sectionExamSubjects = this.sectionExamSubjectService.findSectionExamSubjectsBySubjectExamId(sectionExam
					.getId());
			Collections.sort((List<SectionExamSubject>) sectionExamSubjects, new SectionExamSubjectComparator(SectionExamSubjectComparator.Order.ID));
			int i = 0;
			final int cellNum = ImportSectionExamsColumnConstant.values().length;
			for (final SectionExamSubject sectionExamSubject : sectionExamSubjects) {
				final HSSFCell cell = row.createCell((cellNum) + i);
				cell.setCellValue(sectionExamSubject.getSectionSubject().getSubject().getName() + "(" + sectionExamSubject.getId() + ")" + "/Max Marks("
						+ sectionExamSubject.getMaximumMarks() + ")");
				i++;
			}

			final Collection<StudentExamAllSubjectsDO> studentExamAllSubjectsDOs = this.studentExamSubjectService
					.findStudentExamAllSubjectsDOsBySubjectExamId(sectionExam.getId());
			Collections.sort((List<StudentExamAllSubjectsDO>) studentExamAllSubjectsDOs, new StudentExamAllSubjectsDOComparator(
					StudentExamAllSubjectsDOComparator.Order.STUDENT_ADMISSION_NR));

			int rowCount = 3;
			for (final StudentExamAllSubjectsDO studentExamAllSubjectsDO : studentExamAllSubjectsDOs) {
				i = 0;
				final HSSFRow studentRow = sheet.createRow(rowCount);
				final HSSFCell nameColumn = studentRow.createCell(0);
				nameColumn.setCellValue(studentExamAllSubjectsDO.getStudentAcademicYear().getStudent().getDisplayName());
				final HSSFCell admiNrColumn = studentRow.createCell(1);
				admiNrColumn.setCellValue(studentExamAllSubjectsDO.getStudentAcademicYear().getStudent().getAdmissionNr());
				final HSSFCell stuAcaYearColumn = studentRow.createCell(2);
				stuAcaYearColumn.setCellValue(studentExamAllSubjectsDO.getStudentAcademicYear().getId() + "");
				for (final SectionExamSubject sectionExamSubject : sectionExamSubjects) {
					final HSSFCell marksCell = studentRow.createCell(3 + i);
					final StudentExamSubject studentExamSubject = studentExamAllSubjectsDO.getStudentExamSubjectBySectionExamSubjectId(sectionExamSubject
							.getId());
					if (StudentExamSubjectStatusConstant.TAKEN.equals(studentExamSubject.getStudentExamSubjectStatus())) {
						marksCell.setCellValue(studentExamSubject.getScoredMarks() + "");
					} else if (StudentExamSubjectStatusConstant.ABSENT.equals(studentExamSubject.getStudentExamSubjectStatus())) {
						marksCell.setCellValue("AB");
					} else if (StudentExamSubjectStatusConstant.NOT_APPLICABLE.equals(studentExamSubject.getStudentExamSubjectStatus())) {
						marksCell.setCellValue("NA");
					} else {
						marksCell.setCellValue("");
					}
					i++;
				}
				rowCount++;
			}
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				wb.write(bos);
			} finally {
				bos.close();
			}
			bytes = bos.toByteArray();
		} catch (final Exception ex) {
			throw new ApplicationException(ex);
		}
		return bytes;
	}

	@Override
	public byte[] getExcelAterImportOfSectionMarks(final InputStream fileInputStream, final SectionExam sectionExam, final List<String> importStatus)
			throws ApplicationException {
		byte[] bytes = null;
		if ((importStatus != null) && !importStatus.isEmpty()) {
			try {
				final POIFSFileSystem fs = new POIFSFileSystem(fileInputStream);

				final HSSFWorkbook wb = new HSSFWorkbook(fs);

				final HSSFSheet sheet = wb.getSheetAt(0);

				HSSFRow row;

				int rows;
				int cellNum = ImportSectionExamsColumnConstant.values().length;
				final Collection<SectionExamSubject> sectionExamSubjects = this.sectionExamSubjectService.findSectionExamSubjectsBySubjectExamId(sectionExam
						.getId());
				if (sectionExamSubjects != null) {
					cellNum = cellNum + sectionExamSubjects.size();
				}
				rows = sheet.getPhysicalNumberOfRows();

				final HSSFFont font = wb.createFont();

				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

				final HSSFCellStyle style = wb.createCellStyle();

				style.setFont(font);

				final HSSFFont redFont = wb.createFont();

				redFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

				redFont.setColor(Font.COLOR_RED);

				final HSSFCellStyle redStyle = wb.createCellStyle();

				redStyle.setFont(redFont);
				for (int i = 3; i < rows; i++) {

					row = sheet.getRow(i);

					final HSSFCell cell = row.createCell(cellNum);

					if (importStatus.get(i - 3).startsWith("OK:")) {
						cell.setCellStyle(style);
					} else {
						cell.setCellStyle(redStyle);
					}
					cell.setCellValue(importStatus.get(i - 3));
				}
				final ByteArrayOutputStream bos = new ByteArrayOutputStream();
				try {
					wb.write(bos);
				} finally {
					bos.close();
				}
				bytes = bos.toByteArray();
			} catch (final Exception ex) {
				throw new ApplicationException(ex);
			}
		}
		return bytes;
	}
}
