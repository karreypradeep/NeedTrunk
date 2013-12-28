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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.StudentDao;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Address;
import com.apeironsol.need.core.model.AdmissionReservationFee;
import com.apeironsol.need.core.model.Batch;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.EducationHistory;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Relation;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentStatusHistory;
import com.apeironsol.need.util.constants.GenderConstant;
import com.apeironsol.need.util.constants.ImportAdmissionsColumnConstant;
import com.apeironsol.need.util.constants.RelationTypeConstant;
import com.apeironsol.need.util.constants.ResidenceConstant;
import com.apeironsol.need.util.searchcriteria.AdmissionSearchCriteria;
import com.apeironsol.framework.exception.ApplicationException;

/**
 * Service interface implementation for admissions.
 * 
 * @author Pradeep
 * 
 */
@Service("importAdmissionService")
@Transactional(rollbackFor = Exception.class)
public class ImportAdmissionServiceImpl implements ImportAdmissionService {

	@Resource
	private AdmissionService	admissionService;

	@Resource
	private CountryService		countryService;

	@Resource
	private StudentDao			studentDao;

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public List<String> importAdmissionsFromExcel(final InputStream fileInputStream, final Branch branch, final Klass applyingForKlass,
			final Batch applyingForBatch, final AcademicYear applyingForAcademicYear) throws ApplicationException {
		final List<String> importStatus = new ArrayList<String>();
		try {

			final POIFSFileSystem fs = new POIFSFileSystem(fileInputStream);
			final HSSFWorkbook wb = new HSSFWorkbook(fs);
			final HSSFSheet sheet = wb.getSheetAt(0);

			HSSFRow row;

			int rows; // No of rows

			rows = sheet.getPhysicalNumberOfRows();

			this.validateColumnHeaders(sheet.getRow(0));

			this.validateColumnSubHeaders(sheet.getRow(1));

			StudentStatusHistory history = null;

			// This trick ensures that we get the data properly even if it
			// doesn't start from first few rows

			Student student = null;

			Address primaryAddress = null;

			Collection<Relation> relations = null;

			Collection<EducationHistory> educationHistories = null;

			AdmissionReservationFee admissionReservationFee = null;

			final Map<String, ImportAdmissionsColumnConstant> studentAddressConstants = new HashMap<String, ImportAdmissionsColumnConstant>();

			studentAddressConstants.put("address", ImportAdmissionsColumnConstant.ADDRESS);

			studentAddressConstants.put("city", ImportAdmissionsColumnConstant.CITY);

			studentAddressConstants.put("state", ImportAdmissionsColumnConstant.STATE);

			studentAddressConstants.put("country", ImportAdmissionsColumnConstant.COUNTRY);

			studentAddressConstants.put("pincode", ImportAdmissionsColumnConstant.PINCODE);

			studentAddressConstants.put("mobileNumber", ImportAdmissionsColumnConstant.MOBILE_NUMBER);

			studentAddressConstants.put("alternateNumber", ImportAdmissionsColumnConstant.ALTERNATE_PHONE_NUMBER);

			studentAddressConstants.put("emailId", ImportAdmissionsColumnConstant.EMAIL_ID);

			Collection<Student> studnetsWithSameName = null;

			for (int i = 2; i < rows; i++) {
				row = sheet.getRow(i);
				if (row != null) {
					try {
						boolean importStudentRecord = true;
						String status = null;
						if (row.getCell(ImportAdmissionsColumnConstant.STATUS.getColumnNumber()) != null
								&& row.getCell(ImportAdmissionsColumnConstant.STATUS.getColumnNumber()).toString() != null) {
							status = row.getCell(ImportAdmissionsColumnConstant.STATUS.getColumnNumber()).toString().trim();
							if (status != null && status.trim().length() > 0 && status.startsWith("OK:")) {
								importStudentRecord = false;
							}
						}
						if (importStudentRecord) {
							student = null;

							student = this.getStudent(row, branch, applyingForKlass, applyingForBatch, applyingForAcademicYear);

							primaryAddress = this.getAddress(row, studentAddressConstants, true);

							relations = this.getRelations(row, primaryAddress);

							educationHistories = this.getEducationHistorys(row);

							admissionReservationFee = this.getAdmissionReservationFee(row);

							history = new StudentStatusHistory();

							history.setComments("Admission Submitted");

							if (primaryAddress == null) {
								importStatus.add("NOK:Invalid address for student " + student.getDisplayName());
							} else if (relations == null) {
								importStatus.add("NOK:Invalid relations data " + student.getDisplayName());
							} else {
								if (student.getExternalAdmissionNr() != null) {
									Student studentWithSameExAdNr = this.studentDao.findActiveStudentByExternalAdmissionNumberAndBranchId(
											student.getExternalAdmissionNr(), branch.getId());
									if (studentWithSameExAdNr != null) {
										importStatus.add("NOK: " + "Student with same external admission number " + student.getExternalAdmissionNr()
												+ " already exists for branch : " + branch.getName());
										continue;
									}
								}
								final AdmissionSearchCriteria admissionSearchCriteria = new AdmissionSearchCriteria(branch);
								admissionSearchCriteria.setName(student.getFirstName());
								admissionSearchCriteria.setDateOfBirth(student.getDateOfBirth());
								boolean importDuplicateRecords = false;
								if (row.getCell(ImportAdmissionsColumnConstant.IMPORT_DUPLICATE_RECORDS.getColumnNumber()) != null
										&& row.getCell(ImportAdmissionsColumnConstant.IMPORT_DUPLICATE_RECORDS.getColumnNumber()).toString() != null) {
									importDuplicateRecords = row.getCell(ImportAdmissionsColumnConstant.IMPORT_DUPLICATE_RECORDS.getColumnNumber()).toString()
											.equalsIgnoreCase("yes");
								}
								if (!importDuplicateRecords) {
									studnetsWithSameName = this.admissionService.findAdmissionsBySearchCriteria(admissionSearchCriteria);
								}
								if (studnetsWithSameName == null || studnetsWithSameName.isEmpty()) {
									student = this.admissionService.submitAdmission(student, relations, primaryAddress, educationHistories, branch.getId(),
											admissionReservationFee, false, history);
									importStatus.add("OK:Student " + student.getDisplayName() + " imported with registration number "
											+ student.getRegistrationNr());
								} else {
									importStatus.add("NOK:Student with same name " + student.getDisplayName() + " and date of birth  "
											+ new SimpleDateFormat("MM/dd/yyy").format(student.getDateOfBirth())
											+ " already exists. Please check if student is already in system.");
								}

							}
						} else {
							importStatus.add(row.getCell(ImportAdmissionsColumnConstant.STATUS.getColumnNumber()).toString());
						}
					} catch (final Exception ex) {
						throw new ApplicationException(ex);
					}
				}
			}

		} catch (final Exception ioe) {
			throw new ApplicationException(ioe);
		}
		return importStatus;
	}

