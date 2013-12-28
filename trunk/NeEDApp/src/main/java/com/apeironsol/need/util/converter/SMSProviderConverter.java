package com.apeironsol.need.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.apeironsol.need.core.model.SMSProvider;
import com.apeironsol.need.notifications.service.SMSProviderService;

@FacesConverter("smsProviderConverter")
public class SMSProviderConverter implements Converter {

	private final SMSProviderService	sMSProviderService;

	public SMSProviderConverter() {
		WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		this.sMSProviderService = ctx.getBean(SMSProviderService.class);
	}

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		try {
			if (value != null && !StringUtils.isEmpty(value)) {
				return this.sMSProviderService.findSMSProviderById(Long.valueOf(value));
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value != null && value instanceof SMSProvider) {
			return ((SMSProvider) value).getId().toString();
		} else {
			return null;
		}
	}

}
