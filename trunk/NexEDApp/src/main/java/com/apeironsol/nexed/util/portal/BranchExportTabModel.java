package com.apeironsol.nexed.util.portal;

import java.io.Serializable;

import com.apeironsol.framework.NexEDTab;

public class BranchExportTabModel implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1628613886510407131L;

	private final NexEDTab	exportStudentsTab	= new NexEDTab("exportStudentsTab");

	private final NexEDTab	exportAdmissionsTab	= new NexEDTab("exportAdmissionsTab");

	private NexEDTab			activeTab			= this.getExportStudentsTab();

	private final NexEDTab[]	tabsSequence		= new NexEDTab[] { this.getExportStudentsTab(), this.getExportAdmissionsTab() };

	public int getActiveTabIndex() {

		if (this.getActiveTab() == null) {
			return 0;
		}

		int value = 0;
		for (NexEDTab tab : this.tabsSequence) {

			if (tab.equals(this.getActiveTab())) {
				break;
			}
			if (tab.isRendered()) {
				value++;
			}

		}
		return value;

	}

	public NexEDTab getActiveTab() {
		return this.activeTab;
	}

	public void setActiveTab(final NexEDTab activeTab) {
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
	public NexEDTab getExportStudentsTab() {
		return this.exportStudentsTab;
	}

	/**
	 * @return the exportAdmissionsTab
	 */
	public NexEDTab getExportAdmissionsTab() {
		return this.exportAdmissionsTab;
	}

}