	/**
	 * Get student from row.
	 * 
	 * @param row
	 * @param branch
	 * @param applyingForKlass
	 * @param applyingForBatch
	 * @param applyingForAcademicYear
	 * @return
	 * @throws Exception
	 */
	private Student getStudent(final HSSFRow row, final Branch branch, final Klass applyingForKlass, final Batch applyingForBatch,
			final AcademicYear applyingForAcademicYear) throws Exception {
		final Student student = new Student();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		if (row.getCell(ImportAdmissionsColumnConstant.STUDENT_FIRST_NAME.getColumnNumber()) != null
				&& row.getCell(ImportAdmissionsColumnConstant.STUDENT_FIRST_NAME.getColumnNumber()).toString() != null) {
			student.setFirstName(row.getCell(ImportAdmissionsColumnConstant.STUDENT_FIRST_NAME.getColumnNumber()).toString());
		} else {
			throw new ApplicationException("Student first name is mandatory");
		}
		if (row.getCell(ImportAdmissionsColumnConstant.STUDENT_MIDDLE_NAME.getColumnNumber()) != null
				&& row.getCell(ImportAdmissionsColumnConstant.STUDENT_MIDDLE_NAME.getColumnNumber()).toString().trim().length() > 0) {
			student.setMiddleName(row.getCell(ImportAdmissionsColumnConstant.STUDENT_MIDDLE_NAME.getColumnNumber()).toString());
		}

		if (row.getCell(ImportAdmissionsColumnConstant.STUDENT_LAST_NAME.getColumnNumber()) != null
				&& row.getCell(ImportAdmissionsColumnConstant.STUDENT_LAST_NAME.getColumnNumber()).toString() != null) {
			student.setLastName(row.getCell(ImportAdmissionsColumnConstant.STUDENT_LAST_NAME.getColumnNumber()).toString());
		} else {
			throw new ApplicationException("Student last name is mandatory");
		}

		if (row.getCell(ImportAdmissionsColumnConstant.ADMISSION_DATE.getColumnNumber()) != null
				&& row.getCell(ImportAdmissionsColumnConstant.ADMISSION_DATE.getColumnNumber()).toString().trim().length() > 0) {
			try {
				Date date = formatter.parse(row.getCell(ImportAdmissionsColumnConstant.ADMISSION_DATE.getColumnNumber()).toString());
				student.setAdmissionDate(date);
			} catch (Exception ex) {
				throw new ApplicationException("Invalid date format for admission date.");
			}
		}

		if (row.getCell(ImportAdmissionsColumnConstant.STUDENT_DOB.getColumnNumber()) != null) {
			try {
				Date date = formatter.parse(row.getCell(ImportAdmissionsColumnConstant.STUDENT_DOB.getColumnNumber()).toString());
				student.setDateOfBirth(date);
			} catch (Exception ex) {
				throw new ApplicationException("Invalid date format for student date of birth date.");
			}
		} else {
			throw new ApplicationException("Student date of birth is mandatory");
		}

		if (row.getCell(ImportAdmissionsColumnConstant.EXISTING_ADMISSION_NUMBER.getColumnNumber()) != null
				&& StringUtils.isNotEmpty(row.getCell(ImportAdmissionsColumnConstant.EXISTING_ADMISSION_NUMBER.getColumnNumber()).toString())) {
			student.setExternalAdmissionNr(row.getCell(ImportAdmissionsColumnConstant.EXISTING_ADMISSION_NUMBER.getColumnNumber()).toString());
		}
		if (row.getCell(ImportAdmissionsColumnConstant.GENDER.getColumnNumber()) != null) {
			final String gender = row.getCell(ImportAdmissionsColumnConstant.GENDER.getColumnNumber()).toString();
			if (gender.toLowerCase().contains("male")) {
				student.setGender(GenderConstant.MALE);
			} else if (gender.toLowerCase().contains("female")) {
				student.setGender(GenderConstant.FEMALE);
			} else {
				student.setGender(GenderConstant.OTHER);
			}
		} else {
			throw new ApplicationException("Student gender is mandatory");
		}
		if (row.getCell(ImportAdmissionsColumnConstant.MOTHER_TONGUE.getColumnNumber()) != null
				&& row.getCell(ImportAdmissionsColumnConstant.MOTHER_TONGUE.getColumnNumber()).toString().trim().length() > 0) {
			student.setMotherTongue(row.getCell(ImportAdmissionsColumnConstant.MOTHER_TONGUE.getColumnNumber()).getStringCellValue());
		}

		if (row.getCell(ImportAdmissionsColumnConstant.NATIONALITY.getColumnNumber()) != null
				&& row.getCell(ImportAdmissionsColumnConstant.NATIONALITY.getColumnNumber()).toString().trim().length() > 0) {
			student.setNationality(row.getCell(ImportAdmissionsColumnConstant.NATIONALITY.getColumnNumber()).toString());
		} else {
			throw new ApplicationException("Student nationality is mandatory");
		}

		if (row.getCell(ImportAdmissionsColumnConstant.PREVIOUS_QUALIFIED_EDUCATION.getColumnNumber()) != null
				&& row.getCell(ImportAdmissionsColumnConstant.PREVIOUS_QUALIFIED_EDUCATION.getColumnNumber()).toString().trim().length() > 0) {
			student.setPreviousQualifiedEducation(row.getCell(ImportAdmissionsColumnConstant.PREVIOUS_QUALIFIED_EDUCATION.getColumnNumber()).toString());
		}

		if (row.getCell(ImportAdmissionsColumnConstant.REFERENCED_BY.getColumnNumber()) != null
				&& row.getCell(ImportAdmissionsColumnConstant.REFERENCED_BY.getColumnNumber()).toString().trim().length() > 0) {
			student.setReferencedBy(row.getCell(ImportAdmissionsColumnConstant.REFERENCED_BY.getColumnNumber()).toString());
		}

		if (row.getCell(ImportAdmissionsColumnConstant.RELIGION.getColumnNumber()) != null
				&& row.getCell(ImportAdmissionsColumnConstant.RELIGION.getColumnNumber()).toString().trim().length() > 0) {
			student.setReligion(row.getCell(ImportAdmissionsColumnConstant.RELIGION.getColumnNumber()).toString());
		}

		if (row.getCell(ImportAdmissionsColumnConstant.RESERVATION.getColumnNumber()) != null
				&& row.getCell(ImportAdmissionsColumnConstant.RESERVATION.getColumnNumber()).toString().trim().length() > 0) {
			student.setReservation(row.getCell(ImportAdmissionsColumnConstant.RESERVATION.getColumnNumber()).toString());
		}

		if (row.getCell(ImportAdmissionsColumnConstant.RESIDENCE_TYPE.getColumnNumber()) != null) {
			final String residence = row.getCell(ImportAdmissionsColumnConstant.RESIDENCE_TYPE.getColumnNumber()).toString();
			if (residence != null && residence.trim().length() > 0) {
				if (residence.equalsIgnoreCase("Hostel")) {
					student.setResidence(ResidenceConstant.HOSTEL);
				} else {
					student.setResidence(ResidenceConstant.DAY_SCHOOLER);
				}
			}
		} else {
			throw new ApplicationException("Student residence type is mandatory");
		}
		student.setBranch(branch);
		student.setApplyingForKlass(applyingForKlass);
		student.setAppliedForAcademicYear(applyingForAcademicYear);
		student.setAppliedForBatch(applyingForBatch);
		return student;
	}

