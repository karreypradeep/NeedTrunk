/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */

package com.apeironsol.need.core.portal.util;

import java.util.Locale;

/**
 * The supported locales in the portal.
 * 
 * @author rens.van.leeuwen@leanapps.com
 * @version $Revision: 70747 $
 */
public enum SupportedLocaleType {

	/** The Dutch locale. */
	DUTCH(new Locale("nl")),

	/** The English locale. */
	ENGLISH(new Locale("en"));

	/**
	 * The Java {@link java.util.Locale} that belongs to this supported locale
	 * type.
	 */
	private final Locale locale;

	/**
	 * Constructs a new enumerated value to the supported locales.
	 * 
	 * @param locale
	 *            The Java {@link java.util.Locale} that belongs to this
	 *            supported locale type.
	 */
	private SupportedLocaleType(final Locale locale) {
		this.locale = locale;
	}

	/**
	 * Returns the Java {@link java.util.Locale} that belongs to this supported
	 * locale type.
	 * 
	 * @return The Java {@link java.util.Locale} that belongs to this supported
	 *         locale type.
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Returns the supported locale type that has a corresponding
	 * {@link java.util.Locale} that has the same language as the given
	 * <code>locale</code>.
	 * 
	 * @param locale
	 *            The locale for which the corresponding supported locale type
	 *            should be retrieved.
	 * @return The requested supported locale type, or <code>null</code> when it
	 *         could not be found.
	 */
	public static SupportedLocaleType getByLocaleLanguage(final Locale locale) {
		SupportedLocaleType result = null;

		if (locale != null) {
			result = getByLocaleLanguage(locale.getLanguage());
		}

		return result;
	}

	/**
	 * Returns the supported locale type that has a corresponding
	 * {@link java.util.Locale} that has the same language as the given
	 * <code>language</code>.
	 * 
	 * @param language
	 *            The language for which the corresponding supported locale type
	 *            should be retrieved.
	 * @return The requested supported locale type, or <code>null</code> when it
	 *         could not be found.
	 */
	private static SupportedLocaleType getByLocaleLanguage(final String language) {
		SupportedLocaleType result = null;

		final SupportedLocaleType[] supportedLocaleTypes = values();
		int i = 0;

		while (result == null && supportedLocaleTypes.length > i) {
			result = getSupportedLocaleWhenLanguagesAreEqual(
					supportedLocaleTypes[i++], language);
		}

		return result;
	}

	/**
	 * Returns the given <code>supportedLocaleType</code> if it has the same
	 * language as the given parameter.
	 * 
	 * @param supportedLocaleType
	 *            The supported locale type.
	 * @param language
	 *            The language that the supported locale type should have.
	 * @return The given supported locale type if it has the same language as
	 *         the given parameter, <code>null</code> otherwise.
	 */
	private static SupportedLocaleType getSupportedLocaleWhenLanguagesAreEqual(
			final SupportedLocaleType supportedLocaleType, final String language) {

		SupportedLocaleType result = null;

		if (supportedLocaleType.getLocale().getLanguage().equals(language)) {
			result = supportedLocaleType;
		}

		return result;
	}
}
