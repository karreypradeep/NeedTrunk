/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.portal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.model.DualListModel;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.transportation.model.PickUpPoint;
import com.apeironsol.need.transportation.model.Route;
import com.apeironsol.need.transportation.service.RouteService;
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
public class RouteBean extends AbstractTransportationBean {

	/**
	 * 
	 */
	private static final long			serialVersionUID	= -233104064216333982L;

	private static final Logger								log					= Logger.getLogger(RouteBean.class);

	private Route						route;

	@Resource
	private RouteService				routeService;

	private DualListModel<PickUpPoint>	pickUpPointDualListModel;

	private List<PickUpPoint>			pickUpPointSource	= new ArrayList<PickUpPoint>();

	private List<PickUpPoint>			pickUpPointTarget	= new ArrayList<PickUpPoint>();

	/**
	 * If true new pickup point button and pickup points table are displayed.
	 */
	private boolean						renderNewRouteButton;

	/**
	 * If true new pickup point label is displayed. Else modify pickup point
	 * label is displayed.
	 */
	private boolean						createNewRoute;

	/**
	 * @return the pickUpPointDualListModel
	 */
	public DualListModel<PickUpPoint> getPickUpPointDualListModel() {
		return this.pickUpPointDualListModel;
	}

	/**
	 * @param pickUpPointDualListModel
	 *            the pickUpPointDualListModel to set
	 */
	public void setPickUpPointDualListModel(final DualListModel<PickUpPoint> pickUpPointDualListModel) {
		this.pickUpPointDualListModel = pickUpPointDualListModel;
	}

	/**
	 * @return the renderNewRouteButton
	 */
	public boolean isRenderNewRouteButton() {
		return this.renderNewRouteButton;
	}

	/**
	 * @param renderNewRouteButton
	 *            the renderNewRouteButton to set
	 */
	public void setRenderNewRouteButton(final boolean renderNewRouteButton) {
		this.renderNewRouteButton = renderNewRouteButton;
	}

	/**
	 * @return the createNewRoute
	 */
	public boolean isCreateNewRoute() {
		return this.createNewRoute;
	}

	/**
	 * @param createNewRoute
	 *            the createNewRoute to set
	 */
	public void setCreateNewRoute(final boolean createNewRoute) {
		this.createNewRoute = createNewRoute;
	}

	/**
	 * @return the route
	 */
	public Route getRoute() {
		return this.route;
	}

	/**
	 * @return the pickUpPointSource
	 */
	public List<PickUpPoint> getPickUpPointSource() {
		return this.pickUpPointSource;
	}

	/**
	 * @param pickUpPointSource
	 *            the pickUpPointSource to set
	 */
	public void setPickUpPointSource(final List<PickUpPoint> pickUpPointSource) {
		this.pickUpPointSource = pickUpPointSource;
	}

	/**
	 * @return the pickUpPointTarget
	 */
	public List<PickUpPoint> getPickUpPointTarget() {
		return this.pickUpPointTarget;
	}

	/**
	 * @param pickUpPointTarget
	 *            the pickUpPointTarget to set
	 */
	public void setPickUpPointTarget(final List<PickUpPoint> pickUpPointTarget) {
		this.pickUpPointTarget = pickUpPointTarget;
	}

	public void loadInitRouteData() {
		this.renderNewRouteButton = true;
		this.loadAllRoutes();
	}

	@Override
	public void onTabChange() {

	}

	public String addNewRoute() {
		this.route = new Route();
		this.loadAllActivePickUpPoints();

		this.pickUpPointSource.clear();
		this.pickUpPointTarget.clear();
		this.pickUpPointSource.addAll(this.activePickUpPoints);
		this.pickUpPointDualListModel = new DualListModel<PickUpPoint>(this.pickUpPointSource, this.pickUpPointTarget);

		this.renderNewRouteButton = false;
		this.createNewRoute = true;

		return null;
	}
	

