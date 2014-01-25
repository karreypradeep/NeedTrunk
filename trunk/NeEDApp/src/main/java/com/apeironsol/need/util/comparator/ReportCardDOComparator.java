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

import com.apeironsol.need.academics.dataobject.ReportCardDO;
import com.apeironsol.need.core.model.BranchFeeTypePeriodical;

/**
 * Comparator for {@link BranchFeeTypePeriodical}
 * 
 * @author Pradeep.
 */
public class ReportCardDOComparator implements Comparator<ReportCardDO>, Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -308373212915106743L;

	public static enum Order {

		CREATED_DATE;
	};

	private final Order	orderBy;

	private boolean		ascending	= true;

	public ReportCardDOComparator(final Order orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public int compare(final ReportCardDO source, final ReportCardDO target) {
		int result = 0;
		if (Order.CREATED_DATE.equals(this.orderBy) && source.getReportCard().getCreatedDate() != null && target.getReportCard().getCreatedDate() != null) {
			result = source.getReportCard().getCreatedDate().compareTo(target.getReportCard().getCreatedDate());
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
