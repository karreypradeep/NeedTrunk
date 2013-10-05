package com.apeironsol.need.core.portal.util;

import java.io.Serializable;

public abstract class TabbedBean implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -8682394994317809066L;

	/**
	 * Unique serial version id for this class.
	 */
	private int					activeTabIndex;

	public int getActiveTabIndex() {
		return this.activeTabIndex;
	}

	public void setActiveTabIndex(final int activeTabIndex) {
		this.activeTabIndex = activeTabIndex;
	}

	public abstract void onTabChange();

}
