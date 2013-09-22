/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.apeironsol.need.transportation.model.Route;
import com.apeironsol.need.transportation.model.RoutePickUpPoint;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for route entity implementation.
 * 
 * @author sandeep
 */
@Repository("routeDao")
public class RouteDaoImpl extends BaseDaoImpl<Route> implements RouteDao {

	private static final Logger	log	= Logger.getLogger(RouteDaoImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Route findRouteByRouteName(final String routeName) {
		try {
			TypedQuery<Route> query = this.getEntityManager().createQuery(
					"select r from Route r where r.name = :routeName", Route.class);
			query.setParameter("routeName", routeName);
			return query.getSingleResult();
		} catch (NoResultException ex) {
			log.info(ex.getMessage());
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Route findRouteByRouteNumber(final String routeNumber) {
		try {
			TypedQuery<Route> query = this.getEntityManager().createQuery(
					"select r from Route r where r.number = :routeNumber", Route.class);
			query.setParameter("routeNumber", routeNumber);
			return query.getSingleResult();
		} catch (NoResultException ex) {
			log.info(ex.getMessage());
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Route> findRoutesByBranchId(final Long branchId) {
		TypedQuery<Route> query = this.getEntityManager().createQuery(
				"select r from Route r join r.branch b where b.id = :id", Route.class);
		query.setParameter("id", branchId);
		return query.getResultList();
	}

	@Override
	public Collection<Route> findRoutesByVehicleId(final Long id) {
		TypedQuery<Route> query = this.getEntityManager().createQuery(
				"select r from Route r join r.vehicles v where v.id=:id", Route.class);
		query.setParameter("id", id);
		return query.getResultList();
	}

	@Override
	public Collection<Route> findAllRoutesByPickUpPointId(final Long id) {
		TypedQuery<Route> query = this.getEntityManager().createQuery(
				"select r from Route r join r.pickUpPoints p where p.id=:id", Route.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	@Override
	public Collection<RoutePickUpPoint> findAllRoutePickUpPointsByPickUpPointId(final Long id) {
		TypedQuery<RoutePickUpPoint> query = this.getEntityManager().createQuery(
				"select rpt from RoutePickUpPoint rpt where rpt.routePickUpPointPK.pickUpPointsId = :pickUpPointsId", RoutePickUpPoint.class);
		query.setParameter("pickUpPointsId", id);
		return query.getResultList();
	}

}
