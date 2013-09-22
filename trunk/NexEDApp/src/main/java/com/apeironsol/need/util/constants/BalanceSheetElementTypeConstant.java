/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.constants;

/**
 * Enums for Action constants
 * 
 * @author Pradeep
 */
public enum BalanceSheetElementTypeConstant {

	INCOME("income"), EXPENSE("expense");

	private String	id;

	BalanceSheetElementTypeConstant(final String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}
}
