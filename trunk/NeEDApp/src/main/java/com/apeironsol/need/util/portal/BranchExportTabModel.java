package com.apeironsol.need.util.portal;

import java.io.Serializable;

import com.apeironsol.framework.NeEDTab;

public class BranchExportTabModel implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1628613886510407131L;

	private final NeEDTab	exportStudentsTab	= new NeEDTab("exportStudentsTab");

	private final NeEDTab	exportAdmissionsTab	= new NeEDTab("exportAdmissionsTab");

	private NeEDTab			activeTab			= this.getExportStudentsTab();

	private final NeEDTab[]	tabsSequence		= new NeEDTab[] { this.getExportStudentsTab(), this.getExportAdmissionsTab() };

	public int getActiveTabIndex() {

		if (this.getActiveTab() == null) {
			return 0;
		}

		int value = 0;
		for (NeEDTab tab : this.tabsSequence) {

			if (tab.equals(this.getActiveTab())) {
				break;
			}
			if (tab.isRendered()) {
				value++;
			}

		}
		return value;

	}

	public NeEDTab getActiveTab() {
		return this.activeTab;
	}

	public void setActiveTab(final NeEDTab activeTab) {
		this.activeTab = activeTab;
	}

	public boolean isExportStudentsTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.exportStudentsTab);
	}

	public boolean isExportAdmissionsTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.exportAdmissionsTab);
	}

	/**
	 * @return the exportStudentsTab
	 */
	public NeEDTab getExportStudentsTab() {
		return this.exportStudentsTab;
	}

	/**
	 * @return the exportAdmissionsTab
	 */
	public NeEDTab getExportAdmissionsTab() {
		return this.exportAdmissionsTab;
	}

}
