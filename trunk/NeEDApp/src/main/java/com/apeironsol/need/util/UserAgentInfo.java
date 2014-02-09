/**
 * 
 */
package com.apeironsol.need.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pradeep
 * 
 */
public class UserAgentInfo {
	// User-Agent and Accept HTTP request headers
	private String				userAgent			= "";
	private String				httpAccept			= "";

	// Initialize some initial smartphone string variables.
	public static final String	engineWebKit		= "webkit";
	public static final String	deviceAndroid		= "android";
	public static final String	deviceIphone		= "iphone";
	public static final String	deviceIpod			= "ipod";
	public static final String	deviceSymbian		= "symbian";
	public static final String	deviceS60			= "series60";
	public static final String	deviceS70			= "series70";
	public static final String	deviceS80			= "series80";
	public static final String	deviceS90			= "series90";
	public static final String	deviceWinMob		= "windows ce";
	public static final String	deviceWindows		= "windows";
	public static final String	deviceIeMob			= "iemobile";
	public static final String	enginePie			= "wm5 pie";		// An
																		// old
																		// Windows
																		// Mobile
	public static final String	deviceBB			= "blackberry";
	public static final String	vndRIM				= "vnd.rim";		// Detectable
																		// when
																		// BB
																		// devices
																		// emulate
																		// IE or
																		// Firefox
	public static final String	deviceBBStorm		= "blackberry95";	// Storm
																		// 1 and
																		// 2
	public static final String	devicePalm			= "palm";
	public static final String	deviceWebOS			= "webos";			// For
																		// Palm's
																		// new
																		// WebOS
																		// devices

	public static final String	engineBlazer		= "blazer";		// Old
																		// Palm
	public static final String	engineXiino			= "xiino";			// Another
																		// old
																		// Palm

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

	public static final String	engineOpera			= "opera";			// Popular
																		// browser
	public static final String	engineNetfront		= "netfront";		// Common
																		// embedded
																		// OS
																		// browser
	public static final String	engineUpBrowser		= "up.browser";	// common
																		// on
																		// some
																		// phones
	public static final String	engineOpenWeb		= "openweb";		// Transcoding
																		// by
																		// OpenWave
																		// server
	public static final String	deviceMidp			= "midp";			// a
																		// mobile
																		// Java
																		// technology
	public static final String	uplink				= "up.link";

	public static final String	devicePda			= "pda";			// some
																		// devices
																		// report
																		// themselves
																		// as
																		// PDAs
	public static final String	mini				= "mini";			// Some
																		// mobile
																		// browsers
																		// put
																		// "mini"
																		// in
																		// their
																		// names.
	public static final String	mobile				= "mobile";		// Some
																		// mobile
																		// browsers
																		// put
																		// "mobile"
																		// in
																		// their
																		// user
																		// agent
																		// strings.
	public static final String	mobi				= "mobi";			// Some
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
	public static final String	qtembedded			= "qt embedded";	// for
																		// Sony
																		// Mylo
	public static final String	mylocom2			= "com2";			// for
																		// Sony
																		// Mylo
																		// also

	// In some UserAgents, the only clue is the manufacturer.
	public static final String	manuSonyEricsson	= "sonyericsson";
	public static final String	manuericsson		= "ericsson";
	public static final String	manuSamsung1		= "sec-sgh";
	public static final String	manuSony			= "sony";

	// In some UserAgents, the only clue is the operator.
	public static final String	svcDocomo			= "docomo";
	public static final String	svcKddi				= "kddi";
	public static final String	svcVodafone			= "vodafone";

	// Standard desktop browser detection strings
	public static final String	msie				= "msie";
	public static final String	msie60				= "msie 6.0";
	public static final String	msie61				= "msie 6.1";
	public static final String	msie7				= "msie 7.0";
	public static final String	msie8				= "msie 8.0";
	public static final String	msie9				= "msie 9.0";
	public static final String	firefox				= "firefox";
	public static final String	safari				= "apple";
	public static final String	chrome				= "chrome";
	public static final String	opera				= "presto";

