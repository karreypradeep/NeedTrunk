/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.constants;

import java.util.ArrayList;
import java.util.List;

import com.apeironsol.need.util.EnumUtil;
import com.apeironsol.need.util.portal.ViewUtil;

/**
 * Enums for student status constants.
 * 
 * @author Pradeep
 */
public enum StudentFeeTransactionStatusConstant implements Labeled {

	PAYMENT_PENDING("payment_pending"),
	PAYMENT_PROCESSED("payment_processed"),
	REFUND_REQUEST("refund_request"),
	REJECT_REFUND("reject_refund"),
	REFUND_PENDING("refund_pending"),
	REFUND_PROCESSED("refund_processed"),
	DEDUCTION_REQUEST("deduction_request"),
	REJECT_DEDUCTION("reject_deduction"),
	DEDUCTION_PROCESSED("decuction_processed"),
	TRANSACTOIN_CANCELLED("transactoin_cancelled");

	private String	label;

	StudentFeeTransactionStatusConstant(final String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return ViewUtil.getEnumLabel(this.label);
	}

	@Override
	public void setLabel(final String label) {
		this.label = label;
	}

	public static StudentFeeTransactionStatusConstant[] getValuesSortedByLabels() {
		return EnumUtil.getEnumsSortedByLabels(StudentFeeTransactionStatusConstant.class).toArray(
				new StudentFeeTransactionStatusConstant[StudentFeeTransactionStatusConstant.values().length]);
	}

	private static List<StudentFeeTransactionStatusConstant>	statusForRequestAndPending	= new ArrayList<StudentFeeTransactionStatusConstant>();

	private static List<StudentFeeTransactionStatusConstant>	statusProcessed				= new ArrayList<StudentFeeTransactionStatusConstant>();

	public static List<StudentFeeTransactionStatusConstant> getAllStatusForRequestAndPending() {
		return statusForRequestAndPending;
	}

	public static List<StudentFeeTransactionStatusConstant> getAllProcessedStatus() {
		return statusProcessed;
	}

	static {
		statusForRequestAndPending.add(PAYMENT_PENDING);
		statusForRequestAndPending.add(REFUND_REQUEST);
		statusForRequestAndPending.add(REFUND_PENDING);
		statusForRequestAndPending.add(DEDUCTION_REQUEST);

		statusProcessed.add(PAYMENT_PROCESSED);
		statusProcessed.add(DEDUCTION_PROCESSED);
		statusProcessed.add(REFUND_PROCESSED);
	}

}
