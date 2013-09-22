/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.consumers.worker.util;

import java.io.IOException;
import java.net.URISyntaxException;

import com.apeironsol.need.core.model.SMSProvider;

/**
 * Class for sending email notification for student pending fee.
 * The scope of this bean is prototype as a new bean should be created for
 * sending email as t.
 * 
 * @author Pradeep
 */
public class SMSProviderUtil {

	public static SMSProvider getSMSProvider() throws IOException, URISyntaxException {
		SMSProvider smsProvider = new SMSProvider();
		smsProvider.setScheme("http");
		smsProvider.setHost("user.bulkindiasms.com");
		smsProvider.setPath("/sendsms");
		smsProvider.setUserNameKey("uname");
		smsProvider.setUserName("navarathna123");
		smsProvider.setPasswordKey("pwd");
		smsProvider.setPassword("new@123?");
		smsProvider.setSenderIdKey("senderid");
		smsProvider.setSenderId("VK");
		smsProvider.setRouteKey("route");
		smsProvider.setRoute("A");
		smsProvider.setToKey("to");
		smsProvider.setMessageKey("msg");
		return smsProvider;
	}

}
