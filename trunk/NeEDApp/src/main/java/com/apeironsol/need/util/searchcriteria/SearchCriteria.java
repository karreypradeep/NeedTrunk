/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.util.searchcriteria;

import java.io.Serializable;

import com.apeironsol.need.core.model.Branch;

/**
 * Search criteria interface.
 * 
 * @author Pradeep
 */
public interface SearchCriteria extends Serializable {

	/**
	 * Resets search criteria.
	 */
	void resetSeacrhCriteria();

	/**
	 * Returns true if search criteria is null or empty.
	 */
	boolean isSearchCriteriaIsEmpty();

	/**
	 * Return branch.
	 * 
	 * @return branch.
	 */
	Branch getBranch();

	/**
	 * Return branch.
	 * 
	 * @return branch.
	 */
	void setBranch(final Branch branch);

}
