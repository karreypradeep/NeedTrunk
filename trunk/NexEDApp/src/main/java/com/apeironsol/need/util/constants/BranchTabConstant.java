package com.apeironsol.need.util.constants;

public enum BranchTabConstant {

	BRANCH(0), ASSEMBLY(1), NOTIFICATIONS(2), RULES(3), FINANCIALS(4), REPORTS(5);

	private int	tabIndex;

	BranchTabConstant(final int tabIndex) {
		this.setTabIndex(tabIndex);
	}

	public int getTabIndex() {
		return this.tabIndex;
	}

	public void setTabIndex(final int tabIndex) {
		this.tabIndex = tabIndex;
	}
}
