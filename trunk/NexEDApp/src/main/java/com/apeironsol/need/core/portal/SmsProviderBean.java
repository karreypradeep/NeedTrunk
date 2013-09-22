/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.core.portal;

/**
 * View courses class.
 * 
 * @author Pradeep
 */
import java.util.Collection;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.SMSProvider;
import com.apeironsol.need.notifications.service.SMSProviderService;
import com.apeironsol.need.util.portal.ViewUtil;

@Named
@Scope("session")
public class SmsProviderBean extends AbstractKlassBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long		serialVersionUID	= 7674552637897562805L;

	@Resource
	SMSProviderService				sMSProviderService;

	private SMSProvider				smsProvider;

	private Collection<SMSProvider>	smsProviders;

	public String saveSMSProvider() {
		this.sMSProviderService.saveSMSProvider(this.smsProvider);
		this.setViewOrNewAction(false);
		ViewUtil.addMessage("SMSProvider saved sucessfully.", FacesMessage.SEVERITY_INFO);
		return null;
	}

	public String removeSMSProvider() {
		this.sMSProviderService.removeSMSProvider(this.smsProvider);
		ViewUtil.addMessage("SMSProvider removed sucessfully.", FacesMessage.SEVERITY_INFO);
		return null;
	}

	public String newSMSProvider() {
		this.smsProvider = new SMSProvider();
		this.setViewOrNewAction(true);
		return null;
	}

	public void loadSMSProviders() {
		this.smsProviders = this.sMSProviderService.findAllSMSProviders();
	}

	/**
	 * @return the smsProvider
	 */
	public SMSProvider getSmsProvider() {
		return this.smsProvider;
	}

	/**
	 * @param smsProvider
	 *            the smsProvider to set
	 */
	public void setSmsProvider(final SMSProvider smsProvider) {
		this.smsProvider = smsProvider;
	}

	/**
	 * @return the smsProviders
	 */
	public Collection<SMSProvider> getSmsProviders() {
		return this.smsProviders;
	}

	/**
	 * @param smsProviders
	 *            the smsProviders to set
	 */
	public void setSmsProviders(final Collection<SMSProvider> smsProviders) {
		this.smsProviders = smsProviders;
	}

}
