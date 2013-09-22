/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.reporting.ro;

import java.io.Serializable;

/**
 * @author sunny
 *         Data object for financial report.
 */
public class FinancialRO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -5549774381794342670L;

	private ExpenseRO		expenseRO;

	/**
	 * @return the expenseRO
	 */
	public ExpenseRO getExpenseRO() {
		return this.expenseRO;
	}

	/**
	 * @param expenseRO the expenseRO to set
	 */
	public void setExpenseRO(final ExpenseRO expenseRO) {
		this.expenseRO = expenseRO;
	}


}
