/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.reporting.ro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.apeironsol.need.financial.model.BranchExpense;

/**
 * @author sunny
 *         Data object for expenses report.
 */
public class ExpenseRO implements Serializable{

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -5949273115050157881L;

	private List<BranchExpense>		branchExpenseList;

	/**
	 * @return the branchExpenseList
	 */
	public List<BranchExpense> getBranchExpenseList() {
		return this.branchExpenseList;
	}

	/**
	 * @param branchExpenseList the branchExpenseList to set
	 */
	public void setBranchExpenseList(final List<BranchExpense> branchExpenseList) {
		this.branchExpenseList = branchExpenseList;
	}

	/**
	 * @param branchExpenseList the branchExpenseList to set
	 */
	public void addBranchExpense(final  BranchExpense branchExpense) {
		if(this.branchExpenseList==null){
			this.branchExpenseList = new ArrayList<BranchExpense>();
		}
		this.branchExpenseList.add(branchExpense);
	}

}
