package com.apeironsol.need.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.apeironsol.need.financial.model.FinancialAccount;
import com.apeironsol.need.util.constants.TransactionTypeConstant;
import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "TRANSACTION")
public class Transaction extends BaseEntity implements Serializable {

	/**
	 * Universal version id for this class.
	 */
	private static final long		serialVersionUID	= 8248653604856844190L;

	@NotNull(message = "model.payment_amount_mandatory")
	@Column(name = "AMOUNT", nullable = false)
	private Double					amount;

	@NotNull(message = "model.payment_date_mandatory")
	@Column(name = "TRANSACTION_DATE", nullable = false)
	private Date					transactionDate;

	@NotNull(message = "model.transaction_amount_mandatory")
	@Column(name = "TYPE", nullable = false, length = 40)
	private TransactionTypeConstant	type;

	@NotEmpty(message = "model.transaction_description_mandatory")
	@Column(name = "DESCRIPTION", nullable = false, length = 250)
	private String					description;

	@NotNull(message = "model.financial_account_information_mandatory")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE })
	@JoinColumn(name = "FINANCIAL_ACCOUNT_ID", nullable = false)
	private FinancialAccount		financialAccount;

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	public Date getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(final Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public TransactionTypeConstant getType() {
		return this.type;
	}

	public void setType(final TransactionTypeConstant type) {
		this.type = type;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public FinancialAccount getFinancialAccount() {
		return this.financialAccount;
	}

	public void setFinancialAccount(final FinancialAccount financialAccount) {
		this.financialAccount = financialAccount;
	}

}
