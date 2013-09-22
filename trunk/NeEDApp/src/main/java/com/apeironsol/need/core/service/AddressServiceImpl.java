/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.AddressDao;
import com.apeironsol.need.core.model.Address;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service implementation for address
 * 
 * @author Pradeep
 * 
 */
@Service("addressService")
@Transactional
public class AddressServiceImpl implements AddressService {

	@Resource
	private AddressDao	addressDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Address saveAddress(final Address address) throws BusinessException {
		return this.addressDao.persist(address);
	}
}
