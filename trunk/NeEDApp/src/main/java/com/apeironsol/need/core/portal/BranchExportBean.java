/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 */
package com.apeironsol.need.core.portal;

/**
 * View courses class.
 * 
 * @author Pradeep
 */
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.util.portal.BranchExportTabModel;

@Named
@Scope("session")
public class BranchExportBean extends AbstractTabbedBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long			serialVersionUID		= 3167233761941786538L;

	private   BranchExportTabModel	branchExportTabModel	= new BranchExportTabModel();

	@Override
	public void onTabChange() {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the branchExportTabModel
	 */
	public BranchExportTabModel getBranchExportTabModel() {
		return branchExportTabModel;
	}

	/**
	 * @param branchExportTabModel the branchExportTabModel to set
	 */
	public void setBranchExportTabModel(BranchExportTabModel branchExportTabModel) {
		this.branchExportTabModel = branchExportTabModel;
	}

}
