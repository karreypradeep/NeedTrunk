/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.core.service;

import com.apeironsol.nexed.core.model.Address;
import com.apeironsol.framework.exception.BusinessException;

public interface AddressService {

	/**
	 * Save address.
	 * @param address address to be saved.
	 * @return
	 * @throws BusinessException In case of exception.
	 */
	Address saveAddress(Address address) throws BusinessException;

}
