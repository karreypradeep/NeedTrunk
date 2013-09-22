/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.service;

import java.util.Collection;

import com.apeironsol.need.core.model.SMSProvider;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service interface for batch log.
 * 
 * @author Pradeep
 */
public interface SMSProviderService {
	/**
	 * Find batch log by Id.
	 * 
	 * @param id
	 *            batch log Id.
	 * @return batch log with supplied id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	SMSProvider findSMSProviderById(Long id) throws BusinessException;

	/**
	 * Save batch log.
	 * 
	 * @param smsProvider
	 *            batch log to be saved.
	 * @return persisted batch log.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 */
	SMSProvider saveSMSProvider(SMSProvider smsProvider) throws BusinessException, InvalidArgumentException;

	/**
	 * Remove batch log.
	 * 
	 * @param smsProvider
	 *            batch log to be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeSMSProvider(SMSProvider smsProvider) throws BusinessException;

	/**
	 * Find all batch logs
	 * 
	 * @return collection of all batch logs.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<SMSProvider> findAllSMSProviders() throws BusinessException;

	/**
	 * Retrieve SMSProvider for the supplied smsProviderName.
	 * 
	 * @param smsProviderName
	 *            smsProviderName.
	 * @return SMSProvider for the supplied smsProviderName.
	 */
	SMSProvider findSMSProvidersBySMSProviderName(String smsProviderName);
}