	/**
	 * Get address from row.
	 * 
	 * @param row
	 * @param addressConstants
	 * @param isPrimaryAddress
	 * @return
	 * @throws Exception
	 */
	private Address getAddress(final HSSFRow row, final Map<String, ImportAdmissionsColumnConstant> addressConstants, final boolean isPrimaryAddress)
			throws Exception {
		Address address = new Address();
		final DataFormatter formatter = new DataFormatter(Locale.US);
		if (row.getCell(addressConstants.get("address").getColumnNumber()) != null
				&& row.getCell(addressConstants.get("address").getColumnNumber()).toString() != null) {
			address.setAddress(row.getCell(addressConstants.get("address").getColumnNumber()).toString());
		} else if (isPrimaryAddress) {
			throw new ApplicationException("Address is mandatory");
		}

		if (row.getCell(addressConstants.get("city").getColumnNumber()) != null
				&& row.getCell(addressConstants.get("city").getColumnNumber()).toString() != null) {
			address.setCity(row.getCell(addressConstants.get("city").getColumnNumber()).getStringCellValue());
		} else if (isPrimaryAddress) {
			throw new ApplicationException("City is mandatory");
		}

		if (row.getCell(addressConstants.get("state").getColumnNumber()) != null
				&& row.getCell(addressConstants.get("state").getColumnNumber()).toString() != null) {
			address.setState(row.getCell(addressConstants.get("state").getColumnNumber()).toString());
		} else if (isPrimaryAddress) {
			throw new ApplicationException("State is mandatory");
		}

		if (row.getCell(addressConstants.get("emailId").getColumnNumber()) != null
				&& row.getCell(addressConstants.get("emailId").getColumnNumber()).toString().trim().length() > 0) {
			address.setEmail(row.getCell(addressConstants.get("emailId").getColumnNumber()).toString());
		}

		if (row.getCell(addressConstants.get("alternateNumber").getColumnNumber()) != null
				&& row.getCell(addressConstants.get("alternateNumber").getColumnNumber()).toString().trim().length() > 0) {
			address.setHomePhoneNr(row.getCell(addressConstants.get("alternateNumber").getColumnNumber()).toString());
		}

		if (row.getCell(addressConstants.get("mobileNumber").getColumnNumber()) != null
				&& row.getCell(addressConstants.get("mobileNumber").getColumnNumber()).toString() != null) {
			address.setMobileNr(formatter.formatCellValue(row.getCell(addressConstants.get("mobileNumber").getColumnNumber())));
		} else if (isPrimaryAddress) {
			throw new ApplicationException("Mobile number is mandatory");
		}

		address.setPrimary(isPrimaryAddress);
		if (row.getCell(addressConstants.get("country").getColumnNumber()) != null) {
			address.setCountry(this.countryService.findCountryByIso3(row.getCell(addressConstants.get("country").getColumnNumber()).getStringCellValue()
					.toUpperCase()));
		} else if (isPrimaryAddress) {
			throw new ApplicationException("Country is mandatory");
		}

		if (row.getCell(addressConstants.get("pincode").getColumnNumber()) != null) {
			address.setZipCode(formatter.formatCellValue(row.getCell(addressConstants.get("pincode").getColumnNumber())));
		} else if (isPrimaryAddress) {
			throw new ApplicationException("Pincode is mandatory");
		}

		if (address.getAddress() == null || address.getAddress().trim().length() == 0) {
			address = null;
		} else if (address.getCity() == null || address.getCity().trim().length() == 0) {
			address = null;
		} else if (address.getState() == null || address.getState().trim().length() == 0) {
			address = null;
		} else if (address.getMobileNr() == null || address.getMobileNr().trim().length() == 0) {
			address = null;
		} else if (address.getCountry() == null) {
			address = null;
		} else if (address.getZipCode() == null || address.getZipCode().trim().length() == 0) {
			address = null;
		}
		return address;
	}

