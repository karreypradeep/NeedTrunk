/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.constants;

/**
 * Enums for time zone constants
 * 
 * @author Pradeep
 */
public enum TimezoneConstant implements Labeled {

	GMT_MINUS_12_00("Kwajalein", "(GMT -12:00) Eniwetok, Kwajalein"), GMT_MINUS_11_00("US/Samoa",
			"(GMT -11:00) Midway Island, Samoa"), GMT_MINUS_10_00("US/Hawaii", "(GMT -10:00) Hawaii"), GMT_MINUS_09_00(
			"US/Alaska", "(GMT -9:00) Alaska"), GMT_MINUS_08_00("US/Pacific",
			"(GMT -8:00) Pacific Time (US &amp; Canada)"), GMT_MINUS_07_00("US/Mountain",
			"(GMT -7:00) Mountain Time (US &amp; Canada)"), GMT_MINUS_06_00("US/Central",
			"(GMT -6:00) Central Time (US &amp; Canada), Mexico City"), GMT_MINUS_05_00("US/Eastern",
			"(GMT -5:00) Eastern Time (US &amp; Canada), Bogota, Lima"), GMT_MINUS_04_00("Canada/Atlantic",
			"(GMT -4:00) Atlantic Time (Canada), Caracas, La Paz"), GMT_MINUS_03_30("Canada/Newfoundland",
			"(GMT -3:30) Newfoundland"), GMT_MINUS_03_00("America/Araguaina",
			"(GMT -3:00) Brazil, Buenos Aires, Georgetown"), GMT_MINUS_02_00("Atlantic/South_Georgia",
			"(GMT -2:00) Mid-Atlantic"), GMT_MINUS_01_00("Atlantic/Azores",
			"(GMT -1:00 hour) Azores, Cape Verde Islands"), GMT_MINUS_00_00("Europe/London",
			"(GMT) Western Europe Time, London, Lisbon, Casablanca"), GMT_PLUS_01_00("Europe/Brussels",
			"(GMT +1:00 hour) Brussels, Copenhagen, Madrid, Paris"), GMT_PLUS_02_00("Europe/Kaliningrad",
			"(GMT +2:00) Kaliningrad, South Africa"), GMT_PLUS_03_00("Europe/Moscow",
			"(GMT +3:00) Baghdad, Riyadh, Moscow, St. Petersburg"), GMT_PLUS_03_30("Asia/Tehran", "(GMT +3:30) Tehran"), GMT_PLUS_04_00(
			"Asia/Dubai", "(GMT +4:00) Abu Dhabi, Muscat, Baku, Tbilisi"), GMT_PLUS_04_30("Asia/Kabul",
			"(GMT +4:30) Kabul"), GMT_PLUS_05_00("Asia/Karachi",
			"(GMT +5:00) Ekaterinburg, Islamabad, Karachi, Tashkent"), GMT_PLUS_05_30("Asia/Kolkata",
			"(GMT +5:30) Bombay, Calcutta, Madras, New Delhi"), GMT_PLUS_05_45("Asia/Kathmandu",
			"(GMT +5:45) Kathmandu"), GMT_PLUS_06_00("Asia/Dhaka", "(GMT +6:00) Almaty, Dhaka, Colombo"), GMT_PLUS_07_00(
			"Asia/Bangkok", "(GMT +7:00) Bangkok, Hanoi, Jakarta"), GMT_PLUS_08_00("Asia/Singapore",
			"(GMT +8:00) Beijing, Perth, Singapore, Hong Kong"), GMT_PLUS_09_00("Asia/Tokyo",
			"(GMT +9:00) Tokyo, Seoul, Osaka, Sapporo, Yakutsk"), GMT_PLUS_09_30("Australia/Darwin",
			"(GMT +9:30) Adelaide, Darwin"), GMT_PLUS_10_00("Asia/Vladivostok",
			"(GMT +10:00) Eastern Australia, Guam, Vladivostok"), GMT_PLUS_11_00("Asia/Magadan",
			"(GMT +11:00) Magadan, Solomon Islands, New Caledonia"), GMT_PLUS_12_00("Asia/Kamchatka",
			"(GMT +12:00) Auckland, Wellington, Fiji, Kamchatka");

	private String	label;

	private String	zone;

	TimezoneConstant(final String zone, final String label) {
		this.label = label;
		this.zone = zone;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public void setLabel(final String label) {
		this.label = label;
	}

	public String getZone() {
		return this.zone;
	}

	public void setZone(final String zone) {
		this.zone = zone;
	}

}
