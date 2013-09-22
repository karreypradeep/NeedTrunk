/**
 * 
 */
package com.apeironsol.need.core.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.transportation.model.PickUpPoint;
import com.apeironsol.need.transportation.model.Route;
import com.apeironsol.need.transportation.model.Vehicle;
import com.apeironsol.need.util.constants.StudentTransportationStatusConstant;
import com.apeironsol.framework.BaseEntity;


/**
 * @author Sunny
 * 
 */
@Entity
@Table(name = "STUDENT_TRANSPORTANTION")
public class StudentTransportation extends BaseEntity implements Serializable {

	/**
	 * Universal serial version number.
	 */
	private static final long	serialVersionUID	= 2682894925375595137L;

	@NotNull(message = "model.student_academic_year_mandatory")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STUDENT_ACADEMIC_YEAR_ID", nullable = false)
	private StudentAcademicYear	studentAcademicYear;

	@NotNull(message = "model.pickUpPoint_mandatory")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PICKUPPOINT_ID", nullable = false)
	private PickUpPoint			pickUpPoint;

	@NotNull(message = "model.route_mandatory")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROUTE_ID", nullable = false)
	private Route				route;

	@NotNull(message = "model.vehicle_mandatory")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "VEHICLE_ID", nullable = false)
	private Vehicle				vehicle;
	
	@NotNull(message = "Student transportation status is mandatory.")
	@Basic
	@Column(name = "TRANSPORTATION_STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
	private StudentTransportationStatusConstant studentTransportationStatus;

	
	public StudentAcademicYear getStudentAcademicYear() {
		return this.studentAcademicYear;
	}

	public void setStudentAcademicYear(final StudentAcademicYear studentAcademicYear) {
		this.studentAcademicYear = studentAcademicYear;
	}

	public PickUpPoint getPickUpPoint() {
		return this.pickUpPoint;
	}

	public void setPickUpPoint(final PickUpPoint pickUpPoint) {
		this.pickUpPoint = pickUpPoint;
	}

	public Route getRoute() {
		return this.route;
	}

	public void setRoute(final Route route) {
		this.route = route;
	}

	public Vehicle getVehicle() {
		return this.vehicle;
	}

	public void setVehicle(final Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public StudentTransportationStatusConstant getStudentTransportationStatus() {
		return studentTransportationStatus;
	}

	public void setStudentTransportationStatus(StudentTransportationStatusConstant studentTransportationStatus) {
		this.studentTransportationStatus = studentTransportationStatus;
	}

}