	/**
	 * Get relations from row.
	 * 
	 * @param row
	 * @param primaryAddress
	 * @return
	 * @throws Exception
	 */
	private Collection<Relation> getRelations(final HSSFRow row, final Address primaryAddress) throws Exception {
		Collection<Relation> relations = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		if (row.getCell(ImportAdmissionsColumnConstant.FATHER_FIRST_NAME.getColumnNumber()) != null
				&& row.getCell(ImportAdmissionsColumnConstant.FATHER_FIRST_NAME.getColumnNumber()).toString().trim().length() > 0
				&& row.getCell(ImportAdmissionsColumnConstant.FATHER_LAST_NAME.getColumnNumber()).toString().trim().length() > 0
				&& row.getCell(ImportAdmissionsColumnConstant.FATHER_LAST_NAME.getColumnNumber()) != null) {
			relations = new ArrayList<Relation>();
			final Relation father = new Relation();
			father.setFirstName(row.getCell(ImportAdmissionsColumnConstant.FATHER_FIRST_NAME.getColumnNumber()).toString());
			father.setLastName(row.getCell(ImportAdmissionsColumnConstant.FATHER_LAST_NAME.getColumnNumber()).toString());
			if (row.getCell(ImportAdmissionsColumnConstant.FATHER_ANNUAL_INCOME.getColumnNumber()) != null
					&& row.getCell(ImportAdmissionsColumnConstant.FATHER_ANNUAL_INCOME.getColumnNumber()).getNumericCellValue() > 0) {
				father.setAnnualIncome(row.getCell(ImportAdmissionsColumnConstant.FATHER_ANNUAL_INCOME.getColumnNumber()).getNumericCellValue());
			}
			if (row.getCell(ImportAdmissionsColumnConstant.FATHER_DOB.getColumnNumber()) != null) {
				try {
					Date date = formatter.parse(row.getCell(ImportAdmissionsColumnConstant.FATHER_DOB.getColumnNumber()).toString());
					father.setDateOfBirth(date);
				} catch (Exception ex) {
					throw new ApplicationException("Invalid date format for father date of birth date.");
				}
			}
			if (row.getCell(ImportAdmissionsColumnConstant.FATHER_MIDDLE_NAME.getColumnNumber()) != null
					&& row.getCell(ImportAdmissionsColumnConstant.FATHER_MIDDLE_NAME.getColumnNumber()).toString().trim().length() > 0) {
				father.setMiddleName(row.getCell(ImportAdmissionsColumnConstant.FATHER_MIDDLE_NAME.getColumnNumber()).toString());
			}
			if (row.getCell(ImportAdmissionsColumnConstant.FATHER_OCCUPATION.getColumnNumber()) != null
					&& row.getCell(ImportAdmissionsColumnConstant.FATHER_OCCUPATION.getColumnNumber()).toString().trim().length() > 0) {
				father.setOccupation(row.getCell(ImportAdmissionsColumnConstant.FATHER_OCCUPATION.getColumnNumber()).toString());
			}
			father.setRelationType(RelationTypeConstant.FATHER);
			father.setGender(GenderConstant.MALE);
			final Map<String, ImportAdmissionsColumnConstant> addressConstants = new HashMap<String, ImportAdmissionsColumnConstant>();
			addressConstants.put("address", ImportAdmissionsColumnConstant.FATHER_ADDRESS);
			addressConstants.put("city", ImportAdmissionsColumnConstant.FATHER_CITY);
			addressConstants.put("state", ImportAdmissionsColumnConstant.FATHER_STATE);
			addressConstants.put("country", ImportAdmissionsColumnConstant.FATHER_COUNTRY);
			addressConstants.put("pincode", ImportAdmissionsColumnConstant.FATHER_PINCODE);
			addressConstants.put("mobileNumber", ImportAdmissionsColumnConstant.FATHER_MOBILE_NUMBER);
			addressConstants.put("alternateNumber", ImportAdmissionsColumnConstant.FATHER_ALTERNATE_NUMBER);
			addressConstants.put("emailId", ImportAdmissionsColumnConstant.FATHER_EMAIL_ID);
			final Address fatherAddress = this.getAddress(row, addressConstants, false);
			if (fatherAddress == null) {
				father.setAddress(primaryAddress);
			}
			relations.add(father);
		}

		if (row.getCell(ImportAdmissionsColumnConstant.MOTHER_FIRST_NAME.getColumnNumber()) != null
				&& row.getCell(ImportAdmissionsColumnConstant.MOTHER_FIRST_NAME.getColumnNumber()).toString().trim().length() > 0
				&& row.getCell(ImportAdmissionsColumnConstant.MOTHER_LAST_NAME.getColumnNumber()) != null
				&& row.getCell(ImportAdmissionsColumnConstant.MOTHER_LAST_NAME.getColumnNumber()).toString().trim().length() > 0) {
			if (relations == null) {
				relations = new ArrayList<Relation>();
			}
			final Relation mother = new Relation();
			mother.setFirstName(row.getCell(ImportAdmissionsColumnConstant.MOTHER_FIRST_NAME.getColumnNumber()).toString());
			mother.setLastName(row.getCell(ImportAdmissionsColumnConstant.MOTHER_LAST_NAME.getColumnNumber()).toString());
			if (row.getCell(ImportAdmissionsColumnConstant.MOTHER_ANNUAL_INCOME.getColumnNumber()) != null
					&& row.getCell(ImportAdmissionsColumnConstant.MOTHER_ANNUAL_INCOME.getColumnNumber()).getNumericCellValue() > 0) {
				mother.setAnnualIncome(row.getCell(ImportAdmissionsColumnConstant.MOTHER_ANNUAL_INCOME.getColumnNumber()).getNumericCellValue());
			}

			if (row.getCell(ImportAdmissionsColumnConstant.MOTHER_DOB.getColumnNumber()) != null
					&& row.getCell(ImportAdmissionsColumnConstant.MOTHER_DOB.getColumnNumber()).toString().trim().length() > 0) {
				try {
					Date date = formatter.parse(row.getCell(ImportAdmissionsColumnConstant.MOTHER_DOB.getColumnNumber()).toString());
					mother.setDateOfBirth(date);
				} catch (Exception ex) {
					throw new ApplicationException("Invalid date format for mother date of birth date.");
				}
			}
			if (row.getCell(ImportAdmissionsColumnConstant.MOTHER_MIDDLE_NAME.getColumnNumber()) != null
					&& row.getCell(ImportAdmissionsColumnConstant.MOTHER_MIDDLE_NAME.getColumnNumber()).toString().trim().length() > 0) {
				mother.setMiddleName(row.getCell(ImportAdmissionsColumnConstant.MOTHER_MIDDLE_NAME.getColumnNumber()).toString());
			}
			if (row.getCell(ImportAdmissionsColumnConstant.MOTHER_OCCUPATION.getColumnNumber()) != null
					&& row.getCell(ImportAdmissionsColumnConstant.MOTHER_OCCUPATION.getColumnNumber()).toString().trim().length() > 0) {
				mother.setOccupation(row.getCell(ImportAdmissionsColumnConstant.MOTHER_OCCUPATION.getColumnNumber()).toString());
			}
			mother.setRelationType(RelationTypeConstant.MOTHER);
			mother.setGender(GenderConstant.FEMALE);
			final Map<String, ImportAdmissionsColumnConstant> addressConstants = new HashMap<String, ImportAdmissionsColumnConstant>();
			addressConstants.put("address", ImportAdmissionsColumnConstant.MOTHER_ADDRESS);
			addressConstants.put("city", ImportAdmissionsColumnConstant.MOTHER_CITY);
			addressConstants.put("state", ImportAdmissionsColumnConstant.MOTHER_STATE);
			addressConstants.put("country", ImportAdmissionsColumnConstant.MOTHER_COUNTRY);
			addressConstants.put("pincode", ImportAdmissionsColumnConstant.MOTHER_PINCODE);
			addressConstants.put("mobileNumber", ImportAdmissionsColumnConstant.MOTHER_MOBILE_NUMBER);
			addressConstants.put("alternateNumber", ImportAdmissionsColumnConstant.MOTHER_ALTERNATE_NUMBER);
			addressConstants.put("emailId", ImportAdmissionsColumnConstant.MOTHER_EMAIL_ID);
			final Address motherAddress = this.getAddress(row, addressConstants, false);
			if (motherAddress == null) {
				mother.setAddress(primaryAddress);
			}
			relations.add(mother);
		}

		if (row.getCell(ImportAdmissionsColumnConstant.GUARDIAN_FIRST_NAME.getColumnNumber()) != null
				&& row.getCell(ImportAdmissionsColumnConstant.GUARDIAN_FIRST_NAME.getColumnNumber()).toString().trim().length() > 0
				&& row.getCell(ImportAdmissionsColumnConstant.GUARDIAN_LAST_NAME.getColumnNumber()).toString().trim().length() > 0
				&& row.getCell(ImportAdmissionsColumnConstant.GUARDIAN_LAST_NAME.getColumnNumber()) != null) {
			if (relations == null) {
				relations = new ArrayList<Relation>();
			}
			final Relation guardian = new Relation();
			guardian.setAddress(primaryAddress);
			guardian.setFirstName(row.getCell(ImportAdmissionsColumnConstant.GUARDIAN_FIRST_NAME.getColumnNumber()).toString());
			guardian.setLastName(row.getCell(ImportAdmissionsColumnConstant.GUARDIAN_LAST_NAME.getColumnNumber()).toString());
			if (row.getCell(ImportAdmissionsColumnConstant.GUARDIAN_MIDDLE_NAME.getColumnNumber()) != null
					&& row.getCell(ImportAdmissionsColumnConstant.GUARDIAN_MIDDLE_NAME.getColumnNumber()).toString().trim().length() > 0) {
				guardian.setMiddleName(row.getCell(ImportAdmissionsColumnConstant.GUARDIAN_MIDDLE_NAME.getColumnNumber()).toString());
			}
			if (row.getCell(ImportAdmissionsColumnConstant.GUARDIAN_ANNUAL_INCOME.getColumnNumber()) != null
					&& row.getCell(ImportAdmissionsColumnConstant.GUARDIAN_ANNUAL_INCOME.getColumnNumber()).getNumericCellValue() > 0) {
				guardian.setAnnualIncome(row.getCell(ImportAdmissionsColumnConstant.GUARDIAN_ANNUAL_INCOME.getColumnNumber()).getNumericCellValue());
			}

			if (row.getCell(ImportAdmissionsColumnConstant.GUARDIAN_DOB.getColumnNumber()) != null
					&& row.getCell(ImportAdmissionsColumnConstant.GUARDIAN_DOB.getColumnNumber()).toString().trim().length() > 0) {
				try {
					Date date = formatter.parse(row.getCell(ImportAdmissionsColumnConstant.GUARDIAN_DOB.getColumnNumber()).toString());
					guardian.setDateOfBirth(date);
				} catch (Exception ex) {
					throw new ApplicationException("Invalid date format for guardian date of birth date.");
				}
			}
			if (row.getCell(ImportAdmissionsColumnConstant.GUARDIAN_OCCUPATION.getColumnNumber()) != null
					&& row.getCell(ImportAdmissionsColumnConstant.GUARDIAN_OCCUPATION.getColumnNumber()).toString().trim().length() > 0) {
				guardian.setOccupation(row.getCell(ImportAdmissionsColumnConstant.GUARDIAN_OCCUPATION.getColumnNumber()).toString());
			}

			if (row.getCell(ImportAdmissionsColumnConstant.GUARDIAN_GENDER.getColumnNumber()) != null
					&& row.getCell(ImportAdmissionsColumnConstant.GUARDIAN_GENDER.getColumnNumber()).toString().trim().length() > 0) {
				final String gender = row.getCell(ImportAdmissionsColumnConstant.GENDER.getColumnNumber()).toString();
				if (gender.toLowerCase().contains("male")) {
					guardian.setGender(GenderConstant.MALE);
				} else if (gender.toLowerCase().contains("female")) {
					guardian.setGender(GenderConstant.FEMALE);
				} else {
					guardian.setGender(GenderConstant.OTHER);
				}
			}
			guardian.setRelationType(RelationTypeConstant.GUARDIAN);
			final Map<String, ImportAdmissionsColumnConstant> addressConstants = new HashMap<String, ImportAdmissionsColumnConstant>();
			addressConstants.put("address", ImportAdmissionsColumnConstant.GUARDIAN_ADDRESS);
			addressConstants.put("city", ImportAdmissionsColumnConstant.GUARDIAN_CITY);
			addressConstants.put("state", ImportAdmissionsColumnConstant.GUARDIAN_STATE);
			addressConstants.put("country", ImportAdmissionsColumnConstant.GUARDIAN_COUNTRY);
			addressConstants.put("pincode", ImportAdmissionsColumnConstant.GUARDIAN_PINCODE);
			addressConstants.put("mobileNumber", ImportAdmissionsColumnConstant.GUARDIAN_MOBILE_NUMBER);
			addressConstants.put("alternateNumber", ImportAdmissionsColumnConstant.GUARDIAN_ALTERNATE_NUMBER);
			addressConstants.put("emailId", ImportAdmissionsColumnConstant.GUARDIAN_EMAIL_ID);
			final Address guardianAddress = this.getAddress(row, addressConstants, false);
			if (guardianAddress == null) {
				guardian.setAddress(primaryAddress);
			}
			relations.add(guardian);
		}

		return relations;
	}

