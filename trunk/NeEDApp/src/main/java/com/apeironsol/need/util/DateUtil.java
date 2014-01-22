/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.Months;
import org.joda.time.Period;
import org.joda.time.Weeks;
import org.springframework.util.Assert;

import com.apeironsol.need.util.portal.ViewUtil;

/**
 * Date utility class.
 * 
 * @author Pradeep
 * @author pradeep
 */
public class DateUtil {

	public static int dateDiffInYears(final Calendar from, final Calendar to) {
		final Interval interval = new Interval(from.getTime().getTime(), to.getTime().getTime());
		final Period period = interval.toPeriod();
		return period.getYears();
	}

	public static int dateDiffInMonths(final Calendar from, final Calendar to) {
		final Interval interval = new Interval(from.getTime().getTime(), to.getTime().getTime());
		final Period period = interval.toPeriod();
		return period.getMonths();
	}

	public static int dateDiffInDays(final Calendar from, final Calendar to) {
		final Interval interval = new Interval(from.getTime().getTime(), to.getTime().getTime());
		final Period period = interval.toPeriod();
		return period.getDays();
	}

	public static int dateDiffInYears(final Date from, final Date to) {
		final Interval interval = new Interval(from.getTime(), to.getTime());
		final Period period = interval.toPeriod();
		return period.getYears();
	}

	public static int dateDiffInMonths(final Date from, final Date to) {
		Assert.notNull(from);
		Assert.notNull(to);
		return Months.monthsBetween(new DateMidnight(from.getTime()), new DateMidnight(to.getTime())).getMonths();
	}

	public static int dateDiffInDays(final Date from, final Date to) {
		Assert.notNull(from);
		Assert.notNull(to);
		return Days.daysBetween(new DateMidnight(from.getTime()), new DateMidnight(to.getTime())).getDays();
	}

	public static int dateDiffInHours(final Date from, final Date to) {
		Assert.notNull(from);
		Assert.notNull(to);
		return Hours.hoursBetween(new DateMidnight(from.getTime()), new DateMidnight(to.getTime())).getHours();
	}

	public static int dateDiffInWeeks(final Date from, final Date to) {
		Assert.notNull(from);
		Assert.notNull(to);
		return Weeks.weeksBetween(new DateMidnight(from.getTime()), new DateMidnight(to.getTime())).getWeeks();
	}

	public static Date getSystemDate() {

		// Time stamp
		final Date dt = new Date();
		final DateTimeZone zone = DateTimeZone.forID(ViewUtil.getSystemTimezone());
		final DateTime localtime = new DateTime(dt.getTime(), zone);
		final String dateString = localtime.toString("MM/dd/yyyy hh:mm:ss");
		final DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		Date date = localtime.toDate();
		try {
			date = formatter.parse(dateString);
		} catch (final ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * Clears time information from the supplied calendar.
	 * 
	 * @param calendar
	 *            The calendar for which the time info must be cleared.
	 */
	public static void clearTimeInfo(final Calendar calendar) {

		// Store year, month and day
		final int year = calendar.get(Calendar.YEAR);
		final int month = calendar.get(Calendar.MONTH);
		final int day = calendar.get(Calendar.DATE);

		calendar.clear();
		// Restore year, month and day
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, day);
	}

	/**
	 * Clears time information from the supplied date.
	 * 
	 * @param date
	 *            The date for which the time info must be cleared. d
	 */
	public static void clearTimeInfo(final java.util.Date date) {

		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		clearTimeInfo(calendar);
		date.setTime(calendar.getTime().getTime());
	}

	public static Calendar returnFirstDateOfMonth(final Calendar calendar) {
		final Calendar calendarFirstOfMonth = Calendar.getInstance();
		calendarFirstOfMonth.setTime(calendar.getTime());
		clearTimeInfo(calendarFirstOfMonth);
		calendarFirstOfMonth.set(Calendar.DATE, 1);
		return calendarFirstOfMonth;
	}

	public static Date returnFirstDateOfMonth(final Date month) {
		final Calendar calendarFirstOfMonth = Calendar.getInstance();
		calendarFirstOfMonth.setTime(month);
		clearTimeInfo(calendarFirstOfMonth);
		calendarFirstOfMonth.set(Calendar.DATE, 1);
		return calendarFirstOfMonth.getTime();
	}

	public static Calendar returnLastDateOfMonth(final Calendar calendar) {
		final Calendar calendarFirstOfMonth = Calendar.getInstance();
		calendarFirstOfMonth.setTime(calendar.getTime());
		clearTimeInfo(calendarFirstOfMonth);
		calendarFirstOfMonth.set(Calendar.DATE, 1);
		calendarFirstOfMonth.add(Calendar.MONTH, 1);
		calendarFirstOfMonth.add(Calendar.DATE, -1);
		return calendarFirstOfMonth;
	}

	public static Date returnLastDateOfMonth(final Date month) {
		final Calendar calendarFirstOfMonth = Calendar.getInstance();
		calendarFirstOfMonth.setTime(month);
		clearTimeInfo(calendarFirstOfMonth);
		calendarFirstOfMonth.set(Calendar.DATE, 1);
		calendarFirstOfMonth.add(Calendar.MONTH, 1);
		calendarFirstOfMonth.add(Calendar.DATE, -1);
		return calendarFirstOfMonth.getTime();
	}

	public static int getDateOfMonthSupplied(final Date month) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(month);
		return calendar.get(Calendar.DATE);
	}

