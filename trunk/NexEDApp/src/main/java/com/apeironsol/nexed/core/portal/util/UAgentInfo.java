/**
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 * 
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 * 
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 **/

/* *******************************************
 * // Copyright 2010, Anthony Hand
 * //
 * // File version date: March 14, 2011
 * // Updates:
 * // - Added a stored variable 'isTierTablet' and getIsTierTablet() which are
 * initialized in InitDeviceScan().
 * // - Added a variable and the new DetectBlackBerryTablet() function.
 * // - Added a variable and the new DetectAndroidTablet() function. This is a
 * first draft!
 * // - Added the new DetectTierTablet() function. Use this to detect any of the
 * new
 * // larger-screen HTML5 capable tablets. (The 7 inch Galaxy Tab doesn't
 * quality right now.)
 * // - Moved Windows Phone 7 from iPhone Tier to Rich CSS Tier. Sorry,
 * Microsoft, but IE 7 isn't good enough.
 * //
 * // LICENSE INFORMATION
 * // Licensed under the Apache License, Version 2.0 (the "License");
 * // you may not use this file except in compliance with the License.
 * // You may obtain a copy of the License at
 * // http://www.apache.org/licenses/LICENSE-2.0
 * // Unless required by applicable law or agreed to in writing,
 * // software distributed under the License is distributed on an
 * // "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * // either express or implied. See the License for the specific
 * // language governing permissions and limitations under the License.
 * //
 * //
 * // ABOUT THIS PROJECT
 * // Project Owner: Anthony Hand
 * // Email: anthony.hand@gmail.com
 * // Web Site: http://www.mobileesp.com
 * // Source Files: http://code.google.com/p/mobileesp/
 * //
 * // Versions of this code are available for:
 * // PHP, JavaScript, Java, ASP.NET (C#), and Ruby
 * //
 * // *******************************************
 */
package com.apeironsol.nexed.core.portal.util;

import java.io.Serializable;

/**
 * The DetectSmartPhone class encapsulates information about a browser's
 * connection to your web site. You can use it to
 * find out whether the browser asking for your site's content is probably
 * running on a mobile device. The methods were
 * written so you can be as granular as you want. For example, enquiring whether
 * it's as specific as an iPod Touch or as
 * general as a smartphone class device. The object's methods return true, or
 * false.
 */
