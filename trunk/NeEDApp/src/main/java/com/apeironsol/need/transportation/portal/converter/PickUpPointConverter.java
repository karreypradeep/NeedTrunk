/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.portal.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.apeironsol.need.transportation.model.PickUpPoint;
import com.apeironsol.need.transportation.service.PickUpPointService;

/**
 * @author Pradeep
 * 
 */
@FacesConverter(value = "pickUpPointConverter")
public class PickUpPointConverter implements Converter {

	private PickUpPointService	pickUpPointService;

	public PickUpPointConverter() {
		WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		this.pickUpPointService = ctx.getBean(PickUpPointService.class);
	}

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		try {
			if (value != null && !StringUtils.isEmpty(value)) {
				return this.pickUpPointService.findPickUpPointById(Long.valueOf(value));
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value != null && value instanceof PickUpPoint) {
			return ((PickUpPoint) value).getId().toString();
		} else {
			return null;
		}
	}

}
