package com.apeironsol.need.financial.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.transportation.model.PickUpPointFeeCatalog;
import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for class fee
 * 
 * @author Pradeep
 */

@Entity
@Table(name = "STUDENT_FEE_TXN_DETAILS")
public class StudentFeeTransactionDetails extends BaseEntity implements Serializable {

	/**
	 * Universal serial version number.
	 */
	private static final long		serialVersionUID	= 6889673453522054463L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STUDENT_FEE_ID", nullable = false)
	private StudentFee				studentFee;

	@NotNull(message = "model.amount_mandatory")
	@Column(name = "AMOUNT", nullable = false)
	private Double					amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_LEVEL_FEE_CATALOG_ID")
	private BranchLevelFeeCatalog	branchLevelFeeCatalog;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "KLASS_LEVEL_FEE_CATALOG_ID")
	private KlassLevelFeeCatalog	klassLevelFeeCatalog;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STUDENT_LEVEL_FEE_CATALOG_ID")
	private StudentLevelFeeCatalog	studentLevelFeeCatalog;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PICKUP_POINT_FEE_CATALOG_ID")
	private PickUpPointFeeCatalog	pickUpPointFeeCatalog;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STUDENT_FEE_TXN_ID", nullable = false)
	private StudentFeeTransaction	studentFeeTransaction;

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	public KlassLevelFeeCatalog getKlassLevelFeeCatalog() {
		return this.klassLevelFeeCatalog;
	}

	public void setKlassLevelFeeCatalog(final KlassLevelFeeCatalog klassLevelFeeCatalog) {
		this.klassLevelFeeCatalog = klassLevelFeeCatalog;
	}

	public StudentFeeTransaction getStudentFeeTransaction() {
		return this.studentFeeTransaction;
	}

	public void setStudentFeeTransaction(final StudentFeeTransaction studentFeeTransaction) {
		this.studentFeeTransaction = studentFeeTransaction;
	}

	public BranchLevelFeeCatalog getBranchLevelFeeCatalog() {
		return this.branchLevelFeeCatalog;
	}

	public void setBranchLevelFeeCatalog(final BranchLevelFeeCatalog branchLevelFeeCatalog) {
		this.branchLevelFeeCatalog = branchLevelFeeCatalog;
	}

	public StudentLevelFeeCatalog getStudentLevelFeeCatalog() {
		return this.studentLevelFeeCatalog;
	}

	public void setStudentLevelFeeCatalog(final StudentLevelFeeCatalog studentLevelFeeCatalog) {
		this.studentLevelFeeCatalog = studentLevelFeeCatalog;
	}

	public StudentFee getStudentFee() {
		return this.studentFee;
	}

	public void setStudentFee(final StudentFee studentFee) {
		this.studentFee = studentFee;
	}

	public PickUpPointFeeCatalog getPickUpPointFeeCatalog() {
		return this.pickUpPointFeeCatalog;
	}

	public void setPickUpPointFeeCatalog(final PickUpPointFeeCatalog pickUpPointFeeCatalog) {
		this.pickUpPointFeeCatalog = pickUpPointFeeCatalog;
	}

}
