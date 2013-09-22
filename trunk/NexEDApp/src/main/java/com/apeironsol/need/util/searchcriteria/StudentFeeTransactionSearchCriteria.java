/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.util.searchcriteria;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.util.constants.StudentFeeTransactionStatusConstant;
import com.apeironsol.need.util.constants.StudentFeeTransactionTypeConstant;

/**
 * Singleton class for student fee transaction search criteria..
 * 
 * @author Pradeep
 */
public class StudentFeeTransactionSearchCriteria implements SearchCriteria {

	/**
	 * Universal serial version id for this class
	 */
	private static final long								serialVersionUID	= 6070822367619472726L;

	/**
	 * Name of student.
	 */
	private String											name;

	/**
	 * Student date of birth.
	 */
	private Date											fromDate;

	/**
	 * Student date of birth.
	 */
	private Date											toDate;

	/**
	 * Name of student.
	 */
	private String											admissionNumber;

	/**
	 * Name of student.
	 */
	private String											transactionNumber;

	/**
	 * Student status.
	 */
	private Collection<StudentFeeTransactionStatusConstant>	studentDeeTransactionStatus;

	/**
	 * Student status.
	 */
	private Collection<StudentFeeTransactionTypeConstant>	transactionTypes;

	/**
	 * Branch.
	 */
	private Branch											branch;

	/**
	 * 
	 * @param branch
	 */
	public StudentFeeTransactionSearchCriteria(final Branch branch) {
		this.setBranch(branch);
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return this.fromDate;
	}

	/**
	 * @param transactionDate
	 *            the transactionDate to set
	 */
	public void setFromDate(final Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetSeacrhCriteria() {
		this.fromDate = null;
		this.name = null;
		this.toDate = null;
		this.admissionNumber = null;
		this.transactionNumber = null;
		this.studentDeeTransactionStatus = null;
		this.transactionTypes = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSearchCriteriaIsEmpty() {
		return (this.name == null || this.name.isEmpty()) && (this.admissionNumber == null || this.admissionNumber.isEmpty())
				&& (this.studentDeeTransactionStatus == null || this.studentDeeTransactionStatus.isEmpty()) && (this.transactionTypes == null || this.transactionTypes.isEmpty())
				&& (this.transactionNumber == null || this.transactionNumber.isEmpty()) && this.fromDate == null && this.toDate == null;
	}

	/**
	 * @return the admissionNumber
	 */
	public String getAdmissionNumber() {
		return this.admissionNumber;
	}

	/**
	 * @param admissionNumber
	 *            the admissionNumber to set
	 */
	public void setAdmissionNumber(final String admissionNumber) {
		this.admissionNumber = admissionNumber;
	}

	/**
	 * @return the transactionNumber
	 */
	public String getTransactionNumber() {
		return this.transactionNumber;
	}

	/**
	 * @param transactionNumber
	 *            the transactionNumber to set
	 */
	public void setTransactionNumber(final String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public Date getToDate() {
		return this.toDate;
	}

	public void setToDate(final Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the transactionTypes
	 */
	public Collection<StudentFeeTransactionTypeConstant> getTransactionTypes() {
		return this.transactionTypes;
	}

	/**
	 * @param transactionTypes
	 *            the transactionTypes to set
	 */
	public void setTransactionTypes(final Collection<StudentFeeTransactionTypeConstant> transactionTypes) {
		this.transactionTypes = transactionTypes;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the branch
	 */
	@Override
	public Branch getBranch() {
		return this.branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	@Override
	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	/**
	 * @return the transactionStatus
	 */
	public Collection<StudentFeeTransactionStatusConstant> getStudentDeeTransactionStatus() {
		return this.studentDeeTransactionStatus;
	}

	/**
	 * @param transactionStatus
	 *            the transactionStatus to set
	 */
	public void setStudentDeeTransactionStatus(final Collection<StudentFeeTransactionStatusConstant> transactionStatus) {
		this.studentDeeTransactionStatus = transactionStatus;
	}
}
