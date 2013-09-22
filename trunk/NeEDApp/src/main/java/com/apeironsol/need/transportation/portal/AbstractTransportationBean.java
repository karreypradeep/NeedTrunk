package com.apeironsol.need.transportation.portal;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;

import org.apache.log4j.Logger;

import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.transportation.model.PickUpPoint;
import com.apeironsol.need.transportation.model.PickUpPointFee;
import com.apeironsol.need.transportation.model.Route;
import com.apeironsol.need.transportation.model.Vehicle;
import com.apeironsol.need.transportation.service.PickUpPointFeeCatalogService;
import com.apeironsol.need.transportation.service.PickUpPointFeeService;
import com.apeironsol.need.transportation.service.PickUpPointService;
import com.apeironsol.need.transportation.service.RouteService;
import com.apeironsol.need.transportation.service.VehicleService;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.BusinessException;

public abstract class AbstractTransportationBean extends AbstractTabbedBean {

	/**
	 * 
	 */
	private static final long				serialVersionUID	= -5831556641764126987L;

	private static final Logger				log					= Logger.getLogger(PickUpPointFeeBean.class);

	protected Collection<PickUpPoint>		pickUpPoints		= new ArrayList<PickUpPoint>();

	protected Collection<PickUpPoint>		activePickUpPoints	= new ArrayList<PickUpPoint>();

	protected Collection<Route>				routes				= new ArrayList<Route>();

	protected Collection<Vehicle>			vehicles			= new ArrayList<Vehicle>();

	@Resource
	protected PickUpPointService			pickUpPointService;

	@Resource
	protected RouteService					routeService;

	@Resource
	protected VehicleService				vehicleService;

	@Resource
	protected PickUpPointFeeService			pickUpPointFeeService;

	@Resource
	protected PickUpPointFeeCatalogService	pickUpPointFeeCatalogService;

	protected Collection<PickUpPointFee>	pickUpPointFees;

	protected PickUpPoint					pickUpPoint;

	/**
	 * @return the activePickUpPoints
	 */
	public Collection<PickUpPoint> getActivePickUpPoints() {
		return this.activePickUpPoints;
	}

	/**
	 * @param activePickUpPoints
	 *            the activePickUpPoints to set
	 */
	public void setActivePickUpPoints(final Collection<PickUpPoint> activePickUpPoints) {
		this.activePickUpPoints = activePickUpPoints;
	}

	/**
	 * @return the pickUpPoints
	 */
	public Collection<PickUpPoint> getPickUpPoints() {
		return this.pickUpPoints;
	}

	/**
	 * @param pickUpPoints
	 *            the pickUpPoints to set
	 */
	public void setPickUpPoints(final Collection<PickUpPoint> pickUpPoints) {
		this.pickUpPoints = pickUpPoints;
	}

	/**
	 * @return the routes
	 */
	public Collection<Route> getRoutes() {
		return this.routes;
	}

	/**
	 * @param vehicles
	 *            the vehicles to set
	 */
	public void setVehicles(final Collection<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	/**
	 * @param routes
	 *            the routes to set
	 */
	public void setRoutes(final Collection<Route> routes) {
		this.routes = routes;
	}

	/**
	 * @return the vehicles
	 */
	public Collection<Vehicle> getVehicles() {
		return this.vehicles;
	}

	public void loadAllPickUpPoints() {
		try {
			this.pickUpPoints = this.pickUpPointService.findPickUpPointsByBranchId(this.sessionBean.getCurrentBranch()
					.getId());
		} catch (BusinessException ex) {
			ViewExceptionHandler.handle(ex);
		}
	}

	public void loadAllActivePickUpPoints() {
		try {
			this.activePickUpPoints = this.pickUpPointService.findActivePickUpPointsByBranchId(this.sessionBean
					.getCurrentBranch().getId());
		} catch (BusinessException ex) {
			ViewExceptionHandler.handle(ex);
		}
	}

	public void loadAllRoutes() {
		try {
			this.routes = this.routeService.findRoutesByBranchId(this.sessionBean.getCurrentBranch().getId());
		} catch (BusinessException ex) {
			ViewExceptionHandler.handle(ex);
		}
	}

	public void loadAllVehicles() {
		try {
			this.vehicles = this.vehicleService.findVehiclesByBranchId(this.sessionBean.getCurrentBranch().getId());
		} catch (BusinessException ex) {
			ViewExceptionHandler.handle(ex);
		}
	}

	/**
	 * @return the pickUpPointFees
	 */
	public Collection<PickUpPointFee> getPickUpPointFees() {
		return this.pickUpPointFees;
	}

	/**
	 * @param pickUpPointFees
	 *            the pickUpPointFees to set
	 */
	public void setPickUpPointFees(final Collection<PickUpPointFee> pickUpPointFees) {
		this.pickUpPointFees = pickUpPointFees;
	}

	public void loadPickUpPointFees() {
		try {
			this.pickUpPointFees = this.pickUpPointFeeService.findPickUpPointFeesByPickUpPointId(this.getPickUpPoint()
					.getId());
		} catch (Exception ex) {
			log.info(ex.getMessage(), ex);
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

	/**
	 * @return the pickUpPoint
	 */
	public PickUpPoint getPickUpPoint() {
		return this.pickUpPoint;
	}

	/**
	 * @param pickUpPoint
	 *            the pickUpPoint to set
	 */
	public void setPickUpPoint(final PickUpPoint pickUpPoint) {
		this.pickUpPoint = pickUpPoint;
	}

}
