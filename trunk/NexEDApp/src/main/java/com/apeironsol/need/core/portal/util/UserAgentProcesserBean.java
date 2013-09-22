/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.portal.util;

/**
 * Class for user agent processor.
 * 
 * @author Pradeep
 */
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;

@Named
@Scope(value = "session")
public class UserAgentProcesserBean implements Serializable {

	private static final long	serialVersionUID	= -2399884208294434814L;

	private UAgentInfo			uAgentInfo;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		String userAgentStr = request.getHeader("user-agent");
		String httpAccept = request.getHeader("Accept");
		this.uAgentInfo = new UAgentInfo(userAgentStr, httpAccept);
	}

	public boolean isPhone() {
		// Detects a whole tier of phones that support similar functionality as
		// the iphone
		return this.uAgentInfo.detectTierIphone();
	}

	public boolean isTablet() {
		return this.uAgentInfo.detectTierTablet();
	}

	public boolean isMobile() {
		return this.isPhone() || this.isTablet();
	}
}
