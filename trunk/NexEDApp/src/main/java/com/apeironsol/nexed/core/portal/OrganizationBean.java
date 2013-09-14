/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.core.portal;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.nexed.core.model.Organization;
import com.apeironsol.nexed.core.service.OrganizationService;
import com.apeironsol.nexed.util.constants.RegistrationTypeConstant;
import com.apeironsol.nexed.util.portal.SessionAttribures;
import com.apeironsol.nexed.util.portal.ViewExceptionHandler;
import com.apeironsol.nexed.util.portal.ViewUtil;
import com.apeironsol.framework.exception.BusinessException;

@Named
@Scope(value = "session")
public class OrganizationBean extends AbstractPortalBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long	serialVersionUID	= -6316988332474867136L;

	@Resource
	private OrganizationService	organizationService;

	private Organization		organization;

	@PostConstruct
	public void init() {
		if (ViewUtil.getFromSession(SessionAttribures.ORGANIZATION) == null)
			this.organization = this.organizationService.getOrginazation();
		else
			this.organization = (Organization) ViewUtil.getFromSession(SessionAttribures.ORGANIZATION);

		if (this.organization == null)
			this.organization = new Organization();

	}

	@Override
	public Organization getOrganization() {
		return this.organization;
	}

	public RegistrationTypeConstant[] getRegistrationTypes() {
		return RegistrationTypeConstant.values();
	}

	public void setOrganization(final Organization organization) {
		this.organization = organization;
	}

	public boolean isDisableOrganization() {
		return this.organization.getId() != null;
	}

	public void saveOrganization() {
		try {
			this.organization = this.organizationService.saveOrganization(this.organization);
			ViewUtil.addMessage("Organization saved sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (BusinessException e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void lockUnlockOrganization() {
		try {
			this.organization = this.organizationService.lockUnlockOrganization(this.organization);
			if (this.organization.isLocked())
				ViewUtil.addMessage("Organization locked sucessfully.", FacesMessage.SEVERITY_INFO);
			else
				ViewUtil.addMessage("Organization unlocked sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (BusinessException e) {
			ViewExceptionHandler.handle(e);
		}
	}

}
