/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.portal;

import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.transportation.model.PickUpPoint;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.framework.exception.BusinessException;

/**
 * View PickUpPoint class.
 * 
 * @author sandeep
 * 
 */
@Named
@Scope(value = "session")
public class PickUpPointBean extends AbstractTransportationBean {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -2122818111027091309L;

	private static final Logger	log					= Logger.getLogger(PickUpPointBean.class);

	/**
	 * If true new pickup point button and pickup points table are displayed.
	 */
	private boolean				renderNewPickUpPointButton;

	/**
	 * If true new pickup point label is displayed. Else modify pickup point
	 * label is displayed.
	 */
	private boolean				createNewPickUpPoint;

	/**
	 * @return the renderNewPickUpPointButton
	 */
	public boolean isRenderNewPickUpPointButton() {
		return this.renderNewPickUpPointButton;
	}

	/**
	 * @param renderNewPickUpPointButton
	 *            the renderNewPickUpPointButton to set
	 */
	public void setRenderNewPickUpPointButton(final boolean renderNewPickUpPointButton) {
		this.renderNewPickUpPointButton = renderNewPickUpPointButton;
	}

	/**
	 * @return the createNewPickUpPoint
	 */
	public boolean isCreateNewPickUpPoint() {
		return this.createNewPickUpPoint;
	}

	/**
	 * @param createNewPickUpPoint
	 *            the createNewPickUpPoint to set
	 */
	public void setCreateNewPickUpPoint(final boolean createNewPickUpPoint) {
		this.createNewPickUpPoint = createNewPickUpPoint;
	}

	public void loadInitPickUpPointData() {
		this.renderNewPickUpPointButton = true;
		this.loadAllPickUpPoints();
	}

	public String addNewPickUpPoint() {
		this.pickUpPoint = new PickUpPoint();
		this.renderNewPickUpPointButton = false;
		this.createNewPickUpPoint = true;
		return null;
	}

	public String savePickUpPoint() {
		try {
			this.pickUpPoint.setBranch(this.sessionBean.getCurrentBranch());
			this.pickUpPoint = this.pickUpPointService.savePickUpPoint(this.pickUpPoint);
			this.loadAllPickUpPoints();
			this.loadPickUpPointFees();
			ViewUtil.addMessage("PickUpPoint saved sucessfully.", FacesMessage.SEVERITY_INFO);
			this.setViewAction(true);
			this.setNewAction(false);
		} catch (BusinessException ex) {
			ViewExceptionHandler.handle(ex);
		}
		return null;
	}

	public String canclePickUpPoint() {
		this.loadAllPickUpPoints();
		this.renderNewPickUpPointButton = true;
		return null;
	}

	public String showPickUpPoint() {
		try {
			this.pickUpPoint = this.pickUpPointService.findPickUpPointById(this.pickUpPoint.getId());

			this.loadPickUpPointFees();

		} catch (BusinessException ex) {
			log.info(ex.getMessage());
			ViewExceptionHandler.handle(ex);
		}
		return null;
	}

	public String deletePickUpPoint(final PickUpPoint pickUpPoint) {
		try {
			this.pickUpPoint = pickUpPoint;
			this.pickUpPointService.deletePickUpPoint(this.pickUpPoint.getId());
			this.loadAllPickUpPoints();
			this.renderNewPickUpPointButton = true;
			ViewUtil.addMessage("PickUpPoint deleted sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (BusinessException ex) {
			log.info(ex.getMessage());
			ViewExceptionHandler.handle(ex);
		}
		return null;
	}

	/**
	 * @return the pickUpPoints
	 */
	@Override
	public Collection<PickUpPoint> getPickUpPoints() {
		return this.pickUpPoints;
	}

	@Override
	public void onTabChange() {

	}

	public boolean isDisableSubTab() {
		if (this.pickUpPoint != null && this.pickUpPoint.getId() != null) {
			return false;
		}
		return true;
	}

	public void doneCreatingPickUpPointFee() {
		this.renderNewPickUpPointButton = true;
		this.loadAllPickUpPoints();
		this.setActiveTabIndex(0);
		if (this.createNewPickUpPoint) {
			ViewUtil.addMessage("PickUpPoint created sucessfully.", FacesMessage.SEVERITY_INFO);
		} else {
			ViewUtil.addMessage("PickUpPoint modified sucessfully.", FacesMessage.SEVERITY_INFO);
		}
	}

	public void cancleCreatingPickUpPointFee() {
		this.renderNewPickUpPointButton = true;
		this.loadAllPickUpPoints();
		this.setActiveTabIndex(0);
	}

	/**
	 * Gets user group for given id.
	 * 
	 * @param userId
	 *            user Id
	 * @return User group
	 */
	public PickUpPoint getPickUpPointById(final Long pickUpPointId) {
		PickUpPoint result = null;
		try {
			if (this.pickUpPoints == null || this.pickUpPoints.size() == 0) {
				this.loadAllPickUpPoints();
			}
			for (PickUpPoint pickUpPoint : this.pickUpPoints) {
				if (pickUpPoint.getId().equals(pickUpPointId)) {
					result = pickUpPoint;
					break;
				}
			}
		} catch (Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		return result;
	}

	public void activatePickUpPoint() {
		try {
			this.pickUpPoint = this.pickUpPointService.activatePickUpPoint(this.pickUpPoint);
			ViewUtil.addMessage("PickUpPoint has been activated sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (ApplicationException e) {
			this.pickUpPoint = this.pickUpPointService.findPickUpPointById(this.pickUpPoint.getId());
			ViewExceptionHandler.handle(e);
		} catch (Throwable e) {
			this.pickUpPoint = this.pickUpPointService.findPickUpPointById(this.pickUpPoint.getId());
			ViewExceptionHandler.handle(e);
		}
	}

	public void deactivatePickUpPoint() {
		try {
			this.pickUpPoint = this.pickUpPointService.deactivatePickUpPoint(this.pickUpPoint);
			ViewUtil.addMessage("Class is set to development sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (ApplicationException e) {
			this.pickUpPoint = this.pickUpPointService.findPickUpPointById(this.pickUpPoint.getId());
			ViewExceptionHandler.handle(e);
		} catch (Throwable e) {
			this.pickUpPoint = this.pickUpPointService.findPickUpPointById(this.pickUpPoint.getId());
			ViewExceptionHandler.handle(e);
		}
	}

}
