/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.consumers.worker.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.apeironsol.need.core.model.SMSProvider;

/**
 * Class for sending email notification for student pending fee.
 * The scope of this bean is prototype as a new bean should be created for
 * sending email as t.
 * 
 * @author Pradeep
 */
public class EmailAndSMSUtil {

	/**
	 * Java mail sender for sending mails.
	 */
	private JavaMailSender	mailSender;

	/**
	 * Asynchronous method for sending fee pending notification mail for
	 * student.
	 * 
	 * @param student
	 *            student.
	 * @throws MessagingException
	 */
	public void sendMail(final String fromAddress, final String[] to, final String emailText, final String subject) throws MessagingException {
		MimeMessage message = this.getMailSender().createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(fromAddress);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(emailText, true);
		this.getMailSender().send(message);
	}

	public String sendSMS(final SMSProvider smsProvider, final String[] phoneNumbers, final String message) throws URISyntaxException, ClientProtocolException,
			IOException {

		String messageId = null;

		StringBuilder phoneNumbersBuilder = new StringBuilder();
		for (String phoneNumber : phoneNumbers) {
			phoneNumbersBuilder.append(phoneNumber);
			phoneNumbersBuilder.append(",");
		}

		URIBuilder builder = new URIBuilder();
		builder.setScheme(smsProvider.getScheme()).setHost(smsProvider.getHost()).setPath(smsProvider.getPath())
				.setParameter(smsProvider.getUserNameKey(), smsProvider.getUserName()).setParameter(smsProvider.getPasswordKey(), smsProvider.getPassword())
				.setParameter(smsProvider.getSenderIdKey(), smsProvider.getSenderId()).setParameter(smsProvider.getRouteKey(), smsProvider.getRoute())
				.setParameter(smsProvider.getMessageKey(), message)
				.setParameter(smsProvider.getToKey(), phoneNumbersBuilder.substring(0, phoneNumbersBuilder.length() - 1));

		URI uri = builder.build();
		HttpGet httpget = new HttpGet(uri);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			try {
				ContentType contentType = ContentType.getOrDefault(entity);
				String body = IOUtils.toString(instream, contentType.getCharset().displayName());
				messageId = body;
			} finally {
				instream.close();
			}
		}
		return messageId;
	}

	/**
	 * @return the mailSender
	 */
	public JavaMailSender getMailSender() {
		return this.mailSender;
	}

	/**
	 * @param mailSender
	 *            the mailSender to set
	 */
	public void setMailSender(final JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

}
