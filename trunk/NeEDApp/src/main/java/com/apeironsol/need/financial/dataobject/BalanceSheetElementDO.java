package com.apeironsol.need.financial.dataobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.apeironsol.need.util.constants.BalanceSheetElementTypeConstant;

/**
 * 
 * @author pradeep
 * 
 */
public class BalanceSheetElementDO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long							serialVersionUID				= 3846359971546080993L;

	private BalanceSheetElementTypeConstant				balanceSheetElementType;

	private String										description;

	private Collection<BalanceSheetElementTransaction>	balanceSheetElementTransactions	= null;

	/**
	 * @return the transactionAmount
	 */
	public double getAmount() {
		double transactionAmount = 0.0;
		if (this.balanceSheetElementTransactions != null) {
			for (final BalanceSheetElementTransaction balanceSheetElementTransaction : this.balanceSheetElementTransactions) {
				transactionAmount = transactionAmount + balanceSheetElementTransaction.getTransactionAmount();
			}
		}
		return transactionAmount;
	}

	/**
	 * @return the balanceSheetElementType
	 */
	public BalanceSheetElementTypeConstant getBalanceSheetElementType() {
		return this.balanceSheetElementType;
	}

	/**
	 * @param balanceSheetElementType
	 *            the balanceSheetElementType to set
	 */
	public void setBalanceSheetElementType(final BalanceSheetElementTypeConstant balanceSheetElementType) {
		this.balanceSheetElementType = balanceSheetElementType;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	public Collection<BalanceSheetElementTransaction> getBalanceSheetElementTransactions() {
		return this.balanceSheetElementTransactions;
	}

	public void setBalanceSheetElementTransactions(final Collection<BalanceSheetElementTransaction> balanceSheetElementTransactions) {
		this.balanceSheetElementTransactions = balanceSheetElementTransactions;
	}

	public void addBalanceSheetElementTransaction(final BalanceSheetElementTransaction balanceSheetElementTransaction) {
		if (this.balanceSheetElementTransactions == null) {
			this.balanceSheetElementTransactions = new ArrayList<BalanceSheetElementTransaction>();
		}
		this.balanceSheetElementTransactions.add(balanceSheetElementTransaction);
	}

}
