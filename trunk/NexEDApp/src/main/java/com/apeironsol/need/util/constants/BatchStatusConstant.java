/**
 * 
 */
package com.apeironsol.need.util.constants;

import com.apeironsol.need.util.portal.ViewUtil;

/**
 * @author pradeepk
 * 
 */
public enum BatchStatusConstant implements Labeled {
	CREATED("created"), DISTRIBUTED("distributed"), FINISHED("finished"), CANCELLED("cancelled"), SENDING_FAILED(
			"sending_failed");

	private String	label;

	BatchStatusConstant(final String label) {
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
