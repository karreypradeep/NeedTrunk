/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.constants;

import java.util.Calendar;
import java.util.Date;

import com.apeironsol.need.util.portal.ViewUtil;

/**
 * Enums for month constant
 * 
 * @author Pradeep
 */
public enum MonthConstant implements Labeled {
	JANUARY("jan", 1), FEBRUARY("feb", 2), MARCH("mar", 3), APRIL("apr", 4), MAY("may", 5), JUNE("jun", 6), JULY("jul", 7), AUGUST("aug", 8), SEPTEMBER("sep",
			9), OCTOBER("oct", 10), NOVEMBER("nov", 11), DECEMBER("dec", 12);

	private String			label;

	private final Integer	monthNumber;

	MonthConstant(final String label, final Integer monthNumber) {
		this.label = label;
		this.monthNumber = monthNumber;
	}

	@Override
	public String getLabel() {
		return ViewUtil.getEnumLabel(this.label);
	}

	@Override
	public void setLabel(final String label) {
		this.label = label;
	}

	public static MonthConstant getMonth(final int month) {
		switch (month) {
			case 1:
				return MonthConstant.JANUARY;
			case 2:
				return MonthConstant.FEBRUARY;
			case 3:
				return MonthConstant.MARCH;
			case 4:
				return MonthConstant.APRIL;
			case 5:
				return MonthConstant.MAY;
			case 6:
				return MonthConstant.JUNE;
			case 7:
				return MonthConstant.JULY;
			case 8:
				return MonthConstant.AUGUST;
			case 9:
				return MonthConstant.SEPTEMBER;
			case 10:
				return MonthConstant.OCTOBER;
			case 11:
				return MonthConstant.NOVEMBER;
			case 12:
				return MonthConstant.DECEMBER;
			default:
				return null;
		}
	}

	/**
	 * @return the monthNumber
	 */
	public Integer getMonthNumber() {
		return this.monthNumber;
	}

	public static MonthConstant getMonth(final Date month) {
		Calendar calMonth = Calendar.getInstance();
		calMonth.setTime(month);
		return getMonth(calMonth.get(Calendar.MONTH) + 1);
	}
}
