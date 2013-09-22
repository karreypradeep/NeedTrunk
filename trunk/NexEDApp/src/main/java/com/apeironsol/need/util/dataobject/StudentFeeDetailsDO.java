package com.apeironsol.need.util.dataobject;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.financial.model.BranchLevelFeeCatalog;
import com.apeironsol.need.financial.model.KlassLevelFeeCatalog;
import com.apeironsol.need.financial.model.StudentFee;
import com.apeironsol.need.financial.model.StudentFeeTransactionDetails;
import com.apeironsol.need.financial.model.StudentLevelFeeCatalog;
import com.apeironsol.need.transportation.model.PickUpPointFeeCatalog;
import com.apeironsol.need.util.constants.FeePaymentStatusConstant;
import com.apeironsol.need.util.constants.PaymentMethodConstant;

public class StudentFeeDetailsDO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long							serialVersionUID	= 6643681543413069859L;

	private BranchLevelFeeCatalog						branchLevelFeeCatalog;

	private KlassLevelFeeCatalog						klassLevelFeeCatalog;

	private StudentLevelFeeCatalog						studentLevelFeeCatalog;

	private PickUpPointFeeCatalog						pickUpPointFeeCatalog;

	private Collection<StudentFeeTransactionDetails>	studentFeeTransactionDetails;

	private Date										dueDate;

	private Double										fee;

	private Double										totalFeePaymentAmount;

	private Double										totalFeePaymentPendingAmount;

	private Double										totalFeeDueAmount;

	private Double										totalFeeDeductedAmount;

	private Double										totalFeeDeductionRequestAmount;

	private Double										totalFeeRefundAmount;

	private Double										totalFeeRefundRequestAmount;

	private Double										totalFeeRefundPendingAmount;

	private String										feeName;

	private Double										payingAmount;

	private Double										refundAmount;

	private Double										deductingAmount;

	private StudentFee									studentFee;

	private PaymentMethodConstant						paymentMethod;

	private String										externalReceiptNr;

	private String										chequeNumber;

	private String										bankName;

	private String										bankBranch;

	private Date										feePaidDate;

	private FeePaymentStatusConstant					feePaymentStatusConstant;

	public KlassLevelFeeCatalog getKlassLevelFeeCatalog() {
		return this.klassLevelFeeCatalog;
	}

	public void setKlassLevelFeeCatalog(final KlassLevelFeeCatalog klassLevelFeeCatalog) {
		this.klassLevelFeeCatalog = klassLevelFeeCatalog;
	}

	public Collection<StudentFeeTransactionDetails> getStudentFeeTransactions() {
		return this.studentFeeTransactionDetails;
	}

	public void setStudentFeeTransactions(final Collection<StudentFeeTransactionDetails> studentFeeTransactions) {
		this.studentFeeTransactionDetails = studentFeeTransactions;
	}

	public Double getFee() {
		return this.fee;
	}

	public void setFee(final Double fee) {
		this.fee = fee;
	}

	public Double getTotalFeePaymentAmount() {
		return this.totalFeePaymentAmount;
	}

	public void setTotalFeePaymentAmount(final Double feePaid) {
		this.totalFeePaymentAmount = feePaid;
	}

	public Double getTotalFeeDueAmount() {
		return this.totalFeeDueAmount;
	}

	public void setTotalFeeDueAmount(final Double totalFeeDueAmount) {
		this.totalFeeDueAmount = totalFeeDueAmount;
	}

	public Double getTotalFeeDeductedAmount() {
		return this.totalFeeDeductedAmount;
	}

	public void setTotalFeeDeductedAmount(final Double feeDeducted) {
		this.totalFeeDeductedAmount = feeDeducted;
	}

	public Double getTotalFeeRefundAmount() {
		return this.totalFeeRefundAmount;
	}

	public void setTotalFeeRefundAmount(final Double totalFeeRefundAmount) {
		this.totalFeeRefundAmount = totalFeeRefundAmount;
	}

	public String getFeeName() {
		return this.feeName;
	}

	public void setFeeName(final String feeName) {
		this.feeName = feeName;
	}

	public Double getPayingAmount() {
		return this.payingAmount;
	}

	public void setPayingAmount(final Double payingAmount) {
		this.payingAmount = payingAmount;
	}

	public Double getRefundAmount() {
		return this.refundAmount;
	}

	public void setRefundAmount(final Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Double getDeductingAmount() {
		return this.deductingAmount;
	}

	public void setDeductingAmount(final Double deductingAmount) {
		this.deductingAmount = deductingAmount;
	}

	public StudentFee getStudentFee() {
		return this.studentFee;
	}

	public void setStudentFee(final StudentFee studentFee) {
		this.studentFee = studentFee;
	}

	public Double getTotalNetAmountPaid() {
		return this.totalFeePaymentAmount - this.totalFeeRefundAmount;
	}

	public Double getTotalNetAmount() {
		return this.fee - this.totalFeeDeductedAmount;
	}

	public Double getTotalNetDue() {
		return this.getTotalNetAmount() - this.getTotalNetAmountPaid();
	}

	public PaymentMethodConstant getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(final PaymentMethodConstant paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the externalReceiptNr
	 */
	public String getExternalReceiptNr() {
		return this.externalReceiptNr;
	}

	/**
	 * @param externalReceiptNr
	 *            the externalReceiptNr to set
	 */
	public void setExternalReceiptNr(final String externalReceiptNr) {
		this.externalReceiptNr = externalReceiptNr;
	}

	/**
	 * @return the chequeNumber
	 */
	public String getChequeNumber() {
		return this.chequeNumber;
	}

	/**
	 * @param chequeNumber
	 *            the chequeNumber to set
	 */
	public void setChequeNumber(final String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return this.bankName;
	}

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(final String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the bankBranch
	 */
	public String getBankBranch() {
		return this.bankBranch;
	}

	/**
	 * @param bankBranch
	 *            the bankBranch to set
	 */
	public void setBankBranch(final String bankBranch) {
		this.bankBranch = bankBranch;
	}

	/**
	 * @return the feePaymentStatusConstant
	 */
	public FeePaymentStatusConstant getFeePaymentStatusConstant() {
		return this.feePaymentStatusConstant;
	}

	/**
	 * @param feePaymentStatusConstant
	 *            the feePaymentStatusConstant to set
	 */
	public void setFeePaymentStatusConstant(final FeePaymentStatusConstant feePaymentStatusConstant) {
		this.feePaymentStatusConstant = feePaymentStatusConstant;
	}

	/**
	 * @return the feePaidDate
	 */
	public Date getFeePaidDate() {
		return this.feePaidDate;
	}

	/**
	 * @param feePaidDate
	 *            the feePaidDate to set
	 */
	public void setFeePaidDate(final Date feePaidDate) {
		this.feePaidDate = feePaidDate;
	}

	public Double getTotalFeePaymentPendingAmount() {
		return this.totalFeePaymentPendingAmount;
	}

	public void setTotalFeePaymentPendingAmount(final Double totalFeePaymentPendingAmount) {
		this.totalFeePaymentPendingAmount = totalFeePaymentPendingAmount;
	}

	public Double getTotalFeeDeductionRequestAmount() {
		return this.totalFeeDeductionRequestAmount;
	}

	public void setTotalFeeDeductionRequestAmount(final Double totalFeeDeductionRequestAmount) {
		this.totalFeeDeductionRequestAmount = totalFeeDeductionRequestAmount;
	}

	public Double getTotalFeeRefundRequestAmount() {
		return this.totalFeeRefundRequestAmount;
	}

	public void setTotalFeeRefundRequestAmount(final Double totalFeeRefundRequestAmount) {
		this.totalFeeRefundRequestAmount = totalFeeRefundRequestAmount;
	}

	public Double getTotalFeeRefundPendingAmount() {
		return this.totalFeeRefundPendingAmount;
	}

	public void setTotalFeeRefundPendingAmount(final Double totalFeeRefundPendingAmount) {
		this.totalFeeRefundPendingAmount = totalFeeRefundPendingAmount;
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

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(final Date dueDate) {
		this.dueDate = dueDate;
	}

	public PickUpPointFeeCatalog getPickUpPointFeeCatalog() {
		return this.pickUpPointFeeCatalog;
	}

	public void setPickUpPointFeeCatalog(final PickUpPointFeeCatalog pickUpPointFeeCatalog) {
		this.pickUpPointFeeCatalog = pickUpPointFeeCatalog;
	}

}
