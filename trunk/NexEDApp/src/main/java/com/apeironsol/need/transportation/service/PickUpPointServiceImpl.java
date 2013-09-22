/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.service;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.model.StudentTransportation;
import com.apeironsol.need.core.portal.messages.BusinessMessages;
import com.apeironsol.need.transportation.dao.PickUpPointDao;
import com.apeironsol.need.transportation.dao.PickUpPointFeeCatalogDao;
import com.apeironsol.need.transportation.dao.PickUpPointFeeDao;
import com.apeironsol.need.transportation.dao.RouteDao;
import com.apeironsol.need.transportation.dao.StudentTransportationDao;
import com.apeironsol.need.transportation.model.PickUpPoint;
import com.apeironsol.need.transportation.model.PickUpPointFee;
import com.apeironsol.need.transportation.model.PickUpPointFeeCatalog;
import com.apeironsol.need.transportation.model.RoutePickUpPoint;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.framework.exception.BusinessException;

/**
 * @author sandeep
 * 
 */
@Service("pickUpPointService")
@Transactional
public class PickUpPointServiceImpl implements PickUpPointService {

	@Resource
	PickUpPointDao				pickUpPointDao;

	@Resource
	RouteDao					routeDao;

	@Resource
	PickUpPointFeeDao			pickUpPointFeeDao;

	@Resource
	PickUpPointFeeService		pickUpPointFeeService;

	@Resource
	PickUpPointFeeCatalogDao	pickUpPointFeeCatalogDao;
	
	@Resource
	StudentTransportationDao studentTransportationDao;
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PickUpPoint findPickUpPointById(final Long id) throws BusinessException {
		return this.pickUpPointDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PickUpPoint> findAllPickUpPoints() throws BusinessException {
		return this.pickUpPointDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PickUpPoint savePickUpPoint(final PickUpPoint pickUpPoint) throws BusinessException {
		this.validatePickUpPointForUniqueNameAndCode(pickUpPoint);
		return this.pickUpPointDao.persist(pickUpPoint);
	}

	/**
	 * Checks if pick up point with same name or code already exists in
	 * database.
	 * 
	 * @param pickUpPoint
	 *            pick up point to validate.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	private void validatePickUpPointForUniqueNameAndCode(final PickUpPoint pickUpPoint) throws BusinessException {
		Collection<PickUpPoint> pickUpPoints = this.findPickUpPointsByNameOrCode(pickUpPoint.getName(),
				pickUpPoint.getCode());
		if (!pickUpPoints.isEmpty()) {
			for (PickUpPoint pickUpPointEntry : pickUpPoints) {
				if (((pickUpPointEntry.getId() == null) || (!pickUpPointEntry.getId().equals(pickUpPoint.getId())))
						&& pickUpPointEntry.getName().equalsIgnoreCase(pickUpPoint.getName())) {
					throw new BusinessException(BusinessMessages.getResourceBundleName(),
							BusinessMessages.MSG_NAME_ALREADY_EXIST_FOR_PICKUP_POINT,
							new Object[] { pickUpPoint.getName() });
				} else if (((pickUpPointEntry.getId() == null) || (!pickUpPointEntry.getId()
						.equals(pickUpPoint.getId())))
						&& pickUpPointEntry.getCode().equalsIgnoreCase(pickUpPoint.getCode())) {
					throw new BusinessException(BusinessMessages.getResourceBundleName(),
							BusinessMessages.MSG_CODE_ALREADY_EXIST_FOR_PICKUP_POINT,
							new Object[] { pickUpPoint.getCode() });
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deletePickUpPoint(final Long id) throws BusinessException {
		try {
			// Check if routes are associated with the pickup point.
			Collection<RoutePickUpPoint> routePickUpPoint = this.routeDao.findAllRoutePickUpPointsByPickUpPointId(id);
			if (routePickUpPoint != null && !routePickUpPoint.isEmpty()) {
				throw new BusinessException(BusinessMessages.getResourceBundleName(),
						BusinessMessages.MSG_CANNOT_DELETE_PICKUPPOINT_ROUTES_ASSOCIATED, null);
			}
			
			Collection<StudentTransportation> studentTransportations = this.studentTransportationDao.findStudentTransportationByPickupPointId(id);
			
			if(studentTransportations != null && !studentTransportations.isEmpty()) {
				
				throw new BusinessException("Pickup point cannot be deleted because student transportation is associated with it.");
			}
			
			
			this.pickUpPointFeeService.removePickUpPointFeesByPickUpPointId(id);
			this.pickUpPointDao.remove(this.pickUpPointDao.findById(id));

		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e, FacesMessage.SEVERITY_ERROR);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PickUpPoint> findPickUpPointsByNameOrCode(final String name, final String code) {
		return this.pickUpPointDao.findPickUpPointsByNameOrCode(name, code);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PickUpPoint> findPickUpPointsByBranchId(final Long branchId) throws BusinessException {
		return this.pickUpPointDao.findPickUpPointsByBranchId(branchId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PickUpPoint> findActivePickUpPointsByBranchId(final Long branchId) throws BusinessException {
		return this.pickUpPointDao.findActivePickUpPointsByBranchId(branchId);
	}

	@Override
	public PickUpPoint activatePickUpPoint(final PickUpPoint pickUpPoint) throws BusinessException {
		this.validatePickUpPoint(pickUpPoint);
		pickUpPoint.setActive(true);
		return this.pickUpPointDao.persist(pickUpPoint);
	}

	private void validatePickUpPoint(final PickUpPoint pickUpPoint) {
		PickUpPoint lPickUpPoint = this.pickUpPointDao.findById(pickUpPoint.getId());

		Collection<PickUpPointFee> pickUpPointFees = this.pickUpPointFeeDao
				.findPickUpPointFeesByPickUpPointId(lPickUpPoint.getId());
		if (pickUpPointFees == null || pickUpPointFees.isEmpty()) {
			throw new BusinessException(BusinessMessages.getResourceBundleName(),
					BusinessMessages.MSG_PICKUPPOINT_CANNOT_BE_ACTIVATED_ADD_PICKUPPOINT_FEES,
					new Object[] { lPickUpPoint.getName() });
		}

		for (PickUpPointFee pickUpPointFee : pickUpPointFees) {
			Collection<PickUpPointFeeCatalog> pickUpPointFeeCatalogs = this.pickUpPointFeeCatalogDao
					.findPickUpPointFeeCatalogsByPickUpPointFeeId(pickUpPointFee.getId());
			if (pickUpPointFeeCatalogs == null || pickUpPointFeeCatalogs.isEmpty()) {
				throw new BusinessException(BusinessMessages.getResourceBundleName(),
						BusinessMessages.MSG_PICKUPPOINT_CANNOT_NOT_BE_ACTIVATED_PICKUPPOINT_FEES_PAYMENTS_NOT_DEFINED,
						new Object[] { lPickUpPoint.getName() });
			}
		}
	}

	@Override
	public PickUpPoint deactivatePickUpPoint(final PickUpPoint pickUpPoint) throws BusinessException {
		pickUpPoint.setActive(false);
		return this.pickUpPointDao.persist(pickUpPoint);
	}

}
