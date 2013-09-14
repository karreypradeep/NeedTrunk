package com.apeironsol.nexed.util.portal;

import java.io.Serializable;

import com.apeironsol.framework.NexEDTab;

public class SectionTabModel implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -701578946962644288L;

	private final NexEDTab	sectionTab			= new NexEDTab("sectionTab");

	private final NexEDTab	subjectsTab			= new NexEDTab("subjectsTab");

	private final NexEDTab	studentsTab			= new NexEDTab("studentsTab");

	private final NexEDTab	examsTab			= new NexEDTab("examsTab");

	private final NexEDTab	attendanceTab		= new NexEDTab("attendanceTab");

	private final NexEDTab	facilityTab			= new NexEDTab("facilityTab");

	private final NexEDTab	timetableTab		= new NexEDTab("timetableTab");

	private final NexEDTab	feeCollectedTab		= new NexEDTab("feeCollectedTab");

	private final NexEDTab	reportsTab			= new NexEDTab("reportsTab");

	private final NexEDTab	notificationsTab	= new NexEDTab("notificationsTab");

	private NexEDTab			activeTab			= this.getSectionTab();

	private final NexEDTab[]	tabsSequence		= new NexEDTab[] { this.getSectionTab(), this.getSubjectsTab(), this.getStudentsTab(),
			this.getExamsTab(), this.getAttendanceTab(), this.getFacilityTab(), this.getTimetableTab(), this.getFeeCollectedTab(), this.getReportsTab(),
			this.getNotificationsTab()				};

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

	public NexEDTab getSectionTab() {
		return this.sectionTab;
	}

	public NexEDTab getSubjectsTab() {
		return this.subjectsTab;
	}

	public NexEDTab getStudentsTab() {
		return this.studentsTab;
	}

	public NexEDTab getExamsTab() {
		return this.examsTab;
	}

	public NexEDTab getAttendanceTab() {
		return this.attendanceTab;
	}

	public NexEDTab getFacilityTab() {
		return this.facilityTab;
	}

	public NexEDTab getTimetableTab() {
		return this.timetableTab;
	}

	public NexEDTab getFeeCollectedTab() {
		return this.feeCollectedTab;
	}

	public NexEDTab getReportsTab() {
		return this.reportsTab;
	}

	public NexEDTab getNotificationsTab() {
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