	public static int getDayOfWeekForSuppliedDate(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);

	}

	/**
	 * Sets time supplied in time parameter to date passed as date.
	 * 
	 * @param date
	 *            date to which time has to be changed as per supplied time
	 *            value.
	 * @param time
	 *            new time.
	 * @return
	 */
	public static Date changeTimeOfDate(final Date date, final Date time) {
		final Calendar dateCalendar = Calendar.getInstance();
		final Calendar timeCalendar = Calendar.getInstance();
		dateCalendar.setTime(date);
		timeCalendar.setTime(time);
		dateCalendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
		dateCalendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
		return dateCalendar.getTime();
	}

	public static int calculateNumberOfWeeksInMonth(final Calendar date, final int firstDayOfWeek) {
		Calendar calendar = Calendar.getInstance();
		calendar = (Calendar) date.clone();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		return calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
	}

	public static int calculateNumberOfWeeksInMonth(final Date date, final int firstDayOfWeek) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calculateNumberOfWeeksInMonth(calendar, firstDayOfWeek);
	}

	public static int getFirstDateOfWeekOfMonth(final Calendar month, final int weekOfMonth, final int firstDayOfWeek) {
		final Calendar calendar = (Calendar) month.clone();
		calendar.setFirstDayOfWeek(firstDayOfWeek);
		int firstDateOfWeekOfMonth = 0;
		final int numOfDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 1; i <= numOfDaysInMonth; i++) {
			calendar.set(Calendar.DATE, i);
			if (calendar.get(Calendar.WEEK_OF_MONTH) == weekOfMonth) {
				firstDateOfWeekOfMonth = i;
				break;
			}
		}
		return firstDateOfWeekOfMonth;
	}

	public static int getLastDateOfWeekOfMonth(final Calendar month, final int weekOfMonth, final int firstDayOfWeek) {
		final Calendar calendar = (Calendar) month.clone();
		calendar.setFirstDayOfWeek(firstDayOfWeek);
		int lastDateOfWeekOfMonth = 0;
		final int numOfDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		final int numOfWeeksInMonth = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
		for (int i = 1; i <= numOfDaysInMonth; i++) {
			calendar.set(Calendar.DATE, i);
			if (weekOfMonth == numOfWeeksInMonth) {
				lastDateOfWeekOfMonth = numOfDaysInMonth;
				break;
			} else if (calendar.get(Calendar.WEEK_OF_MONTH) == weekOfMonth + 1) {
				lastDateOfWeekOfMonth = i - 1;
				break;
			}
		}
		return lastDateOfWeekOfMonth;
	}

	public static boolean isFirstDayOfMonth(final Date date) {
		final Calendar startDate = Calendar.getInstance();
		startDate.setTime(date);
		return startDate.get(Calendar.DAY_OF_MONTH) == 1;
	}

	public static Date getDateForDisplayFormat(final Date date) {
		final DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date formattedDate = date;
		try {
			formattedDate = formatter.parse(date.toString());
		} catch (final ParseException e) {
			e.printStackTrace();
		}
		return formattedDate;
	}

}