	/**
	 * GEt Education histories from row.
	 * 
	 * @param row
	 * @return
	 * @throws Exception
	 */
	private Collection<EducationHistory> getEducationHistorys(final HSSFRow row) throws Exception {
		Collection<EducationHistory> educationHistories = null;
		if (row.getCell(ImportAdmissionsColumnConstant.RECENT_SCHOOL_NAME.getColumnNumber()) != null) {
			educationHistories = new ArrayList<EducationHistory>();
			final EducationHistory educationHistory = new EducationHistory();
			educationHistory.setSchoolName(row.getCell(ImportAdmissionsColumnConstant.RECENT_SCHOOL_NAME.getColumnNumber()).toString());
			educationHistories.add(educationHistory);
		}

		return educationHistories;
	}

	/**
	 * Admission reservation fee from row.
	 * 
	 * @param row
	 * @return
	 * @throws Exception
	 */
	private AdmissionReservationFee getAdmissionReservationFee(final HSSFRow row) throws Exception {
		AdmissionReservationFee admissionReservationFee = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		if (row.getCell(ImportAdmissionsColumnConstant.APPLICATION_FORM_FEE.getColumnNumber()) != null
				&& row.getCell(ImportAdmissionsColumnConstant.APPLICATION_FORM_FEE.getColumnNumber()).getNumericCellValue() > 0) {

			admissionReservationFee = new AdmissionReservationFee();

			if (row.getCell(ImportAdmissionsColumnConstant.APPLICATION_FORM_FEE_PAID_DATE.getColumnNumber()) != null
					&& row.getCell(ImportAdmissionsColumnConstant.APPLICATION_FORM_FEE_PAID_DATE.getColumnNumber()).toString().trim().length() > 0
					&& row.getCell(ImportAdmissionsColumnConstant.APPLICATION_FORM_FEE_RECEIPT_NUMBER.getColumnNumber()) != null
					&& row.getCell(ImportAdmissionsColumnConstant.APPLICATION_FORM_FEE_RECEIPT_NUMBER.getColumnNumber()).getStringCellValue().trim().length() > 0) {
				try {
					Date date = formatter.parse(row.getCell(ImportAdmissionsColumnConstant.APPLICATION_FORM_FEE_PAID_DATE.getColumnNumber()).toString());
					admissionReservationFee.setApplicationFeeExternalTransactionDate(date);
				} catch (Exception ex) {
					throw new ApplicationException("Invalid date format for application form fee date.");
				}
				admissionReservationFee.setApplicationFeeExternalTransactionNr(row.getCell(
						ImportAdmissionsColumnConstant.APPLICATION_FORM_FEE_RECEIPT_NUMBER.getColumnNumber()).getStringCellValue());
			}
			admissionReservationFee.setApplicationFormFee(row.getCell(ImportAdmissionsColumnConstant.APPLICATION_FORM_FEE.getColumnNumber())
					.getNumericCellValue());
		}
		return admissionReservationFee;
	}

