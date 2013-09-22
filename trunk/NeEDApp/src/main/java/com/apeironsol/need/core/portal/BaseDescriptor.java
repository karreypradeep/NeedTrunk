/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.core.portal;

/**
 * Base describes class that describes the base menu items.
 * 
 * @author Pradeep
 */

import java.io.Serializable;

public class BaseDescriptor implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long	serialVersionUID	= 5614594358147757458L;
	private String				id;
	private String				name;
	private boolean				newItem;
	private boolean				currentItem;
	private boolean				enabled;
	private String				resourceURI;

	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean isNewItem() {
		return this.newItem;
	}

	public void setNewItem(final boolean newItem) {
		this.newItem = newItem;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isNewEnabled() {
		return this.isNewItem() && this.isCurrentlyEnabled();
	}

	/**
	 * Evaluates that this group is enabled in current context
	 * 
	 * @return
	 */
	public boolean isCurrentlyEnabled() {
		if (!this.enabled) {
			return false;
		}
		return true;
	}

	public boolean isCurrentItem() {
		return this.currentItem;
	}

	public void setCurrentItem(final boolean currentItem) {
		this.currentItem = currentItem;
	}

	public String getResourceURI() {
		return this.resourceURI;
	}

	public void setResourceURI(final String resourceURI) {
		this.resourceURI = resourceURI;
	}

	@Override
	public String toString() {
		return "BaseDescriptor[" + this.name + "]";
	}
}
