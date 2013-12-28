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

/**
 * Class for sending email notification for student pending fee.
 * The scope of this bean is prototype as a new bean should be created for
 * sending email as t.
 * 
 * @author Pradeep
 */
public class SMSCountryProvider {

	public String sendSMS(final String[] phoneNumbers, final String message) throws ApplicationException {
		String retval = "";
		try {
			String postData = "";
			// give all Parameters In String
			String User = "apeironsol";
			String passwd = "apeironsol";
			StringBuffer mobilenumber = new StringBuffer();
			String sid = "Sri MedhaV";
			String mtype = "N";
			String DR = "Y";
			int counter = 1;
			for (String string : phoneNumbers) {
				string = string.replace("(M)", "").trim();
				mobilenumber.append(string);
				if (phoneNumbers.length > 1 && counter != phoneNumbers.length) {
					mobilenumber.append(",");
				}
				counter++;
			}

			postData += "User=" + URLEncoder.encode(User, "UTF-8") + "&passwd=" + passwd + "&mobilenumber=" + mobilenumber + "&message="
					+ URLEncoder.encode(message, "UTF-8") + "&sid=" + sid + "&mtype=" + mtype + "&DR=" + DR;
			URL url = new URL("http://smscountry.com/SMSCwebservice_Bulk.aspx");
			HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();

			// If You Are Behind The Proxy Server Set IP And PORT else Comment
			// Below
			// 4 Lines
			// Properties sysProps = System.getProperties();
			// sysProps.put("proxySet", "true");
			// sysProps.put("proxyHost", "Proxy Ip");
			// sysProps.put("proxyPort", "PORT");

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
