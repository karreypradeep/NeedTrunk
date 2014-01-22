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
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.Organization;
import com.apeironsol.need.core.portal.SessionBean;
import com.apeironsol.need.core.service.BranchService;
import com.apeironsol.need.core.service.OrganizationService;
import com.apeironsol.need.security.service.UserAccountService;
import com.apeironsol.need.util.constants.AccessControlTypeConstant;
import com.apeironsol.need.util.constants.AuthorityConstant;
import com.apeironsol.need.util.portal.SessionAttribures;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;

@Named("loginBean")
@Scope("session")
public class LoginBean implements Serializable {

	private static final long		serialVersionUID			= -2399884208294434813L;

	private static final Logger		log							= Logger.getLogger(LoginBean.class);

	@Resource(name = "authenticationManager")
	private AuthenticationManager	authenticationManager;

	@Resource
	private OrganizationService		organizationService;

	@Resource
	private BranchService			branchService;

	@Resource
	private UserAccountService		userAccountService;

	@Resource
	private SessionBean				sessionBean;

	private Organization			organization;

	// @NotEmpty(message = "Please select the organization unit.")
	private Long					branchId;

	private Collection<Branch>		branchs						= new ArrayList<Branch>();

	@NotEmpty(message = "Username cannot be empty.")
	private String					username;

	@NotEmpty(message = "Password cannot be empty.")
	private String					password;

	private final int				sessionTimeoutTime			= 5;

	private final int				preSessionExpireTimeInSecs	= 15;

