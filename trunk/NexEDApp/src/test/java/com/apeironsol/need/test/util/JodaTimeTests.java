package com.apeironsol.need.test.util;

import java.util.Date;

import org.joda.time.DateTime;

public class JodaTimeTests {

	public static void main(final String args[]) {

		// Week In the Month

		DateTime dateTime = new DateTime(new Date()).plusMonths(1);

		DateTime startDate = dateTime.dayOfMonth().withMinimumValue();

		DateTime monthEndDate = dateTime.dayOfMonth().withMaximumValue();

		int count = 0;

		while (true) {

			count++;

			if (startDate.dayOfWeek().withMaximumValue().isAfter(monthEndDate.getMillis())) {
				System.out.println(startDate + "--------" + monthEndDate);
				break;
			} else {
				System.out.println(startDate + "--------" + startDate.dayOfWeek().withMaximumValue());
			}

			startDate = startDate.plusWeeks(1);

		}

		System.out.println("Total number of week to display : " + count);
	}
}
