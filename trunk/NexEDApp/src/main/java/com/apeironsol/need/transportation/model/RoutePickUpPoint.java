/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity class for RoutePickUpPoint.
 * 
 * @author Sandeep Goud.
 */
@Entity
@Table(name = "ROUTE_PICK_UP_POINT")
public class RoutePickUpPoint implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -802480439597094587L;

	@EmbeddedId
	private RoutePickUpPointPK	routePickUpPointPK;

	/**
	 * @return the routePickUpPointPK
	 */
	public RoutePickUpPointPK getRoutePickUpPointPK() {
		return this.routePickUpPointPK;
	}

	/**
	 * @param routePickUpPointPK
	 *            the routePickUpPointPK to set
	 */
	public void setRoutePickUpPointPK(final RoutePickUpPointPK routePickUpPointPK) {
		this.routePickUpPointPK = routePickUpPointPK;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.routePickUpPointPK == null ? 0 : this.routePickUpPointPK.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		RoutePickUpPoint other = (RoutePickUpPoint) obj;
		if (this.routePickUpPointPK == null) {
			if (other.routePickUpPointPK != null) {
				return false;
			}
		} else if (!this.routePickUpPointPK.equals(other.routePickUpPointPK)) {
			return false;
		}
		return true;
	}
}
