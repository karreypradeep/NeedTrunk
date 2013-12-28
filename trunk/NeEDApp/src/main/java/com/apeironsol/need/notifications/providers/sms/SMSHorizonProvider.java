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
 
import com.apeironsol.framework.exception.ApplicationException;

/**
 * Class for sending email notification for student pending fee.
 * The scope of this bean is prototype as a new bean should be created for
 * sending email as t.
 * 
 * @author Pradeep
 */
public class SMSHorizonProvider  {

	public String sendSMS(final String[] phoneNumbers, final String message) throws ApplicationException {
		String retval = "";
		/*try {
			String username = "apeironsol";
			String password = "inaskpgoud@533@1233";
			StringBuffer mobilenumber = new StringBuffer();
			String sid = "SMedhV";
			String reqid = "1";
			String route_id = "25";
			int counter = 1;
			for (String string : phoneNumbers) {
				string = string.replace("(M)", "").trim();
				mobilenumber.append(string);
				if (phoneNumbers.length > 1 && counter != phoneNumbers.length) {
					mobilenumber.append(",");
				}
				counter++;
			}
			
			 * String data += "username=" + URLEncoder.encode(username, "UTF-8")
			 * + "&password=" + password + "&to=" + mobilenumber + "&message="
			 * + URLEncoder.encode(message, "UTF-8") + "&sender=" + sid +
			 * "&reqid=" + reqid + "&route_id=" + route_id;
			 

			String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
			data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
			data += "&" + URLEncoder.encode("to", "UTF-8") + "=" + URLEncoder.encode(mobilenumber.toString(), "UTF-8");
			data += "&" + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8");
			data += "&" + URLEncoder.encode("sender", "UTF-8") + "=" + URLEncoder.encode(sid, "UTF-8");
			data += "&" + URLEncoder.encode("reqid", "UTF-8") + "=" + URLEncoder.encode(reqid, "UTF-8");
			data += "&" + URLEncoder.encode("route_id", "UTF-8") + "=" + URLEncoder.encode(route_id, "UTF-8");

			// Push the HTTP Request
			URL url = new URL("http://clients.smshorizon.in/API/WebSMS/Http/v1.0a/index.php");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// conn.setRequestMethod("POST");
			// conn.setRequestProperty("Content-Type",
			// "application/x-www-form-urlencoded");
			conn.setDoOutput(true);

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String decodedString;
			while ((decodedString = in.readLine()) != null) {
				retval += decodedString;
			}
			wr.close();
			in.close();

			System.out.println(retval);
		} catch (IOException exception) {
			throw new ApplicationException(exception);
		}*/
		return retval;
	}
}
