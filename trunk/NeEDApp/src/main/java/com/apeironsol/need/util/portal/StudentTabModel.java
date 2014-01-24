package com.apeironsol.need.util.portal;

import java.io.Serializable;

import com.apeironsol.framework.NeEDTab;

public class StudentTabModel implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID		= -701578946962644288L;

	private final NeEDTab		personalTab				= new NeEDTab("personalTab");

	private final NeEDTab		paymentsTab				= new NeEDTab("paymentsTab");

	private final NeEDTab		studentAdditionalFeeTab	= new NeEDTab("studentAdditionalFeeTab");

	private final NeEDTab		studentPocketMoneyTab	= new NeEDTab("studentPocketMoneyTab");

	private final NeEDTab		statusHistoryTab		= new NeEDTab("statusHistoryTab");

	private final NeEDTab		transportationTab		= new NeEDTab("transportationTab");

	private final NeEDTab		notificationTab			= new NeEDTab("notificationTab");

	private final NeEDTab		academicsTab			= new NeEDTab("academicsTab");

	private final NeEDTab		attendanceTab			= new NeEDTab("attendanceTab");

	private final NeEDTab		hostelRoomTab			= new NeEDTab("hostelRoomTab");

	private final NeEDTab		reportCardTab			= new NeEDTab("reportCardTab");

	private NeEDTab				activeTab				= this.getPersonalTab();

	private final NeEDTab[]		tabsSequence			= new NeEDTab[] { this.getPersonalTab(), this.getPaymentsTab(), this.getStudentAdditionalFeeTab(),
			this.getStudentPocketMoneyTab(), this.getAcademicsTab(), this.getReportCardTab(), this.getAttendanceTab(), this.getHostelRoomTab(),
			this.getTransportationTab(), this.getNotificationTab(), this.getStatusHistoryTab() };

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

	public NeEDTab getPersonalTab() {
		return this.personalTab;
	}

	public NeEDTab getPaymentsTab() {
		return this.paymentsTab;
	}

	public NeEDTab getStudentAdditionalFeeTab() {
		return this.studentAdditionalFeeTab;
	}

	public NeEDTab getStudentPocketMoneyTab() {
		return this.studentPocketMoneyTab;
	}

	public NeEDTab getStatusHistoryTab() {
		return this.statusHistoryTab;
	}

	public NeEDTab getTransportationTab() {
		return this.transportationTab;
	}

	public NeEDTab getNotificationTab() {
		return this.notificationTab;
	}

	public NeEDTab getAcademicsTab() {
		return this.academicsTab;
	}

	public boolean isPersonalTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.personalTab);
	}

	public boolean isPaymentsTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.paymentsTab);
	}

	public boolean isStudentAdditionalFeeTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.studentAdditionalFeeTab);
	}

	public boolean isStudentPocketMoneyTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.studentPocketMoneyTab);
	}

	public boolean isStatusHistoryTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.statusHistoryTab);
	}

	public boolean isTransportationTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.transportationTab);
	}

	public boolean isNotificationTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.notificationTab);
	}

	public boolean isAcademicsTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.academicsTab);
	}

	public boolean isAttendanceTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.attendanceTab);
	}

	public boolean isHostelRoomTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.hostelRoomTab);
	}

	public boolean isReportCardTabActive() {
		return this.activeTab != null && this.activeTab.equals(this.reportCardTab);
	}

	/**
	 * @return the attendanceTab
	 */
	public NeEDTab getAttendanceTab() {
		return this.attendanceTab;
	}

	/**
	 * @return the hostelRoomTab
	 */
	public NeEDTab getHostelRoomTab() {
		return this.hostelRoomTab;
	}

	/**
	 * @return the reportCardTab
	 */
	public NeEDTab getReportCardTab() {
		return this.reportCardTab;
	}

}
