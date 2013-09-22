/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.util.constants.PaymentFrequencyConstant;
import com.apeironsol.framework.BaseEntity;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Entity class for Class fee
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "STUDENT_LEVEL_FEE")
public class StudentLevelFee extends BaseEntity implements Serializable {

	/**
	 * Universal serial version number.
	 */
	private static final long					serialVersionUID	= 7095074517619690496L;

	@NotNull(message = "model.amount_mandatory")
	@Min(value = 0, message = "model.amount_cannot_be_lessthen_zero")
	@Column(name = "AMOUNT", nullable = false)
	private Double								amount;

	@NotNull(message = "model.frequency_mandatory")
	@Basic
	@Column(name = "PAYMENT_FREQUENCY", length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	private PaymentFrequencyConstant			paymentFrequency;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STUDENT_ACADEMIC_YEAR_ID", nullable = false)
	private StudentAcademicYear					studentAcademicYear;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BUILD_BLOCK_ID", nullable = false)
	private BuildingBlock						buildingBlock;

	@OneToMany(mappedBy = "studentLevelFee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<StudentLevelFeeCatalog>	studentLevelFeeCatalogs;

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	public PaymentFrequencyConstant getPaymentFrequency() {
		return this.paymentFrequency;
	}

	public void setPaymentFrequency(final PaymentFrequencyConstant paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}

	public BuildingBlock getBuildingBlock() {
		return this.buildingBlock;
	}

	public void setBuildingBlock(final BuildingBlock buildingBlock) {
		this.buildingBlock = buildingBlock;
	}

	@Override
	public void validate() throws InvalidArgumentException {
		super.validate();
		Double totalAmountPayments = 0d;
		if (this.studentLevelFeeCatalogs != null && !this.studentLevelFeeCatalogs.isEmpty()) {
			for (StudentLevelFeeCatalog studentLeveFeeCatalog : this.studentLevelFeeCatalogs) {
				totalAmountPayments += studentLeveFeeCatalog.getAmount();
			}
			if (Math.round(totalAmountPayments) != Math.round(this.amount)) {
				throw new InvalidArgumentException("Total payments amount does not match with fee amount.");
			}
		}

	}

	public Collection<StudentLevelFeeCatalog> getStudentLevelFeeCatalogs() {
		return this.studentLevelFeeCatalogs;
	}

	public void setStudentLevelFeeCatalogs(final Collection<StudentLevelFeeCatalog> studentLevelFeeCatalogs) {
		this.studentLevelFeeCatalogs = studentLevelFeeCatalogs;
	}

	public StudentAcademicYear getStudentAcademicYear() {
		return this.studentAcademicYear;
	}

	public void setStudentAcademicYear(final StudentAcademicYear studentAcademicYear) {
		this.studentAcademicYear = studentAcademicYear;
	}
}
