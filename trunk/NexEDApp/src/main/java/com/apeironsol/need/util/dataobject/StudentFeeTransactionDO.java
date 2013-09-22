package com.apeironsol.need.util.dataobject;

import java.io.Serializable;
import java.util.Collection;

import com.apeironsol.need.financial.model.StudentFeeTransaction;

public class StudentFeeTransactionDO implements Serializable {

	/**
	 * 
	 */
	private static final long							serialVersionUID	= 3512732810528372818L;

	private StudentFeeTransaction						studentFeeTransaction;

	private Collection<StudentFeeTransactionDetailsDO>	studentFeeTransactionDetailsDOs;

	private String										className;

	private String										sectionName;

	public StudentFeeTransaction getStudentFeeTransaction() {
		return this.studentFeeTransaction;
	}

	public void setStudentFeeTransaction(final StudentFeeTransaction studentFeeTransaction) {
		this.studentFeeTransaction = studentFeeTransaction;
	}

	public Collection<StudentFeeTransactionDetailsDO> getStudentFeeTransactionDetailsDOs() {
		return this.studentFeeTransactionDetailsDOs;
	}

	public void setStudentFeeTransactionDetailsDOs(
			final Collection<StudentFeeTransactionDetailsDO> studentFeeTransactionDetailsDOs) {
		this.studentFeeTransactionDetailsDOs = studentFeeTransactionDetailsDOs;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the sectionName
	 */
	public String getSectionName() {
		return sectionName;
	}

	/**
	 * @param sectionName the sectionName to set
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

}