	// OS Detection
	public static final String	windows				= "windows";

	/**
	 * Initialize the userAgent and httpAccept variables
	 * 
	 * @param userAgent
	 *            the User-Agent header
	 * @param httpAccept
	 *            the Accept header
	 */
	public UserAgentInfo(final String userAgent, final String httpAccept) {
		if (userAgent != null) {
			this.userAgent = userAgent.toLowerCase();
		}
		if (httpAccept != null) {
			this.httpAccept = httpAccept.toLowerCase();
		}
	}

	/**
	 * Initialize the userAgent and httpAccept variables by getting the headers
	 * from the HttpServletRequest
	 * 
	 * @param request
	 *            the HttpServletRequest to get the header information from
	 */
	public UserAgentInfo(final HttpServletRequest request) {
		this(request.getHeader("User-Agent"), request.getHeader("Accept"));
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
	 * Detects if the current device is an iPhone.
	 */
	public boolean detectIphone() {
		// The iPod touch says it's an iPhone! So let's disambiguate.
		return (this.userAgent.indexOf(deviceIphone) != -1) && !this.detectIpod();
	}

	/**
	 * Detects if the current device is an iPod Touch.
	 */
	public boolean detectIpod() {
		return this.userAgent.indexOf(deviceIpod) != -1;
	}

	/**
	 * Detects if the current device is an iPhone or iPod Touch.
	 */
	public boolean detectIphoneOrIpod() {
		// We repeat the searches here because some iPods may report themselves
		// as an iPhone, which would be okay.
		return (this.userAgent.indexOf(deviceIphone) != -1) || (this.userAgent.indexOf(deviceIpod) != -1);
	}

	/**
	 * Detects if the current device is an Android OS-based device.
	 */
	public boolean detectAndroid() {
		return this.userAgent.indexOf(deviceAndroid) != -1;
	}

	/**
	 * Detects if the current device is an Android OS-based device and
	 * the browser is based on WebKit.
	 */
	public boolean detectAndroidWebKit() {
		return this.detectAndroid() && this.detectWebkit();
	}

	/**
	 * Detects if the current browser is based on WebKit.
	 */
	public boolean detectWebkit() {
		return this.userAgent.indexOf(engineWebKit) != -1;
	}

	/**
	 * Detects if the current browser is the S60 Open Source Browser.
	 */
	public boolean detectS60OssBrowser() {
		// First, test for WebKit, then make sure it's either Symbian or S60.
		return this.detectWebkit() && ((this.userAgent.indexOf(deviceSymbian) != -1) || (this.userAgent.indexOf(deviceS60) != -1));
	}

	/**
	 * 
	 * Detects if the current device is any Symbian OS-based device,
	 * including older S60, Series 70, Series 80, Series 90, and UIQ,
	 * or other browsers running on these devices.
	 */
	public boolean detectSymbianOS() {
		return (this.userAgent.indexOf(deviceSymbian) != -1) || (this.userAgent.indexOf(deviceS60) != -1) || (this.userAgent.indexOf(deviceS70) != -1)
				|| (this.userAgent.indexOf(deviceS80) != -1) || (this.userAgent.indexOf(deviceS90) != -1);
	}

	/**
	 * Detects if the current browser is a Windows Mobile device.
	 */
	public boolean detectWindowsMobile() {
		// Most devices use 'Windows CE', but some report 'iemobile'
		// and some older ones report as 'PIE' for Pocket IE.
		return (this.userAgent.indexOf(deviceWinMob) != -1) || (this.userAgent.indexOf(deviceIeMob) != -1) || (this.userAgent.indexOf(enginePie) != -1)
				|| (this.detectWapWml() && (this.userAgent.indexOf(deviceWindows) != -1));
	}

	/**
	 * Detects if the current browser is a BlackBerry of some sort.
	 */
	public boolean detectBlackBerry() {
		return (this.userAgent.indexOf(deviceBB) != -1) || (this.httpAccept.indexOf(vndRIM) != -1);
	}

	/**
	 * Detects if the current browser is a BlackBerry Touch
	 * device, such as the Storm
	 */
	public boolean detectBlackBerryTouch() {
		return this.userAgent.indexOf(deviceBBStorm) != -1;
	}

	/**
	 * Detects if the current browser is on a PalmOS device.
	 */
	public boolean detectPalmOS() {
		// Most devices nowadays report as 'Palm', but some older ones reported
		// as Blazer or Xiino.
		if ((this.userAgent.indexOf(devicePalm) != -1) || (this.userAgent.indexOf(engineBlazer) != -1)
				|| ((this.userAgent.indexOf(engineXiino) != -1) && !this.detectPalmWebOS())) {
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
	 * Detects if the current browser is on a Palm device
	 * running the new WebOS.
	 */
	public boolean detectPalmWebOS() {
		return this.userAgent.indexOf(deviceWebOS) != -1;
	}

	/**
	 * Check to see whether the device is any device
	 * in the 'smartphone' category.
	 */
	public boolean detectSmartphone() {
		return (this.detectIphoneOrIpod() || this.detectS60OssBrowser() || this.detectSymbianOS() || this.detectWindowsMobile() || this.detectBlackBerry()
				|| this.detectPalmOS() || this.detectPalmWebOS() || this.detectAndroid());
	}

	/**
	 * Detects whether the device is a Brew-powered device.
	 */
	public boolean detectBrewDevice() {
		return this.userAgent.indexOf(deviceBrew) != -1;
	}

	/**
	 * Detects the Danger Hiptop device.
	 */
	public boolean detectDangerHiptop() {
		return (this.userAgent.indexOf(deviceDanger) != -1) || (this.userAgent.indexOf(deviceHiptop) != -1);
	}

	/**
	 * Detects Opera Mobile or Opera Mini.
	 * Added by AHand
	 */
	public boolean detectOperaMobile() {
		return (this.userAgent.indexOf(engineOpera) != -1) && ((this.userAgent.indexOf(mini) != -1) || (this.userAgent.indexOf(mobi) != -1));
	}

	/**
	 * Detects whether the device supports WAP or WML.
	 */
	public boolean detectWapWml() {
		return (this.httpAccept.indexOf(vndwap) != -1) || (this.httpAccept.indexOf(wml) != -1);
	}

	/**
	 * The quick way to detect for a mobile device.
	 * Will probably detect most recent/current mid-tier Feature Phones
	 * as well as smartphone-class devices.
	 */
	public boolean detectMobileQuick() {
		// Ordered roughly by market share, WAP/XML > Brew > Smartphone.
		if (this.detectWapWml()) {
			return true;
		}
		if (this.detectBrewDevice()) {
			return true;
		}
		// Updated by AHand
		if (this.detectOperaMobile()) {
			return true;
		}
		if (this.userAgent.indexOf(engineUpBrowser) != -1) {
			return true;
		}
		if (this.userAgent.indexOf(engineOpenWeb) != -1) {
			return true;
		}
		if (this.userAgent.indexOf(deviceMidp) != -1) {
			return true;
		}

		if (this.detectSmartphone()) {
			return true;
		}
		if (this.detectDangerHiptop()) {
			return true;
		}

		if (this.detectMidpCapable()) {
			return true;
		}

		if (this.userAgent.indexOf(devicePda) != -1) {
			return true;
		}
		if (this.userAgent.indexOf(mobile) != -1) {
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

	/**
	 * Detects if the current device is a Sony Playstation.
	 */
	public boolean detectSonyPlaystation() {
		return this.userAgent.indexOf(devicePlaystation) != -1;
	}

	/**
	 * Detects if the current device is a Nintendo game device.
	 */
	public boolean detectNintendo() {
		return (this.userAgent.indexOf(deviceNintendo) != -1) || (this.userAgent.indexOf(deviceWii) != -1) || (this.userAgent.indexOf(deviceNintendoDs) != -1);
	}

	/**
	 * Detects if the current device is a Microsoft Xbox.
	 */
	public boolean detectXbox() {
		return this.userAgent.indexOf(deviceXbox) != -1;
	}

	/**
	 * Detects if the current device is an Internet-capable game console.
	 */
	public boolean detectGameConsole() {
		return this.detectSonyPlaystation() || this.detectNintendo() || this.detectXbox();
	}

	/**
	 * Detects if the current device supports MIDP, a mobile Java technology.
	 */
	public boolean detectMidpCapable() {
		return (this.userAgent.indexOf(deviceMidp) != -1) || (this.httpAccept.indexOf(deviceMidp) != -1);
	}

	/**
	 * Detects if the current device is on one of the Maemo-based Nokia Internet
	 * Tablets.
	 */
	public boolean detectMaemoTablet() {
		return ((this.userAgent.indexOf(maemo) != -1) || ((this.userAgent.indexOf(maemoTablet) != -1) && (this.userAgent.indexOf(linux) != -1)));
	}

	/**
	 * Detects if the current device is an Archos media player/Internet tablet.
	 */
	public boolean detectArchos() {
		return this.userAgent.indexOf(deviceArchos) != -1;
	}

	/**
	 * Detects if the current browser is a Sony Mylo device.
	 * Updated by AHand
	 */
	public boolean detectSonyMylo() {
		return (this.userAgent.indexOf(manuSony) != -1) && ((this.userAgent.indexOf(qtembedded) != -1) || (this.userAgent.indexOf(mylocom2) != -1));
	}

	/**
	 * The longer and more thorough way to detect for a mobile device.
	 * Will probably detect most feature phones,
	 * smartphone-class devices, Internet Tablets,
	 * Internet-enabled game consoles, etc.
	 * This ought to catch a lot of the more obscure and older devices, also --
	 * but no promises on thoroughness!
	 */
	public boolean detectMobileLong() {
		return this.detectMobileQuick() || this.detectMaemoTablet() || this.detectGameConsole();
	}

	// *****************************
	// For Desktop Browsers
	// *****************************
	public boolean detectMSIE() {
		return this.userAgent.indexOf(msie) != -1;
	}

	public boolean detectMSIE6() {
		return (this.userAgent.indexOf(msie60) != -1) && (this.userAgent.indexOf(msie61) != -1);
	}

	public boolean detectMSIE7() {
		return this.userAgent.indexOf(msie7) != -1;
	}

	public boolean detectMSIE8() {
		return this.userAgent.indexOf(msie8) != -1;
	}

	public boolean detectMSIE9() {
		return this.userAgent.indexOf(msie9) != -1;
	}

	public boolean detectFirefox() {
		return this.userAgent.indexOf(firefox) != -1;
	}

	public boolean detectSafari() {
		return this.userAgent.indexOf(safari) != -1;
	}

	public boolean detectChrome() {
		return this.userAgent.indexOf(chrome) != -1;
	}

	public boolean detectOpera() {
		return this.userAgent.indexOf(opera) != -1;
	}

	public boolean detectWindows() {
		return this.userAgent.indexOf(windows) != -1;
	}

	// *****************************
	// For Mobile Web Site Design
	// *****************************

	/**
	 * The quick way to detect for a tier of devices.
	 * This method detects for devices which can
	 * display iPhone-optimized web content.
	 * Includes iPhone, iPod Touch, Android, Palm WebOS, etc.
	 */
	public boolean detectTierIphone() {
		return this.detectIphoneOrIpod() || this.detectPalmWebOS() || this.detectAndroid() || this.detectAndroidWebKit();
	}

	/**
	 * The quick way to detect for a tier of devices.
	 * This method detects for all smartphones, but
	 * excludes the iPhone Tier devices.
	 */
	public boolean detectTierSmartphones() {
		return this.detectSmartphone() && (!this.detectTierIphone());
	}

	/**
	 * The quick way to detect for a tier of devices.
	 * This method detects for all other types of phones,
	 * but excludes the iPhone and Smartphone Tier devices.
	 */
	public boolean detectTierOtherPhones() {
		return this.detectMobileQuick() && (!this.detectTierIphone()) && (!this.detectTierSmartphones());
	}
}
