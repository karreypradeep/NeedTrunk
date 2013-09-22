/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.portal;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.model.DualListModel;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.portal.SessionBean;
import com.apeironsol.need.transportation.model.Route;
import com.apeironsol.need.transportation.model.Vehicle;
import com.apeironsol.need.transportation.service.VehicleService;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.BusinessException;

/**
 * 
 * @author sandeep
 * 
 */
@Named
@Scope(value = "session")
public class VehicleBean extends AbstractTransportationBean {

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 1137156271662328507L;

	private static final Logger							log					= Logger.getLogger(VehicleBean.class);

	private Vehicle					vehicle;

	@Resource
	private VehicleService			vehicleService;

	@Resource
	SessionBean						sessionBean;

	private DualListModel<Route>	routeDualListModel;

	private final List<Route>		routeSource			= new ArrayList<Route>();

	private final List<Route>		routeTarget			= new ArrayList<Route>();

	/**
	 * If true new pickup point button and pickup points table are displayed.
	 */
	private boolean					renderNewVehicleButton;

	/**
	 * If true new pickup point label is displayed. Else modify pickup point
	 * label is displayed.
	 */
	private boolean					createNewVehicle;

	/**
	 * @return the routeDualListModel
	 */
	public DualListModel<Route> getRouteDualListModel() {
		return this.routeDualListModel;
	}

	/**
	 * @param routeDualListModel
	 *            the routeDualListModel to set
	 */
	public void setRouteDualListModel(final DualListModel<Route> routeDualListModel) {
		this.routeDualListModel = routeDualListModel;
	}

	/**
	 * @return the routeSource
	 */
	public List<Route> getRouteSource() {
		return this.routeSource;
	}

	/**
	 * @return the routeTarget
	 */
	public List<Route> getRouteTarget() {
		return this.routeTarget;
	}

	/**
	 * @return the renderNewVehicleButton
	 */
	public boolean isRenderNewVehicleButton() {
		return this.renderNewVehicleButton;
	}

	/**
	 * @param renderNewVehicleButton
	 *            the renderNewVehicleButton to set
	 */
	public void setRenderNewVehicleButton(final boolean renderNewVehicleButton) {
		this.renderNewVehicleButton = renderNewVehicleButton;
	}

	/**
	 * @return the createNewVehicle
	 */
	public boolean isCreateNewVehicle() {
		return this.createNewVehicle;
	}

	/**
	 * @param createNewVehicle
	 *            the createNewVehicle to set
	 */
	public void setCreateNewVehicle(final boolean createNewVehicle) {
		this.createNewVehicle = createNewVehicle;
	}

	/**
	 * @return the vehicle
	 */
	public Vehicle getVehicle() {
		return this.vehicle;
	}

	/**
	 * @param vehicle
	 *            the vehicle to set
	 */
	public void setVehicle(final Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public void loadInitVehicleData() {
		this.renderNewVehicleButton = true;
		this.loadAllVehicles();
	}

	public String addNewVehicle() {
		this.vehicle = new Vehicle();
		this.loadAllRoutes();

		this.routeSource.clear();
		this.routeTarget.clear();
		this.routeSource.addAll(this.routes);
		this.routeDualListModel = new DualListModel<Route>(this.routeSource, this.routeTarget);

		this.renderNewVehicleButton = false;
		this.createNewVehicle = true;

		return null;
	}

	public String saveVehicle() {
		try {
			this.vehicle.setBranch(this.sessionBean.getCurrentBranch());
			this.vehicle.setRoutes(this.routeDualListModel.getTarget());
			this.vehicleService.saveVehicle(this.vehicle);
			this.renderNewVehicleButton = true;
			this.loadAllVehicles();

			if (this.createNewVehicle) {
				ViewUtil.addMessage("Vehicle saved sucessfully.", FacesMessage.SEVERITY_INFO);
			} else {
				ViewUtil.addMessage("Vehicle modified sucessfully.", FacesMessage.SEVERITY_INFO);
			}
		} catch (BusinessException ex) {
			log.info(ex.getMessage());
			ViewExceptionHandler.handle(ex);
		}
		return null;
	}

	public String cancleVehicle() {
		this.renderNewVehicleButton = true;
		return null;
	}

	public String showVehicle(final Vehicle vehicle) {
		try {
			this.vehicle = vehicle;
			this.vehicle = this.vehicleService.findVehicleDetailsByVehicleId(this.vehicle.getId());
			this.loadAllRoutes();

			this.routeSource.clear();
			this.routeTarget.clear();
			this.routeTarget.addAll(this.vehicle.getRoutes());

			List<String> targetRoutesList = new ArrayList<String>();
			for (Route targerRoute : this.vehicle.getRoutes()) {
				targetRoutesList.add(targerRoute.getName());
			}
			for (Route sourceRoute : this.routes) {
				if (!targetRoutesList.contains(sourceRoute.getName())) {
					this.routeSource.add(sourceRoute);
				}
			}
			this.routeDualListModel = new DualListModel<Route>(this.routeSource, this.routeTarget);

			this.renderNewVehicleButton = false;
			this.createNewVehicle = false;
		} catch (BusinessException ex) {
			log.info(ex.getMessage());
			ViewExceptionHandler.handle(ex);
		}
		return null;
	}

	public String deleteVehicle(final Vehicle vehicle) {
		try {
			this.vehicle = vehicle;
			this.vehicleService.deleteVehicle(this.vehicle.getId());
			this.loadAllVehicles();
			this.renderNewVehicleButton = true;
			ViewUtil.addMessage("Vehicle deleted sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (BusinessException ex) {
			log.info(ex.getMessage());
			ViewExceptionHandler.handle(ex);
		}
		return null;
	}

	@Override
	public void onTabChange() {

	}

}
