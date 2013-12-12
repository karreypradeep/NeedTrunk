/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 */
package com.apeironsol.need.notifications.providers.sms;

/**
 * @author pradeep
 * 
 */
public class SMSProviderFactory {

	public static SMSProvider getSMSWorker(final String provider) {
		SMSProvider sMSProvider = null;
		if (provider.equals("smscountry")) {
			sMSProvider = new SMSCountryProvider();
		} else if (provider.equals("smshorizon")) {
			sMSProvider = new SMSHorizonProvider();
		}
		return sMSProvider;
	}
}
