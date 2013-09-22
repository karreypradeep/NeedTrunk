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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.apeironsol.need.core.model.Transaction;
import com.apeironsol.need.util.constants.FinancialAccountTypeConstant;
import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "FINANCIAL_ACCOUNT")
public class FinancialAccount extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long				serialVersionUID	= 9156586045865299712L;

	@NotEmpty(message = "model.account_number_mandatory")
	@Column(name = "ACCOUNT_NUMBER", nullable = false)
	private String							accountNumber;

	@NotNull(message = "model.account_type_mandatory")
	@Basic
	@Enumerated(EnumType.STRING)
	@Column(name = "ACCOUNT_TYPE", nullable = false, length = 40)
	private FinancialAccountTypeConstant	accountType;

	@OneToMany(mappedBy = "financialAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Collection<Transaction>			transactions;

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(final String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public FinancialAccountTypeConstant getAccountType() {
		return this.accountType;
	}

	public void setAccountType(final FinancialAccountTypeConstant accountType) {
		this.accountType = accountType;
	}

	public Collection<Transaction> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(final Collection<Transaction> transactions) {
		this.transactions = transactions;
	}

}
