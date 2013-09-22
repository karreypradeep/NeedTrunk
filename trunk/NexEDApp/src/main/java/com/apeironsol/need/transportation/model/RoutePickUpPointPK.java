package com.apeironsol.need.transportation.model;

/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Embeddable class for RoutePickUpPoint.
 * 
 * @author Sandeep Goud.
 */
@Embeddable
public class RoutePickUpPointPK implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= 8258915464491213686L;

	@Basic(optional = false)
	@Column(name = "pickUpPoints_ID")
	private Long				pickUpPointsId;

	@Basic(optional = false)
	@Column(name = "routes_ID")
	private Long				routesId;

	/**
	 * @return the pickUpPointsId
	 */
	public Long getPickUpPointsId() {
		return this.pickUpPointsId;
	}

	/**
	 * @param pickUpPointsId
	 *            the pickUpPointsId to set
	 */
	public void setPickUpPointsId(final Long pickUpPointsId) {
		this.pickUpPointsId = pickUpPointsId;
	}

	/**
	 * @return the routesId
	 */
	public Long getRoutesId() {
		return this.routesId;
	}

	/**
	 * @param routesId
	 *            the routesId to set
	 */
	public void setRoutesId(final Long routesId) {
		this.routesId = routesId;
	}

	@Override
	public int hashCode() {
		Long hash = new Long(0);
		hash += this.pickUpPointsId;
		hash += this.routesId;
		return hash.hashCode();
	}

	@Override
	public boolean equals(final Object object) {
		if (object == null) {
			return false;
		}

		if (object == this) {
			return true;
		}

		if (!(object instanceof RoutePickUpPointPK)) {
			return false;
		}

		RoutePickUpPointPK other = (RoutePickUpPointPK) object;
		if (this.pickUpPointsId.longValue() != other.pickUpPointsId.longValue()) {
			return false;
		}
		if (this.routesId.longValue() != other.routesId.longValue()) {
			return false;
		}
		return true;

	}
}