package com.apeironsol.need.core.portal;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import com.apeironsol.need.academics.model.Exam;
import com.apeironsol.need.academics.service.SectionExamService;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.service.AttendanceService;
import com.apeironsol.need.notifications.model.BranchNotification;
import com.apeironsol.need.notifications.service.BranchNotificationService;
import com.apeironsol.need.notifications.service.NotificationService;
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;
import com.apeironsol.need.util.constants.NotificationTypeConstant;

public abstract class AbstractNotificationBean extends AbstractPortalBean {

	/**
	 * 
	 */
	private static final long	serialVersionUID					= 4843110124821431769L;

	/**
	 * Notification sub type.
	 */
	private String				notificationText;

	/**
	 * Variable to decide what has to be displayed.
	 */
	private ViewAction			viewActionString					= ViewAction.VIEW_BATCH_LOGS;

	@Resource
	public SectionExamService	sectionExamService;

	private boolean				disaplyExamDropdownForNotification	= false;

	private Collection<Exam>	examsForNotification;

	private Exam				selectedExamForNotification;

	/**
	 * Enum class used for deciding what has to be displayed on screen.
	 * 
	 * @author pradeep
	 * 
	 */
	public enum ViewAction {
		VIEW_BATCH_LOGS, VIEW_BATCH_LOG_MESSAGES, VIEW_SEND_NOTIFICATION;
	}

	@Resource
	public NotificationService						notificationService;

	/**
	 * Notification sub type.
	 */
	private NotificationSubTypeConstant				notificationSubTypeConstant;

	private NotificationTypeConstant				notificationTypeConstant;

	@Resource
	BranchNotificationService						branchNotificationService;

	private Collection<BranchNotification>			branchNotifications;

	private Collection<NotificationSubTypeConstant>	notificationSubTypeAvailable;

	private AcademicYear							academicYearForNotification;

	@Resource
	public AttendanceService						attendanceService;

	/**
	 * Event listener for Notification sub type.
	 * 
	 * @return
	 */
	public String handleNotificationSubTypeChange() {
		if (NotificationSubTypeConstant.EXAM_ABSENT_NOTIFICATION.equals(this.notificationSubTypeConstant)
				|| NotificationSubTypeConstant.EXAM_RESULT_NOTIFICATION.equals(this.notificationSubTypeConstant)
				|| NotificationSubTypeConstant.EXAM_SCHEDULE_NOTIFICATION.equals(this.notificationSubTypeConstant)) {
			this.disaplyExamDropdownForNotification = true;
			loadExamsForNotifications();
		} else {
			this.disaplyExamDropdownForNotification = false;
		}
		return null;
	}

	/**
	 * @return the disaplyExamDropdownForNotification
	 */
	public boolean isDisaplyExamDropdownForNotification() {
		return this.disaplyExamDropdownForNotification;
	}

	/**
	 * @param disaplyExamDropdownForNotification
	 *            the disaplyExamDropdownForNotification to set
	 */
	public void setDisaplyExamDropdownForNotification(final boolean disaplyExamDropdownForNotification) {
		this.disaplyExamDropdownForNotification = disaplyExamDropdownForNotification;
	}

	public abstract void loadExamsForNotifications();

	/**
	 * @return the examsForNotification
	 */
	public Collection<Exam> getExamsForNotification() {
		return this.examsForNotification;
	}

	/**
	 * @param examsForNotification
	 *            the examsForNotification to set
	 */
	public void setExamsForNotification(final Collection<Exam> examsForNotification) {
		this.examsForNotification = examsForNotification;
	}

	/**
	 * @return the selectedExamForNotification
	 */
	public Exam getSelectedExamForNotification() {
		return this.selectedExamForNotification;
	}

	/**
	 * @param selectedExamForNotification
	 *            the selectedExamForNotification to set
	 */
	public void setSelectedExamForNotification(final Exam selectedExamForNotification) {
		this.selectedExamForNotification = selectedExamForNotification;
	}

	/**
	 * @return the notificationSubTypeConstant
	 */
	public NotificationSubTypeConstant getNotificationSubTypeConstant() {
		return this.notificationSubTypeConstant;
	}

	/**
	 * @param notificationSubTypeConstant
	 *            the notificationSubTypeConstant to set
	 */
	public void setNotificationSubTypeConstant(final NotificationSubTypeConstant notificationSubTypeConstant) {
		this.notificationSubTypeConstant = notificationSubTypeConstant;
	}

	public void loadBranchNotification() {
		setBranchNotifications(this.branchNotificationService.findBranchNotificationsByBranchId(this.sessionBean.getCurrentBranch().getId()));
	}

