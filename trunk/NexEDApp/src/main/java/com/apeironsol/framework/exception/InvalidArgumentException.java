/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.framework.exception;

/**
 * Exception for invalid argument.
 * 
 * @author Pradeep
 * 
 */
public class InvalidArgumentException extends ApplicationException {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3057500960082633455L;

	/**
	 * Constructs an exception using the supplied message.
	 * 
	 * @param message
	 *            message with additional information about why the argument
	 *            is invalid.
	 */
	public InvalidArgumentException(final String message) {
		super(message);
	}

	/**
	 * Constructs a localized exception from the supplied
	 * resource bundle name and the supplied message key.
	 * 
	 * @param resourceBundleName
	 *            The name of the ResourceBundle that contains
	 *            the localized messages.
	 * @param messageKey
	 *            Key to the resource bundle to retrieve the localized
	 *            message.
	 */
	public InvalidArgumentException(final String resourceBundleName, final String messageKey) {

		super(resourceBundleName, messageKey);
	}

	/**
	 * Constructs a localized exception from the supplied
	 * resource bundle name and the supplied message key and
	 * optional arguments.
	 * 
	 * @param resourceBundleName
	 *            The name of the ResourceBundle that contains
	 *            the localized messages.
	 * @param messageKey
	 *            Key to the resource bundle to retrieve the localized
	 *            message.
	 * @param messageArguments
	 *            The message arguments that will be inserted into
	 *            the localized message.
	 */
	public InvalidArgumentException(final String resourceBundleName, final String messageKey,
			final Object[] messageArguments) {

		super(resourceBundleName, messageKey, messageArguments);
	}

}
