/**
 * 
 */
package com.apeironsol.need.util.constants;

import com.apeironsol.need.util.portal.ViewUtil;

/**
 * @author pradeepk
 * 
 */
public enum BatchLogMessageStatusConstant implements Labeled {
	SUCCESS("success"), CANCELLED("cancelled"), FAILED("failed");

	private String	label;

	BatchLogMessageStatusConstant(final String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return ViewUtil.getEnumLabel(this.label);
	}

	@Override
	public void setLabel(final String label) {
		this.label = label;
	}

}
