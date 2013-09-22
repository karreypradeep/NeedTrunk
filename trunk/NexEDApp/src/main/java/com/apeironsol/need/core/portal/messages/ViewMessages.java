/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */

package com.apeironsol.need.core.portal.messages;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The messages used in views.
 * 
 * @version $Revision: 71132 $
 */
public final class ViewMessages {

	/**
	 * Private constructor to prevent instantiation.
	 */
	private ViewMessages() {
		super();

		// No action needed. Nothing to initialize.
	}

	/**
	 * Get the resource bundle for the given locale.
	 * 
	 * @param locale
	 *            the locale for which to get the resource bundle.
	 * @return the resource bundle for <code>locale</code>.
	 */
	public static ResourceBundle getBundle(final Locale locale) {
		return ResourceBundle.getBundle(ViewMessages.class.getName(), locale);
	}
}
