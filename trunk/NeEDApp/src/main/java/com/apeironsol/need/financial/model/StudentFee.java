/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.model;

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
import javax.validation.constraints.NotNull;

import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.transportation.model.PickUpPointFee;
import com.apeironsol.need.util.constants.FeeClassificationLevelConstant;
import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for Class fee
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "STUDENT_FEE")
public class StudentFee extends BaseEntity implements Serializable {

	/**
	 * Universal serial version number.
	 */
	private static final long				serialVersionUID	= 7095074517619690496L;

	@Basic
	@Column(name = "FEE_CLASSFN", length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	private FeeClassificationLevelConstant	feeClassificationLevel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_LEVEL_FEE_ID")
	private BranchLevelFee					branchLevelFee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "KLASS_LEVEL_FEE_ID")
	private KlassLevelFee					klassLevelFee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STUDENT_LEVEL_FEE_ID")
	private StudentLevelFee					studentLevelFee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PICKUP_POINT_FEE_ID")
	private PickUpPointFee					pickUpPointFee;

	@NotNull(message = "model.student_academic_year_mandatory")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STUDENT_ACADEMIC_YEAR_ID", nullable = false)
	private StudentAcademicYear				studentAcademicYear;

	public KlassLevelFee getKlassFee() {
		return this.klassLevelFee;
	}

	public void setKlassFee(final KlassLevelFee klassLevelFee) {
		this.klassLevelFee = klassLevelFee;
	}

	public StudentAcademicYear getStudentAcademicYear() {
		return this.studentAcademicYear;
	}

	public void setStudentAcademicYear(final StudentAcademicYear studentAcademicYear) {
		this.studentAcademicYear = studentAcademicYear;
	}

	public BranchLevelFee getBranchLevelFee() {
		return this.branchLevelFee;
	}

	public void setBranchLevelFee(final BranchLevelFee branchLevelFee) {
		this.branchLevelFee = branchLevelFee;
	}

	public StudentLevelFee getStudentLevelFee() {
		return this.studentLevelFee;
	}

	public void setStudentLevelFee(final StudentLevelFee studentLevelFee) {
		this.studentLevelFee = studentLevelFee;
	}

	public FeeClassificationLevelConstant getFeeClassificationLevel() {
		return this.feeClassificationLevel;
	}

	public void setFeeClassificationLevel(final FeeClassificationLevelConstant feeClassificationLevel) {
		this.feeClassificationLevel = feeClassificationLevel;
	}

	public PickUpPointFee getPickUpPointFee() {
		return this.pickUpPointFee;
	}

	public void setPickUpPointFee(final PickUpPointFee pickUpPointFee) {
		this.pickUpPointFee = pickUpPointFee;
	}

}
