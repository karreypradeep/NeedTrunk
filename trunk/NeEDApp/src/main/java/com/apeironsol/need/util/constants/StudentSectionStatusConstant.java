package com.apeironsol.need.util.constants;

import com.apeironsol.need.util.EnumUtil;
import com.apeironsol.need.util.portal.ViewUtil;

public enum StudentSectionStatusConstant implements Labeled {

	ACTIVE("active"), COMPLETED("completed"), PROMOTED("promoted"), TRANSFERED("transfered"), DROPOUT("dropout"), ACCEPT_FOR_DROPOUT("accept_for_dropout");

	private String	label;

	StudentSectionStatusConstant(final String label) {
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

	public static StudentSectionStatusConstant[] getValuesSortedByLabels() {
		return EnumUtil.getEnumsSortedByLabels(StudentSectionStatusConstant.class).toArray(
				new StudentSectionStatusConstant[StudentSectionStatusConstant.values().length]);
	}

	public static StudentSectionStatusConstant getStudentSectionStatusForStudentStatus(final StudentStatusConstant studentStatusConstant) {
		if (StudentStatusConstant.ACTIVE.equals(studentStatusConstant)) {
			return ACTIVE;
		} else if (StudentStatusConstant.DROPOUT.equals(studentStatusConstant)) {
			return DROPOUT;
		} else if (StudentStatusConstant.ACCEPT_FOR_DROPOUT.equals(studentStatusConstant)) {
			return ACCEPT_FOR_DROPOUT;
		} else if (StudentStatusConstant.ALMUNUS.equals(studentStatusConstant)) {
			return COMPLETED;
		} else {
			return null;
		}
	}

}
