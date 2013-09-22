package com.apeironsol.need.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.service.SubjectService;

@FacesConverter("subjectConverter")
public class SubjectConverter implements Converter {

	private final SubjectService	subjectService;

	public SubjectConverter() {
		WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		this.subjectService = ctx.getBean(SubjectService.class);
	}

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		try {
			if (value != null && !StringUtils.isEmpty(value)) {
				return this.subjectService.findSubjectById(Long.valueOf(value));
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value != null && value instanceof Section) {
			return ((Section) value).getId().toString();
		} else {
			return null;
		}
	}

}
