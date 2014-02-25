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

import com.apeironsol.need.academics.model.SectionExamSubject;
import com.apeironsol.need.core.model.BranchFeeTypePeriodical;

/**
 * Comparator for {@link BranchFeeTypePeriodical}
 * 
 * @author Pradeep.
 */
public class SectionExamSubjectComparator implements Comparator<SectionExamSubject>, Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5246665397583922139L;

	public static enum Order {

		ID, SECTION_SUBJECT_ID;
	};

	private final Order	orderBy;

	private boolean		ascending	= true;

	public SectionExamSubjectComparator(final Order orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public int compare(final SectionExamSubject source, final SectionExamSubject target) {
		int result = 0;
		if (Order.ID.equals(this.orderBy) && (source.getId() != null) && (target.getId() != null)) {
			result = source.getId().compareTo(target.getId());
		} else if (Order.SECTION_SUBJECT_ID.equals(this.orderBy) && (source.getSectionSubject().getId() != null)
				&& (target.getSectionSubject().getId() != null)) {
			result = source.getSectionSubject().getId().compareTo(target.getSectionSubject().getId());
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
