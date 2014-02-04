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
import com.apeironsol.need.notifications.model.BatchLog;

/**
 * Comparator for {@link BranchFeeTypePeriodical}
 * 
 * @author Pradeep.
 */
public class BatchLogComparator implements Comparator<BatchLog>, Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 154687699288806564L;

	public static enum Order {

		ID, EXECUTION_TIME;
	};

	private final Order	orderBy;

	private boolean		ascending	= false;

	public BatchLogComparator(final Order orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public int compare(final BatchLog source, final BatchLog target) {
		int result = 0;
		if (Order.ID.equals(this.orderBy) && (source.getId() != null) && (target.getId() != null)) {
			result = source.getId().compareTo(target.getId());
		} else if (Order.EXECUTION_TIME.equals(this.orderBy) && (source.getExecutionTime() != null) && (target.getExecutionTime() != null)) {
			result = source.getExecutionTime().compareTo(target.getExecutionTime());

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
