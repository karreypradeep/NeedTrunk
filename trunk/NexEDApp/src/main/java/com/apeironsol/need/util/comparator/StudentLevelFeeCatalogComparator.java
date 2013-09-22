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

import com.apeironsol.need.financial.model.StudentLevelFeeCatalog;

/**
 * Comparator for branch level fee catalog objects.
 * 
 * @author Pradeep.
 */
public class StudentLevelFeeCatalogComparator implements Comparator<StudentLevelFeeCatalog>, Serializable {

	private static final long	serialVersionUID	= -1054777258492582017L;

	public static final String	DUE_DATE			= "DueDate";

	public static final String	AMOUNT				= "Amount";

	private final String		orderString;

	public StudentLevelFeeCatalogComparator(final String orderBy) {
		this.orderString = orderBy;
	}

	@Override
	public int compare(final StudentLevelFeeCatalog obj1, final StudentLevelFeeCatalog obj2) {
		int result = 0;
		if (DUE_DATE.equals(this.orderString)) {
			result = obj1.getDueDate().compareTo(obj2.getDueDate());
		} else if (AMOUNT.equals(this.orderString)) {
			result = obj1.getAmount().compareTo(obj2.getAmount());
		}
		return result;
	}

}
