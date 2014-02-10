/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.apeironsol.framework.BaseEntity;
import com.apeironsol.need.util.constants.AttendanceTypeConstant;

/**
 * Entity class for branch rules.
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "BRANCH_RULE")
public class BranchRule extends BaseEntity implements Serializable {

	/**
	 * Universal serial version number.
	 */
	private static final long		serialVersionUID	= 3258972228145130521L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch					branch;

	@Basic
	@Column(name = "ATTENDANCE_TYPE", length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	private AttendanceTypeConstant	attendanceType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SMS_PROVIDER_ID")
	private SMSProvider				smsProvider;

	@Column(name = "MAXIMUM_SECTION_STRENGTH")
	private Integer					maximumSectionStrength;

	@Column(name = "AUTO_INCREASE_ADMISSION_NR")
	private boolean					autoIncreaseAdmissionNumber;

	@Column(name = "AUTO_INCREASE_EMPLOYEE_NR")
	private boolean					autoIncreaseEmployeeNumber;

	@Column(name = "BATCH_REQUIRED_IND", nullable = false)
	private boolean					batchRequiredIndicator;

	@Column(name = "ABSENT_REASON_REQ_IND")
	private boolean					absentReasonRequiredIndicator;

	/**
	 * Returns branch.
	 * 
	 * @return the branch.
	 */
	public Branch getBranch() {
		return this.branch;
	}

	/**
	 * Sets branch.
	 * 
	 * @param branch
	 *            the branch to set.
	 */
	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	/**
	 * Returns attendance type allowed for branch.
	 * 
	 * @return the attendanceType.
	 */
	public AttendanceTypeConstant getAttendanceType() {
		return this.attendanceType;
	}

	/**
	 * Sets attendance type allowed for branch.
	 * 
	 * @param attendanceType
	 *            the attendanceType to set.
	 */
	public void setAttendanceType(final AttendanceTypeConstant attendanceType) {
		this.attendanceType = attendanceType;
	}

	/**
	 * Returns maximum class strength allowed for branch.
	 * 
	 * @return the maximumClassStrength.
	 */
	public Integer getMaximumSectionStrength() {
		return this.maximumSectionStrength;
	}

	/**
	 * Sets maximum class strength allowed for branch.
	 * 
	 * @param maximumClassStrength
	 *            the maximumClassStrength to set
	 */
	public void setMaximumSectionStrength(final Integer maximumClassStrength) {
		this.maximumSectionStrength = maximumClassStrength;
	}

	/**
	 * Returns true if auto increase admission number is allowed for branch.
	 * 
	 * @return the autoIncreaseAdmissionNumber
	 */
	public boolean isAutoIncreaseAdmissionNumber() {
		return this.autoIncreaseAdmissionNumber;
	}

	/**
	 * Sets auto increase admission number indicator for branch.
	 * 
	 * @param autoIncreaseAdmissionNumber
	 *            the autoIncreaseAdmissionNumber to set
	 */
	public void setAutoIncreaseAdmissionNumber(final boolean autoIncreaseAdmissionNumber) {
		this.autoIncreaseAdmissionNumber = autoIncreaseAdmissionNumber;
	}

	/**
	 * Returns true if auto increase employee number is allowed for branch.
	 * 
	 * @return the autoIncreaseEmployeeNumber
	 */
	public boolean isAutoIncreaseEmployeeNumber() {
		return this.autoIncreaseEmployeeNumber;
	}

	/**
	 * Sets auto increase employee number indicator for branch.
	 * 
	 * @param autoIncreaseEmployeeNumber
	 *            the autoIncreaseEmployeeNumber to set
	 */
	public void setAutoIncreaseEmployeeNumber(final boolean autoIncreaseEmployeeNumber) {
		this.autoIncreaseEmployeeNumber = autoIncreaseEmployeeNumber;
	}

	/**
	 * @return the batchRequiredIndicator
	 */
	public boolean isBatchRequiredIndicator() {
		return this.batchRequiredIndicator;
	}

	/**
	 * @param batchRequiredIndicator
	 *            the batchRequiredIndicator to set
	 */
	public void setBatchRequiredIndicator(final boolean batchRequiredIndicator) {
		this.batchRequiredIndicator = batchRequiredIndicator;
	}

	/**
	 * @return the smsProvider
	 */
	public SMSProvider getSmsProvider() {
		return this.smsProvider;
	}

	/**
	 * @param smsProvider
	 *            the smsProvider to set
	 */
	public void setSmsProvider(final SMSProvider smsProvider) {
		this.smsProvider = smsProvider;
	}

	/**
	 * @return the absentReasonRequiredIndicator
	 */
	public boolean isAbsentReasonRequiredIndicator() {
		return this.absentReasonRequiredIndicator;
	}

	/**
	 * @param absentReasonRequiredIndicator
	 *            the absentReasonRequiredIndicator to set
	 */
	public void setAbsentReasonRequiredIndicator(final boolean absentReasonRequiredIndicator) {
		this.absentReasonRequiredIndicator = absentReasonRequiredIndicator;
	}

}
