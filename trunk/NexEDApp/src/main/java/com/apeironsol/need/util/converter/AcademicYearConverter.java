package com.apeironsol.need.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.service.AcademicYearService;

@FacesConverter("academicYearConverter")
public class AcademicYearConverter implements Converter {

	private final AcademicYearService	academicYearService;

	public AcademicYearConverter() {
		WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		this.academicYearService = ctx.getBean(AcademicYearService.class);
	}

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		try {
			if (value != null && !StringUtils.isEmpty(value)) {
				return this.academicYearService.findAcademicYearById(Long.valueOf(value));
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value != null && value instanceof AcademicYear) {
			return ((AcademicYear) value).getId().toString();
		} else {
			return null;
		}
	}

}