public class UAgentInfo implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5291061844968676802L;

	// User-Agent and Accept HTTP request headers
	private String				userAgent			= "";
	private String				httpAccept			= "";

	// Optional: store values for quickly accessing same info multiple times.
	// Call InitDeviceScan() to initialize these values.
	public boolean				isIphone			= false;
	public boolean				isTierTablet		= false;
	public boolean				isTierIphone		= false;
	public boolean				isTierRichCss		= false;
	public boolean				isTierGenericMobile	= false;

	// Initialize some initial smartphone string variables.
	public static final String	engineWebKit		= "webkit";

	public static final String	deviceIphone		= "iphone";
	public static final String	deviceIpod			= "ipod";
	public static final String	deviceIpad			= "ipad";
	public static final String	deviceMacPpc		= "macintosh";				// Used
																				// for
																				// disambiguation

	public static final String	deviceAndroid		= "android";
	public static final String	deviceGoogleTV		= "googletv";
	public static final String	deviceXoom			= "xoom";					// Motorola
																				// Xoom

	public static final String	deviceSymbian		= "symbian";
	public static final String	deviceS60			= "series60";
	public static final String	deviceS70			= "series70";
	public static final String	deviceS80			= "series80";
	public static final String	deviceS90			= "series90";

	public static final String	deviceWinPhone7		= "windows phone os 7";
	public static final String	deviceWinMob		= "windows ce";
	public static final String	deviceWindows		= "windows";
	public static final String	deviceIeMob			= "iemobile";
	public static final String	devicePpc			= "ppc";					// Stands
																				// for
																				// PocketPC
	public static final String	enginePie			= "wm5 pie";				// An
																				// old
																				// Windows
																				// Mobile

	public static final String	deviceBB			= "blackberry";
	public static final String	vndRIM				= "vnd.rim";				// Detectable
																				// when
																				// BB
																				// devices
																				// emulate
																				// IE
																				// or
																				// Firefox
	public static final String	deviceBBStorm		= "blackberry95";			// Storm
																				// 1
																				// and
																				// 2
	public static final String	deviceBBBold		= "blackberry97";			// Bold
	public static final String	deviceBBTour		= "blackberry96";			// Tour
	public static final String	deviceBBCurve		= "blackberry89";			// Curve
																				// 2
	public static final String	deviceBBTorch		= "blackberry 98";			// Torch
	public static final String	deviceBBPlaybook	= "playbook";				// PlayBook
																				// tablet

	public static final String	devicePalm			= "palm";
	public static final String	deviceWebOS			= "webos";					// For
																				// Palm's
																				// new
																				// WebOS
																				// devices
	public static final String	engineBlazer		= "blazer";				// Old
																				// Palm
	public static final String	engineXiino			= "xiino";					// Another
																				// old
																				// Palm

	public static final String	deviceKindle		= "kindle";				// Amazon
																				// Kindle,
																				// eInk
																				// one.

	public static final String	deviceNuvifone		= "nuvifone";				// Garmin
																				// Nuvifone

	// Initialize variables for mobile-specific content.
	public static final String	vndwap				= "vnd.wap";
	public static final String	wml					= "wml";

	// Initialize variables for other random devices and mobile browsers.
	public static final String	deviceBrew			= "brew";
	public static final String	deviceDanger		= "danger";
	public static final String	deviceHiptop		= "hiptop";
	public static final String	devicePlaystation	= "playstation";
	public static final String	deviceNintendoDs	= "nitro";
	public static final String	deviceNintendo		= "nintendo";
	public static final String	deviceWii			= "wii";
	public static final String	deviceXbox			= "xbox";
	public static final String	deviceArchos		= "archos";

	public static final String	engineOpera			= "opera";					// Popular
																				// browser
	public static final String	engineNetfront		= "netfront";				// Common
																				// embedded
																				// OS
																				// browser
	public static final String	engineUpBrowser		= "up.browser";			// common
																				// on
																				// some
																				// phones
	public static final String	engineOpenWeb		= "openweb";				// Transcoding
																				// by
																				// OpenWave
																				// server
	public static final String	deviceMidp			= "midp";					// a
																				// mobile
																				// Java
																				// technology
	public static final String	uplink				= "up.link";
	public static final String	engineTelecaQ		= "teleca q";				// a
																				// modern
																				// feature
																				// phone
																				// browser
	public static final String	devicePda			= "pda";					// some
																				// devices
																				// report
																				// themselves
																				// as
																				// PDAs
	public static final String	mini				= "mini";					// Some
																				// mobile
																				// browsers
																				// put
																				// "mini"
																				// in
																				// their
																				// names.
	public static final String	mobile				= "mobile";				// Some
																				// mobile
																				// browsers
																				// put
																				// "mobile"
																				// in
																				// their
																				// user
																				// agent
																				// strings.
	public static final String	mobi				= "mobi";					// Some
																				// mobile
																				// browsers
																				// put
																				// "mobi"
																				// in
																				// their
																				// user
																				// agent
																				// strings.

	// Use Maemo, Tablet, and Linux to test for Nokia"s Internet Tablets.
	public static final String	maemo				= "maemo";
	public static final String	maemoTablet			= "tablet";
	public static final String	linux				= "linux";
	public static final String	qtembedded			= "qt embedded";			// for
																				// Sony
																				// Mylo
	public static final String	mylocom2			= "com2";					// for
																				// Sony
																				// Mylo
																				// also

	// In some UserAgents, the only clue is the manufacturer.
	public static final String	manuSonyEricsson	= "sonyericsson";
	public static final String	manuericsson		= "ericsson";
	public static final String	manuSamsung1		= "sec-sgh";
	public static final String	manuSony			= "sony";
	public static final String	manuHtc				= "htc";					// Popular
																				// Android
																				// and
																				// WinMo
																				// manufacturer

	// In some UserAgents, the only clue is the operator.
	public static final String	svcDocomo			= "docomo";
	public static final String	svcKddi				= "kddi";
	public static final String	svcVodafone			= "vodafone";

	// Disambiguation strings.
	public static final String	disUpdate			= "update";				// pda
																				// vs.
																				// update

	/**
	 * Initialize the userAgent and httpAccept variables
	 * 
	 * @param userAgent
	 *            the User-Agent header
	 * @param httpAccept
	 *            the Accept header
	 */
	public UAgentInfo(final String userAgent, final String httpAccept) {
		if (userAgent != null) {
			this.userAgent = userAgent.toLowerCase();
		}
		if (httpAccept != null) {
			this.httpAccept = httpAccept.toLowerCase();
		}

		// Intialize key stored values.
		this.initDeviceScan();
	}

	/**
	 * Return the lower case HTTP_USER_AGENT
	 */
	public String getUserAgent() {
		return this.userAgent;
	}

	/**
	 * Return the lower case HTTP_ACCEPT
	 */
	public String getHttpAccept() {
		return this.httpAccept;
	}

	/**
	 * Return whether the device is an Iphone or iPod Touch
	 */
	public boolean getIsIphone() {
		return this.isIphone;
	}

	/**
	 * Return whether the device is in the Tablet Tier.
	 */
	public boolean getIsTierTablet() {
		return this.isTierTablet;
	}

	/**
	 * Return whether the device is in the Iphone Tier.
	 */
	public boolean getIsTierIphone() {
		return this.isTierIphone;
	}

	/**
	 * Return whether the device is an Iphone or iPod Touch
	 */
	public boolean getIsTierRichCss() {
		return this.isTierRichCss;
	}

	/**
	 * Return whether the device is an Iphone or iPod Touch
	 */
	public boolean getIsTierGenericMobile() {
		return this.isTierGenericMobile;
	}

	/**
	 * Initialize Key Stored Values.
	 */
	public void initDeviceScan() {
		this.isIphone = this.detectIphoneOrIpod();
		this.isTierTablet = this.detectTierTablet();
		this.isTierIphone = this.detectTierIphone();
		this.isTierRichCss = this.detectTierRichCss();
		this.isTierGenericMobile = this.detectTierOtherPhones();
	}

	/**
	 * Detects if the current device is an iPhone.
	 */
	public boolean detectIphone() {
		// The iPad and iPod touch say they're an iPhone! So let's disambiguate.
		if (this.userAgent.indexOf(deviceIphone) != -1 && !this.detectIpad() && !this.detectIpod()) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current device is an iPod Touch.
	 */
	public boolean detectIpod() {
		if (this.userAgent.indexOf(deviceIpod) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current device is an iPad tablet.
	 */
	public boolean detectIpad() {
		if (this.userAgent.indexOf(deviceIpad) != -1 && this.detectWebkit()) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current device is an iPhone or iPod Touch.
	 */
	public boolean detectIphoneOrIpod() {
		// We repeat the searches here because some iPods may report themselves
		// as an iPhone, which would be okay.
		if (this.userAgent.indexOf(deviceIphone) != -1 || this.userAgent.indexOf(deviceIpod) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current device is an Android OS-based device. Ignores
	 * tablets (Honeycomb and later).
	 */
	public boolean detectAndroid() {
		if (this.DetectAndroidTablet()) { // Exclude tablets
			return false;
		}
		if (this.userAgent.indexOf(deviceAndroid) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current device is an Android tablet. Must be at least 8
	 * inches and Honeycomb or later. This
	 * function will be updated rapidly as good tablets emerge in 2011.
	 */
	public boolean DetectAndroidTablet() {
		if (this.userAgent.indexOf(deviceXoom) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current device is an Android OS-based device and the
	 * browser is based on WebKit.
	 */
	public boolean detectAndroidWebKit() {
		if (this.detectAndroid() && this.detectWebkit()) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current device is a GoogleTV.
	 */
	public boolean DetectGoogleTV() {
		if (this.userAgent.indexOf(deviceGoogleTV) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current browser is based on WebKit.
	 */
	public boolean detectWebkit() {
		if (this.userAgent.indexOf(engineWebKit) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current browser is the S60 Open Source Browser.
	 */
	public boolean detectS60OssBrowser() {
		// First, test for WebKit, then make sure it's either Symbian or S60.
		if (this.detectWebkit() && (this.userAgent.indexOf(deviceSymbian) != -1 || this.userAgent.indexOf(deviceS60) != -1)) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current device is any Symbian OS-based device, including
	 * older S60, Series 70, Series 80, Series
	 * 90, and UIQ, or other browsers running on these devices.
	 */
	public boolean detectSymbianOS() {
		if (this.userAgent.indexOf(deviceSymbian) != -1 || this.userAgent.indexOf(deviceS60) != -1 || this.userAgent.indexOf(deviceS70) != -1
				|| this.userAgent.indexOf(deviceS80) != -1 || this.userAgent.indexOf(deviceS90) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current browser is a Windows Phone 7 device.
	 */
	public boolean DetectWindowsPhone7() {
		if (this.userAgent.indexOf(deviceWinPhone7) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current browser is a Windows Mobile device. Excludes
	 * Windows Phone 7 devices. Focuses on Windows
	 * Mobile 6.xx and earlier.
	 */
	public boolean detectWindowsMobile() {
		// Exclude new Windows Phone 7.
		if (this.DetectWindowsPhone7()) {
			return false;
		}
		// Most devices use 'Windows CE', but some report 'iemobile'
		// and some older ones report as 'PIE' for Pocket IE.
		// We also look for instances of HTC and Windows for many of their WinMo
		// devices.
		if (this.userAgent.indexOf(deviceWinMob) != -1 || this.userAgent.indexOf(deviceWinMob) != -1 || this.userAgent.indexOf(deviceIeMob) != -1
				|| this.userAgent.indexOf(enginePie) != -1 || (this.userAgent.indexOf(manuHtc) != -1 && this.userAgent.indexOf(deviceWindows) != -1)
				|| (this.detectWapWml() && this.userAgent.indexOf(deviceWindows) != -1)) {
			return true;
		}

		// Test for Windows Mobile PPC but not old Macintosh PowerPC.
		if (this.userAgent.indexOf(devicePpc) != -1 && !(this.userAgent.indexOf(deviceMacPpc) != -1)) {
			return true;
		}

		return false;
	}

	/**
	 * Detects if the current browser is a BlackBerry of some sort.
	 */
	public boolean detectBlackBerry() {
		if (this.userAgent.indexOf(deviceBB) != -1 || this.httpAccept.indexOf(vndRIM) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current browser is on a BlackBerry tablet device. Example:
	 * PlayBook
	 */
	public boolean DetectBlackBerryTablet() {
		if (this.userAgent.indexOf(deviceBBPlaybook) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current browser is a BlackBerry device AND uses a
	 * WebKit-based browser. These are signatures for
	 * the new BlackBerry OS 6. Examples: Torch
	 */
	public boolean DetectBlackBerryWebKit() {
		if (this.userAgent.indexOf(deviceBB) != -1 && this.userAgent.indexOf(engineWebKit) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current browser is a BlackBerry Touch device, such as the
	 * Storm or Torch
	 */
	public boolean detectBlackBerryTouch() {
		if (this.userAgent.indexOf(deviceBBStorm) != -1 || this.userAgent.indexOf(deviceBBTorch) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current browser is a BlackBerry device AND has a more
	 * capable recent browser. Examples, Storm,
	 * Bold, Tour, Curve2 Excludes the new BlackBerry OS 6 browser!!
	 */
	public boolean detectBlackBerryHigh() {
		// Disambiguate for BlackBerry OS 6 (WebKit) browser
		if (this.DetectBlackBerryWebKit()) {
			return false;
		}
		if (this.detectBlackBerry()) {
			if (this.detectBlackBerryTouch() || this.userAgent.indexOf(deviceBBBold) != -1 || this.userAgent.indexOf(deviceBBTour) != -1
					|| this.userAgent.indexOf(deviceBBCurve) != -1) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Detects if the current browser is a BlackBerry device AND has an older,
	 * less capable browser. Examples: Pearl,
	 * 8800, Curve1
	 */
	public boolean detectBlackBerryLow() {
		if (this.detectBlackBerry()) {
			// Assume that if it's not in the High tier, then it's Low
			if (this.detectBlackBerryHigh()) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * Detects if the current browser is on a PalmOS device.
	 */
	public boolean detectPalmOS() {
		// Most devices nowadays report as 'Palm', but some older ones reported
		// as Blazer or Xiino.
		if (this.userAgent.indexOf(devicePalm) != -1 || this.userAgent.indexOf(engineBlazer) != -1 || this.userAgent.indexOf(engineXiino) != -1) {
			// Make sure it's not WebOS first
			if (this.detectPalmWebOS()) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * Detects if the current browser is on a Palm device running the new WebOS.
	 */
	public boolean detectPalmWebOS() {
		if (this.userAgent.indexOf(deviceWebOS) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current browser is a Garmin Nuvifone.
	 */
	public boolean detectGarminNuvifone() {
		if (this.userAgent.indexOf(deviceNuvifone) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Check to see whether the device is any device in the 'smartphone'
	 * category.
	 */
	public boolean detectSmartphone() {
		return (this.detectIphoneOrIpod() || this.detectS60OssBrowser() || this.detectSymbianOS() || this.detectAndroid() || this.detectWindowsMobile()
				|| this.DetectWindowsPhone7() || this.detectBlackBerry() || this.detectPalmWebOS() || this.detectPalmOS() || this.detectGarminNuvifone());
	}

	/**
	 * Detects whether the device is a Brew-powered device.
	 */
	public boolean detectBrewDevice() {
		if (this.userAgent.indexOf(deviceBrew) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects the Danger Hiptop device.
	 */
	public boolean detectDangerHiptop() {
		if (this.userAgent.indexOf(deviceDanger) != -1 || this.userAgent.indexOf(deviceHiptop) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects Opera Mobile or Opera Mini.
	 */
	public boolean detectOperaMobile() {
		if (this.userAgent.indexOf(engineOpera) != -1 && (this.userAgent.indexOf(mini) != -1 || this.userAgent.indexOf(mobi) != -1)) {
			return true;
		}
		return false;
	}

	/**
	 * Detects whether the device supports WAP or WML.
	 */
	public boolean detectWapWml() {
		if (this.httpAccept.indexOf(vndwap) != -1 || this.httpAccept.indexOf(wml) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current device is an Amazon Kindle.
	 */
	public boolean detectKindle() {
		if (this.userAgent.indexOf(deviceKindle) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * The quick way to detect for a mobile device. Will probably detect most
	 * recent/current mid-tier Feature Phones as
	 * well as smartphone-class devices. Excludes Apple iPads.
	 */
	public boolean detectMobileQuick() {
		// Let's say no if it's an iPad, which contains 'mobile' in its user
		// agent
		if (this.detectIpad()) {
			return false;
		}
		// Most mobile browsing is done on smartphones
		if (this.detectSmartphone()) {
			return true;
		}

		if (this.detectWapWml()) {
			return true;
		}
		if (this.detectBrewDevice()) {
			return true;
		}
		if (this.detectOperaMobile()) {
			return true;
		}

		if (this.userAgent.indexOf(engineNetfront) != -1) {
			return true;
		}
		if (this.userAgent.indexOf(engineUpBrowser) != -1) {
			return true;
		}
		if (this.userAgent.indexOf(engineOpenWeb) != -1) {
			return true;
		}

		if (this.detectDangerHiptop()) {
			return true;
		}

		if (this.detectMidpCapable()) {
			return true;
		}

		if (this.detectMaemoTablet()) {
			return true;
		}
		if (this.detectArchos()) {
			return true;
		}

		if ((this.userAgent.indexOf(devicePda) != -1) && (this.userAgent.indexOf(disUpdate) < 0)) { // no
			// index
			// found
			return true;
		}
		if (this.userAgent.indexOf(mobile) != -1) {
			return true;
		}

		return false;
	}

	/**
	 * Detects if the current device is a Sony Playstation.
	 */
	public boolean detectSonyPlaystation() {
		if (this.userAgent.indexOf(devicePlaystation) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current device is a Nintendo game device.
	 */
	public boolean detectNintendo() {
		if (this.userAgent.indexOf(deviceNintendo) != -1 || this.userAgent.indexOf(deviceWii) != -1 || this.userAgent.indexOf(deviceNintendoDs) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current device is a Microsoft Xbox.
	 */
	public boolean detectXbox() {
		if (this.userAgent.indexOf(deviceXbox) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current device is an Internet-capable game console.
	 */
	public boolean detectGameConsole() {
		if (this.detectSonyPlaystation() || this.detectNintendo() || this.detectXbox()) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current device supports MIDP, a mobile Java technology.
	 */
	public boolean detectMidpCapable() {
		if (this.userAgent.indexOf(deviceMidp) != -1 || this.httpAccept.indexOf(deviceMidp) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current device is on one of the Maemo-based Nokia Internet
	 * Tablets.
	 */
	public boolean detectMaemoTablet() {
		if (this.userAgent.indexOf(maemo) != -1) {
			return true;
		} else if (this.userAgent.indexOf(maemoTablet) != -1 && this.userAgent.indexOf(linux) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current device is an Archos media player/Internet tablet.
	 */
	public boolean detectArchos() {
		if (this.userAgent.indexOf(deviceArchos) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Detects if the current browser is a Sony Mylo device.
	 */
	public boolean detectSonyMylo() {
		if (this.userAgent.indexOf(manuSony) != -1 && (this.userAgent.indexOf(qtembedded) != -1 || this.userAgent.indexOf(mylocom2) != -1)) {
			return true;
		}
		return false;
	}

	/**
	 * The longer and more thorough way to detect for a mobile device. Will
	 * probably detect most feature phones,
	 * smartphone-class devices, Internet Tablets, Internet-enabled game
	 * consoles, etc. This ought to catch a lot of the
	 * more obscure and older devices, also -- but no promises on thoroughness!
	 */
	public boolean detectMobileLong() {
		if (this.detectMobileQuick() || this.detectGameConsole() || this.detectSonyMylo()) {
			return true;
		}

		// detect older phones from certain manufacturers and operators.
		if (this.userAgent.indexOf(uplink) != -1) {
			return true;
		}
		if (this.userAgent.indexOf(manuSonyEricsson) != -1) {
			return true;
		}
		if (this.userAgent.indexOf(manuericsson) != -1) {
			return true;
		}
		if (this.userAgent.indexOf(manuSamsung1) != -1) {
			return true;
		}

		if (this.userAgent.indexOf(svcDocomo) != -1) {
			return true;
		}
		if (this.userAgent.indexOf(svcKddi) != -1) {
			return true;
		}
		if (this.userAgent.indexOf(svcVodafone) != -1) {
			return true;
		}

		return false;
	}

	// *****************************
	// For Mobile Web Site Design
	// *****************************

	/**
	 * The quick way to detect for a tier of devices. This method detects for
	 * the new generation of HTML 5 capable,
	 * larger screen tablets. Includes iPad, Android (e.g., Xoom), BB Playbook,
	 * etc.
	 */
	public boolean detectTierTablet() {
		if (this.detectIpad() || this.DetectAndroidTablet() || this.DetectBlackBerryTablet()) {
			return true;
		}
		return false;
	}

	/**
	 * The quick way to detect for a tier of devices. This method detects for
	 * devices which can display iPhone-optimized
	 * web content. Includes iPhone, iPod Touch, Android, Palm WebOS, etc.
	 */
	public boolean detectTierIphone() {
		if (this.detectIphoneOrIpod() || this.detectAndroid() || this.detectAndroidWebKit() || this.DetectBlackBerryWebKit() || this.detectPalmWebOS()
				|| this.detectGarminNuvifone() || this.detectMaemoTablet()) {
			return true;
		}
		return false;
	}

	/**
	 * The quick way to detect for a tier of devices. This method detects for
	 * devices which are likely to be capable of
	 * viewing CSS content optimized for the iPhone, but may not necessarily
	 * support JavaScript. Excludes all iPhone
	 * Tier devices.
	 */
	public boolean detectTierRichCss() {
		// The following devices are explicitly ok.
		// Note: 'High' BlackBerry devices ONLY
		if (this.detectMobileQuick()
				&& (this.detectWebkit() || this.detectS60OssBrowser() || this.detectBlackBerryHigh() || this.DetectWindowsPhone7()
						|| this.detectWindowsMobile() || this.userAgent.indexOf(engineTelecaQ) != -1)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * The quick way to detect for a tier of devices. This method detects for
	 * all other types of phones, but excludes
	 * the iPhone and RichCSS Tier devices.
	 */
	public boolean detectTierOtherPhones() {
		if (this.detectMobileQuick() && (!this.detectTierIphone()) && (!this.detectTierRichCss())) {
			return true;
		}
		return false;
	}
}