	public String saveRoute() {
		try {
			this.route.setBranch(this.sessionBean.getCurrentBranch());
			this.route.setPickUpPoints(this.pickUpPointDualListModel.getTarget());
			this.routeService.saveRoute(this.route);
			this.renderNewRouteButton = true;
			this.loadAllRoutes();

			if (this.createNewRoute) {
				ViewUtil.addMessage("Route saved sucessfully.", FacesMessage.SEVERITY_INFO);
			} else {
				ViewUtil.addMessage("Route modified sucessfully.", FacesMessage.SEVERITY_INFO);
			}
		} catch (BusinessException ex) {
			log.info(ex.getMessage());
			ViewExceptionHandler.handle(ex);
		}
		return null;
	}

	public String cancleRoute() {
		this.renderNewRouteButton = true;
		return null;
	}

	public String showRoute(final Route route) {
		try {
			this.route = route;
			this.route = this.routeService.findRouteDetailsByRouteId(this.route.getId());
			
			this.loadAllActivePickUpPoints();

			this.pickUpPointSource.clear();
			this.pickUpPointTarget.clear();
			
			this.pickUpPointTarget.addAll(this.route.getPickUpPoints());

			List<String> targetPickUpPointsList = new ArrayList<String>();
			for (PickUpPoint targerPickUpPoint : this.route.getPickUpPoints()) {
				targetPickUpPointsList.add(targerPickUpPoint.getName());
			}
			for (PickUpPoint sourcePickUpPoint : this.activePickUpPoints) {
				if (!targetPickUpPointsList.contains(sourcePickUpPoint.getName())) {
					this.pickUpPointSource.add(sourcePickUpPoint);
				}
			}
			this.pickUpPointDualListModel = new DualListModel<PickUpPoint>(this.pickUpPointSource,
					this.pickUpPointTarget);
			this.renderNewRouteButton = false;
			this.createNewRoute = false;
		} catch (BusinessException ex) {
			log.info(ex.getMessage());
			ViewExceptionHandler.handle(ex);
		}
		return null;
	}

	public String deleteRoute(final Route route) {
		try {
			this.route = route;
			this.routeService.deleteRoute(this.route.getId());
			this.renderNewRouteButton = true;
			this.loadAllRoutes();
			ViewUtil.addMessage("Route deleted sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (BusinessException ex) {
			log.info(ex.getMessage());
			ViewExceptionHandler.handle(ex);
		}
		return null;
	}

	public Route getRouteById(final Long routeId) {
		Route result = null;
		try {
			if (this.routes == null || this.routes.size() == 0) {
				this.loadAllRoutes();
			}
			for (Route route : this.routes) {
				if (route.getId().equals(routeId)) {
					result = route;
					break;
				}
			}
		} catch (Exception ex) {
			log.info(ex.getMessage());
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		return result;
	}

	public void onSelectRouteSource() {
		PickUpPoint selectedPickUpPoint = this.route.getSource();

		for (Iterator<PickUpPoint> pickUpPointIter = this.pickUpPointDualListModel.getSource().iterator(); pickUpPointIter
				.hasNext();) {
			if (pickUpPointIter.next().getName().equals(selectedPickUpPoint.getName())) {
				pickUpPointIter.remove();
				this.pickUpPointDualListModel.getTarget().add(selectedPickUpPoint);
				break;
			}
		}
	}

	public void onSourceChange() {
		PickUpPoint pickUpPoint = this.route.getSource();
		this.pickUpPointSource.clear();
		this.pickUpPointTarget.clear();
		this.pickUpPointSource.addAll(this.activePickUpPoints);
		if (pickUpPoint != null) {
			this.pickUpPointSource.remove(pickUpPoint);
			this.pickUpPointTarget.add(pickUpPoint);
		}
		this.pickUpPointDualListModel = new DualListModel<PickUpPoint>(this.pickUpPointSource, this.pickUpPointTarget);
	}

}
