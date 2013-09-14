/**
 * 
 */
package com.apeironsol.nexed.util.constants;

import com.apeironsol.nexed.util.portal.ViewUtil;

/**
 * @author pradeepk
 * 
 */
public enum PurchaseOrderTypeConstant implements Labeled {
	COMPUTER_PERIPHERALS("computer_peripherals"), STATIONERY("stationery"), CANTEEN("canteen"), INFRASTRUCTURE(
			"infrastructure"), MISCELLANEOUS("miscellaneous");

	private String	label;

	PurchaseOrderTypeConstant(final String label) {
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
