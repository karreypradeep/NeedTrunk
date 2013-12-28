/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.providers.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.framework.exception.SystemException;
import com.apeironsol.need.core.model.SMSProvider;
import com.apeironsol.need.util.PasswordEncoder;

/**
 * Class for sending email notification for student pending fee.
 * The scope of this bean is prototype as a new bean should be created for
 * sending email as t.
 * 
 * @author Pradeep
 */
public class UniversalSMSProvider {
	SMSProvider	sMSProvider	= null;

	public UniversalSMSProvider(final SMSProvider sMSProvider) throws SystemException {
		if (sMSProvider == null) {
			throw new SystemException("SMSProvider cannot be null. Please define SMS Provider to branch.");
		}
		this.sMSProvider = sMSProvider;
	}

	public String sendSMS(final String[] phoneNumbers, final String message) throws ApplicationException {
		String retval = "";
		try {
			// give all Parameters In String
			StringBuffer mobilenumber = new StringBuffer();
			int counter = 1;
			for (String string : phoneNumbers) {
				string = string.replace("(M)", "").trim();
				mobilenumber.append(string);
				if (phoneNumbers.length > 1 && counter != phoneNumbers.length) {
					mobilenumber.append(",");
				}
				counter++;
			}

			String postData = URLEncoder.encode(this.sMSProvider.getUserNameKey(), "UTF-8") + "=" + URLEncoder.encode(this.sMSProvider.getUserName(), "UTF-8");
			postData += "&" + URLEncoder.encode(this.sMSProvider.getPasswordKey(), "UTF-8") + "="
					+ URLEncoder.encode(PasswordEncoder.decrypt(this.sMSProvider.getPassword()), "UTF-8");
			postData += "&" + URLEncoder.encode(this.sMSProvider.getToKey(), "UTF-8") + "=" + URLEncoder.encode(mobilenumber.toString(), "UTF-8");
			postData += "&" + URLEncoder.encode(this.sMSProvider.getMessageKey(), "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8");
			postData += "&" + URLEncoder.encode(this.sMSProvider.getSenderIdKey(), "UTF-8") + "=" + URLEncoder.encode(this.sMSProvider.getSenderId(), "UTF-8");
			postData += "&" + URLEncoder.encode(this.sMSProvider.getRouteKey(), "UTF-8") + "=" + URLEncoder.encode(this.sMSProvider.getRoute(), "UTF-8");
			if (this.sMSProvider.isUseAdditionalParameter1()) {
				postData += "&" + URLEncoder.encode(this.sMSProvider.getAdditionalParameter1Key(), "UTF-8") + "="
						+ URLEncoder.encode(this.sMSProvider.getAdditionalParameter1Value(), "UTF-8");
			}
			if (this.sMSProvider.isUseAdditionalParameter2()) {
				postData += "&" + URLEncoder.encode(this.sMSProvider.getAdditionalParameter2Key(), "UTF-8") + "="
						+ URLEncoder.encode(this.sMSProvider.getAdditionalParameter2Value(), "UTF-8");
			}
			String urlHost = "http://" + this.sMSProvider.getHost() + "/" + this.sMSProvider.getPath();
			URL url = new URL(urlHost);
			HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();

			urlconnection.setRequestMethod("POST");
			urlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlconnection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(urlconnection.getOutputStream(), Charset.forName("UTF-8"));
			out.write(postData);
			out.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), Charset.forName("UTF-8")));
			String decodedString;
			while ((decodedString = in.readLine()) != null) {
				retval += decodedString;
			}
			in.close();

			System.out.println(retval);
		} catch (IOException exception) {
			throw new ApplicationException(exception);
		}
		return retval;
	}
}
