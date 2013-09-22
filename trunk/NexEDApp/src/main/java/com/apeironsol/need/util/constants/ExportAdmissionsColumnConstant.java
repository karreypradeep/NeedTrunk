package com.apeironsol.need.util.constants;

public enum ExportAdmissionsColumnConstant {

	ADMISSION_NUMBER(0, "Admission Number"),
	EXTERNAL_ADMISSION_NUMBER(1, "External Admission Number"),
	REGISTRATION_NUMBER(2, "External Admission Number"),
	STUDENT_FIRST_NAME(3, "Student First Name"),
	STUDENT_MIDDLE_NAME(4, "Student Middle Name"),
	STUDENT_LAST_NAME(5, "Student Last Name"),
	STUDENT_DOB(6, "Student Date Of Birth"),
	GENDER(7, "Gender"),
	APPLIED_CLASS(8, "Applied class"),
	APPLICATION_FORM_FEE(9, "Application Form Fee"),
	APPLICATION_FORM_FEE_RECEIPT_NUMBER(10, "Application Form Fee Receipt Number"),
	APPLICATION_FORM_FEE_PAID_DATE(11, "Application Form Fee Paid Date"),
	RESERVATOIN_FEE(12, "Reservation Fee"),
	RESERVATOIN_FEE_RECEIPT_NUMBER(13, "Reservation Fee Receipt Number"),
	RESERVATOIN_FEE_PAID_DATE(14, "Reservation Fee Paid Date"),
	PREVIOUS_QUALIFIED_EDUCATION(15, "Previous Qualified Education"),
	REFERENCED_BY(16, "Referenced by"),
	ADMISSION_DATE(17, "Admission date"),
	NATIONALITY(18, "Nationality"),
	RELIGION(19, "Religion"),
	MOTHER_TONGUE(20, "Mother Tongue"),
	RESERVATION(21, "Reservation"),
	RESIDENCE_TYPE(22, "Residence Type"),
	ADDRESS(23, "Address"),
	CITY(24, "City"),
	COUNTRY(25, "Country"),
	STATE(26, "State"),
	PINCODE(27, "Pincode"),
	MOBILE_NUMBER(28, "Mobile number"),
	ALTERNATE_PHONE_NUMBER(29, "Alternate phone number"),
	EMAIL_ID(30, "Email Id"),
	FATHER_FIRST_NAME(31, "Father First Name"),
	FATHER_MIDDLE_NAME(32, "Father Middle Name"),
	FATHER_LAST_NAME(33, "Father Last Name"),
	FATHER_DOB(34, "Father Date Of Birth"),
	FATHER_OCCUPATION(35, "Father Occupation"),
	FATHER_ANNUAL_INCOME(36, "Father Annual Income"),
	FATHER_ADDRESS(37, "Father Address"),
	FATHER_CITY(38, "Father City"),
	FATHER_COUNTRY(39, "Father Country"),
	FATHER_STATE(40, "Father State"),
	FATHER_PINCODE(41, "Father Pincode"),
	FATHER_MOBILE_NUMBER(42, "Father Mobile number"),
	FATHER_ALTERNATE_NUMBER(43, "Father Alternate Number"),
	FATHER_EMAIL_ID(44, "Father Email Id"),
	MOTHER_FIRST_NAME(45, "Mother First Name"),
	MOTHER_MIDDLE_NAME(46, "Mother Middle Name"),
	MOTHER_LAST_NAME(47, "Mother Last Name"),
	MOTHER_DOB(48, "Mother Date Of Birth"),
	MOTHER_OCCUPATION(49, "Mother Occupation"),
	MOTHER_ANNUAL_INCOME(50, "Mother Annual Income"),
	MOTHER_ADDRESS(51, "Mother Address"),
	MOTHER_CITY(52, "Mother City"),
	MOTHER_COUNTRY(53, "Mother Country"),
	MOTHER_STATE(54, "Mother State"),
	MOTHER_PINCODE(55, "Mother Pincode"),
	MOTHER_MOBILE_NUMBER(56, "Mother Mobile number"),
	MOTHER_ALTERNATE_NUMBER(57, "Mother Alternate Number"),
	MOTHER_EMAIL_ID(58, "Mother Email Id"),
	GUARDIAN_FIRST_NAME(59, "Guardian First Name"),
	GUARDIAN_MIDDLE_NAME(60, "Guardian Middle Name"),
	GUARDIAN_LAST_NAME(61, "Guardian Last Name"),
	GUARDIAN_DOB(62, "Guardian Date Of Birth"),
	GUARDIAN_GENDER(63, "Guardian Gender"),
	GUARDIAN_OCCUPATION(64, "Guardian Occupation"),
	GUARDIAN_ANNUAL_INCOME(65, "Guardian Annual Income"),
	GUARDIAN_ADDRESS(66, "Guardian Address"),
	GUARDIAN_CITY(67, "Guardian City"),
	GUARDIAN_COUNTRY(68, "Guardian Country"),
	GUARDIAN_STATE(69, "Guardian State"),
	GUARDIAN_PINCODE(70, "Guardian Pincode"),
	GUARDIAN_MOBILE_NUMBER(71, "Guardian Mobile number"),
	GUARDIAN_ALTERNATE_NUMBER(72, "Guardian Alternate Number"),
	GUARDIAN_EMAIL_ID(73, "Guardian Email Id");

	private int		columnNumber;
	private String	columnName;

	ExportAdmissionsColumnConstant(final int columnNumber, final String columnName) {
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

	public static ExportAdmissionsColumnConstant getExportAdmissionsColumnByColumnNumber(final int colNum) {
		ExportAdmissionsColumnConstant result = null;
		for (final ExportAdmissionsColumnConstant constant : ExportAdmissionsColumnConstant.values()) {
			if (constant.getColumnNumber() == colNum) {
				result = constant;
				break;
			}
		}
		return result;
	}
}
