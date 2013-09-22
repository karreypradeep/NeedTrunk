/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.comparator;

import java.io.Serializable;
import java.util.Comparator;

import com.apeironsol.need.core.model.BranchFeeTypePeriodical;
import com.apeironsol.need.financial.model.BranchExpense;

/**
 * Comparator for {@link BranchFeeTypePeriodical}
 * 
 * @author Pradeep.
 */
public class BranchExpenseComparator implements Comparator<BranchExpense>, Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1054777258492582017L;

	public static enum Order {

		EXPENSE_DATE;
	};

	private final Order	orderBy;

	private boolean		ascending	= true;

	public BranchExpenseComparator(final Order orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public int compare(final BranchExpense source, final BranchExpense target) {
		int result = 0;
		if (Order.EXPENSE_DATE.equals(this.orderBy) && source.getExpenseDate() != null && target.getExpenseDate() != null) {
			result = source.getExpenseDate().compareTo(target.getExpenseDate());
		}
		return result != 0 ? this.ascending ? result : result == 1 ? -1 : 1 : result;
	}

	public void setAscending() {
		this.ascending = true;
	}

	public void setDescending() {
		this.ascending = false;
	}
}
