/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.dao;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.SMSProvider;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access class for batch notification.
 * 
 * @author Pradeep
 */
@Repository("sMSProviderDao")
public class SMSProviderDaoImpl extends BaseDaoImpl<SMSProvider> implements SMSProviderDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SMSProvider findSMSProvidersBySMSProviderName(final String smsProviderName) {
		TypedQuery<SMSProvider> query = this.getEntityManager().createQuery("select sp from SMSProvider sp where sp.smsProviderName = :smsProviderName",
				SMSProvider.class);
		query.setParameter("smsProviderName", smsProviderName);
		return query.getSingleResult();
	}

}
