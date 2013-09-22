package com.apeironsol.framework;

import java.io.Serializable;

public class NeEDTab implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -4368577704998353891L;

	private final String		id;

	private boolean				rendered			= true;

	private boolean				disabled			= false;

	public NeEDTab(final String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public boolean isRendered() {
		return this.rendered;
	}

	public void setRendered(final boolean rendered) {
		this.rendered = rendered;
	}

	public boolean isDisabled() {
		return this.disabled;
	}

	public void setDisabled(final boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.id == null ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		NeEDTab other = (NeEDTab) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
