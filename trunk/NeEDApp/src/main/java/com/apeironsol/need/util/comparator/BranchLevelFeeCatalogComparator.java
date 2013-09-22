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

import com.apeironsol.need.financial.model.BranchLevelFeeCatalog;

/**
 * Comparator for branch level fee catalog objects.
 * 
 * @author Pradeep.
 */
public class BranchLevelFeeCatalogComparator implements Comparator<BranchLevelFeeCatalog>, Serializable {

	private static final long	serialVersionUID	= -1054777258492582017L;

	public static final String	DUE_DATE			= "DueDate";

	public static final String	AMOUNT				= "Amount";

	private final String		orderString;

	public BranchLevelFeeCatalogComparator(final String orderBy) {
		this.orderString = orderBy;
	}

	@Override
	public int compare(final BranchLevelFeeCatalog branchLeveFeeCatalog1, final BranchLevelFeeCatalog branchLeveFeeCatalog2) {
		int result = 0;
		if (DUE_DATE.equals(this.orderString)) {
			result = branchLeveFeeCatalog1.getDueDate().compareTo(branchLeveFeeCatalog2.getDueDate());
		} else if (AMOUNT.equals(this.orderString)) {
			result = branchLeveFeeCatalog1.getAmount().compareTo(branchLeveFeeCatalog2.getAmount());
		}
		return result;
	}

}