	/**
	 * Validates columns for row.
	 * 
	 * @param row
	 * @throws ApplicationException
	 */
	private void validateColumnHeaders(final HSSFRow row) throws ApplicationException {
		for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {

			switch (c) {
				case 0: {

					if (!row.getCell(c).getStringCellValue().equalsIgnoreCase("General Information")) {
						throw new ApplicationException("Group column 'General information' is expected at row 1 and column 1 ");
					}

					break;
				}
				case 7: {

					if (!row.getCell(c).getStringCellValue().equalsIgnoreCase("Student Personal Details")) {
						throw new ApplicationException("Group column 'Student Personal Details' is expected at row 1 and column 7 ");
					}
					break;
				}

				case 17: {

					if (!row.getCell(c).getStringCellValue().equalsIgnoreCase("Primary Address")) {
						throw new ApplicationException("Group column 'Primary Address' is expected at row 1 and column 17 ");
					}

					break;
				}
				case 25: {
					if (!row.getCell(c).getStringCellValue()
							.equalsIgnoreCase("Father Details(All Father details are optional if Mother or Guardian details are entered)")) {
						throw new ApplicationException(
								"Group column 'Father Details(All Father details are optional if Mother or Guardian details are entered)' is expected at row 1 and column 25 ");
					}
					break;
				}

				case 39: {

					if (!row.getCell(c).getStringCellValue()
							.equalsIgnoreCase("Mother Details (All Mother details are optional if Father or Guardian details are entered)")) {
						throw new ApplicationException(
								"Group column 'Mother Details (All Mother details are optional if Father or Guardian details are entered)' is expected at row 1 and column 39 ");
					}

					break;
				}
				case 53: {

					if (!row.getCell(c).getStringCellValue()
							.equalsIgnoreCase("Guardian Details (All Guardian details are optional if Father or Mother details are entered)")) {
						throw new ApplicationException(
								"Group column 'Guardian Details (All Guardian details are optional if Father or Mother details are entered)' is expected at row 1 and column 53 ");
					}
					break;
				}
				case 68: {

					if (!row.getCell(c).getStringCellValue().equalsIgnoreCase("Others")) {
						throw new ApplicationException("Group column 'Others' is expected at row 1 and column 68 ");
					}

					break;
				}

				default: {
					if (c > 70) {
						throw new ApplicationException("Group column " + row.getCell(c).getStringCellValue() + " is expected at row 1 and column  " + c + 1);
					}

				}
			}

		}
	}

