/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */

package com.apeironsol.need.core.service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.RelationDao;
import com.apeironsol.need.core.dao.StudentSectionDao;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.AdmissionReservationFee;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Relation;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.util.constants.AdmissionStatusConstant;
import com.apeironsol.need.util.constants.ExportAdmissionsColumnConstant;
import com.apeironsol.need.util.constants.ExportStudentsColumnConstant;
import com.apeironsol.need.util.constants.RelationTypeConstant;
import com.apeironsol.need.util.searchcriteria.AdmissionSearchCriteria;
import com.apeironsol.need.util.searchcriteria.StudentSearchCriteria;
import com.apeironsol.framework.exception.ApplicationException;

/**
 * Service interface implementation for admissions.
 * 
 * @author Pradeep
 * 
 */
@Service("branchExportService")
@Transactional
public class BranchExportServiceImpl implements BranchExportService {

	@Resource
	private StudentSectionDao				studentSectionDao;

	@Resource
	private RelationDao						relationDao;

	@Resource
	private AdmissionService				admissionService;

	@Resource
	private AdmissionReservationFeeService	admissionReservationFeeService;

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public byte[] exportStudents(final AcademicYear academicYear, final Klass klass, final Section section) throws ApplicationException {
		byte[] bytes = null;
		try {
			StudentSearchCriteria studentSearchCriteria = new StudentSearchCriteria(academicYear.getBranch());
			studentSearchCriteria.setAcademicYear(academicYear);
			studentSearchCriteria.setKlass(klass);
			studentSearchCriteria.setSection(section);
			Collection<StudentSection> studentSections = this.studentSectionDao.findStudentSectionsBySearchCriteria(studentSearchCriteria);
			final HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Students");

			sheet.getPhysicalNumberOfRows();

			final HSSFFont font = wb.createFont();

			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			final HSSFCellStyle style = wb.createCellStyle();

			style.setFont(font);
			HSSFRow headingRow = sheet.createRow(0);
			List<String> cellContents = new ArrayList<String>();
			for (int c = 0; c < ExportStudentsColumnConstant.values().length; c++) {
				cellContents.add(c, ExportStudentsColumnConstant.getExportStudentsColumnByColumnNumber(c).getColumnName());
			}
			this.createCellsForRow(headingRow, cellContents, style);
			int count = 1;
			HSSFRow row = null;
			final HSSFFont normalFont = wb.createFont();
			normalFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			final HSSFCellStyle styleNormal = wb.createCellStyle();
			styleNormal.setFont(normalFont);
			String[] cellContentsArray = new String[ExportStudentsColumnConstant.values().length];
			for (StudentSection studentSection : studentSections) {
				row = sheet.createRow(count);
				this.getStudentDetails(studentSection, row, styleNormal, cellContentsArray);
				this.getRelationDetailsForStudents(studentSection, row, styleNormal, cellContentsArray);
				this.createCellsForRow(row, Arrays.asList(cellContentsArray), styleNormal);
				count++;
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

	private void getStudentDetails(final StudentSection studentSection, final HSSFRow row, final HSSFCellStyle style, final String[] cellContentsArray) {
		cellContentsArray[0] = studentSection.getStudentAcademicYear().getStudent().getAdmissionNr();
		cellContentsArray[1] = studentSection.getStudentAcademicYear().getStudent().getExternalAdmissionNr();
		cellContentsArray[2] = studentSection.getStudentAcademicYear().getStudent().getRegistrationNr();
		cellContentsArray[3] = studentSection.getStudentAcademicYear().getStudent().getFirstName();
		cellContentsArray[4] = studentSection.getStudentAcademicYear().getStudent().getMiddleName();
		cellContentsArray[5] = studentSection.getStudentAcademicYear().getStudent().getLastName();
		cellContentsArray[6] = new SimpleDateFormat("dd/MMM/yyyy").format(studentSection.getStudentAcademicYear().getStudent().getDateOfBirth());
		cellContentsArray[7] = studentSection.getStudentAcademicYear().getStudent().getGender().getLabel();
		cellContentsArray[8] = studentSection.getSection().getKlass().getName();
		cellContentsArray[9] = studentSection.getSection().getName();
		cellContentsArray[10] = studentSection.getStudentAcademicYear().getStudent().getNationality();
		cellContentsArray[11] = studentSection.getStudentAcademicYear().getStudent().getReligion();
		cellContentsArray[12] = studentSection.getStudentAcademicYear().getStudent().getMotherTongue();
		cellContentsArray[13] = studentSection.getStudentAcademicYear().getStudent().getReservation();
		cellContentsArray[14] = studentSection.getStudentAcademicYear().getStudent().getResidence().getLabel();
		cellContentsArray[15] = studentSection.getStudentAcademicYear().getStudent().getAddress().getAddress();
		cellContentsArray[16] = studentSection.getStudentAcademicYear().getStudent().getAddress().getCity();
		cellContentsArray[17] = studentSection.getStudentAcademicYear().getStudent().getAddress().getCountry().getName();
		cellContentsArray[18] = studentSection.getStudentAcademicYear().getStudent().getAddress().getState();
		cellContentsArray[19] = studentSection.getStudentAcademicYear().getStudent().getAddress().getZipCode();
		cellContentsArray[20] = studentSection.getStudentAcademicYear().getStudent().getAddress().getContactNumber();
		cellContentsArray[21] = studentSection.getStudentAcademicYear().getStudent().getAddress().getHomePhoneNr();
		cellContentsArray[22] = studentSection.getStudentAcademicYear().getStudent().getAddress().getEmail();
	}

	private void getRelationDetailsForStudents(final StudentSection studentSection, final HSSFRow row, final HSSFCellStyle style,
			final String[] cellContentsArray) {
		Collection<Relation> relations = this.relationDao.findRelationsByStudentId(studentSection.getStudentAcademicYear().getStudent().getId());
		Relation father = null, mother = null, guardian = null;
		for (Relation relation : relations) {
			if (RelationTypeConstant.FATHER.equals(relation.getRelationType())) {
				father = relation;
			} else if (RelationTypeConstant.MOTHER.equals(relation.getRelationType())) {
				mother = relation;
			} else {
				guardian = relation;
			}
		}
		if (father != null) {
			cellContentsArray[23] = father.getFirstName();
			cellContentsArray[24] = father.getMiddleName();
			cellContentsArray[25] = father.getLastName();
			cellContentsArray[26] = father.getDateOfBirth() != null ? new SimpleDateFormat("dd/MMM/yyyy").format(father.getDateOfBirth()) : null;
			cellContentsArray[27] = father.getOccupation();
			cellContentsArray[28] = father.getAnnualIncome() != null ? father.getAnnualIncome().toString() : null;
			cellContentsArray[29] = father.getAddress().getAddress();
			cellContentsArray[30] = father.getAddress().getCity();
			cellContentsArray[31] = father.getAddress().getCountry().getName();
			cellContentsArray[32] = father.getAddress().getState();
			cellContentsArray[33] = father.getAddress().getZipCode();
			cellContentsArray[34] = father.getAddress().getContactNumber();
			cellContentsArray[35] = father.getAddress().getHomePhoneNr();
			cellContentsArray[36] = father.getAddress().getEmail();
		}
		if (mother != null) {
			cellContentsArray[37] = mother.getFirstName();
			cellContentsArray[38] = mother.getMiddleName();
			cellContentsArray[39] = mother.getLastName();
			cellContentsArray[40] = mother.getDateOfBirth() != null ? new SimpleDateFormat("dd/MMM/yyyy").format(father.getDateOfBirth()) : null;
			cellContentsArray[41] = mother.getOccupation();
			cellContentsArray[42] = mother.getAnnualIncome() != null ? father.getAnnualIncome().toString() : null;
			cellContentsArray[43] = mother.getAddress().getAddress();
			cellContentsArray[44] = mother.getAddress().getCity();
			cellContentsArray[45] = mother.getAddress().getCountry().getName();
			cellContentsArray[46] = mother.getAddress().getState();
			cellContentsArray[47] = mother.getAddress().getZipCode();
			cellContentsArray[48] = mother.getAddress().getContactNumber();
			cellContentsArray[49] = mother.getAddress().getHomePhoneNr();
			cellContentsArray[50] = mother.getAddress().getEmail();
		}
		if (guardian != null) {
			cellContentsArray[51] = guardian.getFirstName();
			cellContentsArray[52] = guardian.getMiddleName();
			cellContentsArray[53] = guardian.getLastName();
			cellContentsArray[54] = guardian.getDateOfBirth() != null ? new SimpleDateFormat("dd/MMM/yyyy").format(father.getDateOfBirth()) : null;
			cellContentsArray[55] = guardian.getGender() != null ? father.getGender().getLabel() : null;
			cellContentsArray[56] = guardian.getOccupation();
			cellContentsArray[57] = guardian.getAnnualIncome() != null ? father.getAnnualIncome().toString() : null;
			cellContentsArray[58] = guardian.getAddress().getAddress();
			cellContentsArray[59] = guardian.getAddress().getCity();
			cellContentsArray[60] = guardian.getAddress().getCountry().getName();
			cellContentsArray[61] = guardian.getAddress().getState();
			cellContentsArray[62] = guardian.getAddress().getZipCode();
			cellContentsArray[63] = guardian.getAddress().getContactNumber();
			cellContentsArray[64] = guardian.getAddress().getHomePhoneNr();
			cellContentsArray[65] = guardian.getAddress().getEmail();
		}
	}

	private void createCellsForRow(final HSSFRow row, final List<String> cellContents, final HSSFCellStyle style) {
		HSSFCell cell = null;
		int count = 0;
		for (String cellContent : cellContents) {
			cell = row.createCell(count);
			cell.setCellStyle(style);
			cell.setCellValue(cellContent);
			count++;
		}

	}

	@Override
	public byte[] exportAdmissions(final AcademicYear academicYear, final Klass klass, final AdmissionStatusConstant admissionStatus)
			throws ApplicationException {
		byte[] bytes = null;
		try {
			AdmissionSearchCriteria admissionSearchCriteria = new AdmissionSearchCriteria(academicYear.getBranch());
			admissionSearchCriteria.setAcademicYear(academicYear);
			admissionSearchCriteria.setAppliedForClass(klass);
			admissionSearchCriteria.setAdmissionStatusConstant(admissionStatus);
			Collection<Student> admissions = this.admissionService.findAdmissionsBySearchCriteria(admissionSearchCriteria);
			final HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Admissions");

			sheet.getPhysicalNumberOfRows();

			final HSSFFont font = wb.createFont();

			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			final HSSFCellStyle style = wb.createCellStyle();

			style.setFont(font);
			HSSFRow headingRow = sheet.createRow(0);
			List<String> cellContents = new ArrayList<String>();
			for (int c = 0; c < ExportAdmissionsColumnConstant.values().length; c++) {
				cellContents.add(c, ExportAdmissionsColumnConstant.getExportAdmissionsColumnByColumnNumber(c).getColumnName());
			}
			this.createCellsForRow(headingRow, cellContents, style);
			int count = 1;
			HSSFRow row = null;
			final HSSFFont normalFont = wb.createFont();
			normalFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			final HSSFCellStyle styleNormal = wb.createCellStyle();
			styleNormal.setFont(normalFont);
			String[] cellContentsArray = new String[ExportAdmissionsColumnConstant.values().length];
			for (Student student : admissions) {
				row = sheet.createRow(count);
				this.getAdmissionDetails(student, row, styleNormal, cellContentsArray);
				this.getRelationDetailsForAdmissions(student, row, styleNormal, cellContentsArray);
				this.createCellsForRow(row, Arrays.asList(cellContentsArray), styleNormal);
				count++;
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

	private void getAdmissionDetails(final Student student, final HSSFRow row, final HSSFCellStyle style, final String[] cellContentsArray) {
		AdmissionReservationFee admissionReservationFee = this.admissionReservationFeeService.findAdmissionReservationFeeByStudentID(student.getId());
		cellContentsArray[0] = student.getAdmissionNr();
		cellContentsArray[1] = student.getExternalAdmissionNr();
		cellContentsArray[2] = student.getRegistrationNr();
		cellContentsArray[3] = student.getFirstName();
		cellContentsArray[4] = student.getMiddleName();
		cellContentsArray[5] = student.getLastName();
		cellContentsArray[6] = new SimpleDateFormat("dd/MMM/yyyy").format(student.getDateOfBirth());
		cellContentsArray[7] = student.getGender().getLabel();
		cellContentsArray[8] = student.getApplyingForKlass().getName();

		if (admissionReservationFee != null) {
			cellContentsArray[9] = admissionReservationFee.getApplicationFormFee() != null ? admissionReservationFee.getApplicationFormFee().toString() : null;
			cellContentsArray[10] = admissionReservationFee.getApplicationFeeExternalTransactionNr() != null ? admissionReservationFee
					.getApplicationFeeExternalTransactionNr() : null;
			cellContentsArray[11] = admissionReservationFee.getApplicationFeeExternalTransactionDate() != null ? new SimpleDateFormat("dd/MMM/yyyy")
					.format(admissionReservationFee.getApplicationFeeExternalTransactionDate()) : null;
			cellContentsArray[12] = admissionReservationFee.getReservationFee() != null ? admissionReservationFee.getReservationFee().toString() : null;
			cellContentsArray[13] = admissionReservationFee.getReservationFeeExternalTransactionNr() != null ? admissionReservationFee
					.getReservationFeeExternalTransactionNr() : null;
			cellContentsArray[14] = admissionReservationFee.getReservationFeeExternalTransactionDate() != null ? new SimpleDateFormat("dd/MMM/yyyy")
					.format(admissionReservationFee.getReservationFeeExternalTransactionDate()) : null;
		}
		cellContentsArray[15] = student.getPreviousQualifiedEducation();
		cellContentsArray[16] = student.getReferencedBy();
		cellContentsArray[17] = student.getAdmissionDate() != null ? new SimpleDateFormat("dd/MMM/yyyy").format(student.getAdmissionDate()) : null;

		cellContentsArray[18] = student.getNationality();
		cellContentsArray[19] = student.getReligion();
		cellContentsArray[20] = student.getMotherTongue();
		cellContentsArray[21] = student.getReservation();
		cellContentsArray[22] = student.getResidence().getLabel();
		cellContentsArray[23] = student.getAddress().getAddress();
		cellContentsArray[24] = student.getAddress().getCity();
		cellContentsArray[25] = student.getAddress().getCountry().getName();
		cellContentsArray[26] = student.getAddress().getState();
		cellContentsArray[27] = student.getAddress().getZipCode();
		cellContentsArray[28] = student.getAddress().getContactNumber();
		cellContentsArray[29] = student.getAddress().getHomePhoneNr();
		cellContentsArray[30] = student.getAddress().getEmail();
	}

	private void getRelationDetailsForAdmissions(final Student student, final HSSFRow row, final HSSFCellStyle style, final String[] cellContentsArray) {
		Collection<Relation> relations = this.relationDao.findRelationsByStudentId(student.getId());
		Relation father = null, mother = null, guardian = null;
		for (Relation relation : relations) {
			if (RelationTypeConstant.FATHER.equals(relation.getRelationType())) {
				father = relation;
			} else if (RelationTypeConstant.MOTHER.equals(relation.getRelationType())) {
				mother = relation;
			} else {
				guardian = relation;
			}
		}
		if (father != null) {
			cellContentsArray[31] = father.getFirstName();
			cellContentsArray[32] = father.getMiddleName();
			cellContentsArray[33] = father.getLastName();
			cellContentsArray[34] = father.getDateOfBirth() != null ? new SimpleDateFormat("dd/MMM/yyyy").format(father.getDateOfBirth()) : null;
			cellContentsArray[35] = father.getOccupation();
			cellContentsArray[36] = father.getAnnualIncome() != null ? father.getAnnualIncome().toString() : null;
			cellContentsArray[37] = father.getAddress().getAddress();
			cellContentsArray[38] = father.getAddress().getCity();
			cellContentsArray[39] = father.getAddress().getCountry().getName();
			cellContentsArray[40] = father.getAddress().getState();
			cellContentsArray[41] = father.getAddress().getZipCode();
			cellContentsArray[42] = father.getAddress().getContactNumber();
			cellContentsArray[43] = father.getAddress().getHomePhoneNr();
			cellContentsArray[44] = father.getAddress().getEmail();
		}
		if (mother != null) {
			cellContentsArray[45] = mother.getFirstName();
			cellContentsArray[46] = mother.getMiddleName();
			cellContentsArray[47] = mother.getLastName();
			cellContentsArray[48] = mother.getDateOfBirth() != null ? new SimpleDateFormat("dd/MMM/yyyy").format(father.getDateOfBirth()) : null;
			cellContentsArray[49] = mother.getOccupation();
			cellContentsArray[50] = mother.getAnnualIncome() != null ? father.getAnnualIncome().toString() : null;
			cellContentsArray[51] = mother.getAddress().getAddress();
			cellContentsArray[52] = mother.getAddress().getCity();
			cellContentsArray[53] = mother.getAddress().getCountry().getName();
			cellContentsArray[54] = mother.getAddress().getState();
			cellContentsArray[55] = mother.getAddress().getZipCode();
			cellContentsArray[56] = mother.getAddress().getContactNumber();
			cellContentsArray[57] = mother.getAddress().getHomePhoneNr();
			cellContentsArray[58] = mother.getAddress().getEmail();
		}
		if (guardian != null) {
			cellContentsArray[59] = guardian.getFirstName();
			cellContentsArray[60] = guardian.getMiddleName();
			cellContentsArray[61] = guardian.getLastName();
			cellContentsArray[62] = guardian.getDateOfBirth() != null ? new SimpleDateFormat("dd/MMM/yyyy").format(father.getDateOfBirth()) : null;
			cellContentsArray[63] = guardian.getGender() != null ? father.getGender().getLabel() : null;
			cellContentsArray[64] = guardian.getOccupation();
			cellContentsArray[65] = guardian.getAnnualIncome() != null ? father.getAnnualIncome().toString() : null;
			cellContentsArray[66] = guardian.getAddress().getAddress();
			cellContentsArray[67] = guardian.getAddress().getCity();
			cellContentsArray[68] = guardian.getAddress().getCountry().getName();
			cellContentsArray[69] = guardian.getAddress().getState();
			cellContentsArray[70] = guardian.getAddress().getZipCode();
			cellContentsArray[71] = guardian.getAddress().getContactNumber();
			cellContentsArray[71] = guardian.getAddress().getHomePhoneNr();
			cellContentsArray[73] = guardian.getAddress().getEmail();
		}
	}

}
