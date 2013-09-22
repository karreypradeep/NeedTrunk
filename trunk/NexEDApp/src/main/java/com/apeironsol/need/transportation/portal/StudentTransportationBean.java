/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.portal;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.StudentTransportation;
import com.apeironsol.need.core.portal.StudentBean;
import com.apeironsol.need.transportation.model.Route;
import com.apeironsol.need.transportation.model.Vehicle;
import com.apeironsol.need.transportation.service.RouteService;
import com.apeironsol.need.transportation.service.StudentTransportationService;
import com.apeironsol.need.transportation.service.VehicleService;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;

/**
 * @author sandeep
 * 
 *         StudentTransportation class.
 * 
 */
@Named
@Scope(value = "session")
public class StudentTransportationBean extends AbstractTransportationBean {

	/**
	 * 
	 */
	private static final long					serialVersionUID		= 8415521126960986491L;

	@Resource
	private StudentBean							studentBean;

	@Resource
	private StudentTransportationService		studentTransportationService;

	@Resource
	private RouteService						routeService;

	@Resource
	private VehicleService						vehicleService;

	private StudentTransportation				studentTransportation;

	protected boolean							loadStudentTransportationDetailsFlag;

	protected Collection<StudentTransportation>	studentTransportations;

	private Collection<Route>					routesForPickUpPoint	= new ArrayList<Route>();

	protected Collection<Vehicle>				vehiclesForRoute		= new ArrayList<Vehicle>();

	@Override
	public void onTabChange() {

		this.studentTransportation = this.studentTransportationService.findAssignedStudentTransportationsByStudentAcadmicYearId(this.studentBean
				.getStudentAcademicYear().getId());

		this.loadAllActivePickUpPoints();

		if (this.studentTransportation != null) {
			this.routesForPickUpPoint = this.routeService.findAllRoutesByPickUpPointId(this.studentTransportation.getPickUpPoint().getId());
			this.vehiclesForRoute = this.vehicleService.findAllVehiclesByRouteId(this.studentTransportation.getRoute().getId());
		} else {
			this.studentTransportation = new StudentTransportation();
		}

	}

	public void onPickUpPointChange() {
		this.routesForPickUpPoint = this.routeService.findAllRoutesByPickUpPointId(this.studentTransportation.getPickUpPoint().getId());
	}

	public void onRouteChange() {
		this.vehiclesForRoute = this.vehicleService.findAllVehiclesByRouteId(this.studentTransportation.getRoute().getId());
	}

	public void assignStudentTransportation() {
		try {

			this.studentTransportation.setStudentAcademicYear(this.studentBean.getStudentAcademicYear());

			this.studentTransportation = this.studentTransportationService.assignStudentTransportation(this.studentTransportation);

			if (this.studentTransportation.getId() == null) {
				ViewUtil.addMessage("Transportation for " + this.studentTransportation.getPickUpPoint().getName() + " assigned sucessfully.",
						FacesMessage.SEVERITY_INFO);
			} else {
				ViewUtil.addMessage("Student transportation details are updated sucessfully.", FacesMessage.SEVERITY_INFO);
			}

		} catch (Exception e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void unassignStudentTransportation() {

		try {

			this.studentTransportationService.unassignStudentTransportation(this.studentTransportation.getId());

			this.studentTransportation = new StudentTransportation();

			this.loadAllActivePickUpPoints();

			ViewUtil.addMessage("Student transportation is unassigned sucessfully.", FacesMessage.SEVERITY_INFO);

		} catch (Exception e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void closeStudentTransportation() {

		// TODO Pradeep Close Transportation functionality
	}

	public Collection<Vehicle> getVehiclesForRoute() {
		return this.vehiclesForRoute;
	}

	public void setVehiclesForRoute(final Collection<Vehicle> vehiclesForRoute) {
		this.vehiclesForRoute = vehiclesForRoute;
	}

	public Collection<Route> getRoutesForPickUpPoint() {
		return this.routesForPickUpPoint;
	}

	public void setRoutesForPickUpPoint(final Collection<Route> routesForPickUpPoint) {
		this.routesForPickUpPoint = routesForPickUpPoint;
	}

	public Collection<StudentTransportation> getStudentTransportations() {
		return this.studentTransportations;
	}

	public void setStudentTransportations(final Collection<StudentTransportation> studentTransportations) {
		this.studentTransportations = studentTransportations;
	}

	public boolean isLoadStudentTransportationDetailsFlag() {
		return this.loadStudentTransportationDetailsFlag;
	}

	public void setLoadStudentTransportationDetailsFlag(final boolean loadStudentTransportationDetailsFlag) {
		this.loadStudentTransportationDetailsFlag = loadStudentTransportationDetailsFlag;
	}

	public StudentTransportation getStudentTransportation() {
		return this.studentTransportation;
	}

	public void setStudentTransportation(final StudentTransportation studentTransportation) {
		this.studentTransportation = studentTransportation;
	}

	public void loadStudentTransportationDetails() {
		if (this.loadStudentTransportationDetailsFlag) {

			this.studentTransportations = this.studentTransportationService.findAllStudentTransportationsByStudentAcadmicYearId(this.studentBean
					.getStudentAcademicYear().getId());

		}
		this.loadStudentTransportationDetailsFlag = false;
	}

	public void createNewTransportation() {
		this.studentTransportation = new StudentTransportation();
		this.studentTransportation.setStudentAcademicYear(this.studentBean.getStudentAcademicYear());
		this.setNewAction(true);
		this.setViewOrNewAction(true);
		this.loadAllPickUpPoints();
	}

	public void cancleStudentTransportation() {
		this.loadStudentTransportationDetails();
		this.setNewAction(false);
		this.setViewAction(false);
		this.setViewOrNewAction(false);
	}

	public void showStudentTransportation(final StudentTransportation studentTransportation) {
		this.studentTransportation = studentTransportation;
		this.setViewAction(true);
		this.setViewOrNewAction(true);
		this.loadAllPickUpPoints();
		this.routesForPickUpPoint = this.routeService.findAllRoutesByPickUpPointId(this.studentTransportation.getPickUpPoint().getId());
		this.vehiclesForRoute = this.vehicleService.findAllVehiclesByRouteId(this.studentTransportation.getRoute().getId());
	}

}
