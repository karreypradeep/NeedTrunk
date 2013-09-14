/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.framework.exception;

/**
 * This exception class is used to handle business cases validations in the
 * application
 * 
 * @author Pradeep
 * 
 */
public class BusinessException extends ApplicationException {

	private static final long	serialVersionUID	= 2156225803330994760L;

	public BusinessException(final Throwable cause) {
		super(cause);
		this.initCause(cause);
	}

	public BusinessException(final String resourceBundleName, final String messageKey, final Object[] messageArguments) {
		super(resourceBundleName, messageKey, messageArguments);
	}

	public BusinessException(final Throwable cause, final String resourceBundleName, final String messageKey,
			final Object[] messageArguments) {
		super(cause, resourceBundleName, messageKey, messageArguments);
	}

	public BusinessException(final String message) {
		super(message);
	}
}
