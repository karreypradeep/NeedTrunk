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

import com.apeironsol.need.financial.model.KlassLevelFeeCatalog;

/**
 * Comparator for class fee payment objects.
 * 
 * @author Pradeep.
 */
public class KlassFeePaymentComparator implements Comparator<KlassLevelFeeCatalog>, Serializable {

	private static final long	serialVersionUID	= -1054777258492582017L;

	public static final String	DUE_DATE			= "DueDate";

	public static final String	AMOUNT				= "Amount";

	private final String		orderString;

	public KlassFeePaymentComparator(final String orderBy) {
		this.orderString = orderBy;
	}

	@Override
	public int compare(final KlassLevelFeeCatalog klassFeePayment1, final KlassLevelFeeCatalog klassFeePayment2) {
		int result = 0;
		if (DUE_DATE.equals(this.orderString)) {
			result = klassFeePayment1.getDueDate().compareTo(klassFeePayment2.getDueDate());
		} else if (AMOUNT.equals(this.orderString)) {
			result = klassFeePayment1.getAmount().compareTo(klassFeePayment2.getAmount());
		}
		return result;
	}
}
