/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;
import com.apeironsol.need.core.model.SMSProvider;
import com.apeironsol.need.notifications.dao.SMSProviderDao;
import com.apeironsol.need.util.PasswordEncoder;

/**
 * Service class for batch log.
 * 
 * @author Pradeep
 */
@Service("sMSProviderService")
@Transactional(rollbackFor = Exception.class)
public class SMSProviderServiceImpl implements SMSProviderService {

	@Autowired
	private SMSProviderDao	sMSProviderDao;

	@Override
	public SMSProvider findSMSProviderById(final Long id) throws BusinessException {
		return this.sMSProviderDao.findById(id);
	}

	@Override
	public SMSProvider saveSMSProvider(final SMSProvider smsProvider) throws BusinessException, InvalidArgumentException {
		String encodedPassword = PasswordEncoder.encrypt(smsProvider.getPassword());
		smsProvider.setPassword(encodedPassword);
		return this.sMSProviderDao.persist(smsProvider);
	}

	@Override
	public void removeSMSProvider(final SMSProvider smsProvider) throws BusinessException {
		this.sMSProviderDao.remove(smsProvider);
	}

	@Override
	public Collection<SMSProvider> findAllSMSProviders() throws BusinessException {
		return this.sMSProviderDao.findAll();
	}

	@Override
	public SMSProvider findSMSProvidersBySMSProviderName(final String smsProviderName) {
		return this.sMSProviderDao.findSMSProvidersBySMSProviderName(smsProviderName);
	}

}