	/**
	 * @return the branchNotifications
	 */
	public Collection<BranchNotification> getBranchNotifications() {
		return this.branchNotifications;
	}

	/**
	 * @param branchNotifications
	 *            the branchNotifications to set
	 */
	public void setBranchNotifications(final Collection<BranchNotification> branchNotifications) {
		this.branchNotifications = branchNotifications;
	}

	/**
	 * @return the notificationSubTypeAvailable
	 */
	public Collection<NotificationSubTypeConstant> getNotificationSubTypeAvailable() {
		return this.notificationSubTypeAvailable;
	}

	/**
	 * @param notificationSubTypeAvailable
	 *            the notificationSubTypeAvailable to set
	 */
	public void setNotificationSubTypeAvailable(final Collection<NotificationSubTypeConstant> notificationSubTypeAvailable) {
		this.notificationSubTypeAvailable = notificationSubTypeAvailable;
	}

	public void getBranchNotificationByNotificationType() {
		this.notificationSubTypeAvailable = new ArrayList<NotificationSubTypeConstant>();
		for (final BranchNotification branchNotification : this.branchNotifications) {
			if (!branchNotification.getNotificationSubType().isImplicitMessage()) {
				if (NotificationTypeConstant.EMAIL_NOTIFICATION.equals(this.notificationTypeConstant)
						&& ((null != branchNotification.getEmailIndicator()) && branchNotification.getEmailIndicator())) {
					this.notificationSubTypeAvailable.add(branchNotification.getNotificationSubType());
				} else if (NotificationTypeConstant.SMS_NOTIFICATION.equals(this.notificationTypeConstant) && (null != branchNotification.getSmsIndicator())
						&& branchNotification.getSmsIndicator()) {
					this.notificationSubTypeAvailable.add(branchNotification.getNotificationSubType());
				} else if ((this.notificationTypeConstant == null)
						&& (((null != branchNotification.getSmsIndicator()) && branchNotification.getSmsIndicator()) || ((null != branchNotification
								.getEmailIndicator()) && branchNotification.getEmailIndicator()))) {
					this.notificationSubTypeAvailable.add(branchNotification.getNotificationSubType());
				}
			}
		}
	}

	public String handleNotificationTypeChange() {
		getBranchNotificationByNotificationType();
		this.notificationSubTypeConstant = null;
		handleNotificationSubTypeChange();
		return null;
	}

	public abstract void onTabChange();

	/**
	 * @return the academicYearForNotification
	 */
	public AcademicYear getAcademicYearForNotification() {
		return this.academicYearForNotification;
	}

	/**
	 * @param academicYearForNotification
	 *            the academicYearForNotification to set
	 */
	public void setAcademicYearForNotification(final AcademicYear academicYearForNotification) {
		this.academicYearForNotification = academicYearForNotification;
	}

	/**
	 * @return the notificationTypeConstant
	 */
	public NotificationTypeConstant getNotificationTypeConstant() {
		return this.notificationTypeConstant;
	}

	/**
	 * @param notificationTypeConstant
	 *            the notificationTypeConstant to set
	 */
	public void setNotificationTypeConstant(final NotificationTypeConstant notificationTypeConstant) {
		this.notificationTypeConstant = notificationTypeConstant;
	}

	public String handleAcademicYearChange() {
		this.notificationTypeConstant = null;
		handleNotificationTypeChange();
		return null;
	}

	/**
	 * @return the notificationText
	 */
	public String getNotificationText() {
		return this.notificationText;
	}

	/**
	 * @param notificationText
	 *            the notificationText to set
	 */
	public void setNotificationText(final String notificationText) {
		if ((notificationText == null) || notificationText.trim().isEmpty()) {
			this.notificationText = null;
		} else {
			this.notificationText = notificationText;
		}
	}

	public String setViewSendNotification() {
		setAcademicYearForNotification(null);
		setNotificationTypeConstant(null);
		setNotificationSubTypeConstant(null);
		setNotificationText(null);
		setSelectedExamForNotification(null);
		setDisaplyExamDropdownForNotification(false);
		setViewActionString(ViewAction.VIEW_SEND_NOTIFICATION);
		return null;
	}

	/**
	 * @return the viewActionString
	 */
	public ViewAction getViewActionString() {
		return this.viewActionString;
	}

	/**
	 * @param viewActionString
	 *            the viewActionString to set
	 */
	public void setViewActionString(final ViewAction viewActionString) {
		this.viewActionString = viewActionString;
	}
}
