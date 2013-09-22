/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.portal;

/**
 * View utility class.
 * 
 * @author Pradeep
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;

import com.apeironsol.need.util.config.ContextParams;

public final class ViewUtil {

	private static final Logger	log						= Logger.getLogger(ViewUtil.class);

	public static final String	MODELVALIDATIONMESSAGES	= "com.apeironsol.smsystem.portal.ModelValidationMessages";

	public static String getSystemTimezone() {
		return ServletContextUtils.getContextInitParam(ContextParams.SYSTEM_TIMEZONE);
	}

	public static UIViewRoot getViewRoot() {
		return FacesContext.getCurrentInstance().getViewRoot();
	}

	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public static ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	public static ResourceBundle getResourceBundle(final String name) {
		return getFacesContext().getApplication().getResourceBundle(getFacesContext(), name);
	}

	public static void putInSession(final String key, final Object value) {
		getExternalContext().getSessionMap().put(key, value);
	}

	public static void putInSession(final Enum<?> key, final Object value) {
		getExternalContext().getSessionMap().put(key.toString(), value);
	}

	public static Object getFromSession(final String key) {
		return getExternalContext().getSessionMap().get(key);
	}

	public static Object getFromSession(final Enum<?> key) {
		return getExternalContext().getSessionMap().get(key.toString());
	}

	public static String getRequestURL() {
		return ((HttpServletRequest) getExternalContext().getRequest()).getRequestURI();
	}

	public static HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) getExternalContext().getRequest();
	}

	public static void addMessage(final String message, final Severity severity) {

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, null));

	}

	public static Properties loadPropertiesFromClasspath(final String propFileName) throws IOException {
		final Properties props = new Properties();
		final InputStream inputStream = ViewUtil.class.getClassLoader().getResourceAsStream(propFileName);
		if (inputStream == null) {
			log.info("Property file '" + propFileName + "' not found in the classpath.");
			throw new FileNotFoundException("Property file '" + propFileName + "' not found in the classpath.");
		}
		props.load(inputStream);
		log.info("Sucessfully loaded the property file '" + propFileName + "'");
		log.info(props.toString());
		return props;
	}

	public static String getViewParameterValue(final String name) {
		final FacesContext fc = FacesContext.getCurrentInstance();
		final String param = fc.getExternalContext().getRequestParameterMap().get(name);
		if (param != null && param.trim().length() > 0) {
			return param;
		} else {
			return null;
		}
	}

	public static String prefixRequestContextPath(final String uri, final boolean replaceSuffix) {
		final String requestContextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		if (uri != null && uri.startsWith("/") && replaceSuffix) {
			return (requestContextPath + uri).replace(".xhtml", ".sms");
		} else if (uri != null && uri.startsWith("/")) {
			return requestContextPath + uri;
		} else if (uri != null && replaceSuffix) {
			return (requestContextPath + "/" + uri).replace(".xhtml", ".sms");
		} else if (uri != null) {
			return requestContextPath + "/" + uri;
		} else {
			return null;
		}
	}

	public static String getEnumLabel(final String labelkey) {
		try {
			final ResourceBundle resourceBundle = ViewUtil.getResourceBundle("enumslabels");
			final String localeValue = resourceBundle.getString(labelkey);
			if (localeValue != null) {
				return localeValue;
			}
			return labelkey;
		} catch (final java.util.MissingResourceException e) {
			return labelkey;
		}
	}

	public static String getPrincipal() {
		if (FacesContext.getCurrentInstance() == null || ViewUtil.getExternalContext() == null) {
			return "";
		} else {
			final SecurityContext sprintContext = (SecurityContext) getExternalContext().getSessionMap().get("SPRING_SECURITY_CONTEXT");

			if (sprintContext == null || sprintContext.getAuthentication() == null || sprintContext.getAuthentication().getPrincipal() == null) {
				return null;
			}
			final org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) sprintContext
					.getAuthentication().getPrincipal();
			return user.getUsername();
		}
	}

	public static Collection<GrantedAuthority> getGrantedAuthorities() {
		final SecurityContext sprintContext = (SecurityContext) getExternalContext().getSessionMap().get("SPRING_SECURITY_CONTEXT");
		if (sprintContext == null || sprintContext.getAuthentication() == null || sprintContext.getAuthentication().getAuthorities() == null) {
			return new ArrayList<GrantedAuthority>();
		}
		return sprintContext.getAuthentication().getAuthorities();
	}

	public static Authentication getAuthentication() {
		final SecurityContext sprintContext = (SecurityContext) getExternalContext().getSessionMap().get("SPRING_SECURITY_CONTEXT");
		if (sprintContext == null || sprintContext.getAuthentication() == null) {
			return null;
		}
		return sprintContext.getAuthentication();
	}
}
