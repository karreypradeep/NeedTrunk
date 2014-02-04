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
import com.apeironsol.need.notifications.model.BatchLogMessage;

/**
 * Comparator for {@link BranchFeeTypePeriodical}
 * 
 * @author Pradeep.
 */
public class BatchLogMessageComparator implements Comparator<BatchLogMessage>, Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 7009309620742801686L;

	public static enum Order {

		ID, MESSAGE_SENT_TIME;
	};

	private final Order	orderBy;

	private boolean		ascending	= false;

	public BatchLogMessageComparator(final Order orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public int compare(final BatchLogMessage source, final BatchLogMessage target) {
		int result = 0;
		if (Order.ID.equals(this.orderBy) && (source.getId() != null) && (target.getId() != null)) {
			result = source.getId().compareTo(target.getId());
		} else if (Order.MESSAGE_SENT_TIME.equals(this.orderBy) && (source.getMessageSentTime() != null) && (target.getMessageSentTime() != null)) {
			result = source.getMessageSentTime().compareTo(target.getMessageSentTime());

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
