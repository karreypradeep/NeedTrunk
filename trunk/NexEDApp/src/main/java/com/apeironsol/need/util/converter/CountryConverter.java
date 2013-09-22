/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.apeironsol.need.core.model.Country;
import com.apeironsol.need.core.portal.CountryBean;

/**
 * @author sandeep
 * 
 */
@FacesConverter(value = "countryConverter")
public class CountryConverter implements Converter {

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		return value != null ? value.isEmpty() ? null : this.getCuntryByISO3Code(value) : null;
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		String str = "";
		if (value != null && value instanceof Country) {
			str = "" + ((Country) value).getIso3Code();
		}
		return str;
	}

	private Country getCuntryByISO3Code(final String iso3Code) {
		CountryBean countryBean = (CountryBean) FacesContext.getCurrentInstance().getExternalContext()
				.getApplicationMap().get("countryBean");
		Country country = countryBean.getCountryByIso3Code(iso3Code);
		return country;
	}

}
