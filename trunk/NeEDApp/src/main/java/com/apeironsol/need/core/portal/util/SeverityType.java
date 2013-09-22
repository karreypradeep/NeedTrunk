/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */

package com.apeironsol.need.core.portal.util;

/**
 * <p>
 * The various kinds of severity that are available to the system.
 * </p>
 * 
 * <p>
 * This is a very generic declaration of the available types of severity, but in
 * practice they are closely related to the types of severity that are available
 * in the web services. There, every response holds a <code>status</code>
 * element which in turn is able to hold <em>errors</em>, <em>warnings</em> and
 * <em>information messages</em>.
 * </p>
 * 
 * <p>
 * By defining the severity types in a context-agnostic way, they may be useful
 * in other situations as well.
 * </p>
 * 
 * @author rens.van.leeuwen@leanapps.com
 * @version $Revision: 74346 $
 */
public enum SeverityType {

	/** The error severity. */
	ERROR,

	/** The warning severity. */
	WARNING,

	/** The info severity. */
	INFO;
}
