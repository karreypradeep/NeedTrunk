package com.apeironsol.need.util.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.apeironsol.need.financial.model.StudentFeeTransactionDetails;

public class StudentFeeTransactionDetailsDO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long				serialVersionUID	= -8753764438093337311L;

	private String							feeName;

	private Date							feeDueDate;

	private StudentFeeTransactionDetails	studentFeeTransactionDetails;

	public String getFeeName() {
		return this.feeName;
	}

	public void setFeeName(final String feeName) {
		this.feeName = feeName;
	}

	public StudentFeeTransactionDetails getStudentFeeTransactionDetails() {
		return this.studentFeeTransactionDetails;
	}

	public void setStudentFeeTransactionDetails(final StudentFeeTransactionDetails studentFeeTransactionDetails) {
		this.studentFeeTransactionDetails = studentFeeTransactionDetails;
	}

	public Date getFeeDueDate() {
		return this.feeDueDate;
	}

	public void setFeeDueDate(final Date feeDueDate) {
		this.feeDueDate = feeDueDate;
	}

}
