package com.apeironsol.nexed.util.portal;

import java.io.Serializable;

import com.apeironsol.framework.NexEDTab;

public class StudentTabModel implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID		= -701578946962644288L;

	private final NexEDTab	personalTab				= new NexEDTab("personalTab");

	private final NexEDTab	paymentsTab				= new NexEDTab("paymentsTab");

	private final NexEDTab	studentAdditionalFeeTab	= new NexEDTab("studentAdditionalFeeTab");

	private final NexEDTab	studentPocketMoneyTab	= new NexEDTab("studentPocketMoneyTab");

	private final NexEDTab	statusHistoryTab		= new NexEDTab("statusHistoryTab");

	private final NexEDTab	transportationTab		= new NexEDTab("transportationTab");

	private final NexEDTab	notificationTab			= new NexEDTab("notificationTab");

	private final NexEDTab	academicsTab			= new NexEDTab("academicsTab");

	private final NexEDTab	attendanceTab			= new NexEDTab("attendanceTab");

	private NexEDTab			activeTab				= this.getPersonalTab();

	private final NexEDTab[]	tabsSequence			= new NexEDTab[] { this.getPersonalTab(), this.getPaymentsTab(), this.getStudentAdditionalFeeTab(),
			this.getStudentPocketMoneyTab(), this.getAcademicsTab(), this.getAttendanceTab(), this.getTransportationTab(), this.getNotificationTab(),
			this.getStatusHistoryTab()					};

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

	public NexEDTab getPersonalTab() {
		return this.personalTab;
	}

	public NexEDTab getPaymentsTab() {
		return this.paymentsTab;
	}

	public NexEDTab getStudentAdditionalFeeTab() {
		return this.studentAdditionalFeeTab;
	}

	public NexEDTab getStudentPocketMoneyTab() {
		return this.studentPocketMoneyTab;
	}

	public NexEDTab getStatusHistoryTab() {
		return this.statusHistoryTab;
	}

	public NexEDTab getTransportationTab() {
		return this.transportationTab;
	}

	public NexEDTab getNotificationTab() {
		return this.notificationTab;
	}

	public NexEDTab getAcademicsTab() {
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

	/**
	 * @return the attendanceTab
	 */
	public NexEDTab getAttendanceTab() {
		return this.attendanceTab;
	}

}
