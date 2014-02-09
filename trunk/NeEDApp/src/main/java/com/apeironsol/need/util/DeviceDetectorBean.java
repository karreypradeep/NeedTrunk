package com.apeironsol.need.util;

import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import fiftyone.mobile.detection.BaseDeviceInfo;
import fiftyone.mobile.detection.Provider;

@Named
@ApplicationScoped
public class DeviceDetectorBean {

	// Analyses the detection made in Detector and determines if the device is
	// mobile.
	public boolean getIsMobile() {
		boolean result = false;
		final FacesContext context = FacesContext.getCurrentInstance();
		final Provider p = (Provider) context.getExternalContext().getApplicationMap().get("51Degrees.mobi");
		if (p != null) {
			final String userAgent = ((HttpServletRequest) context.getExternalContext().getRequest()).getHeader("User-Agent");
			final BaseDeviceInfo b = p.getDeviceInfo(userAgent);
			if (b != null) {
				if (b.getFirstPropertyValue("IsMobile").equalsIgnoreCase("True")) {
					result = true;
				}
			}
		}
		return result;
	}
}