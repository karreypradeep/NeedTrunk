/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.procurement.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.financial.model.BranchBankAccount;
import com.apeironsol.need.financial.model.BranchCreditAccount;
import com.apeironsol.need.util.constants.BranchAccountTypeConstant;
import com.apeironsol.need.util.constants.PaymentMethodConstant;
import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for purchase invoice.
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "PURCHASE_INVOICE", uniqueConstraints = { @UniqueConstraint(columnNames = { "INVOICE_NUMBER", "BRANCH_ID" }, name = "UQ_BRANCH_ID_AND_INV_NO_FOR_PURINV") })
public class PurchaseInvoice extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class
	 */
	private static final long			serialVersionUID	= 6498028058874729194L;

	@Column(name = "INVOICE_NUMBER", length = 50, nullable = false)
	private String						invoiceNumber;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PURCHASE_ORDER_ID", nullable = false)
	private PurchaseOrder				purchaseOrder;

	@Column(name = "AMOUNT", nullable = false)
	private Double						amount;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch						branch;

	@Column(name = "INVOICE_DATE")
	@Temporal(TemporalType.DATE)
	private Date						invoiceDate;

	@Column(name = "REMARKS", length = 100, nullable = false)
	private String						remarks;

	@Basic
	@Column(name = "PAYMENT_METHOD")
	@Enumerated(EnumType.STRING)
	private PaymentMethodConstant		paymentMethod;

	@Column(name = "CHEQUE_NR")
	private String						chequeNumber;

	@ManyToOne
	@JoinColumn(name = "BRANCH_BANK_ACCOUNT_ID")
	private BranchBankAccount			branchBankAccount;

	@Column(name = "TRANSACTION_NR", length = 50, nullable = false)
	private String						transactionNr;

	@Column(name = "ACCOUNT_TYPE")
	@Enumerated(EnumType.STRING)
	private BranchAccountTypeConstant	branchAccountType;

	@ManyToOne
	@JoinColumn(name = "BRANCH_CREDIT_ACCOUNT_ID")
	private BranchCreditAccount			branchCreditAccount;

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return this.invoiceNumber;
	}

	/**
	 * @param invoiceNumber
	 *            the invoiceNumber to set
	 */
	public void setInvoiceNumber(final String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return this.amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the purchaseOrder
	 */
	public PurchaseOrder getPurchaseOrder() {
		return this.purchaseOrder;
	}

	/**
	 * @param purchaseOrder
	 *            the purchaseOrder to set
	 */
	public void setPurchaseOrder(final PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	/**
	 * @return the branch
	 */
	public Branch getBranch() {
		return this.branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	/**
	 * @return the invoiceDate
	 */
	public Date getInvoiceDate() {
		return this.invoiceDate;
	}

	/**
	 * @param invoiceDate
	 *            the invoiceDate to set
	 */
	public void setInvoiceDate(final Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return this.remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(final String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the branchBankAccount
	 */
	public BranchBankAccount getBranchBankAccount() {
		return this.branchBankAccount;
	}

	/**
	 * @param branchBankAccount
	 *            the branchBankAccount to set
	 */
	public void setBranchBankAccount(final BranchBankAccount branchBankAccount) {
		this.branchBankAccount = branchBankAccount;
	}

	/**
	 * @return the paymentMethod
	 */
	public PaymentMethodConstant getPaymentMethod() {
		return this.paymentMethod;
	}

	/**
	 * @param paymentMethod
	 *            the paymentMethod to set
	 */
	public void setPaymentMethod(final PaymentMethodConstant paymentMethod) {
		this.paymentMethod = paymentMethod;
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
	 * @return the transactionNr
	 */
	public String getTransactionNr() {
		return this.transactionNr;
	}

	/**
	 * @param transactionNr
	 *            the transactionNr to set
	 */
	public void setTransactionNr(final String transactionNr) {
		this.transactionNr = transactionNr;
	}

	/**
	 * @return the branchAccountType
	 */
	public BranchAccountTypeConstant getBranchAccountType() {
		return this.branchAccountType;
	}

	/**
	 * @param branchAccountType
	 *            the branchAccountType to set
	 */
	public void setBranchAccountType(final BranchAccountTypeConstant branchAccountType) {
		this.branchAccountType = branchAccountType;
	}

	/**
	 * @return the branchCreditAccount
	 */
	public BranchCreditAccount getBranchCreditAccount() {
		return this.branchCreditAccount;
	}

	/**
	 * @param branchCreditAccount
	 *            the branchCreditAccount to set
	 */
	public void setBranchCreditAccount(final BranchCreditAccount branchCreditAccount) {
		this.branchCreditAccount = branchCreditAccount;
	}

}
