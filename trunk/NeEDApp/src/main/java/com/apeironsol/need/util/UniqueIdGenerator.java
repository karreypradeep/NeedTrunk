/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util;

public class UniqueIdGenerator {

	static long	current	= System.currentTimeMillis();

	static public synchronized long get() {
		return current++;
	}
}
