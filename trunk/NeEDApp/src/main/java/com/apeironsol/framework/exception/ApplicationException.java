/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.framework.exception;

public class ApplicationException extends AbstractLocalizedException {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3212309298381775252L;

	public ApplicationException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified cause and a detail message.
	 * 
	 * @param cause
	 *            the cause.
	 */
	public ApplicationException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new exception with the specified detail message.
	 * 
	 * @param resourcebundleName
	 *            The name of the resource bundle.
	 * @param messageKey
	 *            The detail message.
	 * @param messageArguments
	 *            The parameters that will be applied to compose a formatted
	 *            message.
	 */
	public ApplicationException(final String resourcebundleName, final String messageKey,
			final Object... messageArguments) {
		super(resourcebundleName, messageKey, messageArguments);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * 
	 * @param cause
	 *            The cause.
	 * @param resourcebundleName
	 *            The name of the resource bundle.
	 * @param messageKey
	 *            The detail message.
	 * @param messageArguments
	 *            The parameters that will be applied to compose a formatted
	 *            message.
	 */
	public ApplicationException(final Throwable cause, final String resourcebundleName, final String messageKey,
			final Object... messageArguments) {
		super(cause, resourcebundleName, messageKey, messageArguments);
	}
}
