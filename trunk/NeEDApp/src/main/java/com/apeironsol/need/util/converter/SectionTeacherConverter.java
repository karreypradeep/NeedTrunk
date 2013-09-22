package com.apeironsol.need.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.apeironsol.need.core.model.SectionTeacher;
import com.apeironsol.need.core.service.SectionTeacherService;

@FacesConverter("sectionTeacherConverter")
public class SectionTeacherConverter implements Converter {

	private final SectionTeacherService	sectionTeacherService;

	public SectionTeacherConverter() {
		WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		this.sectionTeacherService = ctx.getBean(SectionTeacherService.class);
	}

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		try {
			if (value != null && !StringUtils.isEmpty(value)) {
				return this.sectionTeacherService.findSectionTeacherById(Long.valueOf(value));
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value != null && value instanceof SectionTeacher) {
			return ((SectionTeacher) value).getId().toString();
		} else {
			return null;
		}
	}

}
