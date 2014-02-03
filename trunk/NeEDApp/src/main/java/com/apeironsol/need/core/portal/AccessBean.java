/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.portal;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import com.apeironsol.need.util.portal.ViewUtil;

@Named
@Scope(value = "session")
public class AccessBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long	serialVersionUID	= 5956437365910958622L;

	private String				currentAccess		= "portal";

	@PostConstruct
	public void init() {

	}

	public String getCurrentAccess() {
		return this.currentAccess;
	}

	public void setCurrentAccess(final String currentAccess) {
		this.currentAccess = currentAccess;
	}

	public boolean isAcccessingOrganization() {
		if ("organization".equals(getResourceAccessLevel())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isAccessingBranch() {
		if ("branch".equals(getResourceAccessLevel())) {
			return true;
		} else if ("student".equals(getResourceAccessLevel())) {
			return false;
		} else if ("admission".equals(getResourceAccessLevel())) {
			return false;
		} else {
			return false;
		}
	}

	public boolean isAccessingStudent() {
		if ("student".equals(getResourceAccessLevel())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isAccessingParent() {
		if ("parent".equals(getResourceAccessLevel())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isAccessingEmployee() {
		if ("employee".equals(getResourceAccessLevel())) {
			return true;
		} else {
			return false;
		}
	}

	public String getResourceAccessLevel() {
		final ExternalContext context = ViewUtil.getExternalContext();
		final HttpServletRequest request = (HttpServletRequest) context.getRequest();
		String requestURL = request.getRequestURI();
		if (StringUtils.isEmpty(requestURL)) {
			return null;
		} else if (requestURL.contains("access-denied")) {

			final DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) context.getSessionMap().get(WebAttributes.SAVED_REQUEST);
			if (defaultSavedRequest != null) {
				requestURL = defaultSavedRequest.getRequestURL();
			}
		}

		else if (requestURL.contains("pages/organization")) {
			return "organization";
		} else if (requestURL.contains("pages/branch")) {
			return "branch";
		} else if (requestURL.contains("pages/student")) {
			return "student";
		} else if (requestURL.contains("pages/parent")) {
			return "parent";
		} else if (requestURL.contains("pages/employee")) {
			return "employee";
		} else if (requestURL.contains("pages/admission")) {
			return "admission";
		}
		return null;

	}

	public boolean isSessionExists() {
		final ExternalContext context = ViewUtil.getExternalContext();
		if ((context.getSession(false) != null) && (ViewUtil.getPrincipal() != null)) {
			return true;
		}
		return false;
	}

	public void authorizationErrorMessages() {

		ViewUtil.addMessage("UnAuthorized to access error.", FacesMessage.SEVERITY_ERROR);
		ViewUtil.addMessage("User 					: " + ViewUtil.getPrincipal(), FacesMessage.SEVERITY_ERROR);

		final Collection<GrantedAuthority> grantedAuthorities = ViewUtil.getGrantedAuthorities();
		String auths = "";
		for (final GrantedAuthority grantedAuthority : grantedAuthorities) {
			auths = auths + grantedAuthority.getAuthority();
			auths = auths + " , ";
		}

		auths = auths.substring(0, auths.length() - 3);

		ViewUtil.addMessage("Granted Authorities 	: " + auths, FacesMessage.SEVERITY_ERROR);

	}
}
