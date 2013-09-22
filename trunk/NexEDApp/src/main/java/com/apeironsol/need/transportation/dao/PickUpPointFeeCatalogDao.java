/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.dao;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.transportation.model.PickUpPointFeeCatalog;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for class pick up point implementation.
 * 
 * @author sandeep
 * 
 */
public interface PickUpPointFeeCatalogDao extends BaseDao<PickUpPointFeeCatalog> {

	Collection<PickUpPointFeeCatalog> findPickUpPointFeeCatalogsByPickUpPointFeeId(Long pickUpPointFeeId);

	Collection<PickUpPointFeeCatalog> findPickUpPointFeeCatalogsByPickUpPointFeeIdAndDueDate(Long pickUpPointFeeId, Date dueDate);

}
