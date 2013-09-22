package com.apeironsol.need.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.apeironsol.need.core.model.SectionSubject;
import com.apeironsol.need.core.service.SectionSubjectService;

@FacesConverter("sectionSubjectConverter")
public class SectionSubjectConverter implements Converter {

	private final SectionSubjectService	sectionSubjectService;

	public SectionSubjectConverter() {
		WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		this.sectionSubjectService = ctx.getBean(SectionSubjectService.class);
	}

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		try {
			if (value != null && !StringUtils.isEmpty(value)) {
				return this.sectionSubjectService.findSectionSubjectById(Long.valueOf(value));
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value != null && value instanceof SectionSubject) {
			return ((SectionSubject) value).getId().toString();
		} else {
			return null;
		}
	}

}
