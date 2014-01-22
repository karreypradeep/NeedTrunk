/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.security.portal;

/**
 * Login JSF managed bean class.
 * 
 * @author Pradeep
 */
import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.core.portal.SessionBean;
import com.apeironsol.need.security.service.UserAccountService;
import com.apeironsol.need.security.service.UserService;
import com.apeironsol.need.util.portal.ViewUtil;

@Named
@Scope("session")
public class ChangePasswordBean implements Serializable {

	private static final long		serialVersionUID	= -2399884208294434813L;

	private static final Logger		log					= Logger.getLogger(ChangePasswordBean.class);

	@Resource(name = "authenticationManager")
	private AuthenticationManager	authenticationManager;

	@Resource
	private UserAccountService		userAccountService;

	@Resource
	private UserService				userService;

	@Resource
	private SessionBean				sessionBean;

	@NotEmpty(message = "Old Password cannot be empty.")
	private String					oldPassword;

	@NotEmpty(message = "New Password cannot be empty.")
	private String					newPassword;

	@NotEmpty(message = "Confirm Password cannot be empty.")
	private String					confirmPassword;

	@PostConstruct
	public void init() {
	}

	public String changePassword() throws IOException {

		try {

			Authentication request = new UsernamePasswordAuthenticationToken(this.sessionBean.getPrinipal(), this.oldPassword);
			this.authenticationManager.authenticate(request);
			this.userService.updatePasswordForUserAccount(this.sessionBean.getPrinipal(), this.newPassword);
			ViewUtil.addMessage("Passowrd changed successfully.", FacesMessage.SEVERITY_INFO);
		} catch (ApplicationException exception) {
			log.info(exception.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Authentication failed!", exception.getMessage()));
			return null;
		} catch (AuthenticationException exception) {
			log.info(exception.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Authentication failed!", exception.getMessage()));
			return null;

		}
		return null;
	}

	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return this.oldPassword;
	}

	/**
	 * @param oldPassword
	 *            the oldPassword to set
	 */
	public void setOldPassword(final String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return this.newPassword;
	}

	/**
	 * @param newPassword
	 *            the newPassword to set
	 */
	public void setNewPassword(final String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	/**
	 * @param confirmPassword
	 *            the confirmPassword to set
	 */
	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
