/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.dao;

import com.apeironsol.need.core.model.SMSProvider;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for batch log.
 * 
 * @author Pradeep
 */
public interface SMSProviderDao extends BaseDao<SMSProvider> {

	/**
	 * Retrieve SMSProvider for the supplied smsProviderName.
	 * 
	 * @param smsProviderName
	 *            smsProviderName.
	 * @return SMSProvider for the supplied smsProviderName.
	 */
	SMSProvider findSMSProvidersBySMSProviderName(String smsProviderName);

}
