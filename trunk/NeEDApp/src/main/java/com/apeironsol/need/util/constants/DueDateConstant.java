package com.apeironsol.need.util.constants;

import com.apeironsol.need.util.portal.ViewUtil;

public enum DueDateConstant implements Labeled {

	ONE("one"), TWO("two"), THREE("three"), FOUR("four"), FIVE("five"), SIX("six"), SEVEN("seven"), EIGHT("eight"), NINE(
			"nine"), TEN("ten"), ELEVEN("eleven"), TWELVE("twelve"), THEERTEEN("theerteen"), FOURTEEN("fourteen"), FIFTEEN(
			"fifteen"), SIXTEEN("sixteen"), SEVENTEEN("seventeen"), EIGHTEEN("eightteen"), NINTEEN("ninteen"), TWENTY(
			"twenty"), TWENTY_ONE("twenty_one"), TWENTY_TWO("twenty_two"), TWENTY_THREE("twenty_three"), TWENTY_FOUR(
			"twenty_four"), TWENTY_FIVE("twenty_five"), TWENTY_SIX("twenty_six"), TWENTY_SEVEN("twenty_seven"), TWENTY_EIGHT(
			"twenty_eight"), TWENTY_NINE("twenty_nine"), THIRTY("thirty"), THIRTY_ONE("thity_one");

	private String	label;

	DueDateConstant(final String label) {
		this.setLabel(label);
	}

	@Override
	public String getLabel() {
		return ViewUtil.getEnumLabel(this.label);
	}

	@Override
	public void setLabel(final String label) {
		this.label = label;
	}

	public static DueDateConstant getDay(final int day) {
		switch (day) {
			case 1:
				return DueDateConstant.ONE;
			case 2:
				return DueDateConstant.TWO;
			case 3:
				return DueDateConstant.THREE;
			case 4:
				return DueDateConstant.FOUR;
			case 5:
				return DueDateConstant.FIVE;
			case 6:
				return DueDateConstant.SIX;
			case 7:
				return DueDateConstant.SEVEN;
			case 8:
				return DueDateConstant.EIGHT;
			case 9:
				return DueDateConstant.NINE;
			case 10:
				return DueDateConstant.TEN;
			case 11:
				return DueDateConstant.ELEVEN;
			case 12:
				return DueDateConstant.TWELVE;
			case 13:
				return DueDateConstant.THEERTEEN;
			case 14:
				return DueDateConstant.FOURTEEN;
			case 15:
				return DueDateConstant.FIFTEEN;
			case 16:
				return DueDateConstant.SIXTEEN;
			case 17:
				return DueDateConstant.SEVENTEEN;
			case 18:
				return DueDateConstant.EIGHTEEN;
			case 19:
				return DueDateConstant.NINTEEN;
			case 20:
				return DueDateConstant.TWELVE;
			case 21:
				return DueDateConstant.TWENTY_ONE;
			case 22:
				return DueDateConstant.TWENTY_TWO;
			case 23:
				return DueDateConstant.TWENTY_THREE;
			case 24:
				return DueDateConstant.TWENTY_FOUR;
			case 25:
				return DueDateConstant.TWENTY_FIVE;
			case 26:
				return DueDateConstant.TWENTY_SIX;
			case 27:
				return DueDateConstant.TWENTY_SEVEN;
			case 28:
				return DueDateConstant.TWENTY_EIGHT;
			case 29:
				return DueDateConstant.TWENTY_NINE;
			case 30:
				return DueDateConstant.THIRTY;
			case 31:
				return DueDateConstant.THIRTY_ONE;
			default:
				return null;
		}
	}

}
