/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.constants;

import java.util.ArrayList;
import java.util.List;

import com.apeironsol.need.util.portal.ViewUtil;

/**
 * Enums for week day constant
 * 
 * @author Pradeep
 */
public enum WeekDayConstant implements Labeled {
	SUNDAY("sunday"), MONDAY("monday"), TUESDAY("tuesday"), WEDNESDAY("wednesday"), THURSDAY("thursday"), FRIDAY(
			"friday"), SATURDAY("saturday");

	private String	label;

	WeekDayConstant(final String label) {
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

	public static WeekDayConstant getWeekDay(final int weekDay) {
		switch (weekDay) {
			case 1:
				return WeekDayConstant.SUNDAY;
			case 2:
				return WeekDayConstant.MONDAY;
			case 3:
				return WeekDayConstant.TUESDAY;
			case 4:
				return WeekDayConstant.WEDNESDAY;
			case 5:
				return WeekDayConstant.THURSDAY;
			case 6:
				return WeekDayConstant.FRIDAY;
			case 7:
				return WeekDayConstant.SATURDAY;
			default:
				return null;
		}
	}

	public static List<WeekDayConstant> getWeekDayConstantsExcludingWeekEnd(final List<WeekDayConstant> weekEnd) {
		List<WeekDayConstant> weekDays = new ArrayList<WeekDayConstant>();
		for (WeekDayConstant weekDay : WeekDayConstant.values()) {
			if (!weekEnd.contains(weekDay)) {
				weekDays.add(weekDay);
			}
		}
		return weekDays;
	}

}
