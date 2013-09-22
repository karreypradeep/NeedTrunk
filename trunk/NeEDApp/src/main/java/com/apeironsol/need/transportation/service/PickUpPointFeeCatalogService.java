/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.service;

import java.util.Collection;

import com.apeironsol.need.transportation.model.PickUpPointFeeCatalog;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service Interface for pick up point payment.
 * 
 * @author Sandeep
 * 
 */
public interface PickUpPointFeeCatalogService {

	void removePickUpPointFeeCatalogsByPickUpPointFeeId(final Long pickUpPointFeeId) throws BusinessException;

	Collection<PickUpPointFeeCatalog> findPickUpPointFeeCatalogsByPickUpPointFeeId(Long pickUpPointFeeId);

}
