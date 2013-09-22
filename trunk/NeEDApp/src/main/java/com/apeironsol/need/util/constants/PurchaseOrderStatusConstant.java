/**
 * 
 */
package com.apeironsol.need.util.constants;

import com.apeironsol.need.util.portal.ViewUtil;

/**
 * @author pradeepk
 * 
 */
public enum PurchaseOrderStatusConstant implements Labeled {
	NEW("new"), APPROVED("approved"), REQUEST_FOR_APPROVAL("request_for_approval"), REJECTED("rejected"), CLOSED(
			"closed"), UNDER_CONSIDERATION("under_consideration"), ;

	private String	label;

	PurchaseOrderStatusConstant(final String label) {
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
