/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.reporting.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author pradeep
 * 
 */
public class JReportUtils {

	private static String	EMPTY_STRING	= "";

	/**
	 * Returns map of jasper report parameters defined in the class.
	 * 
	 * @param clazz
	 *            class in which report parameters are defined.
	 * @param object
	 *            the object of type class.
	 * @return map of jasper report parameters defined in the class.
	 */
	public static Map<String, Object> getJReportParameters(final Class<?> clazz, final Object object) {
		final Map<String, Object> reportParameters = new HashMap<String, Object>();
		final List<Field> fields = ReflectionHelper.getDeclaredFieldsInClass(clazz);
		for (final Field field : fields) {
			final Object fieldValue = ReflectionHelper.getFieldValue(object, field);
			reportParameters.put(getFieldStorageName(field), fieldValue == null ? EMPTY_STRING : fieldValue);
		}

		return reportParameters;
	}

	/**
	 * Returns the name of the field under which it is to be found.
	 * 
	 * @param field
	 *            The field.
	 * @return The name of the field, which is either the 'real' name of the
	 *         field as it appears in the code, or the overridden value that can
	 *         be set when using the {@link ModelField} annotation.
	 */
	private static String getFieldStorageName(final Field field) {
		String result = field.getName();

		final JReportParameter annotation = field.getAnnotation(JReportParameter.class);
		if (annotation != null && !annotation.name().equals(JReportParameter.NO_NAME_SET)) {
			result = annotation.name();
		}

		return result;
	}

}
