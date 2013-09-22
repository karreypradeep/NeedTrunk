package com.apeironsol.need.util.constants;

public enum ExportStudentsColumnConstant {

	ADMISSION_NUMBER(0, "Admission Number"),
	EXTERNAL_ADMISSION_NUMBER(1, "External Admission Number"),
	REGISTRATION_NUMBER(2, "External Admission Number"),
	STUDENT_FIRST_NAME(3, "Student First Name"),
	STUDENT_MIDDLE_NAME(4, "Student Middle Name"),
	STUDENT_LAST_NAME(5, "Student Last Name"),
	STUDENT_DOB(6, "Student Date Of Birth"),
	GENDER(7, "Gender"),
	CURRENT_CLASS(8, "Class"),
	CURRENT_SECTION(9, "Section"),
	NATIONALITY(10, "Nationality"),
	RELIGION(11, "Religion"),
	MOTHER_TONGUE(12, "Mother Tongue"),
	RESERVATION(13, "Reservation"),
	RESIDENCE_TYPE(14, "Residence Type"),
	ADDRESS(15, "Address"),
	CITY(16, "City"),
	COUNTRY(17, "Country"),
	STATE(18, "State"),
	PINCODE(19, "Pincode"),
	MOBILE_NUMBER(20, "Mobile number"),
	ALTERNATE_PHONE_NUMBER(21, "Alternate phone number"),
	EMAIL_ID(22, "Email Id"),
	FATHER_FIRST_NAME(23, "Father First Name"),
	FATHER_MIDDLE_NAME(24, "Father Middle Name"),
	FATHER_LAST_NAME(25, "Father Last Name"),
	FATHER_DOB(26, "Father Date Of Birth"),
	FATHER_OCCUPATION(27, "Father Occupation"),
	FATHER_ANNUAL_INCOME(28, "Father Annual Income"),
	FATHER_ADDRESS(29, "Father Address"),
	FATHER_CITY(30, "Father City"),
	FATHER_COUNTRY(31, "Father Country"),
	FATHER_STATE(32, "Father State"),
	FATHER_PINCODE(33, "Father Pincode"),
	FATHER_MOBILE_NUMBER(34, "Father Mobile number"),
	FATHER_ALTERNATE_NUMBER(35, "Father Alternate Number"),
	FATHER_EMAIL_ID(36, "Father Email Id"),
	MOTHER_FIRST_NAME(37, "Mother First Name"),
	MOTHER_MIDDLE_NAME(38, "Mother Middle Name"),
	MOTHER_LAST_NAME(39, "Mother Last Name"),
	MOTHER_DOB(40, "Mother Date Of Birth"),
	MOTHER_OCCUPATION(41, "Mother Occupation"),
	MOTHER_ANNUAL_INCOME(42, "Mother Annual Income"),
	MOTHER_ADDRESS(43, "Mother Address"),
	MOTHER_CITY(44, "Mother City"),
	MOTHER_COUNTRY(45, "Mother Country"),
	MOTHER_STATE(46, "Mother State"),
	MOTHER_PINCODE(47, "Mother Pincode"),
	MOTHER_MOBILE_NUMBER(48, "Mother Mobile number"),
	MOTHER_ALTERNATE_NUMBER(49, "Mother Alternate Number"),
	MOTHER_EMAIL_ID(50, "Mother Email Id"),
	GUARDIAN_FIRST_NAME(51, "Guardian First Name"),
	GUARDIAN_MIDDLE_NAME(52, "Guardian Middle Name"),
	GUARDIAN_LAST_NAME(53, "Guardian Last Name"),
	GUARDIAN_DOB(54, "Guardian Date Of Birth"),
	GUARDIAN_GENDER(55, "Guardian Gender"),
	GUARDIAN_OCCUPATION(56, "Guardian Occupation"),
	GUARDIAN_ANNUAL_INCOME(57, "Guardian Annual Income"),
	GUARDIAN_ADDRESS(58, "Guardian Address"),
	GUARDIAN_CITY(59, "Guardian City"),
	GUARDIAN_COUNTRY(60, "Guardian Country"),
	GUARDIAN_STATE(61, "Guardian State"),
	GUARDIAN_PINCODE(62, "Guardian Pincode"),
	GUARDIAN_MOBILE_NUMBER(63, "Guardian Mobile number"),
	GUARDIAN_ALTERNATE_NUMBER(64, "Guardian Alternate Number"),
	GUARDIAN_EMAIL_ID(65, "Guardian Email Id");

	private int		columnNumber;
	private String	columnName;

	ExportStudentsColumnConstant(final int columnNumber, final String columnName) {
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

	public static ExportStudentsColumnConstant getExportStudentsColumnByColumnNumber(final int colNum) {
		ExportStudentsColumnConstant result = null;
		for (final ExportStudentsColumnConstant constant : ExportStudentsColumnConstant.values()) {
			if (constant.getColumnNumber() == colNum) {
				result = constant;
				break;
			}
		}
		return result;
	}
}
