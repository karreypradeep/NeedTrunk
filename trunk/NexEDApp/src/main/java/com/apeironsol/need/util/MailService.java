/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util;

import java.io.Serializable;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Utility class for sending mail.
 * 
 * @author Pradeep
 */
public class MailService implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID		= 7982816393174285982L;

	/**
	 * The mail server name.
	 */
	private String				mailServer;

	/**
	 * The mail server port number.
	 */
	private Integer				mailSeverPortNumber;

	/**
	 * The sender address.
	 */
	private String				senderAddress;

	/**
	 * The recipient of the mail.
	 */
	private String				recipientAddress;

	/**
	 * The sender of the mail.
	 */
	private String				subjectOfTheMail;

	/**
	 * The content of the message body.
	 */
	private String				messageBodyOfTheMail;

	/**
	 * Constant to hold server name.
	 */
	private static final String	MAIL_SMTP_HOST			= "mail.smtp.host";

	/**
	 * Constant to hold server port number.
	 */
	private static final String	MAIL_SMTP_PORT_NUMBER	= "mail.smtp.port";

	/**
	 * Constant to hold authentication.
	 */
	private static final String	MAIL_SMTP_AUTH			= "mail.smtp.auth";

	/**
	 * Constant to hold mail SMTP submitter.
	 */
	private static final String	MAIL_SMTP_SUBMITTER		= "mail.smtp.submitter";

	/**
	 * The method to send the mail.
	 * 
	 * @param isAuthenicateRequired
	 *            boolean to indicate authentication is required.
	 * @param userName
	 *            The name of user name used for authentication.
	 * @param password
	 *            The password of the user used for authentication.
	 * @throws MessagingException
	 *             in case failed to send message.
	 */
	public void sendMail(final Boolean isAuthenicateRequired, final String userName, final String password)
			throws MessagingException {

		// Setup mail server
		final Properties properties = new Properties();
		properties.setProperty(MAIL_SMTP_HOST, this.mailServer);
		properties.setProperty(MAIL_SMTP_PORT_NUMBER, this.mailSeverPortNumber.toString());

		Session session;

		// Create session with Authenticator if Authenticator Required
		if (isAuthenicateRequired) {
			final MailAuthenticator authenticator = new MailAuthenticator(userName, password);
			properties.setProperty(MAIL_SMTP_AUTH, "true");
			properties.setProperty(MAIL_SMTP_SUBMITTER, authenticator.getPasswordAuthentication().getUserName());
			session = Session.getInstance(properties, authenticator);
		} else {
			session = Session.getInstance(properties, null);
		}

		// Define a new mail message
		final Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(this.senderAddress));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.recipientAddress));
		message.setSubject(this.subjectOfTheMail);

		message.setText(this.messageBodyOfTheMail);

		// Send the message
		Transport.send(message);

	}

	/**
	 * Gets the mail server name.
	 * 
	 * @return The mail server name.
	 */
	public String getMailServer() {
		return this.mailServer;
	}

	/**
	 * Sets the mail server name.
	 * 
	 * @param mailServer
	 *            The mail server name.
	 */
	public void setMailServer(final String mailServer) {
		this.mailServer = mailServer;
	}

	/**
	 * Gets the sender address of the mail.
	 * 
	 * @return the senderAddress of the mail.
	 */
	public String getSenderAddress() {
		return this.senderAddress;
	}

	/**
	 * Sets the sender address of the mail.
	 * 
	 * @param senderAddress
	 *            the sender address of the mail.
	 */
	public void setSenderAddress(final String senderAddress) {
		this.senderAddress = senderAddress;
	}

	/**
	 * Gets the recipient address of the mail.
	 * 
	 * @return the recipient address of the mail.
	 */
	public String getRecipientAddress() {
		return this.recipientAddress;
	}

	/**
	 * Sets the recipient Address of the mail.
	 * 
	 * @param recipientAddress
	 *            the recipient address of the mail.
	 */
	public void setRecipientAddress(final String recipientAddress) {
		this.recipientAddress = recipientAddress;
	}

	/**
	 * Gets the subject of the mail.
	 * 
	 * @return the subjectOfTheMail
	 */
	public String getSubjectOfTheMail() {
		return this.subjectOfTheMail;
	}

	/**
	 * Sets the subject of the mail.
	 * 
	 * @param subjectOfTheMail
	 *            the subject of the mail.
	 */
	public void setSubjectOfTheMail(final String subjectOfTheMail) {
		this.subjectOfTheMail = subjectOfTheMail;
	}

	/**
	 * Gets the message body of the mail.
	 * 
	 * @return the messageBodyOfTheMail
	 */
	public String getMessageBodyOfTheMail() {
		return this.messageBodyOfTheMail;
	}

	/**
	 * Sets the message body of the mail.
	 * 
	 * @param messageBodyOfTheMail
	 *            the message body of the mail.
	 */
	public void setMessageBodyOfTheMail(final String messageBodyOfTheMail) {
		this.messageBodyOfTheMail = messageBodyOfTheMail;
	}

	/**
	 * Gets the mail server port number.
	 * 
	 * @return the mailSeverPortNumber the mail server port number.
	 */
	public Integer getMailSeverPortNumber() {
		return this.mailSeverPortNumber;
	}

	/**
	 * Sets the mail server port number.
	 * 
	 * @param mailSeverPortNumber
	 *            the mail server port number.
	 */
	public void setMailSeverPortNumber(final Integer mailSeverPortNumber) {
		this.mailSeverPortNumber = mailSeverPortNumber;
	}
}

/**
 * Meant for authenticating the user name and password before sending the mail.
 * 
 * @author Pradeep.
 */
class MailAuthenticator extends javax.mail.Authenticator {

	/** The Password Authenticator. */
	private final PasswordAuthentication	authentication;

	/**
	 * Overloaded constructor for Authenticator.
	 * 
	 * @param username
	 *            The user name.
	 * @param password
	 *            The password.
	 */
	public MailAuthenticator(final String username, final String password) {
		this.authentication = new PasswordAuthentication(username, password);
	}

	/**
	 * Gets the Password Authenticator.
	 * 
	 * @return the Password Authenticator.
	 */
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return this.authentication;
	}
}
