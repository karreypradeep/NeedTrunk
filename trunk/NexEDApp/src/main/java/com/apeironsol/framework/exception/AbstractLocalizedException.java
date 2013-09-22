/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.framework.exception;

import java.util.Locale;

import com.apeironsol.need.util.portal.ResourceBundleHandler;

public abstract class AbstractLocalizedException extends RuntimeException {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= 7264409520413365407L;

	private String				resourceBundleVariable;

	private String				messageKey;

	private Object[]			messageArguments;

	public AbstractLocalizedException() {

	}

	public AbstractLocalizedException(final Throwable cause) {
		super(cause);
	}

	public AbstractLocalizedException(final String message) {
		super(message);
	}

	public AbstractLocalizedException(final Throwable cause, final String message) {
		super(message, cause);
	}

	public AbstractLocalizedException(final String resourceBundleName, final String messageKey,
			final Object[] messageArguments) {
		this(null, resourceBundleName, messageKey, messageArguments);
	}

	public AbstractLocalizedException(final Throwable cause, final String resourceBundleName, final String messageKey,
			final Object[] messageArguments) {
		super(cause);

		this.resourceBundleVariable = resourceBundleName;
		this.messageKey = messageKey;

		this.messageArguments = (messageArguments == null ? new Object[0] : messageArguments);
	}

	public String getResourceBundleVariable() {
		return this.resourceBundleVariable;
	}

	public String getMessageKey() {
		return this.messageKey;
	}

	public Object[] getMessageArguments() {
		return this.messageArguments == null ? null : (Object[]) this.messageArguments.clone();
	}

	@Override
	public String getMessage() {
		return this.getMessage(Locale.getDefault());
	}

	public String getMessage(final Locale locale) {
		return this.messageKey == null ? super.getMessage() : ResourceBundleHandler.getLocalizedMessage(
				this.resourceBundleVariable, this.messageKey, this.messageArguments);

	}

	@Override
	public String toString() {
		return this.getMessage();
	}
}
