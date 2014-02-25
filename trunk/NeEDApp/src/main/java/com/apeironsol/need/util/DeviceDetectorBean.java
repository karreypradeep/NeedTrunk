package com.apeironsol.need.util;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;

import com.apeironsol.need.util.portal.ViewUtil;

@Named
@ApplicationScoped
public class DeviceDetectorBean {

	// Analyses the detection made in Detector and determines if the device is
	// mobile.
	public boolean getIsMobile() {
		final UAgentInfo agent = new UAgentInfo(ViewUtil.getHttpServletRequest().getHeader("user-agent"), ViewUtil.getHttpServletRequest().getHeader("Accept"));
		return agent.isMobileOrTablet();
	}
}