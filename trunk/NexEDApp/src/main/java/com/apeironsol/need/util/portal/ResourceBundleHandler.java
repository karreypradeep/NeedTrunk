/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.portal;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class ResourceBundleHandler {

	private static final  Logger	logger	= Logger.getLogger(ResourceBundleHandler.class);

	public static String getLocalizedMessage(final String resourceBundleVar, final String messageKey,
			final Object[] messageArguments) {
		try {
			ResourceBundle resourceBundle = ViewUtil.getResourceBundle(resourceBundleVar);
			return getLocalizedMessage(resourceBundle, messageKey, messageArguments);
		} catch (MissingResourceException exception) {
			logger.warn(
					String.format("Could not locate message:key=%s, bundle=%s", new Object[] { messageKey,
							resourceBundleVar }), exception);
		}
		return messageKey;
	}

	public static String getLocalizedMessage(final ResourceBundle resourceBundle, final String messageKey,
			final Object[] messageArguments) {
		String message;
		try {
			message = resourceBundle.getString(messageKey);

			if ((messageArguments != null) && (messageArguments.length > 0)) {
				try {
					message = MessageFormat.format(message, messageArguments);

				} catch (IllegalArgumentException exception) {
					final int N = messageArguments.length;
					Object[] newMessageArguments = java.util.Arrays.copyOf(messageArguments, N+1);
				    System.arraycopy(newMessageArguments, 0, newMessageArguments, 1, N);
				    newMessageArguments[0] = messageKey;
					logger.error(String.format("Could not format the message:key=%s, args=%s", newMessageArguments));
				}
			}
		} catch (MissingResourceException exception) {
			logger.warn(
					String.format("Could not locate message:key=%s, bundle=%s", new Object[] { messageKey,
							resourceBundle.toString() }), exception);

			message = messageKey;
		}

		return message;
	}

}