	/**
	 * Validates columns for row.
	 * 
	 * @param row
	 * @throws ApplicationException
	 */
	private void validateColumnSubHeaders(final HSSFRow row) throws ApplicationException {
		for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {

			if (!row.getCell(c).getStringCellValue()
					.equalsIgnoreCase(ImportAdmissionsColumnConstant.getImportAdmissionsColumnByColumnNumber(c).getColumnName())) {

				throw new ApplicationException("Column " + row.getCell(c).getStringCellValue() + " is expected at location " + c);
			}
		}
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public byte[] getExcelAterImportOfAdmissions(final InputStream fileInputStream, final List<String> importStatus) throws ApplicationException {
		byte[] bytes = null;
		if (importStatus != null && !importStatus.isEmpty()) {
			try {
				final POIFSFileSystem fs = new POIFSFileSystem(fileInputStream);

				final HSSFWorkbook wb = new HSSFWorkbook(fs);

				final HSSFSheet sheet = wb.getSheetAt(0);

				HSSFRow row;

				int rows;
				final int cellNum = ImportAdmissionsColumnConstant.values().length;

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
				for (int i = 2; i < rows; i++) {

					row = sheet.getRow(i);

					final HSSFCell cell = row.createCell(cellNum - 1);

					if (importStatus.get(i - 2).startsWith("OK:")) {
						cell.setCellStyle(style);
					} else {
						cell.setCellStyle(redStyle);
					}
					cell.setCellValue(importStatus.get(i - 2));
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
