/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.portal;


/**
 * Class to describe header menu items.
 * 
 * @see BaseDescriptor
 * @author Pradeep
 */

public class HeaderDescriptor extends BaseDescriptor {

	private static final long	serialVersionUID	= 6355850028091656137L;
	private String				outcome;

	public String getOutcome() {
		return this.outcome;
	}

	public void setOutcome(final String outcome) {
		this.outcome = outcome;
	}

}
