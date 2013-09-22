/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.transportation.dao.PickUpPointFeeCatalogDao;
import com.apeironsol.need.transportation.model.PickUpPointFeeCatalog;

/**
 * Service implementation interface for pick up point payment.
 * 
 * @author sandeep
 * 
 */
@Service("pickUpPointFeeCatalogService")
@Transactional
public class PickUpPointFeeCatalogServiceImpl implements PickUpPointFeeCatalogService {

	@Resource
	PickUpPointFeeCatalogDao	pickUpPointFeeCatalogDao;

	@Override
	public void removePickUpPointFeeCatalogsByPickUpPointFeeId(final Long pickUpPointFeeId) {
		Collection<PickUpPointFeeCatalog> pickUpPointFeeCatalogs = this
				.findPickUpPointFeeCatalogsByPickUpPointFeeId(pickUpPointFeeId);
		for (PickUpPointFeeCatalog pickUpPointFeeCatalog : pickUpPointFeeCatalogs) {
			this.removePickUpPointFeeCatalog(pickUpPointFeeCatalog);
		}
	}

	private void removePickUpPointFeeCatalog(final PickUpPointFeeCatalog pickUpPointFeeCatalog) {
		this.pickUpPointFeeCatalogDao.remove(pickUpPointFeeCatalog);
	}

	@Override
	public Collection<PickUpPointFeeCatalog> findPickUpPointFeeCatalogsByPickUpPointFeeId(final Long pickUpPointFeeId) {
		return this.pickUpPointFeeCatalogDao.findPickUpPointFeeCatalogsByPickUpPointFeeId(pickUpPointFeeId);
	}
}