	@PostConstruct
	public void init() {
		try {
			this.organization = this.organizationService.getOrginazation();
			this.sessionBean.setCurrentOrganization(this.organization);
			this.branchs = this.branchService.findAllBranchsByActiveState(Boolean.TRUE);
		} catch (Exception ex) {
			log.info(ex.getMessage());
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String login() throws IOException {

		try {
			Collection<GrantedAuthority> grantedAuthorities = null;

			Authentication result = null;

			// Check Authentication And Authorization
			if (!this.isLoggedIn()) {

				Authentication request = new UsernamePasswordAuthenticationToken(this.username, this.password);

				result = this.authenticationManager.authenticate(request);

				grantedAuthorities = result.getAuthorities();

			} else {

				result = ViewUtil.getAuthentication();

				grantedAuthorities = ViewUtil.getGrantedAuthorities();
			}

			if (this.isAccessingBranch() && this.hasEmployeeRole(grantedAuthorities)
					&& !this.organizationService.hasAccess(this.username, this.branchId, AccessControlTypeConstant.BRANCH)) {

				String message = "User " + this.username + " do not have access for branch " + this.branchService.findBranchById(this.branchId).getName();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));

				return null;

			}

			SecurityContextHolder.getContext().setAuthentication(result);

			ExternalContext context = ViewUtil.getExternalContext();

			// Set required data in session.
			ViewUtil.putInSession(SessionAttribures.ORGANIZATION, this.organization);
			if (this.branchId != null) {
				Branch currentBranch = this.branchService.findBranchById(this.branchId);
				this.sessionBean.setCurrentBranch(currentBranch);

			}

			if (!this.isLoggedIn()) {
				this.sessionBean.setCurrentUserAccount(this.userAccountService.findUserAccountByName(this.username));
			}
			DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) context.getSessionMap().get(WebAttributes.SAVED_REQUEST);

			context.redirect(defaultSavedRequest.getRedirectUrl());

			FacesContext.getCurrentInstance().responseComplete();

			this.clearLogin();

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

	private void clearLogin() {
		this.username = null;
		this.password = null;
	}

	public void loginReset() {
		this.clearLogin();
		this.branchId = null;
	}

	public void logout() throws IOException {
		this.clearLogin();
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		context.redirect(context.getRequestContextPath() + "/j_spring_security_logout");
		FacesContext.getCurrentInstance().responseComplete();
	}

	public void redirectToHome() throws IOException {
		String homeURL = this.sessionBean.getHomeURL();

		if (homeURL != null) {

			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect(homeURL);
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
			navigationHandler.handleNavigation(context, null, "home");

		}
		FacesContext.getCurrentInstance().responseComplete();
	}

	public Collection<Branch> getBranchs() {
		return this.branchs;
	}

	public void setBranchs(final Collection<Branch> branchs) {
		this.branchs = branchs;
	}

	@NotNull(message = "Branch is mandatory.")
	public Long getBranchId() {
		return this.branchId;
	}

	public void setBranchId(final Long branchId) {
		ViewUtil.putInSession(SessionAttribures.BRANCH_ID, branchId);
		this.branchId = branchId;
	}

	private boolean hasEmployeeRole(final Collection<GrantedAuthority> grantedAuthorities) {
		return this.hasAuthority(AuthorityConstant.ROLE_EMPLOYEE, grantedAuthorities);
	}

	private boolean hasAuthority(final AuthorityConstant authority, final Collection<GrantedAuthority> grantedAuthorities) {
		boolean result = false;
		for (GrantedAuthority grantedAuthority : grantedAuthorities) {
			if (grantedAuthority.getAuthority().equals(authority.name())) {
				result = true;
				break;
			}
		}
		return result;
	}

	public boolean isPageLoginMenu() {

		return ViewUtil.getExternalContext().getRequestServletPath().contains("home");
	}

	public boolean isPageLogin() {
		return ViewUtil.getExternalContext().getRequestServletPath().contains("login");
	}

	public void putHomeURLInSeccion() {

		String homeURL = ViewUtil.getViewParameterValue("homeURL");
		this.sessionBean.setHomeURL(homeURL);

	}

	public void redirectToLoginMenu() {

		try {

			String requestURL = ViewUtil.getRequestURL();

			String requestContestPath = ViewUtil.getExternalContext().getRequestContextPath();

			String redirectURL = requestContestPath + "/home.sms?homeURL=" + requestURL;

			ViewUtil.getExternalContext().redirect(redirectURL);

			FacesContext.getCurrentInstance().responseComplete();

		} catch (Exception e) {
			log.info(e.getMessage());
			ViewExceptionHandler.handle(e);
		}

	}

	public String getResourceAccessLevel() {
		ExternalContext context = ViewUtil.getExternalContext();
		if (context != null) {

			DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) context.getSessionMap().get(WebAttributes.SAVED_REQUEST);
			if (defaultSavedRequest != null) {
				String requestURL = defaultSavedRequest.getRequestURL();
				if (StringUtils.isEmpty(requestURL)) {
					return null;
				} else if (requestURL.contains("pages/organization")) {
					return "organization";
				} else if (requestURL.contains("pages/branch")) {
					return "branch";
				} else if (requestURL.contains("pages/student")) {
					return "student";
				} else if (requestURL.contains("pages/parent")) {
					return "parent";
				} else if (requestURL.contains("pages/employee")) {
					return "employee";
				} else {
					return null;
				}
			}
		}
		return null;
	}

	public String getRequestURL() {

		return ViewUtil.getRequestURL();
	}

	public boolean isAccessingBranch() {
		if ("branch".equals(this.getResourceAccessLevel())) {
			return true;
		}
		return false;
	}

	public boolean isAccessingOrganization() {
		if ("organization".equals(this.getResourceAccessLevel())) {
			return true;
		}
		return false;
	}

	public boolean isLoggedIn() {
		return ViewUtil.getPrincipal() == null ? false : true;
	}

	public void loadBranchs() {
		try {
			if ("branch".equals(this.getResourceAccessLevel())) {
				this.branchs = this.branchService.findAllBranchsByActiveState(Boolean.TRUE);
			}
		} catch (ApplicationException exception) {
			log.info(exception.getMessage());
			ViewExceptionHandler.handle(exception);
		}
	}

	public int getSessionTimeoutTime() {
		return this.sessionTimeoutTime;
	}

	public int getSessionTimeoutTimeInSecs() {
		return this.sessionTimeoutTime * 60;
	}

	public int getSessionTimeoutTimeInMilliSecs() {
		return this.sessionTimeoutTime * 60 * 1000;
	}

	public int getPreSessionExpireTimeInSecs() {
		return this.preSessionExpireTimeInSecs;
	}

	public int getPreSessionExpireTimeInMilliSecs() {
		return this.preSessionExpireTimeInSecs * 1000;
	}

}
