/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.reporting.ro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.apeironsol.need.core.model.Branch;

/**
 * @author sunny
 *         Data object for branch report.
 * 
 */
public class BranchRO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -8000524361807484357L;

	private Branch				branch;

	private List<KlassRO>		klassROList;

	/**
	 * @return the branch
	 */
	public Branch getBranch() {
		return this.branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	/**
	 * @return the klassROList
	 */
	public List<KlassRO> getKlassROList() {
		return this.klassROList;
	}

	/**
	 * @param klassROList
	 *            the klassROList to set
	 */
	public void setKlassROList(final List<KlassRO> klassROList) {
		this.klassROList = klassROList;
	}

	/**
	 * @param sectionROList the sectionROList to set
	 */
	public void addKlassRO(final  KlassRO klassRO) {
		if(this.klassROList==null){
			this.klassROList = new ArrayList<KlassRO>();
		}
		this.klassROList.add(klassRO);
	}

}
