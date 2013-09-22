package com.apeironsol.need.core.portal;

import java.awt.event.ActionEvent;

public abstract class AbstractTabbedBean extends AbstractPortalBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long	serialVersionUID	= 6076214811423472635L;

	public int getActiveTabIndex() {
		return this.sessionBean.getActiveTabIndex();
	}

	public void setActiveTabIndex(final int activeTabIndex) {
		this.sessionBean.setActiveTabIndex(activeTabIndex);
	}

	public void menuItemActionListener(final ActionEvent e) {
		e.getID();
	}

	public abstract void onTabChange();

}
