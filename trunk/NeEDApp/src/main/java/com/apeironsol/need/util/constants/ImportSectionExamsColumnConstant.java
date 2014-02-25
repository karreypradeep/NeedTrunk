package com.apeironsol.need.util.constants;

public enum ImportSectionExamsColumnConstant {

	STUDENT_NAME(0, "Student Name"), ADMISSION_NUMBER(1, "Admission Number"), STUDENT_ACADEMIC_YEAR_ID(2, "Student Academic Year Id");

	private int		columnNumber;
	private String	columnName;

	ImportSectionExamsColumnConstant(final int columnNumber, final String columnName) {
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
