package com.apeironsol.need.util.portal;

import java.io.Serializable;

import com.apeironsol.framework.NeEDTab;

public class SectionTabModel implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -701578946962644288L;

	private final NeEDTab	sectionTab			= new NeEDTab("sectionTab");

	private final NeEDTab	subjectsTab			= new NeEDTab("subjectsTab");

	private final NeEDTab	studentsTab			= new NeEDTab("studentsTab");

	private final NeEDTab	examsTab			= new NeEDTab("examsTab");

	private final NeEDTab	attendanceTab		= new NeEDTab("attendanceTab");

	private final NeEDTab	facilityTab			= new NeEDTab("facilityTab");

	private final NeEDTab	timetableTab		= new NeEDTab("timetableTab");

	private final NeEDTab	feeCollectedTab		= new NeEDTab("feeCollectedTab");

	private final NeEDTab	reportsTab			= new NeEDTab("reportsTab");

	private final NeEDTab	notificationsTab	= new NeEDTab("notificationsTab");

	private NeEDTab			activeTab			= this.getSectionTab();

	private final NeEDTab[]	tabsSequence		= new NeEDTab[] { this.getSectionTab(), this.getSubjectsTab(), this.getStudentsTab(),
			this.getExamsTab(), this.getAttendanceTab(), this.getFacilityTab(), this.getTimetableTab(), this.getFeeCollectedTab(), this.getReportsTab(),
			this.getNotificationsTab()				};

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

	public NeEDTab getSectionTab() {
		return this.sectionTab;
	}

	public NeEDTab getSubjectsTab() {
		return this.subjectsTab;
	}

	public NeEDTab getStudentsTab() {
		return this.studentsTab;
	}

	public NeEDTab getExamsTab() {
		return this.examsTab;
	}

	public NeEDTab getAttendanceTab() {
		return this.attendanceTab;
	}

	public NeEDTab getFacilityTab() {
		return this.facilityTab;
	}

	public NeEDTab getTimetableTab() {
		return this.timetableTab;
	}

	public NeEDTab getFeeCollectedTab() {
		return this.feeCollectedTab;
	}

	public NeEDTab getReportsTab() {
		return this.reportsTab;
	}

	public NeEDTab getNotificationsTab() {
		return this.notificationsTab;
	}

	public boolean isSectionTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.sectionTab);
	}

	public boolean isSubjectsTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.subjectsTab);
	}

	public boolean isStudentsTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.studentsTab);
	}

	public boolean isExamsTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.examsTab);
	}

	public boolean isAttendanceTabTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.attendanceTab);
	}

	public boolean isFacilityTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.facilityTab);
	}

	public boolean isTimetableTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.timetableTab);
	}

	public boolean isFeeCollectedTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.feeCollectedTab);
	}

	public boolean isReportsTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.reportsTab);
	}

	public boolean isNotificationsTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.notificationsTab);
	}

}
