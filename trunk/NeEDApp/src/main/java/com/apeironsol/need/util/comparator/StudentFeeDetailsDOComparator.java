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

import com.apeironsol.need.util.dataobject.StudentFeeDetailsDO;

/**
 * Comparator for branch level fee catalog objects.
 * 
 * @author Pardeep
 */
public class StudentFeeDetailsDOComparator implements Comparator<StudentFeeDetailsDO>, Serializable {

	private static final long	serialVersionUID		= -1054777258492582017L;

	public static final String	DUE_DATE				= "DueDate";

	public static final String	TOTAL_FEE_DUE_AMOUNT	= "total_fee_due_amount";

	private final String		orderString;

	public StudentFeeDetailsDOComparator(final String orderBy) {
		this.orderString = orderBy;
	}

	@Override
	public int compare(final StudentFeeDetailsDO studentFeeDetailsDO1, final StudentFeeDetailsDO studentFeeDetailsDO2) {
		int result = 0;
		if (DUE_DATE.equals(this.orderString)) {
			result = studentFeeDetailsDO1.getDueDate().compareTo(studentFeeDetailsDO2.getDueDate());
		} else if (TOTAL_FEE_DUE_AMOUNT.equals(this.orderString)) {
			result = studentFeeDetailsDO1.getTotalFeeDueAmount().compareTo(studentFeeDetailsDO2.getTotalFeeDueAmount());
		}
		return result;
	}

}
