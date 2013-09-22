/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.apeironsol.need.util.constants.Labeled;

/**
 * Utility class for Enum.
 * 
 * @author Pradeep
 */
public abstract class EnumUtil {

	/**
	 * Reusable Comparator instance for labeled objects.
	 */
	public static Comparator<Labeled>	LABEL_COMPARATOR	= new Comparator<Labeled>() {
																@Override
																public int compare(final Labeled a, final Labeled b) {
																	return a.getLabel().compareTo(b.getLabel());
																}
															};

	/**
	 * Return the sorted labels for the Enum.
	 */
	public static <E extends Enum<?> & Labeled> List<E> getEnumsSortedByLabels(final Class<E> enumClass) {
		List<String> descriptions = new ArrayList<String>();
		List<E> sortedEnums = new ArrayList<E>();
		Map<String, E> enumMap = new TreeMap<String, E>();
		for (E e : enumClass.getEnumConstants()) {
			descriptions.add(e.getLabel());
			enumMap.put(e.getLabel(), e);
		}
		Collections.sort(descriptions);
		for (String label : descriptions) {
			sortedEnums.add(enumMap.get(label));
		}
		return sortedEnums;
	}
}