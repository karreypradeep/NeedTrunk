package com.apeironsol.need.notifications.portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.portal.AbstractBranchBean;
import com.apeironsol.need.notifications.model.BranchNotification;
import com.apeironsol.need.notifications.service.BranchNotificationService;
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.ApplicationException;

@Named
@Scope(value = "session")
public class BranchNotificationSettingsBean extends AbstractBranchBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long				serialVersionUID	= 8084159250205668843L;

	private Collection<BranchNotification>	branchNotifications;

	@Resource
	BranchNotificationService				branchNotificationService;

	private boolean							loadBranchNotificationFlag;

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
	 * @param branchNotifications
	 *            the branchNotifications to set
	 */
	public void addBranchNotification(final BranchNotification branchNotification) {
		if (this.branchNotifications == null) {
			this.branchNotifications = new ArrayList<BranchNotification>();
		}
		this.branchNotifications.add(branchNotification);
	}

	public void loadBranchNotifications() {
		if (this.loadBranchNotificationFlag) {
			this.setBranchNotifications(this.branchNotificationService.findBranchNotificationsByBranchId(this.sessionBean.getCurrentBranch().getId()));
			Map<NotificationSubTypeConstant, BranchNotification> map = new HashMap<NotificationSubTypeConstant, BranchNotification>();
			for (BranchNotification branchNotification : this.getBranchNotifications()) {
				map.put(branchNotification.getNotificationSubType(), branchNotification);
			}
			for (NotificationSubTypeConstant notificationSubTypeConstant : NotificationSubTypeConstant.getBrachNotifications()) {
				if (map.get(notificationSubTypeConstant) == null) {
					BranchNotification branchNotification = new BranchNotification();
					branchNotification.setBranch(this.sessionBean.getCurrentBranch());
					branchNotification.setNotificationSubType(notificationSubTypeConstant);
					this.addBranchNotification(branchNotification);
				}
			}
			this.loadBranchNotificationFlag = false;
		}
	}

	@Override
	public void onTabChange() {
		this.loadBranchNotificationFlag = true;
	}

	/**
	 * Saves the branch in database.
	 * 
	 * @return
	 */
	public String saveBranchNotifications() {
		try {
			this.setBranchNotifications(this.branchNotificationService.saveBranchNotifications(this.getBranchNotifications()));
		} catch (ApplicationException exception) {
			ViewExceptionHandler.handle(exception);
		}
		ViewUtil.addMessage("Branch rules saved successfully.", FacesMessage.SEVERITY_INFO);
		return null;
	}
}
