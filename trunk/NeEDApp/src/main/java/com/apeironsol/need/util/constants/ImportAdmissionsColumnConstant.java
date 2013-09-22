package com.apeironsol.need.util.constants;

public enum ImportAdmissionsColumnConstant {

	EXISTING_ADMISSION_NUMBER(0, "Existing Admission Number"),
	APPLICATION_FORM_FEE(1, "Application Form Fee"),
	APPLICATION_FORM_FEE_RECEIPT_NUMBER(2, "Application Form Fee Receipt Number"),
	APPLICATION_FORM_FEE_PAID_DATE(3, "Application Form Fee Paid Date"),
	PREVIOUS_QUALIFIED_EDUCATION(4, "Previous Qualified Education"),
	REFERENCED_BY(5, "Referenced by"),
	ADMISSION_DATE(6, "Admission date"),
	STUDENT_FIRST_NAME(7, "Student First Name"),
	STUDENT_MIDDLE_NAME(8, "Student Middle Name"),
	STUDENT_LAST_NAME(9, "Student Last Name"),
	STUDENT_DOB(10, "Student Date Of Birth"),
	GENDER(11, "Gender"),
	NATIONALITY(12, "Nationality"),
	RELIGION(13, "Religion"),
	MOTHER_TONGUE(14, "Mother Tongue"),
	RESERVATION(15, "Reservation"),
	RESIDENCE_TYPE(16, "Residence Type"),
	ADDRESS(17, "Address"),
	CITY(18, "City"),
	COUNTRY(19, "Country"),
	STATE(20, "State"),
	PINCODE(21, "Pincode"),
	MOBILE_NUMBER(22, "Mobile number"),
	ALTERNATE_PHONE_NUMBER(23, "Alternate phone number"),
	EMAIL_ID(24, "Email Id"),
	FATHER_FIRST_NAME(25, "Father First Name"),
	FATHER_MIDDLE_NAME(26, "Father Middle Name"),
	FATHER_LAST_NAME(27, "Father Last Name"),
	FATHER_DOB(28, "Father Date Of Birth"),
	FATHER_OCCUPATION(29, "Father Occupation"),
	FATHER_ANNUAL_INCOME(30, "Father Annual Income"),
	FATHER_ADDRESS(31, "Father Address"),
	FATHER_CITY(32, "Father City"),
	FATHER_COUNTRY(33, "Father Country"),
	FATHER_STATE(34, "Father State"),
	FATHER_PINCODE(35, "Father Pincode"),
	FATHER_MOBILE_NUMBER(36, "Father Mobile number"),
	FATHER_ALTERNATE_NUMBER(37, "Father Alternate Number"),
	FATHER_EMAIL_ID(38, "Father Email Id"),
	MOTHER_FIRST_NAME(39, "Mother First Name"),
	MOTHER_MIDDLE_NAME(40, "Mother Middle Name"),
	MOTHER_LAST_NAME(41, "Mother Last Name"),
	MOTHER_DOB(42, "Mother Date Of Birth"),
	MOTHER_OCCUPATION(43, "Mother Occupation"),
	MOTHER_ANNUAL_INCOME(44, "Mother Annual Income"),
	MOTHER_ADDRESS(45, "Mother Address"),
	MOTHER_CITY(46, "Mother City"),
	MOTHER_COUNTRY(47, "Mother Country"),
	MOTHER_STATE(48, "Mother State"),
	MOTHER_PINCODE(49, "Mother Pincode"),
	MOTHER_MOBILE_NUMBER(50, "Mother Mobile number"),
	MOTHER_ALTERNATE_NUMBER(51, "Mother Alternate Number"),
	MOTHER_EMAIL_ID(52, "Mother Email Id"),
	GUARDIAN_FIRST_NAME(53, "Guardian First Name"),
	GUARDIAN_MIDDLE_NAME(54, "Guardian Middle Name"),
	GUARDIAN_LAST_NAME(55, "Guardian Last Name"),
	GUARDIAN_DOB(56, "Guardian Date Of Birth"),
	GUARDIAN_GENDER(57, "Guardian Gender"),
	GUARDIAN_OCCUPATION(58, "Guardian Occupation"),
	GUARDIAN_ANNUAL_INCOME(59, "Guardian Annual Income"),
	GUARDIAN_ADDRESS(60, "Guardian Address"),
	GUARDIAN_CITY(61, "Guardian City"),
	GUARDIAN_COUNTRY(62, "Guardian Country"),
	GUARDIAN_STATE(63, "Guardian State"),
	GUARDIAN_PINCODE(64, "Guardian Pincode"),
	GUARDIAN_MOBILE_NUMBER(65, "Guardian Mobile number"),
	GUARDIAN_ALTERNATE_NUMBER(66, "Guardian Alternate Number"),
	GUARDIAN_EMAIL_ID(67, "Guardian Email Id"),
	RECENT_SCHOOL_NAME(68, "Recent school name"),
	IMPORT_DUPLICATE_RECORDS(69, "Import Duplicate Records"),
	STATUS(70, "Status");

	private int		columnNumber;
	private String	columnName;

	ImportAdmissionsColumnConstant(final int columnNumber, final String columnName) {
		this.setColumnNumber(columnNumber);
		this.setColumnName(columnName);
	}

	public int getColumnNumber() {
		return this.columnNumber;
	}

	public void setColumnNumber(final int columnNumber) {
		this.columnNumber = columnNumber;
	}

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return this.columnName;
	}

	/**
	 * @param columnName
	 *            the columnName to set
	 */
	public void setColumnName(final String columnName) {
		this.columnName = columnName;
	}

	public static ImportAdmissionsColumnConstant getImportAdmissionsColumnByColumnNumber(final int colNum) {
		ImportAdmissionsColumnConstant result = null;
		for (final ImportAdmissionsColumnConstant constant : ImportAdmissionsColumnConstant.values()) {
			if (constant.getColumnNumber() == colNum) {
				result = constant;
				break;
			}
		}
		return result;
	}
}
