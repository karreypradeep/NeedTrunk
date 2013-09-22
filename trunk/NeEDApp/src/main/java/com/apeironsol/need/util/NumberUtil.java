/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util;

public class NumberUtil {

	public static boolean isNumber(final String number) {
		boolean result = false;
		try {
			Integer.parseInt(number);
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

}
